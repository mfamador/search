package pt.maisis.search.entity;

import java.io.Serializable;

/**
 *
 * @author amador
 */
public interface CrudBaseEntity<T, K> extends Serializable {

    public K getId();

    public void save() throws BusinessException;

    public void remove() throws BusinessException;
}
