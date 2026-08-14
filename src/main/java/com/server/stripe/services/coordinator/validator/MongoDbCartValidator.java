package com.server.stripe.services.coordinator.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.server.common.api.exceptions.validation.cart.CartValidationException;
import com.server.common.models.stripe.checkout.validation.cart.CartValidationErrorEntityDto;
import com.server.common.types.stripe.checkout.validation.cart.CartValidationErrorType;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;

@Service
public class MongoDbCartValidator {

  @Value("${databases.mongodb.collections.products.variations}")
  private String collection;

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public List<ProductVariationModel> validator(List<CheckoutCreateSessionClientRequestDto> cart){
    ArrayList<CartValidationErrorEntityDto> errorsArray = new ArrayList<>();

    List<String> ids = cart.stream().map(entity -> entity.productId()).toList();

    Query query = Query.query(Criteria.where("_id").in(ids));

    List<ProductVariationModel> foundProducts = mongoTemplate.find(query, ProductVariationModel.class, collection);

    Map<String, ProductVariationModel> mapProducts = foundProducts.stream()
    .collect(Collectors.toMap(product -> product.getId(), product -> product));

    cartValidation(cart, mapProducts, errorsArray);

    if(errorsArray.size() > 0){
      throw new CartValidationException(errorsArray);
    } else {
      return foundProducts;
    }
  }

  private void cartValidation(
    List<CheckoutCreateSessionClientRequestDto> cart, 
    Map<String, ProductVariationModel> mapProducts, 
    ArrayList<CartValidationErrorEntityDto> errorsArray
  ){
    cart.forEach(entity -> {
      String id = entity.productId();
      System.out.println(id + " " + mapProducts.containsKey(id));
      if(!mapProducts.containsKey(id)){
        errorsArray.add(new CartValidationErrorEntityDto(
          id, 
          entity.quantity(), 
          0, 
          CartValidationErrorType.NOT_EXISTING
        ));
      } else if(mapProducts.get(id).getStockInfo().getStockAmountAvailable() <= 0){
        errorsArray.add(new CartValidationErrorEntityDto(
          id, 
          entity.quantity(), 
          mapProducts.get(id).getStockInfo().getStockAmountAvailable(), 
          CartValidationErrorType.OUT_OF_STOCK
        ));
      } else if(mapProducts.get(id).getStockInfo().getStockAmountAvailable() - entity.quantity() < 0){
        errorsArray.add(new CartValidationErrorEntityDto(
          id, 
          entity.quantity(), 
          mapProducts.get(id).getStockInfo().getStockAmountAvailable(), 
          CartValidationErrorType.WRONG_QUANTITY
        ));
      }
    });
  }

}
