package cz.muni.fi.pv286.parser;

import cz.muni.fi.pv286.arguments.ProgramArguments;
import cz.muni.fi.pv286.parser.input.PanbyteArrayInput;
import cz.muni.fi.pv286.parser.input.PanbyteBitInput;
import cz.muni.fi.pv286.parser.input.PanbyteHexInput;
import cz.muni.fi.pv286.parser.input.PanbyteInput;
import cz.muni.fi.pv286.parser.input.PanbyteIntInput;
import cz.muni.fi.pv286.parser.input.PanbyteRawInput;
import cz.muni.fi.pv286.parser.output.PanbyteArrayOutput;
import cz.muni.fi.pv286.parser.output.PanbyteBitOutput;
import cz.muni.fi.pv286.parser.output.PanbyteHexOutput;
import cz.muni.fi.pv286.parser.output.PanbyteIntOutput;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;
import cz.muni.fi.pv286.parser.output.PanbyteRawOutput;

import java.io.OutputStream;

public class PanbyteInputOutputFactory {
    /**
     * Creates PanbyteInput from program arguments and selected output
     * @param arguments program arguments
     * @param output target PanByte output
     * @return PanbyteInput, null if PanbyteInput cannot be created
     */
    public static PanbyteInput makeInput(final ProgramArguments arguments, final PanbyteOutput output) {
        PanbyteInput input;

        switch (arguments.getInputFormat()) {
            case BYTES:
                input = new PanbyteRawInput(output);
                if (!arguments.isDelimiterSet()) {
                    // when delimiter is not explicitly set for this mode, ignore it
                    arguments.setDelimiter("");
                }
                break;
            case HEX:
                input = new PanbyteHexInput(output);
                break;
            case BITS:
                /* In that place, argument should be checked and valid */
                input = new PanbyteBitInput(output, arguments.getInputOption());
                break;
            case INT:
                input = new PanbyteIntInput(output, arguments.getInputOption());
                break;
            case ARRAY:
                input = new PanbyteArrayInput(output);
                break;
            default:
                throw new IllegalArgumentException("Unsupported input format");
        }

        return input;
    }

    /**
     * Creates PanbyteOutput from program arguments and selected output
     * @param arguments program arguments
     * @param outputStream target output stream
     * @return PanbyteOutput, null if PanbyteOutput cannot be created
     */
    public static PanbyteOutput makeOutput(final ProgramArguments arguments, final OutputStream outputStream) {
        PanbyteOutput output = null;

        switch (arguments.getOutputFormat()) {
            case BYTES:
                output = new PanbyteRawOutput(outputStream);
                break;
            case HEX:
                output = new PanbyteHexOutput(outputStream, true);
                break;
            case BITS:
                output = new PanbyteBitOutput(outputStream, true);
                break;
            case INT:
                output = new PanbyteIntOutput(outputStream, arguments.getOutputOption());
                break;
            case ARRAY:
                output = new PanbyteArrayOutput(outputStream, arguments.getOutputOption(), arguments.getOutputBrackets());
                break;
            default:
                throw new IllegalArgumentException("Unsupported output format");
        }

        return output;
    }
}
