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
package pt.maisis.search.format;

import java.lang.reflect.Method;
import java.util.Locale;
import java.text.DateFormat;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.util.MessageResources;

/**
 * Classe base dos formatadores do tipo data. <br/>
 * 
 * Disponibiliza constantes que permitem especificar o estilo de formatacão
 * das datas. Estes estilos correspondem aos disponibilizados pela classe 
 * java.text.DateFormat.<br/>
 * 
 * Para especificar o tipo de formatacão deve-se especificar a propriedade 
 * pattern no descriptor de uma dada pesquisa. <br/>
 * Por exemplo:
 * <pre>
 * &lt;formatter&gt;
 *   &lt;name&gt;date&lt;/name&gt;
 *   &lt;pattern&gt;short&lt;/pattern&gt;
 * &lt;/formatter&gt;
 * </pre>
 * 
 * A <code>pattern</code> que se pode especificar nos descriptors das pesquisas 
 * corresponde às constantes {@link #FULL}, {@link #LONG}, {@link #MEDIUM} e 
 * {@link #SHORT} (<i>case insensitive</i>).<br/>
 * Se não for especificada nenhuma <code>pattern</code> é utilizado o estilo
 * por defeito java.text.DateFormat.DEFAULT.
 * 
 * @see java.text.DateFormat
 */
public abstract class AbstractDateFormatter implements Formatter {

    private static Log log = LogFactory.getLog(AbstractDateFormatter.class);
    /**
     * @see java.text.DateFormat.Full
     */
    public static final String FULL = "full";
    /**
     * @see java.text.DateFormat.LONG
     */
    public static final String LONG = "long";
    /**
     * @see java.text.DateFormat.MEDIUM
     */
    public static final String MEDIUM = "medium";
    /**
     * @see java.text.DateFormat.SHORT
     */
    public static final String SHORT = "short";
    protected String pattern;
    protected int style = DateFormat.DEFAULT;
    /**
     * String a utilizar por defeito caso o valor a formatar for null.
     */
    protected String nullValue;

    public AbstractDateFormatter() {
    }

    public void setPattern(final String pattern) {
        this.style = parseStyle(pattern);
        this.pattern = pattern;
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setNullValue(final String nullValue) {
        this.nullValue = nullValue;
    }

    public String getNullValue() {
        return this.nullValue;
    }

    protected int parseStyle(final String pattern) {
        if (FULL.equalsIgnoreCase(pattern)) {
            return DateFormat.FULL;
        }
        if (LONG.equalsIgnoreCase(pattern)) {
            return DateFormat.LONG;
        }
        if (MEDIUM.equalsIgnoreCase(pattern)) {
            return DateFormat.MEDIUM;
        }
        if (SHORT.equalsIgnoreCase(pattern)) {
            return DateFormat.SHORT;
        }
        return DateFormat.DEFAULT;
    }

    protected int getStyle() {
        return this.style;
    }

    public Object format(Object obj) {
        return format(obj, Locale.getDefault());
    }

    protected Date toDate(Object obj) {
        if (obj == null) {
            return null;
        }

        if (obj instanceof Date) {
            return (Date) obj;
        }

        // O driver jdbc do oracle retorna uma instancia de 
        // oracle.sql.TIMESTAMP em vez de java.sql.Date 
        // (qd o stmt/pstmt é forward only)
        Class clazz = obj.getClass();
        if ("oracle.sql.TIMESTAMP".equals(clazz.getName())) {
            try {
                Method method = clazz.getMethod("timestampValue", null);
                return (Date) method.invoke(obj, null);

            } catch (Exception e) {
                log.error(e.getMessage());
                return null;
            }
        }

        MessageResources messages = MessageResources.getInstance();
        String message = messages.getMessage("format.error.invalidObject",
                clazz.getName(), "java.util.Date");
        log.error(message);
        return null;
    }
}
