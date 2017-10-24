package pt.maisis.search;

import pt.maisis.search.format.Formatter;
import pt.maisis.search.format.NullFormatter;

/**
 * Class that implements some methods of the 
 * {@link ResultParameterMetaData} interface common to
 * {@link SimpleResultParameterMetaData} and
 * {@link CompositeResultParameterMetaData} classes.
 *
 * @version 1.0
 */
public abstract class AbstractResultParameterMetaData
        extends DynamicParameterMetaDataImpl
        implements ResultParameterMetaData {

    /** Formatador a utilizar por defeito. */
    protected static final Formatter DEFAULT_FORMATTER = new NullFormatter();
    private String searchLabel;
    private String resultLabel;
    private String align;
    private boolean selectable = true;
    private String headerStyle;
    private String valueStyle;

    public AbstractResultParameterMetaData() {
    }

    public String getSearchLabel() {
        return this.searchLabel;
    }

    public void setSearchLabel(final String searchLabel) {
        this.searchLabel = searchLabel;
    }

    public String getResultLabel() {
        return (this.resultLabel == null)
                ? this.searchLabel
                : this.resultLabel;
    }

    public void setResultLabel(final String resultLabel) {
        this.resultLabel = resultLabel;
    }

    public String getAlign() {
        return this.align;
    }

    public void setAlign(final String align) {
        this.align = align;
    }

    public void setSelectable(final boolean selectable) {
        this.selectable = selectable;
    }

    public boolean isSelectable() {
        return this.selectable;
    }

    public String getHeaderStyle() {
        return this.headerStyle;
    }

    public void setHeaderStyle(final String headerStyle) {
        this.headerStyle = headerStyle;
    }

    public String getValueStyle() {
        return this.valueStyle;
    }

    public void setValueStyle(final String valueStyle) {
        this.valueStyle = valueStyle;
    }
}
