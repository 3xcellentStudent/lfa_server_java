package com.server.services.mongodb.models.products.diffusers;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.server.services.mongodb.models.media.diffusers.DiffusersMediaModel;
import com.server.services.mongodb.models.products.ProductsModel.Descriptions;
import com.server.services.mongodb.models.products.ProductsModel.ProductOption;
import com.server.services.mongodb.models.products.ProductsModel.Specifications;
import com.server.services.mongodb.models.products.ProductsModel.StockInfo;
import com.server.services.mongodb.models.products.ProductsModel.Theme;
import com.server.services.mongodb.models.reviews.diffusers.DiffusersReviewsModel;

import java.util.List;

@Component
@Document(collection = "products_diffusers")
public class DiffusersProductsModel {

  @Id
  private String id;
  private String reviewsId;
  private String mediaId;
  public String rating;
  public String title;
  public Descriptions descriptions;
  public StockInfo stockInfo;
  public List<ProductOption> productOptions;
  public Specifications specifications;
  public Theme theme;
  private DiffusersMediaModel mediaContent;
  private DiffusersReviewsModel reviews;
  private long createdAt;
  private long updatedAt;

  public DiffusersProductsModel(){}

  public DiffusersProductsModel(DiffusersProductsModel requestBody){
    this.rating = requestBody.rating;
    this.title = requestBody.title;
    this.descriptions = requestBody.descriptions;
    this.stockInfo = requestBody.stockInfo;
    this.theme = requestBody.theme;
    this.productOptions = requestBody.productOptions;
    this.specifications = requestBody.specifications;
  }

  public void setId(String id){
    this.id = id;
  }

  public String getId(){
    return this.id;
  }

  public void setReviewsId(String reviewsId){
    this.reviewsId = reviewsId;
  }

  public String getReviewsId(){
    return this.reviewsId;
  }

  public void setMediaId(String mediaId){
    this.mediaId = mediaId;
  }

  public String getMediaId(){
    return this.mediaId;
  }

  public long getCreateTime(){
    return this.createdAt;
  }

  public void setCreateTime(long newTime){
    this.createdAt = newTime;
  }

  public long getUpdateTime(){
    return this.updatedAt;
  }

  public void setUpdateTime(long newTime){
    this.updatedAt = newTime;
  }

  public DiffusersReviewsModel getReviews(){
    return this.reviews;
  }

  public void setReviews(DiffusersReviewsModel reviews){
    this.reviews = reviews;
  }

  public DiffusersMediaModel getMediaContent(){
    return this.mediaContent;
  }

  public void setMediaContent(DiffusersMediaModel mediaContent){
    this.mediaContent = mediaContent;
  }

  public String getCollectionName(){
    return "products_diffusers";
  }

}
