package pt.maisis.search.web.taglib;

import pt.maisis.search.util.MessageResources;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

public class EndCompositeTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        Tag parent;

        if (!((parent = getParent()) instanceof SearchMetaDataTag)) {
            throw new JspException(MessageResources.getInstance().getMessage("tag.invalid.parent"));
        }

        boolean handleEndComposite =
                ((SearchMetaDataTag) parent).handleEndComposite();

        if (handleEndComposite) {
            setAttribute("container",
                    ((SearchMetaDataTag) parent).getCompositeContainer());
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }
}
