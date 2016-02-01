package pt.maisis.search.lucene;


public interface Indexable {
    
    /**
     * returns the names os the fields to index.
     */
    String[] getFields();

    /**
     * returns the value for a given field.
     */
    String getValue(String field);
}
