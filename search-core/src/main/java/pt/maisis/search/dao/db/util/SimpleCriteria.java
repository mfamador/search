package pt.maisis.search.dao.db.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SimpleCriteria extends Criteria {
   private static Log log = LogFactory.getLog(SimpleCriteria.class);
   
    private final String operator;
    private final String column;
    private final Object value;

    public SimpleCriteria(final String operator,
            final String column) {
        this(operator, column, null);
    }

    public SimpleCriteria(final String operator,
            final String column,
            final Object value) {
        this.operator = operator;
        this.column = column;
        this.value = value;
        
        log.debug("operator: " + operator);
    }

    public void write(StringBuffer sb) {
        sb.append(this.column).append(SPACE);
        sb.append(this.operator).append(SPACE);

        if (this.value == null) {
            sb.append(QUESTION_MARK);
        } else {
            sb.append(this.value);
        }
    }
}
