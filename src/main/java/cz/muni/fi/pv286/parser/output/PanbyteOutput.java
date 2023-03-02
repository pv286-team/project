package cz.muni.fi.pv286.parser.output;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public abstract class PanbyteOutput {
    // where to stream stringified bytes
    private final OutputStreamWriter outputWriter;

    /**
     * Initialize new output that formats parsed bytes
     * @param outputWriter where to stream formatted bytes
     */
    public PanbyteOutput(final OutputStreamWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    /**
     * Send the formatted bytes to the output
     * @param buffer data to send
     * @throws IOException when output writer fails
     */
    protected void sendOutputData(final List<Byte> buffer) throws IOException {
        for (Byte b : buffer) {
            // write all bytes
            this.outputWriter.write(b);
        }
        // flush the writer
        this.outputWriter.flush();
    }

    /**
     * Formats the parsed bytes and - if applicable - send them to the output
     * @param buffer parsed bytes
     * @throws IOException when output writer fails
     */
    public abstract void stringify(final List<Byte> buffer) throws IOException;

    /**
     * To call when all data has been already passed to the stringify function
     */
    public abstract void parserFinalize();
}
