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

public class Main {

    public static void main(String[] args) throws IOException {

        ProgramArguments arguments = null;
        try {
            arguments = new ProgramArguments(args);
        } catch (InvalidArgumentsException e) {
            System.out.print(e.getMessage());
            System.exit(1);
        }

        final OutputStream stdoutWriter;
        final InputStream stdinReader;
        if (arguments.getInputFileType().equals(FileType.STANDARD)) {
            // Create default output stream from stdout
            stdoutWriter = System.out;
        } else {
            System.out.print("Not implemented yet.");
            return;
        }

        if (arguments.getInputFileType().equals(FileType.STANDARD)) {
            // Create default input stream from stdin
            stdinReader =  System.in;
        } else {
            System.out.print("Not implemented yet.");
            return;
        }

        // Create default output as raw
        final PanbyteOutput output = new PanbyteRawOutput(stdoutWriter);
        // Create default input as raw with output as output
        final PanbyteInput input = new PanbyteRawInput(output);

        // Buffer of raw characters from the input
        byte[] buffPrimitive = new byte[4096];
        int buffPrimitiveReadCount;

        // Until the end of file is reached, try to fill up buffer
        while ((buffPrimitiveReadCount = stdinReader.read(buffPrimitive)) != -1) {
            // Copy the filled part of the (possibly) partially filled array into a List
            final List<Byte> bytes = new ArrayList<>();
            for (int i = 0; i < buffPrimitiveReadCount; i++) {
                bytes.add(buffPrimitive[i]);
            }

            // Notify input parser about new data and send it unmodifiable list
            input.parse(Collections.unmodifiableList(bytes));
        }

        // Notify input parser that we are done
        input.parserFinalize();
    }
}
