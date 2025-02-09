package com.server.services.mongodb.helpers.queries;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class QueriesHelper {

  public static Query getId(String objectName, List<String> id){
    return new Query(Criteria.where(objectName).in(id));
  }

  public static Query getId(String objectName, String id){
    return new Query(Criteria.where(objectName).is(id));
  }

  public static Query getSliceOfReviewsList(String searchIdentifier, String id, int fromIndex, int toIndex){
    Query query = new Query(Criteria.where(searchIdentifier).is(id));

    query.fields().slice("reviewsList", fromIndex, toIndex);

    return query;
  }

  public static Update getUpdateForNonArray(String field, Object newData){
    Update updates = new Update();

    updates.set(field, newData);

    return updates;
  }

  public static Update getUpdateForArray(String field, Object newData){
    Update updates = new Update();

    updates.push(field).each(newData);

    return updates;
  }

}
