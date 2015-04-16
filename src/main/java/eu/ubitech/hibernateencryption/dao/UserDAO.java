/**
 *  A DAO for entity User extending the Generic Data Access interface.
 */
package eu.ubitech.hibernateencryption.dao;


import eu.ubitech.hibernateencryption.models.User;
import java.math.BigDecimal;

/**
 *
 * @author Christos Paraskeva
 */
public interface UserDAO extends GenericDAO<User, BigDecimal> {
    
 public User findByUsername(String username);
  public User findByPassword(String password);
    

}
