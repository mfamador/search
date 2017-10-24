package pt.maisis.search.format;

import java.util.Locale;
import java.text.DateFormat;
import java.util.Date;

public class DateFormatter extends AbstractDateFormatter {

    public DateFormatter() {
    }

    public Object format(Object obj, Locale locale) {
        Date date = toDate(obj);

        if (date == null) {
            return super.nullValue;
        }

        DateFormat formatter = DateFormat.getDateInstance(getStyle(), locale);
        return formatter.format(obj);
    }
}
