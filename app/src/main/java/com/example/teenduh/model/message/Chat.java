package com.example.teenduh.model.message;

import com.google.firebase.Timestamp;

public class Chat {
  private String id;
  private String mess;
  private Timestamp timestamp;
  private int user;
  
  
  public Chat(String id, String mess, Timestamp timestamp, int user) {
    this.id = id;
    this.mess = mess;
    this.timestamp = timestamp;
    this.user = user;
  }
  
  public int getUser() {
    return user;
  }
  
  public void setUser(int user) {
    this.user = user;
  }
  
  @Override
  public String toString() {
    return "Chat{" +
               "id='" + id + '\'' +
               ", mess='" + mess + '\'' +
               ", timestamp=" + timestamp +
               '}';
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getMess() {
    return mess;
  }
  
  public void setMess(String mess) {
    this.mess = mess;
  }
  
  public Timestamp getTimestamp() {
    return timestamp;
  }
  
  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }
}
