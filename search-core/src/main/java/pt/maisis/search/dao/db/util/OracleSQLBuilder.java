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
