package cz.muni.fi.pv286.parser.input;

import cz.muni.fi.pv286.arguments.InvalidArgumentsException;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;

import java.io.IOException;
import java.util.List;

public interface PanbyteInput {
    /**
     * Notifies both this input and attached output that the end-of-file was reached
     */
    void parserFinalize() throws IOException;
    /**
     * Parses raw input data and saves them into local parsedBytes buffer
     * @param buffer raw read data
     * @throws IOException Output write operation failed if parse method also flushes
     */
    void parse(final List<Byte> buffer) throws IOException, InvalidArgumentsException;
    /**
     * Requests a fresh copy of a subclass of this object with empty buffers
     * Needs to also empty buffers of assigned output
     * @return fresh copy of a subclass of this object with empty buffers
     */
    PanbyteInput getFresh();
}
