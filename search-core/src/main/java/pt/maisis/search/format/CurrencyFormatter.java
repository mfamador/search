package pt.maisis.search.format;

import java.util.Locale;
import java.text.NumberFormat;

/**
 * Formatador que permite formatar um dado valor numérico num valor 
 * em moeda.
 */
public class CurrencyFormatter extends AbstractFormatter {

    public CurrencyFormatter() {
    }

    public Object format(Object obj, Locale locale) {
        if (obj == null) {
            return super.nullValue;
        }

        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        return formatter.format(obj);
    }
}
