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
package pt.maisis.search.dao.jpa;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.config.SearchShareConfig;
import pt.maisis.search.dao.DAOFactory;

/**
 *
 * @author amador
 */
public class JpaDAOFactory extends DAOFactory {

    private static Log log = LogFactory.getLog(JpaDAOFactory.class);
    private static final JpaDAOFactory me = new JpaDAOFactory();
    private EntityManagerFactory emf = createEntityManagerFactory();

    private JpaDAOFactory() {
    }

    /**
     * Returns the instance of this service.
     */
    public static JpaDAOFactory getInstance() {
        return me;
    }

    private EntityManagerFactory createEntityManagerFactory() {
        Map<String, Object> configOverrides = new HashMap<String, Object>();
        SearchShareConfig ssc = SearchShareConfig.getInstance();
        
        if (ssc.getDataSourceName() != null) {
            log.debug("javax.persistence.transactionType: " + "RESOURCE_LOCAL");
            configOverrides.put("javax.persistence.transactionType", "RESOURCE_LOCAL");

            log.debug("javax.persistence.nonJtaDataSource: " + ssc.getDataSourceName());
            configOverrides.put("javax.persistence.nonJtaDataSource", ssc.getDataSourceName());
        } else if (ssc.getJtaDataSourceName() != null) {
            log.debug("javax.persistence.transactionType: " + "JTA");
            configOverrides.put("javax.persistence.transactionType", "JTA");

            log.debug("javax.persistence.nonJtaDataSource: " + ssc.getJtaDataSourceName());
            configOverrides.put("javax.persistence.jtaDataSource", ssc.getJtaDataSourceName());
        }
        
        if (ssc.getAutoCreateDb() != null) {
            log.debug("hibernate.hbm2ddl.auto: " + ssc.getAutoCreateDb());
            configOverrides.put("hibernate.hbm2ddl.auto", ssc.getAutoCreateDb());
        }
        
        if (ssc.getShowSql() != null) {
            log.debug("hibernate.show_sql: " + ssc.getShowSql());
            configOverrides.put("hibernate.show_sql", ssc.getShowSql());
        }

        return Persistence.createEntityManagerFactory("search-share", configOverrides);
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

    @Override
    public String getPrefixoImpl() {
        return "jpa";
    }
}
