package cz.muni.fi.pv286.parser.output;

import cz.muni.fi.pv286.arguments.values.Option;
import cz.muni.fi.pv286.parser.ArrayBracket;
import cz.muni.fi.pv286.parser.Util;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PanbyteArrayOutput extends PanbyteOutputBase {
    /** Parser for received byte */
    final PanbyteOutput innerOutput;
    /** Type of output [decimal, hex, characters, bits] */
    final Option outputOption;
    /** Type of brackets used for printed array */
    Option bracketsOption;
    /** List of all obtained brackets which should be printed */
    List<ArrayBracket> brackets;
    /** Index of current parsed byte */
    int index = 0;

    public PanbyteArrayOutput(OutputStream outputStream, Option outputOption, Option bracketsOption) {
        super(outputStream);
        this.outputOption = outputOption;
        this.bracketsOption = bracketsOption;
        // According to set options, create inner parser
        switch (outputOption) {
            case DEC:
                innerOutput = new PanbyteIntOutput(outputStream, Option.BIG_ENDIAN);
                break;
            case CHAR:
                innerOutput = new PanbyteStringOutput(outputStream);
                break;
            case BIT:
                innerOutput = new PanbyteBitOutput(outputStream, false);
                break;
            case HEX:
            default:
                innerOutput = new PanbyteHexOutput(outputStream, false);
                break;
        }
    }

    @Override
    public void stringify(List<Byte> buffer) throws IOException {
        final List<Byte> out = new ArrayList<>();

        // When the input is not array, print default outer opening bracket
        if (index == 0 && brackets == null && !buffer.isEmpty()) {
            out.add(this.getBracket(ArrayBracket.BracketType.OPENING));
            this.sendOutputData(out);
            out.clear();
        }

        // Iterate over received buffer (mostly it is just one byte)
        for (byte currentByte : buffer) {
            // Closing brackets need to be printed before ',' and opening brackets
            this.putBrackets(ArrayBracket.BracketType.CLOSING);

            if (index > 0) {
                // Set array delimiter
                this.sendOutputData(Util.bytesToList(", ".getBytes(StandardCharsets.US_ASCII)));
            }

            // Put opening brackets after possible delimiter
            this.putBrackets(ArrayBracket.BracketType.OPENING);

            // Print prefix if needed
            switch (outputOption) {
                case BIT:
                    this.sendOutputData(Util.bytesToList("0b".getBytes(StandardCharsets.US_ASCII)));
                    break;
                case HEX:
                    this.sendOutputData(Util.bytesToList("0x".getBytes(StandardCharsets.US_ASCII)));
                    break;
                case DEC:
                case CHAR:
                default:
                    break;
            }
            out.add(currentByte);
            // Parse byte value by inner parser
            this.innerOutput.getFresh();
            this.innerOutput.stringify(out);
            this.innerOutput.parserFinalize();
            out.clear();
            index++;
        }
    }

    @Override
    public void parserFinalize() throws IOException {
        // By this time, all bytes should be printed out
        // along with the inside brackets

        if (brackets == null && index == 0) {
            // input is not array & nothing was printed out, put starting bracket here
            this.sendOutputData(List.of(this.getBracket(ArrayBracket.BracketType.OPENING)));
        }
        // input is not array & something was printed out already, print only closing bracket
        if (brackets == null && index >= 0){
            this.sendOutputData(List.of(this.getBracket(ArrayBracket.BracketType.CLOSING)));
        } else if (brackets != null && index > 0) {
            // array is input & some bytes were printed, standalone brackets are left
            this.putBrackets(ArrayBracket.BracketType.OPENING);
            this.putBrackets(ArrayBracket.BracketType.CLOSING);
        } else if (brackets != null && index == 0) {
            // array is input & no bytes were printed, so the input is an only bunch of brackets
            boolean closing = false;
            for (ArrayBracket b : this.brackets) {
                if (closing  && b.type == ArrayBracket.BracketType.OPENING) {
                    this.sendOutputData(Util.bytesToList(", ".getBytes(StandardCharsets.US_ASCII)));
                    closing = false;
                }
                if (b.type == ArrayBracket.BracketType.CLOSING)
                    closing = true;
                this.sendOutputData(List.of(this.getBracket(b.type)));
            }
        }
        // Everything was printed out, reset to default
        this.index = 0;
        this.brackets = null;
    }

    @Override
    public PanbyteOutput getFresh() {
        return new PanbyteArrayOutput(this.outputStream, outputOption, bracketsOption);
    }



    /** Print all brackets of given type on current index */
    private void putBrackets(ArrayBracket.BracketType type) throws IOException {
        if (brackets == null)
            return;
        for (ArrayBracket b : this.brackets) {
            if (b.index == index && b.type == type) {
                this.sendOutputData(List.of(this.getBracket(type)));
            }
        }
    }

    /** Get bracket as byte */
    private byte getBracket(ArrayBracket.BracketType type) {
        switch (this.bracketsOption) {
            case REGULAR_BRACKETS:
                return type == ArrayBracket.BracketType.OPENING ? (byte) '(' : (byte) ')';
            case SQUARE_BRACKETS:
                return type == ArrayBracket.BracketType.OPENING ? (byte) '[' : (byte) ']';
            case CURLY_BRACKETS:
            default:
                return type == ArrayBracket.BracketType.OPENING ? (byte) '{' : (byte) '}';
        }
    }

    /** Set current array of brackets */
    public void setBrackets(List<ArrayBracket> brackets) {
        this.brackets = List.copyOf(brackets);
    }
}
