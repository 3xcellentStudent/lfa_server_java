package com.server.stripe.services.coordinator.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.server.common.api.exceptions.validation.cart.CartValidationException;
import com.server.common.models.stripe.checkout.validation.cart.CartValidationErrorEntityDto;
import com.server.common.types.stripe.checkout.validation.cart.CartValidationErrorType;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.stripe.dto.checkout.create.client.CheckoutCreateSessionClientRequestDto;

@Service
public class MongoDbCartValidator {

  @Value("${databases.mongodb.collections.products.variations}")
  private String variationsCollection;

  @Autowired
  private MongoTemplate mongoTemplate;

  // public List<ProductVariationModel> validator(List<CheckoutCreateSessionClientRequestDto> cart){
  //   ArrayList<CartValidationErrorEntityDto> errorsArray = new ArrayList<>();

  //   List<String> ids = cart.stream().map(entity -> entity.id()).toList();

  //   Query query = Query.query(Criteria.where("_id").in(ids));

  //   List<ProductVariationModel> foundProducts = mongoTemplate.find(query, ProductVariationModel.class, collection);

  //   Map<String, ProductVariationModel> mapProducts = foundProducts.stream()
  //   .collect(Collectors.toMap(product -> product.getId(), product -> product));

  //   cartValidation(cart, mapProducts, errorsArray);

  //   if(errorsArray.size() > 0){
  //     throw new CartValidationException(errorsArray);
  //   } else {
  //     return foundProducts;
  //   }
  // }

  public List<ProductVariationModel> validator(List<CheckoutCreateSessionClientRequestDto> cart){
    ArrayList<CartValidationErrorEntityDto> errorsArray = new ArrayList<>();

    List<ProductVariationModel> correctProducts = cart.stream().map((item) -> {
      Query query = Query.query(Criteria.where("_id").is(item.id()).and("stockInfo.amountAvailable").gte(item.quantity()));

      Update update = new Update();
      update.inc("stockInfo.amountAvailable", -item.quantity());
      update.inc("stockInfo.amountReserved", item.quantity());

      FindAndModifyOptions options = FindAndModifyOptions.options().returnNew(true);

      ProductVariationModel modifiedDoc = mongoTemplate.findAndModify(query, update, options, ProductVariationModel.class, variationsCollection);

      if(modifiedDoc == null){
        ProductVariationModel actualDoc = mongoTemplate.findById(item.id(), ProductVariationModel.class, variationsCollection);

        if(actualDoc == null){
          errorsArray.add(new CartValidationErrorEntityDto(item.id(), item.quantity(), 0, CartValidationErrorType.NOT_EXISTING));
        } else if(actualDoc.getStockInfo().getAmountAvailable() == 0){
          errorsArray.add(new CartValidationErrorEntityDto(item.id(), item.quantity(), actualDoc.getStockInfo().getAmountAvailable(), CartValidationErrorType.OUT_OF_STOCK));
        } else {
          errorsArray.add(new CartValidationErrorEntityDto(item.id(), item.quantity(), actualDoc.getStockInfo().getAmountAvailable(), CartValidationErrorType.WRONG_QUANTITY));
        }

        return null;
      }

      return modifiedDoc;
    }).filter(Objects::nonNull).toList();


    if(!errorsArray.isEmpty()){
      throw new CartValidationException(errorsArray);
    } else {
      return correctProducts;
    }
  }

  // private void cartValidation(
  //   List<CheckoutCreateSessionClientRequestDto> cart, 
  //   Map<String, ProductVariationModel> mapProducts, 
  //   ArrayList<CartValidationErrorEntityDto> errorsArray
  // ){
  //   cart.forEach(entity -> {
  //     String id = entity.id();
  //     System.out.println(id + " " + mapProducts.containsKey(id));
  //     if(!mapProducts.containsKey(id)){
  //       errorsArray.add(new CartValidationErrorEntityDto(
  //         id, 
  //         entity.quantity(), 
  //         0, 
  //         CartValidationErrorType.NOT_EXISTING
  //       ));
  //     } else if(mapProducts.get(id).getStockInfo().getStockAmountAvailable() <= 0){
  //       errorsArray.add(new CartValidationErrorEntityDto(
  //         id, 
  //         entity.quantity(), 
  //         mapProducts.get(id).getStockInfo().getStockAmountAvailable(), 
  //         CartValidationErrorType.OUT_OF_STOCK
  //       ));
  //     } else if(mapProducts.get(id).getStockInfo().getStockAmountAvailable() - entity.quantity() < 0){
  //       errorsArray.add(new CartValidationErrorEntityDto(
  //         id, 
  //         entity.quantity(), 
  //         mapProducts.get(id).getStockInfo().getStockAmountAvailable(), 
  //         CartValidationErrorType.WRONG_QUANTITY
  //       ));
  //     }
  //   });
  // }

}
