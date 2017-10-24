package pt.maisis.search.value;

import pt.maisis.search.util.ServiceLocator;
import pt.maisis.search.util.DBUtils;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * TODO: permitir cachar resultados do select!!!
 */
public class SqlValue implements ListValue {

    private String datasource;
    private String statement;
    private int keyIndex = 1;
    private int valueIndex = 2;
    private String[] selected;

    public SqlValue() {
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

    public void setKeyIndex(final int keyIndex) {
        this.keyIndex = keyIndex;
    }

    public void setValueIndex(final int valueIndex) {
        this.valueIndex = valueIndex;
    }

    public void setSelected(final String[] selected) {
        this.selected = selected;
    }

    public void setSelected(final List selected) {
        this.selected = new String[selected.size()];
        selected.toArray(this.selected);
    }

    public Object getValue(final Map context) {
        return this.selected;
    }

    public Object getValue() {
        return this.selected;
    }

    public List getList() {
        return getList(this.selected);
    }

    public List getList(final String selected) {
        return getList(new String[]{selected});
    }

    public List getList(final String[] selected) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = getConnection();
            ps = DBUtils.prepareStatement(c, this.statement);
            rs = ps.executeQuery();

            List list = new ArrayList();
            while (rs.next()) {
                String key = rs.getString(this.keyIndex);
                String value = rs.getString(this.valueIndex);

                boolean selectKey = false;
                if (selected != null) {
                    for (int i = 0; i < selected.length; i++) {
                        if (key != null && key.equals(selected[i])) {
                            selectKey = true;
                            break;
                        }
                    }
                }

                KeyValue keyValue = new KeyValue(key, value, selectKey);
                list.add(keyValue);
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            DBUtils.close(rs, ps, c);
        }
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
