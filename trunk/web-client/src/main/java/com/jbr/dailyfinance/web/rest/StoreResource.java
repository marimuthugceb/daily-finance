package com.jbr.dailyfinance.web.rest;

import com.jbr.dailyfinance.api.repository.client.Store;
import com.jbr.dailyfinance.api.repository.server.StoreSecurable;
import com.jbr.dailyfinance.api.service.exceptions.NotFoundException;
import com.jbr.dailyfinance.gae.datastore.StoreServicesImpl;
import com.jbr.dailyfinance.gae.impl.repository.StoreImpl;
import com.jbr.dailyfinance.web.rest.exceptions.ResourceNotFoundException;
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
@Path("/store/")
public class StoreResource extends BaseEntityResource<StoreSecurable,
        StoreServicesImpl> {

    public StoreResource() {
        super(new StoreServicesImpl());
    }

    @GET
    @Produces({"application/json", "application/xml"})
    public List<StoreImpl> getAll() {
        List<StoreSecurable> all = getServiceImpl().list();
        Collections.sort(all, new Comparator<StoreSecurable>() {

            @Override
            public int compare(StoreSecurable o1, StoreSecurable o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return (List)all;
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("/makeTest")
    public List<StoreImpl> makeSome() {
        put(new StoreImpl().setNameAndReturn("Bilka Næstved"));
        put(new StoreImpl().setNameAndReturn("Rema 1000"));
        put(new StoreImpl().setNameAndReturn("Føtex"));
        put(new StoreImpl().setNameAndReturn("T. Hansen"));
        
        return getAll();
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("/{storeId}")
    public Store getStore(@PathParam ("storeId") Long storeId) {
        try {
            return get(storeId);
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @DELETE
    @Path("/{id}")
    public void removeById(@PathParam ("id") Long id) {
        try {
            delete(get(id));
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @DELETE
    @Consumes({"application/json", "application/xml"})
    public void removeDish(StoreSecurable store) {
        try {
            removeById(store.getId());
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @PUT
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public Store edit(StoreImpl entity) {
        return put(entity);
    }

    @POST
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public Store add(StoreImpl entity) {
        //dish.setId(null);
        System.out.println(entity.getName());
        return put(entity);
    }

    @POST
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    @Path("/addlist")
    public void addList(List<StoreImpl> stores) {
        //dish.setId(null);
        for (StoreSecurable store : stores) {
            put(store);
        }
    }



}
