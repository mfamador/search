package pt.maisis.search;

import java.util.List;

public class OrderParameter extends ResultParameter {

    /** Identifica a ordem dos resultados de forma ascendente. */
    public static final int ASCENDING = 0;
    /** Identifica a ordem dos resultados de forma descendente. */
    public static final int DESCENDING = 1;
    private final int order;

    public OrderParameter(final String name,
            final List field,
            final int order) {
        super(name, field);
        this.order = (order == 0) ? ASCENDING : DESCENDING;
    }

    public int getOrder() {
        return this.order;
    }
}
