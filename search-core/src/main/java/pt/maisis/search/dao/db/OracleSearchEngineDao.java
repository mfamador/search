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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import pt.maisis.search.SearchParameter;
import pt.maisis.search.SearchQuery;
import pt.maisis.search.SqlFragmentParameter;
import pt.maisis.search.dao.SearchDaoException;
import pt.maisis.search.dao.db.util.CompositeCriteria;
import pt.maisis.search.dao.db.util.Criteria;
import pt.maisis.search.dao.db.util.OracleSQLBuilder;
import pt.maisis.search.dao.db.util.SQLBuilder;
import pt.maisis.search.dao.db.util.SQLFragmentCriteria;
import pt.maisis.search.dao.db.util.SimpleCriteria;

import java.util.Iterator;

/**
 * Gera o statement sql apropriado para capturar a informacão
 * desejada da base de dados.
 *
 * @version 1.0
 */
public class OracleSearchEngineDao extends SearchEngineDaoImpl {

    public OracleSearchEngineDao() {
    }

    @Override
    protected SQLBuilder createSQLBuilder(final String table) {
        return new OracleSQLBuilder(table);
    }

    @Override
    protected void appendConditions(final SQLBuilder sql,
            final SearchQuery query) {
        Iterator it = query.getSearchParams().iterator();

        while (it.hasNext()) {
            SearchParameter sp = (SearchParameter) it.next();

            appendCondition(sp, sql);
        }

        SqlFragmentParameter sqlFragment = query.getSqlFragmentParam();

        if (sqlFragment != null) {
            sql.addCriteria(new SQLFragmentCriteria(sqlFragment.getValue()));
        }
    }

    protected void appendCondition(final SearchParameter sp,
            final SQLBuilder sql) {
        String field = sp.getField();
        //---------------------------------------------------------------------
        // APARENTEMENTE O DRIVER JDBC DA ORACLE TEM UM PROBLEMA QUALQUER COM
        // OBJECTOS DO TIPO Timestamp, O QUE FAZ COM QUE QUERIES QUE UTILIZEM
        // PARAMETROS DESTE TIPO DEMOREM MUITO TEMPO.
        // ASSIM SENDO, FOI IMPLEMENTADA A SOLUCAO QUE SE SEGUE PARA DAR A
        // VOLTA AO PROBLEMA.
        // ESTE PROBLEMA TEM DE SER MAIS BEM INVESTIGADO. NO ENTANTO, PARA JA
        // ESTA SOLUCAO RESOLVE O PROBLEMA.
        //---------------------------------------------------------------------

        Object value = sp.getValue();

        if (value instanceof Timestamp) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Timestamp timestamp = (Timestamp) value;

            Criteria criteria = new SimpleCriteria(sp.getOperator(),
                    sp.getField(),
                    "to_date('" + dateFormat.format(value)
                    + "', 'yyyy-mm-dd hh24:mi:ss')");

            sql.addCriteria(criteria);
        } else if (value instanceof Timestamp[]) {
            Timestamp[] timestamps = (Timestamp[]) value;

            CompositeCriteria compositeCriteria = new CompositeCriteria(Criteria.OR);

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (int i = 0; i < timestamps.length; i++) {
                Criteria criteria = new SimpleCriteria(sp.getOperator(),
                        sp.getField(),
                        "to_date('" + dateFormat.format(timestamps[i])
                        + "', 'yyyy-mm-dd hh24:mi:ss')");

                compositeCriteria.addCriteria(criteria);
            }

            sql.addCriteria(compositeCriteria);
        } else {
            sql.addCriteria(field, sp.getOperator(), sp.getValue());
        }
    }

    @Override
    protected void setValues(SearchQuery query, PreparedStatement ps)
            throws SQLException, SearchDaoException { // percorrer os valores dos params e fazer o
        // set deles no PreparedStatement.

        Iterator it = query.getSearchParams().iterator();

        int i = 1;

        int currentValue = 0;

        while (it.hasNext()) {
            SearchParameter sp = (SearchParameter) it.next();

            if (!(sp.getValue() instanceof Timestamp)
                    && !(sp.getValue() instanceof Timestamp[])) {
                if (sp.getValue().getClass().isArray()) {
                    Object[] values = (Object[]) sp.getValue();

                    for (int j = 0; j < values.length; j++) {
                        setValue(ps, j + i, values[j]);
                    }

                    i += (values.length - 1);
                } else {
                    setValue(ps, i, sp.getValue());
                }

                ++i;
            }
        }
    }
}
