package pt.maisis.search.format;

import java.util.Locale;
import java.text.MessageFormat;

public class NullMessageFormatter extends AbstractFormatter {

    public NullMessageFormatter() {
    }

    public Object format(Object obj, Locale locale) {
        if (obj == null) {
            return super.nullValue;
        }
        if (!(obj instanceof Object[])) {
            obj = new Object[]{obj.toString()};
        }

        Object[] values = preFormat(obj);
        if (values == null) {
            return null;
        }

        return MessageFormat.format(super.pattern, values);
    }

    protected Object[] preFormat(Object obj) {
        Object[] objs = (Object[]) obj;
        for (int i = 0; i < objs.length; i++) {
            if (objs[i] == null) {
                return null;
            }
        }
        return objs;
    }
}
