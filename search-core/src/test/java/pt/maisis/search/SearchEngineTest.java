package pt.maisis.search;

import pt.maisis.search.config.SearchMetaDataConfig;
import pt.maisis.search.SearchMetaData;

import junit.framework.TestCase;


/**
 * @todo Completar esta classe de teste de forma a testar toda a 
 *       informação possível de definir nos ficheiros de config 
 *       das pesquisas.
 *
 * @version 1.0
 */
public class SearchEngineTest extends TestCase {

    public void testSearchMetaData() {
        SearchEngine se = SearchEngine.getInstance();
        SearchMetaData smd = se.getSearch("searchTest");
        
        assertEquals("Search Test", smd.getLabel());
        assertEquals("Search Test", smd.getDescription());
        assertEquals("pt.maisis.search.dao.db.SearchEngineDaoImpl", smd.getSearchEngineClass());
        assertEquals("java:comp/env/jdbc/SearchDS", smd.getDataSource());
        assertEquals("SEARCH_TEST", smd.getTableSource());

    }
}

