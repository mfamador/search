package pt.maisis.search.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 *
 * @author amador
 */
@Entity
public class SearchParam implements Serializable {

    @EmbeddedId
    protected SearchParamPK searchParamPK = new SearchParamPK();
    private String value;

    public String getName() {
        return this.searchParamPK.getName();
    }

    public void setName(String name) {
        this.searchParamPK.setName(name);
    }
    
    public Report getReport() {
        return this.searchParamPK.getReport();
    }

    public void setReport(Report report) {
        this.searchParamPK.setReport(report);
    }

    public SearchParamPK getSearchParamPK() {
        return searchParamPK;
    }

    public void setSearchParamPK(SearchParamPK searchParamPK) {
        this.searchParamPK = searchParamPK;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SearchParam{"
                + " name=" + this.searchParamPK.getName()
                + " value=" + value + '}';
    }
}
