package pt.maisis.search;

import java.util.Map;

/**
 * Interface que representa a <i>metadata</i> dinâmica de um dado
 * <i>parameter</i> ({@link SearchParameterMetaData} ou 
 * {@link ResultParameterMetaData}). <br/>
 *
 * Nos descriptors XML das pesquisas é possível definir, para além 
 * das propriedades disponíveis por defeito (name, field, etc), 
 * propriedades dinâmicas.
 *
 * @version 1.0
 */
public interface DynamicParameterMetaData
        extends ParameterMetaData {

    /**
     * Retorna todas as propriedades dinâmicas associadas
     * ao campo de retorno ou critério de pesquisa.
     */
    Map getProperties();

    /**
     * Retorna o valor da dada propriedade.
     */
    String getProperty(String name);
}
