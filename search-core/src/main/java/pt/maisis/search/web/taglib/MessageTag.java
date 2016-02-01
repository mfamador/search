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

import java.io.IOException;
import javax.servlet.jsp.JspException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.config.SearchConfig;
import pt.maisis.search.util.MessageResources;

/**
 * Class description goes here.
 *
 * @version 1.0
 * @author Marco Amador
 */
public class MessageTag extends SearchBaseTag {

    private static Log log = LogFactory.getLog(MessageTag.class);
    private String key;

    public String getKey() {
        return this.key;
    }

    public void setKey(final String key) {
        this.key = key;
    }
    private String var;

    public String getVar() {
        return this.var;
    }

    public void setVar(final String var) {
        this.var = var;
    }

    @Override
    public int doStartTag() throws JspException {
        if (key != null && var == null) {
            try {
                pageContext.getOut().write(MessageResources.getInstance(pageContext.getRequest().getLocale(), 
                        SearchConfig.getInstance().getMessageResources()).getMessage(key));
            } catch (Exception e) {
                log.error(e);
            }
        } else if (key != null) {
            pageContext.setAttribute(var, MessageResources.getInstance(pageContext.getRequest().getLocale(), 
                        SearchConfig.getInstance().getMessageResources()).getMessage(key));
        }

        return 0;
    }
}
