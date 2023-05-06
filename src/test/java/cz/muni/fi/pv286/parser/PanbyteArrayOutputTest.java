package cz.muni.fi.pv286.parser;

import cz.muni.fi.pv286.arguments.values.Option;
import cz.muni.fi.pv286.parser.input.*;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;
import cz.muni.fi.pv286.parser.output.PanbyteArrayOutput;
import cz.muni.fi.pv286.parser.output.PanbyteHexOutput;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static cz.muni.fi.pv286.Main.processIO;

public class PanbyteArrayOutputTest {

    @Test
    void TestAssignment_01() {
        String inputString = "01020304";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{0x1, 0x2, 0x3, 0x4}"));
    }

    @Test
    void TestAssignment_02() {
        String inputString = "{0x01, 2, 0b11, '\\x04'}";
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
    void TestAssignment_03() {
        String inputString = "{0x01,2,0b11 ,'\\x04' }";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{0x1, 0x2, 0x3, 0x4}"));
    }

    @Test
    void TestAssignment_04() {
        String inputString = "[0x01, 2, 0b11, '\\x04']";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{0x1, 0x2, 0x3, 0x4}"));
    }

    @Test
    void TestAssignment_05() {
        String inputString = "(0x01, 2, 0b11, '\\x04')";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.DEC, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{1, 2, 3, 4}"));
    }

    @Test
    void TestAssignment_06() {
        String inputString = "{0x01, 2, 0b11, '\\x04'}";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.CHAR, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{'\\x01', '\\x02', '\\x03', '\\x04'}"));
    }

    @Test
    void TestAssignment_07() {
        String inputString = "[0x01, 2, 0b11, '\\x04']";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.BIT, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{0b1, 0b10, 0b11, 0b100}"));
    }

    @Test
    void TestAssignment_08() {
        String inputString = "(0x01, 2, 0b11, '\\x04')";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.REGULAR_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("(0x1, 0x2, 0x3, 0x4)"));
    }

    @Test
    void TestAssignment_09() {
        String inputString = "{0x01, 2, 0b11, '\\x04'}";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.DEC, Option.SQUARE_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("[1, 2, 3, 4]"));
    }

    @Test
    void TestAssignment_10() {
        String inputString = "[[1, 2], [3, 4], [5, 6]]";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{{0x1, 0x2}, {0x3, 0x4}, {0x5, 0x6}}"));
    }

    @Test
    void TestAssignment_11() {
        String inputString = "[[1, 2], [3, 4], [5, 6]]";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.DEC, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{{1, 2}, {3, 4}, {5, 6}}"));
    }

    @Test
    void TestAssignment_12() {
        String inputString = "{{0x01, (2), [3, 0b100, 0x05], '\\x06'}}";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.DEC, Option.SQUARE_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("[[1, [2], [3, 4, 5], 6]]"));
    }

    @Test
    void TestAssignment_13() {
        String inputString = "()";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{}"));
    }

    @Test
    void TestAssignment_14() {
        String inputString = "([],{})";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.SQUARE_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("[[], []]"));
    }

    @Test
    void nestedArray_curlyHex_double() {
        String inputString = "[[1, 2]]";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{{0x1, 0x2}}"));
    }

    @Test
    void nestedArray_curlyHex_triple() {
        String inputString = "[[[1, 2]]]";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{{{0x1, 0x2}}}"));
    }

    @Test
    void nestedArray_curlyHex_twoDouble() {
        String inputString = "[[1, 2], [3, 4]]";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{{0x1, 0x2}, {0x3, 0x4}}"));
    }

    @Test
    void nestedArray_curlyHex_oneTriple() {
        String inputString = "[[1, (2)], [3, 4]]";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{{0x1, {0x2}}, {0x3, 0x4}}"));
    }

    @Test
    void nestedArrayBrackets_square_complex() {
        String inputString = "[[], [[], [[([()])],[]]]]";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.SQUARE_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("[[], [[], [[[[[]]]], []]]]"));
    }

    @Test
    void emptyInputArray() {
        String inputString = "";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.SQUARE_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("[]"));
    }

    @Test
    void emptyInputBytes() {
        String inputString = "";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.SQUARE_BRACKETS);
        final PanbyteInput input = new PanbyteRawInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("[]"));
    }

    @Test
    void inputBits_leftPad() {
        String inputString = "0010011001100";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{0x4, 0xcc}"));
    }

    @Test
    void inputBits_rightPad() {
        String inputString = "001001100110";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{0x26, 0x60}"));
    }

    @Test
    void inputBits_rightPad_outputChars() {
        String inputString = "001001100110";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.CHAR, Option.SQUARE_BRACKETS);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("['&', '`']"));
    }

    @Test
    void inputInt_outputHex() {
        String inputString = "1234";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.SQUARE_BRACKETS);
        final PanbyteInput input = new PanbyteIntInput(output, Option.RIGHT_PAD);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("[0x4, 0xd2]"));
    }

    @Test
    void inputArrayInt_outputInt() {
        String inputString = "(123, 12, (1560))";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.SQUARE_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("[0x7b, 0xc, [0x6, 0x18]]"));
    }

    @Test
    void inputArrayInt_outputBits() {
        String inputString = "(123, 12, (15600))";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.BIT, Option.SQUARE_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("[0b1111011, 0b1100, [0b111100, 0b11110000]]"));
    }

    @Test
    void inputInt_outputArrayBits() {
        String inputString = "123456789";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.BIT, Option.REGULAR_BRACKETS);
        final PanbyteInput input = new PanbyteIntInput(output, Option.BIG_ENDIAN);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("(0b111, 0b1011011, 0b11001101, 0b10101)"));
    }

    @Test
    void inputArray_outputArrayHex() {
        String inputString = "(0x01)";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{0x1}"));
    }

    @Test
    void inputArray_outputArrayHex_delimiter() {
        String inputString = "(0x01\n)";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    void inputArray_outputArrayHex_delimiterComplex() {
        String inputString = "(0x01, (0x02, 0x03), 0x05\n)";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    void inputArray_outputArrayHex_shortHex() {
        String inputString = "(0x1)";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    void inputArray_outputArrayHex_space() {
        String inputString = "(0x 1)";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    void inputArray_outputArrayHex_differentBracket() {
        String inputString = "(0x01}";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    void inputSingleValueArray_outputSingleValueArray() {
        String inputString = "{128}";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{0x80}"));
    }

    @Test
    void inputTwoElemNestedArray_outputTwoElemNestedArray() {
        String inputString = "{{}, 1}";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{{}, 0x1}"));
    }


    @Test
    void inputThreeElemNestedArray_outputThreeElemNestedArray() {
        String inputString = "{128, {}, 128}";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes(StandardCharsets.US_ASCII));

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes(StandardCharsets.US_ASCII));
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("{0x80, {}, 0x80}"));
    }
}
