package pt.maisis.search.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
public final class SearchShareConfig {

    private static Log log = LogFactory.getLog(SearchShareConfig.class);
    private static final String DTD_PUBLIC_IDENTIFIER = "-//Maisis//DTD Search Share Configuration 1.5//EN";
    private static final String DTD_SYSTEM_IDENTIFIER = "/META-INF/search-share-config_1_5.dtd";
    public static final String SEARCH_SHARE_CONFIG_FILE = "search-share-config.xml";
    private String messageResources;
    private String dataSourceName;
    private String jtaDataSourceName;
    private String autoCreateDb;
    private String showSql;
    private String inputReportId = "inputReportId";
    private String inputReportName = "inputReportName";
    private String inputReportType = "inputReportType";
    private String inputEntityId = "inputEntityId";
    private String inputEntityTypeId = "inputEntityTypeId";
    private String inputCreatorId = "inputCreatorId";
    private String inputPermissionId = "inputPermissionId";
    private String inputSlidingWindow = "inputSlidingWindow";
    private String submitName = "searchShareSubmit";
    private String operation = "operation";
    private String url = "url";
    private String reportId = "reportId";
    private static SearchShareConfig me = new SearchShareConfig();

    /**
     * Retorna uma instância desta classe.
     */
    public static SearchShareConfig getInstance() {
        return me;
    }

    private SearchShareConfig() {

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

        digester.addBeanPropertySetter("*/jta-data-source-name",
                "jtaDataSourceName");

        digester.addBeanPropertySetter("*/auto-create-db",
                "autoCreateDb");

        digester.addBeanPropertySetter("*/show-sql",
                "showSql");

        digester.addBeanPropertySetter("*/input-report-id",
                "inputReportId");

        digester.addBeanPropertySetter("*/input-report-name",
                "inputReportName");

        digester.addBeanPropertySetter("*/input-report-type",
                "inputReportType");

        digester.addBeanPropertySetter("*/input-entity-id",
                "inputEntityId");

        digester.addBeanPropertySetter("*/input-entity-type-id",
                "inputEntityTypeId");

        digester.addBeanPropertySetter("*/input-creator-id",
                "inputCreatorId");

        digester.addBeanPropertySetter("*/input-permission-id",
                "inputPermissionId");

        digester.addBeanPropertySetter("*/input-sliding-window",
                "inputSlidingWindow");

        digester.addBeanPropertySetter("*/submit-name",
                "submitName");

        digester.addBeanPropertySetter("*/operation",
                "operation");

        digester.addBeanPropertySetter("*/report-id",
                "reportId");

        digester.addBeanPropertySetter("*/url",
                "url");

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream input = null;
        try {
            URL sc = cl.getResource(SEARCH_SHARE_CONFIG_FILE);

            if (sc == null) {
                String message = MessageResources.getInstance().getMessage("config.fileNotFound", SEARCH_SHARE_CONFIG_FILE);
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
                log.debug(e);
            }
        }
    }

    public static void reload() {
        SearchShareConfig config = new SearchShareConfig();
        me = config;
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

    public String getDataSourceName() {
        return this.dataSourceName;
    }

    public void setDataSourceName(final String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getJtaDataSourceName() {
        return jtaDataSourceName;
    }

    public void setJtaDataSourceName(String jtaDataSourceName) {
        this.jtaDataSourceName = jtaDataSourceName;
    }

    public String getAutoCreateDb() {
        return autoCreateDb;
    }

    public void setAutoCreateDb(String autoCreateDb) {
        this.autoCreateDb = autoCreateDb;
    }

    public String getShowSql() {
        return showSql;
    }

    public void setShowSql(String showSql) {
        this.showSql = showSql;
    }

    public String getInputReportId() {
        return inputReportId;
    }

    public void setInputReportId(String inputReportId) {
        this.inputReportId = inputReportId;
    }

    public String getInputReportName() {
        return this.inputReportName;
    }

    public void setInputReportName(final String inputReportName) {
        this.inputReportName = inputReportName;
    }

    public String getInputReportType() {
        return inputReportType;
    }

    public void setInputReportType(String inputReportType) {
        this.inputReportType = inputReportType;
    }

    public String getInputSlidingWindow() {
        return inputSlidingWindow;
    }

    public void setInputSlidingWindow(String inputSlidingWindow) {
        this.inputSlidingWindow = inputSlidingWindow;
    }
    
    public String getInputEntityId() {
        return this.inputEntityId;
    }

    public void setInputEntityId(final String inputEntityId) {
        this.inputEntityId = inputEntityId;
    }

    public String getInputCreatorId() {
        return inputCreatorId;
    }

    public void setInputCreatorId(String inputCreatorId) {
        this.inputCreatorId = inputCreatorId;
    }

    public String getInputPermissionId() {
        return inputPermissionId;
    }

    public void setInputPermissionId(String inputPermissionId) {
        this.inputPermissionId = inputPermissionId;
    }

    public String getInputEntityTypeId() {
        return inputEntityTypeId;
    }

    public void setInputEntityTypeId(String inputEntityTypeId) {
        this.inputEntityTypeId = inputEntityTypeId;
    }

    public String getSubmitName() {
        return this.submitName;
    }

    public void setSubmitName(final String submitName) {
        this.submitName = submitName;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
