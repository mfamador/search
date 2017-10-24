package pt.maisis.search.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author amador
 */
@Embeddable
public class ReportEntityPK implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Report report;
    @Column(nullable = false)
    private String entityTypeId;
    @Column(nullable = false)
    private String entityId;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityTypeId() {
        return entityTypeId;
    }

    public void setEntityTypeId(String entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
