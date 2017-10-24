package pt.maisis.search.template;

import java.util.Map;
import java.util.HashMap;

public class Context {

    private final Map attributes = new HashMap();

    public Context() {
    }

    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    public Map getAttributes() {
        return this.attributes;
    }
}
