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
package pt.maisis.search.web;

import java.util.Map;
import java.util.HashMap;

/**
 * Classe que representa uma pesquisa web.
 *
 * @version 1.0
 *
 * @see WebSearchMetaData
 */
public class WebSearch {

    public static final String REQUEST_SCOPE = "request";
    public static final String SESSION_SCOPE = "session";
    private String name;
    private String searchView;
    private String resultView;
    private String searchFormName;
    private String searchFormScope;
    private String searchFormClass;
    private String searchResultName;
    private String searchResultScope;
    private int count;
    private int resultRecordLimit;
    private int queryTimeout;
    private boolean selectAllResultParams;
    private boolean hideDuplicatedResultParams;
    private boolean hideDuplicatedHtmlResultParams;
    private String exporterFilenamePrefix;
    private Boolean exporterCompression;
    private Integer exporterCompressionRecord;
    // TODO: ter um map por pesquisa não é eficiente em termos
    // de memória. Deve-se passar a utilizar o mesmo map para 
    // todas as pesquisas. Utilizar uma chave composta (nome 
    // pesquisa + format).
    private Map views;

    public WebSearch() {
    }

    /**
     * Retorna o nome da pesquisa.
     */
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Retorna o nome da view (página jsp ou html, etc..)
     * que permite ao utilizador efectuar uma pesquisa.
     */
    public String getSearchView() {
        return this.searchView;
    }

    public void setSearchView(final String searchView) {
        this.searchView = searchView;
    }

    /**
     * Retorna o nome da view (página jsp ou html, etc..)
     * que apresenta o resultado de uma pesquisa.
     */
    public String getResultView() {
        return this.resultView;
    }

    public void setResultView(final String resultView) {
        this.resultView = resultView;
    }

    public String getSearchFormName() {
        if (this.searchFormName == null) {
            this.searchFormName = this.name + "Form";
        }

        return this.searchFormName;
    }

    public void setSearchFormName(final String searchFormName) {
        this.searchFormName = searchFormName;
    }

    /**
     * Retorna o scope (request ou session) em que os
     * parâmetros de pesquisa devem ser colocados.
     */
    public String getSearchFormScope() {
        return this.searchFormScope;
    }

    public void setSearchFormScope(final String searchFormScope) {
        this.searchFormScope = searchFormScope;
    }

    public String getSearchFormClass() {
        return this.searchFormClass;
    }

    public void setSearchFormClass(final String searchFormClass) {
        this.searchFormClass = searchFormClass;
    }

    /**
     * Retorna o nome do atributo em que o resultado da pesquisa 
     * é armazenado.
     */
    public String getSearchResultName() {
        return this.searchResultName;
    }

    public void setSearchResultName(final String searchResultName) {
        this.searchResultName = searchResultName;
    }

    /**
     * Retorna o scope (request ou session) em que o
     * resultado da pesquisa deve ser colocado.
     */
    public String getSearchResultScope() {
        return this.searchResultScope;
    }

    public void setSearchResultScope(final String searchResultScope) {
        this.searchResultScope = searchResultScope;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public int getResultRecordLimit() {
        return this.resultRecordLimit;
    }

    public void setResultRecordLimit(final int resultRecordLimit) {
        this.resultRecordLimit = resultRecordLimit;
    }

    public int getQueryTimeout() {
        return this.queryTimeout;
    }

    public void setQueryTimeout(final int queryTimeout) {
        this.queryTimeout = queryTimeout;
    }

    public void setViews(final Map views) {
        this.views = views;
    }

    public boolean isSelectAllResultParams() {
        return selectAllResultParams;
    }

    public void setSelectAllResultParams(final boolean selectAllResultParams) {
        this.selectAllResultParams = selectAllResultParams;
    }

    public boolean isHideDuplicatedResultParams() {
        return hideDuplicatedResultParams;
    }

    public void setHideDuplicatedResultParams(final boolean hideDuplicatedResultParams) {
        this.hideDuplicatedResultParams = hideDuplicatedResultParams;
    }

    public boolean isHideDuplicatedHtmlResultParams() {
        return hideDuplicatedHtmlResultParams;
    }

    public void setHideDuplicatedHtmlResultParams(final boolean hideDuplicatedHtmlResultParams) {
        this.hideDuplicatedHtmlResultParams = hideDuplicatedHtmlResultParams;
    }

    public String getExporterFilenamePrefix() {
        return this.exporterFilenamePrefix;
    }

    public void setExporterFilenamePrefix(final String exporterFilenamePrefix) {
        this.exporterFilenamePrefix = exporterFilenamePrefix;
    }

    public Boolean isExporterCompression() {
        return this.exporterCompression;
    }

    public void setExporterCompression(final Boolean exporterCompression) {
        this.exporterCompression = exporterCompression;
    }

    public Integer getExporterCompressionRecord() {
        return this.exporterCompressionRecord;
    }

    public void setExporterCompressionRecord(final Integer exporterCompressionRecord) {
        this.exporterCompressionRecord = exporterCompressionRecord;
    }

    public Map getViews() {
        return this.views;
    }

    public void addResultView(String format, String name) {
        // não é necessário ser thread safe (só existe um configurador).
        if (views == null) {
            this.views = new HashMap();
        }

        views.put(format, name);
    }

    public String getResultView(String format) {
        String view;
        if (format == null
                || this.views == null
                || (view = (String) this.views.get(format)) == null) {
            return this.resultView;
        }
        return view;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("WebSearch[").
                append("name=").
                append(getName()).
                append(", searchView=").
                append(getSearchView()).
                append(", resultView=").
                append(getResultView()).
                append(", searchFormName=").
                append(getSearchFormName()).
                append(", searchFormScope=").
                append(getSearchFormScope()).
                append(", searchFormClass=").
                append(getSearchFormClass()).
                append(", searchResultName=").
                append(getSearchResultName()).
                append(", searchResultScope=").
                append(getSearchResultScope()).
                append("]");
        return sb.toString();
    }
}
