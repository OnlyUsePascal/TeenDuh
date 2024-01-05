package com.example.teenduh.model.message.matches;

public class NewMatchesViewModel {
  private String name;
  private String imageUrl;

  public NewMatchesViewModel(String name, String imageUrl) {
    this.name = name;
    this.imageUrl = imageUrl;
  }

  public String getName() {
    return name;
  }

  public String getImageUrl() {
    return imageUrl;
  }
}
