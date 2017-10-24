package pt.maisis.search.util;

import java.util.AbstractList;
import java.util.List;
import java.io.Serializable;

/**
 * Simples decorator que possibilita aceder aos índices de uma dada lista sem que 
 * uma <code>IndexOutOfBoundsException</code> seja lancada caso o índice acedido 
 * seja maior ou igual que o tamanho da lista <code>(index >= size())</code>.
 * O conteúdo vazio é preenchido com nulls.
 */
public class SafeList extends AbstractList implements Serializable {

    private final List list;

    public static List decorate(List list) {
        return new SafeList(list);
    }

    protected SafeList(List list) {
        this.list = list;
    }

    public Object get(int index) {
        int size = size();

        if (index < size) {
            return this.list.get(index);
        } else {
            for (int i = size; i <= index; i++) {
                this.list.add(null);
            }
            return null;
        }
    }

    public Object set(int index, Object element) {
        int size = size();
        Object previousElement = get(index);
        this.list.set(index, element);
        return previousElement;
    }

    public int size() {
        return this.list.size();
    }
}
