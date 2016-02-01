/*
 * %W% %E% Marco Amador
 *
 * Copyright (c) 1994-2011 Maisis - Information Systems. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of MAISIS
 * Information Systems, Lda. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with MAISIS.
 *
 * MAISIS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MAISIS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package pt.maisis.search.export.jfreereport.function;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Fun��o que faz exactamente o mesmo que a funcao, com o mesmo
 * nome, da framework JFreeReport, com excepcao que retorna 
 * <code>null</code> caso o resultado seja um numero inv�lido.
 *
 * @version 1.0
 *
 * @see org.jfree.report.function.PercentageExpression
 */
public class PercentageExpression 
    extends org.jfree.report.function.PercentageExpression
    implements Serializable {

    public PercentageExpression () {}

    public Object getValue () {
        BigDecimal value = (BigDecimal) super.getValue();
//        if (value != null) {
//            if (value.isNaN() || value.isInfinite()) {
//                return null;
//            }
//        }
        return value;
    }
}
