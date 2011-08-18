package com.jbr.dailyfinance.web.rest;

import com.jbr.dailyfinance.api.repository.client.Product;
import com.jbr.dailyfinance.api.repository.server.ProductSecurable;
import com.jbr.dailyfinance.api.service.exceptions.NotFoundException;
import com.jbr.dailyfinance.gae.datastore.ProductServicesImpl;
import com.jbr.dailyfinance.gae.impl.repository.ProductImpl;
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
@Path("/product/")
public class ProductResource extends BaseEntityResource<ProductSecurable,
        ProductServicesImpl> {

    public ProductResource() {
        super(new ProductServicesImpl());
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("makeTest")
    public List<ProductImpl> makeSome() {
        ProductImpl product = new ProductImpl();
        product.setName("Mælk");
        product.setManufacturer("Arla");
        product.setPrice(5.95d);
        product.setCategoryId(1L);
        put(product);
        product = new ProductImpl();
        product.setName("Yoghurt");
        product.setManufacturer("Arla");
        product.setPrice(5.95d);
        product.setCategoryId(1L);
        put(product);
        return getAll();
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("{productId}")
    public Product getProduct(@PathParam ("productId") Long productId) {
        try {
            return get(productId);
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    public void removeById(@PathParam ("id") Long id) {
        try {
            delete(get(id));
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @DELETE
    @Consumes({"application/json", "application/xml"})
    public void removeDish(ProductSecurable product) {
        removeById(product.getId());
    }

    @PUT
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public Product edit(ProductImpl entity) {
        return put(entity);
    }

    @POST
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public Product add(ProductImpl entity) {
        //dish.setId(null);
        System.out.println(entity.getName());
        return put(entity);
    }

    @POST
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    @Path("addlist")
    public void addList(List<ProductImpl> products) {
        //dish.setId(null);
        for (ProductSecurable product : products) {
            put(product);
        }
    }

    @GET
    @Produces({"application/json", "application/xml"})
    public List<ProductImpl> getAll() {
        List<ProductSecurable> all = getServiceImpl().list();
        Collections.sort(all, new Comparator<ProductSecurable>() {

            @Override
            public int compare(ProductSecurable o1, ProductSecurable o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return (List)all;
    }


}
