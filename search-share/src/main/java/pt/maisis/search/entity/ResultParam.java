package pt.maisis.search.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 *
 * @author amador
 */
@Entity
public class ResultParam implements Serializable, Comparable {

    @EmbeddedId
    protected ResultParamPK resultParamPK = new ResultParamPK();
    private Integer displayOrder;

    public String getName() {
        return this.resultParamPK.getName();
    }

    public void setName(String name) {
        this.resultParamPK.setName(name);
    }

    public Report getReport() {
        return this.resultParamPK.getReport();
    }

    public void setReport(Report report) {
        this.resultParamPK.setReport(report);
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public ResultParamPK getResultParamPK() {
        return resultParamPK;
    }

    public void setResultParamPK(ResultParamPK resultParamPK) {
        this.resultParamPK = resultParamPK;
    }

    @Override
    public String toString() {
        return "ResultParam{"
                + " name=" + this.resultParamPK.getName()
                + " displayOrder=" + displayOrder + '}';
    }

    public int compareTo(Object o) {
        if (!(o instanceof ResultParam)) {
            throw new ClassCastException("A ResultParam object expected.");
        }
        int oDisplayOrder = ((ResultParam) o).getDisplayOrder();
        return this.displayOrder - oDisplayOrder;
    }
}
