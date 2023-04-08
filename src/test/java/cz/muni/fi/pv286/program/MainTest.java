package cz.muni.fi.pv286.program;

import cz.muni.fi.pv286.TestUtils;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static cz.muni.fi.pv286.Main.main;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing whole main() function.
 * Not able to test wrong command line arguments as the program exits.
 */
class MainTest {
    @Test
    public void bytesToBytes_printable() {
        String str = "This is some byte string sent to the main() function";
        TestUtils.setInput(str);
        ByteArrayOutputStream out = TestUtils.getOutput();
        String[] args = {"-f", "bytes", "-t", "bytes"};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        assertEquals(str, out.toString());
    }

    @Test
    public void bytesToBytes_nonPrintable() {
        String str = "\02\64abc\t\r";
        TestUtils.setInput(str);
        ByteArrayOutputStream out = TestUtils.getOutput();
        String[] args = {"-f", "bytes", "-t", "bytes"};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        assertEquals(str, out.toString());
    }

    @Test
    public void bytesToInt_nonPrintable() {
        String str = "\64";
        TestUtils.setInput(str);
        ByteArrayOutputStream out = TestUtils.getOutput();
        String[] args = {"-f", "bytes", "-t", "int"};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        assertEquals("52", out.toString());
    }

    @Test
    public void bytesToHex_noDelimiter() {
        String str = "hel\nlo\n";
        TestUtils.setInput(str);
        ByteArrayOutputStream out = TestUtils.getOutput();
        String[] args = {"-f", "bytes", "-t", "hex"};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        assertEquals("68656c0a6c6f0a", out.toString());
    }

    @Test
    public void bitsToArray_empty() {
        String str = "";
        TestUtils.setInput(str);
        ByteArrayOutputStream out = TestUtils.getOutput();
        String[] args = {"-f", "bits", "-t", "array", "--to-options=a", "--to-options=a"};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        assertEquals("{}", out.toString());
    }

    /* Various tests for delimiter */

    @Test
    public void bytesToHex_delimiter() {
        String str = "hel\nlo\n";
        TestUtils.setInput(str);
        ByteArrayOutputStream out = TestUtils.getOutput();
        String[] args = {"-f", "bytes", "-t", "hex", "--delimiter=\"\n\""};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        assertEquals("68656c\n6c6f\n", out.toString());
    }
    @Test
    public void hexToBits_delimiter() {
        String str = "abcdeaf1";
        TestUtils.setInput(str);
        ByteArrayOutputStream out = TestUtils.getOutput();
        String[] args = {"-f", "hex", "-t", "bits", "--delimiter=a"};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        assertEquals("a1011110011011110a11110001", out.toString());
    }

    @Test
    public void bitsToHex_delimiter() {
        String str = "000100010";
        TestUtils.setInput(str);
        ByteArrayOutputStream out = TestUtils.getOutput();
        String[] args = {"-f", "bits", "-t", "hex", "--delimiter=1"};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        assertEquals("00100100", out.toString());
    }

    @Test
    public void intToHex_delimiter() {
        String str = "123435";
        TestUtils.setInput(str);
        ByteArrayOutputStream out = TestUtils.getOutput();
        String[] args = {"-f", "int", "-t", "hex", "--delimiter=\"3\"", "--from-options=little"};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        assertEquals("0c304305", out.toString());
    }
}