package cz.muni.fi.pv286.arguments;

import cz.muni.fi.pv286.arguments.values.FileType;
import cz.muni.fi.pv286.arguments.values.Format;
import cz.muni.fi.pv286.arguments.values.Option;

import java.util.ArrayList;
import java.util.List;

public class ProgramArguments {

    private FileType inputFileType = FileType.STANDARD;
    private String inputFileName = "";
    private Format inputFormat = Format.NONE;
    private Option inputOption = Option.NONE;
    private FileType outputFileType = FileType.STANDARD;
    private String outputFileName = "";
    private Format outputFormat = Format.NONE;
    private Option outputOption = Option.NONE;
    private Option outputBrackets = Option.NONE;
    private String delimiter = "\n";
    private boolean delimiterSet = false;
    private Boolean help = false;


    public ProgramArguments(String[] args) throws InvalidArgumentsException {
        ArgumentParser.parseArguments(args, this);
        ArgumentValidator.validateArguments(this);
    }

    public FileType getInputFileType() {
        return inputFileType;
    }

    public void setInputFileType(FileType inputFileType) {
        this.inputFileType = inputFileType;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public Format getInputFormat() {
        return inputFormat;
    }

    public void setInputFormat(Format inputFormat) {
        this.inputFormat = inputFormat;
    }

    public Option getInputOption() {
        return inputOption;
    }


    public void setInputOption(Option inputOption) {
        this.inputOption = inputOption;
    }

    public FileType getOutputFileType() {
        return outputFileType;
    }

    public void setOutputFileType(FileType outputFileType) {
        this.outputFileType = outputFileType;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public Format getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(Format outputFormat) {
        this.outputFormat = outputFormat;
    }

    public Option getOutputOption() {
        return outputOption;
    }

    public void setOutputOption(Option option) {
        this.outputOption = option;
    }

    public Option getOutputBrackets() {
        return outputBrackets;
    }

    public void setOutputBrackets(Option outputBrackets) {
        this.outputBrackets = outputBrackets;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public boolean isDelimiterSet() {
        return this.delimiterSet;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
        this.delimiterSet = true;
    }

    public Boolean getHelp() {
        return help;
    }

    public void setHelp(Boolean help) {
        this.help = help;
    }
}
