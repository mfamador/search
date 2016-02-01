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

import pt.maisis.search.Result;
import pt.maisis.search.SearchResult;
import pt.maisis.search.PageableSearchResult;
import pt.maisis.search.SearchResultElement;
import pt.maisis.search.RequiredResultParameterMetaData;
import pt.maisis.search.util.MessageResources;

import java.util.List;
import java.util.Iterator;
import javax.servlet.jsp.JspException;

/**
 * @version 1.0
 */
public class SearchResultBodyTag extends TagSupport {

    public static final String INDEX = "index";
    private int index;
    private Result result;
    private Iterator elements;
    private SearchResultElement element;
    private List rrpmds;

    public SearchResultBodyTag() {
    }

    @Override
    public int doStartTag() throws JspException {

        SearchResultTag parent = (SearchResultTag) findAncestorWithClass(this, SearchResultTag.class);

        if (parent == null) {
            throw new JspException(MessageResources.getInstance().getMessage("tag.invalid.parent"));
        }

        this.result = parent.getResult();
        this.rrpmds = result.getRequiredResultMetaData();
        this.elements = result.getSearchResult().getElements().iterator();

        // se o resultado for do tipo paginável é necessário iniciar 
        // a var index com o número do primerio registo da página.
        if (this.result.isSearchResultPageable()) {
            SearchResult sr = this.result.getSearchResult();
            this.index = ((PageableSearchResult) sr).getStart() + 1;
        } else {
            this.index = 1;
        }

        if (this.elements.hasNext()) {
            this.element = (SearchResultElement) this.elements.next();
            setRequiredParameters();
            setIndex();
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }

    @Override
    public int doAfterBody() throws JspException {
        if (this.elements.hasNext()) {
            this.element = (SearchResultElement) this.elements.next();
            setRequiredParameters();
            setIndex();
            return EVAL_BODY_AGAIN;
        }
        return SKIP_BODY;
    }

    /**
     * Coloca no contexto da página todos os required result
     * params.
     */
    protected void setRequiredParameters() {
        SearchResult sr = this.result.getSearchResult();
        Iterator it = this.rrpmds.iterator();
        while (it.hasNext()) {
            RequiredResultParameterMetaData rrpmd = (RequiredResultParameterMetaData) it.next();

            String name = rrpmd.getName();
            int index = sr.getIndex(name);
            Object value = this.element.getValue(index);
            if (value == null) {
                removeAttribute(name);
            } else {
                setAttribute(name, value);
            }
        }
    }

    protected void setIndex() {
        setAttribute(INDEX, this.index++);
    }

    public SearchResultElement getSearchResultElement() {
        return this.element;
    }

    public SearchResult getSearchResult() {
        return this.result.getSearchResult();
    }

    public Result getResult() {
        return this.result;
    }
}
