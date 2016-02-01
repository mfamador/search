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
package pt.maisis.search.entity;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pt.maisis.search.dao.CrudDAO;
import pt.maisis.search.dao.DAOException;
import pt.maisis.search.dao.DAOFactory;

/**
 *
 * @author amador
 */
public class CrudEntity<Entity, Type> implements CrudBaseEntity<Entity, Type> {

    private static Log log = LogFactory.getLog(CrudEntity.class);

    public Type getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    protected Class<Entity> entityClass;

    private Class getDaoClass() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<Entity>) genericSuperclass.getActualTypeArguments()[0];
        String daoClassName = "pt.maisis.search.dao." + this.entityClass.getSimpleName() + "DAO";
        try {
            Class daoClass = Class.forName(daoClassName);
            return daoClass;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    public void save() throws BusinessException {
        DAOFactory factory = DAOFactory.getInstance();
        try {
            CrudDAO dao = factory.getDao(getDaoClass());
            dao.save(this);
        } catch (DAOException e) {
            log.error("ERROR : " + this);
            throw new BusinessException(e);
        }
    }

    public Entity find(long oid) {
        DAOFactory factory = DAOFactory.getInstance();

        CrudDAO dao = factory.getDao(getDaoClass());

        return (Entity) (dao).find(oid);
    }

    public Entity find(Map criteria) {
        DAOFactory factory = DAOFactory.getInstance();

        CrudDAO dao = factory.getDao(getDaoClass());

        return (Entity) (dao).find(criteria);
    }

    public void remove() throws BusinessException {
        DAOFactory factory = DAOFactory.getInstance();
        CrudDAO dao = factory.getDao(getDaoClass());
        dao.remove(this.getId());
    }
}
