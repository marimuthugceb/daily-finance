package com.jbr.dailyfinance.web.rest;

import com.jbr.dailyfinance.api.repository.client.Category;
import com.jbr.dailyfinance.api.repository.server.CategorySecurable;
import com.jbr.dailyfinance.gae.datastore.CategoryServicesImpl;
import com.jbr.dailyfinance.gae.impl.repository.CategoryImpl;
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
@Path("/category/")
public class CategoryResource extends BaseEntityResource<CategorySecurable,
        CategoryServicesImpl> {

    public CategoryResource() {
        super(new CategoryServicesImpl());
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("/makeTest")
    public CategorySecurable makeACategory() {
        final CategorySecurable category = new CategoryImpl();
        category.setName("Mælk");
        category.setType(Category.Type.food);
        put(category);
        return category;
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("/{categoryId}")
    public Category getCategory(@PathParam ("categoryId") Long categoryId) {
        return get(categoryId);
    }

    @DELETE
    @Path("/{id}")
    public void removeById(@PathParam ("id") Long id) {
        delete(get(id));
    }

    @DELETE
    @Consumes({"application/json", "application/xml"})
    public void removeDish(CategorySecurable category) {
        removeById(category.getId());
    }

    @PUT
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public Category edit(CategoryImpl entity) {
        return put(entity);
    }

    @POST
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    public Category add(CategoryImpl entity) {
        //dish.setId(null);
        System.out.println(entity.getName());
        return put(entity);
    }

    @POST
    @Produces({"application/json", "application/xml"})
    @Consumes({"application/json", "application/xml"})
    @Path("/addlist")
    public void addList(List<CategoryImpl> categorys) {
        //dish.setId(null);
        for (CategorySecurable category : categorys) {
            put(category);
        }
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("/list")
    public List<CategoryImpl> getAll() {
        List<CategorySecurable> all = getServiceImpl().list();
        Collections.sort(all, new Comparator<CategorySecurable>() {

            @Override
            public int compare(CategorySecurable o1, CategorySecurable o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return (List)all;
    }


}
