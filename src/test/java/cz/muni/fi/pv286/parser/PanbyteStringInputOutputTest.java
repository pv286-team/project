package cz.muni.fi.pv286.parser;

import cz.muni.fi.pv286.arguments.values.Option;
import cz.muni.fi.pv286.parser.input.PanbyteBitInput;
import cz.muni.fi.pv286.parser.input.PanbyteInput;
import cz.muni.fi.pv286.parser.input.PanbyteStringInput;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;
import cz.muni.fi.pv286.parser.output.PanbyteRawOutput;
import cz.muni.fi.pv286.parser.output.PanbyteStringOutput;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;

import static cz.muni.fi.pv286.Main.processIO;

public class PanbyteStringInputOutputTest {
    @Test
    void printableCharacters_alphabet()  {
        String inputString = "a";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteStringOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteStringInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("'a'"));
    }

    @Test
    void printableCharacters_number()  {
        String inputString = "8";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteStringOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteStringInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("'8'"));
    }

    @Test
    void printableCharacters_escapedToUnescaped()  {
        String inputString = "\\x51";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteStringOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteStringInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("'Q'"));
    }

    @Test
    void printableCharacters_escapedToEscaped()  {
        String inputString = "\\x0f";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteStringOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteStringInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("'\\x0f'"));
    }

    @Test
    void printableCharacters_special()  {
        String inputString = "\\n";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteStringOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteStringInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("'\\n'"));
    }
}
