package pt.maisis.search;

import pt.maisis.search.dao.SearchEngineDaoFactory;
import pt.maisis.search.dao.SearchEngineDao;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

/**
 * Container de todas as pesquisas configuradas.
 *
 * @version 1.0
 */
public class SearchSetMetaData implements Serializable {

    /** Índice das pesquisas. */
    private final Map metaData = new HashMap();
    /** Implementacões do motor de pesquisa, por pesquisa. */
    private final Map implementations = new HashMap();

    public SearchSetMetaData() {
    }

    public void add(SearchMetaData metaData) {
        this.metaData.put(metaData.getName(), metaData);

        this.implementations.put(metaData.getName(),
                SearchEngineDaoFactory.create(metaData.getSearchEngineClass()));
    }

    public int size() {
        return this.metaData.size();
    }

    public SearchMetaData getMetaData(String name) {
        return (SearchMetaData) this.metaData.get(name);
    }

    public SearchEngineDao getImplementation(String name) {
        return (SearchEngineDao) this.implementations.get(name);
    }

    public SearchMetaData[] getAll() {
        SearchMetaData[] result = new SearchMetaData[this.metaData.size()];
        this.metaData.values().toArray(result);
        return result;
    }

    public String toString() {
        return new StringBuffer("SearchSetMetaData[").append("metaData=").append(metaData.values()).append("]").toString();
    }
}
