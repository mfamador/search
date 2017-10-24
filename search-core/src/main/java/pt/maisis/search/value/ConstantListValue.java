package pt.maisis.search.value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Classe que representa uma lista de constantes (key/value).
 * 
 */
public class ConstantListValue implements ListValue {

    private List entries = new ArrayList();
    private List selected = new ArrayList();

    public List getList() {
        return this.entries;
    }

    public List getList(String selected) {
        return getList(new String[]{selected});
    }

    public List getList(String[] selected) {
        List result = new ArrayList(this.entries.size());

        for (Iterator it = this.entries.iterator(); it.hasNext();) {
            KeyValue keyValue = (KeyValue) it.next();

            boolean selectKey = false;
            if (selected != null) {
                for (int i = 0; i < selected.length; i++) {
                    if (keyValue.getKey() != null
                            && keyValue.getKey().equals(selected[i])) {
                        selectKey = true;
                        break;
                    }
                }
            }
            result.add(new KeyValue(keyValue, selectKey));
        }
        return result;
    }

    public Object getValue() {
        if (this.selected.isEmpty()) {
            return null;
        }
        if (this.selected.size() == 1) {
            return this.selected.get(0);
        }

        String[] selectedStrings = new String[this.selected.size()];
        this.selected.toArray(selectedStrings);
        return selectedStrings;
    }

    public Object getValue(Map context) {
        return getValue();
    }

    public void addKeyValue(final KeyValue keyValue) {
        this.entries.add(keyValue);
        if (keyValue.isSelected()) {
            this.selected.add(keyValue.getKey());
        }
    }
}
