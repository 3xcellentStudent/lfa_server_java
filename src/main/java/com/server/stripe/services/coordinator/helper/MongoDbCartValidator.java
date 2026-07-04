package com.server.stripe.services.coordinator.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;
import com.server.stripe.dto.checkout.create.validate.CartValidationErrorEntityDto;

public class MongoDbCartValidator {

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public ResponseEntity<Object> validate(List<CheckoutCreateSessionClientRequestDto> body){
    ArrayList<CartValidationErrorEntityDto> errorsArray = new ArrayList<>();

    List<String> listOfId = body.stream().map(entity -> entity.productId()).toList();

    // matchesFound
    Query query = Query.query(Criteria.where("_id").in(listOfId));
    List<ProductVariationModel> productsEntities = mongoTemplate.find(query, ProductVariationModel.class);

    Map<String, ProductVariationModel> productsMap = productsEntities.stream().collect(Collectors.toMap(ProductVariationModel::getId, product -> product));

    body.forEach(entity -> {
      String id = entity.productId();
      if(!productsMap.containsKey(id)){
        errorsArray.add(new CartValidationErrorEntityDto(id, entity.collectionName(), entity.quantity(), 0));
      } else if(productsMap.get(id).getStockInfo().stockAmountAvailable - entity.quantity() < 0){
        errorsArray.add(new CartValidationErrorEntityDto(id, entity.collectionName(), entity.quantity(), productsMap.get(id).getStockInfo().stockAmountAvailable));
      }
    });

    if(errorsArray.size() > 0){
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorsArray);
    } else {
      return ResponseEntity.ok().body(productsEntities);
    }

  }

}
