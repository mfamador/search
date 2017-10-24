package pt.maisis.search.service;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.ContainerMetaData;
import pt.maisis.search.Result;
import pt.maisis.search.SearchEngine;
import pt.maisis.search.SearchMetaData;
import pt.maisis.search.SearchParameterMetaData;
import pt.maisis.search.SearchQueryBuilder;
import pt.maisis.search.SearchQueryMetaData;
import pt.maisis.search.export.SearchResultExporter;
import pt.maisis.search.validator.ValidationResult;

public final class SearchService {

    private static Log log = LogFactory.getLog(SearchService.class);
    private static final SearchService me = new SearchService();
    public static final String DEFAULT_FORMAT = SearchResultExporter.HTML;
    public static final String COUNT = "count";
    public static final String SELECT_ALL_RESULT_PARAMS = "selectAllResultParams";
    public static final String SELECT_ALL_RESULTS = "selectAllResults";
    private int defaultCount = 10;
    private boolean defaultSelectAllResultParams = true;

    private SearchService() {
    }

    /**
     * Returns the instance of this service.
     */
    public static SearchService getInstance() {
        return me;
    }

    private void buildSearchParams(String search,
            ValidationResult validationResult,
            SearchQueryBuilder builder) {

        SearchEngine se = SearchEngine.getInstance();
        SearchMetaData smd = se.getSearch(search);
        List spmds = smd.getSearchParameters();

        for (Iterator it = spmds.iterator(); it.hasNext();) {
            SearchParameterMetaData spmd = (SearchParameterMetaData) it.next();
            ContainerMetaData cmd = spmd.getContainer();

        }
    }

    private void buildResultParams(String search,
            SearchQueryBuilder builder) {
        SearchEngine se = SearchEngine.getInstance();
        SearchMetaData smd = se.getSearch(search);

        builder.buildResultParams(smd.getSelectedResultParameterNames());
    }

    protected SearchQueryMetaData buildSearchQuery(ValidationResult converted,
            String search) {
        SearchQueryBuilder builder = new SearchQueryBuilder(search);
        buildSearchParams(search, converted, builder);
        //buildSqlFragmentParam(form, builder);
        buildResultParams(search, builder);
        //buildOrderParams(form, builder);
        return builder.getSearchQuery();
    }

    public List executeSearch(String search) {
        return executeSearch(search, 0, 15);
    }

    public List executeSearch(String search, int start, int count) {
        ValidationResult validationResult = null;
        SearchQueryMetaData query = buildSearchQuery(validationResult, search);

        SearchEngine se = SearchEngine.getInstance();
        Result result = se.executeSearch(query, start, count, 0);

        return result.getSearchResult().getElements();
    }
}
