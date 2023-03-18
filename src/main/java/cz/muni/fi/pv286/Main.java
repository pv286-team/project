package cz.muni.fi.pv286;

import cz.muni.fi.pv286.arguments.InvalidArgumentsException;
import cz.muni.fi.pv286.arguments.ProgramArguments;
import cz.muni.fi.pv286.arguments.values.FileType;
import cz.muni.fi.pv286.parser.input.PanbyteHexInput;
import cz.muni.fi.pv286.parser.input.PanbyteInput;
import cz.muni.fi.pv286.parser.input.PanbyteIntInput;
import cz.muni.fi.pv286.parser.input.PanbyteRawInput;
import cz.muni.fi.pv286.parser.output.PanbyteHexOutput;
import cz.muni.fi.pv286.parser.input.PanbyteBitInput;
import cz.muni.fi.pv286.parser.output.PanbyteBitOutput;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;
import cz.muni.fi.pv286.parser.output.PanbyteIntOutput;
import cz.muni.fi.pv286.parser.output.PanbyteRawOutput;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, InvalidArgumentsException {

        ProgramArguments arguments = null;
        try {
            arguments = new ProgramArguments(args);
        } catch (InvalidArgumentsException e) {
            System.out.print(e.getMessage());
            System.exit(1);
        }

        final OutputStream outputStream;
        final InputStream inputStream;
        if (arguments.getInputFileType().equals(FileType.STANDARD)) {
            // Create default output stream from stdout
            outputStream = System.out;
        } else {
            System.out.print("Not implemented yet.");
            return;
        }

        if (arguments.getInputFileType().equals(FileType.STANDARD)) {
            // Create default input stream from stdin
            inputStream =  System.in;
        } else {
            System.out.print("Not implemented yet.");
            return;
        }

        final PanbyteOutput output;
        final PanbyteInput input;

        switch (arguments.getOutputFormat()) {
            case BYTES:
                output = new PanbyteRawOutput(outputStream);
                break;
            case HEX:
                output = new PanbyteHexOutput(outputStream);
                break;
            case BITS:
                output = new PanbyteBitOutput(outputStream);
                break;
            case INT:
                output = new PanbyteIntOutput(outputStream, arguments.getOutputOption());
                break;
            default:
                System.out.print("Not implemented yet.");
                return;
        }

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
            default:
                System.out.print("Not implemented yet.");
                return;
        }

        Main.processIO(inputStream, outputStream, input, arguments.getDelimiter().getBytes());
    }

    /**
     * Processes all inputs possibly split by delimiter until end of file is reached
     * For each input a new instance from inputFabricator is created
     * @param inputStream from where to read input
     * @param outputStream where to write output
     * @param inputFabricator model from which fresh inputs are cloned
     * @param delimiter delimiter to consider
     */
    public static void processIO(final InputStream inputStream, final OutputStream outputStream, final PanbyteInput inputFabricator, final byte[] delimiter) throws IOException, InvalidArgumentsException {
        final VirtualByteReader reader = new VirtualByteReader(inputStream);

        // the single IO process returns true when delimiter was hit and there is more data to be read
        while (Main.processIOSingle(reader, inputFabricator, delimiter)) {
            // flush the delimiter to the output if whole input was not read yet
            outputStream.write(delimiter);
            outputStream.flush();
        }
    }

    /**
     * Processes a single input (separated by delimiter or end of file), sends it to the input parser and terminates
     * @param reader input source
     * @param inputFabricator model from which fresh inputs are cloned
     * @param delimiter delimiter to consider
     * @return true if more input is to be read, false otherwise
     */
    private static boolean processIOSingle(final VirtualByteReader reader, final PanbyteInput inputFabricator, final byte[] delimiter) throws IOException, InvalidArgumentsException {
        final PanbyteInput input = inputFabricator.getFresh();

        // buffer read bytes so that it can be sent to the input parser at chunks
        final List<Byte> bufferInternal = new ArrayList<>();

        boolean delimiterReached = false;
        while (!delimiterReached && !reader.isAllRead()) {
            if (delimiter.length > 0 && reader.isNext(delimiter, true)) {
                // there is delimiter ahead, pop it from the input, ignore it and end this input
                delimiterReached = true;
            } else {
                // there is still at least one byte to read
                bufferInternal.add(reader.readByte());
            }

            // if the buffer is larger enough or the end of this input was reached, flush
            if (bufferInternal.size() % 4096 == 0 || delimiterReached || reader.isAllRead()) {
                input.parse(Collections.unmodifiableList(bufferInternal));
                bufferInternal.clear();
            }
        }

        // notify input parser that we are done
        input.parserFinalize();

        // returns true if there is more data to be read
        return !reader.isAllRead();
    }
}
