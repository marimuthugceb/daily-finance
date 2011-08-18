package com.jbr.dailyfinance.web.rest;

import com.jbr.dailyfinance.gae.datastore.SummationServicesImpl;
import com.jbr.dailyfinance.gae.impl.repository.SumCategoryTypeImpl;
import java.text.ParseException;
import java.util.List;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author jbr
 */
@Path("/sum/month")
public class SummationResource {

    final private SummationServicesImpl sumService = new SummationServicesImpl();


    @GET
    @Produces({"application/json", "application/xml"})
    @Path("/{month}")
    public List<SumCategoryTypeImpl> getTicket(@PathParam ("month") String month) throws ParseException {
        return sumService.getSumOfMonthByCategoryType(ISODate.dateFormat().parse(month));
    }
    

}
