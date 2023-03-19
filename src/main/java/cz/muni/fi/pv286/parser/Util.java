package cz.muni.fi.pv286.parser;

import java.util.ArrayList;
import java.util.List;

public class Util {
    /**
     * Convert a single byte into its ASCII value string
     * @param b byte to be converted into its ASCII value string
     * @return ASCII representation of given byte value
     */
    public static String byteAsASCII(final Byte b) {
        return String.valueOf((char) b.byteValue());
    }

    public static List<Byte> byteList(final String text) {
        final List<Byte> list = new ArrayList<>();
        byte[] bytes = text.getBytes();
        for (byte b : bytes) {
            list.add(b);
        }
        return list;
    }
}
