package com.jbr.dailyfinance.web.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author jbr
 */
public class ResourceNotFoundException extends WebApplicationException {
    public ResourceNotFoundException(String message) {
        super(Response.status(Response.Status.NOT_FOUND).entity(
                "404 NOT FOUND " + message).type(MediaType.TEXT_PLAIN).build());
    }
}
