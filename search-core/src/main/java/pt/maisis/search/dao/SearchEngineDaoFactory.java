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

/**
 * Factory responsável por criar instâncias de classes que
 * implementem o interface {@link SearchEngineDao}.
 * <p>
 * Por defeito, a implementacão utilizada é a especificada
 * pela propriedade {@link #DEFAULT_CLASS}. No entanto, caso
 * necessário, pode ser especificada no ficheiro de configuracão
 * (<code>search-config.xml</code>), a implementacão desejada.
 *
 * @see pt.maisis.search.dao.SearchEngineDao
 */
public final class SearchEngineDaoFactory {

    /** Implementacão a utilizar por defeito. */
    public static final String DEFAULT_CLASS =
            "pt.maisis.search.dao.db.SearchEngineDaoImpl";

    private SearchEngineDaoFactory() {
    }

    /**
     * Retorna uma implementacão do interface {@link SearchEngineDao}.
     */
    public static SearchEngineDao create() throws SearchDaoException {

        SearchConfig sc = SearchConfig.getInstance();
        String clazz = sc.getSearchEngineClassName();
        return (clazz == null)
                ? create(DEFAULT_CLASS)
                : create(clazz);
    }

    /**
     * Retorna a implementacão do interface {@link SearchEngineDao},
     * identificada pelo parâmetro <code>className</code>.
     */
    public static SearchEngineDao create(final String className)
            throws SearchDaoException {

        if (className == null) {
            return SearchEngineDaoFactory.create();
        }
        try {
            Object obj = Class.forName(className).newInstance();

            if (obj instanceof SearchEngineDao) {
                return (SearchEngineDao) obj;
            }

            throw new SearchDaoException(className + " nao e uma instancia de SearchEngineDao");

        } catch (Exception e) {
            throw new SearchDaoException(e);
        }
    }
}
