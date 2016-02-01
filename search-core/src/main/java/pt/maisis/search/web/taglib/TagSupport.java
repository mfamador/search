/*
 * %W% %E% Marco Amador
 *
 * Copyright (c) 1994-2011 Maisis - Information Systems. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Maisis
 * Information Systems, Lda. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Maisis.
 *
 * MAISIS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MAISIS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package pt.maisis.search.web.taglib;

import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 * @version 1.0
 */
public class TagSupport extends javax.servlet.jsp.tagext.TagSupport
        implements TryCatchFinally {

    private String refreshCount;
    private String action;

    public String getRefreshCount() {
        return this.refreshCount;
    }

    public void setRefreshCount(final String refreshCount) {
        this.refreshCount = refreshCount;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(final String action) {
        this.action = action;
    }

    public TagSupport() {
    }

    /**
     * @see #setAttribute(String, Object)
     */
    protected void setAttribute(final String name, final char value) {
        setAttribute(name, String.valueOf(value));
    }

    /**
     * @see #setAttribute(String, Object)
     */
    protected void setAttribute(final String name, final int value) {
        setAttribute(name, new Integer(value));
    }

    /**
     * @see #setAttribute(String, Object)
     */
    protected void setAttribute(final String name, final long value) {
        setAttribute(name, new Long(value));
    }

    /**
     * @see #setAttribute(String, Object)
     */
    protected void setAttribute(final String name, final float value) {
        setAttribute(name, new Float(value));
    }

    /**
     * @see #setAttribute(String, Object)
     */
    protected void setAttribute(final String name, final double value) {
        setAttribute(name, new Double(value));
    }

    /**
     * @see #setAttribute(String, Object)
     */
    protected void setAttribute(final String name, final boolean value) {
        setAttribute(name, Boolean.valueOf(value));
    }

    /**
     * Invoca <code>pageContext.setAttribute()</code>.
     * Se o nome ou valor for <code>null</code>, a invocacão é
     * silenciosamente ignorada.
     *
     * @param name  nome do atributo.
     * @param value valor do atributo.
     */
    protected void setAttribute(final String name, final Object value) {
        if (name != null && value != null) {
            pageContext.setAttribute(name, value);
        }
    }

    protected void removeAttribute(final String name) {
        pageContext.removeAttribute(name);
    }

    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    public void doFinally() {
    }
}
