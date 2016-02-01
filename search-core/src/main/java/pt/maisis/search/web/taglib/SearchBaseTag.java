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

import pt.maisis.search.config.WebSearchMetaDataConfig;
import pt.maisis.search.web.WebSearchMetaData;
import pt.maisis.search.web.WebSearch;
import pt.maisis.search.web.SearchForm;
import pt.maisis.search.validator.ValidationErrors;
import pt.maisis.search.web.SearchBaseServlet;

import pt.maisis.search.SearchEngine;
import pt.maisis.search.SearchMetaData;
import pt.maisis.search.Result;

import javax.servlet.jsp.PageContext;

/**
 * Base tag utilizada pelas restantes tags da framework
 * de pesquisa.
 *
 * @version 1.0
 */
public class SearchBaseTag extends TagSupport {

    protected String search;

    public SearchBaseTag() {
    }

    public WebSearchMetaData getWebSearchMetaData() {
        WebSearchMetaDataConfig config = WebSearchMetaDataConfig.getInstance();
        return config.getWebSearchMetaData();
    }

    public WebSearch getWebSearch() {
        WebSearchMetaData wsmd = getWebSearchMetaData();
        return wsmd.getSearch(this.search);
    }

    /**
     * Retorna a instância de {@link SearchEngine}.
     */
    public SearchEngine getSearchEngine() {
        return SearchEngine.getInstance();
    }

    /**
     * Retorna a <code>SearchMetaData</code> para a pesquisa 
     * atribuida através do método {@link #setSearch}.
     */
    public SearchMetaData getSearchMetaData() {
        return getSearchEngine().getSearch(this.search);
    }

    /**
     * Retorna a <code>SearchForm</a> utilizada na pesquisa.
     */
    public SearchForm getSearchForm() {
        WebSearch webSearch = getWebSearch();

        String formName = webSearch.getSearchFormName();

        // TODO: para já o código que se segue é suficiente. 
        // No entanto, pode não chegar no futuro. Isto é, 
        // o nome da form vai ser gerado automaticamente caso 
        // seja do tipo sessão.
        return (SearchForm) pageContext.findAttribute(formName);
    }

    /**
     * Retorna o resultado da última pesquisa.
     */
    protected Result getResult() {
        WebSearch webSearch = getWebSearch();
        String resultName = webSearch.getSearchResultName();
        return (Result) pageContext.findAttribute(resultName);
    }

    protected ValidationErrors getValidationErrors() {
        return (ValidationErrors) pageContext.getAttribute(SearchBaseServlet.ERRORS, PageContext.REQUEST_SCOPE);
    }

    /**
     * Atribui o nome da pesquisa.
     */
    public void setSearch(final String search) {
        this.search = search;
    }

    /**
     * Retorna o nome da pesquisa.
     */
    public String getSearch() {
        return this.search;
    }

    public boolean isSearchAvailable() {
        return (this.search == null || this.search.trim().length() == 0)
                ? false : true;
    }
}
