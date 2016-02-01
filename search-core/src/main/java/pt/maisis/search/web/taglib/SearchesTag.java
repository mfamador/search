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

import pt.maisis.search.SearchEngine;
import pt.maisis.search.SearchMetaData;

import javax.servlet.jsp.JspException;

/**
 * Para cada pesquisa configurada no descriptor search-config.xml 
 * faz o rendering do corpo da tag e coloca a instância de 
 * {@link SearchMetaData} no contexto da tag com o nome 
 * <b>searchMetaData</b>.
 *
 * @version 1.0
 */
public class SearchesTag extends SearchBaseTag {

    /**
     * Nome do atributo que é colocado no contexto da tag e que
     * contém uma instância de {@link pt.maisis.search.SearchMetaData}.
     */
    public static final String SEARCH_METADATA = "searchMetaData";
    /** Metadata de todas as pesquisa configuradas. */
    private SearchMetaData[] smds;
    private SearchMetaData smd;
    /** Índice da pesquisa actual. */
    private int index;
    private String search;

    public SearchesTag() {
    }

    @Override
    public void setSearch(final String search) {
        this.search = search;
    }

    @Override
    public int doStartTag() throws JspException {

        if (isSearchAvailable()) {
            SearchMetaData smd = getSearchMetaData();
            if (smd == null) {
                return SKIP_BODY;
            }
            setAttribute(SEARCH_METADATA, smd);
            return EVAL_BODY_INCLUDE;
        }

        SearchEngine se = SearchEngine.getInstance();

        if (this.search != null) {
            this.smd = se.getSearchSetMetaData().getMetaData(this.search);
            if (smd == null) {
                return SKIP_BODY;
            }

            setAttribute(SEARCH_METADATA, smd);
        } else {
            this.smds = se.getSearchSetMetaData().getAll();

            if (smds.length == 0) {
                return SKIP_BODY;
            }

            this.index = 0;
            setAttribute(SEARCH_METADATA, smds[this.index++]);
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doAfterBody() throws JspException {
        if (this.search != null) {
            return SKIP_BODY;
        }

        if (this.smds != null && this.index < this.smds.length) {
            setAttribute(SEARCH_METADATA, smds[this.index++]);
            return EVAL_BODY_AGAIN;
        }
        return SKIP_BODY;
    }

    public void doFinally() {
        removeAttribute(SEARCH_METADATA);
    }
}
