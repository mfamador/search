package pt.maisis.search.web.taglib;

import pt.maisis.search.Result;

import javax.servlet.jsp.JspException;

/**
 * Simples tag que faz o set do resultado de uma pesquisa 
 * ({@link pt.maisis.search.SearchResult}) no contexto da 
 * página.
 *
 * @version 1.0
 */
public class SearchResultTag
        extends SearchBaseTag {

    public SearchResultTag() {
    }
    private boolean showHeader = false;

    public boolean isShowHeader() {
        return this.showHeader;
    }

    public void setShowHeader(final boolean showHeader) {
        this.showHeader = showHeader;
    }

    public int doStartTag() throws JspException {
        Result result = getResult();
        if (showHeader) {
            return EVAL_BODY_INCLUDE;
        }
        if (result == null || result.getSearchResult().getResultCount() == 0) {
            return SKIP_BODY;
        }
        return EVAL_BODY_INCLUDE;
    }
}
