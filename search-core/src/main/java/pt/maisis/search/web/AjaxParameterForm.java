package pt.maisis.search.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @version 1.0
 */
public class AjaxParameterForm {

    /** Campos de retorno disponíveis. */
    private String[] params;
    private String search;
    private String searchParam;
    private String oper;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public AjaxParameterForm(HttpServletRequest request,
            HttpServletResponse response) {
        this.request = request;
        this.response = response;
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

    public String[] getParams() {
        return this.params;
    }

    public void setParams(final String[] params) {
        this.params = params;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }

    public String getSearchParam() {
        return this.searchParam;
    }

    public void setSearchParam(final String searchParam) {
        this.searchParam = searchParam;
    }

    public String getOper() {
        return this.oper;
    }

    public void setOper(final String oper) {
        this.oper = oper;
    }
}
