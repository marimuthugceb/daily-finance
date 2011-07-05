/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance.api.repository.server;

/**
 *
 * @author jbr
 */
public interface IUser {
    String getUserId();
    String getUserName();
    String getEmail();
    String getAuthDomain();
}
