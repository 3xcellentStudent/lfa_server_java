package com.server.services.mongodb.models.global;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "global_data")
public class GlobalDataModel {

  @Id
  public String _id;
  public Theme theme;

  public GlobalDataModel(){}

  public GlobalDataModel(GlobalDataModel globalDataModel){
    this.theme = globalDataModel.theme;
  }

  public String setId(String newId){
    this._id = newId;
    return this._id;
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
