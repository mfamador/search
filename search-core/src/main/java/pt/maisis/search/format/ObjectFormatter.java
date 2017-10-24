package pt.maisis.search.format;

import java.util.Locale;

/**
 * Formatador que se limita a retornar o valor de um dado objecto
 * no formato string (toString()).
 * <br/>
 * Este formatador é utilizado pela framework de pesquisa, quando
 * nenhum formatador específico é definido no descriptor da 
 * pesquisa.
 *
 *
 * @version 1.0
 */
public class ObjectFormatter extends AbstractFormatter {

    public ObjectFormatter() {
    }

    /**
     * Retorna o resultado da invocacão do método 
     * <code>toString()</code> no dado <code>obj</code>.
     * NOTA: Se o parâmetro <code>obj</code> for uma instância 
     * de <code>String</code>, retorna essa mesma instância.
     *
     * @param obj objecto a formatar.
     * @param locale o locale é simplesmente ignorado.
     * @return toString() do parâmetro <code>obj</code> ou 
     *  null, caso parâmetro seja null.
     */
    public Object format(Object obj, Locale locale) {
        return obj;
    }
}
