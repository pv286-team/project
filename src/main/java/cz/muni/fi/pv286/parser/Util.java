package cz.muni.fi.pv286.parser;

public class Util {
    /**
     * Convert a single byte into its ASCII value string
     * @param b byte to be converted into its ASCII value string
     * @return ASCII representation of given byte value
     */
    public static String byteAsASCII(final Byte b) {
        return String.valueOf((char) b.byteValue());
    }
}
