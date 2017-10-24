package pt.maisis.search.dao;

import java.util.List;
import java.util.Map;

public interface CrudDAO<Entidade, Key> {

    public void save(Entidade entidade) throws DAOException;

    public void remove(Key oid);

    public List<Entidade> find();

    public List<Entidade> find(Integer start, Integer count);

    public List<Entidade> find(Integer start, Integer count, Map<String, Object> criteria, Map<String, String> orderCriteria);

    public List<Entidade> find(Integer start, Integer count, Map<String, Object> criteria);

    public List<Entidade> find(Map<String, Object> criteria);

    public List<Entidade> find(Map<String, Object> criteria, Map<String, String> orderCriteria);

    public Entidade find(Key key);

    public int count();

    public int count(Map<String, Object> criteria);
}
