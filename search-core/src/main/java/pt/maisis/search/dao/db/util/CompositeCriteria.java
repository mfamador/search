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

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class CompositeCriteria extends Criteria {

    private final List criterias;
    private final String operator;

    public CompositeCriteria(final String operator) {
        this.criterias = new ArrayList();
        this.operator = operator;
    }

    public void addCriteria(final Criteria criteria) {
        this.criterias.add(criteria);
    }

    public int size() {
        return this.criterias.size();
    }

    public void write(StringBuffer sb) {
        sb.append(OPEN_BRACKET);
        Iterator i = this.criterias.iterator();
        while (i.hasNext()) {
            Criteria criteria = (Criteria) i.next();
            criteria.write(sb);
            if (i.hasNext()) {
                sb.append(SPACE).append(operator).append(SPACE);
            }
        }
        sb.append(CLOSE_BRACKET);
    }
}
