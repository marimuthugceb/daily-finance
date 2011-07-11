/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance.web.rest;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author jbr
 */
@Path("/auth/")
public class AuthResource {
    final UserService userService = UserServiceFactory.getUserService();


    @GET
    @Produces({"application/json", "application/xml"})
    @Path("user")
    public String getUser() {

        User currentUser = userService.getCurrentUser();
        System.out.println("Get user says: " + currentUser);
        System.out.println("Userlogin url:" + userService.createLoginURL("/plan.html"));
        if (currentUser == null)
            return null;

        return currentUser.getEmail();
    }
    @GET
    @Produces({"application/json", "application/xml"})
    @Path("loginUrl")
    public String getLoginUrl() {
        return userService.createLoginURL("/main.jsp");
    }
    @GET
    @Produces({"application/json", "application/xml"})
    @Path("logoutUrl")
    public String getLogoutUrl() {
        return userService.createLogoutURL("/out.html");
    }
}
