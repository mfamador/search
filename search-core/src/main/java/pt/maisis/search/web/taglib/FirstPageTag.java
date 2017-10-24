package pt.maisis.search.web.taglib;

import pt.maisis.search.util.MessageResources;

import javax.servlet.jsp.JspException;

/**
 * Tag que permite fazer o rendering do corpo caso a página actual
 * não seja a primeira.
 * Semelhante à PreviousPageTag. No entanto, os atributos URL e START
 * correspondem à primeira página e não a página imediatamente anterior.
 *
 * TODO: criar uma base tag!!!
 *
 * @version 1.0
 */
public class FirstPageTag extends TagSupport {

    public FirstPageTag() {
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
            int start = 0;
            setAttribute(PageableSearchResultTag.START, start);
            setAttribute(PageableSearchResultTag.URL, parent.getURL(getAction(), getRefreshCount(), start));
            return EVAL_BODY_INCLUDE;
        }

        return SKIP_BODY;
    }
}
