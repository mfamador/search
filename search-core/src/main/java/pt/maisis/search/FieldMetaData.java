package pt.maisis.search;

import java.util.List;
import java.util.ArrayList;

public class FieldMetaData {

    private List fields = new ArrayList();

    public FieldMetaData() {
    }

    public List getFields() {
        return this.fields;
    }

    public String getField(final int index) {
        return (String) this.fields.get(index);
    }

    public void addField(final String field) {
        this.fields.add(field);
    }

    public void addFieldMetaData(final FieldMetaData field) {
        this.fields.addAll(field.getFields());
    }

    public String toString() {
        return new StringBuffer("FieldMetaData[").append("fields=").append(this.fields).append("]").toString();
    }
}
