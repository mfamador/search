package pt.maisis.search.config;

import junit.framework.TestCase;


/**
 * Testes para a classe {@link SearchConfig}.
 *
 * @version 1.0
 */
public class SearchConfigTest extends TestCase {

    
    public void testConfig() {
        SearchConfig config = SearchConfig.getInstance();

        assertEquals("java:comp/env/jdbc/SearchEngineDBPool", 
                     config.getDataSourceName());

        assertEquals("pt.maisis.search.dao.db.SearchEngineDaoImpl", 
                     config.getSearchEngineClassName());

        assertEquals("pt.maisis.search.dao.xml.SearchEngineMetaDataDaoImpl", 
                     config.getSearchEngineMetaDataClassName());
        
        assertEquals("test-search.xml", 
                     config.getSearchEngineMetaDataDescriptors().get(0));
    }
}
