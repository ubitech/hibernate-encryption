/**
 * An interface which provide all the available methods of a Generic Data Access
 * Object Class.
 */
package eu.ubitech.hibernateencryption.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Christos Paraskeva
 *
 * @param <T> Entity
 * @param <ID> Unique ID field
 */
public interface GenericDAO<T, ID extends Serializable> {
    

    /**
     * Save entity to database.
     *
     * @param entity The entity object to store in DB
     */
    public void save(T entity);

    /**
     * Save a Set of entities to database.
     *
     * @param Set<entity> The entity object to store in DB
     */
    public void saveAllTransaction(Set<T> entities, Session session);

    public void saveAll(Set<T> entities);

    /**
     * Save with in a transaction session
     *
     * @param entity
     * @param txSession
     */
    public void saveInTransaction(T entity, Session txSession);

    public void merge(T entity);

    /**
     * Delete a specific entity from database.
     *
     * @param entity The entity object to delete from DB
     */
    public void delete(T entity);

    /**
     * Finds a set of entities based on a given query.
     *
     * @param query The query in HQL to make the search
     * @return A list of the entities matched the query criteria
     */
    public List<T> findMany(Query query);

    /**
     * Find a specific entity based on a given query.
     *
     * @param query The query in HQL to make the search
     * @return A unique entity returned by the given query
     */
    public T findOne(Query query);

    /**
     * Find a set of entities based on a given query.
     *
     * @param clazz The entity type to retrieve all objects
     * @return A set of all entities of current type which are stored in DB
     */
    public List findAll(Class clazz);

    /**
     * Find an entity given a unique ID.
     *
     * @param clazz The instance of the entity to return
     * @param id The id of the entity to make the search
     * @return A unique entity based on the given ID
     */
    public T findByID(Class clazz, BigDecimal id);

}
