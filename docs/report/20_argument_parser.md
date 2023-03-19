## Argument parser
All arguments are passed to the program from the command line.
The `ArgumentParser` and `ArgumentValidator` classes are used to parse and validate
the input arguments.
The resulting options are stored in an instance of the `ProgramArguments` class.

#### Clarification on repeat options:
* repeating formats is considered an error
* repeating input and output file settings is considered an error
* repeating a delimiter is not considered an error, the last set is used
* repeating the input and output options is not considered an error, the last set is used

### Representation of program arguments
The `ProgramArgument` class provides a simple interface to use custom arguments passed to the
program from the command line. When an instance is created, it should guarantee
that the given combination of arguments and options is valid and does not need to
be checked further in the program.

Input and output formats can only take the values `bytes, bits, int, hex, array` as
specified in the specification. Input and output options are divided into variables
representing options for input (padding for bits format, endianness for int format),
output (endianness for int format, number expression for array) and bracket type
(only for the array as output format).

### Parsing of command line arguments
The `ArgumentParser` static class contains a simple algorithm for parsing input arguments.

First, the arguments are split into name and value pairs and stored in objects
of class `Argument`. An array of strings is traversed in turn, and long and short
switches are distinguished. For short options, the next item in the array is stored
as the argument value. For long options, the currently processed string is split.

Then the array of `Argument` objects is converted to the values stored in
the `ProgramArguments` object. If given by the input, missing values are set to
default.

### Validation of arguments
Finally, after parsing all arguments, the values set in the `ProgramArguments` class
object are validated. The combinations of input arguments and options and output
arguments, options and any parentheses (for array format) are checked.

### Encountered problems
Most of the problems were related to finding the ideal simple representation of
program arguments and then validating all possible combinations for different
formats.
