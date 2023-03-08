package cz.muni.fi.pv286.parser.output;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Outputs received bytes as hex
 */
public class PanbyteHexOutput extends PanbyteOutput {

    public PanbyteHexOutput(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public void stringify(final List<Byte> buffer) throws IOException {
        final List<Byte> out = new ArrayList<>();

        // each byte convert into two hex characters
        for (final Byte byteBuffer : buffer) {
            final String hexString = String.format("%x", byteBuffer);
            final byte[] hexStringBytes = hexString.getBytes();
            assert hexStringBytes.length == 1 || hexStringBytes.length == 2;
            if (hexStringBytes.length < 2) {
                out.add((byte) '0');
            }
            for (byte hexStringByte : hexStringBytes) {
                out.add(hexStringByte);
            }
        }

        this.sendOutputData(out);
    }

    @Override
    public void parserFinalize() {
        // no-op, everything already has been sent from stringify
    }
}
