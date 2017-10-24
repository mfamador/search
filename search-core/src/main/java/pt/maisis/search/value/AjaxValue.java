package pt.maisis.search.value;

import pt.maisis.search.util.ServiceLocator;
import pt.maisis.search.AjaxParameterMetaData;

import java.sql.Connection;
import javax.sql.DataSource;

public class AjaxValue {

    private String datasource;
    private String statement;
    private String source;
    private AjaxParameterMetaData parameter;

    public AjaxValue() {
    }

    public void setDataSource(final String datasource) {
        this.datasource = datasource;
    }

    public String getDataSource() {
        return this.datasource;
    }

    public void setStatement(final String statement) {
        this.statement = statement;
    }

    public String getStatement() {
        return this.statement;
    }

    public String getDatasource() {
        return this.datasource;
    }

    public void setDatasource(final String datasource) {
        this.datasource = datasource;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(final String source) {
        this.source = source;
    }

    public void setParameterMetaData(final AjaxParameterMetaData parameter) {
        this.parameter = parameter;
    }

    public AjaxParameterMetaData getParameterMetaData() {
        return parameter;
    }

    private Connection getConnection() {
        try {
            ServiceLocator sl = ServiceLocator.getInstance();
            DataSource ds = (DataSource) sl.getDataSource(this.datasource);
            return ds.getConnection();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
