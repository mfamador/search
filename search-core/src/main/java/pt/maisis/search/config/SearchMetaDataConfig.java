package pt.maisis.search.config;

import pt.maisis.search.SearchSetMetaData;
import pt.maisis.search.SearchMetaData;
import pt.maisis.search.xml.SearchEntityResolver;
import pt.maisis.search.util.MessageResources;

import java.util.List;
import java.util.Iterator;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.digester.Digester;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.SearchEngine;

/**
 * Classe responsável por fazer o parse de cada uma das pesquisas
 * definidas no ficheiro <code>search-config.xml</code>.
 *
 * @version 1.0
 *
 * @see pt.maisis.search.config.SearchConfig
 */
public final class SearchMetaDataConfig {

    private static Log log =
            LogFactory.getLog(SearchMetaDataConfig.class);
    private static final String DTD_PUBLIC_IDENTIFIER =
            "-//Maisis//DTD Search MetaData 1.5//EN";
    private static final String DTD_SYSTEM_IDENTIFIER =
            "/META-INF/search-metadata_1_5.dtd";
    private static SearchMetaDataConfig me = new SearchMetaDataConfig();
    private SearchSetMetaData searchSetMetaData;

    /**
     * Retorna a instância desta classe.
     */
    public static SearchMetaDataConfig getInstance() {
        return me;
    }

    private void parseDescriptors(String search) {
        Digester digester = new Digester();
        digester.setValidating(true);

        // registar a cópia local do DTD
        URL url = this.getClass().getResource(DTD_SYSTEM_IDENTIFIER);
        if (url != null) {
            digester.register(DTD_PUBLIC_IDENTIFIER, url.toString());
        }
        digester.addRuleSet(new SearchMetaDataConfigRuleSet());
        digester.setEntityResolver(new SearchEntityResolver(digester));

        if (search == null || this.searchSetMetaData == null) {
            this.searchSetMetaData = new SearchSetMetaData();
        }

        SearchConfig sc = SearchConfig.getInstance();
        List descriptors = sc.getSearchEngineMetaDataDescriptors();
        Iterator it = descriptors.iterator();
        boolean found = false;
        while (it.hasNext() && found == false) {
            try {
                String descriptor = (String) it.next();
                SearchMetaData smd = getSearchMetaData(digester, descriptor);

                if (search != null && search.equals(smd.getName())) {
                    found = true;
                    SearchEngine.getInstance().setSearchMetadata(smd);
                }
                this.searchSetMetaData.add(smd);
            }
            catch (Exception e) {
                log.error(e);
                throw new SearchConfigException(e.getMessage());
            }
        }
    }

    private void parseDescriptors() {
        parseDescriptors(null);
    }

    private SearchMetaDataConfig() {
        parseDescriptors();
    }

    private SearchMetaData getSearchMetaData(final Digester digester,
            final String descriptor)
            throws Exception {

        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        if (log.isInfoEnabled()) {
            log.info("[parsing]: " + descriptor);
        }

        InputStream input = null;
        try {
            URL url = cl.getResource(descriptor);

            if (url == null) {
                String message = MessageResources.getInstance().getMessage("config.fileNotFound", descriptor);
                log.error(message);
                throw new SearchConfigException(message);
            }

            input = url.openStream();
            return (SearchMetaData) digester.parse(input);

        }
        finally {
            if (input != null) {
                try {
                    input.close();
                }
                catch (IOException e) {
                    // ignorar
                }
            }
        }
    }

    /**
     * Retorna toda a metadata definida. 
     * @see pt.maisis.search.SearchSetMetaData
     */
    public SearchSetMetaData getSearchSetMetaData() {
        return this.searchSetMetaData;
    }

    static void reload() {
        SearchMetaDataConfig smd = new SearchMetaDataConfig();
        me = smd;
    }

    static void reload(String search) {
        if (me == null) {
            SearchMetaDataConfig smd = new SearchMetaDataConfig();
            me = smd;
        }
        me.parseDescriptors(search);
    }
}
