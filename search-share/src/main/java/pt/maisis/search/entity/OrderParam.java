package pt.maisis.search.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 *
 * @author amador
 */
@Entity
public class OrderParam implements Serializable, Comparable {
    public static final String ORDER_TYPE_ASC = "0";
    public static final String ORDER_TYPE_DESC = "1";

    @EmbeddedId
    protected OrderParamPK orderParamPK = new OrderParamPK();
    private String orderType;
    private Integer displayOrder;

    public String getName() {
        return this.orderParamPK.getName();
    }

    public void setName(String name) {
        this.orderParamPK.setName(name);
    }

    public Report getReport() {
        return this.orderParamPK.getReport();
    }

    public void setReport(Report report) {
        this.orderParamPK.setReport(report);
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public OrderParamPK getOrderParamPK() {
        return orderParamPK;
    }

    public void setOrderParamPK(OrderParamPK orderParamPK) {
        this.orderParamPK = orderParamPK;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "OrderParam{"
                + " name=" + this.orderParamPK.getName()
                + " displayOrder=" + displayOrder
                + " orderType=" + orderType + '}';
    }

    public int compareTo(Object o) {
        if (!(o instanceof OrderParam)) {
            throw new ClassCastException("An OrderParam object expected.");
        }
        int oDisplayOrder = ((OrderParam) o).getDisplayOrder();
        return this.displayOrder - oDisplayOrder;
    }
}
