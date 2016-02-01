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

public class ArrayCriteria extends Criteria {

    protected static final String DEFAULT_OPERATOR = "in";
    protected static final int IN_SIZE = 256;
    private final String operator;
    private final String column;
    private Object[] values;
    private final int size;

    public ArrayCriteria(final String column,
            final int size) {
        this(DEFAULT_OPERATOR, column, size);
    }

    public ArrayCriteria(final String operator,
            final String column,
            final int size) {
        this.operator = operator;
        this.column = column;
        this.size = size;
    }

    public ArrayCriteria(final String column,
            final Object[] values) {
        this(DEFAULT_OPERATOR, column, values);
    }

    public ArrayCriteria(final String operator,
            final String column,
            final Object[] values) {
        this(operator, column, values.length);
        this.values = values;
    }

    public void write(StringBuffer sb) {
        if (size > 0) {
            sb.append(OPEN_BRACKET);
            for (int i = 0; i < size;) {
                sb.append(column).append(SPACE);
                sb.append(this.operator).append(SPACE);
                sb.append(OPEN_BRACKET).append(getValue(i));
                ++i;
                for (int j = 1; j < IN_SIZE && i < size; j++, i++) {
                    sb.append(COMMA).append(getValue(j));
                }
                sb.append(CLOSE_BRACKET);

                if (i < size) {
                    sb.append(SPACE).append(OR).append(SPACE);
                }
            }
            sb.append(CLOSE_BRACKET);
        }
    }

    private String getValue(int index) {
        if (this.values == null) {
            return "" + QUESTION_MARK;
        }
        return values[index].toString();
    }
}
