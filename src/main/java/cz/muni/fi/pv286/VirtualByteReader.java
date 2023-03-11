package cz.muni.fi.pv286;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;

public class VirtualByteReader {
    private final InputStream inputStream;
    private final ArrayDeque<Byte> buffer = new ArrayDeque<>();
    private boolean endOfFile = false;

    public VirtualByteReader(final InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public Byte readByte() throws IOException {
        if (this.isAllRead()) {
            return null;
        }
        return this.buffer.pollFirst();
    }

    public boolean isAllRead() throws IOException {
        while (buffer.isEmpty() && !this.endOfFile) {
            this.readBytesToBuffer();
        }
        // when we are here, either the buffer is not empty or EOF was reached
        // so checking for empty buffer is enough
        return buffer.isEmpty();
    }

    private void readBytesToBuffer() throws IOException {
        // Buffer of raw characters from the input
        byte[] buffPrimitive = new byte[4096];
        int buffPrimitiveReadCount = inputStream.read(buffPrimitive);

        if (buffPrimitiveReadCount == -1) {
            this.endOfFile = true;
            return;
        }

        for (int i = 0; i < buffPrimitiveReadCount; i++) {
            this.buffer.add(buffPrimitive[i]);
        }
    }
}
