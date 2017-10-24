package pt.maisis.search;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import pt.maisis.search.dao.SearchEngineDao;
import pt.maisis.search.dao.SearchEngineMetaDataDao;
import pt.maisis.search.dao.SearchDaoException;
import pt.maisis.search.dao.SearchEngineMetaDataDaoFactory;
import pt.maisis.search.config.SearchConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.config.WebSearchMetaDataConfig;
import pt.maisis.search.format.Formatter;
import pt.maisis.search.util.StringUtils;
import pt.maisis.search.validator.ValidationErrors;
import pt.maisis.search.validator.ValidationResult;
import pt.maisis.search.value.Value;
import pt.maisis.search.web.SearchForm;
import pt.maisis.search.web.WebExporter;
import pt.maisis.search.web.WebSearch;
import pt.maisis.search.web.WebSearchMetaData;

/**
 * Motor de pesquisa.
 *
 * @version 1.0
 */
public class SearchEngine {

    private static Log log = LogFactory.getLog(SearchEngine.class);
    private static SearchEngine me = new SearchEngine();
    private final SearchEngineMetaDataDao semd;
    private final SearchSetMetaData searches; // cache

    private SearchEngine() {
        this.semd = SearchEngineMetaDataDaoFactory.create();
        this.searches = this.semd.getSearchSetMetaData();
    }

    /**
     * Retorna a instância desta classe.
     */
    public static SearchEngine getInstance() {
        return me;
    }

    public void setSearchMetadata(SearchMetaData smd) {
        this.searches.add(smd);
    }

    /**
     * Retorna um <i>container</i> do tipo {@link SearchSetMetaData} com todas
     * as pesquisas previamente configuradas.
     */
    public SearchSetMetaData getSearchSetMetaData()
            throws SearchDaoException {
        return this.searches;
    }

    /**
     * Retorna a instância de {@link SearchMetaData} identificada pelo parâmetro
     * <code>name</code>.
     */
    public SearchMetaData getSearch(String name)
            throws SearchDaoException {
        return this.searches.getMetaData(name);
    }

    /**
     * Efectua uma dada pesquisa, retornando todos os resultados resultantes da
     * pesquisa.
     *
     * @param query query com a informacão para a pesquisa.
     * @return uma instância de {@link Result} com o resultado da pesquisa bem
     * como a metadata associada.
     * @exception {@link SearchException} se ocorrer um erro.
     */
    public Result executeSearch(final SearchQueryMetaData query)
            throws SearchException {

        try {
            SearchQuery sq = query.getQuery();
            SearchMetaData smd = query.getSearchMetaData();
            SearchEngineDao se = getImplementation(smd.getName());
            SearchResult sr = se.executeSearch(sq);
            return wrapResult(sr, query);

        } catch (SearchDaoException e) {
            throw new SearchException(e);
        }
    }

    /**
     * Efectua uma dada pesquisa cujos resultados podem ser paginados.
     *
     * @param query query com a informacão para a pesquisa.
     * @param start índice do primeiro resultado a considerar.
     * @param count número de resultados desejados.
     * @return uma instância de {@link Result} com o resultado da pesquisa bem
     * como a metadata associada.
     * @exception {@link SearchException} se ocorrer um erro.
     */
    public Result executeSearch(final SearchQueryMetaData query,
            final int start, final int count)
            throws SearchException {

        try {
            SearchQuery sq = query.getQuery();
            SearchMetaData smd = query.getSearchMetaData();
            SearchEngineDao se = getImplementation(smd.getName());
            SearchResult sr = se.executeSearch(sq, start, count);
            return wrapResult(sr, query);

        } catch (SearchDaoException e) {
            throw new SearchException(e);
        }
    }

    /**
     * Efectua uma dada pesquisa cujos resultados podem ser paginados. Se o
     * parâmetro
     * <code>total</total> for igual a zero é feito um
     * count dos resultados.
     *
     * @param query query com a informacão para a pesquisa.
     * @param start índice do primeiro resultado a considerar.
     * @param count número de resultados desejados.
     * @param total contador dos resultados.
     * @return uma instância de {@link Result} com o resultado da pesquisa bem
     * como a metadata associada.
     * @exception {@link SearchException} se ocorrer um erro.
     */
    public Result executeSearch(final SearchQueryMetaData query,
            final int start, final int count,
            final int total)
            throws SearchException {

        try {
            SearchQuery sq = query.getQuery();
            SearchMetaData smd = query.getSearchMetaData();
            SearchEngineDao se = getImplementation(smd.getName());
            SearchResult sr = se.executeSearch(sq, start, count, total);
            return wrapResult(sr, query);

        } catch (SearchDaoException e) {
            throw new SearchException(e);
        }
    }

    private Result wrapResult(final SearchResult sr,
            final SearchQueryMetaData query) {
        return new ResultImpl(sr,
                query.getSearchMetaData(),
                query.getResultMetaData(),
                query.getRequiredResultMetaData());
    }

    private SearchEngineDao getImplementation(final String searchName) {
        return this.searches.getImplementation(searchName);
    }

    // alterar isto!!!!!!!!!!!
    public static void reload() {
        SearchConfig.reload();
        SearchEngine se = new SearchEngine();
        me = se;
    }

    public static void reload(String search) {
        SearchConfig.reload(search);
        // SearchEngine se = new SearchEngine();
        // me = se;
    }

    /*
     * only at export
     */
    public ByteArrayOutputStream export(SearchForm form, String format, Locale locale) {

        Result result = executeSearch(form, locale);

        if (result.getSearchResult().isEmpty()) {
            log.error("não tem resultados");
            return null;
        }

        WebSearchMetaDataConfig config = WebSearchMetaDataConfig.getInstance();
        WebSearchMetaData wsmd = config.getWebSearchMetaData();
        WebSearch webSearch = wsmd.getSearch(form.getSearch());
        String resultView = webSearch.getResultView(format);
        WebExporter exporter = wsmd.getExporter(resultView);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            exporter.getExporter().export(getDisplayableSearchParams(form, locale), result, baos, format, locale);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return baos;
    }

    public void buildSearchParams(SearchForm form,
            ValidationResult validationResult,
            SearchQueryBuilder builder) {

        SearchEngine se = SearchEngine.getInstance();
        SearchMetaData smd = se.getSearch(form.getSearch());
        List spmds = smd.getSearchParameters();

        for (Iterator it = spmds.iterator(); it.hasNext();) {
            SearchParameterMetaData spmd = (SearchParameterMetaData) it.next();
            ContainerMetaData cmd = spmd.getContainer();
            Object value = validationResult.getValue(spmd.getName());

            if (value != null) {
                builder.buildSearchParam(spmd.getName(), value);
            }
        }
    }

    public void buildResultParams(SearchForm form,
            SearchQueryBuilder builder) {
        SearchEngine se = SearchEngine.getInstance();
        SearchMetaData smd = se.getSearch(form.getSearch());

        if (form.isSelectAllResultParams()) {
            builder.buildResultParams(smd.getSelectedResultParameterNames());
        } else {
            String[] resultParams = form.getSelectedResultParams();

            for (int i = 0; i < resultParams.length; i++) {
                if (smd.getResultParameter(resultParams[i]) != null) {
                    builder.buildResultParam(resultParams[i]);
                }
            }
        }
    }

    public void buildOrderParams(SearchForm form,
            SearchQueryBuilder builder) {

        String[] resultOrder = form.getResultOrder();
        int orderType = form.getOrderType();

        if (resultOrder != null) {
            SearchEngine se = SearchEngine.getInstance();
            SearchMetaData smd = se.getSearch(form.getSearch());

            for (int i = 0; i < resultOrder.length; i++) {
                if (smd.getResultParameter(resultOrder[i]) != null) {
                    builder.buildOrderParam(resultOrder[i], orderType);
                }
            }
        }
    }

    public void buildSqlFragmentParam(SearchForm form,
            SearchQueryBuilder builder) {

        SearchEngine se = SearchEngine.getInstance();
        SearchMetaData smd = se.getSearch(form.getSearch());
        SqlFragmentParameterMetaData sql = smd.getSqlFragmentParameter();

        if (sql != null) {
            builder.buildSqlFragmentParam((String) form.getSearchParam(sql.getName()));
        }
    }

    public SearchQueryMetaData buildSearchQuery(ValidationResult converted,
            SearchForm form) {
        String search = form.getSearch();
        SearchQueryBuilder builder = new SearchQueryBuilder(search);
        buildSearchParams(form, converted, builder);
        buildSqlFragmentParam(form, builder);
        buildResultParams(form, builder);
        buildOrderParams(form, builder);
        return builder.getSearchQuery();
    }

    private void setSearchParametersMetaData(final ContainerMetaData cmd,
            final SearchForm form) {
        if (cmd.isComposite()) {
            Iterator it = cmd.getChildren().iterator();
            while (it.hasNext()) {
                setSearchParametersMetaData((ContainerMetaData) it.next(), form);
            }
        } else {
            Value defaultValue = cmd.getDefaultValue();

            if (defaultValue != null) {
                // É necessário fazer o set do valor por defeito nas radio boxes 
                // checked, caso contrário a última radio fica seleccionada.
                if (cmd.getType() != null) {
                    if (cmd.getType().equals(ContainerMetaData.TYPE_RADIO)
                            && !cmd.isChecked()) {
                        return;
                    }
                }

                form.setSearchParam(cmd.getName(), defaultValue.getValue());
            }
        }
    }

    /*
     * only at export
     */
    public Result executeSearch(SearchForm form, Locale locale) {

        Map searchParams = (Map<String, Object>) ((HashMap<String, Object>) form.getSearchParams()).clone();
        SearchEngine se = SearchEngine.getInstance();
        SearchMetaData smd = se.getSearchSetMetaData().getMetaData(form.getSearch());

        if (form.getResultParams() == null) {
            form.setSelectAllResultParams(true);
            form.setResultParams(smd.getResultParameterNames());
            form.setSelectedResultParams(smd.getSelectedResultParameterNames());
        }

        Iterator it = smd.getSearchParameters().iterator();
        while (it.hasNext()) {
            SearchParameterMetaData spmd = (SearchParameterMetaData) it.next();
            setSearchParametersMetaData(spmd.getContainer(), form);
        }

        Iterator iterator = searchParams.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iterator.next();

            form.setSearchParam((String) mapEntry.getKey(), mapEntry.getValue());
            log.debug("SET OLD SEARCH PARAM: The key is: " + mapEntry.getKey()
                    + ",value is :" + mapEntry.getValue());
            sb.append("_").append(mapEntry.getValue());
        }

        SearchQueryBuilder builder = new SearchQueryBuilder(form.getSearch());
        builder.buildResultParams(form.getSelectedResultParams());

        builder.buildOrderParams(form.getResultOrder(), form.getOrderType());
        
        ValidationErrors validationErrors = new ValidationErrors();
        ValidationResult validationResult = new ValidationResult(form.getSearchParams());
        form.validate(validationResult, validationErrors, locale);
        SearchQueryMetaData query = buildSearchQuery(validationResult, form);

        return se.executeSearch(query);
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

    private String getLabel(final ContainerMetaData container) {
        if (container.getLabel() == null && container.getParent() != null) {
            return container.getParent().getLabel();
        }

        return container.getLabel();
    }

    public Map getDisplayableSearchParams(SearchForm form, Locale locale) {

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
}
