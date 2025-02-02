package com.server.services.mongodb.helpers.queries;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Field;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class QueriesHelper {

  public static Query getId(String objectName, List<String> id){
    return new Query(Criteria.where(objectName).in(id));
  }

  public static Query getId(String objectName, String id){
    return new Query(Criteria.where(objectName).in(id));
  }

  public static Field getSliceOfReviewsList(String objectName, String id, int fromIndex, int toIndex){
    int limit = toIndex - fromIndex;

    return new Query(Criteria.where(objectName).is(id)).fields().slice("reviewsList", fromIndex, limit);
  }

  public static Update getUpdateForObject(List<String> fields, List<Object> newData){
    Update updates = new Update();

    int index = 0;
    for(String field : fields){
      updates.set(field, newData.get(index));
      index++;
    }

    return updates;
  }

}
