package cz.muni.fi.pv286.parser.input;

import cz.muni.fi.pv286.parser.output.PanbyteOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An abstract class for receiving raw input
 */
public abstract class PanbyteInput {
    // where to send parsed data
    protected final PanbyteOutput output;
    // internal buffer of already parsed data
    protected ArrayList<Byte> parsedBytes = new ArrayList<>();

    /**
     * Initialized new input
     * @param output where to send parsed data
     */
    public PanbyteInput(final PanbyteOutput output) {
        if (output == null) {
            throw new NullPointerException("Output cannot be set to null");
        }
        this.output = output;
    }

    /**
     * Notifies both this input and attached output that the end-of-file was reached
     */
    public void parserFinalize() throws IOException {
        this.output.parserFinalize();
    }

    /**
     * Flush the current value of parsedBytes to the selected output and clear the buffer
     * @throws IOException Output write operation failed
     */
    protected void flush() throws IOException {
        this.output.stringify(Collections.unmodifiableList(this.parsedBytes));
        this.parsedBytes.clear();
    }

    /**
     * Parses raw input data and saves them into local parsedBytes buffer
     * @param buffer raw read data
     * @throws IOException Output write operation failed if parse method also flushes
     */
    public abstract void parse(final List<Byte> buffer) throws IOException;

    /**
     * Requests a fresh copy of a subclass of this object with empty buffers
     * Needs to also empty buffers of assigned output
     * @return fresh copy of a subclass of this object with empty buffers
     */
    public abstract PanbyteInput getFresh();
}
