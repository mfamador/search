package pt.maisis.search.dao.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.OrderParameter;
import pt.maisis.search.PageableSearchResult;
import pt.maisis.search.Parameter;
import pt.maisis.search.ResultParameter;
import pt.maisis.search.SearchParameter;
import pt.maisis.search.SearchQuery;
import pt.maisis.search.SearchResult;
import pt.maisis.search.SearchResultElement;
import pt.maisis.search.SearchResultImpl;
import pt.maisis.search.SearchTooManyResultsException;
import pt.maisis.search.SqlFragmentParameter;
import pt.maisis.search.dao.SearchDaoException;
import pt.maisis.search.dao.SearchEngineDao;
import pt.maisis.search.dao.db.util.SQLBuilder;
import pt.maisis.search.dao.db.util.SQLFragmentCriteria;
import pt.maisis.search.util.DBUtils;
import pt.maisis.search.util.ServiceLocator;
import pt.maisis.search.util.ServiceLocatorException;
import pt.maisis.search.util.StringUtils;
import pt.maisis.search.SearchQueryTimeoutException;

/**
 * Gera o statement sql apropriado para capturar a informacão
 * desejada da base de dados.
 *
 * @version 1.0
 */
public class SearchEngineDaoImpl implements SearchEngineDao {

    private static Log log = LogFactory.getLog(SearchEngineDaoImpl.class);
    /** Constante que identifica a captura de todos os resultados. */
    protected static final int ALL_RESULTS = Integer.MAX_VALUE;

    public SearchEngineDaoImpl() {
    }

    /**
     * {@inheritDoc}
     */
    public SearchResult executeSearch(final SearchQuery query)
            throws SearchDaoException {
        return executeSearch(query, 0, ALL_RESULTS, ALL_RESULTS, false);
    }

    /**
     * {@inheritDoc}
     */
    public SearchResult executeSearch(final SearchQuery query,
            final int start,
            final int count)
            throws SearchDaoException {
        return executeSearch(query, start, count, 0, true);
    }

    /**
     * {@inheritDoc}
     */
    public SearchResult executeSearch(final SearchQuery query,
            final int start,
            final int count,
            final int total)
            throws SearchDaoException {
        return executeSearch(query, start, count, total, true);
    }

    protected SearchResult executeSearch(final SearchQuery query,
            final int start,
            final int count,
            int total,
            final boolean scrollable)
            throws SearchDaoException {

        if (log.isDebugEnabled()) {
            log.debug("[start:" + start
                    + ", count:" + count
                    + ", total:" + total
                    + ", scrollable:" + scrollable
                    + "]");
        }

        if (total == 0) {
            if (( total = countResults(query) ) == 0) {

                if (log.isDebugEnabled()) {
                    log.debug("[total 1:" + total
                            + "]");
                }

                SearchResult sr =
                        new SearchResultImpl(query.getResultParams());

                return ( scrollable )
                        ? new PageableSearchResult(sr, false, start, count, total)
                        : sr;
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("[total 2:" + total
                    + "]");
        }

        return select(query, start, count, total, scrollable);
    }

    protected SearchResult select(final SearchQuery query,
            final int start,
            final int count,
            int total,
            final boolean scrollable) {

        int initial_total = total;
        if (total == Integer.MAX_VALUE && query.getResultRecordLimit() > 0) {
            /*
             * execute count
             */
            total = countResults(query);
        }

        if (total != Integer.MAX_VALUE && query.getResultRecordLimit() > 0 && total > query.getResultRecordLimit()) {
            throw new SearchTooManyResultsException();
        }

        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = buildStatement(query);
            c = getConnection(query.getDataSource());

            if (log.isDebugEnabled()) {
                log.debug("[sql]: "
                        + StringUtils.removeExtraSpacesAndNewLines(sql));
            }

            ps = DBUtils.prepareStatement(c, sql, scrollable);
            ps.setQueryTimeout(query.getQueryTimeout());

            setValues(query, ps);

            long startTime = System.currentTimeMillis();

            if (initial_total == Integer.MAX_VALUE) {
                ps.setFetchSize(Math.min(Math.max(total / 5, 10), 1000));
            }

            rs = ps.executeQuery();

            if (log.isDebugEnabled()) {
                log.debug("[sql query took]: "
                        + ( System.currentTimeMillis() - startTime ) + "ms");
            }

            if (scrollable) {
                if (log.isDebugEnabled()) {
                    log.debug("[total 3 ]: "
                            + total);
                }

                return getResult(rs, query, start, count, total);
            }

            return getResult(rs, query);
        }
        catch (SQLException e) {
            log.error(e);
            if (e.getErrorCode() == 1013) {
                throw new SearchQueryTimeoutException();
            } else {
                throw new SearchDaoException(e);
            }
        }
        finally {
            DBUtils.close(rs, ps, c);
        }
    }

    protected int countResults(final SearchQuery query) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = buildCountStatement(query);

            c = getConnection(query.getDataSource());

            if (log.isDebugEnabled()) {
                log.debug("[sql count]: "
                        + StringUtils.removeExtraSpacesAndNewLines(sql));
            }

            ps = DBUtils.prepareStatement(c, sql);
            ps.setQueryTimeout(query.getQueryTimeout());

            setValues(query, ps);

            long startTime = System.currentTimeMillis();

            rs = ps.executeQuery();

            if (log.isDebugEnabled()) {
                log.debug("[sql count took]: "
                        + ( System.currentTimeMillis() - startTime )
                        + "ms");
            }

            int count = ( rs.next() ) ? rs.getInt(1) : 0;

            if (log.isDebugEnabled()) {
                log.debug("[count]: " + count);
            }

            return count;

        }
        catch (SQLException e) {
            log.error(e);
            if (e.getErrorCode() == 1013) {
                throw new SearchQueryTimeoutException();
            } else {
                throw new SearchDaoException(e);
            }
        }
        finally {
            DBUtils.close(rs, ps, c);
        }
    }

    /**
     * Retorna todos os resultados obtidos da BD.
     * <br>
     * Não é aconselhável utilizar este método em situacões em que
     * o número de resultados é elevado. Nestes casos é preferível
     * utilizar o método
     * {@link #getResult(ResultSet, SearchQuery, int, int, int)}
     */
    protected SearchResult getResult(final ResultSet rs,
            final SearchQuery query)
            throws SQLException {

        SearchResultImpl sr = new SearchResultImpl(query.getResultParams());

        long startTime = 0;
        if (log.isDebugEnabled()) {
            startTime = System.currentTimeMillis();
        }

        while (rs.next()) {
            buildResultElement(sr, rs, query);
        }

        if (log.isDebugEnabled()) {
            log.debug("[scrolling results took]: "
                    + ( System.currentTimeMillis() - startTime )
                    + "ms");
        }
        return sr;
    }

    /**
     * Retorna um subconjunto dos resultados obtidos da BD.
     * @see #getResult(ResultSet, SearchQuery)
     */
    protected SearchResult getResult(final ResultSet rs,
            final SearchQuery query,
            final int start,
            final int count,
            final int total)
            throws SQLException {

        // rs.setFetchSize(count);

        long startTime = 0;
        if (log.isDebugEnabled()) {
            startTime = System.currentTimeMillis();
        }

        int numberOfRecords = count;
        boolean hasNext = false;

        if (start >= 0 && rs.absolute(start + 1)) {
            SearchResultImpl sr = new SearchResultImpl(query.getResultParams());

            do {
                buildResultElement(sr, rs, query);
            } while (( hasNext = rs.next() ) && ( --numberOfRecords > 0 ));

            if (log.isDebugEnabled()) {
                log.debug("[scrolling results took]: "
                        + ( System.currentTimeMillis() - startTime )
                        + "ms");
            }
            return new PageableSearchResult(sr, hasNext, start,
                    count, total);
        }
        return PageableSearchResult.EMPTY_RESULT_SET;
    }

    /**
     * Cria uma nova instância de {@link SearchResultElement} para
     * armazenar todos os valores do registo actual.
     */
    protected void buildResultElement(final SearchResultImpl sr,
            final ResultSet rs,
            final SearchQuery query)
            throws SQLException {

        int colIndex = 1;
        List resultParams = query.getResultParams();
        SearchResultElement sre = sr.createElement();

        Iterator it = resultParams.iterator();
        while (it.hasNext()) {
            ResultParameter rp = (ResultParameter) it.next();
            colIndex = addValue(colIndex, rs, rp, sre);
        }
    }

    /**
     * Adiciona um novo valor ao {@link SearchResultElement}.
     * @return o índice da próxima coluna no <code>ResultSet</code>.
     */
    protected int addValue(int colIndex,
            final ResultSet rs,
            final ResultParameter rp,
            final SearchResultElement sre)
            throws SQLException {

        if (rp.size() > 1) {
            Object[] values = new Object[rp.size()];
            int i = 0;
            for (; i < values.length; i++) {
                Object obj = rs.getObject(colIndex + i);
                values[i] = obj;
            }
            sre.addValue(values);
            return colIndex + i;
        }

        Object obj = rs.getObject(colIndex);
        if (obj != null) {
            if (( rs.getObject(colIndex) ).getClass().getName().contains("oracle.sql.CLOB")) {
                java.sql.Clob clob = rs.getClob(colIndex);
                sre.addValue(clob.getSubString(1, (int) clob.length()));
                return ++colIndex;
            } else if (obj.getClass().getName().equals("java.sql.Date")) {
                obj = rs.getTimestamp(colIndex);
            }
        }

        sre.addValue(obj);
        return ++colIndex;
    }

    protected void setValues(final SearchQuery query,
            final PreparedStatement ps)
            throws SQLException, SearchDaoException {

        // percorrer os valores dos params e fazer o
        // set deles no PreparedStatement.
        Iterator it = query.getSearchParams().iterator();

        int i = 1;
        int currentValue = 0;
        while (it.hasNext()) {
            SearchParameter sp = (SearchParameter) it.next();
            if (sp.getValue().getClass().isArray()) {
                Object[] values = (Object[]) sp.getValue();

                for (int j = 0; j < values.length; j++) {
                    setValue(ps, j + i, values[j]);
                }
                i += values.length - 1;
            } else {
                setValue(ps, i, sp.getValue());
            }
            ++i;
        }
    }

    protected void setValue(final PreparedStatement ps,
            final int index,
            final Object value)
            throws SQLException {
        if (log.isDebugEnabled()) {
            log.debug("[" + index + "]: " + value);
        }

        if (value instanceof java.sql.Date) {
            ps.setDate(index, (java.sql.Date) value);
        } else if (value instanceof java.sql.Timestamp) {
            ps.setTimestamp(index, (java.sql.Timestamp) value);
        } else {
            ps.setObject(index, value);
        }
    }

    protected String buildCountStatement(SearchQuery query) {
        SQLBuilder sql = createSQLBuilder(query.getTableSource());
        sql.addColumn("count(*)");
        appendConditions(sql, query);
        return sql.getStatement();
    }

    protected String buildStatement(final SearchQuery query) {
        SQLBuilder sql = createSQLBuilder(query.getTableSource());
        appendColumns(sql, query);
        appendConditions(sql, query);
        appendOrder(sql, query);
        return sql.getStatement();
    }

    protected SQLBuilder createSQLBuilder(String table) {
        return new SQLBuilder(table);
    }

    protected void appendColumns(final SQLBuilder sql,
            final SearchQuery query) {
        Iterator it = query.getResultParams().iterator();
        while (it.hasNext()) {
            Parameter rp = (Parameter) it.next();
            sql.addColumn(rp.getField());
        }
    }

    protected void appendConditions(final SQLBuilder sql,
            final SearchQuery query) {
        Iterator it = query.getSearchParams().iterator();
        while (it.hasNext()) {
            SearchParameter sp = (SearchParameter) it.next();
            sql.addCriteria(sp.getField(), sp.getOperator(), sp.getValue());
        }

        SqlFragmentParameter sqlFragment = query.getSqlFragmentParam();
        if (sqlFragment != null) {
            sql.addCriteria(new SQLFragmentCriteria(sqlFragment.getValue()));
        }
    }

    protected void appendOrder(final SQLBuilder sql,
            final SearchQuery query) {
        Iterator it = query.getOrderParams().iterator();
        while (it.hasNext()) {
            OrderParameter op = (OrderParameter) it.next();

            int order = ( op.getOrder() == OrderParameter.ASCENDING )
                    ? SQLBuilder.ORDER_TYPE_ASCENDING
                    : SQLBuilder.ORDER_TYPE_DESCENDING;

            if (op.size() > 0) {
                Iterator i = op.getFields().iterator();
                while (i.hasNext()) {
                    sql.addOrder(order, (String) i.next());
                }
            } else {
                sql.addOrder(order, op.getField());
            }
        }
    }

    /**
     * Utiliza o servico {@link pt.maisis.servicelocator.ServiceLocator}
     * para obter uma referência para a datasource desejada.
     */
    protected DataSource getDataSource(final String dataSource)
            throws ServiceLocatorException {
        ServiceLocator sl = ServiceLocator.getInstance();
        return (DataSource) sl.getDataSource(dataSource);
    }

    /**
     * Captura uma conexão da datasource especificada.
     */
    protected Connection getConnection(final String dataSource) {
        try {
            if (log.isDebugEnabled()) {
                log.debug("[dataSource]: " + dataSource);
            }

            return getDataSource(dataSource).getConnection();
        }
        catch (Exception e) {
            log.error(e);
            throw new SearchDaoException(e);
        }
    }
}
