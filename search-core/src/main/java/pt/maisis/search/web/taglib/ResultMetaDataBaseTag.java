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
