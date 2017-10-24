package pt.maisis.search.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.ajaxtags.servlets.BaseAjaxServlet;
import org.ajaxtags.xml.AjaxXmlBuilder;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.SearchEngine;
import pt.maisis.search.SearchMetaData;
import pt.maisis.search.SearchParameterMetaData;
import pt.maisis.search.config.SearchConfig;
import pt.maisis.search.util.DBUtils;
import pt.maisis.search.util.ServiceLocator;
import pt.maisis.search.value.KeyValue;
import pt.maisis.search.value.SqlValue;

/**
 *
 * @version 1.0
 */
public class AjaxSearchServlet extends BaseAjaxServlet {

    private static Log log = LogFactory.getLog(AjaxSearchServlet.class);

    public String getXmlContent(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        AjaxParameterForm form = new AjaxParameterForm(request, response);
        Map map = request.getParameterMap();
        try {
            populateForm(form, map);
        }
        catch (Exception e) {
        }

        SearchEngine se = SearchEngine.getInstance();
        SearchMetaData smd = se.getSearch(form.getSearch());
        SearchParameterMetaData spd = smd.getSearchParameter(form.getSearchParam());


        String defaultStatement = "";
        if (spd.getContainer().getDefaultValue() instanceof SqlValue) {
            defaultStatement = ( (SqlValue) spd.getContainer().getDefaultValue() ).getStatement();
        }

        List lista = null;
        lista = getList(spd.getContainer().getAjaxValue().getDataSource(),
                spd.getContainer().getAjaxValue().getStatement(),
                defaultStatement,
                form.getParams(), form.getOper());

        response.setHeader("Cache-Control", "no-cache");
        AjaxXmlBuilder builder = new AjaxXmlBuilder();
        builder.addItemAsCData("", "");
        if (lista != null) {
            Iterator it = lista.iterator();
            while (it.hasNext()) {
                KeyValue object = (KeyValue) it.next();
                builder.addItemAsCData(object.getValue(), object.getKey());
            }
        }
        return builder.toString();
    }

    protected void populateForm(AjaxParameterForm form, Map params)
            throws ServletException {
        try {
            BeanUtils.populate(form, params);
        }
        catch (Exception e) {
            log.error(e);
            throw new ServletException(e);
        }
    }

    public List getList(String datasource,
            String statement,
            String defaultStatement,
            String[] param,
            String oper) {

        if (log.isDebugEnabled()) {
            log.debug("datasource:" + datasource);
            log.debug("statement:" + statement);
            log.debug("defaultStatement:" + defaultStatement);
            log.debug("oper:" + oper);
            if (param != null) {
                log.debug("parameters:" + param.length);
            }
        }

        boolean hasParameters = false;
        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                if (param[i].length() > 0) {
                    hasParameters = true;
                }
            }

            for (int i = 0; i < param.length; i++) {
                if (param[i].length() > 0) {
                    String prm = param[i];
                    if (statement.indexOf('#') >= 0) {
                        String params = "?";
                        String[] temp = null;
                        temp = prm.split(",");
                        for (int j = 1; j < temp.length; j++) {
                            params += ",?";
                        }
                        statement = statement.replaceFirst("#", params);
                    }
                } else {
                    statement = statement.replaceFirst("#", "");
                }
            }
            
            log.debug("final statement:" + statement);
        } else {
            return null;
        }

        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = getConnection(datasource);

            if (hasParameters || defaultStatement.length() == 0) {
                if (hasParameters) {
                    ps = DBUtils.prepareStatement(c, statement);
                } else {
                    return null;
                }
            } else {
                ps = DBUtils.prepareStatement(c, defaultStatement);
            }

            if (param != null && param.length > 0) {
                int contador = 0;
                for (int i = 0; i < param.length; i++) {
                    if (param[i].length() > 0) {
                        String prm = param[i];
                        if ("like".equals(oper)) {
                            prm = "%" + prm + "%";
                        }
                        if (prm.indexOf(',') >= 0) {
                            String[] temp = null;
                            temp = prm.split(",");
                            for (int j = 0; j < temp.length; j++) {
                                log.debug("set parameter 1:" + temp[j]);
                                ps.setString(++contador, temp[j]);
                            }
                        } else {
                            log.debug("set parameter 2:" + prm);
                            ps.setString(++contador, prm);
                        }
                    }
                }
            }
            rs = ps.executeQuery();
            List list = new ArrayList();
            while (rs.next()) {
                String key = rs.getString(1);
                String value = rs.getString(2);
                log.debug("key:" + key);
                log.debug("value:" + value);
                KeyValue keyValue = new KeyValue(key, value);
                list.add(keyValue);
            }
            return list;
        }
        catch (SQLException e) {
            log.error(e);
            return null;
        }
        finally {
            DBUtils.close(rs, ps, c);
        }
    }

    private Connection getConnection(String datasource) {
        try {
            if (datasource == null) {
                SearchConfig config = SearchConfig.getInstance();
                datasource = config.getDataSourceName();
            }
            ServiceLocator sl = ServiceLocator.getInstance();
            DataSource ds = (DataSource) sl.getDataSource(datasource);
            return ds.getConnection();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
