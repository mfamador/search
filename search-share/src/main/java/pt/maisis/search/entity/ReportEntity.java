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
