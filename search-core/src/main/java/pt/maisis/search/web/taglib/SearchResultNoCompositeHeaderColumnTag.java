package pt.maisis.search.web.taglib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.jsp.JspException;

import pt.maisis.search.Result;
import pt.maisis.search.ResultParameterMetaData;
import pt.maisis.search.util.MessageResources;

/**
 * Simples tag que faz o set do resultado de uma pesquisa 
 * ({@link pt.maisis.search.SearchResult}) no contexto da página.
 *
 * @version 1.0
 */
public class SearchResultNoCompositeHeaderColumnTag extends TagSupport {

    public static final String RESULT_PARAMETER = "resultParam";
    private SearchResultTag parent;

    public SearchResultNoCompositeHeaderColumnTag() {
    }
    private Iterator i;

    public int doStartTag() throws JspException {
        List lista = new ArrayList();

        this.parent = (SearchResultTag) findAncestorWithClass(this, SearchResultTag.class);

        if (this.parent == null) {
            throw new JspException(MessageResources.getInstance().getMessage("tag.invalid.parent"));
        }

        Result result = parent.getResult();

        if (result != null) {
            setAttributes(result.getResultMetaData(), lista);
            if (!lista.isEmpty()) {
                i = lista.iterator();
                setAttribute(RESULT_PARAMETER, i.next());
                return EVAL_BODY_INCLUDE;
            }
        }
        return SKIP_BODY;
    }

    protected void setAttributes(List nextRowList, List lista) throws JspException {
        Iterator iter = nextRowList.iterator();
        while (iter.hasNext()) {
            ResultParameterMetaData rpmd =
                    (ResultParameterMetaData) iter.next();

            if (!rpmd.isComposite()) {
                lista.add(rpmd);
            } else {
                if (rpmd.getChildren().size() > 0) {
                    setAttributes(rpmd.getChildren(), lista);
                }
            }
        }
    }

    public int doAfterBody() throws JspException {
        if (i.hasNext()) {
            setAttribute(RESULT_PARAMETER, i.next());
            return EVAL_BODY_AGAIN;
        }
        return SKIP_BODY;
    }
}
