package com.server.services.mongodb.models.products;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Map;

@Document
public class ProductsModel {

  @Id
  public String id;
  public Common common;
  public SectionTitle sectionTitle;
  public SectionDescr sectionDescr;
  public SectionDetails sectionDetails;
  public SectionReviews sectionReviews;

  public ProductsModel(){}
  public ProductsModel(ProductsModel product){
    this.common = product.common;
    this.sectionTitle = product.sectionTitle;
    this.sectionDescr = product.sectionDescr;
    this.sectionDetails = product.sectionDetails;
    this.sectionReviews = product.sectionReviews;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public Common getCommon() {
    return common;
  }

  public void setCommon(Common common) {
    this.common = common;
  }

  public SectionTitle getSectionTitle() {
    return sectionTitle;
  }

  public void setSectionTitle(SectionTitle sectionTitle) {
    this.sectionTitle = sectionTitle;
  }

  public SectionDescr getSectionDescr() {
    return sectionDescr;
  }

  public void setSectionDescr(SectionDescr sectionDescr) {
    this.sectionDescr = sectionDescr;
  }

  public SectionDetails getSectionDetails() {
    return sectionDetails;
  }

  public void setSectionDetails(SectionDetails sectionDetails) {
    this.sectionDetails = sectionDetails;
  }

  public SectionReviews getSectionReviews() {
    return sectionReviews;
  }

  public void setSectionReviews(SectionReviews sectionReviews) {
    this.sectionReviews = sectionReviews;
  }
  
  public static class Common {
    public double rating;
    public Map<String, String> category;
    public String title;
    public Theme theme;
    public String productLogo;
    public SxQuantity sxQuantity;
  }

  public static class Theme {
    public Map<String, String> colors;
    public Map<String, SxCircle> shadows;
  }

  public static class SxCircle {
    public String boxShadow;
  }

  public static class SxQuantity {
    public String boxShadow;
    public String backgroundColor;
    public String color;
    public Hover hover;
  }

  public static class Hover {
    public String backgroundColor;
    public String color;
    public String boxShadow;
  }

  public static class SectionTitle {
    public String description;
    public double price;
    public int quantityMax;
    public Map<String, String> sx;
    public PurchasePart purchasePart;
  }

  public static class PurchasePart {
    public SxCont sxCont;
    public Components components;
  }

  public static class SxCont {
    public String color;
    public String backgroundColor;
  }

  public static class Components {
    public TitleC titleC;
    public AddCartC addCartC;
    public SelectionC selectionC;
  }

  public static class TitleC {
    public RatingC ratingC;
  }

  public static class RatingC {
    public String content;
    public Map<String, String> sxText;
    public Map<String, String> sxRating;
  }

  public static class AddCartC {
    public Map<String, Object> sxBtn;
  }

  public static class SelectionC {
    public Map<String, Object> sxBox;
    public List<FieldC> fieldC;
  }

  public static class FieldC {
    public Map<String, String> sxField;
    public String name;
    public String type;
    public List<Item> items;
  }

  public static class Item {
    public String value;
    public String fill;
    public String stroke;
  }

  public static class SectionDescr {
    public Map<String, Object> sx;
    public List<List<Image>> images;
    public List<String> description;
  }

  public static class Image {
    public String media;
    public String src;
  }

  public static class SectionDetails {
    public Map<String, Object> sx;
    public List<DetailArray> array;
  }

  public static class DetailArray {
    public String title;
    public List<DetailItem> items;
  }

  public static class DetailItem {
    public String name;
    public String value;
  }

  public static class SectionReviews {
    public ReviewsSnapshot reviewsSnapshot;
    public Map<String, Object> sxRating;
    public Map<String, Object> sxText;
    public Map<String, Object> sxFilter;
    public UserReviews userReviews;
  }

  public static class ReviewsSnapshot {
    public int five;
    public int four;
    public int three;
    public int two;
    public int one;
  }

  public static class UserReviews {
    public Theme theme;
    public List<Review> reviewsArray;
  }

  public static class Review {
    public String name;
    public String text;
    public int rating;
    public List<String> attachments;
  }
}
