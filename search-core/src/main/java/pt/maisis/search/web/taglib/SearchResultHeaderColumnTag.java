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

import pt.maisis.search.ResultParameterMetaData;
import pt.maisis.search.util.ApplicationResources;
import pt.maisis.search.util.MessageResources;
import pt.maisis.search.web.LocaleConfig;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.jsp.JspException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * Simples tag que faz o set do resultado de uma pesquisa 
 * ({@link pt.maisis.search.SearchResult}) no contexto da página.
 *
 * @version 1.0
 */
public class SearchResultHeaderColumnTag extends TagSupport {

    public static final String RESULT_PARAMETER = "resultParam";
    public static final String ORDERED = "ordered";
    public static final String ORDER_TYPE = "orderType";
    public static final String ROWSPAN = "rowspan";
    public static final String COLSPAN = "colspan";
    public static final String LABEL = "label";
    private SearchResultHeaderTag parent;
    private int maximumDeep;
    private int currentRowIndex;
    private List nextRowList;
    private Iterator currentRowIterator;

    public SearchResultHeaderColumnTag() {
    }

    public int doStartTag() throws JspException {

        this.parent = (SearchResultHeaderTag) findAncestorWithClass(this, SearchResultHeaderTag.class);

        if (this.parent == null) {
            throw new JspException(MessageResources.getInstance().getMessage("tag.invalid.parent"));
        }

        List rpmds = this.parent.getResultMetaData();
        this.maximumDeep = this.parent.getMaximumDeep();
        this.currentRowIndex = this.parent.getCurrentRowIndex();
        this.currentRowIterator = rpmds.iterator();
        this.nextRowList = new ArrayList();
        setAttributes();
        return EVAL_BODY_INCLUDE;
    }

    public int doAfterBody() throws JspException {
        if (this.currentRowIterator.hasNext()) {
            setAttributes();
            return EVAL_BODY_AGAIN;
        }
        this.parent.setResultMetaData(this.nextRowList);
        return SKIP_BODY;
    }

    protected void setAttributes() throws JspException {
        ResultParameterMetaData rpmd =
                (ResultParameterMetaData) this.currentRowIterator.next();

        setAttribute(RESULT_PARAMETER, rpmd);

        Locale locale = LocaleConfig.getLocale((HttpServletRequest) pageContext.getRequest());
        ApplicationResources messages = ApplicationResources.getInstance(locale);
        setAttribute(LABEL, messages.getMessage(rpmd.getResultLabel()));

        removeAttribute(ORDERED);
        removeAttribute(ORDER_TYPE);

        setAttribute(ORDERED, this.parent.isOrdered(rpmd.getName()));
        setAttribute(ORDER_TYPE, parent.getOrderType());

        if (rpmd.isComposite()) {
            removeAttribute(ROWSPAN);
            setAttribute(COLSPAN, rpmd.getChildren().size());
        } else {
            removeAttribute(COLSPAN);
            setAttribute(ROWSPAN, this.maximumDeep - this.currentRowIndex);
        }

        if (rpmd.isComposite()) {
            nextRowList.addAll(rpmd.getChildren());
        }
    }
}
