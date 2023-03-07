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
    private List<Option> inputOption = new ArrayList<>();
    private FileType outputFileType = FileType.STANDARD;
    private String outputFileName = "";
    private Format outputFormat = Format.NONE;
    private List<Option> outputOption = new ArrayList<>();
    private String delimiter = "";
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

    public List<Option> getInputOption() {
        return inputOption;
    }

    public void addInputOption(Option inputOption) {
        this.inputOption.add(inputOption);
    }

    public void setInputOption(List<Option> inputOption) {
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

    public List<Option> getOutputOption() {
        return outputOption;
    }

    public void addOutputOption(Option option) {
        this.outputOption.add(option);
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public Boolean getHelp() {
        return help;
    }

    public void setHelp(Boolean help) {
        this.help = help;
    }

    public void setOutputOption(List<Option> outputOption) {
        this.outputOption = outputOption;
    }
}
