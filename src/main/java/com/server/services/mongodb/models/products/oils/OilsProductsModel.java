package com.server.services.mongodb.models.products.oils;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.server.services.mongodb.models.products.ProductsModel.Category;
import com.server.services.mongodb.models.products.ProductsModel.Descriptions;
import com.server.services.mongodb.models.products.ProductsModel.MediaContent;
import com.server.services.mongodb.models.products.ProductsModel.ProductOption;
import com.server.services.mongodb.models.products.ProductsModel.Specifications;
import com.server.services.mongodb.models.products.ProductsModel.StockInfo;
import com.server.services.mongodb.models.reviews.ReviewsModel.Reviews;

import java.util.List;

@Document(collection = "oils")
public class OilsProductsModel {

  @Id
  public String id;
  public double rating;
  public Reviews reviews;
  public Category category;
  public String title;
  public Descriptions descriptions;
  public StockInfo stockInfo;
  public List<ProductOption> productOptions;
  public MediaContent mediaContent;
  public Specifications specifications;

  public OilsProductsModel(){}
  
  public OilsProductsModel(OilsProductsModel product){
    this.rating = product.rating;
    this.reviews = product.reviews;
    this.category = product.category;
    this.title = product.title;
    this.descriptions = product.descriptions;
    this.stockInfo = product.stockInfo;
    this.productOptions = product.productOptions;
    this.mediaContent = product.mediaContent;
    this.specifications = product.specifications;
  }

  public String setId(String id){
    this.id = id;
    return id;
  }
}
