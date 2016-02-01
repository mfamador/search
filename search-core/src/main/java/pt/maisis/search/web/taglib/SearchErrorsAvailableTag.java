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
package pt.maisis.search.web.taglib;

import pt.maisis.search.validator.ValidationErrors;

import javax.servlet.jsp.JspException;

/**
 * Faz o rendering do corpo da tag caso tenha ocorrido algum erro no 
 * pedido de pesquisa. Ou seja, se existir no <code>request</code> 
 * o atributo {@link SearchBaseServlet.ERRORS} com uma instância de
 * {@link SearchErrors} com erros.
 * 
 * @version 1.0
 *
 * @see pt.maisis.search.web.SearchErrorsTag
 */
public class SearchErrorsAvailableTag extends SearchBaseTag {

    @Override
    public int doStartTag() throws JspException {
        ValidationErrors errors = getValidationErrors();
        if (errors == null || errors.isEmpty()) {
            return SKIP_BODY;
        }
        return EVAL_BODY_INCLUDE;
    }
}
