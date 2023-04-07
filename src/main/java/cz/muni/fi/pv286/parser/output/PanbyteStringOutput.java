package cz.muni.fi.pv286.parser.output;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PanbyteStringOutput extends PanbyteOutputBase {
    private final List<Byte> unparsedBytes = new ArrayList<>();
    private final PanbyteHexOutput innerHexOutput;
    public PanbyteStringOutput(OutputStream outputStream) {
        super(outputStream);
        innerHexOutput = new PanbyteHexOutput(outputStream, true);
    }

    @Override
    public void stringify(List<Byte> buffer) throws IOException {
        this.unparsedBytes.addAll(buffer);
    }

    @Override
    public void parserFinalize() throws IOException {
        this.printPrintable();
        this.unparsedBytes.clear();
    }

    @Override
    public PanbyteOutput getFresh() {
        return new PanbyteStringOutput(this.outputStream);
    }

    public void printPrintable() throws IOException {
        List<Byte> out = new ArrayList<>();
        for (byte currentByte : unparsedBytes) {
            // first, add quotation marks
            out.add((byte) '\'');
            this.sendOutputData(out);
            out.clear();

            if (currentByte >= 32 && currentByte <= 126) {
                out.add(currentByte);
                this.sendOutputData(out);
                out.clear();
            } else if (currentByte >= 8 && currentByte <= 13) {
                switch (currentByte) {
                    case '\b':
                        out.add((byte) '\\');
                        out.add((byte) 'b');
                        break;
                    case '\t':
                        out.add((byte) '\\');
                        out.add((byte) 't');
                        break;
                    case '\n':
                        out.add((byte) '\\');
                        out.add((byte) 'n');
                        break;
                    case 11:
                        out.add((byte) '\\');
                        out.add((byte) 'v');
                        break;
                    case 12:
                        out.add((byte) '\\');
                        out.add((byte) 'f');
                        break;
                    case '\r':
                        out.add((byte) '\\');
                        out.add((byte) 'r');
                        break;
                }
            } else {
                out.add((byte) '\\');
                out.add((byte) 'x');
                this.sendOutputData(out);
                out.clear();
                out.add(currentByte);
                innerHexOutput.stringify(out);
                innerHexOutput.parserFinalize();
                out.clear();
            }
            // add the marks also at the back
            out.add((byte) '\'');
            this.sendOutputData(out);
        }
    }
}
