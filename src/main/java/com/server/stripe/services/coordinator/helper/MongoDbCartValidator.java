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

import com.server.common.models.stripe.checkout.validation.cart.CartValidationErrorEntityDto;
import com.server.common.types.stripe.checkout.validation.cart.CartValidationErrorType;
import com.server.config.api.exceptions.validation.cart.CartValidationException;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;

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
        errorsArray.add(new CartValidationErrorEntityDto(id, entity.collectionName(), entity.quantity(), 0, CartValidationErrorType.NOT_EXISTING));
      } else if(productsMap.get(id).getStockInfo().stockAmountAvailable - entity.quantity() < 0){
        errorsArray.add(
          new CartValidationErrorEntityDto(id, entity.collectionName(), entity.quantity(), productsMap.get(id).getStockInfo().stockAmountAvailable, CartValidationErrorType.WRONG_QUANTITY)
        );
      }
    });

    if(errorsArray.size() > 0){
      throw new CartValidationException(errorsArray);
    } else {
      return ResponseEntity.ok().body(productsEntities);
    }

  }

}
