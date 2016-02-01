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
package pt.maisis.search.template;

import pt.maisis.search.util.RenderTool;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.util.StringUtils;
import java.util.Properties;
import java.io.StringWriter;
import java.io.InputStream;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <i>Wrapper</i> que encapsula alguns métodos da framework Velocity.
 */
public final class VelocityTemplate {

    private static Log log = LogFactory.getLog(VelocityTemplate.class);
    /** Referência para o próprio contexto. */
    public static final String CONTEXT = "context";
    /** Instância de org.apache.velocity.tools.generic.RenderTool. */
    public static final String RENDER = "render";
    /** Instância de org.apache.velocity.util.StringUtils. */
    public static final String STRING_UTILS = "stringutils";
    private static final VelocityTemplate me = new VelocityTemplate();

    private VelocityTemplate() {
        InputStream input = null;
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            Properties props = new Properties();
            input = cl.getResourceAsStream("velocity/velocity.properties");
            props.load(input);

            Velocity.init(props);

        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // nada a fazer
                }
            }
        }
    }

    public static VelocityTemplate getInstance() {
        return me;
    }

    /**
     * Faz o merge do dado ficheiro tendo em conta o dado contexto.<br/>
     *
     * No contexto são colocadas as seguintes variáveis:
     * {@link #CONTEXT}, {@link #RENDER}, {@link #STRING_UTILS}.
     */
    public String merge(VelocityContext context, String templateFileName) {

        addContextValues(context);
        StringWriter writer = new StringWriter();

        try {
            Template template = Velocity.getTemplate(templateFileName);
            template.merge(context, writer);

        } catch (Exception e) {
            log.error(e);
        }

        String text = writer.toString();

        if (log.isDebugEnabled()) {
            log.debug(text);
        }

        return text;
    }

    /**
     * Faz a <i>evaluation</i> da dada string tendo em conta o dado
     * contexto.<br/>
     *
     * No contexto são colocadas as seguintes variáveis:
     * {@link #CONTEXT}, {@link #RENDER}, {@link #STRING_UTILS}.
     */
    public String evaluate(VelocityContext context, String template) {

        addContextValues(context);
        StringWriter writer = new StringWriter();

        try {
            Velocity.evaluate(context, writer, this.getClass().getName(), template);

        } catch (Exception e) {
            log.error(e);
        }

        return writer.toString();
    }

    /**
     * Adiciona ao contexto algumas variáveis úteis.
     */
    private void addContextValues(VelocityContext context) {
        context.put(RENDER, new RenderTool());
        context.put(CONTEXT, context);
        context.put(STRING_UTILS, new StringUtils());
    }
}
