package cz.muni.fi.pv286.parser;

import cz.muni.fi.pv286.parser.input.PanbyteInput;
import cz.muni.fi.pv286.parser.input.PanbyteHexInput;
import cz.muni.fi.pv286.parser.input.PanbyteRawInput;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;
import cz.muni.fi.pv286.parser.output.PanbyteHexOutput;
import cz.muni.fi.pv286.parser.output.PanbyteRawOutput;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;

import static cz.muni.fi.pv286.Main.processIO;


class PanbyteHexInputOutputTest {
    @Test
    void INPUT_Single_full() {
        String inputString = "68656C6C6F";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("hello"));
    }

    @Test
    void INPUT_Single_full_high() {
        String inputString = "499602d2";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("499602d2"));
    }

    @Test
    void INPUT_Single_full_high_noZeroPadding() {
        String inputString = "499602d2";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, false);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("49962d2"));
    }


    @Test
    void INPUT_SingleWhiteSpaces_full() {
        String inputString = "68 65 6C 6C 6F";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("hello"));
    }

    @Test
    void INPUT_Two_full() {
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            input.parse(Util.byteList("68 65 6C"));
            input.parse(Util.byteList("6C 6F"));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("hello"));
    }

    @Test
    void INPUT_Two_half() {
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            input.parse(Util.byteList("68 65 6"));
            input.parse(Util.byteList(" C6C 6F"));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("hello"));
    }

    @Test
    void OUTPUT_Two_full() {
        final String inputString = "hello";

        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteRawInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("68656c6c6f"));
    }
}
