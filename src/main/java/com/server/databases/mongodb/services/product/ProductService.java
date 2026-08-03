package com.server.databases.mongodb.services.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.server.databases.mongodb.dto.product.CreateNewProductDto;
import com.server.databases.mongodb.models.product.ProductParentModel;
import com.server.databases.mongodb.models.product.variation.ProductVariationModel;
import com.server.databases.mongodb.models.reviews.ReviewsModel;

@Service
public class ProductService {

  @Value("${databases.mongodb.collections.product.main}")
  private String collection;
  @Value("${databases.mongodb.collections.product.variation}")
  private String variationCollection;
  @Value("${databases.mongodb.collections.media}")
  private String mediaCollection;
  @Value("${databases.mongodb.collections.reviews}")
  private String reviewsCollection;

  @Autowired
  private MongoTemplate mongoTemplate;

  public ProductParentModel createOne(CreateNewProductDto body){
    ProductParentModel dto = new ProductParentModel(body);

    dto.setMediaContent(body.mediaContent());

    ProductParentModel insertedDocument = mongoTemplate.insert(dto, collection);
    
    return insertedDocument;
  }

  public List<ProductParentModel> findManyRecursiveById(List<String> id){
    Query query = Query.query(Criteria.where("id").in(id));

    List<ProductParentModel> docsList = mongoTemplate.find(query, ProductParentModel.class, collection);
    
    List<ProductParentModel> nonNullDocsList = docsList.stream()
    .map(entity -> addEntitiesToOneProductObject(entity)).filter(Objects::nonNull).toList();

    return nonNullDocsList;
  }

  public ResponseEntity<Object> deleteRecursiveById(List<String> ids, String productCollectionName){
    List<ProductParentModel> deletedProducts = mongoTemplate
    .findAllAndRemove(Query.query(Criteria.where("id").in(ids)), ProductParentModel.class, productCollectionName);

    List<ReviewsModel> deletedReviews = mongoTemplate
    .findAllAndRemove(
      Query.query(Criteria.where("parentId").in(ids)), ReviewsModel.class, reviewsCollection + "-" + productCollectionName
    );

    List<ProductVariationModel> deletedProductVariations = mongoTemplate
    .findAllAndRemove(
      Query.query(Criteria.where("parentId").in(ids)), 
      ProductVariationModel.class, 
      variationCollection + "-" + productCollectionName
    );

    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("products", deletedProducts);
    responseBody.put("reviews", deletedReviews);
    responseBody.put("productVariations", deletedProductVariations);

    return ResponseEntity.ok(responseBody);
  }

  private ProductParentModel addEntitiesToOneProductObject(ProductParentModel product){
    Query query = Query.query(Criteria.where("parentId").in(product.getId()));

    List<ProductVariationModel> variationEntities = mongoTemplate
    .find(query, ProductVariationModel.class, variationCollection);

    product.setVariations(variationEntities);
    return product;
  }

}
