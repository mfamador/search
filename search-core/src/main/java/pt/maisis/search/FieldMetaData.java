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

import java.util.List;
import java.util.ArrayList;

public class FieldMetaData {

    private List fields = new ArrayList();

    public FieldMetaData() {
    }

    public List getFields() {
        return this.fields;
    }

    public String getField(final int index) {
        return (String) this.fields.get(index);
    }

    public void addField(final String field) {
        this.fields.add(field);
    }

    public void addFieldMetaData(final FieldMetaData field) {
        this.fields.addAll(field.getFields());
    }

    public String toString() {
        return new StringBuffer("FieldMetaData[").append("fields=").append(this.fields).append("]").toString();
    }
}
