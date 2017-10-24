package pt.maisis.search;

import java.util.ArrayList;
import java.util.List;

import pt.maisis.search.value.AjaxValue;
import pt.maisis.search.value.Value;

/**
 * Simples container que permite associar um componente gráfico 
 * (<i>text</i>, <i>select</i>, etc) a um critério de pesquisa.
 *
 * @version 1.0
 */
public class SimpleContainerMetaData extends AbstractContainerMetaData {

    private String type;
    private String labelOrientation;
    private int size;
    private int inputSize;
    private boolean checked;
    private boolean disabled;
    private boolean readonly;
    private Value defaultValue;
    private AjaxValue ajaxValue;
    private EventMetaData event;

    public SimpleContainerMetaData() {
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        if (type != null) {
            this.type = type.toLowerCase();
        }
    }

    public String getLabelOrientation() {
        return this.labelOrientation;
    }

    public void setLabelOrientation(final String labelOrientation) {
        if (labelOrientation != null) {
            this.labelOrientation = labelOrientation.toLowerCase();
        }
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public int getInputSize() {
        return this.inputSize;
    }

    public void setInputSize(final int inputSize) {
        this.inputSize = inputSize;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isReadonly() {
        return this.readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public EventMetaData getEvent() {
        return this.event;
    }

    public void setEvent(final EventMetaData event) {
        this.event = event;
    }

    public Value getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(final Value defaultValue) {
        this.defaultValue = defaultValue;
    }

    public AjaxValue getAjaxValue() {
        return this.ajaxValue;
    }

    public void setAjaxValue(final AjaxValue ajaxValue) {
        this.ajaxValue = ajaxValue;
    }

    public boolean isComposite() {
        return false;
    }

    public List getChildren() {
        List list = new ArrayList();
        list.add(this);
        return list;
    }
}
