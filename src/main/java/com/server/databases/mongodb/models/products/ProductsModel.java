package com.server.databases.mongodb.models.products;

import java.util.List;

import org.springframework.stereotype.Component;

import com.server.databases.mongodb.models.reviews.ReviewsModel.ReviewsSnapshot;

@Component
public class ProductsModel {

  public static class CreatedTime {
    public long createdAt;
  }

  public static class UpdatedTime {
    public long updatedAt;
  }

  public static class Descriptions {
    public String summary;
    public String[] presentable;
  }

  public static class StockInfo {
    public String category;
    public int quantityMax;
    public String price;
    public ReviewsSnapshot reviewsSnapshot = new ReviewsSnapshot();
    public int countOfReviews;
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
    public int mediaIndex;
  }

  public static class MediaContent {
    public TitleContent titleContent;
    public List<List<Image>> images;

    public static class TitleContent {
      public String productLogo;
      public String descriptionVideo;
    }

    public static class Image {
      public String media;
      public String src;
    }
  }

  public static class Specifications {
      public List<String> titles;
      public List<Properties> properties;

    public static class Properties {
      public String name;
      public List<PropertiesArrayObject> array;
    }

    public static class PropertiesArrayObject {
      public String name;
      public String value;
    }
  }

}