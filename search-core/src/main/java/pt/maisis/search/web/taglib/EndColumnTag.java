package pt.maisis.search.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

public class EndColumnTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        Tag parent;

        if (!((parent = getParent()) instanceof SearchMetaDataTag)) {
            throw new JspException("A tag 'endColumn' deve ter como pai a tag 'searchMetaData'");
        }

        boolean handleEndColumn =
                ((SearchMetaDataTag) parent).handleEndColumn();

        if (handleEndColumn) {
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }
}
