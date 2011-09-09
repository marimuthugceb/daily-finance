package com.jbr.dailyfinance.gae.datastore;

import com.google.appengine.api.datastore.Query;
import com.jbr.dailyfinance.api.repository.client.SumCategory;
import com.jbr.dailyfinance.api.repository.server.SumCategorySecurable;
import com.jbr.dailyfinance.api.service.SumCategoryServices;
import com.jbr.dailyfinance.gae.impl.repository.DatastoreEntity;
import com.jbr.dailyfinance.gae.impl.repository.SumCategoryImpl;
import java.util.Date;
import java.util.List;

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
        Query q = new Query(SumCategoryImpl.KIND);
        q.addFilter(SumCategoryImpl.p.sumDate.toString(),
                Query.FilterOperator.GREATER_THAN_OR_EQUAL, startdate);
        q.addFilter(SumCategoryImpl.p.sumDate.toString(),
                Query.FilterOperator.LESS_THAN_OR_EQUAL, enddate);
        List<DatastoreEntity> list = SecuredDatastore.getList(
                clazz, q, 0, Integer.MAX_VALUE);

        return (List)list;
    }

}
