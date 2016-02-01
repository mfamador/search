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

public class AjaxParameterMetaData {

    private List parameters = new ArrayList();

    public AjaxParameterMetaData() {
    }

    public List getParameters() {
        return this.parameters;
    }

    public String getParameter(final int index) {
        return (String) this.parameters.get(index);
    }

    public void addParameter(final String parameter) {
        this.parameters.add(parameter);
    }

    public void addParameterMetaData(final AjaxParameterMetaData parameter) {
        this.parameters.addAll(parameter.getParameters());
    }

    public String toString() {
        return new StringBuffer("AjaxParameterMetaData[").append("parameters=").append(this.parameters).append("]").toString();
    }
}
