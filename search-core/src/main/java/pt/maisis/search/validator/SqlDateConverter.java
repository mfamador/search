package pt.maisis.search.validator;

import pt.maisis.search.ContainerMetaData;

import java.util.Date;
import java.util.Locale;

public class SqlDateConverter extends DateConverter {

    public SqlDateConverter() {
    }

    public boolean convert(ValidationResult result,
            ValidationErrors errors,
            ContainerMetaData container,
            Locale locale) {

        boolean convert = super.convert(result, errors, container, locale);
        if (!convert) {
            return convert;
        }

        Date date = (Date) result.getValue(container.getName());

        if (date == null) {
            return convert;
        }

        result.setValue(container.getName(),
                new java.sql.Date(date.getTime()));
        return true;
    }
}
