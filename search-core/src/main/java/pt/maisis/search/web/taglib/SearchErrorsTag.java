package pt.maisis.search.web.taglib;

import pt.maisis.search.validator.ValidationErrors;
import pt.maisis.search.validator.ValidationError;
import pt.maisis.search.util.ApplicationResources;
import pt.maisis.search.web.LocaleConfig;

import java.util.Locale;
import java.util.Iterator;
import javax.servlet.jsp.JspException;

import javax.servlet.http.HttpServletRequest;

/**
 * Tag que coloca no contexto da página os erros ocorridos
 * no pedido de uma dada pesquisa.
 *
 * @version 1.0
 *
 * @see pt.maisis.search.web.SearchErrorsAvailableTag
 */
public class SearchErrorsTag extends SearchBaseTag {

    public static final String ERROR = "error";
    private Iterator errorsIterator;

    public int doStartTag() throws JspException {
        ValidationErrors errors = getValidationErrors();
        if (errors == null || errors.isEmpty()) {
            return SKIP_BODY;
        }

        this.errorsIterator = errors.getErrors().iterator();
        setError();

        return EVAL_BODY_INCLUDE;
    }

    public int doAfterBody() throws JspException {
        if (this.errorsIterator.hasNext()) {
            setError();
            return EVAL_BODY_AGAIN;
        }

        return SKIP_BODY;
    }

    private void setError() {
        ValidationError error = (ValidationError) this.errorsIterator.next();
        Locale locale = LocaleConfig.getLocale((HttpServletRequest) pageContext.getRequest());
        ApplicationResources messages = ApplicationResources.getInstance(locale);
        String message = messages.getMessage(error.getKey(), error.getValues());
        setAttribute(ERROR, message);
    }
}
