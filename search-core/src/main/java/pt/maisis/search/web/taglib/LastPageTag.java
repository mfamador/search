package pt.maisis.search.web.taglib;

import pt.maisis.search.util.MessageResources;

import javax.servlet.jsp.JspException;

/**
 *
 * TODO: criar uma base tag!!!
 *
 * @version 1.0
 */
public class LastPageTag extends TagSupport {

    public LastPageTag() {
    }

    @Override
    public int doStartTag() throws JspException {

        PageableSearchResultTag parent = (PageableSearchResultTag) findAncestorWithClass(this, PageableSearchResultTag.class);

        if (parent == null) {
            throw new JspException(MessageResources.getInstance().getMessage("tag.invalid.parent"));
        }

        int currentPage = parent.getCurrentPage();
        int totalPages = parent.getNumberPages();
        int count = parent.getCount();

        if (currentPage < totalPages) {
            int start = (totalPages - 1) * count;
            setAttribute(PageableSearchResultTag.START, start);
            setAttribute(PageableSearchResultTag.URL, parent.getURL(getAction(), getRefreshCount(), start));
            return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    }
}
