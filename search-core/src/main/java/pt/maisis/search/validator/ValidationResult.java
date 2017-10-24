package pt.maisis.search.validator;

import java.util.Map;
import java.util.HashMap;

/**
 * Container que é inicialmente <i>populated</i> com os valores dos 
 * search params introduzidos pelo utilizador para uma dada pesquisa.
 *
 * <p>Em ambiente web, todos estes valores são strings.
 * À medida que os critérios de pesquisa vão sendo validados,
 * este container vai sendo actualizado com os valores devidamente 
 * convertidos, quando a conversão se aplicar.</p>
 *
 * @version 1.0
 */
public class ValidationResult {

    final Map params = new HashMap();

    public ValidationResult() {
    }

    /**
     * Cria uma nova instância criando uma copia dos valores 
     * contidos no dado <code>Map</code>.
     */
    public ValidationResult(final Map params) {
        this.params.putAll(params);
    }

    /**
     * Retorna o valor para o dado search param name.
     */
    public Object getValue(final String name) {
        return this.params.get(name);
    }

    /**
     * Remove o dado search param name do resultado.
     */
    public Object removeValue(final String name) {
        return this.params.remove(name);
    }

    /**
     * Retorna todos os valores dos search params.
     */
    public Map getValues() {
        return this.params;
    }

    /**
     * Permite actualizar ou adicionar um dado search param.
     */
    public void setValue(final String name, final Object value) {
        this.params.put(name, value);
    }

    public String toString() {
        return new StringBuffer("ValidationResult[").append("params=").append(params).append("]").toString();
    }
}
