package cz.muni.fi.pv286.parser.input;

import cz.muni.fi.pv286.arguments.InvalidArgumentsException;
import cz.muni.fi.pv286.arguments.values.Option;
import cz.muni.fi.pv286.parser.ArrayBracket;
import cz.muni.fi.pv286.parser.output.PanbyteArrayOutput;
import cz.muni.fi.pv286.parser.output.PanbyteMockOutput;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parsers array input
 */
public class PanbyteArrayInput extends PanbyteInput {
    private enum ParseStatus {
        /** No data was yet parsed, expecting top-level opening bracket */
        ROOT,
        /** Reading byte input right into internal parser until end of represented byte is reached */
        INPUT,
        /** Seek the start of new input or opening bracket */
        SEEK_INPUT_START,
        /** Same as above, but closing bracket is not a valid character now */
        SEEK_INPUT_START_CANNOT_CLOSE,
        /** Seek the comma separating two inputs or closing brackets  */
        SEEK_INPUT_END
    }

    /** Comma separating two represented bytes */
    private final static byte DEFAULT_SEPARATOR = ',';

    /** List of characters that are expected to found in the right order */
    private final List<Byte> expectedClosingBrackets = new ArrayList<>();
    /** List of all encountered brackets and their types for usage in ArrayOutput */
    private final List<ArrayBracket> brackets = new ArrayList<>();
    /** Yet-to-be parsed bytes */
    private final List<Byte> unparsedBuffer = new ArrayList<>();
    /** Current parsing state, as in automata */
    private ParseStatus state = ParseStatus.ROOT;
    /** Count of parsed represented bytes, for storing correct position of brackets */
    private int parsedByteIndex = 0;
    /** Internal parser for single represented byte */
    private PanbyteInput innerInput;
    /** Mock output for reading bytes from the internal one */
    private PanbyteMockOutput innerOutput;
    /** This character marks the end of the current input */
    private byte inputSeparator;

    private final PanbyteOutput output;

    public PanbyteArrayInput(final PanbyteOutput output) {
        super(output);
        this.output = output;
    }

    @Override
    public void parse(final List<Byte> buffer) throws IOException, InvalidArgumentsException {
        this.unparsedBuffer.addAll(buffer);

        while (this.unparsedBuffer.size() > 0) {
            final Byte nextByte = this.unparsedBuffer.remove(0);
            final ArrayBracket bracket = this.addBracket(nextByte);

            switch (this.state) {
                // root node -> look for a top-level bracket
                case ROOT:
                    // skip all whitespace
                    if (Character.isWhitespace(nextByte)) {
                        continue;
                    }
                    // force next character as bracket
                    if (bracket == null) {
                        throw new IllegalArgumentException("Expected a top-level bracket first");
                    }
                    // bracket was found, enter seeking the next input
                    this.state = ParseStatus.SEEK_INPUT_START;
                    continue;
                case SEEK_INPUT_START:
                case SEEK_INPUT_START_CANNOT_CLOSE:
                    // skip all whitespace
                    if (Character.isWhitespace(nextByte)) {
                        continue;
                    }
                    // just locally save the brackets
                    if (bracket != null) {
                        if (bracket.type == ArrayBracket.BracketType.CLOSING) {
                            if (this.state == ParseStatus.SEEK_INPUT_START_CANNOT_CLOSE) {
                                throw new IllegalArgumentException("Cannot close now");
                            }
                            this.state = ParseStatus.SEEK_INPUT_END;
                        } else {
                            // allow closing this newly opened bracket
                            this.state = ParseStatus.SEEK_INPUT_START;
                        }
                        continue;
                    }
                    // if not enough characters is left in buffer to decide input type, return the parsed byte and end cycle
                    if (!this.createNewInput(nextByte)) {
                        this.unparsedBuffer.add(0, nextByte);
                        break;
                    }
                    // inner input parser was successfully created
                    this.state = ParseStatus.INPUT;
                    continue;
                case INPUT:
                    // end of input was reached, flush the inner output to the real output and search for next input
                    if (nextByte == this.inputSeparator || (bracket != null && bracket.type == ArrayBracket.BracketType.CLOSING)) {
                        this.innerInput.parserFinalize();
                        final List<Byte> parsedBytes = this.innerOutput.getReceivedBytes();
                        this.parsedByteIndex += parsedBytes.size();
                        if (bracket != null) {
                            // we need to update the bracket index as the real number of bytes parsed can be different from the outer count
                            bracket.index = this.parsedByteIndex;
                        }
                        this.output.stringify(parsedBytes);
                        // if this separator was not the default comma, continue to search for the comma without parsing
                        // otherwise jump right to the next input
                        this.state = nextByte == DEFAULT_SEPARATOR ? ParseStatus.SEEK_INPUT_START_CANNOT_CLOSE : ParseStatus.SEEK_INPUT_END;
                    } else {
                        // push next byte to the inner parser
                        this.innerInput.parse(List.of(nextByte));
                        this.innerInput.flush();
                    }
                    continue;
                case SEEK_INPUT_END:
                    // skip all whitespace
                    if (Character.isWhitespace(nextByte)) {
                        continue;
                    }
                    // command found, next input can begin
                    if (nextByte == DEFAULT_SEPARATOR) {
                        this.state = ParseStatus.SEEK_INPUT_START_CANNOT_CLOSE;
                        continue;
                    }
                    // we can always close bracket while seeking for next input
                    if (bracket != null && bracket.type == ArrayBracket.BracketType.CLOSING) {
                        continue;
                    }
                    throw new IllegalArgumentException("Character '" + nextByte + "' not allowed while seeking next input");
                default:
                    throw new IllegalStateException("Unknown parsing state");
            }
        }
    }

    /**
     * Try to parse current character as bracket and if successful, add to list of all brackets and possibly
     * add new closing expectation.
     * Checks that closing brackets are in correct order.
     * @param character Byte to be parsed into bracket
     * @return Parsed bracket if successful, null if not a known bracket
     */
    private ArrayBracket addBracket(final Byte character) {
        final ArrayBracket bracket = ArrayBracket.fromByte(this.parsedByteIndex, character);
        if (bracket == null) {
            return null;
        }
        if (bracket.type == ArrayBracket.BracketType.OPENING ) {
            this.expectedClosingBrackets.add(0, bracket.pair);
        } else {
            if (this.expectedClosingBrackets.size() == 0 || this.expectedClosingBrackets.get(0) != bracket.character) {
                throw new IllegalArgumentException("Closing bracket '" + character + "' was not expected on index " + this.parsedByteIndex);
            }
            // pop expected closing bracket as it has just been found
            this.expectedClosingBrackets.remove(0);
        }

        this.brackets.add(bracket);
        if (this.output instanceof PanbyteArrayOutput) {
            ((PanbyteArrayOutput) this.output).setBrackets(brackets);
        }
        return bracket;
    }

    /**
     * Cast character into new current internal input
     * @param character character that decides what type of inputs comes next
     * @return true if we can decide on the type of input, false if more bytes need to be read into unparsed buffer first
     */
    private boolean createNewInput(final byte character) throws IOException, InvalidArgumentsException {
        this.innerOutput = new PanbyteMockOutput();

        if (character == '\'') {
            this.inputSeparator = '\'';
            this.innerInput = new PanbyteStringInput(this.innerOutput);
            return true;
        }
        if (Character.isDigit(character)) {
            this.inputSeparator = DEFAULT_SEPARATOR;

            if (character == '0') {
                // we need one more byte to decide if we should parse hex or bits
                if (this.unparsedBuffer.size() == 0) {
                    return false;
                }

                switch (this.unparsedBuffer.remove(0)) {
                    case 'b':
                        this.innerInput = new PanbyteBitInput(this.innerOutput, Option.LEFT_PAD);
                        return true;
                    case 'x':
                        this.innerInput = new PanbyteHexInput(this.innerOutput);
                        return true;
                }
            } else {
                this.innerInput = new PanbyteIntInput(this.innerOutput, Option.BIG_ENDIAN);
                // this byte is already supposed to be parsed
                this.innerInput.parse(List.of(character));
                this.innerInput.flush();
                return true;
            }
        }

        throw new IllegalArgumentException("Invalid prefix character '" + character + "'");
    }

    /**
     * Assert that all left bytes in the unparsed buffer are whitespace
     */
    @Override
    public void parserFinalize() throws IOException {
        // check if all brackets were correctly closed
        if (this.expectedClosingBrackets.size() > 0) {
            throw new IllegalArgumentException("Brackets were not correctly formatted");
        }
        if (this.unparsedBuffer.size() > 0) {
            throw new IllegalArgumentException("Not all bytes could be parsed");
        }

        super.parserFinalize();
    }

    @Override
    public PanbyteInput getFresh() {
        return new PanbyteArrayInput(this.output.getFresh());
    }
}
