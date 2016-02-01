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
