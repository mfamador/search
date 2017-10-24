package pt.maisis.search.dao;

import pt.maisis.search.SearchSetMetaData;

public interface SearchEngineMetaDataDao {

    /**
     * Retorna a metadata relativa a todas as pesquisas configuradas.
     *
     * @return <code>SearchSetMetaData</code> que representa todos 
     *  os tipos de pesquisas disponíveis.
     * @exception SearchDaoException se um erro ocorrer.
     */
    SearchSetMetaData getSearchSetMetaData()
            throws SearchDaoException;
}
