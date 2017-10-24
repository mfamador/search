package pt.maisis.search.format;

import java.util.Locale;

/**
 * Interface que todos os formatadores (formatters) devem, directa
 * ou indirectamente, implementar.
 *
 * @version 1.0
 */
public interface Formatter {

    /**
     * Todos os formatadores podem usufruir da possibilidade de
     * especificar um padrão (pattern) no descriptor da pesquisa.
     */
    void setPattern(String pattern);

    /**
     * @return o padrão especificado no descriptor da pesquisa.
     */
    String getPattern();

    /**
     * String a utilizar caso o valor do result param for null.
     */
    void setNullValue(String nullValue);

    /**
     * Retorna o valor por defeito especificado através de
     * {@link #setNullValue()}.
     */
    String getNullValue();

    /**
     * Método que permite formatar um dado objecto utilizando
     * o locale por defeito da JVM.
     *
     * @param obj objecto a formaar.
     * @return String resultante da formatacão.
     */
    Object format(Object obj);

    /**
     * Método que permite formatar um dado objecto utilizando
     * o locale especificado.
     *
     * @param obj objecto a formatar.
     * @param locale intância de {@link Locale} a ter em conta
     *  na formatacão.
     * @return String resultante da formatacão.
     */
    Object format(Object obj, Locale locale);
}
