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
