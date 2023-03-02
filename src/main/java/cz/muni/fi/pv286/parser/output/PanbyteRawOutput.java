package cz.muni.fi.pv286.parser.output;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Outputs all received bytes in their raw format
 */
public class PanbyteRawOutput extends PanbyteOutput {

    public PanbyteRawOutput(OutputStreamWriter outputWriter) {
        super(outputWriter);
    }

    @Override
    public void stringify(final List<Byte> buffer) throws IOException {
        // the bytes are raw == already formatted, just send them
        this.sendOutputData(buffer);
    }

    @Override
    public void parserFinalize() {
        // no-op, everything already has been sent from stringify
    }
}
