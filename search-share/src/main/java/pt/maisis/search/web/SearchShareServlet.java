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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.Map;
import javax.servlet.ServletException;
import pt.maisis.search.ResultParameterMetaData;
import pt.maisis.search.SearchEngine;
import pt.maisis.search.SearchMetaData;
import pt.maisis.search.config.SearchShareConfig;
import pt.maisis.search.entity.OrderParam;
import pt.maisis.search.entity.Report;
import pt.maisis.search.entity.ReportEntity;
import pt.maisis.search.entity.ResultParam;
import pt.maisis.search.entity.SearchParam;
import pt.maisis.search.validator.ValidationError;
import pt.maisis.search.validator.ValidationErrors;
import pt.maisis.search.validator.ValidationResult;

public class SearchShareServlet extends SearchServlet {

    private static Log log = LogFactory.getLog(SearchShareServlet.class);
    protected static final String OPERATION_REMOVE = "remove";
    protected static final String OPERATION_EXECUTE = "execute";

    /**
     * saveSearch
     */
    protected Report saveSearch(HttpServletRequest request, HttpServletResponse response) {

        if (log.isDebugEnabled()) {
            log.debug("init");
        }

        SearchShareConfig ssc = SearchShareConfig.getInstance();

        if (log.isDebugEnabled()) {
            log.debug("getDataSourceName: " + ssc.getDataSourceName());
            log.debug("getMessageResources: " + ssc.getMessageResources());
            log.debug("getInputReportId: " + ssc.getInputReportId());
            log.debug("getInputReportName: " + ssc.getInputReportName());
            log.debug("getInputCreatorId: " + ssc.getInputCreatorId());
            log.debug("getInputEntityId: " + ssc.getInputEntityId());
            log.debug("getSubmitName: " + ssc.getSubmitName());
            log.debug("operation: " + ssc.getOperation());
            log.debug("url: " + ssc.getUrl());
            log.debug("search name: " + request.getParameter(SEARCH));
        }

        ValidationErrors validationErrors = new ValidationErrors();

        if (request.getParameter(ssc.getInputReportName()) == null || request.getParameter(ssc.getInputReportName()).length() == 0) {
            validationErrors.add(new ValidationError("validation.error.share.reportName.required"));
            log.error("validation.error.share.reportName.required");
        }

        if (request.getParameter(ssc.getInputCreatorId()) == null || request.getParameter(ssc.getInputCreatorId()).length() == 0) {
            validationErrors.add(new ValidationError("validation.error.share.creatorId.required"));
            log.error("validation.error.share.creatorId.required");
        }

        if (request.getParameter(ssc.getInputEntityId()) == null || request.getParameter(ssc.getInputEntityId()).length() == 0) {
            validationErrors.add(new ValidationError("validation.error.share.entityId.required"));
            log.error("validation.error.share.entityId.required");
        }

        Report report = new Report();

        try {
            SearchForm form = createForm(request, response);
            if (form == null) {
                validationErrors.add(new ValidationError("validation.error.share.unknown"));
                log.error("validation.error.share.unknown");
            }

            if (validationErrors != null && !validationErrors.isEmpty()) {
                /*request.setAttribute(Constants.SEARCH_SHARE_REPORT_ID, request.getParameter(ssc.getInputReportId()));
                request.setAttribute(Constants.SEARCH_SHARE_REPORT_NAME, request.getParameter(ssc.getInputReportName()));
                request.setAttribute(Constants.SEARCH_SHARE_REPORT_TYPE, request.getParameter(ssc.getInputReportType()));
                request.setAttribute(Constants.SEARCH_SHARE_ENTITY_ID, request.getParameter(ssc.getInputEntityId()));
                request.setAttribute(Constants.SEARCH_SHARE_ENTITY_TYPE_ID, request.getParameter(ssc.getInputEntityTypeId()));
                request.setAttribute(Constants.SEARCH_SHARE_PERMISSION_ID, request.getParameter(ssc.getInputPermissionId()));
                
                request.setAttribute(ssc.getInputReportId(), request.getParameter(ssc.getInputReportId()) + "2");
                request.setAttribute(ssc.getInputReportName(), request.getParameter(ssc.getInputReportName()) + "2");
                request.setAttribute(ssc.getInputReportType(), request.getParameter(ssc.getInputReportType()) + "2");
                request.setAttribute(ssc.getInputEntityId(), request.getParameter(ssc.getInputEntityId()) + "2");
                request.setAttribute(ssc.getInputEntityTypeId(), request.getParameter(ssc.getInputEntityTypeId()) + "2");
                request.setAttribute(ssc.getInputPermissionId(), request.getParameter(ssc.getInputPermissionId()) + "2");*/

                saveErrors(request, validationErrors);
            }

            Map params = request.getParameterMap();
            populateForm(form, params);

            ValidationResult validationResult = new ValidationResult(form.getSearchParams());

            if (request.getParameter(ssc.getInputReportId()) != null) {
                try {
                    report.setId(Long.parseLong(request.getParameter(ssc.getInputReportId())));
                    report = report.find(Long.parseLong(request.getParameter(ssc.getInputReportId())));
                    
                    if(report != null) {
                        report.setSearchParams(null);
                        report.setOrderParams(null);
                        report.setResultParams(null);
                    }
                } catch (Exception e) {
                    log.error(e);
                }
            }

            report.setSearchName(request.getParameter(SEARCH));
            report.setReportName(request.getParameter(ssc.getInputReportName()));
            report.setReportType(request.getParameter(ssc.getInputReportType()));
            
            log.debug("\n\n\n");
            log.debug("1: " + report.getReportCount());
            log.debug("2: " + request.getParameter(COUNT));
            
            if (request.getParameter(COUNT) != null) {
                try {
                    // TO DO verificar se igual a web-search-config ou web.xml
                    log.debug("3: " + request.getParameter(COUNT));
                    report.setReportCount(Integer.decode(request.getParameter(COUNT)));
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            } else {
                report.setReportCount(null);
            }

            log.debug("4: " + report.getReportCount() + "\n\n\n");

            if (request.getParameter(ssc.getInputSlidingWindow()) != null) {
                report.setSlidingWindow(Boolean.TRUE);
            } else if (report.getSlidingWindow() != null) {
                report.setSlidingWindow(Boolean.FALSE);
            }

            SearchEngine se = SearchEngine.getInstance();
            SearchMetaData smd = se.getSearch(request.getParameter(SEARCH));

            List<ResultParameterMetaData> rpl = smd.getResultParameters();

            String creatorId = request.getParameter(ssc.getInputCreatorId());

            if (report.getCreatorId() == null) {
                report.setCreatorId(creatorId);
            } else {
                report.setChangedBy(creatorId);
            }

            // store entities
            List<ReportEntity> reportEntities = new ArrayList<ReportEntity>();

            String[] entities = request.getParameterValues(ssc.getInputEntityId());
            for (int ie = 0; ie < entities.length; ie++) {
                log.debug("parameter " + ie + ": " + entities[ie]);

                String entityId = entities[ie];

                if (entityId.contains(",")) {
                    String[] entityIds = entityId.split(",");
                    for (int i = 0; i < entityIds.length; i++) {
                        ReportEntity re = new ReportEntity();
                        re.setEntityId(entityIds[i]);

                        if (request.getParameter(ssc.getInputEntityTypeId()) == null || request.getParameter(ssc.getInputEntityTypeId()).length() == 0) {
                            re.setEntityTypeId("1");
                        } else {
                            re.setEntityTypeId(request.getParameter(ssc.getInputEntityTypeId()));
                        }

                        if (request.getParameter(ssc.getInputPermissionId()) == null || request.getParameter(ssc.getInputPermissionId()).length() == 0) {
                            re.setPermissionId("1");
                        } else {
                            re.setPermissionId(request.getParameter(ssc.getInputPermissionId()));
                        }

                        reportEntities.add(re);
                    }
                } else {
                    ReportEntity re = new ReportEntity();
                    re.setEntityId(entityId);

                    if (request.getParameter(ssc.getInputEntityTypeId()) == null || request.getParameter(ssc.getInputEntityTypeId()).length() == 0) {
                        re.setEntityTypeId("1");
                    } else {
                        re.setEntityTypeId(request.getParameter(ssc.getInputEntityTypeId()));
                    }

                    if (request.getParameter(ssc.getInputPermissionId()) == null || request.getParameter(ssc.getInputPermissionId()).length() == 0) {
                        re.setPermissionId("1");
                    } else {
                        re.setPermissionId(request.getParameter(ssc.getInputPermissionId()));
                    }

                    reportEntities.add(re);
                }
            }

            report.setReportEntities(reportEntities);

            List<SearchParam> searchParams = new ArrayList<SearchParam>();
            Map<String, Object> sps = form.getSearchParams();
            if (searchParams != null) {
                for (Map.Entry<String, Object> entry : sps.entrySet()) {
                    log.debug("searchParam: name=" + entry.getKey() + " value=" + entry.getValue());

                    /*SearchParameterMetaData spmd = smd.getSearchParameter(entry.getKey());
                    log.debug("\n\n\n\n\n" + spmd.getName() + " - " + spmd.getSave() + "\n\n\n\n\n");*/

                    SearchParam sp = new SearchParam();
                    sp.setName(entry.getKey());
                    sp.setValue(entry.getValue().toString());
                    searchParams.add(sp);
                }

                report.setSearchParams(searchParams);
            }

            List<ResultParam> resultParams = new ArrayList<ResultParam>();
            if (form.getSelectedResultParams() != null) {
                List<String> rps = Arrays.asList(form.getSelectedResultParams());
                int i = 0;
                for (String resultParam : rps) {
                    log.debug("resultParam: name=" + resultParam);

                    ResultParam rp = new ResultParam();
                    rp.setName(resultParam);
                    rp.setDisplayOrder(i++);

                    resultParams.add(rp);
                }

                report.setResultParams(resultParams);
            }

            List<OrderParam> orderParams = new ArrayList<OrderParam>();
            if (form.getResultOrder() != null) {
                List<String> ops = Arrays.asList(form.getResultOrder());
                int i = 0;
                for (String orderParam : ops) {
                    log.debug("orderParam: name=" + orderParam);

                    OrderParam op = new OrderParam();
                    op.setName(orderParam);
                    op.setDisplayOrder(i++);
                    op.setOrderType(Integer.toString(form.getOrderType()));
                    orderParams.add(op);
                }

                log.debug("orderType: " + form.getOrderType());

                report.setOrderParams(orderParams);
            }

            /*
             * Gravação do relatório personalizado
             */
            if (validationErrors == null || validationErrors.isEmpty()) {
                report.save();

                if (log.isDebugEnabled()) {
                    log.debug("report.getReportCount(): " + report.getReportCount());
                    log.debug("report.getReportName(): " + report.getReportName());
                    log.debug("report.getId(): " + report.getId());
                }
            }
        } catch (Exception e) {
            log.error(e);
        }

        log.debug("end");

        return report;
    }

    /**
     * manageOperations
     */
    protected boolean manageOperations(HttpServletRequest request,
            HttpServletResponse response,
            Map<String, Object> parameterMap) {

        boolean executeSearch = true;
        boolean forwardToUrl = true;

        SearchShareConfig ssc = SearchShareConfig.getInstance();
        if (request.getParameter(ssc.getSubmitName()) != null) {
            Report report = saveSearch(request, response);

            SearchShareHelper.fillParameterMap(report, parameterMap);
            /*request.setAttribute("inputReportName", report.getReportName());
            if (report.getId() != null) {
            request.setAttribute("inputReportId", report.getId().toString());
            }*/
        }

        if (request.getParameter(ssc.getOperation()) != null) {

            if (OPERATION_EXECUTE.equals(request.getParameter(ssc.getOperation()))) {
                log.debug("\n\n\nOPERATION EXECUTE\n\n\n");
                forwardToUrl = false;

                Report report = (new Report()).find(Long.parseLong(request.getParameter(ssc.getReportId())));
                log.debug("\n\n\n\n\n\n\nreport to execute : " + report + "\n\n\n\n\n\n");

                SearchShareHelper.fillParameterMap(report, parameterMap);
                // parameterMap.put("prepare", "true");

            } else if (OPERATION_REMOVE.equals(request.getParameter(ssc.getOperation()))) {
                log.debug("\n\n\nOPERATION REMOVE\n\n\n");
                executeSearch = false;
                Report report = new Report();
                report.setId(Long.parseLong(request.getParameter(ssc.getReportId())));

                try {
                    report.remove();
                } catch (Exception e) {
                    log.debug(e);
                }
            }

            if (forwardToUrl) {
                log.debug("request.getPathInfo: " + request.getPathInfo());
                log.debug("request.getRequestURI: " + request.getRequestURI());
                log.debug("request.getContextPath: " + request.getContextPath());
                log.debug("request: " + request.toString());

                String url = request.getParameter(ssc.getUrl());
                log.debug("url: " + url);
                if (url != null) {
                    url.replaceAll("'", "");
                }

                try {
                    redirect(response, url != null ? url : "");
                } catch (Exception e) {
                    log.error(e);
                    log.error(e.getStackTrace());
                }
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
        if (log.isDebugEnabled()) {
            log.debug("doGet");
        }



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
        if (log.isDebugEnabled()) {
            log.debug("doPost");
        }

        Map<String, Object> parameterMap = new HashMap<String, Object>();
        if (manageOperations(request, response, parameterMap)) {
            if (!parameterMap.isEmpty()) {
                for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                    log.debug("parameter name=" + entry.getKey() + " value=" + entry.getValue());
                }
            }

            super.doPost(request, response, parameterMap);
        }
    }
}
