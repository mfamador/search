package pt.maisis.search.lucene;

public class IndexableBean implements Indexable {
    private static final String[] FIELDS = new String[] {"id", "name"};

    private String id;
    private String name;

    public IndexableBean(final String id, final String name) {
	this.id = id;
	this.name = name;
    }

    public String getId() {
	return this.id;
    }

    public String getName() {
	return this.name;
    }

    public String[] getFields() {
	return FIELDS;
    }

    public String getValue(String field) {
	if ("id".equals(field)) {
	    return this.id;
	}
	return this.name;
    }
}
