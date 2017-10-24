package pt.maisis.search.format;

import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CustomDateFormatter extends DateFormatter {

    private static Log log = LogFactory.getLog(CustomDateFormatter.class);
    protected String pattern;

    public CustomDateFormatter() {
    }

    public void setPattern(final String pattern) {
        super.setPattern(pattern);
        this.pattern = pattern;
    }

    public Object format(Object obj, Locale locale) {

        try {
            Date date = toDate(obj);

            if (date == null) {
                return super.nullValue;
            }

            if (this.pattern == null) {
                return super.format(obj, locale);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat(this.pattern);

            return dateFormat.format(date);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return "";
    }
}
