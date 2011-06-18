/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jbr.dailyfinance.web.rest;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.jbr.dailyfinance.api.repository.IProduct;
import com.jbr.dailyfinance.gae.datastore.NotAllowedException;
import com.jbr.dailyfinance.gae.impl.repository.Product;
import static com.jbr.dailyfinance.gae.datastore.SecuredDatastore.*;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author jbr
 */
@Path("/product/")
public class ProductResource extends BaseEntityResource<IProduct> {

    public ProductResource() {
        super(IProduct.class);
    }

    private IProduct makeAProduct() {
        final IProduct product = new Product();
        product.setName("Mælk");
        product.setManufacturer("Arla");
        product.setPrice(5.95d);
        
        return product;
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("/{productId}")
    public IProduct getProduct(@PathParam ("productId") Long productId) {
        IProduct product = makeAProduct();
//        try {
//            product = get(Product.class, productId);
//        } catch (EntityNotFoundException ex) {
//            return null;
//        } catch (NotAllowedException ex) {
//            return null;
//        }
        return product;
    }
//
//
//    @DELETE
//    @Consumes({"text/plain"})
//    @Path("/{dishId}")
//    public void removeDishById(@PathParam ("dishId") Long dishId) throws IOException {
//        Dish dish = getDish(dishId);
//        delete(dish);
//    }
//
//    @DELETE
//    @Consumes({"application/json", "application/xml"})
//    @Path("/{dishId}")
//    public void removeDish(Dish dish) throws IOException {
//        removeDishById(dish.getId());
//    }
//
//    @PUT
//    @Produces({"application/json", "application/xml"})
//    @Consumes({"application/json", "application/xml"})
//    @Path("/{dishId}")
//    public Dish editDish(Dish dish) {
//        System.out.println("Dish name in dish is " + dish.getName());
//        return put(dish);
//    }
//
//    @POST
//    @Produces({"application/json", "application/xml"})
//    @Consumes({"application/json", "application/xml"})
//    @Path("/add")
//    public Dish addDish(Dish dish) {
//        //dish.setId(null);
//        System.out.println(dish.getName());
//        return put(dish);
//    }
//
//    @POST
//    @Produces({"application/json", "application/xml"})
//    @Consumes({"application/json", "application/xml"})
//    @Path("/addlist")
//    public void addDishList(List<Dish> dishes) {
//        //dish.setId(null);
//        for (Dish dish : dishes) {
//            System.out.println(dish.getName());
//            put(dish);
//        }
//    }
//
//    @GET
//    @Produces({"application/json", "application/xml"})
//    @Path("/list")
//    public List<Dish> getAllDishes() throws IOException {
//        //final String query = "select from " + Dish.class.getName() + " order by name desc";
//        Query query = new Query("dish");
//        List<Dish> dishes = getList(Dish.class, query, 2000);
//        System.out.println("Number of dishes in list=" + dishes.size());
//        Collections.sort(dishes, new Comparator<Dish>() {
//
//            @Override
//            public int compare(Dish o1, Dish o2) {
//                return o1.getName().compareTo(o2.getName());
//            }
//        });
//        for (Dish dish : dishes)
//            dish.setAddUsagesInPlans(true);
//        return dishes;
//    }
//
//    @GET
//    @Produces("text/plain")
//    @Path("/makeadish")
//    public String doMakeADish() {
//        Dish newdish = put(makeADish());
//        //Greeting greeting = new Greeting(null, "something", new Date());
//        return "Created a new plan with id=" + newdish.getId();
//    }
}
