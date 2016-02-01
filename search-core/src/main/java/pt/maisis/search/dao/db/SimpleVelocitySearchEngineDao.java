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
package pt.maisis.search.dao.db;

import pt.maisis.search.SearchQuery;
import pt.maisis.search.dao.SearchDaoException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import pt.maisis.search.dao.SearchEngineDao;

/**
 * Implementacão de {@link SearchEngineDao} que permite efectuar
 * pesquisas a uma base de dados, tendo em conta templates SQL
 * definidas nos ficheiros de configuracão das pesquisas.<p/>
 *
 * Esta implementacão é igual à implementacão 
 * {@link VelocitySearchEngineDao}. No entanto, ...
 *
 * @todo pensar melhor nisto!!!
 *
 * @version 1.0
 */
public class SimpleVelocitySearchEngineDao extends VelocitySearchEngineDao {

    @Override
    protected void setValues(final SearchQuery query,
            final PreparedStatement ps)
            throws SQLException, SearchDaoException {
        // nada a fazer
    }
}
