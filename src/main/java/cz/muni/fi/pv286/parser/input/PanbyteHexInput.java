package cz.muni.fi.pv286.parser.input;

import cz.muni.fi.pv286.parser.Util;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Parsers hex input
 * Ignores whitespace
 */
public class PanbyteHexInput extends PanbyteInputBase {
    /** A tuple of bytes */
    private static class ByteTuple {
        public final byte a;
        public final byte b;

        public ByteTuple(final byte a, final byte b) {
            this.a = a;
            this.b = b;
        }
    }

    /** All allowed lowercase hex characters */
    private static final Byte[] allowedCharacters = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /** Initialized in constructor as set for faster searching in allowed LOWERCASE characters */
    private final Set<Byte> allowedCharactersSet = new HashSet<>();
    /** Internal buffer of yet unparsed bytes, may contain up to one (odd) byte from parse() call */
    private final List<Byte> unparsedBuffer = new ArrayList<>();

    public PanbyteHexInput(final PanbyteOutput output) {
        super(output);
        this.allowedCharactersSet.addAll(Arrays.asList(PanbyteHexInput.allowedCharacters));
    }

    @Override
    public void parse(final List<Byte> buffer) throws IOException {
        // append all received bytes to the previously unparsed ones
        this.unparsedBuffer.addAll(buffer);

        // should flush only if a change was made to the output
        boolean shouldFlush = false;

        // read unparsed bytes in tuples while they are available
        ByteTuple bytes;
        while ((bytes = this.popTwoUnparsedBytes()) != null) {
            // parse hex string into an integer
            final String hexString = Util.byteAsASCII(bytes.a) + Util.byteAsASCII(bytes.b);
            assert hexString.length() == 2;
            final int hexNumber = Integer.parseInt(hexString, 16);

            // two hex characters fit inside one byte
            final byte parsedByte = (byte) hexNumber;
            assert parsedByte >= 0 ? hexNumber == parsedByte : -256 + hexNumber == parsedByte;

            // mark the byte as parsed
            this.parsedBytes.add(parsedByte);
            shouldFlush = true;
        }

        if (shouldFlush) {
            this.flush();
        }
    }

    /**
     * Assert that all left bytes in the unparsed buffer are whitespace
     */
    @Override
    public void parserFinalize() throws IOException {
        // check if everything was already parsed and nothing more is available
        if (this.popUnparsedByte() != null) {
            throw new IllegalArgumentException("More input is expected");
        }

        super.parserFinalize();
    }

    /**
     * Take two allowed bytes from the unparsed bytes or take none
     * @return Bytes tuple if available, null if no more byte tuples can be taken
     */
    private ByteTuple popTwoUnparsedBytes() {
        Byte a = this.popUnparsedByte();
        if (a == null) {
            return null;
        }
        Byte b = this.popUnparsedByte();
        if (b == null) {
            // return the first byte back to the beginning of unparsed bytes
            this.unparsedBuffer.add(0, a);
            return null;
        }

        return new ByteTuple(a, b);
    }

    /**
     * Takes a single allowed unparsed byte if available
     * @return allowed byte or null if not more are available
     */
    private Byte popUnparsedByte() {
        while (this.unparsedBuffer.size() > 0) {
            // pop first unparsed byte
            final Byte nextByte = this.unparsedBuffer.remove(0);

            if (Character.isWhitespace(nextByte)) {
                // skip all whitespace
                continue;
            }

            final byte[] nextByteLowercase = Util.byteAsASCII(nextByte).toLowerCase().getBytes();
            // nextByteLowercase length must be exactly one, because all allowed characters are ASCII
            if (nextByteLowercase.length != 1 || !this.allowedCharactersSet.contains(nextByteLowercase[0])) {
                throw new IllegalArgumentException("Byte '" + nextByte + "' is not in allowed characters for this mode");
            }

            return nextByte;
        }

        // no more bytes
        return null;
    }

    @Override
    public PanbyteInput getFresh() {
        return new PanbyteHexInput(this.output.getFresh());
    }
}
