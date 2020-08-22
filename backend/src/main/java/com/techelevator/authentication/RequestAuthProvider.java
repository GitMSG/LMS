package com.techelevator.authentication;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import com.techelevator.user.User;
import com.techelevator.user.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RequestAuthProvider
 */
@Component
public class RequestAuthProvider implements AuthProvider {

    private HttpServletRequest request;
    private UserDao dao;
    public final static String USER_KEY = "appCurrentUser";

    @Autowired
    public RequestAuthProvider(HttpServletRequest request, UserDao dao) {
        this.request = request;
        this.dao = dao;
    }

    @Override
    public boolean isLoggedIn() {
        return (request.getAttribute(USER_KEY) != null);
    }

    @Override
    public User getCurrentUser() {
        return (User) request.getAttribute(USER_KEY);
    }

    @Override
    public boolean signIn(String email, String password) {
        User authenticatedUser = dao.getValidUserWithPassword(email, password);
        if (authenticatedUser != null) {
        	System.out.println("inside RAP 'signin  method  "+ dao.getUserByEmail(email).toString());
            request.setAttribute(USER_KEY, authenticatedUser);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void logOff() {
        request.removeAttribute(USER_KEY);
    }

    @Override
    public boolean changePassword(String existingPassword, String newPassword) {
        User userFromSession = (User) request.getAttribute(USER_KEY);
        if (userFromSession == null) {
            return false;
        }
        User userFromDb = dao.getValidUserWithPassword(userFromSession.getEmail(), existingPassword);
        if (userFromDb != null && userFromDb.getId() == userFromDb.getId()) {
            dao.changePassword(userFromSession, newPassword);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void register(String email, String password, String permission) {
        dao.saveUser(email, password, permission);
    }

    @Override
    public boolean userHasPermission(String[] roles) {
        User currentUser = getCurrentUser();
        System.out.println("inside RAP 'userhaspermission method  "+ currentUser+"  "+roles);
        if (currentUser != null && roles != null) {
            return Arrays.asList(roles).contains(currentUser.getPermission());
        } else {
            return false;
        }
    }
}