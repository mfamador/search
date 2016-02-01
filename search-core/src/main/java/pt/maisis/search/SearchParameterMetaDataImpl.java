/*
 * %W% %E% Marco Amador
 *
 * Copyright (c) 1994-2011 Maisis - Information Systems. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of MAISIS
 * Information Systems, Lda. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with MAISIS.
 *
 * MAISIS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MAISIS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package pt.maisis.search;

public class SearchParameterMetaDataImpl
        extends DynamicParameterMetaDataImpl
        implements SearchParameterMetaData {

    private String field;
    private String operator;
    private ContainerMetaData cmd;
    private SearchMetaData smd;

    public SearchParameterMetaDataImpl() {
    }

    public String getField() {
        return this.field;
    }

    public void setField(final String field) {
        this.field = field;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(final String operator) {
        this.operator = operator;
    }

    public ContainerMetaData getContainer() {
        return this.cmd;
    }

    public void setContainer(final ContainerMetaData cmd) {
        this.cmd = cmd;

        AbstractContainerMetaData acmd = (AbstractContainerMetaData) cmd;
        acmd.setName(getName());
        acmd.setSearchParameterMetaData(this);
    }

    public void setSearchMetaData(final SearchMetaData smd) {
        this.smd = smd;
    }

    public SearchMetaData getSearchMetaData() {
        return this.smd;
    }

    public String toString() {
        return new StringBuffer("SearchParameterMetaDataImpl[").append("name=").append(getName()).append(", field=").append(this.field).append(", operator=").append(this.operator).append(", cmd=").append(this.cmd).append("]").toString();
    }
}
