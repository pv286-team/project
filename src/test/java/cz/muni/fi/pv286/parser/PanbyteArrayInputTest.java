package cz.muni.fi.pv286.parser;

import cz.muni.fi.pv286.parser.input.PanbyteInput;
import cz.muni.fi.pv286.parser.input.PanbyteArrayInput;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;
import cz.muni.fi.pv286.parser.output.PanbyteHexOutput;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static cz.muni.fi.pv286.Main.processIO;


class PanbyteArrayInputTest {
    @Test
    void INPUT_Single_full() {
        String inputString = "((1, 2), (3, 4))";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("01020304"));
    }

    @Test
    void INPUT_Empty_array() {
        String inputString = "{}";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals(""));
    }

    @Test
    void INPUT_Empty_array_double() {
        String inputString = "((),())";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals(""));
    }

    @Test
    void INPUT_Empty_content() {
        String inputString = "(                         )";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals(""));
    }

    @Test
    void INPUT_Empty() {
        String inputString = "";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals(""));
    }

    @Test
    void INPUT_Invalid_empty() {
        String inputString = "{,}";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter,true);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            return;
        }

        assert(false);
    }

    @Test
    void INPUT_Invalid_ending() {
        String inputString = "{{1,}}";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            return;
        }

        assert(false);
    }

    @Test
    void INPUT_Invalid_closing() {
        String inputString = "{)";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            return;
        }

        assert(false);
    }
}
