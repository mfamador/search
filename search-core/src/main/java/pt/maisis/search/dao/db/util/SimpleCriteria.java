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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SimpleCriteria extends Criteria {
   private static Log log = LogFactory.getLog(SimpleCriteria.class);
   
    private final String operator;
    private final String column;
    private final Object value;

    public SimpleCriteria(final String operator,
            final String column) {
        this(operator, column, null);
    }

    public SimpleCriteria(final String operator,
            final String column,
            final Object value) {
        this.operator = operator;
        this.column = column;
        this.value = value;
        
        log.debug("operator: " + operator);
    }

    public void write(StringBuffer sb) {
        sb.append(this.column).append(SPACE);
        sb.append(this.operator).append(SPACE);

        if (this.value == null) {
            sb.append(QUESTION_MARK);
        } else {
            sb.append(this.value);
        }
    }
}
