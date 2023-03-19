## Parsing of bit format
The implementation is in the `PanbyteBitInput` and `PanbyteBitOutput` classes.

### Bit input
Parsing is done in eighths of bytes, which are converted to bit values and stored
as a single byte. If it is not possible to take the entire octet from the input,
the next input is waited for.

Because of padding, it is necessary to distinguish when the parsed bytes are sent
to the output. If padding is set to true, then the parsed eights can be sent to the
output immediately. In the case of left padding, it is necessary to wait for the
entire input to be read before adjusting the bytes to match the correct padding.

### Bit output
The bit output is quite simple. To implement it, it is only necessary to read
individual bits in parsed bytes and convert them to output values 1 and 0.

### Encountered problems
One of the main problems in the implementation was the use of the `byte` type,
which is signed in Java. Therefore, it was necessary to create custom helper
functions to implement the bit shift.
