package pt.maisis.search.config;

import pt.maisis.search.util.MessageResources;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.digester.Digester;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Classe responsável por fazer o parse e validação do ficheiro
 * de configuração <code>search-index-config.xml</code>.
 *
 * @version 1.0
 */
public final class SearchIndexConfig {
    private static Log log =
        LogFactory.getLog(SearchIndexConfig.class);

    private static final String DTD_PUBLIC_IDENTIFIER
        = "-//Maisis//DTD Search Index Configuration 1.0//EN";
    private static final String DTD_SYSTEM_IDENTIFIER
        = "/META-INF/search-index-config_1_0.dtd";

    public static final String CONFIG_FILE
        = "search-index-config.xml";

    private static SearchIndexConfig me = new SearchIndexConfig();

    private String indexDir;


    /**
     * Retorna uma instância desta classe.
     */
    public static SearchIndexConfig getInstance() {
        return me;
    }
    
    private SearchIndexConfig() {
        Digester digester = new Digester();
        digester.push(this);
        digester.setValidating(true);

        URL url = this.getClass().getResource(DTD_SYSTEM_IDENTIFIER);
        if (url != null) {
            digester.register(DTD_PUBLIC_IDENTIFIER, url.toString());
        }

        digester.addBeanPropertySetter
            ("*/index-dir",
             "indexDir");

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream input = null;
        try {
            URL sc = cl.getResource(CONFIG_FILE);

            if (sc == null) {
                String message = MessageResources.getInstance().getMessage
                    ("config.fileNotFound", CONFIG_FILE);
                log.error(message);
                throw new SearchConfigException(message);
            }

            input = sc.openStream();
            digester.parse(input);

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
    }

    public String getIndexDir() {
        return this.indexDir;
    }

    public void setIndexDir(final String indexDir) {
	this.indexDir = indexDir;
    }
}
