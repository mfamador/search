package pt.maisis.search.web.taglib;

import pt.maisis.search.SearchMetaData;
import pt.maisis.search.ContainerMetaData;
import pt.maisis.search.SearchParameterMetaData;
import pt.maisis.search.web.SearchForm;
import pt.maisis.search.util.StringUtils;

import javax.servlet.jsp.JspException;
import java.util.Iterator;

/**
 * Tag que coloca no contexto da página os labels ({@link #LABEL})
 * e valores ({@link #VALUE}) dos critérios de pesquisa utilizados.
 *
 * TODO: flexibilizar esta tag. Por exemplo, os valores dos search
 *       params do tipo composite são concatenados independentemente
 *       deste ser ou não o comportamente desejado.
 *
 * @version 1.0
 */
public class SearchParamsTag extends SearchBaseTag {

    public static final String LABEL = "label";
    public static final String VALUE = "value";
    Iterator spmds;
    SearchParameterMetaData currentSpmd;

    public SearchParamsTag() {
    }

    public int doStartTag() throws JspException {
        SearchMetaData smd = getSearchMetaData();
        SearchForm form = getSearchForm();

        if (smd == null || smd.getSearchParameters().isEmpty() || form == null) {
            return SKIP_BODY;
        }

        this.spmds = smd.getSearchParameters().iterator();

        if (!this.spmds.hasNext()) {
            return SKIP_BODY;
        }

        String values = getNextValues();
        if (values == null) {
            return SKIP_BODY;
        }
        setAttribute(LABEL, getLabel());
        setAttribute(VALUE, values);
        return EVAL_BODY_INCLUDE;
    }

    public int doAfterBody() throws JspException {
        if (this.spmds.hasNext()) {
            String values = getNextValues();
            if (values == null) {
                return SKIP_BODY;
            }
            setAttribute(LABEL, getLabel());
            setAttribute(VALUE, values);
            return EVAL_BODY_AGAIN;
        }
        return SKIP_BODY;
    }

    private String getNextValues() {
        String values = null;
        do {
            this.currentSpmd = (SearchParameterMetaData) this.spmds.next();
            values = checkValues();
        } while (values == null && this.spmds.hasNext());

        return values;
    }

    private String checkValues() {
        SearchForm form = getSearchForm();
        ContainerMetaData container = this.currentSpmd.getContainer();

        if (container.isComposite()) {
            Iterator children = container.getChildren().iterator();
            StringBuffer buffer = new StringBuffer();
            while (children.hasNext()) {
                ContainerMetaData child = (ContainerMetaData) children.next();
                String value = getValue(form, child);
                if (value != null) {
                    buffer.append(" ").append(value);
                }
            }
            return buffer.toString();
        }
        return getValue(form, container);
    }

    private String getValue(final SearchForm form,
            final ContainerMetaData container) {

        // Não considerar os containers do tipo hidden.
        if (ContainerMetaData.TYPE_HIDDEN.equals(container.getType())) {
            return null;
        }

        String name = container.getName();
        Object fakeValue = form.getFakeParam(name);
        Object value = (fakeValue == null)
                ? form.getSearchParam(name)
                : fakeValue;

        if (value == null) {
            return null;
        }

        if (value.getClass().isArray()) {
            return StringUtils.join(value);
        }
        return (String) value;
    }

    /**
     * Retorna o label do <code>container</code>.
     * No caso do label ser <code>null</code>, retorna o label
     * do pai, caso o tenha.
     */
    private String getLabel() {
        ContainerMetaData container = this.currentSpmd.getContainer();
        if (container.getLabel() == null && container.getParent() != null) {
            return container.getParent().getLabel();
        }
        return container.getLabel();
    }
}
