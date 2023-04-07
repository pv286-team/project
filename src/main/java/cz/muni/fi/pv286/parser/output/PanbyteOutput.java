package cz.muni.fi.pv286.parser.output;

import java.io.IOException;
import java.util.List;

public interface PanbyteOutput {
    /**
     * Formats the parsed bytes and - if applicable - send them to the output
     * @param buffer parsed bytes
     * @throws IOException when output writer fails
     */
    void stringify(final List<Byte> buffer) throws IOException;

    /**
     * To call when all data has been already passed to the stringify function
     */
    void parserFinalize() throws IOException;

    /**
     * Requests a fresh copy of a subclass of this object with empty buffers
     * @return fresh copy of a subclass of this object with empty buffers
     */
    PanbyteOutput getFresh();
}
