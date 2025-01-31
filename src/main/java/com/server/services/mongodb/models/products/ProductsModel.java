package com.server.services.mongodb.models.products;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ProductsModel {

  public static class CreatedTime {
    public long createdAt;
  }

  public static class UpdatedTime {
    public long updatedAt;
  }

  // public static class Category {
  //   public String name;
  // }

  public static class Descriptions {
    public String summary;
    public String[] presentable;
  }

  public static class StockInfo {
    public String category;
    public int quantityMax;
    public String price;
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

}