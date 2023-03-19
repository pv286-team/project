## Parsing of int format
The implementation is in the `PanbyteIntInput` and `PanbyteIntOutput` classes.

### Int input
The parsing of integer from digits has to be divided into two main parts. Digits have to be accumulated into some buffer 
and processed only when there are no more digits on input because the bit representation of an integer depends on the whole 
integer, not on individual digits.

An instance of `BigInteger` class is created at the start of parsing. This class can represent arbitrarily large integers. 
For each digit from input, the internal `BigInteger` instance is multiplied by ten and the digit is converted 
to the new `BigInteger` instance and added to the internal one. This procedure allows us to process each digit 
separately, not storing arrays or Strings in memory but only one instance of `BigInteger` class.

When all input is read the internal `BigInteger` instance is converted to the byte array interpretation of the read 
integer. The byte array is reversed if there is the little-endian option selected - if not, byte array stays as it is, 
and it is passed on to the output parser.

### Int output
The output procedure fully utilises the possibilities of the `BigInteger` class. Firstly it reverses the internal byte 
array if the little-endian option for output is selected. Then the `BigInteger` class is constructed from the internal 
byte array, and it is converted to the decimal `String` representation of the integer by method of `BigInteger` class. 
The resulting `String` is then outputted.

### Encountered problems
As this parser was created after raw, bit and hex parsers, the structure of the program was ready for parsers 
that parses a chunk of data, outputs them and parses another chunk. That is not the case of this parser, so the structure 
had to be changed a little.