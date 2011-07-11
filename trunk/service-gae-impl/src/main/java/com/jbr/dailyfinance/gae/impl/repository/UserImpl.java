package com.jbr.dailyfinance.gae.impl.repository;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.jbr.dailyfinance.api.repository.server.User;
import java.io.Serializable;

/**
 *
 * @author jbr
 */
public class UserImpl implements User, Serializable {
    final static transient UserService userService = UserServiceFactory.getUserService();
    private String userId;
    private String userName;
    private String email;
    private String authDomain;

    public UserImpl() {
        userId = userService.getCurrentUser().getUserId();
        userName = userService.getCurrentUser().getNickname();
        email = userService.getCurrentUser().getEmail();
        authDomain = userService.getCurrentUser().getAuthDomain();
    }

    UserImpl(com.google.appengine.api.users.User u) {
        userId = u.getUserId();
        userName = u.getNickname();
        email = u.getEmail();
        authDomain = u.getAuthDomain();
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    public String getAuthDomain() {
        return authDomain;
    }

    


}
