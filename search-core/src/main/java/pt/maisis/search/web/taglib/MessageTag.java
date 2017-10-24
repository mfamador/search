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
