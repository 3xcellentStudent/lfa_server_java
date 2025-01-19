package com.server.services.mongodb.models.products.diffusers;

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

@Document(collection = "diffusers")
public class DiffusersProductsModel {

  @Id
  private String _id;
  private String reviews_id;
  public String media_id;
  public double rating;
  public Reviews reviews;
  public Category category;
  public String title;
  public Descriptions descriptions;
  public StockInfo stockInfo;
  public List<ProductOption> productOptions;
  public MediaContent mediaContent;
  public Specifications specifications;
  private long created_at;
  private long updated_at;

  public DiffusersProductsModel(){}

  public DiffusersProductsModel(DiffusersProductsModel product){
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

  public void setId(String _id){
    this._id = _id;
  }

  public void setReviewsId(String reviews_id){
    this.reviews_id = reviews_id;
  }

  public String getId(){
    return this._id;
  }

  public String getReviewsId(){
    return this.reviews_id;
  }

  public long getCreationTime(){
    return this.created_at;
  }

  public void setCreationTime(long newTime){
    this.created_at = newTime;
  }

  public long getUpdateTime(){
    return this.updated_at;
  }

  public void setUpdateTime(long newTime){
    this.updated_at = newTime;
  }
}























// package com.server.services.mongodb.models.products.diffusers;

// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;

// import java.util.List;

// @Document(collection = "diffusers")
// public class ProductsModel {

//   @Id
//   public String id;
//   public double rating;
//   public Reviews reviews;
//   public Category category;
//   public String title;
//   public Descriptions descriptions;
//   public StockInfo stockInfo;
//   public List<ProductOption> productOptions;
//   public MediaContent mediaContent;
//   public Specifications specifications;

//   public ProductsModel(){}
  
//   public ProductsModel(ProductsModel product){
//     this.rating = product.rating;
//     this.reviews = product.reviews;
//     this.category = product.category;
//     this.title = product.title;
//     this.descriptions = product.descriptions;
//     this.stockInfo = product.stockInfo;
//     this.productOptions = product.productOptions;
//     this.mediaContent = product.mediaContent;
//     this.specifications = product.specifications;
//   }

//   public String setId(String id){
//     this.id = id;
//     return id;
//   }

//   public static class Reviews {
//     public int countOfReviews;
//     public ReviewsSnapshot reviewsSnapshot;
//     public List<Review> reviewsList;

//     public static class ReviewsSnapshot {
//       public int five;
//       public int four;
//       public int three;
//       public int two;
//       public int one;
//     }

//     public static class Review {
//       public String name;
//       public String text;
//       public String title;
//       public String rating;
//       public List<String> attachments;
//     }
//   }

//   public static class Category {
//     public String name;
//   }

//   public static class Descriptions {
//     public String summary;
//     public String[] presentable;
//   }

//   public static class StockInfo {
//     public int quantityMax;
//     public double price;
//   }

//   public static class ProductOption {
//     public String name;
//     public String type;
//     public List<Item> items;
//   }

//   public static class Item {
//     public String value;
//     public String fill;
//     public String stroke;
//     public boolean stockStatus;
//   }

//   public static class MediaContent {
//     public TitleContent titleContent;
//     public List<List<Image>> images;

//     public static class TitleContent {
//       public String productLogo;
//       public String descriptionVideo;
//     }

//     public static class Image {
//       public String media;
//       public String src;
//     }
//   }

//   public static class Specifications {
//       public List<SpecificationsTitles> titles;
//       public List<Properties> properties;

//     public static class SpecificationsTitles {
//       public String name;
//     }

//     public static class PropertiesArrayObject {
//       public String name;
//       public String value;
//     }

//     public static class Properties {
//       public String name;
//       public List<PropertiesArrayObject> array;
//     }
//   }

// }