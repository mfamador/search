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
