package pt.maisis.search.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.util.Map;

import java.io.IOException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.Constants;

/**
 * Permite fazer o set dos valores de defeito especificados
 * no descriptor xml.
 *
 * @version 1.0
 */
public class PrepareSearchServlet extends SearchBaseServlet {
    private static Log log = LogFactory.getLog(PrepareSearchServlet.class);

    /**
     * Indica que para alem dos valores definidos no descriptor da 
     * pesquisa, permite, apos o set destes ultimos, fazer os set
     * com os valores que vem no request.
     */
    public static final String POPULATE = "populate";

    /**
     * Delega o pedido para o mÃ©todo {@link #prepareSearch}.
     */
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        prepareSearch(request, response, null);
    }

    /**
     * Delega o pedido para o mÃ©todo {@link #prepareSearch}.
     */
    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

        prepareSearch(request, response, null);
    }

    /**
     * Delega o pedido para o mÃ©todo {@link #prepareSearch}.
     */
    public void doGet(HttpServletRequest request,
            HttpServletResponse response,
            Map parameterMap)
            throws IOException, ServletException {

        prepareSearch(request, response, parameterMap);
    }

    /**
     * Delega o pedido para o mÃ©todo {@link #prepareSearch}.
     */
    public void doPost(HttpServletRequest request,
            HttpServletResponse response,
            Map parameterMap)
            throws IOException, ServletException {

        prepareSearch(request, response, parameterMap);
    }

    /**
     * Prepara a pesquisa identificada pelo parâmetro 
     * <code>search</code>.
     */
    protected void prepareSearch(HttpServletRequest request,
            HttpServletResponse response,
            Map parameterMap)
            throws IOException, ServletException {

        SearchForm form = createForm(request, response);
        if (form == null) {
            return;
        }

        String populate = request.getParameter(POPULATE);
        if ("true".equals(populate)) {
            try {
                Map params = request.getParameterMap();
                if (parameterMap != null && !parameterMap.isEmpty()) {
                    params = parameterMap;
                }

                BeanUtils.populate(form, params);

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
                
                //log.debug(form.getCount());
                request.setAttribute(Constants.COUNT, form.getCount());
            }
            catch (Exception e) {
                throw new ServletException(e);
            }
        }

        WebSearch webSearch = getWebSearch(form.getSearch());
        forward(request, response, webSearch.getSearchView());
    }

    @Override
    protected boolean isPreparableSearch(HttpServletRequest request) {
        return true;
    }
}
