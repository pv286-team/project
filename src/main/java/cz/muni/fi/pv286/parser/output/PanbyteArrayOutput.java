package cz.muni.fi.pv286.parser.output;

import cz.muni.fi.pv286.arguments.InvalidArgumentsException;
import cz.muni.fi.pv286.arguments.values.Option;
import cz.muni.fi.pv286.parser.ArrayBracket;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class PanbyteArrayOutput extends PanbyteOutput {
    PanbyteOutput innerOutput;
    Option outputOption;
    Option bracketsOption;
    List<ArrayBracket> brackets;
    int index = 0;

    public PanbyteArrayOutput(OutputStream outputStream, Option outputOption, Option bracketsOption) {
        super(outputStream);
        this.outputOption = outputOption;
        this.bracketsOption = bracketsOption;
        this.index = 0;
        this.brackets = null;
        switch (outputOption) {
            case DEC:
                innerOutput = new PanbyteIntOutput(outputStream, Option.BIG_ENDIAN);
                break;
            case CHAR:
                innerOutput = new PanbyteStringOutput(outputStream);
                break;
            case BIT:
                innerOutput = new PanbyteBitOutput(outputStream);
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

        // by default print the most out bracket first
        if (index == 0 && brackets == null) {
            out.add(this.getBracket(true));
            this.sendOutputData(out);
        }
        out.clear();

        for (byte currentByte : buffer) {
            if (index > 0) {
                // set whitespace
                out.add((byte) ',');
                out.add((byte) ' ');
                this.sendOutputData(out);
                out.clear();
            }

            // put opening brackets
            if (brackets != null) {
                this.putBrackets(true);
            }

            // print prefix if needed
            switch (outputOption) {
                case DEC:
                    break;
                case CHAR:
                    break;
                case BIT:
                    out.add((byte) '0');
                    out.add((byte) 'b');
                    break;
                case HEX:
                    out.add((byte) '0');
                    out.add((byte) 'x');
                    break;
                default:
                    break;
            }
            this.sendOutputData(out);
            out.clear();
            out.add(currentByte);
            this.innerOutput.stringify(out);
            this.innerOutput.parserFinalize();
            out.clear();

            // put closing brackets
            if (brackets != null) {
                this.putBrackets(false);
            }
            index++;
        }
    }

    @Override
    public void parserFinalize() throws IOException {
        final List<Byte> out = new ArrayList<>();
        // add only most outer closing bracket
        if (brackets == null) {
            out.add(this.getBracket(false));
            this.sendOutputData(out);
        } else  {
            this.putBrackets(false);
        }
    }

    @Override
    public PanbyteOutput getFresh() {
        return new PanbyteArrayOutput(this.outputStream, outputOption, bracketsOption);
    }

    private void putBrackets(boolean putOpening) throws IOException {
        final List<Byte> out = new ArrayList<>();
        for (ArrayBracket b : brackets) {
            int bracketIndex = b.index;
            if (bracketIndex == index) {
                boolean opening = this.brackets.get(bracketIndex).type == ArrayBracket.BracketType.OPENING;
                if (putOpening == opening) {
                    out.add(this.getBracket(putOpening));
                    this.sendOutputData(out);
                    out.clear();
                }
            }
        }
    }

    private Byte getBracket(boolean opening) {
        switch (outputOption) {
            case REGULAR_BRACKETS:
                return opening ? (byte) '(' : (byte) ')';
            case SQUARE_BRACKETS:
                return opening ? (byte) '[' : (byte) ']';
            case CURLY_BRACKETS:
            default:
                return opening ? (byte) '{' : (byte) '}';
        }
    }

    public void setBrackets(List<ArrayBracket> brackets) {
        this.brackets = brackets;
    }
}
