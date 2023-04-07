package cz.muni.fi.pv286;

import cz.muni.fi.pv286.arguments.InvalidArgumentsException;
import cz.muni.fi.pv286.arguments.ProgramArguments;
import cz.muni.fi.pv286.parser.PanbyteInputOutputFactory;
import cz.muni.fi.pv286.parser.input.PanbyteInput;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ProgramArguments arguments = null;
        try {
            arguments = new ProgramArguments(args);
        } catch (InvalidArgumentsException e) {
            System.err.print(e.getMessage() + "\n");
            System.err.println(ProgramArguments.getHelpString());
            System.exit(1);
        }

        if (arguments.getHelp()) {
            System.out.println(ProgramArguments.getHelpString());
            return;
        }

        OutputStream outputStream = null;
        InputStream inputStream = null;

        switch (arguments.getOutputFileType()) {
            case STANDARD:
                outputStream = System.out;
                break;
            case FILE:
                try {
                    outputStream = new FileOutputStream(arguments.getOutputFileName());
                } catch (FileNotFoundException e) {
                    System.err.println("Output file not found: " + arguments.getOutputFileName());
                }
                break;
            default:
                throw new IllegalArgumentException("Input type not implemented");
        }

        switch (arguments.getInputFileType()) {
            case STANDARD:
                inputStream = System.in;
                break;
            case FILE:
                try {
                    inputStream = new FileInputStream(arguments.getInputFileName());
                } catch (FileNotFoundException e) {
                    System.err.println("Input file not found: " + arguments.getInputFileName());
                }
                break;
            default:
                throw new IllegalArgumentException("Input type not implemented");
        }

        final PanbyteOutput output = PanbyteInputOutputFactory.makeOutput(arguments, outputStream);
        PanbyteInput input = null;

        if (output != null) {
            input = PanbyteInputOutputFactory.makeInput(arguments, output);
        }
        if (input == null) {
            System.out.println(ProgramArguments.getHelpString());
            System.exit(1);
        }

        try {
            Main.processIO(inputStream, outputStream, input, arguments.getDelimiter().getBytes());
        } catch (OutOfMemoryError err) {
            System.err.println("Program run out of memory - probably the input is too big.");
            System.exit(1);
        } catch (IllegalArgumentException | InvalidArgumentsException e) {
            System.err.println("Unexpected error: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error occurred at input/output read/write: " + e.getMessage());
            System.exit(1);
        }
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
        return delimiterReached || !reader.isAllRead();
    }
}
