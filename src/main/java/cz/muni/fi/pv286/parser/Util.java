package cz.muni.fi.pv286.parser;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Util {
    /** Map of brackets, keys are opening; values are closing */
    public static Map<Byte, Byte> bracketsOpening = Map.of((byte) '{', (byte) '}', (byte) '(', (byte) ')', (byte) '[', (byte) ']');
    /** Map of brackets, keys are closing; values are opening */
    public static Map<Byte, Byte> bracketsClosing = Map.of((byte) '}', (byte) '{', (byte) ')', (byte) '(', (byte) ']', (byte) '[');

    /**
     * Convert a single byte into its ASCII value string
     * @param b byte to be converted into its ASCII value string
     * @return ASCII representation of given byte value
     */
    public static String byteAsASCII(final Byte b) {
        return String.valueOf((char) b.byteValue());
    }

    /**
     * Converts arbitrary string into a list of bytes
     * @param text text to get bytes from
     * @return raw bytes representation of given string
     */
    public static List<Byte> byteList(final String text) {
        final List<Byte> list = new ArrayList<>();
        byte[] bytes = text.getBytes();
        for (byte b : bytes) {
            list.add(b);
        }
        return list;
    }

    /**
     * Converts list of bytes into string
     * @param bytes list of bytes
     * @return ASCII string from given bytes
     */
    public static String stringFromBytes(final List<Byte> bytes) {
        final byte[] bytesArray = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            bytesArray[i] = bytes.get(i);
        }
        return new String(bytesArray, StandardCharsets.US_ASCII);
    }

    /**
     * Unescapes a string that contains standard Java escape sequences.
     * <ul>
     * <li><strong>&#92;b &#92;f &#92;n &#92;r &#92;t &#92;" &#92;'</strong> :
     * BS, FF, NL, CR, TAB, double and single quote.</li>
     * <li><strong>&#92;X &#92;XX &#92;XXX</strong> : Octal character
     * specification (0 - 377, 0x00 - 0xFF).</li>
     * <li><strong>&#92;uXXXX</strong> : Hexadecimal based Unicode character.</li>
     * </ul>
     * @author <a href="https://gist.github.com/uklimaschewski/6741769">Udo Klimaschewski</a>, adapted to support \x?? chars
     *
     * @param st
     *            A string optionally containing standard java escape sequences.
     * @return The translated string.
     */
    @SuppressWarnings({"ConcatenationWithEmptyString", "DataFlowIssue"})
    public static String unescapeJavaString(String st) {

        StringBuilder sb = new StringBuilder(st.length());

        for (int i = 0; i < st.length(); i++) {
            char ch = st.charAt(i);
            if (ch == '\\') {
                char nextChar = (i == st.length() - 1) ? '\\' : st
                        .charAt(i + 1);
                // Octal escape?
                if (nextChar >= '0' && nextChar <= '7') {
                    String code = "" + nextChar;
                    i++;
                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                            && st.charAt(i + 1) <= '7') {
                        code += st.charAt(i + 1);
                        i++;
                        if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
                                && st.charAt(i + 1) <= '7') {
                            code += st.charAt(i + 1);
                            i++;
                        }
                    }
                    sb.append((char) Integer.parseInt(code, 8));
                    continue;
                }
                switch (nextChar) {
                    case '\\':
                        ch = '\\';
                        break;
                    case 'b':
                        ch = '\b';
                        break;
                    case 'f':
                        ch = '\f';
                        break;
                    case 'n':
                        ch = '\n';
                        break;
                    case 'r':
                        ch = '\r';
                        break;
                    case 't':
                        ch = '\t';
                        break;
                    case '\"':
                        ch = '\"';
                        break;
                    case '\'':
                        ch = '\'';
                        break;
                    // Hex Unicode: u????
                    case 'u':
                        if (i >= st.length() - 5) {
                            ch = 'u';
                            break;
                        }
                        int code = Integer.parseInt(
                                "" + st.charAt(i + 2) + st.charAt(i + 3)
                                        + st.charAt(i + 4) + st.charAt(i + 5), 16);
                        sb.append(Character.toChars(code));
                        i += 5;
                        continue;
                    // Hex x??
                    case 'x':
                        if (i >= st.length() - 3) {
                            ch = 'x';
                            break;
                        }
                        int hexCode = Integer.parseInt(
                                "" + st.charAt(i + 2) + st.charAt(i + 3), 16);
                        sb.append(Character.toChars(hexCode));
                        i += 3;
                        continue;
                }
                i++;
            }
            sb.append(ch);
        }
        return sb.toString();
    }

    /**
     * Converts array of bytes into list of bytes
     * @param bytes array of bytes
     * @return list of bytes
     */
    public static List<Byte> bytesToList(byte[] bytes) {
        List<Byte> result = new ArrayList<>();
        for (byte b : bytes)
            result.add(b);
        return result;
    }
}
