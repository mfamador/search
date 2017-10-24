package pt.maisis.search.validator;

import pt.maisis.search.ContainerMetaData;

import java.util.Locale;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Validator que converte uma string numa instância de 
 * <code>Number</code>.<br/>
 *
 * @version 1.0
 */
public class NumberConverter extends AbstractConverter {

    public NumberConverter() {
    }

    public boolean convert(ValidationResult result,
            ValidationErrors errors,
            ContainerMetaData container,
            Locale locale) {
        try {
            String value = (String) result.getValue(container.getName());
            Number number = NumberFormat.getInstance(locale).parse(value);
            result.setValue(container.getName(), number);

            return true;

        } catch (ParseException e) {
            String key = getKey(container, locale, "validation.error.number");
            ValidationError error = new ValidationError(key, getLabel(container, locale));

            errors.add(error);
            return false;
        }
    }
}
