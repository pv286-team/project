## Parsing of hex format

The implementation is in the `PanbyteHexInput` and `PanbyteHexOutput` classes.

### Hex input

Parsing is done in pairs of bytes, which are converted to and stored
as a single byte. If only a odd number of bytes is provided, the class waits for more input to be read.

### Hex output

Hex output is simple, as all that is needed to do is to format every internally stored byte into two bytes, which are the ASCII hex representation of said byte.
