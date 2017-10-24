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
