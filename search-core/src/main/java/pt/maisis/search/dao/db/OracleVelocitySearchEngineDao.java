/*
 * %W% %E% Marco Amador
 *
 * Copyright (c) 1994-2011 Maisis - Information Systems. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Maisis
 * Information Systems, Lda. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Maisis.
 *
 * MAISIS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MAISIS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package pt.maisis.search.dao.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import pt.maisis.search.SearchParameter;
import pt.maisis.search.SearchQuery;
import pt.maisis.search.dao.SearchDaoException;
import pt.maisis.search.template.VelocityTemplate;
import pt.maisis.search.util.OracleSearchSqlTool;

/**
 *
 * @version 1.0
 */
public class OracleVelocitySearchEngineDao extends VelocitySearchEngineDao {

    private static Log log = LogFactory.getLog(OracleVelocitySearchEngineDao.class);
    private final OracleSearchSqlTool sqlTool = new OracleSearchSqlTool();

    @Override
    protected String buildCountStatement(final SearchQuery query) {
        VelocityContext context = new VelocityContext();
        context.put(QUERY_TYPE, COUNT);
        context.put(RESULT_PARAMS, query.getResultParams());

        List sp = query.getSearchParams();
        if (!sp.isEmpty()) {
            context.put(SEARCH_PARAMS, sp);
        }

        context.put(SQL_TOOL, sqlTool);
        setAllSearchParams(context, query.getSearchParams());

        VelocityTemplate velocity = VelocityTemplate.getInstance();
        return velocity.evaluate(context, query.getSqlTemplate());
    }

    @Override
    protected String buildStatement(final SearchQuery query) {

        VelocityContext context = new VelocityContext();
        context.put(QUERY_TYPE, GET);
        context.put(RESULT_PARAMS, query.getResultParams());

        List sp = query.getSearchParams();
        if (!sp.isEmpty()) {
            context.put(SEARCH_PARAMS, sp);
        }

        List op = query.getOrderParams();
        if (!op.isEmpty()) {
            context.put(ORDER_PARAMS, op);
        }

        context.put(SQL_TOOL, sqlTool);

        setAllSearchParams(context, query.getSearchParams());

        VelocityTemplate velocity = VelocityTemplate.getInstance();
        return velocity.evaluate(context, query.getSqlTemplate());
    }

    private void setAllSearchParams(final VelocityContext context,
            final List searchParams) {
        for (Iterator it = searchParams.iterator(); it.hasNext();) {
            SearchParameter sp = (SearchParameter) it.next();
            context.put(sp.getName(), sp.getValue());
        }
    }

    @Override
    protected void setValues(final SearchQuery query, final PreparedStatement ps)
            throws SQLException, SearchDaoException {

        Iterator it = query.getSearchParams().iterator();

        int i = 1;

        while (it.hasNext()) {
            SearchParameter sp = (SearchParameter) it.next();

            if (!( sp.getValue() instanceof Timestamp ) && !( sp.getValue() instanceof Timestamp[] )) {
                if (sp.getValue().getClass().isArray()) {
                    Object[] values = (Object[]) sp.getValue();

                    for (int j = 0; j < values.length; j++) {
                        setValue(ps, j + i, values[j]);
                    }

                    i += ( values.length - 1 );
                } else {
                    setValue(ps, i, sp.getValue());
                }
            } else {
                if (sp.getValue() instanceof Timestamp) {
                    setValue(ps, i, sp.getValue());
                } else {
                    Timestamp[] values = (Timestamp[]) sp.getValue();
                    for (int j = 0; j < values.length; j++) {
                        setValue(ps, j + i, values[j]);
                    }
                    i += ( values.length - 1 );
                }
            }
            ++i;
        }
    }

    @Override
    protected void setValue(final PreparedStatement ps,
            final int index,
            final Object value)
            throws SQLException {

        String val = value.toString();
        if (value instanceof Timestamp) {
            Timestamp ts = (Timestamp) value;
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            val = dateFormat.format(ts);
        } else if (value instanceof Date) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            val = dateFormat.format((Date) value);
        }

        if (log.isDebugEnabled()) {
            log.debug("[" + index + "]: " + val);
        }
        ps.setObject(index, val);
    }
}
