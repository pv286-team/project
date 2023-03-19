## Bytes parser

The implementation is done in `PanbyteRawInput` and `PanbyteRawOutput` classes. Because our project internally represents the data as raw bytes, the implementation is really simple in the form of pass-through.

### Encountered problems

Because the bytes format should not consider a delimiter unless explicitly specified, we needed to modify the argument parser so that it records if a delimiter was specified. Otherwise, this mode would skip all `\n` characters as it has considered it as a default delimiter.
