package pt.maisis.search.value;

import java.util.List;

public interface ListValue extends Value {

    List getList();

    List getList(String selected);

    List getList(String[] selected);
}
