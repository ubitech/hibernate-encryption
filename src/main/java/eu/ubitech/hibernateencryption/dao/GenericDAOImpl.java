/**
 * An abstract implementation of all the methods provided by the Generic Data
 * Access Object Interface.
 */
package eu.ubitech.hibernateencryption.dao;

import eu.ubitech.hibernateencryption.app.HibernateUtil;
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
public abstract class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

    protected Session getSession() {
        return HibernateUtil.INSTANCE.getSession();
    }

    protected void commitTransaction(Session session) {
        HibernateUtil.INSTANCE.commitTransaction(session);
    }

    protected void rollbackTransaction(Session session) {
        HibernateUtil.INSTANCE.rollbackTransaction(session);
    }

    protected void closeSession(Session session) {
        HibernateUtil.INSTANCE.closeSession(session);
    }

    public void save(T entity) {
        Session hibernateSession = this.getSession();
        hibernateSession.beginTransaction();
        hibernateSession.saveOrUpdate(entity);
        this.commitTransaction(hibernateSession);
    }

    public void saveAll(Set<T> entities) {
        Session hibernateSession = this.getSession();
        hibernateSession.beginTransaction();
        int iters = 0;
        for (T t : entities) {
            hibernateSession.saveOrUpdate(t);
            if (iters++ % 20 == 0) { //20, same as the JDBC batch size
                //flush a batch of inserts and release memory:
                hibernateSession.flush();
                hibernateSession.clear();
            }
        }
        this.commitTransaction(hibernateSession);
    }

    public void merge(T entity) {
        Session hibernateSession = this.getSession();
        hibernateSession.merge(entity);
    }

    public void delete(T entity) {
        Session hibernateSession = this.getSession();
        hibernateSession.delete(entity);
    }

    public List<T> findMany(Query query) {
        List<T> t;
        t = (List<T>) query.list();
        return t;
    }

    public T findOne(Query query) {
        T t;
        t = (T) query.uniqueResult();
        return t;
    }

    public T findByID(Class clazz, BigDecimal id) {
        Session hibernateSession = this.getSession();
        T t = null;
        t = (T) hibernateSession.get(clazz, id);
        return t;
    }

    public List findAll(Class clazz) {
        Session hibernateSession = this.getSession();
        List T = null;
        Query query = hibernateSession.createQuery("from " + clazz.getName());
        T = query.list();
        return T;
    }

    /*
     *  The below methods ara used only for transactional operations
     */
    /**
     *
     * @param entities
     * @param txSession
     */
    public void saveAllTransaction(Set<T> entities, Session txSession) {
        int iters = 0;
        for (T t : entities) {
            txSession.save(t);
            if (iters++ % 20 == 0) { //20, same as the JDBC batch size
                //flush a batch of inserts and release memory:
                txSession.flush();
                txSession.clear();
            }
        }
    }

    /**
     *
     * @param entity
     * @param txSession
     */
    public void saveInTransaction(T entity, Session txSession) {
        txSession.saveOrUpdate(entity);
    }

}
