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
    boolean openingBracket;

    public PanbyteArrayOutput(OutputStream outputStream, Option outputOption, Option bracketsOption) {
        super(outputStream);
        this.outputOption = outputOption;
        this.bracketsOption = bracketsOption;
        this.openingBracket = true;
        switch (outputOption) {
            case DEC:
                innerOutput = new PanbyteIntOutput(outputStream, Option.BIG_ENDIAN);
                break;
            case CHAR:
                // TODO: Character output
                break;
            case BIT:
                innerOutput = new PanbyteBitOutput(outputStream);
                break;
            case HEX:
            default:
                innerOutput = new PanbyteHexOutput(outputStream);
                break;
        }
    }

    @Override
    public void stringify(List<Byte> buffer) throws IOException {
        final List<Byte> out = new ArrayList<>();

        // by default print the most out bracket first
        if (this.openingBracket) {
            out.add(this.getBracket(true));
            this.sendOutputData(out);
        }
        out.clear();

        for (byte currentByte : buffer) {
            if (!this.openingBracket) {
                // set whitespace
                out.add((byte) ',');
                out.add((byte) ' ');
                this.sendOutputData(out);
            }
            this.openingBracket = false;
            // print prefix if needed
            switch (outputOption) {
                case DEC:
                    break;
                case CHAR:
                    // TODO: Character output
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
        }
    }

    @Override
    public void parserFinalize() throws IOException {
        // add only most outer closing bracket
        final List<Byte> out = new ArrayList<>();
        out.add(this.getBracket(false));
        this.sendOutputData(out);
    }

    @Override
    public PanbyteOutput getFresh() {
        return new PanbyteArrayOutput(this.outputStream, outputOption, bracketsOption);
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
        //TODO
    }
}
