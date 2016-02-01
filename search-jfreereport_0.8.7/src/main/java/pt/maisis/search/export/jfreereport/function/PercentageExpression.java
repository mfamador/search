package pt.maisis.search.export.jfreereport.function;

import java.io.Serializable;

/**
 * Fun��o que faz exactamente o mesmo que a fun��o, com o mesmo
 * nome, da framework JFreeReport, com excep��o que retorna 
 * <code>null</code> caso o resultado seja um n�mero inv�lido.
 *
 * @version 1.0
 *
 * @see org.jfree.report.function.PercentageExpression
 */
public class PercentageExpression 
    extends org.jfree.report.function.PercentageExpression
    implements Serializable {

    public PercentageExpression () {}

    public Object getValue () {
        
        Double value = (Double) super.getValue();
        if (value != null) {
            if (value.isNaN() || value.isInfinite()) {
                return null;
            }
        }
        return value;
    }
}
