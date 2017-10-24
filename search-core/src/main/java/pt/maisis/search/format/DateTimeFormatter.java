package pt.maisis.search.format;

import pt.maisis.search.util.StringUtils;

import java.util.Locale;
import java.text.DateFormat;
import java.util.Date;

/**
 * Permite formatar uma data numa string com a informacão da data e hora.
 * No descriptor da pesquisa, quando se define o elemento <code>pattern</code>
 * deve-se especificar os estilos para a data e para a hora separados por
 * vírgula (por esta ordem).<br/>
 *
 * Exemplo:
 *
 * <pre>
 * &lt;formatter&gt;
 *   &lt;name&gt;dateTime&lt;/name&gt;
 *   &lt;pattern&gt;short,full&lt;/pattern&gt;
 * &lt;/formatter&gt;
 * </pre>
 */
public class DateTimeFormatter extends AbstractDateFormatter {

    private int datePattern = DateFormat.DEFAULT;
    private int timePattern = DateFormat.DEFAULT;

    public DateTimeFormatter() {
    }

    public void setPattern(final String pattern) {
        if (pattern.indexOf(",") != -1) {
            String[] patterns = (String[]) StringUtils.split(pattern);
            this.datePattern = parseStyle(patterns[0]);
            this.timePattern = parseStyle(patterns[1]);
        } else {
            this.datePattern = this.timePattern = parseStyle(pattern);
        }
    }

    public Object format(Object obj, Locale locale) {
        Date date = toDate(obj);

        if (date == null) {
            return super.nullValue;
        }

        DateFormat formatter = DateFormat.getDateTimeInstance(this.datePattern, this.timePattern, locale);

        return formatter.format(obj);
    }
}
