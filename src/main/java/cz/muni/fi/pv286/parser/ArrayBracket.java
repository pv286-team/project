package cz.muni.fi.pv286.parser;

public class ArrayBracket {
    public enum BracketType {
        OPENING,
        CLOSING
    }

    /** Index of byte on which this array bracket was found */
    public final int index;
    /** Opening or closing bracket */
    public final BracketType type;
    /** Original character of the bracket */
    public final byte character;
    /** Character that goes to the pair (closing for opening, opening for closing) */
    public final byte pair;

    public ArrayBracket(final int index, final BracketType type, final byte character) {
        this.index = index;
        this.type = type;
        this.character = character;

        if (this.type == BracketType.OPENING) {
            this.pair = Util.bracketsOpening.get(this.character);
        } else {
            this.pair = Util.bracketsClosing.get(this.character);
        }
    }

    /**
     * Gets bracket type from character
     * @param character bracket character
     * @return OPENING or CLOSING; null if not known bracket character
     */
    public static BracketType getType(final byte character) {
        if (Util.bracketsOpening.containsKey(character)) {
            return BracketType.OPENING;
        }
        if (Util.bracketsClosing.containsKey(character)) {
            return BracketType.CLOSING;
        }

        return null;
    }

    /**
     * Gets array bracket from index and character
     * @param index on which index this bracket occurred
     * @param character bracket character
     * @return null if not a bracket character
     */
    public static ArrayBracket fromByte(final int index, final byte character) {
        final BracketType type = ArrayBracket.getType(character);
        if (type == null) {
            return null;
        }

        return new ArrayBracket(index, type, character);
    }
}
