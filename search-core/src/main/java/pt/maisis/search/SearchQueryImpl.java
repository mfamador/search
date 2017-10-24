package pt.maisis.search;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Simples implementacão do interface {@link SearchQuery}.
 *
 * @version 1.0
 */
public class SearchQueryImpl implements SearchQuery, Serializable {

    private String dataSource;
    private String tableSource;
    private String sqlTemplate;
    private List searchParams = new ArrayList();
    private List resultParams = new ArrayList();
    private List orderParams = new ArrayList();
    private SqlFragmentParameter sqlFragment;
    private int resultRecordLimit;
    private int queryTimeout;

    public SearchQueryImpl() {
    }

    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(final String dataSource) {
        this.dataSource = dataSource;
    }

    public String getTableSource() {
        return this.tableSource;
    }

    public void setTableSource(final String tableSource) {
        this.tableSource = tableSource;
    }

    public String getSqlTemplate() {
        return this.sqlTemplate;
    }

    public void setSqlTemplate(final String sqlTemplate) {
        this.sqlTemplate = sqlTemplate;
    }

    public List getSearchParams() {
        return this.searchParams;
    }

    public void addSearchParam(final SearchParameter sp) {
        this.searchParams.add(sp);
    }

    public List getResultParams() {
        return this.resultParams;
    }

    public void addResultParam(final ResultParameter rp) {
        this.resultParams.add(rp);
    }

    public List getOrderParams() {
        return this.orderParams;
    }

    public void addOrderParam(final OrderParameter op) {
        this.orderParams.add(op);
    }

    public SqlFragmentParameter getSqlFragmentParam() {
        return this.sqlFragment;
    }

    public void setSqlFragmentParam(final SqlFragmentParameter sqlFragment) {
        this.sqlFragment = sqlFragment;
    }

    public int getResultRecordLimit() {
        return this.resultRecordLimit;
    }

    public void setResultRecordLimit(final int resultRecordLimit) {
        this.resultRecordLimit = resultRecordLimit;
    }

    public int getQueryTimeout() {
        return this.queryTimeout;
    }

    public void setQueryTimeout(final int queryTimeout) {
        this.queryTimeout = queryTimeout;
    }
}
