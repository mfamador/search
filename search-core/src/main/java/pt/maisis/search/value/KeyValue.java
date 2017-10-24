package pt.maisis.search.value;

public class KeyValue {

    private String key;
    private String value;
    private boolean selected;

    public KeyValue() {
    }

    public KeyValue(final String key,
            final String value) {
        this(key, value, false);
    }

    public KeyValue(final String key,
            final String value,
            final boolean selected) {
        this.key = key;
        this.value = value;
        this.selected = selected;
    }

    public KeyValue(final KeyValue keyValue,
            final boolean selected) {
        this(keyValue.key, keyValue.value, selected);
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getValue() {
        if (this.value == null) {
            return this.key;
        }
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
