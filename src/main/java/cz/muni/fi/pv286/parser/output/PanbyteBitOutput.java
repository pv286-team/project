package cz.muni.fi.pv286.parser.output;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PanbyteBitOutput extends PanbyteOutputBase {
    final boolean zeroPadding;

    public PanbyteBitOutput(OutputStream outputStream, boolean zeroPadding) {
        super(outputStream);
        this.zeroPadding = zeroPadding;
    }

    @Override
    public void stringify(List<Byte> buffer) throws IOException {
        final List<Byte> out = new ArrayList<>();

        for (final Byte currentByte : buffer) {
            boolean firstNonZeroBit = false;
            final StringBuilder bitString = new StringBuilder();
            for (int i = 7; i >= 0; i--) {
                int bitValue = (int) currentByte & (1 << i);
                if (bitValue != 0) {
                    firstNonZeroBit = true;
                }
                if (!zeroPadding && !firstNonZeroBit) {
                    continue;
                }
                String bit = bitValue != 0 ? "1" : "0";
                bitString.append(bit);
            }

            final byte[] bitStringBytes = bitString.toString().getBytes(StandardCharsets.US_ASCII);
            for (byte bitStringByte : bitStringBytes) {
                out.add(bitStringByte);
            }
        }

        this.sendOutputData(out);
    }

    @Override
    public void parserFinalize() {

    }

    @Override
    public PanbyteOutput getFresh() {
        return new PanbyteBitOutput(this.outputStream, zeroPadding);
    }
}
