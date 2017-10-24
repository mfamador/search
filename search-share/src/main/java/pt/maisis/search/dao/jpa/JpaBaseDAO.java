package pt.maisis.search.dao.jpa;

import java.util.List;
import java.util.Map;
import javax.persistence.Query;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pt.maisis.search.dao.CrudDAO;
import pt.maisis.search.dao.DAOException;
import pt.maisis.search.entity.CrudBaseEntity;

public abstract class JpaBaseDAO<T extends CrudBaseEntity<T, K>, K> implements CrudDAO<T, K> {

    private static Log log = LogFactory.getLog(JpaBaseDAO.class);

    public JpaBaseDAO() {
    }

    public abstract Class<T> getEntityClass();

    @PersistenceContext(unitName = "search-share")
    protected EntityManager getEntityManager() {
        return JpaDAOFactory.getInstance().getEntityManagerFactory().createEntityManager();
    }

    public void save(T unsaved) throws DAOException {
        EntityManager manager = getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = manager.getTransaction();
            tx.begin();
            if (unsaved.getId() == null) {
                manager.persist(unsaved);
            } else {
                manager.merge(unsaved);
            }
            manager.flush();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            log.error(e);
            throw new DAOException(e);
        } finally {
            manager.close();
        }
    }

    public void remove(K id) throws DAOException {
        EntityManager manager = getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = manager.getTransaction();
            tx.begin();
            manager.remove(manager.getReference(getEntityClass(), id));
            manager.flush();
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            log.error(e);
            throw new DAOException(e);
        } finally {
            manager.close();
        }
    }

    public List<T> find() throws DAOException {
        return find(null, null, null);
    }

    public T find(K id) throws DAOException {
        EntityManager manager = getEntityManager();
        try {
            return manager.find(getEntityClass(), id);
        } catch (RuntimeException e) {
            throw new DAOException("Erro ao tentar recuperar a entidade " + getEntityClass().getName() + " com o identificador: " + id + ".", e);
        }
    }

    public List<T> find(Map<String, Object> criteria) {
        return find(null, null, criteria, null);
    }

    public List<T> find(Map<String, Object> criteria, Map<String, String> orderCriteria) {
        return find(null, null, criteria, orderCriteria);
    }

    public List<T> find(Integer start, Integer count, Map<String, Object> criteria) {
        return find(null, null, criteria, null);
    }

    public List<T> find(Integer start, Integer count) {
        return find(null, null, null, null);
    }

    public List<T> find(Integer start, Integer count, Map<String, Object> criteria, Map<String, String> orderCriteria) {
        StringBuilder sb = new StringBuilder().append("SELECT t FROM ").append(getEntityClass().getSimpleName()).append(" t");

        if (criteria != null && !criteria.isEmpty()) {
            sb.append(" WHERE ");
            int i = 0;
            for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                if (i > 0) {
                    sb.append(" AND ");
                }
                sb.append(entry.getKey()).append(" = :").append(entry.getKey());
                i++;
            }
        }

        if (orderCriteria != null && !orderCriteria.isEmpty()) {
            for (Map.Entry<String, String> entry : orderCriteria.entrySet()) {
                sb.append(" ORDER BY ").append(entry.getKey()).append(" ").append(entry.getValue());
            }
        }

        log.debug("query = " + sb.toString());
        EntityManager manager = getEntityManager();
        Query query = manager.createQuery(sb.toString());

        if (criteria != null && !criteria.isEmpty()) {
            for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        if (start != null) {
            if (start < 0) {
                start = 0;
            }
            query.setFirstResult(start.intValue());
        }

        if (count != null) {
            query.setMaxResults(count.intValue());
        }

        return query.getResultList();
    }

    public int count() throws DAOException {
        return count(null);
    }

    public int count(Map<String, Object> criteria) {
        StringBuilder sb = new StringBuilder().append("SELECT count(t) FROM ").append(getEntityClass().getSimpleName()).append(" t");

        if (criteria != null && !criteria.isEmpty()) {
            sb.append(" WHERE ");
            int i = 0;
            for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                if (i > 0) {
                    sb.append(" AND ");
                }
                sb.append(entry.getKey()).append(" = :").append(entry.getKey());
                i++;
            }
        }

        log.debug("query = " + sb.toString());
        EntityManager manager = getEntityManager();
        Query query = manager.createQuery(sb.toString());

        if (criteria != null && !criteria.isEmpty()) {
            for (Map.Entry<String, Object> entry : criteria.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        return ((Long) query.getSingleResult()).intValue();
    }
}
