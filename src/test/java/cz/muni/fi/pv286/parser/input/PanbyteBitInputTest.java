package cz.muni.fi.pv286.parser.input;

import cz.muni.fi.pv286.arguments.values.Option;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;
import cz.muni.fi.pv286.parser.output.PanbyteRawOutput;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class PanbyteBitInputTest {

    // TODO: get that functionality directly from main()
    void readInput(InputStream stdinReader, PanbyteInput input) throws IOException {
        byte[] buffPrimitive = new byte[4096];
        int buffPrimitiveReadCount;
        // Until the end of file is reached, try to fill up buffer
        while ((buffPrimitiveReadCount = stdinReader.read(buffPrimitive)) != -1) {
            // Copy the filled part of the (possibly) partially filled array into a List
            final List<Byte> bytes = new ArrayList<>();
            for (int i = 0; i < buffPrimitiveReadCount; i++) {
                bytes.add(buffPrimitive[i]);
            }

            // Notify input parser about new data and send it unmodifiable list
            input.parse(Collections.unmodifiableList(bytes));
        }

        // Notify input parser that we are done
        input.parserFinalize();
    }

    @Test
    void TestAssignment_01() {
        String inputString = "0100 1111 0100 1011";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        try {
            readInput(stdinReader, input);
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
            readInput(stdinReader, input);
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("OK"));
    }

    @Test
    void OneByte_full() {
        String inputString = "01001111";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            readInput(stdinReader, input);
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("O"));
    }

    @Test
    void OneByteWhiteSpaces_full() {
        String inputString = "0 100\t111\n1\n";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            readInput(stdinReader, input);
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("O"));
    }

    @Test
    void FourBits_rightPad() {
        String inputString = "0011";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            readInput(stdinReader, input);
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("0"));
    }

    @Test
    void FiveBits_rightPad_whitespaces() {
        String inputString = "00 11\t0 0";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            readInput(stdinReader, input);
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("0"));
    }

    @Test
    void OneByte_rightPad() {
        String inputString = "01001110";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            readInput(stdinReader, input);
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("N"));
    }

    @Test
    void OneByte_leftPad1() throws IOException {
        String inputString = "0110000";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        readInput(stdinReader, input);

        String result = stdoutWriter.toString();
        assert(result.equals("0"));
    }

    @Test
    void OneByte_leftPad2() {
        String inputString = "110001";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        try {
            readInput(stdinReader, input);
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("1"));
    }

    @Test
    void TwoBytes_leftPad1() {
        String inputString = "0110001 00110010";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        try {
            readInput(stdinReader, input);
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("12"));
    }

    @Test
    void TwoBytes_leftPad2() {
        String inputString = "110001 00110011";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.LEFT_PAD);

        try {
            readInput(stdinReader, input);
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("13"));
    }

    @Test
    void TwoBytes_full() {
        String inputString = "01001111 01001011";
        final OutputStream stdoutWriter = new java.io.ByteArrayOutputStream();
        final InputStream stdinReader = new java.io.ByteArrayInputStream(inputString.getBytes());

        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        final PanbyteInput input = new PanbyteBitInput(output, Option.RIGHT_PAD);

        try {
            readInput(stdinReader, input);
        } catch (Exception e) {
            assert(false);
        }

        String result = stdoutWriter.toString();
        assert(result.equals("OK"));
    }
}