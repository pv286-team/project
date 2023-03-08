package cz.muni.fi.pv286.parser.input;

import cz.muni.fi.pv286.parser.output.PanbyteOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class PanbyteHexInput extends PanbyteInput {
    private static class ByteTuple {
        public final byte a;
        public final byte b;

        public ByteTuple(final byte a, final byte b) {
            this.a = a;
            this.b = b;
        }
    }

    private static final Byte[] allowedCharacters = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private final Set<Byte> allowedCharactersSet = new HashSet<>();
    private final List<Byte> unparsedBuffer = new ArrayList<>();

    public PanbyteHexInput(final PanbyteOutput output) {
        super(output);
        this.allowedCharactersSet.addAll(Arrays.asList(PanbyteHexInput.allowedCharacters));
    }

    @Override
    public void parse(final List<Byte> buffer) throws IOException {
        this.unparsedBuffer.addAll(buffer);

        boolean shouldFlush = false;
        ByteTuple bytes;
        while ((bytes = this.popTwoUnparsedBytes()) != null) {
            final String hexString = this.byteAsASCII(bytes.a) + this.byteAsASCII(bytes.b);
            assert hexString.length() == 2;
            final int hexNumber = Integer.parseInt(hexString, 16);
            final byte parsedByte = (byte) hexNumber;
            assert hexNumber == parsedByte;
            this.parsedBytes.add(parsedByte);
            shouldFlush = true;
        }

        if (shouldFlush) {
            this.flush();
        }
    }

    @Override
    public void parserFinalize() {
        // Assert that all left bytes in buffer are whitespace (they return null after parsing)
        while (this.unparsedBuffer.size() > 0)  {
            if (this.popUnparsedByte() != null) {
                throw new IllegalArgumentException("More input is expected");
            }
        }

        super.parserFinalize();
    }

    private ByteTuple popTwoUnparsedBytes() {
        Byte a = this.popUnparsedByte();
        if (a == null) {
            return null;
        }
        Byte b = this.popUnparsedByte();
        if (b == null) {
            // return a back into unparsed bytes
            this.unparsedBuffer.add(0, a);
            return null;
        }

        return new ByteTuple(a, b);
    }

    private Byte popUnparsedByte() {
        while (this.unparsedBuffer.size() > 0) {
            // pop first unparsed byte
            final Byte nextByte = this.unparsedBuffer.remove(0);

            if (Character.isWhitespace(nextByte)) {
                // skip all whitespace
                continue;
            }

            final byte[] nextByteLowercase = this.byteAsASCII(nextByte).toLowerCase().getBytes();
            // nextByteLowercase length must be exactly one, because all allowed characters are ASCII
            if (nextByteLowercase.length != 1 || !this.allowedCharactersSet.contains(nextByteLowercase[0])) {
                throw new IllegalArgumentException("Byte '" + nextByte + "' is not in allowed characters for this mode");
            }

            return nextByte;
        }

        // no more bytes
        return null;
    }

    private String byteAsASCII(final Byte b) {
        return String.valueOf((char) b.byteValue());
    }
}
