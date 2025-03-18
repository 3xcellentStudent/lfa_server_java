package com.server.databases.mongodb.models.products.diffusers;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.server.databases.mongodb.models.media.diffusers.DiffusersMediaModel;
import com.server.databases.mongodb.models.products.ProductsModel.Descriptions;
import com.server.databases.mongodb.models.products.ProductsModel.ProductOption;
import com.server.databases.mongodb.models.products.ProductsModel.Specifications;
import com.server.databases.mongodb.models.products.ProductsModel.StockInfo;

import java.util.ArrayList;
import java.util.List;

@Component
@Document(collection = "products_diffusers")
public class DiffusersProductsModel {

  @Id
  private String id;
  private ArrayList<String> reviewsId = new ArrayList<>();
  private String mediaId;
  public String rating;
  public String title;
  public Descriptions descriptions;
  public StockInfo stockInfo;
  public List<ProductOption> productOptions;
  public Specifications specifications;
  private DiffusersMediaModel mediaContent;
  private long createdAt;
  private long updatedAt;

  public DiffusersProductsModel(){}

  public DiffusersProductsModel(DiffusersProductsModel requestBody){
    this.rating = requestBody.rating;
    this.title = requestBody.title;
    this.descriptions = requestBody.descriptions;
    this.stockInfo = requestBody.stockInfo;
    this.productOptions = requestBody.productOptions;
    this.specifications = requestBody.specifications;
  }

  public void setId(String id){
    this.id = id;
  }

  public String getId(){
    return this.id;
  }

  public void setReviewsId(ArrayList<String> reviewsId){
    this.reviewsId = reviewsId;
  }

  public ArrayList<String> getReviewsId(){
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

  public DiffusersMediaModel getMediaContent(){
    return this.mediaContent;
  }

  public void setMediaContent(DiffusersMediaModel mediaContent){
    this.mediaContent = mediaContent;
  }

  public int getReviewsSnapshotByFieldName(String field){
    switch(field){
      case "five": return stockInfo.reviewsSnapshot.five;
      case "four": return stockInfo.reviewsSnapshot.four;
      case "three": return stockInfo.reviewsSnapshot.three;
      case "two": return stockInfo.reviewsSnapshot.two;
      case "one": return stockInfo.reviewsSnapshot.one;
      default: return stockInfo.reviewsSnapshot.five;
    }
  }

  public String getCollectionName(){
    return "products_diffusers";
  }

}
