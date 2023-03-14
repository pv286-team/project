package cz.muni.fi.pv286.parser.output;

import cz.muni.fi.pv286.arguments.values.Option;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PanbyteIntOutput extends PanbyteOutput {

    private final Option endianity;

    public PanbyteIntOutput(OutputStream outputStream, Option option) {
        super(outputStream);
        endianity = option;
    }

    @Override
    public void stringify(final List<Byte> buffer) throws IOException {
        final List<Byte> out = new ArrayList<>();

        if (endianity == Option.LITTLE_ENDIAN) {
            Collections.reverse(buffer);
        }

        final byte[] integerByteArray = new byte[buffer.size()];
        for (int i = 0; i < buffer.size(); i++) {
            integerByteArray[i] = buffer.get(i);
        }

        final BigInteger integer = new BigInteger(integerByteArray);
        final String digits = integer.toString(10);

        // using platform default charset
        for (byte b : digits.getBytes()) {
            out.add(b);
        }

        this.sendOutputData(out);
    }

    @Override
    public void parserFinalize() {
        // no-op, everything already has been sent from stringify
    }

    @Override
    public PanbyteIntOutput getFresh() {
        return new PanbyteIntOutput(this.outputStream, endianity);
    }
}
