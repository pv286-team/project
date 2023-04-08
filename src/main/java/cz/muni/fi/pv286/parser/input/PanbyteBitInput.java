package cz.muni.fi.pv286.parser.input;

import cz.muni.fi.pv286.arguments.values.Option;
import cz.muni.fi.pv286.parser.Util;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PanbyteBitInput extends PanbyteInputBase {
    /** Internal buffer of yet unparsed bytes */
    private final List<Byte> unparsedBuffer = new ArrayList<>();
    /** All allowed lowercase bit characters */
    private static final Byte[] allowedCharacters = {'0', '1'};
    /** Initialized in constructor as set for faster searching in allowed LOWERCASE characters */
    private final Set<Byte> allowedCharactersSet = new HashSet<>();
    private static final int highestBitIndex = 7;
    private static final int bits = 8;

    /** While reading bits, they might be aligned from left or right
     *  This can be adjusted after reading all bits from input */
    private final ArrayList<Byte> unalignedParsedBytes = new ArrayList<>();
    private int parsedBitsNumber;
    private final Option padding;


    public PanbyteBitInput(PanbyteOutput output, Option option) {
        super(output);
        padding = option;
        this.parsedBitsNumber = 0;
        this.allowedCharactersSet.addAll(Arrays.asList(PanbyteBitInput.allowedCharacters));
    }

    @Override
    public void parse(List<Byte> buffer) throws IOException {
        // append all received bytes to the previously unparsed ones
        this.unparsedBuffer.addAll(buffer);

        //Byte readByte;
        byte currentByte = 0;
        int numberOfBits = highestBitIndex;
        byte[] eightBytes;

        // read from unparsed bytes by 8
        while ((eightBytes = this.popUnparsedEightBytes()) != null) {
            for (byte popped : eightBytes) {
                int bitValue = (int) popped - (int) '0';
                // add newly added bit to the highest possible position
                currentByte |= (bitValue << numberOfBits);
                numberOfBits--;
                parsedBitsNumber++;
                if (numberOfBits == -1) {
                    // if full byte, add it to the output buffer
                    addSingleByte(currentByte);
                    numberOfBits = highestBitIndex;
                    currentByte = 0;
                }
            }
        }
        // the right padding is the native way how to store bits, no need for further aligning
        if (padding == Option.RIGHT_PAD) {
            this.flush();
        }
    }

    @Override
    public PanbyteInput getFresh() {
        return new PanbyteBitInput(this.output.getFresh(), padding);
    }

    /**
     * Assert that all left bytes in the unparsed buffer are whitespace
     */
    @Override
    public void parserFinalize() throws IOException {
        // if there are still some unparsed input bytes, there is less than 8 of them (otherwise already parsed)
        if (!unparsedBuffer.isEmpty()) {
            Byte popped;
            int numberOfBits = highestBitIndex;
            byte currentByte = 0;
            while ((popped = popUnparsedByte()) != null) {
                int bitValue = (int) popped - (int) '0';
                currentByte |= (bitValue << numberOfBits);
                numberOfBits--;
                parsedBitsNumber++;
            }
            addSingleByte(currentByte);
        }
        // for right padding we can directly flush
        if (padding == Option.RIGHT_PAD) {
            this.flush();
        }
        // for left padding, we need to read all data from input and then align
        if (padding == Option.LEFT_PAD) {
            this.parsedBytes.addAll(alignBytesLeft());
            this.flush();
        }

        super.parserFinalize();
    }

    /**
     * Add processed byte either directly to output or to the temporal auxiliary buffer
     */
    private void addSingleByte(byte current) {
        if (padding == Option.LEFT_PAD) {
            unalignedParsedBytes.add(current);
        } else {
            this.parsedBytes.add(current);
        }
    }

    /**
     * Read from unparsed buffer 8 bytes representing 8 bits,
     * if 8 bytes cannot be read, put them back.
     * @return allowed byte or null if not more are available
     */
    private byte[] popUnparsedEightBytes() {
        int poppedNumber = 0;
        byte[] bytes = new byte[bits];

        for (int i = 0; i < bits; i++) {
            Byte popped = popUnparsedByte();
            if (popped == null) {
                // there are not 8 bits ready, put them back
                for (int j = 0; j < poppedNumber; j++) {
                    this.unparsedBuffer.add(j, bytes[j]);
                }
                return null;
            }
            poppedNumber++;
            bytes[i] = popped;
        }
        return bytes;
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

    /**
     * Takes single input byte, the number of bits to be shifted and direction
     * @return shifted byte
     */
    private byte shift(byte in, int shift, boolean direction) {
        byte out = 0;
        // go through all bits in input byte, check where 1 is and insert it shifted to the output byte
        for (int i = highestBitIndex; i >= 0; i--) {
            int mask = 1 << i;
            if (((int) in & (1 << i)) != 0) {
                out |= (direction ? mask >> shift : mask << shift);
            }
        }
        return out;
    }

    /**
     * Takes unaligned parsed bit and aligns them according to the input option
     * @return padded bits from left/right
     */
    private ArrayList<Byte> alignBytesLeft() {
        ArrayList<Byte> alignedBytes = new ArrayList<>();
        if (parsedBitsNumber == 0 || parsedBitsNumber % bits == 0) {
            // We do not have to add padding
            alignedBytes.addAll(unalignedParsedBytes);
            return alignedBytes;
        }

        // Get size of the shift and pad the first byte
        int shift = 8 - (parsedBitsNumber % bits);
        byte b = unalignedParsedBytes.get(0);
        byte shifted = shift(b, shift, true);
        alignedBytes.add(shifted);

        // Then, get & shift second part of the current byte and first part of next byte, so they fit into new one byte
        for (int i = 0; i < unalignedParsedBytes.size() - 1; i++) {
            byte currentByte = 0;
            currentByte |= shift(unalignedParsedBytes.get(i), bits - shift, false);
            currentByte |= shift(unalignedParsedBytes.get(i + 1), shift, true);
            alignedBytes.add(currentByte);
        }
        return alignedBytes;
    }
}
