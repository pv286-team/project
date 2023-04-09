package cz.muni.fi.pv286;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class allows to read bytes in the byte-by-byte format while
 * internally buffering input from the stream for faster IO handling
 */
public class VirtualByteReader {
    // source stream
    private final InputStream inputStream;
    // internal read buffer
    private final ArrayDeque<Byte> buffer = new ArrayDeque<>();
    // if true, then nothing more can be read
    private boolean endOfFile = false;

    public VirtualByteReader(final InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * Reads a single byte
     * @return Byte if there is at least one unseen, null if nothing more can be read
     */
    public Byte readByte() throws IOException {
        if (this.isAllRead()) {
            return null;
        }
        return this.buffer.pollFirst();
    }

    /**
     * Peeks at the next buffer that is supposed to be read, but does not read it
     * @return Byte if there is at least one unseen, null if nothing more can be read
     */
    public Byte peek() throws IOException {
        if (this.isAllRead()) {
            return null;
        }
        return this.buffer.peekFirst();
    }

    /**
     * Actively checks if more bytes can be read or if end of file was already reached
     * @return true if at least one more byte can be obtained by this.read()
     */
    public boolean isAllRead() throws IOException {
        while (buffer.isEmpty() && !this.endOfFile) {
            this.readBytesToBuffer();
        }
        // when we are here, either the buffer is not empty or EOF was reached
        // so checking for empty buffer is enough
        return buffer.isEmpty();
    }

    /**
     * Checks if the following bytes to be read are exactly as passed.
     * Unless pop is set to true, does not modify the bytes to be read in any way
     * @param bytes bytes that are expected to be read
     * @param pop if set to true then and the bytes match the ones to be read, then the bytes are skipped from the reading
     * @return true if subsequent (bytes.length)-calls to thus.read() would result exactly in the content of bytes
     */
    public boolean isNext(byte[] bytes, boolean pop) throws IOException {
        final List<Byte> readBytes = new ArrayList<>();
        boolean matches = true;

        for (int i = 0; i < bytes.length; i++) {
            if (i == 0 && (!this.isAllRead() && this.peek() != bytes[0])) {
                // quickly compare the head element without removing it from the buffer
                return false;
            }

            // pop the next byte and compare
            final Byte nextByte = this.readByte();
            if (nextByte == null) {
                // nothing more to be read -> does not match
                matches = false;
                break;
            }

            // mark the byte as read
            readBytes.add(nextByte);

            if (nextByte != bytes[i]) {
                matches = false;
                break;
            }
        }

        if (!matches || !pop) {
            // return all read bytes to the beginning of the internal read buffer
            Collections.reverse(readBytes);
            for (Byte readByte : readBytes) {
                this.buffer.addFirst(readByte);
            }
        }

        return matches;
    }

    /**
     * Reads up-to 4096 new bytes and saves them to the internal buffer.
     * May read less.
     * If the end of file was reached, sets endOfFile to true.
     */
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
