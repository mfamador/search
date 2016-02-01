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
package pt.maisis.search.xml;

import org.apache.commons.digester.Digester;

import java.io.IOException;
import java.io.File;
import java.net.URL;

import org.xml.sax.SAXException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Simples Entity Resolver que considera o classpath na procura
 * dos system ids referidos nos descriptors xml das pesquisas.
 *
 * TODO: compor
 *
 * @version 1.0
 */
public class SearchEntityResolver implements EntityResolver {

    private static Log log = LogFactory.getLog(SearchEntityResolver.class);
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String FILE_PROTOCOL = "file";
    private final Digester digester;

    public SearchEntityResolver(final Digester digester) {
        this.digester = digester;
    }

    public InputSource resolveEntity(final String publicId,
            final String systemId)
            throws SAXException, IOException {

        try {
            // Utilizar primeiro o resolver do digester.
            // Normalmente o publicId é utilizado para traduzir no 
            // systemId desejado.
            InputSource source = this.digester.resolveEntity(publicId, systemId);
            URL url = new URL(source.getSystemId());

            if (!FILE_PROTOCOL.equals(url.getProtocol()) || exists(url)) {
                return source;
            }

            // procurar o ficheiro no classpath
            if (log.isTraceEnabled()) {
                log.trace("looking for systemId in classpath: " + systemId);
            }

            File file = new File((new URL(systemId)).getFile());
            String filename = file.getPath();
            filename = filename.replaceAll("%20", " ");

            if (filename.startsWith(USER_DIR)) {
                filename = filename.substring(USER_DIR.length());
            }
            if (!filename.startsWith("/")) {
                filename = "/" + filename;
            }

            url = this.getClass().getResource(filename);
            source = new InputSource();
            if (url != null) {
                source.setSystemId(url.toString());
            }

            return source;

        } catch (Exception e) {
            log.error(e);
            throw new SAXException(e.getMessage());
        }
    }

    private boolean exists(URL url) {
        return (new File(url.getFile())).exists();
    }
}
