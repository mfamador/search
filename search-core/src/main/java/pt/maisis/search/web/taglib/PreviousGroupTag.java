package pt.maisis.search.web.taglib;

import pt.maisis.search.util.MessageResources;

import javax.servlet.jsp.JspException;

/**
 *
 * @version 1.0
 */
public class PreviousGroupTag extends TagSupport {

    public PreviousGroupTag() {
    }

    @Override
    public int doStartTag() throws JspException {

        PageableSearchResultTag parent = (PageableSearchResultTag) findAncestorWithClass(this, PageableSearchResultTag.class);

        if (parent == null) {
            throw new JspException(MessageResources.getInstance().getMessage("tag.invalid.parent"));
        }

        int numberVisiblePages = parent.getNumberVisiblePages();
        int startPage = parent.getStartPage();
        int count = parent.getCount();

        if (startPage > numberVisiblePages) {
            int start = (startPage - 2) * count;
            setAttribute(PageableSearchResultTag.START, start);
            setAttribute(PageableSearchResultTag.URL, parent.getURL(start));
            return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    }
}
