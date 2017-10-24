package pt.maisis.search.dao.xml;

import pt.maisis.search.SearchSetMetaData;
import pt.maisis.search.dao.SearchDaoException;
import pt.maisis.search.dao.SearchEngineMetaDataDao;
import pt.maisis.search.config.SearchMetaDataConfig;

public class SearchEngineMetaDataDaoImpl implements SearchEngineMetaDataDao {

    public SearchEngineMetaDataDaoImpl() {
    }

    public SearchSetMetaData getSearchSetMetaData() throws SearchDaoException {
        SearchMetaDataConfig config = SearchMetaDataConfig.getInstance();
        return config.getSearchSetMetaData();
    }
}
