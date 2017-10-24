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
