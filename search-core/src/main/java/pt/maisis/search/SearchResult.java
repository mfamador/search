package pt.maisis.search;

import java.util.List;

/**
 * Classe que representa o resultado de uma pesquisa.
 *
 * @version 1.0
 */
public interface SearchResult {

    /**
     * @return a lista de elementos ({@link SearchResultElement})
     * contidos no resultado.
     */
    List getElements();

    List getNames();

    /**
     * @return o {@link SearchResultElement} para o dado
     * <code>index</code>.
     */
    SearchResultElement getElement(int index);

    /**
     * @return o número de elementos contidos no resultado da
     * pesquisa.
     */
    int getResultCount();

    /**
     * @return <code>true</code> se o resultado da pesquisa estiver
     * vazio.
     */
    boolean isEmpty();

    /**
     * Permite verificar se o resultado contém o campo de retorno
     * identificado pelo parâmetro <code>name</code>.
     */
    boolean contains(String name);

    /**
     * @return o índice de um dado result parameter através
     * do nome.
     */
    int getIndex(String name);

    String getName(int index);
}
