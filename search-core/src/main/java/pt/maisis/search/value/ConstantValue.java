package pt.maisis.search.value;

import java.util.Map;

/**
 * Classe que represenra um valor constante.
 *
 * @version 1.0
 */
public class ConstantValue implements Value {

    private String value;

    public ConstantValue() {
    }

    public void setConstant(final String value) {
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    public Object getValue(final Map context) {
        return this.value;
    }
}
