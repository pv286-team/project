package cz.muni.fi.pv286.parser.input;

import cz.muni.fi.pv286.parser.output.PanbyteOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * An abstract class for receiving raw input
 */
public abstract class PanbyteInputBase implements PanbyteInput {
    // where to send parsed data
    protected final PanbyteOutput output;
    // internal buffer of already parsed data
    protected final ArrayList<Byte> parsedBytes = new ArrayList<>();

    /**
     * Initialized new input
     * @param output where to send parsed data
     */
    public PanbyteInputBase(final PanbyteOutput output) {
        if (output == null) {
            throw new NullPointerException("Output cannot be set to null");
        }
        this.output = output;
    }

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
}
