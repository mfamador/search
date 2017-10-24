package pt.maisis.search;

import pt.maisis.search.config.SearchConfig;
import pt.maisis.search.format.Formatter;

import java.util.List;

public class SimpleResultParameterMetaData
        extends AbstractResultParameterMetaData {

    private Formatter formatter = DEFAULT_FORMATTER;
    private Formatter exporterFormatter = null;
    private FieldMetaData field;
    private int width;
    private int height;

    public SimpleResultParameterMetaData() {
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public FieldMetaData getFieldMetaData() {
        return this.field;
    }

    public void setFieldMetaData(final FieldMetaData field) {
        this.field = field;
    }

    public void setFormatter(final String name,
            final String pattern,
            final String nullValue) {
        try {
            SearchConfig config = SearchConfig.getInstance();
            String clazz = config.getSearchFormatter(name);

            this.formatter = (Formatter) Class.forName(clazz).newInstance();
            this.formatter.setPattern(pattern);
            this.formatter.setNullValue(nullValue);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Formatter getFormatter() {
        return this.formatter;
    }

    public void setExporterFormatter(final String name,
            final String pattern,
            final String nullValue) {
        try {
            SearchConfig config = SearchConfig.getInstance();
            String clazz = config.getSearchFormatter(name);

            this.exporterFormatter = (Formatter) Class.forName(clazz).newInstance();
            this.exporterFormatter.setPattern(pattern);
            this.exporterFormatter.setNullValue(nullValue);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Formatter getExporterFormatter() {
        return this.exporterFormatter;
    }

    public boolean isComposite() {
        return false;
    }

    public List getChildren() {
        return null;
    }

    public String toString() {
        return new StringBuffer("SimpleResultParameterMetaData[").append("name=").append(super.name).append(", field=").append(this.field).append("]").toString();
    }
}
