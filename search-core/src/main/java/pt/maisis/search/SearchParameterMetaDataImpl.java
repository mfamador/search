package pt.maisis.search;

public class SearchParameterMetaDataImpl
        extends DynamicParameterMetaDataImpl
        implements SearchParameterMetaData {

    private String field;
    private String operator;
    private ContainerMetaData cmd;
    private SearchMetaData smd;

    public SearchParameterMetaDataImpl() {
    }

    public String getField() {
        return this.field;
    }

    public void setField(final String field) {
        this.field = field;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(final String operator) {
        this.operator = operator;
    }

    public ContainerMetaData getContainer() {
        return this.cmd;
    }

    public void setContainer(final ContainerMetaData cmd) {
        this.cmd = cmd;

        AbstractContainerMetaData acmd = (AbstractContainerMetaData) cmd;
        acmd.setName(getName());
        acmd.setSearchParameterMetaData(this);
    }

    public void setSearchMetaData(final SearchMetaData smd) {
        this.smd = smd;
    }

    public SearchMetaData getSearchMetaData() {
        return this.smd;
    }

    public String toString() {
        return new StringBuffer("SearchParameterMetaDataImpl[").append("name=").append(getName()).
          append(", field=").append(this.field).append(", operator=").append(this.operator).append(", cmd=").
          append(this.cmd).append("]").toString();
    }
}
