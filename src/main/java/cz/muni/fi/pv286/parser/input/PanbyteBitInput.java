package cz.muni.fi.pv286.parser.input;

import cz.muni.fi.pv286.parser.Util;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;

import java.io.IOException;
import java.util.*;

public class PanbyteBitInput extends PanbyteInput {
    /** Internal buffer of yet unparsed bytes */
    private final List<Byte> unparsedBuffer = new ArrayList<>();
    /** All allowed lowercase bit characters */
    private static final Byte[] allowedCharacters = {'0', '1'};
    /** Initialized in constructor as set for faster searching in allowed LOWERCASE characters */
    private final Set<Byte> allowedCharactersSet = new HashSet<>();

    /** While reading bits, they might be aligned from left or right
     *  This can be adjusted after reading all bits from input */
    private ArrayList<Byte> unalignedParsedBytes = new ArrayList<>();


    public PanbyteBitInput(PanbyteOutput output) {
        super(output);
        this.allowedCharactersSet.addAll(Arrays.asList(PanbyteBitInput.allowedCharacters));
    }

    @Override
    public void parse(List<Byte> buffer) throws IOException {
        // append all received bytes to the previously unparsed ones
        this.unparsedBuffer.addAll(buffer);

        Byte readByte = 0;
        byte currentByte = 0;
        int numberOfBits = 7;
        // read unparsed bits while they are available
        while ((readByte = this.popUnparsedByte()) != null) {
            int bitValue = (int) readByte - 48;
            currentByte |= (bitValue << numberOfBits);
            numberOfBits--;
            if (numberOfBits == -1) {
                unalignedParsedBytes.add(currentByte);
                numberOfBits = 7;
            }
        }

        this.parsedBytes.addAll(unalignedParsedBytes);
        this.flush();
    }

    /**
     * Assert that all left bytes in the unparsed buffer are whitespace
     */
    @Override
    public void parserFinalize() {
        // check if everything was already parsed and nothing more is available
        if (this.popUnparsedByte() != null) {
            throw new IllegalArgumentException("More input is expected");
        }

        super.parserFinalize();
    }

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
}
