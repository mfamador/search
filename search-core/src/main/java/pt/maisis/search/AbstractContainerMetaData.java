package pt.maisis.search;

import pt.maisis.search.validator.Validatable;

/**
 * Abstract class that implements the 
 * {@link ContainerMetaData} interface methods common to
 * simple e composite containers types.
 *
 * @version 1.0
 */
public abstract class AbstractContainerMetaData implements ContainerMetaData {

    private String name;
    private String label;
    private CompositeContainerMetaData parent;
    private SearchParameterMetaData spmd;
    private Validatable validatable;

    public AbstractContainerMetaData() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public ContainerMetaData getParent() {
        return this.parent;
    }

    public void setParent(final CompositeContainerMetaData parent) {
        this.parent = parent;
    }

    public SearchParameterMetaData getSearchParameterMetaData() {
        if (this.parent == null) {
            return this.spmd;
        }
        return this.parent.getSearchParameterMetaData();
    }

    public void setSearchParameterMetaData(final SearchParameterMetaData spmd) {
        this.spmd = spmd;
    }

    public Validatable getValidatable() {
        return this.validatable;
    }

    public void setValidatable(final Validatable validatable) {
        this.validatable = validatable;
    }
}
