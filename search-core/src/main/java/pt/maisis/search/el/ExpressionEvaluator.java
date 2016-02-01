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
package pt.maisis.search.el;

import org.apache.commons.jexl.JexlHelper;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;

import java.util.Map;

/**
 * EL...
 *
 * @version 1.0
 */
public class ExpressionEvaluator {

    private ExpressionEvaluator() {
    }

    public static Object evaluate(Map context, String expression) {

        JexlContext jc = JexlHelper.createContext();
        jc.setVars(context);

        Object o = null;
        try {
            Expression e = ExpressionFactory.createExpression(expression);
            return e.evaluate(jc);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
