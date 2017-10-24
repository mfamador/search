package pt.maisis.search.export.jfreereport.function;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Fun��o que faz exactamente o mesmo que a funcao, com o mesmo
 * nome, da framework JFreeReport, com excepcao que retorna 
 * <code>null</code> caso o resultado seja um numero inv�lido.
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
        BigDecimal value = (BigDecimal) super.getValue();
//        if (value != null) {
//            if (value.isNaN() || value.isInfinite()) {
//                return null;
//            }
//        }
        return value;
    }
}
