package com.example.teenduh.model.message;

import android.annotation.SuppressLint;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MessageViewModel {
  private String uid;
  private String senderUid;
  private String receiverUid;
  private String name;
  private String messagePreview;
  private String imageUrl;
  private Date date;
  private Boolean isUnread;

  public MessageViewModel(String uid, String senderUid, String receiverUid, String name, String message, String imageUrl, Date date, Boolean isUnread) {
    this.uid = uid;
    this.senderUid = senderUid;
    this.receiverUid = receiverUid;
    this.name = name;
    this.messagePreview = message;
    this.imageUrl = imageUrl;
    this.date = date;
    this.isUnread = isUnread;
  }

  public String getUid() {
    return uid;
  }

  public Date getDate() {
    return date;
  }

  public Boolean getUnread() {
    return isUnread;
  }

  public String getName() {
    return name;
  }

  public String getMessagePreview() {
    return messagePreview;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getSenderUid() {
    return senderUid;
  }

  public String getPreviewDate() {
    Date currentDate = new Date();

    Calendar currentDateCalendar = new GregorianCalendar();
    Calendar messageDateCalendar = new GregorianCalendar();

    currentDateCalendar.setTime(currentDate);
    messageDateCalendar.setTime(date);

    int currentYear = currentDateCalendar.get(Calendar.YEAR);
    int currentDay = currentDateCalendar.get(Calendar.DAY_OF_MONTH);
    int currentWeek = currentDateCalendar.get(Calendar.WEEK_OF_YEAR);

    int messageYear = messageDateCalendar.get(Calendar.YEAR);
    int messageMonth = messageDateCalendar.get(Calendar.MONTH);
    int messageDay = messageDateCalendar.get(Calendar.DAY_OF_MONTH);
    int messageWeek = messageDateCalendar.get(Calendar.WEEK_OF_YEAR);

    if (currentYear == messageYear) {
      if (currentWeek == messageWeek) {
        if (currentDay == messageDay) {
          return String.format(
            "%02d:%02d",
            messageDateCalendar.get(Calendar.HOUR_OF_DAY),
            messageDateCalendar.get(Calendar.MINUTE)
          );
        } else {
          return messageDateCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US);
        }
      } else {
        return String.format(
          "%s %d",
          messageDateCalendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US),
          messageDay
        );
      }
    }

    return String.format("%02d.%02d.%02d", messageDay, messageMonth + 1, messageYear);
  }

  public String getReceiverUid() {
    return receiverUid;
  }
}
