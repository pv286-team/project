package cz.muni.fi.pv286.parser.output;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Saves all parsed bytes into a local list only
 */
public class PanbyteMockOutput extends PanbyteOutput {
    private final List<Byte> bytes = new ArrayList<>();

    public PanbyteMockOutput() {
        super(new ByteArrayOutputStream());
    }

    /**
     * Gets all bytes currently stored in memory
     * @return all bytes currently stored in memory
     */
    public List<Byte> getReceivedBytes() {
        return Collections.unmodifiableList(this.bytes);
    }

    @Override
    public void stringify(final List<Byte> buffer) {
        this.bytes.addAll(buffer);
    }

    @Override
    public void parserFinalize() {
        // no-op, nothing is ever send anywhere
    }

    @Override
    public PanbyteOutput getFresh() {
        return new PanbyteMockOutput();
    }
}
