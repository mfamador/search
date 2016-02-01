/*
 * %W% %E% Marco Amador
 *
 * Copyright (c) 1994-2011 Maisis - Information Systems. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Maisis
 * Information Systems, Lda. ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Maisis.
 *
 * MAISIS MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF
 * THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. MAISIS SHALL NOT BE LIABLE FOR
 * ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */
package pt.maisis.search.dao;

import pt.maisis.search.config.SearchConfig;
import pt.maisis.search.util.MessageResources;

/**
 * Factory responsável por criar instâncias de classes concretas 
 * que implementem o interface {@link SearchEngineMetaDataDao}.
 * <p>
 * Por defeito, a implementacão utilizada é a especificada
 * pela propriedade {@link #DEFAULT_CLASS}. 
 * 
 * @version 1.0
 */
public final class SearchEngineMetaDataDaoFactory {

    /** Implementacão a utilizar por defeito. */
    public static final String DEFAULT_CLASS =
            "pt.maisis.search.dao.xml.SearchEngineMetaDataDaoImpl";

    private SearchEngineMetaDataDaoFactory() {
    }

    public static SearchEngineMetaDataDao create() throws SearchDaoException {
        SearchConfig sc = SearchConfig.getInstance();
        String clazz = sc.getSearchEngineMetaDataClassName();

        if (clazz == null) {
            clazz = DEFAULT_CLASS;
        }

        try {
            return (SearchEngineMetaDataDao) Class.forName(clazz).newInstance();

        } catch (Exception e) {
            String message = MessageResources.getInstance().getMessage("config.searchEngineClassNotFound",
                    clazz, SearchConfig.SEARCH_CONFIG_FILE);
            throw new SearchDaoException(message);
        }
    }
}
