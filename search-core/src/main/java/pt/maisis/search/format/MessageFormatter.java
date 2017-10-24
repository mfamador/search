package pt.maisis.search.format;

import java.util.Locale;
import java.text.MessageFormat;
import pt.maisis.search.util.MessageResources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Formatador que pega num objecto ou array de objectos, formata-os
 * e coloca as strings resultantes nos sítios apropriados da pattern 
 * especificada no descriptor da pesquisa.
 * Este formatador precisa que seja especificada a 
 * <code>pattern</code> a utilizar no processo de formatacão.
 * <br/>
 * Para mais informacão sobre o formato das patterns ver
 * {@link java.text.MessageFormat}.
 *
 * @version 1.0
 */
public class MessageFormatter extends AbstractFormatter {

    private static Log log = LogFactory.getLog(MessageFormatter.class);

    public MessageFormatter() {
        super();
    }

    public MessageFormatter(final String pattern) {
        super();
        super.setPattern(pattern);
    }

    /**
     * Formata o dado objecto tendo em conta a pattern configurada
     * no descriptor da pesquisa.
     * <br/>
     * NOTA: se o dado parâmetro for um array, os valores a null
     * serão transformados em strings vazias antes da formatacão
     * propriamente dita.
     * <br/>
     * Por exemplo:
     * <pre>
     *   Object obj = new Object[] {"a", null, "b"};
     *   Formatter f = new MessageFormatter();
     *   f.setPattern("{0} {1} {2}");
     *   f.format(obj); // resultado: a  b
     * </pre>
     */
    public Object format(Object obj, Locale locale) {
        if (super.pattern == null) {
            MessageResources messages = MessageResources.getInstance();
            String message =
                    messages.getMessage("format.error.patternRequired");
            log.error(message);
            return null;
        }

        if (obj == null) {
            return super.nullValue;
        }

        if (!(obj instanceof Object[])) {
            obj = new Object[]{obj.toString()};
        }

        replaceNulls((Object[]) obj);
        return MessageFormat.format(super.pattern, (Object[]) obj);
    }

    /**
     * Substitui os nulls por strings vazias.
     */
    private void replaceNulls(Object[] objs) {
        for (int i = 0; i < objs.length; i++) {
            if (objs[i] == null) {
                objs[i] = "";
            }
        }
    }
}
