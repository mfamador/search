package pt.maisis.search.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.config.SearchShareConfig;
import pt.maisis.search.entity.Report;

/**
 *
 * @author amador
 */
public class SearchSharePrepareServlet extends PrepareSearchServlet {

    private static Log log = LogFactory.getLog(SearchSharePrepareServlet.class);
    protected static final String OPERATION_PREPARE = "prepare";

    /**
     * manageOperations
     */
    protected boolean manageOperations(HttpServletRequest request,
            HttpServletResponse response,
            Map<String, Object> parameterMap) {

        boolean executeSearch = true;
        SearchShareConfig ssc = SearchShareConfig.getInstance();

        if (request.getParameter(ssc.getOperation()) != null) {

            if (OPERATION_PREPARE.equals(request.getParameter(ssc.getOperation()))) {
                Report report = ( new Report() ).find(Long.parseLong(request.getParameter(ssc.getReportId())));
                SearchShareHelper.fillParameterMap(report, parameterMap);
            }
        }

        return executeSearch;
    }

    /**
     * doGet
     */
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        if (manageOperations(request, response, parameterMap)) {
            if (log.isDebugEnabled() && !parameterMap.isEmpty()) {
                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                    log.debug("parameter name=" + entry.getKey() + " value=" + entry.getValue());
                }
            }

            super.doGet(request, response, parameterMap);
        }
    }

    /**
     * doPost
     */
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        if (manageOperations(request, response, parameterMap)) {
            if (log.isDebugEnabled() && !parameterMap.isEmpty()) {
                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                    log.debug("parameter name=" + entry.getKey() + " value=" + entry.getValue());
                }
            }

            super.doPost(request, response, parameterMap);
        }
    }
}
