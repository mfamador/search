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
import pt.maisis.search.PageableSearchResult;

import javax.servlet.jsp.JspException;

public class PageableSearchResultTag extends SearchBaseTag {

    /** Índice do primeiro resultado. */
    public static final String START = "start";
    /** Número de resultados por página. */
    public static final String COUNT = "count";
    /** Número do primeiro resultado da página actual. */
    public static final String BEGIN = "begin";
    /** Número do último resultado da página actual. */
    public static final String END = "end";
    /** Número total de resultados. */
    public static final String TOTAL = "total";
    /** Número total de páginas. */
    public static final String TOTAL_PAGES = "totalPages";
    /** Página seleccionada. */
    public static final String SELECTED_PAGE = "selectedPage";
    public static final String PREVIOUS = "previous";
    public static final String NEXT = "next";
    /**
     * URL que é criado e colocado no pageContext com o único 
     * objectivo de facilitar no processo de criacão do layout 
     * da view.<p/>
     *
     * Por exemplo, para apresentar um nova página de resultados
     * é necessário construir um url que contenha uma série de 
     * parâmetros HTTP (search, start, total, etc). <br/>
     *
     * Utilizando este atributo, deixa de ser necessário a criacão
     * dinâmica do url.
     */
    public static final String URL = "url";
    private int numberVisiblePages = Integer.MAX_VALUE;
    private int numberPages;
    private int currentPage;
    private int startPage;
    private int start;
    private int end;
    private int total;
    private int count;

    public void setNumberVisiblePages(final int numberVisiblePages) {
        this.numberVisiblePages = numberVisiblePages;
    }

    public int getNumberVisiblePages() {
        return this.numberVisiblePages;
    }

    public int getNumberPages() {
        return this.numberPages;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public int getStartPage() {
        return this.startPage;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public int getTotal() {
        return this.total;
    }

    public int getCount() {
        return this.count;
    }

    public int doStartTag() throws JspException {
        Result result = getResult();
        if (result == null
                || result.getSearchResult().getResultCount() == 0
                || !result.isSearchResultPageable()) {
            return SKIP_BODY;
        }

        PageableSearchResult sr = (PageableSearchResult) result.getSearchResult();

        this.start = sr.getStart() + 1;
        this.end = sr.getEnd();
        this.total = sr.getTotal();
        this.count = sr.getCount();
        this.numberPages = sr.getTotalPages();
        this.currentPage = sr.getCurrentPage() + 1;
        this.startPage = calculateStartPage();

        setAttribute(BEGIN, start);
        setAttribute(END, end);
        setAttribute(TOTAL, total);
        setAttribute(COUNT, count);
        setAttribute(TOTAL_PAGES, this.numberPages);
        setAttribute(SELECTED_PAGE, this.currentPage);

        return EVAL_BODY_INCLUDE;
    }

    public String getURL(String refreshCount, final int start) {

        return getURL("search", refreshCount, start);
    }

    public String getURL(String action, String refreshCount, final int start) {
        StringBuffer sb =
                new StringBuffer();

        if (action == null || action.length() == 0) {
            action = "search";
        }

        sb.append(action).append("?reset=false").append("&search=").append(getSearch()).append("&count=").append(this.count).append("&start=").append(start); //.append("&selectAllResultParams=").append(getSearchForm().isSelectAllResultParams());

        if (!"true".equals(refreshCount)) {
            sb.append("&total=").append(this.total);
        }

        return sb.toString();
    }

    public String getURL(final int start) {
        return getURL("false", start);
    }

    private int calculateStartPage() {
        int group =
                this.currentPage / this.numberVisiblePages
                + Math.min(this.currentPage % this.numberVisiblePages, 1);

        return group * this.numberVisiblePages - this.numberVisiblePages + 1;
    }
}
