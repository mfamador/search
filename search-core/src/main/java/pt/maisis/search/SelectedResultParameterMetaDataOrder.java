package pt.maisis.search;

import java.io.Serializable;

public class SelectedResultParameterMetaDataOrder
        extends ParameterMetaDataImpl
        implements Serializable {

    /** Identifica o resultado de uma pesquisa para ser ordenado
    por este param de forma ascendente. */
    public static final String ASCENDING_ORDER = "ASC";
    /** Identifica o resultado de uma pesquisa para ser ordenado
    por este param de forma descendente. */
    public static final String DESCENDING_ORDER = "DESC";
    private String orderTypeAsString;

    public SelectedResultParameterMetaDataOrder() {
    }

    public int getOrderType() {
        return this.orderTypeAsString == ASCENDING_ORDER
                ? OrderParameter.ASCENDING
                : OrderParameter.DESCENDING;
    }

    public String getOrderTypeAsString() {
        return this.orderTypeAsString;
    }

    public void setOrderTypeAsString(final String orderTypeAsString) {
        if (orderTypeAsString != null) {
            this.orderTypeAsString = (ASCENDING_ORDER.equalsIgnoreCase(orderTypeAsString))
                    ? ASCENDING_ORDER
                    : DESCENDING_ORDER;
        }
    }
}
