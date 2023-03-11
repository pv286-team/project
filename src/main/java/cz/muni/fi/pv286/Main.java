package cz.muni.fi.pv286;

import cz.muni.fi.pv286.arguments.InvalidArgumentsException;
import cz.muni.fi.pv286.arguments.ProgramArguments;
import cz.muni.fi.pv286.arguments.values.FileType;
import cz.muni.fi.pv286.parser.input.PanbyteInput;
import cz.muni.fi.pv286.parser.input.PanbyteRawInput;
import cz.muni.fi.pv286.parser.output.PanbyteOutput;
import cz.muni.fi.pv286.parser.output.PanbyteRawOutput;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

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
            default:
                System.out.print("Not implemented yet.");
                return;
        }

        switch (arguments.getInputFormat()) {
            case BYTES:
                input = new PanbyteRawInput(output);
                break;
            default:
                System.out.print("Not implemented yet.");
                return;
        }

        Main.processIO(inputStream, outputStream, input, (byte) '\n');
    }

    private static void processIO(final InputStream inputStream, final OutputStream outputStream, final PanbyteInput inputFabricator, final byte delimiter) throws IOException {
        // buffer which contains data that were read after delimiter was found
        // this data is part of the next IO iteration
        final List<Byte> bufferLeftover = new ArrayList<>();

        while (Main.processIOSingle(inputStream, inputFabricator, delimiter, bufferLeftover)) {
            // flush the delimiter to the output if whole input was not read yet
            outputStream.write(delimiter);
            outputStream.flush();
        }
    }

    private static boolean processIOSingle(final InputStream inputStream, final PanbyteInput inputFabricator, final byte delimiter, final List<Byte> bufferLeftover) throws IOException {
        final PanbyteInput input = inputFabricator.getFresh();

        // Buffer of raw characters from the input
        byte[] buffPrimitive = new byte[4096];
        int buffPrimitiveReadCount;

        final List<Byte> bufferInternal = new ArrayList<>(bufferLeftover);
        bufferLeftover.clear();

        boolean delimiterEncountered = false;
        boolean endOfFileEncountered = false;

        // Until the end of file or delimiter is reached, try to fill up buffer
        while (!delimiterEncountered && !endOfFileEncountered) {
            buffPrimitiveReadCount = inputStream.read(buffPrimitive);
            if (buffPrimitiveReadCount == -1) {
                // we cannot end the cycle here
                // we still may have data inside bufferInternal that needs further parsing
                endOfFileEncountered = true;
            }

            // process new data only if we are not already at the end of the file
            if (!endOfFileEncountered) {
                // copy the filled part of the (possibly) partially filled array into a correct List
                for (int i = 0; i < buffPrimitiveReadCount; i++) {
                    byte currByte = buffPrimitive[i];

                    // check if the current input is split with this byte
                    if (currByte == delimiter) {
                        delimiterEncountered = true;
                        if (i == buffPrimitiveReadCount - 1) {
                            // the delimiter is the last byte of the already read input
                            // test if the delimiter is also the last byte of whole input
                            byte[] nextByte = new byte[1];
                            if (inputStream.read(nextByte) == -1) {
                                endOfFileEncountered = true;
                            } else {
                                // do not forget to add the read byte to the leftovers
                                bufferLeftover.add(nextByte[0]);
                            }
                        }

                        // do not process the delimiter further, skip it from parsing
                        // continue adding all already read bytes into leftover array
                        continue;
                    }

                    if (!delimiterEncountered) {
                        // no delimiter was found yet, add the current byte to queue for parsing
                        bufferInternal.add(currByte);
                    } else {
                        // delimiter was found already, queue the byte to be parsed by then next input parser
                        bufferLeftover.add(currByte);
                    }
                }
            }

            // notify input parser about new data and send it unmodifiable list
            if (bufferInternal.size() > 0) {
                input.parse(Collections.unmodifiableList(bufferInternal));
                bufferInternal.clear();
            }
        }

        // notify input parser that we are done
        input.parserFinalize();

        // returns true if delimiter was encountered -> there is more data
        return !endOfFileEncountered;
    }
}
