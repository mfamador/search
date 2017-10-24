package pt.maisis.search;

/**
 * Interface que marca um dado resultado como paginável.
 *
 * @version 1.0
 */
public interface Pageable {

    /**
     * Retorna <code>true</code> se o container contem mais 
     * páginas.
     */
    boolean hasNext();

    /**
     * Retorna <code>true</code> se a página actual não fôr
     * a primeira.
     */
    boolean hasPrevious();

    /**
     * Retorna o índice do início da próxima página.
     */
    int getStartOfNextPage();

    /**
     * Retorna o índice do início da página anterior.
     */
    int getStartOfPreviousPage();
}
