package cz.muni.fi.pv286.parser.input;

import cz.muni.fi.pv286.arguments.values.Option;
import cz.muni.fi.pv286.parser.Util;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** This class stores digit after digit in BigInteger and only then converts them to bytes. */
public class PanbyteIntInput extends PanbyteInput {

    private final List<Byte> unparsedBuffer = new ArrayList<>();
    private BigInteger integer = new BigInteger("0");
    private final Option endianity;

    public PanbyteIntInput(final PanbyteOutput output, Option option) {
        super(output);
        endianity = option;
    }

    public void parse(final List<Byte> buffer) {
        // append all received bytes to the previously unparsed ones
        this.unparsedBuffer.addAll(buffer);

        while (this.unparsedBuffer.size() > 0) {
            // pop first unparsed byte
            final Byte nextByte = this.unparsedBuffer.remove(0);

            if (Character.isWhitespace(nextByte)) {
                // skip all whitespace
                continue;
            }
            if (!Character.isDigit(nextByte)) {
                // skip all non-digit characters
                continue;
            }

            integer = integer.multiply(new BigInteger("10"));
            integer = integer.add(new BigInteger(Util.byteAsASCII(nextByte)));
        }
    }

    @Override
    public void parserFinalize() throws IOException {
        // check if everything was already parsed and nothing more is available
        if (this.unparsedBuffer.size() > 0) {
            throw new IllegalArgumentException("More input is expected");
        }
        // check if some partial parsing have been done
        if (this.parsedBytes.size() > 0) {
            throw new IllegalArgumentException("Internal buffer should be empty");
        }

        byte[] integerByteArray = integer.toByteArray();

        for (byte b : integerByteArray) {
            this.parsedBytes.add(b);
        }

        if (endianity == Option.LITTLE_ENDIAN) {
            Collections.reverse(this.parsedBytes);
        }

        this.flush();
        super.parserFinalize();
    }

    @Override
    public PanbyteInput getFresh() {
        return new PanbyteIntInput(this.output.getFresh(), endianity);
    }
}
