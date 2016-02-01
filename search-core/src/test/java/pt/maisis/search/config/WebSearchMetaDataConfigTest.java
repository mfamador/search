package pt.maisis.search.config;

import junit.framework.TestCase;


/**
 * Testes para a classe {@link WebSearchMetaDataConfig}.
 *
 * @version 1.0
 */
public class WebSearchMetaDataConfigTest extends TestCase {

    
    public void testConfig() {
        try {
            WebSearchMetaDataConfig config = WebSearchMetaDataConfig.getInstance();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
