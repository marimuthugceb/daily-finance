package com.jbr.dailyfinance.gae.datastore;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.jbr.dailyfinance.api.repository.client.Category.Type;
import com.jbr.dailyfinance.api.repository.server.TicketLineSecurable;
import com.jbr.dailyfinance.api.service.TicketLineServices;
import com.jbr.dailyfinance.gae.impl.repository.TicketLineImpl;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jbr
 */
public class TicketLineServicesImpl extends BasicOperationsImpl<TicketLineSecurable>
        implements TicketLineServices {
    final static MemcacheService cache = MemcacheServiceFactory.getMemcacheService();

    public TicketLineServicesImpl() {
        super(TicketLineImpl.class, TicketLineImpl.KIND);
    }

    public Map<Long, TicketLineSecurable> getTicketLinesInCache(long ticketId) {
        Map<Long, TicketLineSecurable> cachelist = (Map) cache.get("ticketlinesForTicketId=" + ticketId);
        return cachelist;
    }

    @Override
    public TicketLineSecurable newEntity() {
        return new TicketLineImpl();
    }

    @Override
    public TicketLineSecurable put(TicketLineSecurable entity) {
        final TicketLineSecurable put = super.put(entity);
        addTicketLineToTicketInCache(entity);
        TicketServicesImpl tsi = new TicketServicesImpl();
        Date ticketDate = tsi.get(entity.getTicketId()).getTicketDate();
        CategoryServicesImpl csi = new CategoryServicesImpl();
        Type type = csi.get(entity.getCategoryId()).getType();
        SummationServicesImpl.addToSumOfMonth(ticketDate, type, entity.getAmount());
        return put;
    }

    public void putTicketLinesInCache(Long ticketId, final List<TicketLineSecurable> list) {
        HashMap<Long, TicketLineSecurable> cachelist = new HashMap<Long, TicketLineSecurable>();
        for (TicketLineSecurable tl : list) {
            cachelist.put(tl.getId(), tl);
        }
        cache.put("ticketlinesForTicketId=" + ticketId, cachelist);
    }
    public void putTicketLinesInCache(Long ticketId, final Map<Long, TicketLineSecurable> map) {
        cache.put("ticketlinesForTicketId=" + ticketId, map);
    }

    @Override
    public TicketLineSecurable putUnsecured(TicketLineSecurable entity) {
        final TicketLineSecurable putUnsecured = super.putUnsecured(entity);
        addTicketLineToTicketInCache(entity);
        return putUnsecured;
    }

    @Override
    public void delete(TicketLineSecurable entity) {
        removeTicketLineFromTicketInCache(entity);
        TicketServicesImpl tsi = new TicketServicesImpl();
        Date ticketDate = tsi.get(entity.getTicketId()).getTicketDate();
        CategoryServicesImpl csi = new CategoryServicesImpl();
        Type type = csi.get(entity.getCategoryId()).getType();
        SummationServicesImpl.addToSumOfMonth(ticketDate, type, entity.getAmount()*-1);
        super.delete(entity);
    }

    private void removeTicketLineFromTicketInCache(TicketLineSecurable entity) {
        Map<Long, TicketLineSecurable> cachemap = (Map) getTicketLinesInCache(entity.getTicketId());
        if (cachemap == null) {
            return;
        }
        System.out.println("Size in cache before remove=" + getTicketLinesInCache(entity.getTicketId()).size());
        System.out.println(String.format("Removing ticketlineid %s from ticketid %s in cache",
                entity.getId(), entity.getTicketId()));
        cachemap.remove(entity.getId());
        putTicketLinesInCache(entity.getTicketId(), cachemap);
        System.out.println("Size in cache after remove=" + getTicketLinesInCache(entity.getTicketId()).size());
    }
    
    protected void addTicketLineToTicketInCache(TicketLineSecurable entity) {
        Map<Long, TicketLineSecurable> cachelist = getTicketLinesInCache(entity.getTicketId());
        if (cachelist == null) {
            cachelist = new HashMap<Long, TicketLineSecurable>();
        }

        System.out.println(String.format("Adding ticketlineid %s to ticketid %s in cache",
                entity.getId(), entity.getTicketId()));
        cachelist.put(entity.getId(), entity);
        putTicketLinesInCache(entity.getTicketId(), cachelist);
        System.out.println("Size in cache now=" + getTicketLinesInCache(entity.getTicketId()).size());
    }

    /**
     * Unsecure list of ticketlines of given ticketid
     * @param ticketId
     * @return
     */
    public List<TicketLineSecurable> listForAllUsers(Long ticketId) {
        if (ticketId == null)
            return super.list();
        Map<Long, TicketLineSecurable> cachemap = getTicketLinesInCache(ticketId);
        if (cachemap==null) {
            System.out.println(String.format("Ticketlines for ticketid %s not found in cache", ticketId));
        } else {
            System.out.println(String.format("%s ticketlines for ticketid %s found in cache!",
                    cachemap.size(), ticketId));
            return new ArrayList(cachemap.values());
        }

        final Query q = new Query(kind);
        q.addFilter(TicketLineImpl.p.ticketId.toString(),
                Query.FilterOperator.EQUAL, ticketId);
        final List list = (List) UnsecuredDatastore.getList(clazz, q, 0, 10000);
        System.out.println(String.format("Putting list with %s of ticketlines for ticketid %s in cache",
                list.size(), ticketId));
        putTicketLinesInCache(ticketId, list);
        return list;
    }

//    public List<TicketLineSecurable> listForCategoryIdAndDate(Long categoryId,
//            Date fromDate, Date toDate) {
//        final Query q = new Query(kind);
//
//    }


}
