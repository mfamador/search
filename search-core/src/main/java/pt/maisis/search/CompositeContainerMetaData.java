package pt.maisis.search;

import pt.maisis.search.value.Value;
import pt.maisis.search.value.AjaxValue;

import java.util.List;
import java.util.ArrayList;
import pt.maisis.search.config.SearchConfig;
import pt.maisis.search.format.Formatter;
import pt.maisis.search.format.MessageFormatter;

/**
 * Composite Container composed by several containers.
 * Allow to assocate several graphical components (<i>text</i>, 
 * <i>select</i>, etc) to a single search criteria.
 *
 * @version 1.0
 */
public class CompositeContainerMetaData extends AbstractContainerMetaData {

    private static final Formatter DEFAULT_FORMATTER =
            new MessageFormatter("{0} {1}");
    private List containers = new ArrayList();
    private Formatter formatter = DEFAULT_FORMATTER;

    public CompositeContainerMetaData() {
    }

    /**
     * Permite adicionar um novo container a este container.
     */
    public void addContainer(final ContainerMetaData cmd) {
        this.containers.add(cmd);
        AbstractContainerMetaData acmd = (AbstractContainerMetaData) cmd;
        acmd.setParent(this);
    }

    /**
     * Retorna todos os containers agregados a este container.
     */
    public List getChildren() {
        return this.containers;
    }

    public int indexOf(final ContainerMetaData cmd) {
        return this.containers.indexOf(cmd);
    }

    public String getType() {
        return null;
    }

    public String getLabelOrientation() {
        throw new UnsupportedOperationException();
    }

    public int getSize() {
        throw new UnsupportedOperationException();
    }

    public int getInputSize() {
        throw new UnsupportedOperationException();
    }

    public boolean isDisabled() {
        throw new UnsupportedOperationException();
    }

    public boolean isChecked() {
        throw new UnsupportedOperationException();
    }

    public boolean isReadonly() {
        throw new UnsupportedOperationException();
    }

    public Value getDefaultValue() {
        throw new UnsupportedOperationException();
    }

    public AjaxValue getAjaxValue() {
        throw new UnsupportedOperationException();
    }

    public EventMetaData getEvent() {
        throw new UnsupportedOperationException();
    }

    public void setFormatter(final String name,
            final String pattern,
            final String nullValue) {
        try {
            SearchConfig config = SearchConfig.getInstance();
            String clazz = config.getSearchFormatter(name);

            this.formatter = (Formatter) Class.forName(clazz).newInstance();
            this.formatter.setPattern(pattern);
            this.formatter.setNullValue(nullValue);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Formatter getFormatter() {
        return this.formatter;
    }

    /**
     * Retorna <code>true</code>, visto ser um container composto.
     */
    public boolean isComposite() {
        return true;
    }

    public String toString() {
        return new StringBuffer("CompositeContainerMetaData[").append("name=").append(getName()).append(", label=").append(getLabel()).append("]").toString();
    }
}
