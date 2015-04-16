package eu.ubitech.hibernateencryption.app;

import eu.ubitech.hibernateencryption.dao.UserDAO;
import eu.ubitech.hibernateencryption.dao.UserDAOImpl;
import eu.ubitech.hibernateencryption.models.User;
import java.util.List;

/**
 *
 * @author Christos Paraskeva
 */
public class App {

    public static void main(String[] args) {

        //DAO Definitions
        UserDAO userDAO = new UserDAOImpl();

        //Step1: Insert a User
        System.out.println("Create a user...");
        User user = new User("user123", "pass1234");
        userDAO.save(user);

        //Step2: | Find User based on username
        String userToFind = "user2";
        User findUser = userDAO.findByUsername(userToFind);
        if (null != findUser) {
            System.out.println("Found user: " + userToFind + " with ID: " + user.getId() + " and password: " + user.getPassword());
        } else {
            System.out.println("User: " + userToFind + " could not be found!");
        }

        //Step2: | Find User based on password (works only for unencrypted passwords)
        String passwordTomatch = "xxxxxxxxx";
        User findUser2 = userDAO.findByPassword(passwordTomatch);
        if (null != findUser2) {
            System.out.println("Match user with password: " + passwordTomatch + " and ID: " + findUser2.getId() + " and username: " + findUser2.getUsername());
        } else {
            System.out.println("No users found, with matching password : " + passwordTomatch);
        }

        //Step3: Get all Users
        List<User> users = userDAO.findAll(User.class);
        for (User tmpUser : users) {
            System.out.println("ID: " + tmpUser.getId() + " Username: " + tmpUser.getUsername() + " Password: " + tmpUser.getPassword());
        }
    }

}
