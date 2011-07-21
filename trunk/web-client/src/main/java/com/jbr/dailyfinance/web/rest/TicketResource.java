package com.jbr.dailyfinance.web.rest;

import com.jbr.dailyfinance.api.repository.client.Ticket;
import com.jbr.dailyfinance.api.repository.server.StoreSecurable;
import com.jbr.dailyfinance.api.repository.server.TicketSecurable;
import com.jbr.dailyfinance.api.service.StoreServices;
import com.jbr.dailyfinance.gae.datastore.StoreServicesImpl;
import com.jbr.dailyfinance.gae.datastore.TicketServicesImpl;
import com.jbr.dailyfinance.gae.impl.repository.StoreImpl;
import com.jbr.dailyfinance.gae.impl.repository.TicketImpl;
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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

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
    public List<TicketImpl> getAll() {
        List<TicketSecurable> all = getServiceImpl().list();
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
        return get(ticketId);
    }

    @DELETE
    @Path("/{id}")
    public void removeById(@PathParam ("id") Long id) {
        delete(get(id));
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
    public Ticket add(TicketImpl entity) {
        //dish.setId(null);
        System.out.println(String.format("Adding new ticket for store %s at %s",
                getStoreOfId(entity.getStoreId()),
                entity.getTicketDate()));
        return put(entity);
    }

    private StoreSecurable getStoreOfId(Long id) {
        return new StoreServicesImpl().get(id);
    }

    @POST
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    @Path("/addlist")
    public void addList(List<TicketWithStore> tickets) {
        //dish.setId(null);
        for (TicketWithStore ticket : tickets) {
            TicketImpl ticketImpl = new TicketImpl(ticket.getId());
            ticketImpl.setTicketDate(ticket.getTicketDate());
            if (ticket.getStoreName() != null) {
                StoreServices storeServices = new StoreServicesImpl();
                StoreSecurable store = storeServices.get(ticket.getStoreName());
                if (store == null) {
                    //Create store if store with given name was not allready existing.
                    StoreImpl newStore = new StoreImpl();
                    newStore.setName(ticket.getStoreName());
                    store = storeServices.put(newStore);
                }
                ticketImpl.setStoreId(store.getId());
            }
            put(ticketImpl);
        }
    }

    @XmlRootElement(name = "store")
    @XmlAccessorType(XmlAccessType.FIELD)
    public class TicketWithStore {

        String storeName;
        Long   id;
        Date   ticketDate;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getStoreName() {
            return storeName;
        }

        public void setStoreName(String storeName) {
            this.storeName = storeName;
        }

        public Date getTicketDate() {
            return ticketDate;
        }

        public void setTicketDate(Date ticketDate) {
            this.ticketDate = ticketDate;
        }


    }


}
