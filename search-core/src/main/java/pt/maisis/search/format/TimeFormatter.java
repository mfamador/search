package pt.maisis.search.format;

import java.util.Locale;
import java.text.DateFormat;
import java.util.Date;

public class TimeFormatter extends AbstractDateFormatter {

    public TimeFormatter() {
    }

    public Object format(Object obj, Locale locale) {
        Date date = toDate(obj);

        if (date == null) {
            return super.nullValue;
        }

        DateFormat formatter = DateFormat.getTimeInstance(getStyle(), locale);
        return formatter.format(obj);
    }
}
