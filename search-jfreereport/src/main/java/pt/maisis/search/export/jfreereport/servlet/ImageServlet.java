/*
 * %W% %E% Marco Amador
 *
 * Copyright (c) 1994-2011 Maisis - Information Systems. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of MAISIS
 * Information Systems, Lda. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with MAISIS.
 *
 * MAISIS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MAISIS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package pt.maisis.search.export.jfreereport.servlet;

import pt.maisis.search.util.MessageResources;
import pt.maisis.search.util.StringUtils;
import pt.maisis.search.export.jfreereport.JFreeReportConfig;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Servlet indespensável para a geracão de relatórios (jfree reports)
 * no formato HTML, que contenham imagens.
 *
 * @version 1.0
 */
public class ImageServlet extends HttpServlet {
    private static Log log = LogFactory.getLog(ImageServlet.class);

    /**
     * Parâmetro que identifica o nome da imagem.
     * Este parâmetro é obrigatório.
     */
    public static final String PARAM_NAME = "name";

    /**
     * Processa o pedido HTTP (GET) para uma dada imagem.
     * @see #PARAM_NAME
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
        throws ServletException, IOException {

        String image = request.getParameter(PARAM_NAME);

        if (image == null || StringUtils.isEmpty(image)) {
            String message = MessageResources.getInstance().getMessage
                ("imageservlet.param.required");
            log.error(message);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
            return;
        }

        File imagefile = getFile(image);
        if (!imagefile.exists() || !imagefile.isFile()) {
            String message = MessageResources.getInstance().getMessage
                ("imageservlet.param.invalid");
            log.error(message);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
            return;
        }

        InputStream input = new FileInputStream(imagefile);
        try {
            response.setContentType(getMimeType(image));
            response.setContentLength((int)imagefile.length());
            copy(input, response.getOutputStream());

        } catch (IOException e) {
            log.error(e);

        } finally {
            input.close();
        }
    }

    /**
     * Retorna o MIME type da imagem.
     */
    private String getMimeType(String image) {
        String mimetype = getServletContext().getMimeType(image);

        // Não é suposto o mimetype ser null, no entanto...
        if (mimetype == null) {

            // Neste momento, a framework só suporta dois tipos de
            // formatos (PNG e JPEG).
            if ("png".equals(image.substring(image.length() - 3))) {
                return "image/png";
            }
            return "image/jpeg";
        }

        return mimetype;
    }

    /**
     * Retorna o nome absoluto da imagem.
     */
    private File getFile(String image) {
        String directory = JFreeReportConfig.getInstance().getTmpDir();
        return new File(directory, image);
    }

    private void copy(InputStream input, OutputStream output)
      throws IOException {

        byte[] buffer = new byte[256];
        while (true) {
            int bytesRead = input.read(buffer);
            if (bytesRead == -1) {
                break;
            }
            output.write(buffer, 0, bytesRead);
        }
    }
}
