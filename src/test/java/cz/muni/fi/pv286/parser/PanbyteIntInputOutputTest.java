package cz.muni.fi.pv286.parser;

import cz.muni.fi.pv286.arguments.values.Option;

import cz.muni.fi.pv286.parser.input.PanbyteHexInput;
import cz.muni.fi.pv286.parser.input.PanbyteInput;
import cz.muni.fi.pv286.parser.input.PanbyteIntInput;
import cz.muni.fi.pv286.parser.output.PanbyteHexOutput;
import cz.muni.fi.pv286.parser.output.PanbyteIntOutput;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;

import static cz.muni.fi.pv286.Main.processIO;


class PanbyteIntInputOutputTest {

    @Test
    void TestAssignment_01() {
        String inputString = "1234567890";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteIntInput(output, Option.BIG_ENDIAN);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("499602d2"));
    }

    @Test
    void TestAssignment_02()  {
        String inputString = "1234567890";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteIntInput(output, Option.BIG_ENDIAN);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("499602d2"));
    }

    @Test
    void TestAssignment_03()  {
        String inputString = "1234567890";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteIntInput(output, Option.LITTLE_ENDIAN);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("d2029649"));
    }

    @Test
    void TestAssignment_04() {
        String inputString = "499602d2";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteIntOutput(stdoutWriter, Option.BIG_ENDIAN);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("1234567890"));
    }

    @Test
    void TestAssignment_05() {
        String inputString = "499602d2";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteIntOutput(stdoutWriter, Option.BIG_ENDIAN);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("1234567890"));
    }

    @Test
    void TestAssignment_06() {
        String inputString = "d2029649";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteIntOutput(stdoutWriter, Option.LITTLE_ENDIAN);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("1234567890"));
    }

    @Test
    void Big_to_Big() {
        String inputString = "1234567890";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteIntOutput(stdoutWriter, Option.BIG_ENDIAN);
        final PanbyteInput input = new PanbyteIntInput(output, Option.BIG_ENDIAN);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("1234567890"));
    }

    @Test
    void Little_to_Little() {
        String inputString = "1234567890";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteIntOutput(stdoutWriter, Option.LITTLE_ENDIAN);
        final PanbyteInput input = new PanbyteIntInput(output, Option.LITTLE_ENDIAN);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("1234567890"));
    }

    @Test
    void INPUT_Zero_Big_to_Little() {
        String inputString = "0";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteIntOutput(stdoutWriter, Option.LITTLE_ENDIAN);
        final PanbyteInput input = new PanbyteIntInput(output, Option.BIG_ENDIAN);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("0"));
    }

    @Test
    void INPUT_Zero_Little_to_Big() {
        String inputString = "0";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteIntOutput(stdoutWriter, Option.BIG_ENDIAN);
        final PanbyteInput input = new PanbyteIntInput(output, Option.LITTLE_ENDIAN);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("0"));
    }

    @Test
    void INPUT_One_Big_to_Little() {
        String inputString = "1";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteIntOutput(stdoutWriter, Option.LITTLE_ENDIAN);
        final PanbyteInput input = new PanbyteIntInput(output, Option.BIG_ENDIAN);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("1"));
    }

    @Test
    void INPUT_One_Little_to_Big() {
        String inputString = "1";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteIntOutput(stdoutWriter, Option.BIG_ENDIAN);
        final PanbyteInput input = new PanbyteIntInput(output, Option.LITTLE_ENDIAN);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("1"));
    }

    @Test
    void INPUT_Huge_Big_to_Big() {
        String inputString = "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteIntOutput(stdoutWriter, Option.BIG_ENDIAN);
        final PanbyteInput input = new PanbyteIntInput(output, Option.BIG_ENDIAN);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890"));
    }

    @Test
    void INPUT_Huge_Little_to_Little() {
        String inputString = "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteIntOutput(stdoutWriter, Option.LITTLE_ENDIAN);
        final PanbyteInput input = new PanbyteIntInput(output, Option.LITTLE_ENDIAN);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901234567890"));
    }
}