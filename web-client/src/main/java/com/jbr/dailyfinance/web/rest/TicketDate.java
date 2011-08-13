package com.jbr.dailyfinance.web.rest;

import java.net.URI;
import java.util.Date;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * A fictive representation of tickets grouped by their ticketDate
 * @author jbr
 */
@XmlRootElement(name = "ticketdate")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TicketDate implements Comparable<TicketDate> {
    

    Date ticketDate;

    public TicketDate() {
    }

    public TicketDate(Date ticketDate) {
        this.ticketDate = ticketDate;
    }

    @XmlElement
    public Date getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(Date ticketDate) {
        this.ticketDate = ticketDate;
    }

    @XmlElement
    public String getTickets() {
        URI u = UriBuilder.fromResource(TicketDateResource.class)
                .path("{ticketdate}/ticket").build(ISODate.dateFormat().format(ticketDate));
        return u.toASCIIString();
    }

    @Override
    public int compareTo(TicketDate t) {
        return getTicketDate().compareTo(t.getTicketDate()) * -1;
    }

}
