package cz.muni.fi.pv286.parser.input;

import cz.muni.fi.pv286.parser.Util;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Input parser for encoded strings
 */
public class PanbyteStringInput extends PanbyteInput {
    private final List<Byte> unparsedBytes = new ArrayList<>();

    public PanbyteStringInput(final PanbyteOutput output) {
        super(output);
    }

    @Override
    public void parse(final List<Byte> buffer) {
        // We need all data to be read first in memory so that escape characters are interpreted correctly
        this.unparsedBytes.addAll(buffer);
    }

    @Override
    public void parserFinalize() throws IOException {
        // Convert all bytes into a Java string and then perform the un-escaping operation
        final String escapedString = Util.stringFromBytes(this.unparsedBytes);
        String unescapedString;
        try {
            unescapedString = Util.unescapeJavaString(escapedString);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Not all bytes could be formatted");
        }
        this.unparsedBytes.clear();
        this.parsedBytes.addAll(Util.byteList(unescapedString));
        this.flush();
        super.parserFinalize();
    }

    @Override
    public PanbyteInput getFresh() {
        return new PanbyteStringInput(this.output.getFresh());
    }
}
