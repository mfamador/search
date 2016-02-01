package pt.maisis.search.export.jfreereport.function;

import java.io.Serializable;

/**
 * Função que faz exactamente o mesmo que a função, com o mesmo
 * nome, da framework JFreeReport, com excepção que retorna 
 * <code>null</code> caso o resultado seja um número inválido.
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
