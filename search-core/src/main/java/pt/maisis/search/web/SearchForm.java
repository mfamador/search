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

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.OrderParameter;
import pt.maisis.search.SearchEngine;
import pt.maisis.search.SearchMetaData;
import pt.maisis.search.util.ArrayUtils;
import pt.maisis.search.validator.ValidationError;
import pt.maisis.search.validator.ValidationErrors;
import pt.maisis.search.validator.ValidationResult;
import pt.maisis.search.validator.Validator;

/**
 * Form que utilizada para receber os parâmetros de pesquisa
 * submitidos num formulário web.<p/>
 *
 * Este parâmetros poderão ser, posteriormente, acedidos através
 * desta form.<p/>
 *
 * A form tem, também, a responsabilidade de validadar os parâmetros
 * de pesquisa {@link #validate}.
 *
 * @version 1.0
 */
public class SearchForm implements Serializable {

    private static Log log = LogFactory.getLog(SearchForm.class);
    /** Identifica a obrigatoriedade de especificar os campos
    de retorno. */
    public static final String ERROR_RESULT_PARAMS_REQUIRED = "error.resultparams.required";
    /** Identifica a ordem dos resultados de forma ascendente. */
    public static final int ASCENDING = OrderParameter.ASCENDING;
    /** Identifica a ordem dos resultados de forma descendente. */
    public static final int DESCENDING = OrderParameter.DESCENDING;
    /** Parâmetros de pesquisa. */
    private final Map searchParams = new LinkedHashMap();
    /** Parâmetros de pesquisa falsos. Podem ser úteis só por
    questões de UI. */
    private final Map fakeParams = new LinkedHashMap();
    /** Campos de retorno disponíveis. */
    private String[] resultParams;
    /** Campos de retorno seleccionados. */
    private String[] selectedResultParams;
    /** Identificador da pesquisa. */
    private String search;
    /** Campos de ordenacão. */
    private String[] resultOrder;
    /** Tipo de ordenacão. */
    private int orderType = OrderParameter.ASCENDING;
    /** Índice do primeiro resultado da pesquisa a considerar. */
    private int start = 0;
    /** Número de resultados por página. */
    private int count;
    /** Número de resultados por pesquisa. */
    private int resultRecordLimit;
    /** Número de segundos limite para executar a query. */
    private int queryTimeout;
    /** Total de resultados. */
    private int total;
    /** Flag que identifica se os resultParams devem ser todos
    retornados no resultado de uma pesquisa. */
    private boolean selectAllResultParams;
    private boolean hideDuplicatedResultParams;
    private boolean hideDuplicatedHtmlResultParams;
    private String exporterFilenamePrefix;
    private Boolean exporterCompression;
    private Integer exporterCompressionRecord;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /*Search Share*/
    private String searchShareReportId;
    private String searchShareReportName;
    private String searchShareReportType;
    private Boolean searchShareSlidingWindow;
    private String[] searchShareEntityId;
    private String searchShareEntityTypeId;
    private String searchSharePermissionId;

    public String getSearchShareReportId() {
        return searchShareReportId;
    }

    public void setSearchShareReportId(String searchShareReportId) {
        this.searchShareReportId = searchShareReportId;
    }

    public String getSearchShareReportName() {
        return searchShareReportName;
    }

    public void setSearchShareReportName(String searchShareReportName) {
        this.searchShareReportName = searchShareReportName;
    }

    public String getSearchShareReportType() {
        return searchShareReportType;
    }

    public void setSearchShareReportType(String searchShareReportType) {
        this.searchShareReportType = searchShareReportType;
    }

    public Boolean getSearchShareSlidingWindow() {
        return searchShareSlidingWindow;
    }

    public void setSearchShareSlidingWindow(Boolean searchShareSlidingWindow) {
        this.searchShareSlidingWindow = searchShareSlidingWindow;
    }
    
    public String[] getSearchShareEntityId() {
        return searchShareEntityId;
    }

    public void setSearchShareEntityId(String[] searchShareEntityId) {
        this.searchShareEntityId = searchShareEntityId;
    }

    public String getSearchShareEntityTypeId() {
        return searchShareEntityTypeId;
    }

    public void setSearchShareEntityTypeId(String searchShareEntityTypeId) {
        this.searchShareEntityTypeId = searchShareEntityTypeId;
    }

    public String getSearchSharePermissionId() {
        return searchSharePermissionId;
    }

    public void setSearchSharePermissionId(String searchSharePermissionId) {
        this.searchSharePermissionId = searchSharePermissionId;
    }
    
    public void setRequest(final HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public void setResponse(final HttpServletResponse response) {
        this.response = response;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public void setSelectAllResultParams(final boolean selectAllResultParams) {
        this.selectAllResultParams = selectAllResultParams;
    }

    public boolean isSelectAllResultParams() {
        return this.selectAllResultParams;
    }

    public void setHideDuplicatedResultParams(final boolean hideDuplicatedResultParams) {
        this.hideDuplicatedResultParams = hideDuplicatedResultParams;
    }

    public boolean isHideDuplicatedResultParams() {
        return this.hideDuplicatedResultParams;
    }

    public void setHideDuplicatedHtmlResultParams(final boolean hideDuplicatedHtmlResultParams) {
        this.hideDuplicatedHtmlResultParams = hideDuplicatedHtmlResultParams;
    }

    public boolean isHideDuplicatedHtmlResultParams() {
        return this.hideDuplicatedHtmlResultParams;
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

    /**
     * Retorna o nome identificador da pesquisa.
     */
    public String getSearch() {
        return this.search;
    }

    /**
     * Atribui o nome identificador da pesquisa.
     */
    public void setSearch(final String search) {
        this.search = search;
    }

    /**
     * Retorna o índice do primeiro resultado a considerar.
     */
    public int getStart() {
        return this.start;
    }

    /**
     * Atribui o índice do primeiro resultado a considerar.
     */
    public void setStart(final int start) {
        this.start = start;
    }

    /**
     * Retorna o número máximo de resultados a retornar pela pesquisa.
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Atribui o número máximo de resultados a retornar pela pesquisa.
     */
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

    /**
     * Retorna o número total de resultados.
     */
    public int getTotal() {
        return this.total;
    }

    /**
     * Atribui o número total de resultados.
     */
    public void setTotal(final int total) {
        this.total = total;
    }

    /**
     * Retorna o tipo de ordenacão dos resultados.
     * @see #ASCENDING
     * @see #DESCENDING
     */
    public int getOrderType() {
        return this.orderType;
    }

    /**
     * Atribui o tipo de ordenacão dos resultados.
     * @see #ASCENDING
     * @see #DESCENDING
     */
    public void setOrderType(final int orderType) {
        this.orderType = orderType;
    }

    /**
     * Retorna os nomes dos campos de retorno a ordenar.
     * O tipo de ordenacão é retornado pelo método
     * {@link #getOrderType}.
     */
    public String[] getResultOrder() {
        return this.resultOrder;
    }

    /**
     * Atribui os nomes dos campos de retorno a ordenar.
     * O tipo de ordenacão é especificado pelo método
     * {@link #setOrderType}.
     */
    public void setResultOrder(final String[] resultOrder) {
        this.resultOrder = resultOrder;
    }

    public Object getSearchParam(final Object name) {
        return this.searchParams.get(name);
    }

    public void setSearchParam(final String name, final Object value) {
        if (value != null) {
            if (value.getClass().isArray()
                    || value instanceof List
                    || StringUtils.isNotBlank((String) value)) {
                this.searchParams.put(name, value);
            }
        }
    }

    public void removeSearchParam(final String name) {
        this.searchParams.remove(name);
    }

    public void removeFakeParam(final String name) {
        this.fakeParams.remove(name);
    }

    public Map getSearchParams() {
        return this.searchParams;
    }

    public String getFakeParam(final String name) {
        return (String) this.fakeParams.get(name);
    }

    public void setFakeParam(final String name, final String value) {
        if (StringUtils.isNotBlank(value)) {
            this.fakeParams.put(name, value);
        }
    }

    public String[] getResultParams() {
        return this.resultParams;
    }

    public void setResultParams(final String[] resultParams) {
        this.resultParams = resultParams;
    }

    /**
     * Retorna os nomes dos result params seleccionados
     * pelo utilizador.
     */
    public String[] getSelectedResultParams() {
        return this.selectedResultParams;
    }

    public void setSelectedResultParams(final String[] selectedResultParams) {
        this.selectedResultParams = selectedResultParams;
    }

    /**
     * Método invocado pela {@link SearchBaseServlet}, imediatamente
     * antes de ser feito o <i>populate</i> da form com os parâmetros
     * de pesquisa.<p/>
     *
     * Este método é especialmente útil em situacões em que a form é
     * de sessão.<p/>
     *
     * @see SearchBaseServlet.RESET
     */
    public void reset() {

        this.selectedResultParams = null;
        this.searchParams.clear();
        this.fakeParams.clear();
        this.resultParams = null;
        this.resultOrder = null;
        this.search = null;
        this.orderType = OrderParameter.ASCENDING;
        this.start = 0;
        this.count = 0;
        this.total = 0;
        this.exporterFilenamePrefix = null;
        this.resultRecordLimit = 0;

        /*
         * Search Share
         */
        this.searchShareReportId = null;
        this.searchShareReportName = null;
        this.searchShareReportType = null;
        this.searchShareSlidingWindow = null;
        this.searchShareEntityId = null;
        this.searchShareEntityTypeId = null;
        this.searchSharePermissionId = null;                
    }

    /**
     * Método invocado pela {@link SearchBaseServlet} depois de ser
     * feito o <code>reset</code> e o <code>populate</code> da form.
     */
    public void validate(ValidationResult result, ValidationErrors errors, Locale locale) {

        SearchEngine se = SearchEngine.getInstance();
        SearchMetaData smd = se.getSearch(this.search);

        // validate selected result params
        if (this.selectedResultParams == null && !selectAllResultParams) {
            errors.add(new ValidationError(ERROR_RESULT_PARAMS_REQUIRED));
        }

        // validate
        Validator.getInstance().validate(smd, result, errors, locale);
    }

    public String toString() {
        return new StringBuffer("SearchForm[").append("search=").
                append(this.search).append(", orderType=").
                append(this.orderType).append(", start=").
                append(this.start).append(", count=").
                append(this.count).append(", total=").
                append(this.total).append(", searchParams=").
                append(this.searchParams).append(", resultParams=").
                append(ArrayUtils.asList(this.resultParams)).
                append(", selectedResultParams=").
                append(ArrayUtils.asList(this.selectedResultParams)).
                append(", resultOrder=").append(ArrayUtils.asList(this.resultOrder)).
                append("]").toString();
    }
}
