package com.example.teenduh.model.message;

import com.example.teenduh._util.AndroidUtil;
import com.example.teenduh.model.User;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
  private String id;
  private List<String> users;
  private Timestamp lastAct;
  private String lastMess;
  private int lastSender;
  private List<Chat> chats;
  
  public ChatRoom(String id, List<String> users, Timestamp lastAct, String lastMess, int lastSender) {
    this.id = id;
    this.users = users;
    this.lastAct = lastAct;
    this.lastMess = lastMess;
    this.lastSender = lastSender;
    this.chats = new ArrayList<>();
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public List<String> getUsers() {
    return users;
  }
  
  public void setUsers(List<String> users) {
    this.users = users;
  }
  
  public Timestamp getLastAct() {
    return lastAct;
  }
  
  public void setLastAct(Timestamp lastAct) {
    this.lastAct = lastAct;
  }
  
  public String getLastMess() {
    return lastMess;
  }
  
  public void setLastMess(String lastMess) {
    this.lastMess = lastMess;
  }
  
  public int getLastSender() {
    return lastSender;
  }
  
  public void setLastSender(int lastSender) {
    this.lastSender = lastSender;
  }
  
  public List<Chat> getChats() {
    return chats;
  }
  
  public void setChats(List<Chat> chats) {
    this.chats = chats;
  }
  
  public User getOtherUser(){
    String otherId = "";
    for (String user : users) {
      if (!user.equals(AndroidUtil.getCurUser().getId())) {
        otherId = user;
        break;
      }
    }
    
    return AndroidUtil.getUserWithId(otherId);
  }
  
  public int getSenderIndex(String id){
    return users.indexOf(id);
  }
  
  @Override
  public String toString() {
    return "ChatRoom{" +
               "id='" + id + '\'' +
               ", users=" + users +
               ", lastAct=" + lastAct +
               ", lastMess='" + lastMess + '\'' +
               ", lastSender=" + lastSender +
               '}';
  }
}
