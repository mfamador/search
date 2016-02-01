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
package pt.maisis.search.web.taglib;

import java.util.Iterator;
import javax.servlet.jsp.JspException;

public abstract class ResultMetaDataBaseTag extends SearchBaseTag {

    private Iterator rpmds;
    private String attributeName;

    public ResultMetaDataBaseTag() {
    }

    @Override
    public int doStartTag() throws JspException {
        this.rpmds = getResultParamsIterator();
        this.attributeName = getAttributeName();

        if (this.rpmds == null || !this.rpmds.hasNext()) {
            return SKIP_BODY;
        }
        setAttribute(this.attributeName, this.rpmds.next());
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspException {
        if (this.rpmds.hasNext()) {
            pageContext.setAttribute(this.attributeName, this.rpmds.next());
            return EVAL_BODY_AGAIN;
        }

        return SKIP_BODY;
    }

    public abstract String getAttributeName();

    public abstract Iterator getResultParamsIterator();
}
