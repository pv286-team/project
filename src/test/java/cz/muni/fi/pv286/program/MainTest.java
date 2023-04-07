package cz.muni.fi.pv286.program;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static cz.muni.fi.pv286.Main.main;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing whole main() function.
 * Not able to test wrong command line arguments as the program exits.
 */
class MainTest {
    private void setInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    private ByteArrayOutputStream getOutput() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        return out;
    }

    @Test
    public void bytesToBytes_printable() {
        String str = "This is some byte string sent to the main() function";
        setInput(str);
        ByteArrayOutputStream out = getOutput();
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
        setInput(str);
        ByteArrayOutputStream out = getOutput();
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
        setInput(str);
        ByteArrayOutputStream out = getOutput();
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
        setInput(str);
        ByteArrayOutputStream out = getOutput();
        String[] args = {"-f", "bytes", "-t", "hex"};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        assertEquals("68656c0a6c6f0a", out.toString());
    }

    @Test
    public void bytesToHex_delimiter() {
        String str = "hel\nlo\n";
        setInput(str);
        ByteArrayOutputStream out = getOutput();
        String[] args = {"-f", "bytes", "-t", "hex", "--delimiter=\"\n\""};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        assertEquals("68656c\n6c6f\n", out.toString());
    }

    @Test
    public void bitsToArray_empty() {
        String str = "";
        setInput(str);
        ByteArrayOutputStream out = getOutput();
        String[] args = {"-f", "bits", "-t", "array", "--to-options=a", "--to-options=a"};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        assertEquals("{}", out.toString());
    }
}