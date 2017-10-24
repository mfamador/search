package pt.maisis.search.web.taglib;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;

/**
 * Simples tag criada para facilitar a implementação de novas
 * tags.
 *
 * @author Marco Amador
 */
public class BaseTag extends SimpleTagSupport {

    /**
     * Retorna o contexto da p�gina.
     * @return a refer�ncia para o <code>PageContext</code>.
     */
    public PageContext getPageContext() {
        return (PageContext) getJspContext();
    }

    public JspWriter getOut() {
        return getPageContext().getOut();
    }

    public ServletContext getServletContext() {
        return getPageContext().getServletContext();
    }

    /**
     * Retorna a sess�o actual.
     * @return refer�ncia para a <code>HttpSession</code>.
     */
    public HttpSession getSession() {
        return getPageContext().getSession();
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) getPageContext().getRequest();
    }

    /**
     * M�todo helper que permite fazer o set, no contexto da p�gina,
     * de um atributo do tipo <code>boolean</code>.
     */
    public void setAttribute(String name, boolean value) {
        setAttribute(name, (value) ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * M�todo helper que permite fazer o set, no contexto da p�gina,
     * de um atributo do tipo <code>char</code>.
     */
    public void setAttribute(String name, char value) {
        setAttribute(name, String.valueOf(value));
    }

    /**
     * M�todo helper que permite fazer o set, no contexto da p�gina,
     * de um atributo do tipo <code>short</code>.
     */
    public void setAttribute(String name, short value) {
        setAttribute(name, new Short(value));
    }

    /**
     * M�todo helper que permite fazer o set, no contexto da p�gina,
     * de um atributo do tipo <code>int</code>.
     */
    public void setAttribute(String name, int value) {
        setAttribute(name, new Integer(value));
    }

    /**
     * M�todo helper que permite fazer o set, no contexto da p�gina,
     * de um atributo do tipo <code>float</code>.
     */
    public void setAttribute(String name, float value) {
        setAttribute(name, new Float(value));
    }

    /**
     * M�todo helper que permite fazer o set, no contexto da p�gina,
     * de um atributo do tipo <code>long</code>.
     */
    public void setAttribute(String name, long value) {
        setAttribute(name, new Long(value));
    }

    /**
     * M�todo helper que permite fazer o set, no contexto da p�gina,
     * de um atributo do tipo <code>double</code>.
     */
    public void setAttribute(String name, double value) {
        setAttribute(name, new Double(value));
    }

    /**
     * M�todo helper que permite fazer o set, no contexto da p�gina,
     * de um atributo do tipo <code>Object</code>.
     */
    public void setAttribute(String name, Object value) {
        getPageContext().setAttribute(name, value);
    }

    public void removeAttribute(String name) {
        getPageContext().removeAttribute(name);
    }

    public void removeAttributes(String[] names) {
        for (int i = 0; i < names.length; i++) {
            removeAttribute(names[i]);
        }
    }

    public void invoke(JspFragment fragment) throws JspException, IOException {
        fragment.invoke(null);
    }

    /**
     * M�todo invocado pelo container.
     * <br>
     * Este m�todo invoca o m�todo {@link #doBodyTag} para tags
     * que contenham corpo (<i>body</i>).
     */
    @Override
    public void doTag() throws JspException, IOException {
        final JspFragment body = getJspBody();

        if (body != null) {
            doBodyTag(getJspBody());
        }
    }

    /**
     * M�todo invocado pelo m�tdo {@link #doTag} quando a tag
     * actual tem corpo (<i>body</i>).
     */
    public void doBodyTag(final JspFragment body)
            throws JspException, IOException {
    }
}
