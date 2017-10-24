package pt.maisis.search.web.taglib;

import pt.maisis.search.util.MessageResources;

import javax.servlet.jsp.JspException;

/**
 *
 * @version 1.0
 */
public class NextGroupTag extends TagSupport {

    public NextGroupTag() {
    }

    @Override
    public int doStartTag() throws JspException {
        PageableSearchResultTag parent = (PageableSearchResultTag) findAncestorWithClass(this, PageableSearchResultTag.class);

        if (parent == null) {
            throw new JspException(MessageResources.getInstance().getMessage("tag.invalid.parent"));
        }

        int numberVisiblePages = parent.getNumberVisiblePages();
        int startPage = parent.getStartPage();
        int numberPages = parent.getNumberPages();
        int count = parent.getCount();

        if (startPage - 1 + numberVisiblePages < numberPages) {
            int start = (startPage - 1 + numberVisiblePages) * count;
            setAttribute(PageableSearchResultTag.START, start);
            setAttribute(PageableSearchResultTag.URL, parent.getURL(start));
            return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    }
}
