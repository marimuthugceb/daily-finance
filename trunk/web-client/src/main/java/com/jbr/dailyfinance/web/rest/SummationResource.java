package com.jbr.dailyfinance.web.rest;

import com.jbr.dailyfinance.gae.datastore.SumCategoryServicesImpl;
import com.jbr.dailyfinance.gae.datastore.SummationServicesImpl;
import com.jbr.dailyfinance.gae.impl.repository.SumCategoryImpl;
import com.jbr.dailyfinance.gae.impl.repository.SumCategoryTypeImpl;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ws.rs.GET;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author jbr
 */
@Path("/sum")
public class SummationResource {

    final private SummationServicesImpl sumService = new SummationServicesImpl();

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("month/{month}")
    public SumCategoryTypeImpl getTicket(@PathParam ("month") String month) throws ParseException {
        return (SumCategoryTypeImpl) sumService.getSumOfMonthByCategoryType(ISODate.dateFormat().parse(month));
    }

    @GET
    @Produces({"application/json", "application/xml"})
    @Path("month/category/{startdate},{enddate}")
    public List<SumCategoryImpl> getSumCategories(
            @PathParam ("startdate") String startdateRaw,
            @PathParam ("enddate") String enddateRaw) throws ParseException {

        System.out.println("startdateRaw = " + startdateRaw);
        System.out.println("enddateRaw = " + enddateRaw);
        Date startdate = ISODate.dateFormat().parse(startdateRaw);
        Date enddate = ISODate.dateFormat().parse(enddateRaw);

        SumCategoryServicesImpl scserv = new SumCategoryServicesImpl();


        return (List)scserv.list(startdate, enddate);
    }



    @GET
    @Produces("text/plain")
    @Path("update")
    public String updateSumCategories() {
        sumService.updateSumCategories();
        return "Ok. Sums is updated";
    }
}
