package pt.maisis.search.validator;

import pt.maisis.search.ContainerMetaData;

import java.util.Locale;

public class SqlLikeConverter extends AbstractConverter {

    public SqlLikeConverter() {
    }

    public boolean convert(ValidationResult result,
            ValidationErrors errors,
            ContainerMetaData container,
            Locale locale) {

        String value = (String) result.getValue(container.getName());

        String sb = new StringBuffer().append('%').append(value.trim().toUpperCase().replace(' ', '%')).append('%').toString();

        result.setValue(container.getName(), sb);
        return true;
    }
}
