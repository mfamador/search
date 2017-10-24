package pt.maisis.search.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.util.MessageResources;
import pt.maisis.search.xml.SearchEntityResolver;

/**
 * Classe responsável por fazer o parse e validacão do ficheiro
 * de configuracão <code>search-config.xml</code>.
 *
 * @version 1.0
 */
public final class SearchConfig {

    private static Log log =
            LogFactory.getLog(SearchConfig.class);
    private static final String DTD_PUBLIC_IDENTIFIER = "-//Maisis//DTD Search Configuration 1.5//EN";
    private static final String DTD_SYSTEM_IDENTIFIER = "/META-INF/search-config_1_5.dtd";
    public static final String SEARCH_CONFIG_FILE = "search-config.xml";
    /** Default Formatters. */
    private static final String[] DEFAULT_FORMATTERS = {
        "null", "pt.maisis.search.format.NullFormatter",
        "number", "pt.maisis.search.format.NumberFormatter",
        "time", "pt.maisis.search.format.TimeFormatter",
        "date", "pt.maisis.search.format.DateFormatter",
        "dateTime", "pt.maisis.search.format.DateTimeFormatter",
        "customDate", "pt.maisis.search.format.CustomDateFormatter",
        "message", "pt.maisis.search.format.MessageFormatter",
        "nullMessage", "pt.maisis.search.format.NullMessageFormatter",
        "currency", "pt.maisis.search.format.CurrencyFormatter",
        "percent", "pt.maisis.search.format.PercentFormatter",
        "object", "pt.maisis.search.format.ObjectFormatter"
    };
    /** Default Validators. */
    private static final String[] DEFAULT_CONVERTERS = {
        "date", "pt.maisis.search.validator.DateConverter",
        "sqldate", "pt.maisis.search.validator.SqlDateConverter",
        "sqltime", "pt.maisis.search.validator.SqlTimeConverter",
        "sqltimestamp", "pt.maisis.search.validator.SqlTimestampConverter",
        "sqllike", "pt.maisis.search.validator.SqlLikeConverter",
        "split", "pt.maisis.search.validator.SplitConverter",
        "number", "pt.maisis.search.validator.NumberConverter"
    };
    private static SearchConfig me = new SearchConfig();
    private final List descriptors = new ArrayList();
    private final Map formatters = new HashMap();
    private final Map converters = new HashMap();
    private String messageResources;
    private String dataSourceName;
    private String searchEngineClassName;
    private String searchEngineMetaDataClassName;

    /**
     * Retorna uma instância desta classe.
     */
    public static SearchConfig getInstance() {
        return me;
    }

    private void setDefaultFormatters() {
        for (int i = 0; i < DEFAULT_FORMATTERS.length;) {
            addSearchFormatter(DEFAULT_FORMATTERS[i++],
                    DEFAULT_FORMATTERS[i++]);
        }
    }

    private void setDefaultValidators() {
        for (int i = 0; i < DEFAULT_CONVERTERS.length;) {
            addSearchConverter(DEFAULT_CONVERTERS[i++],
                    DEFAULT_CONVERTERS[i++]);
        }
    }

    private SearchConfig() {
        setDefaultFormatters();
        setDefaultValidators();

        Digester digester = new Digester();
        digester.push(this);
        digester.setValidating(true);
        digester.setEntityResolver(new SearchEntityResolver(digester));

        URL url = this.getClass().getResource(DTD_SYSTEM_IDENTIFIER);
        if (url != null) {
            digester.register(DTD_PUBLIC_IDENTIFIER, url.toString());
        }

        digester.addBeanPropertySetter("*/message-resources",
                "messageResources");

        digester.addBeanPropertySetter("*/data-source-name",
                "dataSourceName");

        digester.addBeanPropertySetter("*/search-engine-class",
                "searchEngineClassName");

        digester.addBeanPropertySetter("*/search-engine-metadata-class",
                "searchEngineMetaDataClassName");

        digester.addCallMethod("*/descriptor",
                "addSearchEngineMetaDataDescriptor", 1);
        digester.addCallParam("*/descriptor", 0);

        digester.addCallMethod("*/formatter",
                "addSearchFormatter", 2);
        digester.addCallParam("*/formatter/name", 0);
        digester.addCallParam("*/formatter/class", 1);

        digester.addCallMethod("*/converter",
                "addSearchConverter", 2);
        digester.addCallParam("*/converter/name", 0);
        digester.addCallParam("*/converter/class", 1);

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream input = null;
        try {
            URL sc = cl.getResource(SEARCH_CONFIG_FILE);

            if (sc == null) {
                String message = MessageResources.getInstance().getMessage("config.fileNotFound", SEARCH_CONFIG_FILE);
                log.error(message);
                throw new SearchConfigException(message);
            }

            input = sc.openStream();
            digester.parse(input);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new SearchConfigException(e.getMessage());
        }
        finally {
            try {
                if (input != null) {
                    input.close();
                }
            }
            catch (IOException e) {
                // ignorar
            }
        }
    }

    /**
     * Nome do ficheiro message resources a utilizar.
     */
    public String getMessageResources() {
        return this.messageResources;
    }

    public void setMessageResources(final String messageResources) {
        this.messageResources = messageResources;
    }

    /**
     * Nome da <code>datasource</code> que deve ser utilizada pela
     * framework de pesquisa.
     */
    public String getDataSourceName() {
        return this.dataSourceName;
    }

    public void setDataSourceName(final String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    /**
     * Nome absoluto da classe que implementa o interface
     * {@link pt.maisis.search.dao.SearchEngineDao}, que será utilizada
     * pela framework de pesquisa.
     */
    public String getSearchEngineClassName() {
        return this.searchEngineClassName;
    }

    public void setSearchEngineClassName(final String searchEngineClassName) {
        this.searchEngineClassName = searchEngineClassName;
    }

    /**
     * Nome absoluto da classe que implementa o interface
     * {@link pt.maisis.search.dao.SearchEngineMetaDataDao}, que será
     * utilizada pela framework de pesquisa.
     */
    public String getSearchEngineMetaDataClassName() {
        return this.searchEngineMetaDataClassName;
    }

    public void setSearchEngineMetaDataClassName(final String searchEngineMetaDataClassName) {
        this.searchEngineMetaDataClassName = searchEngineMetaDataClassName;
    }

    /**
     * Lista com os nomes dos descriptors que contêm a metadata para
     * os diversos tipos de pesquisas disponíveis.
     */
    public List getSearchEngineMetaDataDescriptors() {
        return this.descriptors;
    }

    public void addSearchEngineMetaDataDescriptor(final String descriptor) {
        this.descriptors.add(descriptor);
    }

    public void addSearchFormatter(final String name, final String clazz) {
        this.formatters.put(name, clazz);
    }

    public String getSearchFormatter(final String name) {
        if (name == null) {
            return (String) this.formatters.get("null");
        }
        return (String) this.formatters.get(name);
    }

    public void addSearchConverter(final String name, final String clazz) {
        this.converters.put(name, clazz);
    }

    public String getSearchConverter(final String name) {
        return (String) this.converters.get(name);
    }

    public static void reload() {
        SearchConfig config = new SearchConfig();
        me = config;
        SearchMetaDataConfig.reload();
        WebSearchMetaDataConfig.reload();
    }

    public static void reload(String search) {
        SearchMetaDataConfig.reload(search);
    }
}
