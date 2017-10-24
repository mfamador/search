package pt.maisis.search.web;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.Constants;
import pt.maisis.search.SearchEngine;
import pt.maisis.search.SearchMetaData;
import pt.maisis.search.SearchParameterMetaData;
import pt.maisis.search.config.SearchShareConfig;
import pt.maisis.search.entity.OrderParam;
import pt.maisis.search.entity.Report;
import pt.maisis.search.entity.ReportEntity;
import pt.maisis.search.entity.ResultParam;
import pt.maisis.search.entity.SearchParam;

/**
 *
 * @author amador
 */
public class SearchShareHelper {
    
    private static Log log = LogFactory.getLog(SearchShareHelper.class);
    
    public static long daysBetween(Calendar startDate, Calendar endDate) {
        Calendar date = (Calendar) startDate.clone();
        long daysBetween = 0;
        while (date.before(endDate)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }
    
    static String transformSlidingWindowsDate(Report report, String original_value, SearchParameterMetaData spmd) {
        String value = original_value;
        
        Timestamp comparableDate = report.getChangeDate() != null ? report.getChangeDate() : report.getCreationDate();
        
        String pattern = "yyyy-MM-dd";
        
        Calendar reportCal = Calendar.getInstance();
        if (comparableDate != null) {
            reportCal.setTime(new Date(comparableDate.getTime()));
        }
        reportCal.set(Calendar.HOUR_OF_DAY, 0);
        reportCal.set(Calendar.MINUTE, 0);
        reportCal.set(Calendar.SECOND, 0);
        reportCal.set(Calendar.MILLISECOND, 0);
        
        Date reportDate = reportCal.getTime();
        log.debug("report date: " + reportDate);
        
        if (spmd.getProperty(Constants.SLIDING_WINDOW_PATTERN) != null) {
            pattern = spmd.getProperty(Constants.SLIDING_WINDOW_PATTERN);
            
            log.debug("pattern: " + pattern);
        }
        
        if (Constants.YEAR_SLIDING_WINDOW.equalsIgnoreCase(spmd.getProperty(Constants.SLIDING_WINDOW))) {
            /* YEAR */
            log.debug("year");
            
        } else if (Constants.MONTH_SLIDING_WINDOW.equalsIgnoreCase(spmd.getProperty(Constants.SLIDING_WINDOW))) {
            /* MONTH */
            log.debug("month");
            
        } else if (Constants.DAY_SLIDING_WINDOW.equalsIgnoreCase(spmd.getProperty(Constants.SLIDING_WINDOW))) {
            /* DAY */
            log.debug("day");
            
            Calendar filterCal = Calendar.getInstance();
            DateFormat timeFormat = new SimpleDateFormat(pattern);
            if (original_value != null) {
                try {
                    filterCal.setTime(timeFormat.parse(original_value));
                    filterCal.clear(Calendar.HOUR_OF_DAY);
                    filterCal.clear(Calendar.MINUTE);
                    filterCal.clear(Calendar.SECOND);
                    filterCal.clear(Calendar.MILLISECOND);
                } catch (ParseException e) {
                }
            }
            
            log.debug("report date: " + reportCal.getTime());
            log.debug("filter date: " + filterCal.getTime());
            
            long diff = (filterCal.compareTo(reportCal) < 0 ? -daysBetween(filterCal, reportCal) : daysBetween(reportCal, filterCal));
            
            log.debug("diff: " + diff);
            
            Calendar cal = Calendar.getInstance();
            cal.clear(Calendar.HOUR_OF_DAY);
            cal.clear(Calendar.MINUTE);
            cal.clear(Calendar.SECOND);
            cal.clear(Calendar.MILLISECOND);
            cal.add(Calendar.DATE, (int) diff);
            
            log.debug("new filter date: " + cal.getTime());
            
            value = timeFormat.format(cal.getTime());
        } else if (Constants.HOUR_SLIDING_WINDOW.equalsIgnoreCase(spmd.getProperty(Constants.SLIDING_WINDOW))) {
            /* HOUR */
            log.debug("hour");
            
        } else if (Constants.DATEHOUR_SLIDING_WINDOW.equalsIgnoreCase(spmd.getProperty(Constants.SLIDING_WINDOW))) {
            /* DATEHOUR*/
            log.debug("datehour");
            
        } else {
            log.debug("invalid sliding window property");
            
        }
        
        return value;
    }

    /*
     * Fill request Search Form
     */
    public static void fillForm(Report report, SearchForm form) {
        // fill form
        form.setSearch(report.getSearchName());
        /*if (report.getReportCount() != null && report.getReportCount() > 0) {
            form.setCount(report.getReportCount());
        }*/
        
        SearchEngine se = SearchEngine.getInstance();
        SearchMetaData smd = se.getSearch(report.getSearchName());
        
        if (report.getResultParams() != null && !report.getResultParams().isEmpty()) {
            List<ResultParam> resultParams = report.getResultParams();
            Collections.sort(resultParams);
            
            List<String> rpns = new ArrayList<String>(Arrays.asList(smd.getResultParameterNames()));
            
            List srps = new ArrayList<String>();
            for (ResultParam resultParam : resultParams) {
                srps.add(resultParam.getName());
                rpns.remove(resultParam.getName());
            }
            
            form.setSelectedResultParams((String[]) srps.toArray(new String[srps.size()]));
            form.setResultParams((String[]) rpns.toArray(new String[rpns.size()]));
        } else {
            form.setSelectAllResultParams(true);
        }

        /*
         * fill order params
         */
        if (report.getOrderParams() != null && !report.getOrderParams().isEmpty()) {
            List<OrderParam> orderParams = report.getOrderParams();
            Collections.sort(orderParams);
            
            String orderType = "0";
            List ops = new ArrayList<String>();
            for (OrderParam orderParam : orderParams) {
                ops.add(orderParam.getName());
                orderType = orderParam.getOrderType();
            }
            
            form.setResultOrder((String[]) ops.toArray(new String[ops.size()]));
            form.setOrderType(Integer.parseInt(orderType));
        }
        
        if (report.getSearchParams() != null) {
            for (SearchParam searchParam : report.getSearchParams()) {
                
                String value = searchParam.getValue();
                if (Boolean.TRUE.equals(report.getSlidingWindow())) {
                    
                    SearchParameterMetaData spmd = smd.getSearchParameter(searchParam.getName());
                    if (spmd != null
                            && spmd.getProperty(Constants.SLIDING_WINDOW) != null) {
                        log.debug("\n\n\n\n\n\n\n");
                        log.debug("report-sliding-window: " + report.getSlidingWindow());
                        log.debug("param sliding-window: " + spmd.getProperty(Constants.SLIDING_WINDOW));
                        log.debug("original-value: " + value);
                        value = transformSlidingWindowsDate(report, value, spmd);
                        log.debug("transformed-value: " + value);
                        log.debug("\n\n\n\n\n\n\n");
                    }
                }
                
                form.setSearchParam(searchParam.getName(), value);
            }
        }
        
    }

    /*
     * Fill request parameter map
     */
    static void fillParameterMap(Report report, Map<String, Object> parameterMap) {
        SearchShareConfig ssc = SearchShareConfig.getInstance();

        /*
         * fill request
         */
        parameterMap.put(Constants.SEARCH, report.getSearchName());
        if (report.getId() != null) {
            parameterMap.put(Constants.SEARCH_SHARE_REPORT_ID, report.getId().toString());
            parameterMap.put(ssc.getInputReportId(), report.getId().toString());
        }
        parameterMap.put(Constants.SEARCH_SHARE_REPORT_NAME, report.getReportName());
        parameterMap.put(Constants.SEARCH_SHARE_REPORT_TYPE, report.getReportType());
        if (report.getReportCount() != null && report.getReportCount() > 0) {
            parameterMap.put(Constants.SEARCH_SHARE_COUNT, report.getReportCount());
        }
        parameterMap.put(Constants.SEARCH_SHARE_SLIDING_WINDOW, report.getSlidingWindow());
        parameterMap.put(ssc.getInputReportName(), report.getReportName());
        
        List<ReportEntity> reportEntities = report.getReportEntities();
        
        if (reportEntities != null && reportEntities.size() > 0) {
            
            String[] searchShareEntityId = new String[reportEntities.size()];
            String[] searchShareEntityTypeId = new String[reportEntities.size()];
            String[] searchSharePermissionId = new String[reportEntities.size()];
            
            int i = 0;
            for (ReportEntity entity : reportEntities) {
                searchShareEntityId[i] = entity.getEntityId();
                searchShareEntityTypeId[i] = entity.getEntityTypeId();
                searchSharePermissionId[i] = entity.getPermissionId();
                i++;
            }
            
            parameterMap.put(Constants.SEARCH_SHARE_ENTITY_ID, searchShareEntityId);
            parameterMap.put(Constants.SEARCH_SHARE_ENTITY_TYPE_ID, searchShareEntityTypeId);
            parameterMap.put(Constants.SEARCH_SHARE_PERMISSION_ID, searchSharePermissionId);
        }
        
        SearchEngine se = SearchEngine.getInstance();
        SearchMetaData smd = se.getSearch(report.getSearchName());

        /*
         * fill search params
         */
        if (report.getSearchParams() != null) {
            for (SearchParam searchParam : report.getSearchParams()) {
                
                String value = searchParam.getValue();
                if (Boolean.TRUE.equals(report.getSlidingWindow())) {
                    
                    SearchParameterMetaData spmd = smd.getSearchParameter(searchParam.getName());
                    if (spmd != null
                            && spmd.getProperty(Constants.SLIDING_WINDOW) != null) {
                        log.debug("\n\n\n\n\n\n\n");
                        
                        log.debug("report-sliding-window: " + report.getSlidingWindow());
                        log.debug("param sliding-window: " + spmd.getProperty(Constants.SLIDING_WINDOW));
                        log.debug("original-value: " + value);
                        value = transformSlidingWindowsDate(report, value, spmd);
                        
                        log.debug("transformed-value: " + value);
                        log.debug("\n\n\n\n\n\n\n");
                    }
                }
                
                parameterMap.put(Constants.SEARCH_PARAM + "(" + searchParam.getName() + ")", value);
            }
        }

        /*
         * fill result params
         */
        if (report.getResultParams() != null && !report.getResultParams().isEmpty()) {
            List<ResultParam> resultParams = report.getResultParams();
            Collections.sort(resultParams);
            
            List<String> rpns = new ArrayList<String>(Arrays.asList(smd.getResultParameterNames()));
            
            List srps = new ArrayList<String>();
            for (ResultParam resultParam : resultParams) {
                srps.add(resultParam.getName());
                rpns.remove(resultParam.getName());
            }
            
            parameterMap.put(Constants.SELECTED_RESULT_PARAMS, (String[]) srps.toArray(new String[srps.size()]));
            
            parameterMap.put(Constants.RESULT_PARAMS, (String[]) rpns.toArray(new String[rpns.size()]));
        } else {
            parameterMap.put(Constants.SELECT_ALL_RESULT_PARAMS, "true");
        }

        /*
         * fill order params
         */
        if (report.getOrderParams() != null && !report.getOrderParams().isEmpty()) {
            List<OrderParam> orderParams = report.getOrderParams();
            Collections.sort(orderParams);
            
            String orderType = "0";
            List ops = new ArrayList<String>();
            for (OrderParam orderParam : orderParams) {
                ops.add(orderParam.getName());
                orderType = orderParam.getOrderType();
            }
            
            parameterMap.put(Constants.ORDER_PARAMS, (String[]) ops.toArray(new String[ops.size()]));
            parameterMap.put(Constants.ORDER_TYPE, orderType);
        }
    }
}
