package pt.maisis.search.dao.db.util;

public class BetweenCriteria extends Criteria {

    protected static final String OPERATOR = "between";
    private final String column;
    private final Object[] values;

    public BetweenCriteria(final String column) {
        this(column, null);
    }

    public BetweenCriteria(final String column,
            final Object values) {
        this(column, (Object[]) values);
    }

    public BetweenCriteria(final String column,
            final Object[] values) {
        this.column = column;
        this.values = values;
    }

    public void write(StringBuffer sb) {
        sb.append(this.column).append(SPACE);
        sb.append(OPERATOR).append(SPACE);

        if (this.values == null) {
            sb.append(QUESTION_MARK).append(SPACE);
            sb.append(AND).append(SPACE).append(QUESTION_MARK);
        } else {
            sb.append(this.values[0]).append(SPACE);
            sb.append(AND).append(SPACE).append(this.values[1]);
        }
    }
}
