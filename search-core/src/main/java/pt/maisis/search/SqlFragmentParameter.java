package pt.maisis.search;

public class SqlFragmentParameter {

    private final String value;

    public SqlFragmentParameter(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return new StringBuffer("SqlFragmentParameter[").append("value=").append(this.value).append("]").toString();
    }
}
