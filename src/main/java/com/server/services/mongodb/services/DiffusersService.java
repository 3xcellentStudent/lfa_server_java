package com.server.services.mongodb.services;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.services.mongodb.models.products.diffusers.DiffusersProductsModel;
import com.server.services.mongodb.models.reviews.diffusers.DiffusersReviewsModel;
import com.server.services.mongodb.repositories.products.diffusers.DiffusersProductsRepository;
import com.server.services.mongodb.repositories.reviews.diffusers.DiffusersReviewsRepository;

@Service
public class DiffusersService {

  private DiffusersReviewsRepository reviewsRepository;
  private DiffusersProductsRepository productsRepository;
  
  private DiffusersReviewsModel reviewsModel;

  private String newStringId(String string){
    return UUID.fromString(string).toString();
  }

  public ResponseEntity<Object> createOne(DiffusersProductsModel productModel){
    try {
      String _id = newStringId(productModel.title);
      String reviews_id = newStringId(_id);

      productModel.setId(_id);
      productModel.setReviewsId(reviews_id);

      reviewsModel.setParentId(_id);
      reviewsModel.setId(reviews_id);

      DiffusersProductsModel savedProduct = productsRepository.save(productModel);

      DiffusersReviewsModel savedReviews = reviewsRepository.save(reviewsModel);

      JSONObject jsonBody = new JSONObject()
      .put("product", savedProduct)
      .put("reviews", savedReviews);

      return ResponseEntity.ok(jsonBody.toString());
    } catch(IllegalArgumentException error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("internal server error: " + error.getMessage());
    }
  }

  public ResponseEntity<Object> updateOneById(String _id, DiffusersProductsModel productModel){
    try {
      if(productsRepository.existsById(_id)){
        DiffusersProductsModel updatedProduct = productsRepository.save(productModel);

        return ResponseEntity.ok(updatedProduct);
      } else {
        return ResponseEntity.status(404).body("Such an object does not exist");
      }
    } catch(IllegalArgumentException error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("internal server error: " + error.getMessage());
    }
  }

  public ResponseEntity<Object> getById(Iterable<String> _ids){
    try {
      List<DiffusersProductsModel> foundProduct = productsRepository.findAllById(_ids);

      List<String> reviews_ids = foundProduct.stream()
      .map(DiffusersProductsModel::getReviewsId)
      .filter(Objects::nonNull)
      .collect(Collectors.toList());

      List<DiffusersReviewsModel> foundReviews = reviewsRepository.findAllById(reviews_ids);

      JSONObject jsonBody = new JSONObject()
      .put("products", foundProduct)
      .put("reviews", foundReviews);

      return ResponseEntity.ok(jsonBody); 
    } catch(IllegalArgumentException error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("internal server error: " + error.getMessage());
    }
  }

  public ResponseEntity<Object> deleteById(Iterable<String> _ids){
    try {

      List<DiffusersProductsModel> foundProducts = productsRepository.findAllById(_ids)
      .stream().filter(Objects::nonNull).toList();

      List<String> reviews_ids = foundProducts.stream()
      .map(DiffusersProductsModel::getReviewsId)
      .filter(Objects::nonNull)
      .toList();

      List<DiffusersReviewsModel> foundReviews = reviewsRepository.findAllById(reviews_ids);

      reviewsRepository.deleteAllById(reviews_ids);

      productsRepository.deleteAllById(_ids);

      JSONObject jsonBody = new JSONObject()
      .put("products", foundProduct)
      .put("reviews", foundReviews);

      return ResponseEntity.ok();
    } catch(IllegalArgumentException error){
      System.err.println(error.getMessage());
      error.printStackTrace();
      return ResponseEntity.internalServerError().body("Internal server error: " + error.getMessage());
    }
  }

}
