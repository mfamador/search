package pt.maisis.search.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.*;
import pt.maisis.search.config.WebSearchMetaDataConfig;
import pt.maisis.search.export.SearchResultExportException;
import pt.maisis.search.export.SearchResultExporter;
import pt.maisis.search.format.Formatter;
import pt.maisis.search.util.StringUtils;
import pt.maisis.search.validator.ValidationError;
import pt.maisis.search.validator.ValidationErrors;
import pt.maisis.search.validator.ValidationResult;

/**
 * Servlet que permite, de uma forma razoavelmente simples, utilizar 
 * a framework de pesquisa num ambiente web.
 * <br>
 * Como com qualquer outra servlet é necessário declarar esta servlet
 * no ficheiro <code>web.xml</code>:
 * <br>
 *
 * <pre>
 * &lt;servlet&gt;
 *  &lt;servlet-name&gt;search&lt;/servlet-name&gt;
 *  &lt;servlet-class&gt;pt.maisis.search.web.SearchServlet&lt;/servlet-class&gt;
 *  &lt;load-on-startup&gt;1&lt;/load-on-startup&gt;
 * &lt;/servlet&gt;
 * 
 * &lt;servlet-mapping&gt;
 *  &lt;servlet-name&gt;search&lt;/servlet-name&gt;
 *  &lt;url-pattern&gt;search&lt;/url-pattern&gt;
 * &lt;/servlet-mapping&gt;
 * </pre>
 *
 * @version 1.0
 */
public class SearchServlet extends SearchBaseServlet {

    private static Log log = LogFactory.getLog(SearchServlet.class);
    public static final String DEFAULT_FORMAT = SearchResultExporter.HTML;
    private int defaultCount = 10;
    private int defaultResultRecordLimit = 0;
    private int defaultQueryTimeout = 0;
    private boolean defaultSelectAllResultParams;
    private boolean defaultHideDuplicatedResultParams;
    private boolean defaultHideDuplicatedHtmlResultParams;
    private String defaultExporterFilenamePrefix;
    private Boolean defaultExporterCompression;
    private Integer defaultExporterCompressionRecord;

    /*
     * Captura os init parameters {@link #COUNT} e
     * {@link #SELECT_ALL_RESULT_PARAMS}.
     */
    @Override
    public void init() throws ServletException {
        if (log.isDebugEnabled()) {
            log.debug("SEARCH 2.1.3");
        }
        super.init();

        String count = getServletConfig().getInitParameter(Constants.COUNT);
        String selectAllResultParams = getServletConfig().getInitParameter(Constants.SELECT_ALL_RESULT_PARAMS);

        String resultRecordLimit = getServletConfig().getInitParameter(Constants.RESULT_RECORD_LIMIT);
        String queryTimeout = getServletConfig().getInitParameter(Constants.QUERY_TIMEOUT);
        String hideDuplicatedResultParams = getServletConfig().getInitParameter(Constants.HIDE_DUPLICATED_RESULT_PARAMS);
        String hideDuplicatedHtmlResultParams = getServletConfig().getInitParameter(Constants.HIDE_DUPLICATED_HTML_RESULT_PARAMS);

        String exporterFilenamePrefix = getServletConfig().getInitParameter(Constants.EXPORTER_FILENAME_PREFIX);
        String exporterCompression = getServletConfig().getInitParameter(Constants.EXPORTER_COMPRESSION);
        String exporterCompressionRecord = getServletConfig().getInitParameter(Constants.EXPORTER_COMPRESSION_RECORD);

        if (count != null) {
            try {
                this.defaultCount = Integer.parseInt(count);
            } catch (NumberFormatException e) {
                if (log.isDebugEnabled()) {
                    log.debug("[servlet init]: init param 'count' invalido. " + "Vou utilizar, por defeito, o valor " + Constants.COUNT);
                }
            }
        }

        if (resultRecordLimit != null) {
            try {
                this.defaultResultRecordLimit = Integer.parseInt(resultRecordLimit);
            } catch (NumberFormatException e) {
                if (log.isDebugEnabled()) {
                    log.debug("[servlet init]: init param 'resultRecordLimit' invalido. " + "Vou utilizar, por defeito, o valor " + Constants.COUNT);
                }
            }
        }

        if (queryTimeout != null) {
            try {
                this.defaultQueryTimeout = Integer.parseInt(queryTimeout);
            } catch (NumberFormatException e) {
                if (log.isDebugEnabled()) {
                    log.debug("[servlet init]: init param 'queryTimeout' invalido. " + "Vou utilizar, por defeito, o valor " + Constants.COUNT);
                }
            }
        }

        this.defaultSelectAllResultParams =
                Boolean.valueOf(selectAllResultParams).booleanValue();

        this.defaultHideDuplicatedResultParams =
                Boolean.valueOf(hideDuplicatedResultParams).booleanValue();

        this.defaultHideDuplicatedHtmlResultParams =
                Boolean.valueOf(hideDuplicatedHtmlResultParams).booleanValue();

        if (exporterFilenamePrefix != null) {
            defaultExporterFilenamePrefix = exporterFilenamePrefix;
        } else {
            defaultExporterFilenamePrefix = "report";
        }

        try {
            this.defaultExporterCompression = Boolean.valueOf(exporterCompression);
        } catch (Exception e) {
            this.defaultExporterCompression = Boolean.FALSE;
        }

        try {
            this.defaultExporterCompressionRecord = Integer.valueOf(exporterCompressionRecord);
        } catch (Exception e) {
            this.defaultExporterCompressionRecord = new Integer(0);
        }

        if (log.isDebugEnabled()) {
            log.debug("SelectAllResultParams: " + this.defaultSelectAllResultParams);
            log.debug("HideDuplicatedResultParams: " + this.defaultHideDuplicatedResultParams);
            log.debug("HideDuplicatedHtmlResultParams: " + this.defaultHideDuplicatedHtmlResultParams);
            log.debug("ExporterFilenamePrefix: " + this.defaultExporterFilenamePrefix);
            log.debug("ExporterCompression: " + this.defaultExporterCompression);
            log.debug("ExporterCompressionRecord: " + this.defaultExporterCompressionRecord);
        }
    }

    /**
     * Delega o pedido HTTP para o método {@link #executeSearch}.
     */
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        executeSearch(request, response, null);
    }

    private void logRequest(HttpServletRequest request) {
        Enumeration en = request.getParameterNames();

        while (en.hasMoreElements()) {
            String paramName = (String) en.nextElement();
            log.debug(paramName + " = " + request.getParameter(paramName));

            String[] paramValues = request.getParameterValues(paramName);
            for (int i = 0; i < paramValues.length; i++) {
                log.debug("     values " + paramName + " = " + paramValues[i]);
            }
        }

        Iterator it = request.getParameterMap().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) it.next();
            String paramName = (String) me.getKey();

            log.debug(paramName + "  class: " + me.getValue().getClass().getName());

            if (me.getValue().getClass().isArray()) {
                log.debug(paramName + "  IS ARRAY !!! ");
                String[] params = (String[]) me.getValue();
                for (int i = 0; i < params.length; i++) {
                    log.debug("     values " + paramName + " = " + params[i]);
                }
            }
        }
    }

    public void doGet(HttpServletRequest request,
            HttpServletResponse response,
            Map parameterMap)
            throws IOException, ServletException {

        /*if (log.isDebugEnabled()) {
        logRequest(request);
        }*/

        executeSearch(request, response, parameterMap);
    }

    /**
     * Delega o pedido HTTP para o método {@link #executeSearch}.
     */
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        /*if (log.isDebugEnabled()) {
        logRequest(request);
        }*/

        executeSearch(request, response, null);
    }

    public void doPost(HttpServletRequest request,
            HttpServletResponse response,
            Map parameterMap)
            throws IOException, ServletException {

        /*if (log.isDebugEnabled()) {
        logRequest(request);
        }*/
        executeSearch(request, response, parameterMap);
    }

    /**
     * Método executado para os pedidos {@link doGet} e GET e 
     * {@link doPost}.
     */
    protected void executeSearch(HttpServletRequest request,
            HttpServletResponse response,
            Map parameterMap)
            throws IOException, ServletException {

        String localeParam = (String) request.getParameter(LOCALE);

        if (localeParam != null) {
            Locale newLocale = null;
            if (localeParam.indexOf('_') > -1) {
                String[] temp = null;
                try {
                    temp = localeParam.split("_");
                    newLocale = new Locale(temp[0], temp[1]);
                } catch (Exception e) {
                    return;
                }
            } else {
                newLocale = new Locale(localeParam);
            }

            if (newLocale != null) {
                LocaleConfig.setLocale(request.getSession(), newLocale);
                Config.set(request.getSession(), Config.FMT_LOCALE, newLocale);
            }
        }

        SearchForm form = createForm(request, response, parameterMap);
        if (form == null) {
            return;
        }

        Map params = request.getParameterMap();
        if (parameterMap != null && !parameterMap.isEmpty()) {
            params = parameterMap;
        }

        populateForm(form, params);

        if (log.isTraceEnabled()) {
            log.trace("[search form]: " + form);
        }

        WebSearch webSearch = getWebSearch(form.getSearch());

        ValidationErrors validationErrors = new ValidationErrors();
        ValidationResult validationResult = new ValidationResult(form.getSearchParams());

        Locale locale = LocaleConfig.getLocale(request);
        form.validate(validationResult, validationErrors, locale);

        if (validationErrors == null || !validationErrors.isEmpty()) {
            saveErrors(request, validationErrors);
            forward(request, response, webSearch.getSearchView());
            return;
        }

        int resultRecordLimit = this.defaultResultRecordLimit;
        int queryTimeout = this.defaultQueryTimeout;

        SearchEngine se = SearchEngine.getInstance();
        SearchQueryMetaData query = se.buildSearchQuery(validationResult, form);

        if (form.getResultRecordLimit() != 0) {
            resultRecordLimit = form.getResultRecordLimit();
        }
        query.setResultRecordLimit(resultRecordLimit);

        if (form.getQueryTimeout() != 0) {
            queryTimeout = form.getQueryTimeout();
        }
        query.setQueryTimeout(queryTimeout);

        Result result = null;
        String allResults = request.getParameter(Constants.SELECT_ALL_RESULTS);
        try {
            if ("true".equals(allResults)) {
                result = se.executeSearch(query);
            } else {
                result = se.executeSearch(query, form.getStart(), form.getCount(), form.getTotal());

                if (log.isDebugEnabled()) {
                    log.debug("Result count: " + ((PageableSearchResult) result.getSearchResult()).getTotal());
                }
            }
        } catch (SearchTooManyResultsException e) {
            validationErrors.add(new ValidationError("error.tooManyResults", new Integer(resultRecordLimit)));
            saveErrors(request, validationErrors);
            forward(request, response, webSearch.getSearchView());
            return;
        } catch (SearchQueryTimeoutException e) {
            validationErrors.add(new ValidationError("error.queryTimeout", new Integer(queryTimeout)));
            saveErrors(request, validationErrors);
            forward(request, response, webSearch.getSearchView());
            return;
        }

        // Por vezes existe a necessidade de apresentar os critérios
        // de pesquisa no resultado de uma pesquisa.
        // O que seria simples se não fosse o conceito introduzido
        // na framework relativa aos 'fake params'.
        // Assim, na altura da apresentacão dos resultados é necessário
        // analizar o que é um 'search param' e o que é um 'fake param'.
        // Neste momento, optou-se por facilitar o processo criando
        // um novo tipo de objecto que encapsula esta decisão.
        //
        // TODO: remover o conceito de 'fake params' que só traz 
        // problemas!
        Map searchParams = getDisplayableSearchParams(form, locale);
        request.setAttribute(Constants.SEARCH_PARAMS, searchParams);
        request.setAttribute(Constants.SEARCH_FORM, form);
        /*
         * search share
         */
        request.setAttribute(Constants.SEARCH_SHARE_REPORT_ID, form.getSearchShareReportId());
        request.setAttribute(Constants.SEARCH_SHARE_REPORT_NAME, form.getSearchShareReportName());
        request.setAttribute(Constants.SEARCH_SHARE_REPORT_TYPE, form.getSearchShareReportType());
        request.setAttribute(Constants.SEARCH_SHARE_SLIDING_WINDOW, form.getSearchShareSlidingWindow());
        request.setAttribute(Constants.SEARCH_SHARE_ENTITY_ID, form.getSearchShareEntityId());
        request.setAttribute(Constants.SEARCH_SHARE_ENTITY_TYPE_ID, form.getSearchShareEntityTypeId());
        request.setAttribute(Constants.SEARCH_SHARE_PERMISSION_ID, form.getSearchSharePermissionId());

        // apresentar resultado
        String format = request.getParameter(Constants.FORMAT);
        if (format == null) {
            format = DEFAULT_FORMAT;
        }

        /*
         * HideDuplicatedResultParams
         */
        if (form.isHideDuplicatedResultParams()
                || (form.isHideDuplicatedHtmlResultParams() && SearchResultExporter.HTML.equals(format))) {
            /* Hide Duplicates */
            SearchMetaData smd = se.getSearch(form.getSearch());
            String[] resultParams = form.getSelectedResultParams();
            if (resultParams == null) {
                resultParams = smd.getSelectedResultParameterNames();
            }

            Map formatters = new HashMap();
            if (resultParams != null) {
                int index = 0;
                for (int i = 0; i < resultParams.length; i++) {
                    ResultParameterMetaData rpmd = smd.getResultParameter(resultParams[i]);
                    formatters.put(new Integer(index++), rpmd.getFormatter());
                    if (rpmd != null) {
                        if (rpmd.isComposite()) {
                            List children = rpmd.getChildren();
                            for (Iterator it = children.iterator(); it.hasNext();) {
                                rpmd = (ResultParameterMetaData) it.next();
                                formatters.put(new Integer(index++), rpmd.getFormatter());
                            }
                        }
                    }
                }
            }

            Map oldElements = new HashMap();
            for (Iterator it = result.getSearchResult().getElements().iterator(); it.hasNext();) {
                SearchResultElement element = (SearchResultElement) it.next();
                boolean hide = true;
                for (int i = 0; i < element.size(); i++) {
                    Object formattedElement = null;
                    if (element != null
                            && element.getValue(i) != null) {
                        if (formatters.get(new Integer(i)) != null) {
                            formattedElement = ((Formatter) formatters.get(new Integer(i))).format(element.getValue(i), locale);
                        }
                    }

                    if (formattedElement != null
                            && oldElements.get(new Integer(i)) != null
                            && formattedElement.equals(oldElements.get(new Integer(i)))) {
                        oldElements.put(new Integer(i), formattedElement);
                        if (hide) {
                            element.setValue(i, "");
                        }
                    } else {
                        hide = false;
                        oldElements.put(new Integer(i), formattedElement);
                    }
                }
            }
        }

        // colocar o resultado da pesquisa no request
        request.setAttribute(webSearch.getSearchResultName(), result);

        if (result.getSearchResult().isEmpty()) {
            validationErrors.add(new ValidationError("error.noResults"));
            saveErrors(request, validationErrors);
            forward(request, response, webSearch.getSearchView());
            return;
        }

        String resultView = webSearch.getResultView(format);
        WebSearchMetaDataConfig config = WebSearchMetaDataConfig.getInstance();
        WebSearchMetaData wsmd = config.getWebSearchMetaData();


        if (wsmd.isExporter(resultView)) {
            WebExporter exporter = wsmd.getExporter(resultView);

            String exporterFilenamePrefix = this.defaultExporterFilenamePrefix;
            if (form.getExporterFilenamePrefix() != null) {
                exporterFilenamePrefix = form.getExporterFilenamePrefix();
            }

            Boolean exporterCompression = this.defaultExporterCompression;
            if (form.isExporterCompression() != null) {
                exporterCompression = form.isExporterCompression();
            }

            Integer exporterCompressionRecord = this.defaultExporterCompressionRecord;
            if (form.getExporterCompressionRecord() != null) {
                exporterCompressionRecord = form.getExporterCompressionRecord();
            }

            boolean compression = false;
            if (exporterCompression.booleanValue() && result.getSearchResult().getResultCount() > exporterCompressionRecord.intValue()) {
                compression = true;
            }

            export(request, response, searchParams,
                    result, exporter, format, exporterFilenamePrefix, compression, locale);
        } else {
            forward(request, response, resultView);
        }
    }

    protected Map getDisplayableSearchParams(SearchForm form, Locale locale) {

        SearchEngine se = SearchEngine.getInstance();
        SearchMetaData smd = se.getSearch(form.getSearch());

        Iterator it = smd.getSearchParameters().iterator();
        Map searchParams = new LinkedHashMap();
        while (it.hasNext()) {
            SearchParameterMetaData spmd = (SearchParameterMetaData) it.next();
            ContainerMetaData container = spmd.getContainer();

            String values = getValues(form, container, locale);
            if (values != null) {
                searchParams.put(getLabel(container), values);
            }
        }
        return searchParams;
    }

    private String getValues(final SearchForm form,
            final ContainerMetaData container,
            final Locale locale) {

        if (container.isComposite()) {
            CompositeContainerMetaData composite =
                    (CompositeContainerMetaData) container;

            Formatter formatter = composite.getFormatter();
            List children = composite.getChildren();
            String[] values = new String[children.size()];
            int i = 0;
            for (Iterator it = children.iterator(); it.hasNext(); i++) {
                ContainerMetaData child = (ContainerMetaData) it.next();
                values[i] = getValue(form, child);
            }
            return (String) formatter.format(values, locale);
        }
        return getValue(form, container);
    }

    private String getValue(final SearchForm form,
            final ContainerMetaData container) {

        // Não considerar os containers do tipo hidden.
        if (ContainerMetaData.TYPE_HIDDEN.equals(container.getType())) {
            return null;
        }

        String name = container.getName();
        Object fakeValue = form.getFakeParam(name);
        Object value = (fakeValue == null)
                ? form.getSearchParam(name)
                : fakeValue;

        if (value == null) {
            return null;
        }

        if (value.getClass().isArray()) {
            return StringUtils.join(value);
        }
        return (String) value;
    }

    /**
     * Retorna o label do <code>container</code>. 
     * No caso do label ser <code>null</code>, retorna o label 
     * do pai, caso o tenha.
     */
    private String getLabel(final ContainerMetaData container) {
        if (container.getLabel() == null && container.getParent() != null) {
            return container.getParent().getLabel();
        }

        return container.getLabel();
    }

    /**
     * Faz o <i>populate</i> da {@link SearchForm} com os valores 
     * do pedido HTTP.
     */
    protected void populateForm(SearchForm form, Map params)
            throws ServletException {

        try {
            BeanUtils.populate(form, params);

            // WORK ON form.setSearchParam("title", params.get("searchParam(title)"));

            /*
            Treat Short Names
             */
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry me = (Map.Entry) iterator.next();
                if (((String) me.getKey()).startsWith("sp(")) {
                    String paramName = (String) me.getKey();
                    paramName = "searchParam(" + paramName.substring(3);
                    BeanUtils.setProperty(form, paramName, me.getValue());
                } else if (((String) me.getKey()).startsWith("srp")) {
                    BeanUtils.setProperty(form, "selectedResultParams", me.getValue());
                } else if (((String) me.getKey()).equals("filename")) {
                    BeanUtils.setProperty(form, "exporterFilenamePrefix", me.getValue());
                }
            }

            WebSearch webSearch = getWebSearch(form.getSearch());

            if (form.getCount() == 0) {
                if (webSearch.getCount() == 0) {
                    form.setCount(this.defaultCount);
                } else {
                    form.setCount(webSearch.getCount());
                }
            }

            if (form.getResultRecordLimit() == 0) {
                if (webSearch.getResultRecordLimit() == 0) {
                    form.setResultRecordLimit(this.defaultResultRecordLimit);
                } else {
                    form.setResultRecordLimit(webSearch.getResultRecordLimit());
                }
            }

            if (form.getQueryTimeout() == 0) {
                if (webSearch.getQueryTimeout() == 0) {
                    form.setQueryTimeout(this.defaultQueryTimeout);
                } else {
                    form.setQueryTimeout(webSearch.getQueryTimeout());
                }
            }

            if (params.get(Constants.SELECT_ALL_RESULT_PARAMS) == null
                    && form.getSelectedResultParams() == null) {

                if (webSearch.isSelectAllResultParams()) {
                    form.setSelectAllResultParams(true);
                } else {
                    form.setSelectAllResultParams(this.defaultSelectAllResultParams);
                }
            } else if (params.get(Constants.SELECT_ALL_RESULT_PARAMS) == null
                    && form.getSelectedResultParams() != null) {
                form.setSelectAllResultParams(false);
            } else if (params.get(Constants.SELECT_ALL_RESULT_PARAMS) != null) {
                boolean paramSelectAllResultParams = Boolean.valueOf(params.get(Constants.SELECT_ALL_RESULT_PARAMS).toString()).booleanValue();
                form.setSelectAllResultParams(paramSelectAllResultParams);
            }

            if (params.get(Constants.HIDE_DUPLICATED_RESULT_PARAMS) == null) {
                if (webSearch.isHideDuplicatedResultParams()) {
                    form.setHideDuplicatedResultParams(true);
                } else {
                    form.setHideDuplicatedResultParams(this.defaultHideDuplicatedResultParams);
                }
            }

            if (params.get(Constants.HIDE_DUPLICATED_HTML_RESULT_PARAMS) == null) {
                if (webSearch.isHideDuplicatedHtmlResultParams()) {
                    form.setHideDuplicatedHtmlResultParams(true);
                } else {
                    form.setHideDuplicatedHtmlResultParams(this.defaultHideDuplicatedHtmlResultParams);
                }
            }

            if (params.get(Constants.EXPORTER_FILENAME_PREFIX) == null) {
                if (webSearch.getExporterFilenamePrefix() != null && form.getExporterFilenamePrefix() == null) {
                    form.setExporterFilenamePrefix(webSearch.getExporterFilenamePrefix());
                } else if (form.getExporterFilenamePrefix() == null) {
                    form.setExporterFilenamePrefix(this.defaultExporterFilenamePrefix);
                }
            }

            if (params.get(Constants.EXPORTER_COMPRESSION) == null) {
                if (webSearch.isExporterCompression() != null && form.isExporterCompression() == null) {
                    form.setExporterCompression(webSearch.isExporterCompression());
                } else if (form.isExporterCompression() == null) {
                    form.setExporterCompression(this.defaultExporterCompression);
                }
            }

            if (params.get(Constants.EXPORTER_COMPRESSION_RECORD) == null) {
                if (webSearch.getExporterCompressionRecord() != null && form.getExporterCompressionRecord() == null) {
                    form.setExporterCompressionRecord(webSearch.getExporterCompressionRecord());
                } else if (form.getExporterCompressionRecord() == null) {
                    form.setExporterCompressionRecord(this.defaultExporterCompressionRecord);
                }
            }
        } catch (Exception e) {
            log.error(e);
            throw new ServletException(e);
        }
    }

    public Map getZipHeaders(String filenamePrefix) {
        Map headers = new LinkedHashMap();
        headers.put("Content-Disposition",
                "attachment;filename=" + filenamePrefix + ".zip");

        headers.put("Content-Encoding", "compress");

        return headers;
    }

    private String getExtension(String format) {
        if (SearchResultExporter.XLS.equalsIgnoreCase(format)) {
            return "xls";
        }
        if (SearchResultExporter.PXLS.equalsIgnoreCase(format)) {
            return "xls";
        }
        if (SearchResultExporter.PDF.equalsIgnoreCase(format)) {
            return "pdf";
        }
        if (SearchResultExporter.PNG.equalsIgnoreCase(format)) {
            return "png";
        }
        if (SearchResultExporter.RTF.equalsIgnoreCase(format)) {
            return "rtf";
        }
        if (SearchResultExporter.CSV.equalsIgnoreCase(format)) {
            return "csv";
        }

        return null;
    }

    protected void export(HttpServletRequest request,
            HttpServletResponse response,
            Map searchParams,
            Result result,
            WebExporter exporter,
            String format,
            String exporterFilenamePrefix,
            boolean compression,
            Locale locale)
            throws IOException, ServletException {
        try {
            if (compression) {
                response.reset();
                response.addHeader("Content-Type", "application/octet-stream");
                //response.setContentType("application/zip");
                response.addHeader("Content-Disposition", "attachment; filename=" + exporterFilenamePrefix + ".zip");
                ServletOutputStream ouputStream = response.getOutputStream();
                ZipOutputStream out = new ZipOutputStream(ouputStream);
                out.setLevel(9);
                out.putNextEntry(new ZipEntry(exporterFilenamePrefix + "." + getExtension(format)));
                exporter.getExporter().export(searchParams, result, out, format, locale);
                out.closeEntry();
                out.finish();
                out.close();
            } else {
                Map headers = exporter.getExporter().getHeaders(result, format, exporterFilenamePrefix);
                Set entries = headers.entrySet();
                for (Iterator it = entries.iterator(); it.hasNext();) {
                    Map.Entry entry = (Map.Entry) it.next();

                    log.debug("setting headers key = " + entry.getKey() + " value = " + entry.getValue());
                    response.setHeader((String) entry.getKey(), (String) entry.getValue());
                }

                if (SearchResultExporter.HTML_FRAGMENT.equalsIgnoreCase(format)) {
                    if (exporter.getHtmlHeader() != null) {
                        RequestDispatcher dispatcher = request.getRequestDispatcher(exporter.getHtmlHeader());
                        dispatcher.include(request, response);
                    }
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    PrintWriter writer = response.getWriter();
                    exporter.getExporter().export(searchParams, result, out, format, locale);
                    writer.write(out.toString());
                    if (exporter.getHtmlFooter() != null) {
                        RequestDispatcher dispatcher = request.getRequestDispatcher(exporter.getHtmlFooter());
                        dispatcher.include(request, response);
                    }
                    return;
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug("[export]: "
                                + result.getSearchResult().getResultCount()
                                + " elements.");
                    }
                    OutputStream out = response.getOutputStream();
                    exporter.getExporter().export(searchParams, result, out, format, locale);
                }
            }
        } catch (SearchResultExportException e) {
            log.error(e);
        }
    }
}
