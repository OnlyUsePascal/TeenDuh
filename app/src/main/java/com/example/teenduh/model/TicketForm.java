package com.example.teenduh.model;

public class TicketForm {
  private String name;
  private String number;
  private String date;
  private String reason;

  public TicketForm() {
  }

  public TicketForm(String name, String number, String date, String reason) {
    this.name = name;
    this.number = number;
    this.date = date;
    this.reason = reason;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }
}
