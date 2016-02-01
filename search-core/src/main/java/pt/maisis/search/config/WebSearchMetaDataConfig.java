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
package pt.maisis.search.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.util.MessageResources;
import pt.maisis.search.web.WebExporter;
import pt.maisis.search.web.WebSearch;
import pt.maisis.search.web.WebSearchMetaData;
import pt.maisis.search.xml.SearchEntityResolver;

/**
 * Classe responsável por fazer o parse e validacão do ficheiro de
 * configuracão {@link #WEB_SEARCH_CONFIG_FILE}.<br/>
 *
 * Este ficheiro deve ter declaradas todas as pesquisas a utilizar
 * em ambiente web.
 *
 * @version 1.0
 */
public final class WebSearchMetaDataConfig {

    private static Log log = LogFactory.getLog(WebSearchMetaDataConfig.class);
    private static final String DTD_PUBLIC_IDENTIFIER = "-//Maisis//DTD Web Search Config 1.5//EN";
    private static final String DTD_SYSTEM_IDENTIFIER = "/META-INF/web-search-config_1_5.dtd";
    /** Ficheiro de configuracão das pesquisas em ambiente web. */
    public static final String WEB_SEARCH_CONFIG_FILE = "web-search-config.xml";
    /** Instância deste configurador. */
    private static WebSearchMetaDataConfig me = new WebSearchMetaDataConfig();
    /** Metadata das pesquisas web, lidas do ficheiro. */
    private WebSearchMetaData webSearchMetaData;

    /**
     * Retorna uma instância desta classe.
     */
    public static WebSearchMetaDataConfig getInstance() {
        return me;
    }

    /**
     * Carrega a metadata web  definida no ficheiro de configuracão 
     * {@link #WEB_SEARCH_CONFIG_FILE} e torna-a acessível através
     * do método {@link #getWebSearchMetaData}.
     */
    private WebSearchMetaDataConfig() {

        Digester digester = new Digester();
        digester.setValidating(true);

        URL url = this.getClass().getResource(DTD_SYSTEM_IDENTIFIER);
        if (url != null) {
            digester.register(DTD_PUBLIC_IDENTIFIER, url.toString());
        }

        digester.setEntityResolver(new SearchEntityResolver(digester));

        digester.addObjectCreate("web-search-config",
                WebSearchMetaData.class);

        /*----- exporters -----*/

        digester.addObjectCreate("web-search-config/exporter",
                WebExporter.class);

        digester.addSetProperties("web-search-config/exporter",
                "name",
                "name");

        digester.addSetProperties("web-search-config/exporter",
                "html-header",
                "htmlHeader");

        digester.addSetProperties("web-search-config/exporter",
                "html-footer",
                "htmlFooter");

        digester.addCallMethod("web-search-config/exporter",
                "setExporter", 3);
        digester.addCallParam("web-search-config/exporter",
                0, "class");
        digester.addCallParam("web-search-config/exporter",
                1, "template");
        digester.addCallParam("web-search-config/exporter",
                2, "velocity");

        digester.addSetNext("web-search-config/exporter",
                "addWebExporter");

        /*----- searches -----*/

        digester.addObjectCreate("web-search-config/search",
                WebSearch.class);

        digester.addSetProperties("web-search-config/search",
                "name",
                "name");

        digester.addSetProperties("web-search-config/search",
                "search-view",
                "searchView");

        digester.addSetProperties("web-search-config/search",
                "result-view",
                "resultView");

        digester.addSetProperties("web-search-config/search",
                "search-form",
                "searchFormName");

        digester.addSetProperties("web-search-config/search",
                "search-form-scope",
                "searchFormScope");

        digester.addSetProperties("web-search-config/search",
                "search-form-class",
                "searchFormClass");

        digester.addSetProperties("web-search-config/search",
                "search-result",
                "searchResultName");

        digester.addSetProperties("web-search-config/search",
                "search-result-scope",
                "searchResultScope");

        digester.addSetProperties("web-search-config/search",
                "count",
                "count");

        digester.addSetProperties("web-search-config/search",
                "result-record-limit",
                "resultRecordLimit");

        digester.addSetProperties("web-search-config/search",
                "query-timeout",
                "queryTimeout");

        digester.addSetProperties("web-search-config/search",
                "hide-duplicated-result-params",
                "hideDuplicatedResultParams");

        digester.addSetProperties("web-search-config/search",
                "hide-duplicated-html-result-params",
                "hideDuplicatedHtmlResultParams");

        digester.addSetProperties("web-search-config/search",
                "exporter-filename-prefix",
                "exporterFilenamePrefix");

        digester.addSetProperties("web-search-config/search",
                "exporter-compression",
                "exporterCompression");

        digester.addSetProperties("web-search-config/search",
                "exporter-compression-record",
                "exporterCompressionRecord");

        digester.addSetProperties("web-search-config/search",
                "select-all-result-params",
                "selectAllResultParams");

        digester.addCallMethod("web-search-config/search/result-view",
                "addResultView", 2);
        digester.addCallParam("web-search-config/search/result-view",
                0, "format");
        digester.addCallParam("web-search-config/search/result-view",
                1, "name");

        digester.addSetNext("web-search-config/search",
                "addWebSearch");

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream input = null;
        try {
            URL wsmd = cl.getResource(WEB_SEARCH_CONFIG_FILE);

            if (wsmd == null) {
                String message = MessageResources.getInstance().getMessage("config.fileNotFound", WEB_SEARCH_CONFIG_FILE);
                log.error(message);
                throw new SearchConfigException(message);
            }

            input = wsmd.openStream();
            this.webSearchMetaData = (WebSearchMetaData) digester.parse(input);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new SearchConfigException(e.getMessage());

        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                // ignorar
            }
        }

        // TODO: alterar este check!
        // Aproveitar para verificar se existe alguma
        // pesquisa web que não tenha metadata.

//         List searches = this.webSearchMetaData.getSearches();
//         Iterator it = searches.iterator();
//         SearchEngine se = SearchEngine.getInstance();

//         while (it.hasNext()) {
//             WebSearch webSearch = (WebSearch) it.next();
//             if (se.getSearch(webSearch.getName()) == null) {
//                 String message = MessageResources.getMessage
//                     ("config.invalidSearch", 
//                      WEB_SEARCH_CONFIG_FILE,
//                      webSearch.getName(), 
//                      SearchConfig.SEARCH_CONFIG_FILE);

//                 log.error(message);
//                 throw new SearchConfigException(message);
//             }
//         }
    }

    /**
     * Retorna uma instância de
     * {@link pt.maisis.search.web.WebSearchMetaData}
     * com a metatada definida no ficheiro de configuracão
     * {@link #WEB_SEARCH_CONFIG_FILE}.
     */
    public WebSearchMetaData getWebSearchMetaData() {
        return this.webSearchMetaData;
    }

    static void reload() {
        WebSearchMetaDataConfig wsmd = new WebSearchMetaDataConfig();
        me = wsmd;
    }

    static void reload(String search) {
        /* TODO */
    }
}
