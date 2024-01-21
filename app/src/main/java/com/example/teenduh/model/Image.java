package com.example.teenduh.model;

import android.net.Uri;

import java.net.URI;

public class Image {
  private int image;
  private String heading;
  private String description;
  private Uri uri;
  
  public Image(int image, String text) {
    this.image = image;
    this.heading = text;
  }

  public Image(int image, String heading, String description) {
    this.image = image;
    this.heading = heading;
    this.description = description;
  }
  
  public Image(Uri uri, String heading){
    this.uri = uri;
    this.heading = heading;
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
  
  public Uri getUri() {
    return uri;
  }
}
