package pt.maisis.search.dao;

import pt.maisis.search.SearchQuery;
import pt.maisis.search.SearchResult;

/**
 * Interface que deve ser implementado em funcão das necessidades
 * do sistema.<br/>
 *
 * Neste momento existem duas implementacão diferentes:
 * {@link pt.maisis.search.dao.db.SearchEngineDaoImpl} e
 * {@link pt.maisis.search.dao.db.VelocitySearchEngineDao}.
 *
 * @version 1.0
 */
public interface SearchEngineDao {

    /**
     * Efectua uma dada pesquisa, retornando todos os resultados
     * resultantes da pesquisa.
     *
     * @param query query com a informacão para a pesquisa.
     * @return uma instância de {@link SearchResult} com o resultado
     *  da pesquisa.
     * @exception {@link SearchDaoException} se ocorrer um erro.
     */
    SearchResult executeSearch(SearchQuery query)
            throws SearchDaoException;

    /**
     * Efectua uma dada pesquisa cujos resultados podem ser paginados.
     *
     * @param query query com a informacão para a pesquisa.
     * @param start índice do primeiro resultado a considerar.
     * @param count número de resultados desejados.
     * @return uma instância de {@link SearchResult} com o resultado
     *  da pesquisa.
     * @exception {@link SearchDaoException} se ocorrer um erro.
     */
    SearchResult executeSearch(SearchQuery query,
            int start,
            int count)
            throws SearchDaoException;

    /**
     * Efectua uma dada pesquisa cujos resultados podem ser paginados.
     * Se o parâmetro <code>total</total> for igual a zero é feito um
     * count dos resultados.
     *
     * @param query query com a informacão para a pesquisa.
     * @param start índice do primeiro resultado a considerar.
     * @param count número de resultados desejados.
     * @param total contador dos resultados.
     * @return uma instância de {@link SearchResult} com o resultado
     *  da pesquisa.
     * @exception {@link SearchDaoException} se ocorrer um erro.
     */
    SearchResult executeSearch(SearchQuery query,
            int start,
            int count,
            int total)
            throws SearchDaoException;
}
