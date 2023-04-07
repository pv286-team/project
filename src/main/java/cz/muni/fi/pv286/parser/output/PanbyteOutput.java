package cz.muni.fi.pv286.parser.output;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public abstract class PanbyteOutput {
    // where to stream stringified bytes
    protected final OutputStream outputStream;

    /**
     * Initialize new output that formats parsed bytes
     * @param outputStream where to stream formatted bytes
     */
    public PanbyteOutput(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * Send the formatted bytes to the output
     * @param buffer data to send
     * @throws IOException when output writer fails
     */
    protected void sendOutputData(final List<Byte> buffer) throws IOException {
        // convert Byte list into primitive byte array
        final int bufferSize = buffer.size();
        final byte[] primitiveBuffer = new byte[bufferSize];
        for (int i = 0; i < bufferSize; i++) {
            primitiveBuffer[i] = buffer.get(i);
        }

        // send all data to the output
        this.outputStream.write(primitiveBuffer);
        // flush the writer
        this.outputStream.flush();
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
    public abstract void parserFinalize() throws IOException;

    /**
     * Requests a fresh copy of a subclass of this object with empty buffers
     * @return fresh copy of a subclass of this object with empty buffers
     */
    public abstract PanbyteOutput getFresh();
}
