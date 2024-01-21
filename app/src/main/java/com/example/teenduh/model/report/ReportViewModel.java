package com.example.teenduh.model.report;

import com.google.firebase.Timestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class ReportViewModel {
  private String id;
  private String reporterId;
  private String receiverId;
  private String description;
  private boolean viewed;
  private Date date;

  public ReportViewModel() {
  }

  public ReportViewModel(String id, String callerId, String receiverId, String description, boolean viewed, Date date) {
    this.id = id;
    this.reporterId = callerId;
    this.receiverId = receiverId;
    this.description = description;
    this.viewed = viewed;
    this.date = date;
  }

  public String getId() {
    return id;
  }

  public String getReporterId() {
    return reporterId;
  }

  public String getShortenedReporterId() {
    return reporterId.substring(0, 5);
  }

  public String getReceiverId() {
    return receiverId;
  }

  public String getShortenedReceiverId() {
    return receiverId.substring(0, 5);
  }

  public String getDescription() {
    return description;
  }

  public boolean isViewed() {
    return viewed;
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
}
