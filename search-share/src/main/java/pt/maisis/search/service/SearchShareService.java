package pt.maisis.search.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.entity.Report;
import pt.maisis.search.web.SearchForm;
import pt.maisis.search.web.SearchShareHelper;

public final class SearchShareService {

    private static Log log = LogFactory.getLog(SearchShareService.class);
    private static final SearchShareService me = new SearchShareService();

    private SearchShareService() {
    }

    /**
     * Returns the instance of this service.
     */
    public static SearchShareService getInstance() {
        return me;
    }

    public SearchForm getSearchForm(long reportId) {

        SearchForm form = new SearchForm();

        Report report = (new Report()).find(reportId);
        log.debug("\n\n\n\n\n\n\nreport to execute : " + report + "\n\n\n\n\n\n");

        SearchShareHelper.fillForm(report, form);
        
        return form;
    }
}
