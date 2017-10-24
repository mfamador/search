package pt.maisis.search.web.taglib;

import pt.maisis.search.util.MessageResources;

import javax.servlet.jsp.JspException;

/**
 *
 * @version 1.0
 */
public class PreviousPageTag extends TagSupport {

    public PreviousPageTag() {
    }

    @Override
    public int doStartTag() throws JspException {

        PageableSearchResultTag parent = (PageableSearchResultTag) findAncestorWithClass(this, PageableSearchResultTag.class);

        if (parent == null) {
            throw new JspException(MessageResources.getInstance().getMessage("tag.invalid.parent"));
        }

        int currentPage = parent.getCurrentPage();
        int count = parent.getCount();

        if (currentPage > 1) {
            int start = (currentPage - 2) * count;
            setAttribute(PageableSearchResultTag.START, start);

            setAttribute(PageableSearchResultTag.URL, parent.getURL(getAction(), getRefreshCount(), start));
            return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    }
}
