package pt.maisis.search.dao.db.util;

public class NullCriteria extends Criteria {

    private static final String NULLABLE = " is null";
    private final String column;

    public NullCriteria(final String column) {
        this.column = column;
    }

    public void write(StringBuffer sb) {
        sb.append(this.column);
        sb.append(NULLABLE);
    }
}
