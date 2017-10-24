package pt.maisis.search.validator;

import pt.maisis.search.ContainerMetaData;

import java.util.Iterator;
import java.util.Date;
import java.util.Locale;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import pt.maisis.search.util.MessageResources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Date Converter que permite converter strings em datas.<br/>
 *
 * Este converter precisa que sejam definidas, no descriptor da 
 * pesquisa, as <i>patterns</i> a utilizar para formatar as datas, 
 * em funcão dos <i>locales</i>.<br/>
 *
 * Exemplo:
 * <pre>
 * &lt;validation&gt;
 *   &lt;converter&gt;date&lt;/converter&gt;
 *   &lt;converter-properties&gt;
 *     &lt;property&gt;
 *	  &lt;name&gt;pt&lt;/name&gt;
 *	  &lt;value&gt;yyyy-MM-dd&lt;/value&gt;
 *     &lt;/property&gt;
 *     &lt;property&gt;
 *	  &lt;name&gt;en&lt;/name&gt;
 *	  &lt;value&gt;MM-dd-yyyy&lt;/value&gt;
 *     &lt;/property&gt;
 *     &lt;property&gt;
 *	  &lt;name&gt;default-pattern&lt;/name&gt;
 *	  &lt;value&gt;yyyy-MM-dd&lt;/value&gt;
 *     &lt;/property&gt;
 *   &lt;/converter-properties&gt;
 * &lt;/validation&gt;
 * </pre>
 *
 * @version 1.0
 */
public class DateConverter extends AbstractConverter {

    private static Log log = LogFactory.getLog(DateConverter.class);
    /**
     * Pattern que é utilizada por defeito, quando não é encontrada 
     * nenhuma pattern para um dado locale.
     */
    public static final String DEFAULT_PATTERN = "default-pattern";

    public DateConverter() {
    }

    public boolean convert(ValidationResult result,
            ValidationErrors errors,
            ContainerMetaData container,
            Locale locale) {

        if (container.isComposite()) {
            boolean failed = false;
            Calendar c = Calendar.getInstance();
            c.clear();

            for (Iterator it = container.getChildren().iterator(); it.hasNext();) {
                ContainerMetaData child = (ContainerMetaData) it.next();
                Object value = result.getValue(child.getName());

                if (value instanceof Date) {
                    add(c, (Date) value);

                } else {
                    String key = getKey(container, locale,
                            "validation.error.composite.required");

                    ValidationError error = new ValidationError(key, getLabel(child, locale), getLabel(container, locale));

                    errors.add(error);
                    failed = true;
                }
            }
            if (failed) {
                return false;
            }

            result.setValue(container.getName(), c.getTime());
            return true;
        }

        String value = (String) result.getValue(container.getName());
        String pattern = getPattern(locale);

        if (pattern == null) {
            String error = MessageResources.getInstance().getMessage("validation.error.date.config", locale, container.getName());
            log.error(error);
            return false;
        }

        if (log.isTraceEnabled()) {
            String message = MessageResources.getInstance().getMessage("validation.date.pattern.choosed", pattern);
            log.trace(message);
        }

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            formatter.setLenient(false);
            result.setValue(container.getName(), formatter.parse(value));
            return true;

        } catch (ParseException e) {
            String key = getKey(container, locale, "validation.error.date");

            ValidationError error = new ValidationError(key, getLabel(container, locale));

            errors.add(error);
            return false;
        }
    }

    /**
     * Tenta encontrar a pattern para o dado locale.
     */
    private String getPattern(Locale locale) {
        String pattern = getProperty(locale.toString());

        if (pattern == null) {
            if (hasVariant(locale)) {
                pattern = getProperty(removeVariant(locale));
            }
            if (pattern == null && hasCountry(locale)) {
                pattern = getProperty(removeCountry(locale));
            }
            if (pattern == null) {
                pattern = getProperty(DEFAULT_PATTERN);
            }
        }
        return pattern;
    }

    private boolean hasVariant(Locale locale) {
        return locale.getVariant() != null
                && !"".equals(locale.getVariant());
    }

    private boolean hasCountry(Locale locale) {
        return locale.getCountry() != null
                && !"".equals(locale.getCountry());
    }

    private String removeVariant(Locale locale) {
        return new StringBuffer(5).append(locale.getLanguage()).append("_").append(locale.getCountry()).toString();
    }

    private String removeCountry(Locale locale) {
        return locale.getLanguage();
    }

    private void add(Calendar c, Date d) {
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d);

        int value;
        value = c2.get(Calendar.YEAR);
        if (c.get(Calendar.YEAR) < value) {
            c.set(Calendar.YEAR, value);
        }

        value = c2.get(Calendar.MONTH);
        if (c.get(Calendar.MONTH) < value) {
            c.set(Calendar.MONTH, value);
        }

        value = c2.get(Calendar.DAY_OF_MONTH);
        if (c.get(Calendar.DAY_OF_MONTH) < value) {
            c.set(Calendar.DAY_OF_MONTH, value);
        }

        value = c2.get(Calendar.HOUR_OF_DAY);
        if (c.get(Calendar.HOUR_OF_DAY) < value) {
            c.set(Calendar.HOUR_OF_DAY, value);
        }

        value = c2.get(Calendar.MINUTE);
        if (c.get(Calendar.MINUTE) < value) {
            c.set(Calendar.MINUTE, value);
        }

        value = c2.get(Calendar.SECOND);
        if (c.get(Calendar.SECOND) < value) {
            c.set(Calendar.SECOND, value);
        }

        value = c2.get(Calendar.MILLISECOND);
        if (c.get(Calendar.MILLISECOND) < value) {
            c.set(Calendar.MILLISECOND, value);
        }
    }
}
