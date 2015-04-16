/**
 * An implementation of all the methods provided by the User Data Access Object
 * Interface.
 */
package eu.ubitech.hibernateencryption.dao;

import eu.ubitech.hibernateencryption.models.User;
import java.math.BigDecimal;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Christos Paraskeva
 */
public class UserDAOImpl extends GenericDAOImpl<User, BigDecimal> implements UserDAO {

    /**
     * Fetch a specific User from database based on the given username.
     *
     * @param username The username of the entity User to search in DB.
     * @return An instance of User entity which found in DB (returns null if the
     * entity does not exist).
     */
    public User findByUsername(String username) {
        User user = null;
        Session session = super.getSession();
        session.beginTransaction();
        //super.getSession().getTransaction().begin();
        Query query = session.createQuery("from User where username= :username").setMaxResults(1).setParameter("username", username);
        user = this.findOne(query);
        super.commitTransaction(session);
        return user;
    }
    
    
    
        /**
     * Fetch a specific User from database based on the given password.
     *
     * @param password The password of the entity User to search in DB.
     * @return An instance of User entity which found in DB (returns null if the
     * entity does not exist).
     */
    public User findByPassword(String password) {
        User user = null;
        Session session = super.getSession();
        session.beginTransaction();
        Query query = session.createQuery("from User where password= :password").setMaxResults(1).setParameter("password", password);
        user = this.findOne(query);
        super.commitTransaction(session);
        return user;
    }
}
