package pt.maisis.search;

public class SqlFragmentParameterMetaData
        extends ParameterMetaDataImpl {

    private String value;

    public SqlFragmentParameterMetaData() {
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
