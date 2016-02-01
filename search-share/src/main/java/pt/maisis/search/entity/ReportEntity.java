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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 *
 * @author amador
 */
@Entity
public class ReportEntity implements Serializable {

    @EmbeddedId
    protected ReportEntityPK reportEntityPK = new ReportEntityPK();
    private String permissionId;

    public String getEntityTypeId() {
        return this.reportEntityPK.getEntityTypeId();
    }

    public void setEntityTypeId(String entityTypeId) {
        this.reportEntityPK.setEntityTypeId(entityTypeId);
    }

    public String getEntityId() {
        return this.reportEntityPK.getEntityId();
    }

    public void setEntityId(String entityId) {
        this.reportEntityPK.setEntityId(entityId);
    }

    public Report getReport() {
        return this.reportEntityPK.getReport();
    }

    public void setReport(Report report) {
        this.reportEntityPK.setReport(report);
    }

    public ReportEntityPK getReportEntityPK() {
        return reportEntityPK;
    }

    public void setReportEntityPK(ReportEntityPK reportEntityPK) {
        this.reportEntityPK = reportEntityPK;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public String toString() {
        return "ReportEntity{"
                + " entityTypeId=" + this.reportEntityPK.getEntityTypeId()
                + " entityId=" + this.reportEntityPK.getEntityId()
                + " permissionId=" + permissionId + '}';
    }
}
