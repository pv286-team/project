## Overall project design

The main design goal of the project was to create an easily extensible program with a high focus on memory-safe operations. The functionality of the program is to be assured with both unit (manually coded) and integration tests (taken from the assignment).

### Integration tests

Input and output examples from the assignment were adapted into a simple Python script which first builds the project using maven commands and then checks if the expected output is the same as the actual output. As these simple tests are written as simple Python, creating a new test case is as simple as adding a new line to the correct place in the code. These tests are executed after each push as a GitHub action.

### Input processing

Our goal for a memory-safe program is to never store the whole input in memory (unless necessary), but rather read the input in chunks while outputting data as soon as it can be correctly formatted. To help with reading the input, a `VirtualByteReader` class was created. This class internally reads input by 4096B chunks whenever necessary, but provides interface for reading the input byte-by-byte. Furthermore, because the virtually read, but not-yet-requested raw input bytes are stored internally in memory, a helper method was created which can check the top of the byte queue if an arbitrary-long delimiter follows. With these two functions, we can then precisely detect when one input ends, and another begins with ease.

### The idea behind parsers and formatters

The primary idea of the software is that everything processed is just bytes, which are only represented in different forms. For this, two main abstract classes exist -- `PanbyteInput` for parsing input & representing it internally as bytes and `PanbyteOutput` for formatting internal bytes into the requested output format. This makes implementing each input or output mode as easy as creating a child of one of these two classes.

The overall process of processing IO operation can be imagined as follows:

```
input bytes -> VirtualByteReader.readChunk() -> VirtualByteReader.readByte() -> PanbyteInput.parse() -> PanbyteInput.flush() -> PanbyteOutput.format() -> PanbyteOutput.sendDataAndFlush() -> output bytes
```

All inputs and outputs also check for the correctness and completeness of the input, namely if no disallowed characters are present and that everything could be parsed when end of file is reached.
