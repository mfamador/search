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

import pt.maisis.search.web.SearchForm;

import javax.servlet.jsp.JspException;

public class OrderTypeTag extends SearchBaseTag {

    /** Atributo que  colocado no contexto da tag e que identifica
    o tipo de ordenacao seleccionado. */
    public static final String ORDER = "order";

    public int doStartTag() throws JspException {

        SearchForm form = getSearchForm();
        if (form == null) {
            return SKIP_BODY;
        }

        int orderType = form.getOrderType();
        setAttribute(ORDER, orderType);

        return EVAL_BODY_INCLUDE;
    }
}
