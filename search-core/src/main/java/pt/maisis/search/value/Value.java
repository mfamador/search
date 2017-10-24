package pt.maisis.search.value;

import java.util.Map;

public interface Value {

    /**
     * Retorna o valor por defeito a apresentar
     * para um dado container. 
     *
     * @return uma string ou um array de strings.
     */
    Object getValue();

    Object getValue(Map context);
}
