package pt.maisis.search.web.taglib;

import pt.maisis.search.validator.ValidationErrors;

import javax.servlet.jsp.JspException;

/**
 * Faz o rendering do corpo da tag caso tenha ocorrido algum erro no 
 * pedido de pesquisa. Ou seja, se existir no <code>request</code> 
 * o atributo {@link SearchBaseServlet.ERRORS} com uma instância de
 * {@link SearchErrors} com erros.
 * 
 * @version 1.0
 *
 * @see pt.maisis.search.web.SearchErrorsTag
 */
public class SearchErrorsAvailableTag extends SearchBaseTag {

    @Override
    public int doStartTag() throws JspException {
        ValidationErrors errors = getValidationErrors();
        if (errors == null || errors.isEmpty()) {
            return SKIP_BODY;
        }
        return EVAL_BODY_INCLUDE;
    }
}
