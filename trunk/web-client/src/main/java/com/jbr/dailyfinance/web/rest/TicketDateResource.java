package com.jbr.dailyfinance.web.rest;

import com.jbr.dailyfinance.api.repository.server.TicketSecurable;
import com.jbr.dailyfinance.gae.datastore.TicketServicesImpl;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 *
 * @author jbr
 */
@Path("ticketdate")
public class TicketDateResource extends BaseEntityResource<TicketSecurable,
        TicketServicesImpl> {

    public TicketDateResource() {
        super(new TicketServicesImpl());
    }

    @GET
    @Produces({"application/json", "application/xml"})
    public List<TicketDate> getTicketDates(
            @DefaultValue("31") @QueryParam("records") int records,
            @DefaultValue("0") @QueryParam("startrecord") int startRecord) {

        final List<TicketDate> ticketDates = new ArrayList<TicketDate>();
        int recordNo = 0;
        Date previousDate = null;
        for (TicketSecurable t : list(0, 1000000)) {
            if (previousDate == null ||
                    !previousDate.equals(t.getTicketDate())) {
                if (recordNo>=records+startRecord)
                    break;
                if (recordNo>=startRecord)
                    ticketDates.add(new TicketDate(t.getTicketDate()));
                recordNo++;
                previousDate = t.getTicketDate();
            }
        }
        
        return ticketDates;
    }

    @GET
    @Path("/{ticketDate}")
    @Produces({"application/json", "application/xml"})
    public TicketDate getTicketDate(@PathParam("ticketDate") String ticketDate) 
            throws ParseException {
        return new TicketDate(ISODate.dateFormat().parse(ticketDate));
    }

    @Path("/{ticketDate}/ticket")
    public TicketResource getTickets() {
        return new TicketResource();
    }


}
