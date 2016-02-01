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

import pt.maisis.search.validator.Validatable;

/**
 * Abstract class that implements the 
 * {@link ContainerMetaData} interface methods common to
 * simple e composite containers types.
 *
 * @version 1.0
 */
public abstract class AbstractContainerMetaData implements ContainerMetaData {

    private String name;
    private String label;
    private CompositeContainerMetaData parent;
    private SearchParameterMetaData spmd;
    private Validatable validatable;

    public AbstractContainerMetaData() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public ContainerMetaData getParent() {
        return this.parent;
    }

    public void setParent(final CompositeContainerMetaData parent) {
        this.parent = parent;
    }

    public SearchParameterMetaData getSearchParameterMetaData() {
        if (this.parent == null) {
            return this.spmd;
        }
        return this.parent.getSearchParameterMetaData();
    }

    public void setSearchParameterMetaData(final SearchParameterMetaData spmd) {
        this.spmd = spmd;
    }

    public Validatable getValidatable() {
        return this.validatable;
    }

    public void setValidatable(final Validatable validatable) {
        this.validatable = validatable;
    }
}
