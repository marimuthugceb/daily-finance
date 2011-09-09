package com.jbr.dailyfinance.web.rest;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.jbr.dailyfinance.api.repository.client.TicketLine;
import com.jbr.dailyfinance.api.repository.server.TicketLineSecurable;
import com.jbr.dailyfinance.api.service.exceptions.NotFoundException;
import com.jbr.dailyfinance.gae.datastore.TicketLineServicesImpl;
import com.jbr.dailyfinance.gae.impl.repository.TicketLineImpl;
import com.jbr.dailyfinance.web.rest.exceptions.ResourceNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author jbr
 */
@Path("/ticketline/")
public class TicketLineResource extends BaseEntityResource<TicketLineSecurable,
        TicketLineServicesImpl> {

    public TicketLineResource() {
        super(new TicketLineServicesImpl());
    }

    @GET
    @Produces({"application/json", "application/xml"})
    public List<TicketLineImpl> getAll(@PathParam ("ticketId") Long ticketId) {
        List<TicketLineSecurable> all = getServiceImpl().listForAllUsers(ticketId);
        Collections.sort(all, new Comparator<TicketLineSecurable>() {

            @Override
            public int compare(TicketLineSecurable o1, TicketLineSecurable o2) {
                int i = o1.getTicketId().compareTo(o2.getTicketId());
                if (i!=0)
                    return i;
                return o1.getId().compareTo(o2.getId());
            }
        });
        return (List)all;
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("makeTest")
    public TicketLineSecurable makeSome() {
        TicketLineSecurable ticketline = new TicketLineImpl();
        ticketline.setAmount(12.25d);
        ticketline.setNumber(1);
        ticketline.setProductId(1L);
        ticketline.setTicketId(1L);
        put(ticketline);

        ticketline = new TicketLineImpl();
        ticketline.setAmount(12.25d);
        ticketline.setNumber(1);
        ticketline.setProductId(2L);
        ticketline.setTicketId(1L);
        put(ticketline);
        return ticketline;
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("{ticketlineId}")
    public TicketLine getTicketLine(@PathParam ("ticketlineId") Long ticketlineId) {
        try {
            return get(ticketlineId);
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public void removeById(@PathParam ("id") Long id) {
        delete(get(id));
    }

    @DELETE
    @Consumes({"application/json", "application/xml"})
    public void removeDish(TicketLineSecurable ticketline) {
        removeById(ticketline.getId());
    }

    @PUT
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public TicketLine edit(TicketLineImpl entity) {
        return put(entity);
    }

//    @POST
//    @Produces({"application/json", "application/xml"})
//    @Consumes({"application/json", "application/xml"})
//    public TicketLine add(TicketLineImpl entity) {
//        //dish.setId(null);
//        System.out.println("Adding ticketline");
//        return put(entity);
//    }

    @POST
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public List<TicketLineImpl> addList(List<TicketLineImpl> ticketlines) {
        ArrayList<TicketLineImpl> l = new ArrayList<TicketLineImpl>(ticketlines.size());
        for (TicketLineSecurable ticketline : ticketlines) {
            l.add((TicketLineImpl)put(ticketline));
        }
        return l;
    }
}
