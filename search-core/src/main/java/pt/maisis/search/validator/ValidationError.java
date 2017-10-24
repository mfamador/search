package pt.maisis.search.validator;

import java.io.Serializable;
import pt.maisis.search.util.ArrayUtils;

/**
 * Classe que representa um erro de validacão.
 *
 * @version 1.0
 *
 * @see ValidationErrors
 */
public class ValidationError implements Serializable {

    private final String key;
    private final Object[] values;

    public ValidationError(final String key) {
        this(key, null);
    }

    public ValidationError(final String key,
            final Object value) {
        this(key, new Object[]{value});
    }

    public ValidationError(final String key,
            final Object value1,
            final Object value2) {
        this(key, new Object[]{value1, value2});
    }

    public ValidationError(final String key,
            final Object value1,
            final Object value2,
            final Object value3) {
        this(key, new Object[]{value1, value2, value3});
    }

    public ValidationError(final String key,
            final Object[] values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return (this.key);
    }

    public Object[] getValues() {
        return (this.values);
    }

    public String toString() {
        return new StringBuffer("ValidationError[").append("key=").append(this.key).append(", value=").append(ArrayUtils.asList(this.values)).append("]").toString();
    }
}
