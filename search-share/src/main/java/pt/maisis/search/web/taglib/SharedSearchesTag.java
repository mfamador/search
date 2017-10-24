package pt.maisis.search.web.taglib;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.entity.Report;
import pt.maisis.search.entity.ReportEntity;
import pt.maisis.search.service.ReportService;

/**
 *
 * @author amador
 */
public class SharedSearchesTag extends BaseTag {

    private static Log log = LogFactory.getLog(SharedSearchesTag.class);
    private static final String REPORTS = "reports";
    private static final String TOTAL = "total";
    private static final String START = "start";
    private static final String COUNT = "count";
    private static final String PREVIOUS_START = "previousStart";
    private static final String NEXT_START = "nextStart";
    private Integer start;
    private Integer count;
    private String creator;
    private String changedBy;
    private String entity;
    private String entityType;
    private String search;
    private String reportName;
    private String reportType;
    private int state = Report.STATE_ACTIVE;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    /*
     * doTag
     */
    @Override
    public void doTag() throws JspException, IOException {
        JspFragment body = getJspBody();

        if (body != null) {

            if (count == 0) {
                count = 5;
            }
            Map<String, Object> criteria = new HashMap<String, Object>();
            if (search != null) {
                log.debug("search = " + search);
                criteria.put(Report.SEARCH_NAME, search);
            }

            if (creator != null) {
                log.debug("creator = " + creator);
                criteria.put(Report.CREATOR_ID, creator);
            }

            if (changedBy != null) {
                log.debug("changedBy = " + changedBy);
                criteria.put(Report.CHANGED_BY, changedBy);
            }

            if (reportName != null) {
                log.debug("reportName = " + reportName);
                criteria.put(Report.REPORT_NAME, reportName);
            }

            if (reportType != null) {
                log.debug("reportType = " + reportType);
                criteria.put(Report.REPORT_TYPE, reportType);
            }

            log.debug("state = " + state);
            criteria.put(Report.STATE, new Integer(state));

            Map<String, String> orderCriteria = new HashMap<String, String>();
            orderCriteria.put(Report.CREATION_DATE, "desc");

            ReportService rs = new ReportService();
            if (start != null && count != null) {
                int total = 0;

                if (entity != null || entityType != null) {
                    ReportEntity reportEntity = new ReportEntity();
                  
                    if(entity != null) {
                        reportEntity.setEntityId(entity);
                    }
                    if(entityType != null) {
                        reportEntity.setEntityTypeId(entityType);
                    }
                    
                    total = rs.count(reportEntity, criteria);
                } else {
                    total = rs.count(criteria);
                }

                log.debug("count = " + count);
                log.debug("total = " + total);
                log.debug("start = " + start);

                if (start >= total) {
                    start = total - count;
                }

                Integer previousStart;
                Integer nextStart;
                if (start > 0) {
                    previousStart = Math.max(0, start - count);
                    setAttribute(PREVIOUS_START, previousStart);
                }

                if (start < total - count) {
                    nextStart = start + count;
                    setAttribute(NEXT_START, nextStart);
                }

                List<Report> reports = null;
                if (entity != null || entityType != null) {
                    ReportEntity reportEntity = new ReportEntity();
                    
                    if(entity != null) {
                        reportEntity.setEntityId(entity);
                    }
                    if(entityType != null) {
                        reportEntity.setEntityTypeId(entityType);
                    }
                    
                    reports = rs.find(reportEntity, start, count, criteria, orderCriteria);
                } else {
                    reports = rs.find(start, count, criteria, orderCriteria);
                }

                setAttribute(REPORTS, reports);
                setAttribute(TOTAL, total);
                setAttribute(START, start);
                setAttribute(COUNT, count);
                invoke(body);
                removeAttribute(REPORTS);
                removeAttribute(TOTAL);
                removeAttribute(START);
                removeAttribute(COUNT);
                removeAttribute(PREVIOUS_START);
                removeAttribute(NEXT_START);
            } else {
                List<Report> reports = null;
                if (entity != null || entityType != null) {
                    ReportEntity reportEntity = new ReportEntity();
                    if(entity != null) {
                        reportEntity.setEntityId(entity);
                    }
                    if(entityType != null) {
                        reportEntity.setEntityTypeId(entityType);
                    }
                    reports = rs.find(reportEntity, criteria, orderCriteria);
                } else {
                    reports = rs.find(criteria, orderCriteria);
                }

                setAttribute(REPORTS, reports);
                invoke(body);
                removeAttribute(REPORTS);
            }
        }
    }
}
