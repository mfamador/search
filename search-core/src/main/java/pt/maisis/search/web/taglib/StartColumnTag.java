package pt.maisis.search.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

public class StartColumnTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {

        Tag parent;

        if (!((parent = getParent()) instanceof SearchMetaDataTag)) {
            throw new JspException("A tag 'startColumn' deve ter como pai a tag 'searchMetaData'");
        }

        boolean handleStartColumn =
                ((SearchMetaDataTag) parent).handleStartColumn();

        if (handleStartColumn) {
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }
}
