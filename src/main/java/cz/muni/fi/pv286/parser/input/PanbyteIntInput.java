package cz.muni.fi.pv286.parser.input;

import cz.muni.fi.pv286.arguments.values.Option;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/** This class has to load all digits from input in the internal buffer and only then convert them to bytes. */
public class PanbyteIntInput extends PanbyteInput {

    private final List<Byte> unparsedBuffer = new ArrayList<>();
    private final List<Byte> integerInputBytes = new ArrayList<>();
    private String digits = "";
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

            integerInputBytes.add(nextByte);
        }

        // from parsed bytes make String and append it to stored digits
        digits = digits.concat(integerInputBytes.stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining())
        );
    }

    @Override
    public void parserFinalize() {
        // check if everything was already parsed and nothing more is available
        if (this.unparsedBuffer.size() > 0) {
            throw new IllegalArgumentException("More input is expected");
        }
        // check if some partial parsing have been done
        if (this.parsedBytes.size() > 0) {
            throw new IllegalArgumentException("Internal buffer should be empty");
        }

        BigInteger integer = new BigInteger(digits);
        byte[] integerByteArray = integer.toByteArray();

        for (byte b : integerByteArray) {
            this.parsedBytes.add(b);
        }

        if (endianity == Option.LITTLE_ENDIAN) {
            Collections.reverse(this.parsedBytes);
        }

        super.parserFinalize();
    }

    @Override
    public PanbyteInput getFresh() {
        return new PanbyteIntInput(this.output.getFresh(), endianity);
    }
}
