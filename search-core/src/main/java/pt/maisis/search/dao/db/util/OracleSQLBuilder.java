package pt.maisis.search.dao.db.util;

public class OracleSQLBuilder extends SQLBuilder {

    private static final String SQL_SELECT = "select /*+ FIRST_ROWS */ ";

    /**
     * Cria uma instância de <code>OracleSQLBuilder</code>.
     * @param table nome da tabela ou view.
     */
    public OracleSQLBuilder(final String table) {
        super(table);
    }

    public String getStatement() {
        StringBuffer sql = new StringBuffer();
        sql.append(SQL_SELECT);
        sql.append(getColumns());
        sql.append(SQL_FROM);
        sql.append(getTable());

        if (getCriteria() != null) {
            sql.append(SQL_WHERE);
            sql.append(getCriteria());
        }
        if (getOrder() != null) {
            sql.append(SQL_ORDER_BY);
            sql.append(getOrder());
        }

        return sql.toString();
    }
}
