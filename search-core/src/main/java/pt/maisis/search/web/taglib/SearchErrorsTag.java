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
import pt.maisis.search.validator.ValidationError;
import pt.maisis.search.util.ApplicationResources;
import pt.maisis.search.web.LocaleConfig;

import java.util.Locale;
import java.util.Iterator;
import javax.servlet.jsp.JspException;

import javax.servlet.http.HttpServletRequest;

/**
 * Tag que coloca no contexto da página os erros ocorridos
 * no pedido de uma dada pesquisa.
 *
 * @version 1.0
 *
 * @see pt.maisis.search.web.SearchErrorsAvailableTag
 */
public class SearchErrorsTag extends SearchBaseTag {

    public static final String ERROR = "error";
    private Iterator errorsIterator;

    public int doStartTag() throws JspException {
        ValidationErrors errors = getValidationErrors();
        if (errors == null || errors.isEmpty()) {
            return SKIP_BODY;
        }

        this.errorsIterator = errors.getErrors().iterator();
        setError();

        return EVAL_BODY_INCLUDE;
    }

    public int doAfterBody() throws JspException {
        if (this.errorsIterator.hasNext()) {
            setError();
            return EVAL_BODY_AGAIN;
        }

        return SKIP_BODY;
    }

    private void setError() {
        ValidationError error = (ValidationError) this.errorsIterator.next();
        Locale locale = LocaleConfig.getLocale((HttpServletRequest) pageContext.getRequest());
        ApplicationResources messages = ApplicationResources.getInstance(locale);
        String message = messages.getMessage(error.getKey(), error.getValues());
        setAttribute(ERROR, message);
    }
}
