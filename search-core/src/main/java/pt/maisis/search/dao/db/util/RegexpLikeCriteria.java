package pt.maisis.search.dao.db.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RegexpLikeCriteria extends Criteria {
    private static Log log = LogFactory.getLog(RegexpLikeCriteria.class);
    protected static final String OPERATOR = "regexp_like";
    private final String column;
    private final Object[] values;

    public RegexpLikeCriteria(final String column) {
        this(column, null);
    }

    public RegexpLikeCriteria(final String column,
            final Object values) {
        this(column, (Object[]) values);
    }

    public RegexpLikeCriteria(final String column,
            final Object[] values) {
        this.column = column;
        this.values = values;
    }

    public void write(StringBuffer sb) {
        sb.append(OPERATOR).append(SPACE).append(OPEN_BRACKET);
        sb.append(this.column).append(COMMA);

        if (this.values == null) {
            sb.append(QUESTION_MARK).append(SPACE);
            sb.append(AND).append(SPACE).append(QUESTION_MARK);
        } else {
            sb.append(this.values[0]).append(SPACE);
            sb.append(AND).append(SPACE).append(this.values[1]);
        }
        sb.append(OPERATOR).append(CLOSE_BRACKET);
    }
}
