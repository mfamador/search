package pt.maisis.search.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

public class StartRowTag extends TagSupport {

    public int doStartTag() throws JspException {

        Tag parent;

        if (!((parent = getParent()) instanceof SearchMetaDataTag)) {
            throw new JspException("A tag 'startRow' deve ter como pai a tag 'searchMetaData'");
        }

        boolean handleStartRow =
                ((SearchMetaDataTag) parent).handleStartRow();

        if (handleStartRow) {
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }
}
