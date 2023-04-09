package cz.muni.fi.pv286.arguments;

public class Argument {
    private final String name;
    private final String value;

    public Argument(String name, String value) throws InvalidArgumentsException {
        if (name.isEmpty()) {
            throw new InvalidArgumentsException("Name of argument cannot be empty");
        }
        this.name = name;
        this.value = this.stripValue(value);
    }

    public String getName() { return this.name; }

    public String getValue() {
        return this.value;
    }

    /** Strips the possible "" away from argument value */
    private String stripValue(String value) {
        if (value.startsWith("\"") && value.endsWith("\"") && value.length() > 1)
            return value.substring(1, value.length() - 1);
        return value;
    }
}
