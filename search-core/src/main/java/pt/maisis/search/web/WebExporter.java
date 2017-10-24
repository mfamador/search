package pt.maisis.search.web;

import pt.maisis.search.export.SearchResultExporter;

public class WebExporter {

    private String name;
    private String htmlHeader;
    private String htmlFooter;
    private SearchResultExporter exporter;

    public WebExporter() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setExporter(final String clazz,
            final String template,
            final String useVelocityTemplate) {
        if (clazz != null) {
            try {
                this.exporter =
                        (SearchResultExporter) Class.forName(clazz).newInstance();

                this.exporter.setTemplate(template);

                this.exporter.useVelocityTemplate(Boolean.valueOf(useVelocityTemplate).booleanValue());

                // this.exporter.setSearchMetaData(this);

            } catch (Exception e) {
                e.printStackTrace(); // alertar
            }
        }
    }

    public SearchResultExporter getExporter() {
        return this.exporter;
    }

    public String getHtmlHeader() {
        return this.htmlHeader;
    }

    public void setHtmlHeader(final String htmlHeader) {
        this.htmlHeader = htmlHeader;
    }

    public String getHtmlFooter() {
        return this.htmlFooter;
    }

    public void setHtmlFooter(final String htmlFooter) {
        this.htmlFooter = htmlFooter;
    }
}
