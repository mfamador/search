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
package pt.maisis.search.util;

import java.sql.Date;
import java.sql.Timestamp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.SearchParameter;

/**
 *
 * @author dconceicao
 */
public class OracleSearchSqlTool extends SearchSqlTool {

    private static Log log = LogFactory.getLog(OracleSearchSqlTool.class);

    public OracleSearchSqlTool() {
    }

    public String getCriteria(final SearchParameter param) {
        StringBuffer sb = new StringBuffer();

        if ("regexp_like".equalsIgnoreCase(param.getOperator())) {
            sb.append(param.getOperator()).append(" (");
            sb.append(' ').append(param.getField()).append(",");
        } else {
            sb.append(param.getField());
            sb.append(' ').append(param.getOperator());
        }


        if (param.getValue() instanceof Timestamp) {
            sb.append(' ').append("to_date(" + getQuestionMark(param) + ", 'yyyy-mm-dd hh24:mi:ss')");
        } else if (param.getValue() instanceof Date) {
            sb.append(' ').append("to_date(" + getQuestionMark(param) + ", 'yyyy-mm-dd hh24:mi:ss')");
        } else if (param.getValue() instanceof Timestamp[]) {
            Timestamp[] array = (Timestamp[]) param.getValue();
            sb.append("(");
            for (int i = 0; i < array.length; i++) {
                sb.append(' ').append("to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
                if (i < ( array.length - 1 )) {
                    sb.append(",");
                }
            }
            sb.append(")");
        } else {
            sb.append(' ').append(getQuestionMark(param));
        }

        if ("regexp_like".equalsIgnoreCase(param.getOperator())) {
            sb.append(")");
        }
        return sb.toString();
    }
}
