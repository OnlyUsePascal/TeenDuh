package com.example.teenduh.model;

public class Image {
  private int image;
  private String heading;
  private String description;
  public Image(int image, String text) {
    this.image = image;
    this.heading = text;
  }

  public Image(int image, String text, String description) {
    this.image = image;
    this.heading = text;
    this.description = description;
  }

  public int getImage(){
    return image;
  }
  public String getHeading(){
    return heading;
  }

  public String getDescription() {
    return description;
  }

}
