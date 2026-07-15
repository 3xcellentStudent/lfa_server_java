package com.server.stripe.services.coordinator.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public List<ProductVariationModel> validator(List<CheckoutCreateSessionClientRequestDto> cart){
    ArrayList<CartValidationErrorEntityDto> errorsArray = new ArrayList<>();
    ArrayList<ProductVariationModel> allFoundProductsArray = new ArrayList<>();

    Map<String, List<CheckoutCreateSessionClientRequestDto>> cartMapByCollectionName = cart.stream()
    .collect(Collectors.groupingBy(entity -> entity.collectionName()));

    cartMapByCollectionName.forEach((collectionName, items) -> {
      List<String> ids = items.stream().map(entity -> entity.productId()).toList();

      Query query = Query.query(Criteria.where("_id").in(ids));

      List<ProductVariationModel> foundProducts = mongoTemplate.find(query, ProductVariationModel.class, collectionName);

      allFoundProductsArray.addAll(foundProducts);
    });
    
    Map<String, ProductVariationModel> allFoundProductsMap = allFoundProductsArray.stream()
    .collect(Collectors.toMap(ProductVariationModel::getId, product -> product));

    cartValidation(cart, allFoundProductsMap, errorsArray);

    if(errorsArray.size() > 0){
      throw new CartValidationException(errorsArray);
    } else {
      return allFoundProductsArray;
    }
  }

  private void cartValidation(
    List<CheckoutCreateSessionClientRequestDto> cart, 
    Map<String, ProductVariationModel> allFoundProductsMap, 
    ArrayList<CartValidationErrorEntityDto> errorsArray
  ){
    cart.forEach(entity -> {
      String id = entity.productId();
      System.out.println(id + " " + allFoundProductsMap.containsKey(id));
      if(!allFoundProductsMap.containsKey(id)){
        errorsArray.add(new CartValidationErrorEntityDto(
          id, 
          entity.collectionName(), 
          entity.quantity(), 
          0, 
          CartValidationErrorType.NOT_EXISTING
        ));
      } else if(allFoundProductsMap.get(id).getStockInfo().getStockAmountAvailable() <= 0){
        errorsArray.add(new CartValidationErrorEntityDto(
          id, 
          entity.collectionName(), 
          entity.quantity(), 
          allFoundProductsMap.get(id).getStockInfo().getStockAmountAvailable(), 
          CartValidationErrorType.OUT_OF_STOCK
        ));
      } else if(allFoundProductsMap.get(id).getStockInfo().getStockAmountAvailable() - entity.quantity() < 0){
        errorsArray.add(new CartValidationErrorEntityDto(
          id, 
          entity.collectionName(), 
          entity.quantity(), 
          allFoundProductsMap.get(id).getStockInfo().getStockAmountAvailable(), 
          CartValidationErrorType.WRONG_QUANTITY
        ));
      }
    });
  }

}
