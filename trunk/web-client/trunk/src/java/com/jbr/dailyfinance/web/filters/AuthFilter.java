package com.jbr.dailyfinance.web.filters;

import com.jbr.dailyfinance.web.rest.AuthResource;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jbr
 */
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        final UserService userService = UserServiceFactory.getUserService();
        //System.out.println("User in filter is: " + userService.getCurrentUser());
        if (!userService.isUserLoggedIn()) {
            //response.getWriter().print("NO ACCESS");
            final AuthResource auth = new AuthResource();
            System.out.println("User not logged in. Redirecting to " + auth.getLoginUrl());
            ((HttpServletResponse)response).sendRedirect(auth.getLoginUrl());
            response.flushBuffer();
        }
        else
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
