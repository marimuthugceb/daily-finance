package com.jbr.dailyfinance.gae.datastore;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Query;
import com.jbr.dailyfinance.api.repository.client.SumCategory;
import com.jbr.dailyfinance.api.repository.server.CategorySecurable;
import com.jbr.dailyfinance.api.repository.server.SumCategorySecurable;
import com.jbr.dailyfinance.api.service.CategoryServices;
import com.jbr.dailyfinance.api.service.SumCategoryServices;
import com.jbr.dailyfinance.gae.impl.repository.DatastoreEntity;
import com.jbr.dailyfinance.gae.impl.repository.SumCategoryImpl;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jbr
 */
public class SumCategoryServicesImpl extends BasicOperationsImpl<SumCategorySecurable>
        implements SumCategoryServices {

    final static UnsecuredDatastore datastore = new UnsecuredDatastore();

    public SumCategoryServicesImpl() {
        super(SumCategoryImpl.class, SumCategoryImpl.KIND);
    }

    @Override
    public SumCategorySecurable newEntity() {
        return new SumCategoryImpl();
    }

    /**
     * Unsecured get of SumCategory by sumDate and categoryId
     * @param sumDate
     * @param categoryId
     * @return
     */
    @Override
    public SumCategory get(Date sumDate, long categoryId) {
        Query q = new Query(SumCategoryImpl.KIND);
        q.addFilter(SumCategoryImpl.p.sumDate.toString(),
                Query.FilterOperator.EQUAL, sumDate);
        q.addFilter(SumCategoryImpl.p.categoryId.toString(),
                Query.FilterOperator.EQUAL, categoryId);
        List<DatastoreEntity> list = UnsecuredDatastore.getList(
                clazz, q, 0, 1);

        if (list.isEmpty())
            return null;
        return (SumCategory) list.get(0);
    }

    /**
     * Delete all without any user checking
     */
    public void deleteAll() {
        for (SumCategorySecurable sc : listUnsecured())
            UnsecuredDatastore.delete((DatastoreEntity) sc);
    }

    public List<SumCategorySecurable> list(Date startdate, Date enddate) {
        System.out.println(String.format("Getting SumCategories between %s and %s",
                startdate, enddate));
        ArrayList<SumCategorySecurable> returnList = new ArrayList<SumCategorySecurable>();
        Calendar c = new GregorianCalendar();
        c.setTime(startdate);
        final CategoryServices cc = new CategoryServicesImpl();
        final List<CategorySecurable> categories = cc.list();

        while (c.getTime().before(enddate)) {
            c.add(Calendar.MONTH, 1);
            final Date currentdate = c.getTime();
            for (CategorySecurable category : categories) {
                System.out.println(String.format(
                        "Now getting sums for category %s, at %s",
                        category.getName(), currentdate));
                SumCategoryImpl sumCategory = SummationServicesImpl
                        .getSumOfMonthByCategoryFromCache(currentdate, category.getId());
                // If nothing found in cache then try get from datastore
                if (sumCategory != null) {
                    System.out.println("Found in cache!");
                } else {
                    System.out.println("Not found in cache, now checking datastore");
                    try {
                        final String cacheKey = SummationServicesImpl.getCacheKey(
                                currentdate, category.getId());
                        sumCategory = SecuredDatastore.get(clazz, kind, cacheKey);
                        System.out.println(String.format("Got %s from datastore on key %s",
                                sumCategory, cacheKey));
                        SummationServicesImpl.putSumOfMonthByCategoryInCache(
                                currentdate, sumCategory);
                    } catch (EntityNotFoundException ex) {
                        System.out.println("SumCategory '" + category.getName() + "' not found in datastore");
                        continue;
                    } catch (NotAllowedException ex) {
                        System.err.println(ex);
                        continue;
                    }
                }
                returnList.add(sumCategory);
            }
        }


        return returnList;
//        Query q = new Query(SumCategoryImpl.KIND);
//        q.addFilter(SumCategoryImpl.p.sumDate.toString(),
//                Query.FilterOperator.GREATER_THAN_OR_EQUAL, startdate);
//        q.addFilter(SumCategoryImpl.p.sumDate.toString(),
//                Query.FilterOperator.LESS_THAN_OR_EQUAL, enddate);
//        List<DatastoreEntity> list = SecuredDatastore.getList(
//                clazz, q, 0, Integer.MAX_VALUE);
//
//        return (List)list;
    }

}
