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

import java.io.Serializable;

/**
 * Classe que representa um campo de retorno necessário no 
 * resultado de uma dada pesquisa.
 *
 * @version 1.0
 */
public class RequiredResultParameterMetaData
        extends DynamicParameterMetaDataImpl
        implements Serializable {

    private FieldMetaData field;
    private String depends;
    private String unless;

    public RequiredResultParameterMetaData() {
    }

    public void setFieldMetaData(final FieldMetaData field) {
        this.field = field;
    }

    public FieldMetaData getFieldMetaData() {
        return this.field;
    }

    public String getDepends() {
        return this.depends;
    }

    public void setDepends(final String depends) {
        this.depends = depends;
    }

    public String getUnless() {
        return this.unless;
    }

    public void setUnless(final String unless) {
        this.unless = unless;
    }

    public String toString() {
        return new StringBuffer("RequiredResultParameterMetaData[").append("name=").append(super.name).append(", field=").append(this.field).append(", depends=").append(this.depends).append(", unless=").append(this.unless).append("]").toString();
    }
}
