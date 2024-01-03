package com.example.teenduh.model;

public class Image {
    private int image;
    private String text;
    public Image(int image, String text){
        this.image = image;
        this.text = text;
    }
    public int getImage(){
        return image;
    }
    public String getText(){
        return text;
    }

}
