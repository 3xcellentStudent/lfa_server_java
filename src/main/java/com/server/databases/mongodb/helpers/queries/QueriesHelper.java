package com.server.databases.mongodb.helpers.queries;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class QueriesHelper {

  public static <T> Query getId(String selector, List<T> id){
    return new Query(Criteria.where(selector).in(id));
  }

  public static Query getId(String selector, String id){
    return new Query(Criteria.where(selector).is(id));
  }

  public static Query getSliceOfReviewsList(String searchIdentifier, String id, int fromIndex, int toIndex){
    Query query = new Query(Criteria.where(searchIdentifier).is(id));

    query.fields().slice("reviewsList", fromIndex, toIndex);

    return query;
  }

  public static Update getUpdateForNonArray(String field, Object newData){
    Update update = new Update();

    update.set(field, newData);

    return update;
  }

  public static Update getUpdateForArray(String field, Object newData){
    Update update = new Update();

    update.push(field).each(newData);

    return update;
  }

  public static Update doUnsetForArray(List<Integer> indexes, String selector){
    Update update = new Update();

    for (Integer index : indexes) {
      update.unset(selector + "." + index);
    }

    return update;
  }

}
