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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.dao.jpa.JpaDAOFactory;

/**
 *
 * @author amador
 */
public abstract class DAOFactory {

    private static Log log = LogFactory.getLog(DAOFactory.class);
    public static final int JPA = 1;
    private static final int DEFAULT_REPOSITORY = JPA;

    public static DAOFactory getInstance() {
        return getInstance(DEFAULT_REPOSITORY);
    }

    public static DAOFactory getInstance(int repository) throws DAOException {
        log.debug("Implementação para o repositório: " + repository);
        switch (repository) {
            case JPA:
                return JpaDAOFactory.getInstance();
            default:
                throw new DAOException("O repositório seleccionado não possui implementação.");
        }
    }
    
    public abstract String getPrefixoImpl();

    @SuppressWarnings("unchecked")
    public <T extends CrudDAO> T getDao(Class<T> daoInterface) throws DAOException {
        T dao = null;

        String daoPackage = daoInterface.getPackage().getName() + "." + getPrefixoImpl();
        String daoInterfaceName = daoInterface.getSimpleName();
        String daoClassSimpleName = getPrefixoImpl().substring(0, 1).toUpperCase().concat(getPrefixoImpl().substring(1)) + daoInterfaceName;
        String daoClassName = daoPackage + "." + daoClassSimpleName;
        log.debug("Implementação solicitada para o DAO: " + daoClassName);

        try {
            Class daoClass = Class.forName(daoClassName);
            dao = (T) daoClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new DAOException(e);
        }

        return dao;
    }
}
