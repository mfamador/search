package pt.maisis.search.web.taglib;

import pt.maisis.search.ContainerMetaData;
import pt.maisis.search.util.ApplicationResources;
import pt.maisis.search.util.MessageResources;
import pt.maisis.search.web.SearchForm;
import pt.maisis.search.value.Value;
import pt.maisis.search.value.AjaxValue;
import pt.maisis.search.value.ListValue;
import pt.maisis.search.web.LocaleConfig;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import pt.maisis.search.SearchParameterMetaData;

/**
 * TODO: se a form não for encontrada, os valores devem ser os
 * valores por defeito configurados no xml.
 *
 * @version 1.0
 */
public class ContainerMetaDataTag extends TagSupport {

    /**
     * Instância de {@link ContainerMetaData} que é colocada no
     * contexto da página.
     */
    public static final String CONTAINER = "container";
    public static final String SEARCH_PARAM = "searchParam";
    /** Valor a ser apresentado pelo container. */
    public static final String VALUE = "value";
    /** Valor do label traduzido tendo em conta o locale do user. */
    public static final String LABEL = "label";
    /** 
     * Por vezes o valor propriamente dito não é apresentado ao
     * utilizador, nestes casos é apresentado um valor mais
     * amigável, em termos de interface.
     *
     * TODO: remover o conceito de fakeparam da framework!
     */
    public static final String FAKE_VALUE = "fakeValue";
    private String type;

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public int doStartTag() throws JspException {
        Tag parent;

        if (!((parent = getParent()) instanceof SearchMetaDataTag)) {
            throw new JspException(MessageResources.getInstance().getMessage("tag.invalid.parent"));
        }

        ContainerMetaData container =
                ((SearchMetaDataTag) parent).getContainer();
        SearchParameterMetaData searchParam =
                ((SearchMetaDataTag) parent).getSearchParameter();
        SearchForm form =
                ((SearchMetaDataTag) parent).getSearchForm();

        // TODO: melhorar
        removeAttribute(VALUE);
        removeAttribute(FAKE_VALUE);
        removeAttribute("values");
        removeAttribute("ajax");

        if (container.getType() != null && container.getType().equals(this.type)) {
            setAttribute(CONTAINER, container);
            setAttribute(SEARCH_PARAM, searchParam);

            Locale locale = LocaleConfig.getLocale((HttpServletRequest) pageContext.getRequest());
            ApplicationResources messages = ApplicationResources.getInstance(locale);
            setAttribute(LABEL, messages.getMessage(container.getLabel()));

            if (form != null) {
                String name = container.getName();
                Object value = form.getSearchParam(name);
                Object fakeValue = form.getFakeParam(name);

                setAttribute(VALUE, value);
                setAttribute(FAKE_VALUE, fakeValue);

                Value defaultValue = container.getDefaultValue();
                if (defaultValue instanceof ListValue) {
                    setAttribute("values", getList(defaultValue, value));
                }
                AjaxValue ajaxValue = container.getAjaxValue();
                if (ajaxValue != null) {
                    setAttribute("ajax", ajaxValue);
                }

            }

            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }

    private List getList(Value defaultValue, Object value) {
        ListValue list = (ListValue) defaultValue;
        if (value instanceof String[]) {
            return list.getList((String[]) value);
        }
        return list.getList((String) value);
    }
}
