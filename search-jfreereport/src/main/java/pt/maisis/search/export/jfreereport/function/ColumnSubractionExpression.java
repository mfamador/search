package pt.maisis.search.export.jfreereport.function;

import java.io.Serializable;
import org.jfree.report.function.AbstractExpression;

/**
 * Calcula a subtracão entre duas colunas.
 * <p/>
 * A funcão aceita o parâmetro <code>minuend</code> e o parâmetro <code>subtrahend</code>.
 * <p/>
 * Fórmula:
 * <pre>
 * c (minuend) - b (subtrahend) = a (difference)</code>
 * </pre>
 *
 */
public class ColumnSubractionExpression
    extends AbstractExpression 
    implements Serializable {

    private String minuend;
    private String subtrahend;

    public ColumnSubractionExpression() {}


    public String getMinuend() {
        return this.minuend;
    }

    public void setMinuend(final String minuend) {
        this.minuend = minuend;
    }

    public String getSubtrahend() {
        return this.subtrahend;
    }

    public void setSubtrahend(final String subtrahend) {
        this.subtrahend = subtrahend;
    }

    /**
     * Retorna o valor da diferenca.
     */
    public Object getValue() {
        Object minuendValue = getDataRow().get(this.minuend);
        Object subtrahendValue = getDataRow().get(this.subtrahend);
        if (minuendValue instanceof Number == false || 
            subtrahendValue instanceof Number == false) {
            return null;
        }

        double a = ((Number)minuendValue).doubleValue();
        double b = ((Number)subtrahendValue).doubleValue();
        
        return new Double(a - b);
    }
}
