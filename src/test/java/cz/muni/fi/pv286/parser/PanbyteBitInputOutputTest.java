package cz.muni.fi.pv286.parser;

import cz.muni.fi.pv286.arguments.values.Option;
import cz.muni.fi.pv286.parser.input.PanbyteInput;
import cz.muni.fi.pv286.parser.input.PanbyteBitInput;
import cz.muni.fi.pv286.parser.input.PanbyteRawInput;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;
import cz.muni.fi.pv286.parser.output.PanbyteBitOutput;
import cz.muni.fi.pv286.parser.output.PanbyteHexOutput;
import cz.muni.fi.pv286.parser.output.PanbyteRawOutput;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;

import static cz.muni.fi.pv286.Main.processIO;


class PanbyteBitInputOutputTest {

    @Test
    void TestAssignment_01() {
        String inputString = "0100 1111 0100 1011";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("OK"));
    }

    @Test
    void TestAssignment_02()  {
        String inputString = "100111101001011";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("OK"));
    }

    @Test
    void TestAssignment_03()  {
        String inputString = "100111101001011";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("9e96"));
    }

    @Test
    void TestAssignment_04() {
        String inputString = "OK";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteBitOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteRawInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("0100111101001011"));
    }

    @Test
    void INPUT_OneByte_full() {
        String inputString = "01001111";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("O"));
    }

    @Test
    void INPUT_OneByteWhiteSpaces_full() {
        String inputString = "0 100\t111   1\n";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("O\n"));
    }

    @Test
    void INPUT_FourBits_rightPad() {
        String inputString = "0011";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("0"));
    }

    @Test
    void INPUT_FiveBits_rightPad_whitespaces() {
        String inputString = "00 11\t0 0\n";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("0\n"));
    }

    @Test
    void INPUT_OneByte_rightPad() {
        String inputString = "01001110";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("N"));
    }

    @Test
    void INPUT_OneByte_leftPad1() {
        String inputString = "0110000";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("0"));
    }

    @Test
    void INPUT_OneByte_leftPad2() {
        String inputString = "110001";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("1"));
    }

    @Test
    void INPUT_TwoBytes_leftPad1() {
        String inputString = "0110001 00110010";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("12"));
    }

    @Test
    void INPUT_TwoBytes_leftPad2() {
        String inputString = "110001 00110011";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("13"));
    }

    @Test
    void INPUT_TwoBytes_full() {
        String inputString = "01001111 01001011";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("OK"));
    }

    @Test
    void OUTPUT_OneByte_full() {
        String inputString = "01001111";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteBitOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals(inputString));
    }

    @Test
    void OUTPUT_TwoBytes_full() {
        String inputString = "01001111 01001011";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("OK"));
    }

    @Test
    void OUTPUT_TwoBytes_leftPad2() {
        String inputString = "110001 00110011";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteBitOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("0011000100110011"));
    }

    @Test
    void OUTPUT_TwoBytes_leftPad2_noZeroPadding() {
        String inputString = "110001";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteBitOutput(stdoutWriter, false);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("110001"));
    }

    @Test
    void OUTPUT_TwoBytes_leftPad3_noZeroPadding() {
        String inputString = "1";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteBitOutput(stdoutWriter, false);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("1"));
    }

    @Test
    void OUTPUT_TwoBytes_leftPad4() {
        String inputString = "10 11 001100\t11";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteBitOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("0000101100110011"));
    }
}