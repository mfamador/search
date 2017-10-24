package pt.maisis.search.validator;

import pt.maisis.search.ContainerMetaData;

import java.util.Locale;

/**
 * Interface que todos os <i>converters</i> devem, directa ou 
 * indirectamente, implementar.</br>
 * 
 * Por questões de facilidade foi criada a classe 
 * {@link AbstractConverter} que facilita a criacão de novos 
 * converters.
 *
 * @version 1.0
 */
public interface Converter {

    /**
     * No descriptor XML da pesquisa é possível definir propriedades
     * que podem ser utilizadas pelo <code>converter</code>.<br/>
     * 
     * Este método é incovado para cada propriedade definida.
     */
    void addProperty(String name,
            String value);

    /**
     * Método invocado pelo {@link validator} na fase em que precisa
     * de converter os dados.
     *
     * @return true se a conversão for efectuada com sucesso ou 
     *  false, caso contrário.
     */
    boolean convert(ValidationResult result,
            ValidationErrors errors,
            ContainerMetaData container,
            Locale locale);
}
