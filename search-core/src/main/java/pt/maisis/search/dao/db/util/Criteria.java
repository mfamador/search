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

public abstract class Criteria {

    protected static final char COMMA = ',';
    protected static final char SPACE = ' ';
    protected static final char QUESTION_MARK = '?';
    protected static final char OPEN_BRACKET = '(';
    protected static final char CLOSE_BRACKET = ')';
    public static final String OR = "or";
    public static final String AND = "and";
    public static final String LIKE = "like";
    public static final String NOT_LIKE = "not like";
    public static final String NOT_IN = "not in";
    public static final String IN = "in";

    public abstract void write(StringBuffer sb);
}
