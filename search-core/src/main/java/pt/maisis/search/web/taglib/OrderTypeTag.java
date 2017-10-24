package pt.maisis.search.web.taglib;

import pt.maisis.search.web.SearchForm;

import javax.servlet.jsp.JspException;

public class OrderTypeTag extends SearchBaseTag {

    /** Atributo que  colocado no contexto da tag e que identifica
    o tipo de ordenacao seleccionado. */
    public static final String ORDER = "order";

    public int doStartTag() throws JspException {

        SearchForm form = getSearchForm();
        if (form == null) {
            return SKIP_BODY;
        }

        int orderType = form.getOrderType();
        setAttribute(ORDER, orderType);

        return EVAL_BODY_INCLUDE;
    }
}
