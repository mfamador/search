package pt.maisis.search;

/**
 * Classe que representa um critério de pesquisa.
 *
 * @version 1.0
 */
public interface SearchParameterMetaData
        extends DynamicParameterMetaData {

    /**
     * Campo da persistência que este critério de pesquisa mapeia.
     */
    String getField();

    /**
     * Operador que deverá ser utilizado no processo de pesquisa para
     * se obter os resultados pretendidos.
     */
    String getOperator();

    /**
     * Retorna o <code>container</code> associado a este critério de 
     * pesquisa.
     */
    ContainerMetaData getContainer();

    /**
     * Retorna a metadata à qual este critério de pesquisa pertence.
     */
    SearchMetaData getSearchMetaData();
}
