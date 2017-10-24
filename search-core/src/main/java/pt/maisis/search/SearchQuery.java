package pt.maisis.search;

import java.util.List;

/**
 * Classe que representa uma query de pesquisa.
 *
 * @version 1.0
 */
public interface SearchQuery {

    /**
     * Nome da datasource a ser utilizada.
     */
    String getDataSource();

    /**
     * Nome da tabela/view a utilizar na pesquisa.
     */
    String getTableSource();

    /**
     * SQL template.
     */
    String getSqlTemplate();

    /**
     * Critérios de pesquisa a utilizar na pesquisa.
     * Retorna uma lista vazia caso não tenham sido
     * especificados nenhuns critérios de pesquisa.
     * @see SearchParameter
     */
    List getSearchParams();

    SqlFragmentParameter getSqlFragmentParam();

    /**
     * Retorna os campos de retorno a serem devolvidos 
     * no resultado da pesquisa.
     * @see ResultParameter
     */
    List getResultParams();

    /**
     * Campos de ordenacão a utilizar na pesquisa.
     * Retorna uma lista vazia caso não tenham sido
     * especificados nenhuns campos de ordenacão.
     * @see OrderParameter
     */
    List getOrderParams();

    /**
     */
    int getResultRecordLimit();

    /**
     */
    void setResultRecordLimit(int resultRecordLimit);

    /**
     */
    int getQueryTimeout();

    /**
     */
    void setQueryTimeout(int queryTimout);
}
