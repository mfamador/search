package pt.maisis.search.dao.db.util;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Builder de statements SQL.
 * <br>
 * O objectivo desta classe é facilitar a construcão de statements SQL.
 * <br>
 * A ordem pela qual os métodos {@link #addColumn}, {@link #addCriteria},
 * e {@link #addOrder} são invocados não afecta o statement resultante.
 */
public class SQLBuilder {

    private static Log log = LogFactory.getLog(SQLBuilder.class);
    public static String OPERATOR_IN = "in";
    public static String OPERATOR_NOT_IN = "not in";
    public static String OPERATOR_BETWEEN = "between";
    public static String OPERATOR_EQUAL = "=";
    public static String OPERATOR_LIKE = "like";
    public static String OPERATOR_NOT_LIKE = "not like";
    public static String OPERATOR_REGEXP_LIKE = "regexp_like";
    public static String OPERATOR_GREATER = ">";
    public static String OPERATOR_GREATER_OR_EQUAL = ">=";
    public static String OPERATOR_SMALLER = "<";
    public static String OPERATOR_SMALLER_OR_EQUAL = "<=";
    public static String[] VALID_OPERATORS = {
        OPERATOR_IN,
        OPERATOR_NOT_IN,
        OPERATOR_BETWEEN,
        OPERATOR_EQUAL,
        OPERATOR_LIKE,
        OPERATOR_NOT_LIKE,
        OPERATOR_REGEXP_LIKE,
        OPERATOR_GREATER,
        OPERATOR_GREATER_OR_EQUAL,
        OPERATOR_SMALLER,
        OPERATOR_SMALLER_OR_EQUAL
    };
    private Set OPERATORS = new HashSet(Arrays.asList(VALID_OPERATORS));
    protected static final String COMMA = ",";
    protected static final String SQL_SELECT = "select ";
    protected static final String SQL_FROM = " from ";
    protected static final String SQL_WHERE = " where ";
    protected static final String SQL_ORDER_BY = " order by ";
    protected static final String SQL_AND = " and ";
    protected static final String ASCENDING_ORDER = " asc";
    protected static final String DESCENDING_ORDER = " desc";
    public static final int ORDER_TYPE_ASCENDING = 0;
    public static final int ORDER_TYPE_DESCENDING = 1;
    private String table;
    private StringBuffer columns;
    private StringBuffer criteria;
    private StringBuffer order;

    /**
     * Cria uma instância de <code>SQLBuilder</code>.
     * @param table nome da tabela ou view.
     */
    public SQLBuilder(final String table) {
        this.table = table;
    }

    /**
     * Adiciona uma nova coluna ao statement.
     * @param column nome da coluna.
     */
    public void addColumn(final String column) {
        prepareColumn();
        this.columns.append(column);
    }

    /**
     * Adiciona um novo critério ao statement.
     * @param criteria uma instância de <code>Criteria</code>.
     * @see pt.maisis.search.dao.db.util.Criteria
     */
    public void addCriteria(final Criteria criteria) {
        prepareCriteria();
        criteria.write(this.criteria);
    }

    /**
     * Adiciona um novo critério ao statement. Este método é similar
     * ao método {@link #addCriteria(Criteria)}, no entanto, neste
     * caso é criada uma instância de {@link Criteria} em funcão do
     * parâmetro <code>operator</code>.
     * <br>
     * Este método, para além dos parâmetros <code>column</code> e
     * <code>operator</code> contêm também o parâmetro
     * <code>value</code>. Este parâmetro só é necessário em situacões
     * em que o operador em causa é o operador IN. Caso contrário este
     * parâmetro é simplesmente ignorado.
     * <br>
     * Caso exista a necessidade de criar um critério e fazer atribuicão de
     * valores em vez de ?, deve-se utilizar o método
     * {@link #addCriteria(Criteria)}.
     *
     * @param column nome da coluna da tabela ou view
     * @param operator tipo de operador
     * @param value valor da coluna
     */
    public void addCriteria(final String column,
            final String operator,
            final Object value) {

        final String lowerOperator = operator.toLowerCase();
   
        if (OPERATORS.contains(lowerOperator)) {
            if (lowerOperator.equals(OPERATOR_BETWEEN)) {
                addCriteria(new BetweenCriteria(column));
            } else if (lowerOperator.equals(OPERATOR_IN)) {
                if (value == null || !value.getClass().isArray()) {
                    throw new SQLBuilderException("A criacao de um novo criterio utilizando o operador"
                            + " 'IN' precisa de um valor do tipo array");
                } else {
                    addCriteria(new ArrayCriteria(column, ( (Object[]) value ).length));
                }
            } else if (lowerOperator.equals(OPERATOR_REGEXP_LIKE)) {
                addCriteria(new RegexpLikeCriteria(column));
            } else {
                addCriteria(new SimpleCriteria(operator, column));
            }
        } else {
            throw new SQLBuilderException("Operador invalido: " + operator);
        }
    }

    /**
     * Adiciona uma ordenacão ao statement.
     * @param order  tipo de ordenacão.
     * @param column coluna a ordenar.
     */
    public void addOrder(final int order, final String column) {
        switch (order) {
            case ORDER_TYPE_ASCENDING:
                addAscendingOrder(column);
                break;
            case ORDER_TYPE_DESCENDING:
                addDescendingOrder(column);
                break;
        }
    }

    /**
     * Adiciona uma ordenacão do tipo ascendente ao statement.
     * @param column coluna a ordenar de forma ascendente.
     */
    public void addAscendingOrder(final String column) {
        prepareOrder();
        this.order.append(column).append(ASCENDING_ORDER);
    }

    /**
     * Adiciona uma ordenacão do tipo descendente ao statement.
     * @param column coluna a ordenar de forma descendente.
     */
    public void addDescendingOrder(final String column) {
        prepareOrder();
        this.order.append(column).append(DESCENDING_ORDER);
    }

    /*---------- helpers ----------*/
    private void prepareOrder() {
        if (this.order == null) {
            this.order = new StringBuffer();
        } else {
            this.order.append(COMMA);
        }
    }

    private void prepareCriteria() {
        if (this.criteria == null) {
            this.criteria = new StringBuffer();
        } else {
            this.criteria.append(SQL_AND);
        }
    }

    private void prepareColumn() {
        if (this.columns == null) {
            this.columns = new StringBuffer();
        } else {
            this.columns.append(COMMA);
        }
    }

    public String getStatement() {
        StringBuffer sql = new StringBuffer();
        sql.append(SQL_SELECT);
        sql.append(getColumns());
        sql.append(SQL_FROM);
        sql.append(getTable());

        if (this.criteria != null) {
            sql.append(SQL_WHERE);
            sql.append(getCriteria());
        }
        if (this.order != null) {
            sql.append(SQL_ORDER_BY);
            sql.append(getOrder());
        }

        return sql.toString();
    }

    protected String getTable() {
        return this.table;
    }

    protected StringBuffer getColumns() {
        return this.columns;
    }

    protected StringBuffer getCriteria() {
        return this.criteria;
    }

    protected StringBuffer getOrder() {
        return this.order;
    }
}
