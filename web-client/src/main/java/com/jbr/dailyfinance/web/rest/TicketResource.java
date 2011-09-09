package com.jbr.dailyfinance.web.rest;

import com.jbr.dailyfinance.api.repository.client.Ticket;
import com.jbr.dailyfinance.api.repository.server.StoreSecurable;
import com.jbr.dailyfinance.api.repository.server.TicketLineSecurable;
import com.jbr.dailyfinance.api.repository.server.TicketSecurable;
import com.jbr.dailyfinance.api.service.StoreServices;
import com.jbr.dailyfinance.api.service.exceptions.NotFoundException;
import com.jbr.dailyfinance.gae.datastore.StoreServicesImpl;
import com.jbr.dailyfinance.gae.datastore.TicketLineServicesImpl;
import com.jbr.dailyfinance.gae.datastore.TicketServicesImpl;
import com.jbr.dailyfinance.gae.impl.repository.StoreImpl;
import com.jbr.dailyfinance.gae.impl.repository.TicketImpl;
import com.jbr.dailyfinance.web.rest.exceptions.ResourceNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
@Path("/ticket/")
public class TicketResource extends BaseEntityResource<TicketSecurable,
        TicketServicesImpl> {

     public TicketResource() {
        super(new TicketServicesImpl());
    }

    @GET
    @Produces({"application/json", "application/xml"})
    public List<TicketImpl> getAll(@PathParam("ticketDate") String ticketDate) throws ParseException {
        List<TicketSecurable> all = getServiceImpl().getTickets(ticketDate==null?null:
                ISODate.dateFormat().parse(ticketDate));
        Collections.sort(all, new Comparator<TicketSecurable>() {

            @Override
            public int compare(TicketSecurable o1, TicketSecurable o2) {
                return o1.getTicketDate().compareTo(o2.getTicketDate());
            }
        });
        return (List)all;
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("/makeTest")
    public TicketImpl makeATicket() {
        new StoreResource().makeSome();
        new CategoryResource().makeACategory();
        new ProductResource().makeSome();
        final TicketImpl ticket = new TicketImpl();
        ticket.setStoreId(1L);
        ticket.setTicketDate(new Date(111, 6, 1));
        put(ticket);
        new TicketLineResource().makeSome();
        return ticket;
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("/{ticketId}")
    public Ticket getTicket(@PathParam ("ticketId") Long ticketId) {
        try {
            return get(ticketId);
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
    
    @Path("/{ticketId}/ticketline")
    public TicketLineResource getTicketLine(@PathParam ("ticketId") Long ticketId) {
        return new TicketLineResource();
    }

    @DELETE
    @Path("/{id}")
    public void removeById(@PathParam ("id") Long id) {
        final TicketSecurable t = get(id);
        final TicketLineServicesImpl tls = new TicketLineServicesImpl();

        for (TicketLineSecurable tl : tls.listForAllUsers(id)) {
            tls.delete(tl);
        }
        delete(t);
    }

    @DELETE
    @Consumes({"application/json", "application/xml"})
    public void removeDish(TicketSecurable ticket) {
        removeById(ticket.getId());
    }

    @PUT
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public Ticket edit(TicketImpl entity) {
        return put(entity);
    }

    @POST
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public List<TicketImpl> addList(List<TicketWithStore> tickets) {
        final ArrayList<TicketImpl> returnList =
                new ArrayList<TicketImpl>(tickets.size());
        for (TicketWithStore ticket : tickets) {
            System.out.println(ticket);
            TicketImpl ticketImpl = new TicketImpl(ticket.getId());
            ticketImpl.setTicketDate(ticket.getTicketDate());
            ticketImpl.setStoreId(ticket.getStoreId());
            if (ticket.getStoreName() != null) {
                StoreServices storeServices = new StoreServicesImpl();
                StoreSecurable store = storeServices.get(ticket.getStoreName());
                if (store == null) {
                    //Create store if store with given name was not allready existing.
                    StoreImpl newStore = new StoreImpl();
                    newStore.setName(ticket.getStoreName());
                    System.out.println("Adding new store " + newStore);
                    store = storeServices.put(newStore);
                }
                ticketImpl.setStoreId(store.getId());
            }
            System.out.println("Putting ticket: " + ticketImpl);
            returnList.add((TicketImpl)put(ticketImpl));

        }
        return returnList;
    }


}
