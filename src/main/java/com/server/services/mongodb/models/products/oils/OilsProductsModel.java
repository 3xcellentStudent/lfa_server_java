package com.server.services.mongodb.models.products.oils;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.server.services.mongodb.models.media.diffusers.DiffusersMediaModel;
import com.server.services.mongodb.models.products.ProductsModel.Descriptions;
import com.server.services.mongodb.models.products.ProductsModel.ProductOption;
import com.server.services.mongodb.models.products.ProductsModel.Specifications;
import com.server.services.mongodb.models.products.ProductsModel.StockInfo;
import com.server.services.mongodb.models.products.ProductsModel.Theme;
import com.server.services.mongodb.models.products.diffusers.DiffusersProductsModel;
import com.server.services.mongodb.models.reviews.diffusers.DiffusersReviewsModel;

import java.util.List;

@Document(collection = "productsOils")
public class OilsProductsModel {

  @Id
  private String id;
  private String reviews_id = null;
  private String media_id = null;
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

  public OilsProductsModel(){}

  public OilsProductsModel(DiffusersProductsModel requestBody){
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

  public void setReviewsId(String reviews_id){
    this.reviews_id = reviews_id;
  }

  public String getReviewsId(){
    return this.reviews_id;
  }

  public void setMediaId(String media_id){
    this.media_id = media_id;
  }

  public String getMediaId(){
    return this.media_id;
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

}
