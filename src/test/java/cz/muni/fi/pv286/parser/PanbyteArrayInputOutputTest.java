package cz.muni.fi.pv286.parser;

import cz.muni.fi.pv286.arguments.values.Option;
import cz.muni.fi.pv286.parser.input.PanbyteArrayInput;
import cz.muni.fi.pv286.parser.input.PanbyteBitInput;
import cz.muni.fi.pv286.parser.input.PanbyteHexInput;
import cz.muni.fi.pv286.parser.input.PanbyteInput;
import cz.muni.fi.pv286.parser.output.PanbyteArrayOutput;
import cz.muni.fi.pv286.parser.output.PanbyteHexOutput;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;
import cz.muni.fi.pv286.parser.output.PanbyteRawOutput;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.OutputStream;

import static cz.muni.fi.pv286.Main.processIO;

public class PanbyteArrayInputOutputTest {

    @Test
    void TestAssignment_01() {
        String inputString = "01020304";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
        final PanbyteInput input = new PanbyteHexInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
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
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteHexOutput(stdoutWriter, true);
        final PanbyteInput input = new PanbyteArrayInput(output);

        try {
            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("01020304"));
    }

//    @Test
//    void TestAssignment_03() {
//        // TODO
//        String inputString = "{0x01,2,0b11 ,'\\x04' }";
//        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
//        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());
//
//        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
//        final PanbyteInput input = new PanbyteArrayInput(output);
//
//        try {
//            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
//        } catch (Exception e) {
//            //assert(false);
//        }
//
//        String result = stdoutWriter.toString();
//        assert(result.equals("{0x1, 0x2, 0x3, 0x4}"));
//    }

//    @Test
//    void TestAssignment_08() {
//        // TODO
//        String inputString = "[[1, 2], [3, 4], [5, 6]]";
//        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
//        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());
//
//        final PanbyteOutput output = new PanbyteArrayOutput(stdoutWriter, Option.HEX, Option.CURLY_BRACKETS);
//        final PanbyteInput input = new PanbyteArrayInput(output);
//
//        try {
//            processIO(stdinReader, stdoutWriter, input, "\n".getBytes());
//        } catch (Exception e) {
//            //assert(false);
//        }
//
//        String result = stdoutWriter.toString();
////        assert(result.equals("{{0x1, 0x2}, {0x3, 0x4}, {0x5, 0x6}}"));
//    }

    @Test
    void nestedArray_curlyHex_simple() {
        String inputString = "[[1, 2]]";
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
        assert(result.equals("{{0x1, 0x2}}"));
    }

    @Test
    void nestedArray_curlyHex_two() {
        String inputString = "[[1, 2], [3, 4]]";
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
        assert(result.equals("{{0x1, 0x2}, {0x3, 0x4}}"));
    }
}
