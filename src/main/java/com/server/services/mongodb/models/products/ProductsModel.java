package com.server.services.mongodb.models.products;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class ProductsModel {

  @Id
  public String id;
  public Theme theme;
  public double rating;
  public Reviews reviews;
  public Category category;
  public String title;
  public Descriptions descriptions;
  public StockInfo stockInfo;
  public List<ProductOption> productOptions;
  public MediaContent mediaContent;

  public ProductsModel(){}
  
  public ProductsModel(ProductsModel product){
    this.theme = product.theme;
    this.rating = product.rating;
    this.reviews = product.reviews;
    this.category = product.category;
    this.title = product.title;
    this.descriptions = product.descriptions;
    this.stockInfo = product.stockInfo;
    this.productOptions = product.productOptions;
    this.mediaContent = product.mediaContent;
  }

  public String setId(String id){
    this.id = id;
    return id;
  }

  public static class Theme {
    public Colors colors;

    public static class Colors {
      public TextColors text;
      public BackgroundColors backgrounds;

      public static class TextColors {
        public ColorTypes primary;
        public ColorTypes secondary;
        public ColorTypes optional;
      }
      public static class BackgroundColors {
        public ColorTypes primary;
        public ColorTypes secondary;
        public ColorTypes optional;
        public ColorTypes elementsPrimary;
        public ColorTypes elementsSecondary;
        public ColorTypes elementsOptional;
      }
      public static class ColorTypes {
        public String hex;
        public String rgb;
      }
    }
  }

  public static class Reviews {
    public int countOfReviews;
    public ReviewsSnapshot reviewsSnapshot;
    public List<Review> reviewsList;
  }

  public static class ReviewsSnapshot {
    public int five;
    public int four;
    public int three;
    public int two;
    public int one;
  }

  public static class Review {
    public String name;
    public String text;
    public String rating;
    public List<String> attachments;
  }

  public static class Category {
    public String name;
  }

  public static class Descriptions {
    public String summary;
    public String[] presentable;
  }

  public static class StockInfo {
    public int quantityMax;
    public double price;
  }

  public static class ProductOption {
    public String name;
    public String type;
    public List<Item> items;
  }

  public static class Item {
    public String value;
    public String fill;
    public String stroke;
    public boolean stockStatus;
  }

  public static class MediaContent {
    public TitleContent titleContent;
    public List<List<Image>> images;
  }

  public static class TitleContent {
    public String productLogo;
  }

  public static class Image {
    public String media;
    public String src;
  }
}