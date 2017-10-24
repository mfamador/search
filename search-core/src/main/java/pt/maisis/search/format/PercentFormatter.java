package pt.maisis.search.format;

import java.util.Locale;
import java.text.NumberFormat;
import java.text.DecimalFormat;

public class PercentFormatter extends AbstractFormatter {

    public PercentFormatter() {
    }

    public Object format(Object obj, Locale locale) {
        if (obj == null) {
            return super.nullValue;
        }

        if (super.pattern == null) {
            NumberFormat formatter = NumberFormat.getPercentInstance(locale);
            return formatter.format(obj);
        } else {
            DecimalFormat formatter = new DecimalFormat(pattern);
            return formatter.format(obj);
        }
    }
}
