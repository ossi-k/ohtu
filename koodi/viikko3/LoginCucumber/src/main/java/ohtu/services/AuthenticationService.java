package ohtu.services;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import ohtu.data_access.UserDao;

public class AuthenticationService {

    private UserDao userDao;

    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (user.getUsername().equals(username)
                    && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    public boolean invalid(String username, String password) {
        // validity check of username and password
        boolean notOnlyLettersPassword = true;
        boolean notOnlyLettersUsername = true;
        char[] passwordArray = password.toCharArray();
        char[] usernameArray = username.toCharArray();

        for (int i = 0; i < passwordArray.length; i++) {
            if (!(Character.isLetter(passwordArray[i]))) {
                notOnlyLettersPassword = true;
                break;
            }
        }

        for (int i = 0; i < usernameArray.length; i++) {
            if (!(Character.isLetter(usernameArray[i]))) {
                notOnlyLettersUsername = true;
                break;
            }
        }

        if (userDao.findByName(username) != null) {
            return true;
        }

        if (passwordArray.length < 8) {
            return true;
        }
        
        if (usernameArray.length < 3) {
            return true;
        }

        if (!notOnlyLettersPassword) {
            return true;
        }

        if (!notOnlyLettersUsername) {
            return true;
        }
        return false;
    }
}
