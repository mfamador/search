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
package pt.maisis.search.format;

import java.util.Locale;

/**
 * Formatador que se limita a retornar o valor de um dado objecto
 * no formato string (toString()).
 * <br/>
 * Este formatador é utilizado pela framework de pesquisa, quando
 * nenhum formatador específico é definido no descriptor da 
 * pesquisa.
 *
 *
 * @version 1.0
 */
public class NullFormatter extends AbstractFormatter {

    public NullFormatter() {
    }

    /**
     * Retorna o resultado da invocacão do método 
     * <code>toString()</code> no dado <code>obj</code>.
     * NOTA: Se o parâmetro <code>obj</code> for uma instância 
     * de <code>String</code>, retorna essa mesma instância.
     *
     * @param obj objecto a formatar.
     * @param locale o locale é simplesmente ignorado.
     * @return toString() do parâmetro <code>obj</code> ou 
     *  null, caso parâmetro seja null.
     */
    public Object format(Object obj, Locale locale) {
        if (obj instanceof String) {
            return (String) obj;
        }
        return (obj == null) ? super.nullValue : obj.toString();
    }
}
