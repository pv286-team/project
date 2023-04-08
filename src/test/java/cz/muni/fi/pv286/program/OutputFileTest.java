package cz.muni.fi.pv286.program;

import cz.muni.fi.pv286.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.Instant;

import static cz.muni.fi.pv286.Main.main;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class OutputFileTest {
    @TempDir
    private static Path testDir;
    private static Path outputFilePath;

    @BeforeEach
    public void setUp() {
        outputFilePath = testDir.resolve(Instant.now().toString().replace(':', '_'));
    }

    @Test
    void emptyOutputFile_short() {
        String input = "";
        TestUtils.setInput(input);
        String[] args = {"-f", "bytes", "-t", "bytes", "-o", outputFilePath.toString()};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        String outputContent = TestUtils.readFile(outputFilePath.toString());
        assertEquals("", outputContent);
    }

    @Test
    void byteOutputFile() {
        String input = "ahoj\njak to jde?\n\n";
        TestUtils.setInput(input);
        String[] args = {"-f", "bytes", "-t", "bytes", "-o", outputFilePath.toString()};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        String outputContent = TestUtils.readFile(outputFilePath.toString());
        assertEquals(input, outputContent);
    }

    @Test
    void intOutputFile() {
        String input = "1234567890\n";
        TestUtils.setInput(input);
        String[] args = {"-f", "int", "-t", "hex", "-o", outputFilePath.toString(), "--from-options=little"};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        String outputContent = TestUtils.readFile(outputFilePath.toString());
        assertEquals("d2029649\n", outputContent);
    }

    @Test
    void bitsOutputFile() {
        String input = "lorem ipsumdolorsit amet\n";
        TestUtils.setInput(input);
        String[] args = {"-f", "bytes", "-t", "bits", "-o", outputFilePath.toString()};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        String outputContent = TestUtils.readFile(outputFilePath.toString());
        assertEquals("01101100011011110111001001100101011011010010000001101001011100000111001101110101011011010110010001101111011011000110111101110010011100110110100101110100001000000110000101101101011001010111010000001010\n", outputContent);
    }

    @Test
    void arrayOutputFile() {
        String input = "1234567890";
        TestUtils.setInput(input);
        String[] args = {"-f", "int", "-t", "hex", "-o", outputFilePath.toString(), "--from-options=little"};
        try {
            main(args);
        } catch (Exception e) {
            assertFalse(false);
        }

        String outputContent = TestUtils.readFile(outputFilePath.toString());
        assertEquals("d2029649\n", outputContent);
    }
}
