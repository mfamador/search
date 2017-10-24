package pt.maisis.search.service;

import java.util.ArrayList;

public class MyList extends ArrayList {

    public String getName() {
        return (String) this.get(0);
    }

    public String getUsername() {
        return (String) this.get(1);
    }
}
