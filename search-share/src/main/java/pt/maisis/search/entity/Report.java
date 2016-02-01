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
package pt.maisis.search.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author amador
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r"),
    @NamedQuery(name = "Report.findById", query = "SELECT r FROM Report r WHERE r.id = :id"),
    @NamedQuery(name = "Report.findBySearchName", query = "SELECT r FROM Report r WHERE r.searchName = :searchName"),
    @NamedQuery(name = "Report.findByReportName", query = "SELECT r FROM Report r WHERE r.reportName = :reportName"),
    @NamedQuery(name = "Report.findByReportType", query = "SELECT r FROM Report r WHERE r.reportType = :reportType"),
    @NamedQuery(name = "Report.findByCreatorId", query = "SELECT r FROM Report r WHERE r.creatorId = :creatorId"),
    @NamedQuery(name = "Report.findByChangedBy", query = "SELECT r FROM Report r WHERE r.changedBy = :changedBy"),
    @NamedQuery(name = "Report.findByCreationDate", query = "SELECT r FROM Report r WHERE r.creationDate = :creationDate"),
    @NamedQuery(name = "Report.findByChangeDate", query = "SELECT r FROM Report r WHERE r.changeDate = :changeDate"),
    @NamedQuery(name = "Report.findByState", query = "SELECT r FROM Report r WHERE r.state = :state")})
public class Report extends CrudEntity<Report, Long> implements Serializable {

    public static final String ID = "id";
    public static final String SEARCH_NAME = "searchName";
    public static final String REPORT_NAME = "reportName";
    public static final String REPORT_TYPE = "reportType";
    public static final String REPORT_COUNT = "reportCount";
    public static final String CREATOR_ID = "creatorId";
    public static final String CHANGED_BY= "changedBy";
    public static final String CREATION_DATE = "creationDate";
    public static final String CHANGE_DATE = "changeDate";
    public static final String ENTITY_ID = "entityId";
    public static final String ENTITY_TYPE = "entityType";
    public static final String STATE = "state";
    public static final int STATE_ACTIVE = 1;
    public static final int STATE_INACTIVE = 2;
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable=false)
    private String searchName;
    @Column(nullable=false)
    private String reportName;
    private String reportType;
    private Integer reportCount;
    private String creatorId;
    private String changedBy;
    private Timestamp creationDate;
    private Timestamp changeDate;
    private int state;
    private Boolean slidingWindow;
    
    @OneToMany(mappedBy = "searchParamPK.report", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SearchParam> searchParams;
    @OneToMany(mappedBy = "resultParamPK.report", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ResultParam> resultParams;
    @OneToMany(mappedBy = "orderParamPK.report", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderParam> orderParams;
    @OneToMany(mappedBy = "reportEntityPK.report", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ReportEntity> reportEntities;

    public Timestamp getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Timestamp changeDate) {
        this.changeDate = changeDate;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Integer getReportCount() {
        return reportCount;
    }

    public void setReportCount(Integer reportCount) {
        this.reportCount = reportCount;
    }
    
    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<SearchParam> getSearchParams() {
        return searchParams;
    }

    public void setSearchParams(List<SearchParam> searchParams) {
        for (SearchParam sp : searchParams) {
            sp.setReport(this);
        }
        this.searchParams = searchParams;
    }

    public List<ResultParam> getResultParams() {

        return resultParams;
    }

    public void setResultParams(List<ResultParam> resultParams) {
        for (ResultParam rp : resultParams) {
            rp.setReport(this);
        }
        this.resultParams = resultParams;
    }

    public List<OrderParam> getOrderParams() {
        return orderParams;
    }

    public void setOrderParams(List<OrderParam> orderParams) {
        for (OrderParam op : orderParams) {
            op.setReport(this);
        }
        this.orderParams = orderParams;
    }

    public List<ReportEntity> getReportEntities() {
        return reportEntities;
    }

    public void setReportEntities(List<ReportEntity> reportEntities) {
        for (ReportEntity re : reportEntities) {
            re.setReport(this);
        }
        this.reportEntities = reportEntities;
    }

    public Boolean getSlidingWindow() {
        return slidingWindow;
    }

    public void setSlidingWindow(Boolean slidingWindow) {
        this.slidingWindow = slidingWindow;
    }

    @Override
    public String toString() {
        return "Report{" + "id=" + id
                + " searchName=" + searchName
                + " reportName=" + reportName
                + " reportType=" + reportType
                + " reportCount=" + reportCount
                + " creatorId=" + creatorId
                + " changedBy=" + changedBy
                + " creationDate=" + creationDate
                + " changeDate=" + changeDate
                + " state=" + state
                + " slidingWindow=" + slidingWindow
                + " searchParams=" + searchParams
                + " resultParams=" + resultParams
                + " orderParams=" + orderParams
                + " reportEntities=" + reportEntities + '}';
    }

    @Override
    public void save() throws BusinessException {
        Date today = new java.util.Date();
        if (id == null) {
            state = STATE_ACTIVE;
            creationDate = new Timestamp(today.getTime());
        } else {
            changeDate = new Timestamp(today.getTime());
        }

        super.save();
    }
}
