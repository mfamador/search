package pt.maisis.search;

/**
 *
 * @version 1.0
 */
public class ParameterMetaDataImpl implements ParameterMetaData {

    protected String name;

    public ParameterMetaDataImpl() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
