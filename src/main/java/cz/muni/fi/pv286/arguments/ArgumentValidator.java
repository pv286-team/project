package cz.muni.fi.pv286.arguments;

import cz.muni.fi.pv286.arguments.values.FileType;
import cz.muni.fi.pv286.arguments.values.Option;

import java.util.ArrayList;
import java.util.List;

public class ArgumentValidator {

    /*  In case contradictory options are provided, use the latter. In case
     *  multiple options can be combined, apply all of them.
     */

    private static void validateInputFormat(ProgramArguments arguments) throws InvalidArgumentsException {
        switch (arguments.getInputFormat()) {
            case NONE:
                throw new InvalidArgumentsException("No input format given");
            case BYTES:
                if (!arguments.getInputOption().isEmpty()) {
                    throw new InvalidArgumentsException("Bytes format does not support input options");
                }
                break;
            case HEX:
                if (!arguments.getInputOption().isEmpty()) {
                    throw new InvalidArgumentsException("Hex format does not support input options");
                }
                break;
            case INT:
                Option lastInt = arguments.getInputOption().get(arguments.getInputOption().size() - 1);
                if (!lastInt.equals(Option.BIG_ENDIAN) && !lastInt.equals(Option.LITTLE_ENDIAN)) {
                    throw new InvalidArgumentsException("Invalid option for integer input format");
                }
                arguments.setInputOption(new ArrayList<>(List.of(lastInt)));
                break;
            case BITS:
                Option lastBits = arguments.getInputOption().get(arguments.getInputOption().size() - 1);
                if (!lastBits.equals(Option.LEFT_PAD) && !lastBits.equals(Option.RIGHT_PAD)) {
                    throw new InvalidArgumentsException("Invalid option for bits input format");
                }
                arguments.setInputOption(new ArrayList<>(List.of(lastBits)));
                break;
            case ARRAY:
                // TODO: Validate two values - format and brackets
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
                if (!arguments.getOutputOption().isEmpty()) {
                    throw new InvalidArgumentsException("Bytes format does not support output options");
                }
                break;
            case HEX:
                if (!arguments.getOutputOption().isEmpty()) {
                    throw new InvalidArgumentsException("Hex format does not support output options");
                }
                break;
            case INT:
                Option lastInt = arguments.getOutputOption().get(arguments.getOutputOption().size() - 1);
                if (!lastInt.equals(Option.BIG_ENDIAN) && !lastInt.equals(Option.LITTLE_ENDIAN)) {
                    throw new InvalidArgumentsException("Invalid option for integer output format");
                }
                arguments.setOutputOption(new ArrayList<>(List.of(lastInt)));
                break;
            case BITS:
                Option lastBits = arguments.getOutputOption().get(arguments.getOutputOption().size() - 1);
                if (!lastBits.equals(Option.LEFT_PAD) && !lastBits.equals(Option.RIGHT_PAD)) {
                    throw new InvalidArgumentsException("Invalid option for bits output format");
                }
                arguments.setOutputOption(new ArrayList<>(List.of(lastBits)));
                break;
            case ARRAY:
                // TODO: Validate two values - format and brackets
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
