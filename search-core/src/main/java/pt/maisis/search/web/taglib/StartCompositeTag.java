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

import pt.maisis.search.web.LocaleConfig;
import pt.maisis.search.ContainerMetaData;
import pt.maisis.search.util.MessageResources;
import pt.maisis.search.util.ApplicationResources;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public class StartCompositeTag extends TagSupport {
    public static final String LABEL = "label";

    @Override
    public int doStartTag() throws JspException {

        Tag parent;

        if (!((parent = getParent()) instanceof SearchMetaDataTag)) {
	    MessageResources messages = MessageResources.getInstance();
            throw new JspException(messages.getMessage("tag.invalid.parent"));
        }

        boolean handleStartComposite =
            ((SearchMetaDataTag) parent).handleStartComposite();

        if (handleStartComposite) {
	    ContainerMetaData container = ((SearchMetaDataTag) parent).getCompositeContainer();
            setAttribute("container", container);

	    Locale locale = LocaleConfig.getLocale((HttpServletRequest)pageContext.getRequest());
	    ApplicationResources messages = ApplicationResources.getInstance(locale);
            setAttribute(LABEL, messages.getMessage(container.getLabel()));

            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }
}
