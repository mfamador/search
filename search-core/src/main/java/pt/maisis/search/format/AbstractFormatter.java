package pt.maisis.search.format;

import java.util.Locale;

/**
 * Abstract Formatter que tem como objectivo facilitar a
 * criacão de novos formatters.
 *
 * @version 1.0
 */
public abstract class AbstractFormatter implements Formatter {

    protected String pattern;
    protected String nullValue;

    public void setPattern(final String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setNullValue(final String nullValue) {
        this.nullValue = nullValue;
    }

    public String getNullValue() {
        return this.nullValue;
    }

    /**
     * Método que delega o pedido para o método 
     * {@link format(Object, Locale)} utilizando o locale por defeito 
     * da JVM.
     * <br/>
     * Se o valor do parâmetro <code>obj</code> for null retorna
     * o valor especificado em {@link #setNullValue(String)}.
     */
    public Object format(Object obj) {
        if (obj == null) {
            return this.nullValue;
        }
        return format(obj, Locale.getDefault());
    }
}
