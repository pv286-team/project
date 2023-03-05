package cz.muni.fi.pv286.arguments;

import cz.muni.fi.pv286.arguments.values.Format;
import org.junit.jupiter.api.Test;

class ProgramArgumentsTest {

    @Test
    void printHelp_short() {
        String[] args = {"-h"};
        try {
            ProgramArguments arguments = new ProgramArguments(args);
            assert(arguments.getHelp());
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    void printHelp_long() {
        String[] args = {"--help"};
        try {
            ProgramArguments arguments = new ProgramArguments(args);
            assert(arguments.getHelp());
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    void printHelp_longWrong() {
        String[] args = {"-help"};
        try {
            ProgramArguments arguments = new ProgramArguments(args);
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    void printHelp_moreArg() {
        String[] args = {"-h", "arg"};
        try {
            ProgramArguments arguments = new ProgramArguments(args);
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    void formatBytes_short() {
        String[] args = {"-f", "bytes","-t", "bytes"};
        try {
            ProgramArguments arguments = new ProgramArguments(args);
            assert(arguments.getInputFormat().equals(Format.BYTES));
            assert(arguments.getInputOption().isEmpty());
            assert(arguments.getOutputFormat().equals(Format.BYTES));
            assert(arguments.getOutputOption().isEmpty());
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    void formatBytes_long() {
        String[] args = {"--from=bytes", "--to=bytes"};
        try {
            ProgramArguments arguments = new ProgramArguments(args);
            assert(arguments.getInputFormat().equals(Format.BYTES));
            assert(arguments.getInputOption().isEmpty());
            assert(arguments.getOutputFormat().equals(Format.BYTES));
            assert(arguments.getOutputOption().isEmpty());
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    void formatBytes_longshort() {
        String[] args = {"--from=bytes", "-t", "bytes"};
        try {
            ProgramArguments arguments = new ProgramArguments(args);
            assert(arguments.getInputFormat().equals(Format.BYTES));
            assert(arguments.getInputOption().isEmpty());
            assert(arguments.getOutputFormat().equals(Format.BYTES));
            assert(arguments.getOutputOption().isEmpty());
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    void formatBytes_fromOption() {
        String[] args = {"--from=bytes", "-t", "bytes", "--from-options=big"};
        try {
            ProgramArguments arguments = new ProgramArguments(args);
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    void formatBytes_toOption() {
        String[] args = {"--from=bytes", "-t", "bytes", "--to-options=big"};
        try {
            ProgramArguments arguments = new ProgramArguments(args);
            assert(false);
        } catch (Exception e) {
            assert(true);
        }
    }

}