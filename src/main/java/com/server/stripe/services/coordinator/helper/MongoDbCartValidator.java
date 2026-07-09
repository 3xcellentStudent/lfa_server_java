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
  
  public List<ProductVariationModel> validate(List<CheckoutCreateSessionClientRequestDto> cart){
    ArrayList<CartValidationErrorEntityDto> errorsArray = new ArrayList<>();

    List<String> listOfId = cart.stream().map(entity -> entity.productId()).toList();

    // matchesFound
    Query query = Query.query(Criteria.where("id").in(listOfId));
    List<ProductVariationModel> productVariations = mongoTemplate.find(query, ProductVariationModel.class, collectiuonName);

    Map<String, ProductVariationModel> productsMap = productVariations.stream().collect(Collectors.toMap(ProductVariationModel::getId, product -> product));

    cart.forEach(entity -> {
      String id = entity.productId();
      System.out.println(id + " " + productsMap.containsKey(id));
      if(!productsMap.containsKey(id)){
        errorsArray.add(new CartValidationErrorEntityDto(id, entity.collectionName(), entity.quantity(), 0, CartValidationErrorType.NOT_EXISTING));
      } else if(productsMap.get(id).getStockInfo().getStockAmountAvailable() - entity.quantity() < 0){
        errorsArray.add(
          new CartValidationErrorEntityDto(id, entity.collectionName(), entity.quantity(), productsMap.get(id).getStockInfo().getStockAmountAvailable(), CartValidationErrorType.WRONG_QUANTITY)
        );
      }
    });

    if(errorsArray.size() > 0){
      throw new CartValidationException(errorsArray);
    } else {
      return productVariations;
    }

  }

}
