package cz.muni.fi.pv286.arguments;

import cz.muni.fi.pv286.arguments.values.FileType;
import cz.muni.fi.pv286.arguments.values.Option;

public class ArgumentValidator {

    /*  In case contradictory options are provided, use the latter. In case
     *  multiple options can be combined, apply all of them.
     */

    private static void validateInputFormat(ProgramArguments arguments) throws InvalidArgumentsException {
        switch (arguments.getInputFormat()) {
            case NONE:
                throw new InvalidArgumentsException("No input format given");
            case BYTES:
                if (!arguments.getInputOption().equals(Option.NONE)) {
                    throw new InvalidArgumentsException("Bytes format does not support input options");
                }
                break;
            case HEX:
                if (!arguments.getInputOption().equals(Option.NONE)) {
                    throw new InvalidArgumentsException("Hex format does not support input options");
                }
                break;
            case INT:
                Option intInputOption = arguments.getInputOption();
                if (!intInputOption.equals(Option.BIG_ENDIAN) && !intInputOption.equals(Option.LITTLE_ENDIAN)) {
                    throw new InvalidArgumentsException("Invalid option for integer input format");
                }
                break;
            case BITS:
                Option bitInputOption = arguments.getInputOption();
                if (!bitInputOption.equals(Option.LEFT_PAD) && !bitInputOption.equals(Option.RIGHT_PAD)) {
                    throw new InvalidArgumentsException("Invalid option for bits input format");
                }
                break;
            case ARRAY:
                if (!arguments.getInputOption().equals(Option.NONE)) {
                    throw new InvalidArgumentsException("Array format does not support input options");
                }
                break;
            default:
                throw new InvalidArgumentsException("Undefined input format");
        }
    }

    private static void validateOutputFormat(ProgramArguments arguments) throws InvalidArgumentsException {
        switch (arguments.getOutputFormat()) {
            case NONE:
                throw new InvalidArgumentsException("No output format given");
            case BYTES:
                if (!arguments.getOutputOption().equals(Option.NONE)
                        || !arguments.getOutputBrackets().equals(Option.NONE)) {
                    throw new InvalidArgumentsException("Bytes format does not support output options");
                }
                break;
            case HEX:
                if (!arguments.getOutputOption().equals(Option.NONE)
                        || !arguments.getOutputBrackets().equals(Option.NONE)) {
                    throw new InvalidArgumentsException("Hex format does not support output options");
                }
                break;
            case INT:
                Option intOutputOption = arguments.getOutputOption();
                if (!intOutputOption.equals(Option.BIG_ENDIAN) && !intOutputOption.equals(Option.LITTLE_ENDIAN)
                        || !arguments.getOutputBrackets().equals(Option.NONE)) {
                    throw new InvalidArgumentsException("Invalid option for integer input format");
                }
                break;
            case BITS:
                if (!arguments.getOutputOption().equals(Option.NONE)
                        || !arguments.getOutputBrackets().equals(Option.NONE)) {
                    throw new InvalidArgumentsException("Bit format does not support output options");
                }
                break;
            case ARRAY:
                Option arrayOutputOption = arguments.getOutputOption();
                if (!arrayOutputOption.equals(Option.HEX)
                        && !arrayOutputOption.equals(Option.DEC)
                        && !arrayOutputOption.equals(Option.CHAR)
                        && !arrayOutputOption.equals(Option.BIT)) {
                    throw new InvalidArgumentsException("Invalid option for array input format");
                }
                Option arrayBracketsOption = arguments.getOutputBrackets();
                if (!arrayBracketsOption.equals(Option.CURLY_BRACKETS)
                        && !arrayBracketsOption.equals(Option.REGULAR_BRACKETS)
                        && !arrayBracketsOption.equals(Option.SQUARE_BRACKETS)) {
                    throw new InvalidArgumentsException("Invalid option for array input format");
                }
                break;
            default:
                throw new InvalidArgumentsException("Undefined output format");
        }
    }

    public static void validateArguments(ProgramArguments arguments) throws InvalidArgumentsException {
        if (arguments.getHelp()) {
            return;
        }

        validateInputFormat(arguments);
        validateOutputFormat(arguments);

        if (arguments.getInputFileType().equals(FileType.FILE)
            && arguments.getInputFileName().isEmpty()) {
            throw new InvalidArgumentsException("Input file name is missing");
        }

        if (arguments.getOutputFileType().equals(FileType.FILE)
                && arguments.getOutputFileName().isEmpty()) {
            throw new InvalidArgumentsException("Output file name is missing");
        }
    }
}
