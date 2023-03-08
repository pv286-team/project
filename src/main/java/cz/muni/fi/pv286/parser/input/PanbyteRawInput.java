package cz.muni.fi.pv286.parser.input;

import cz.muni.fi.pv286.parser.output.PanbyteOutput;

import java.io.IOException;
import java.util.List;

/**
 * Input parser for raw bytes
 */
public class PanbyteRawInput extends PanbyteInput {
    public PanbyteRawInput(final PanbyteOutput output) {
        super(output);
    }

    @Override
    public void parse(final List<Byte> buffer) throws IOException {
        // raw bytes are already parsed -> send to output and flush
        this.parsedBytes.addAll(buffer);
        this.flush();
    }
}
