package cz.muni.fi.pv286.parser.output;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PanbyteBitOutput extends PanbyteOutput {

    public PanbyteBitOutput(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public void stringify(List<Byte> buffer) throws IOException {
        final List<Byte> out = new ArrayList<>();

        for (final Byte currentByte : buffer) {
            final StringBuilder bitString = new StringBuilder();
            for (int i = 7; i >= 0; i--) {
                String bit = ((int) currentByte & (1 << i)) != 0 ? "1" : "0";
                bitString.append(bit);
            }

            final byte[] bitStringBytes = bitString.toString().getBytes();
            for (byte bitStringByte : bitStringBytes) {
                out.add(bitStringByte);
            }
        }

        this.sendOutputData(out);
    }

    @Override
    public void parserFinalize() {

    }
}
