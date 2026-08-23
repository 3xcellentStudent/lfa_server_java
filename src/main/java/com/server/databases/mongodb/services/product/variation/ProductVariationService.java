package com.server.databases.mongodb.services.product.variation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.bulk.BulkWriteResult;
import com.server.databases.mongodb.dto.product.variation.CreateVariationByParentId;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;

@Service
public class ProductVariationService {

  private Logger logger = LoggerFactory.getLogger(ProductVariationService.class);

  @Value("${databases.mongodb.collections.products.variations}")
  private String variationsCollection;
  @Value("${databases.mongodb.collections.products.main}")
  private String parentCollection;

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public ProductVariationModel createByParentId(CreateVariationByParentId body){
    if(!mongoTemplate.exists(Query.query(Criteria.where("_id").is(body.parentId())), parentCollection)){
      return null;
    }

    ProductVariationModel model = new ProductVariationModel(body);

    ProductVariationModel insertedDocument = mongoTemplate.insert(model, variationsCollection);
    
    return insertedDocument;
  }

  public Integer bulkOpsInventoryUpdate(List<CheckoutCreateSessionClientRequestDto> cart){
    BulkOperations bulkOps = mongoTemplate.bulkOps(BulkMode.UNORDERED, ProductVariationModel.class, variationsCollection);
    
    cart.forEach(entity -> {
      Query query = Query.query(Criteria.where("_id").is(entity.id()));

      Update update = new Update();
      update.inc("stockInfo.stockAmountAvailable", -entity.quantity());
      update.inc("stockInfo.stockAmountReserved", entity.quantity());

      bulkOps.updateOne(query, update);
    });

    BulkWriteResult result = bulkOps.execute();
    logger.info("The result of collection " + variationsCollection + ": " + result);
    
    return result.getModifiedCount();
  }

  public List<ProductVariationModel> deteleManyById(List<String> ids){
    Query deletedVariationsQuery = Query.query(Criteria.where("_id").in(ids));

    List<ProductVariationModel> deletedVariationsEntities = mongoTemplate
    .findAllAndRemove(deletedVariationsQuery, ProductVariationModel.class, variationsCollection);

    return deletedVariationsEntities;
  }

}
