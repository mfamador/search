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
import org.jfree.report.function.AbstractExpression;

/**
 * Calcula a subtracão entre duas colunas.
 * <p/>
 * A funcão aceita o parâmetro <code>minuend</code> e o parâmetro <code>subtrahend</code>.
 * <p/>
 * Fórmula:
 * <pre>
 * c (minuend) - b (subtrahend) = a (difference)</code>
 * </pre>
 *
 */
public class ColumnSubractionExpression
    extends AbstractExpression 
    implements Serializable {

    private String minuend;
    private String subtrahend;

    public ColumnSubractionExpression() {}


    public String getMinuend() {
        return this.minuend;
    }

    public void setMinuend(final String minuend) {
        this.minuend = minuend;
    }

    public String getSubtrahend() {
        return this.subtrahend;
    }

    public void setSubtrahend(final String subtrahend) {
        this.subtrahend = subtrahend;
    }

    /**
     * Retorna o valor da diferenca.
     */
    public Object getValue() {
        Object minuendValue = getDataRow().get(this.minuend);
        Object subtrahendValue = getDataRow().get(this.subtrahend);
        if (minuendValue instanceof Number == false || 
            subtrahendValue instanceof Number == false) {
            return null;
        }

        double a = ((Number)minuendValue).doubleValue();
        double b = ((Number)subtrahendValue).doubleValue();
        
        return new Double(a - b);
    }
}
