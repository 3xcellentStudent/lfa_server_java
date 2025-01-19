package com.server.services.mongodb.models.products;

import java.util.List;

public class ProductsModel {

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
      public List<SpecificationsTitles> titles;
      public List<Properties> properties;

    public static class SpecificationsTitles {
      public String name;
    }

    public static class PropertiesArrayObject {
      public String name;
      public String value;
    }

    public static class Properties {
      public String name;
      public List<PropertiesArrayObject> array;
    }
  }

}