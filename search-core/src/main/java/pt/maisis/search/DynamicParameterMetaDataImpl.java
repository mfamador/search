package pt.maisis.search;

import java.util.Map;
import java.util.HashMap;

/**
 *
 * @version 1.0
 */
public class DynamicParameterMetaDataImpl
        extends ParameterMetaDataImpl
        implements DynamicParameterMetaData {

    /** Map vazio a ser utilizado quando nenhuma propriedade dinamica e 
    declarada no descriptor xml da pesquisa. */
    private static final Map NO_PROPERTIES = new HashMap(0);
    private Map properties;

    public DynamicParameterMetaDataImpl() {
    }

    public Map getProperties() {
        return (this.properties == null)
                ? NO_PROPERTIES
                : this.properties;
    }

    public String getProperty(final String name) {
        return (this.properties == null)
                ? null
                : (String) this.properties.get(name);
    }

    public void addProperty(final String name, final String value) {
        if (this.properties == null) {
            this.properties = new HashMap();
        }
        properties.put(name, value);
    }
}
