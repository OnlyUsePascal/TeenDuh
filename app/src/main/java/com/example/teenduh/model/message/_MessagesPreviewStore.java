package com.example.teenduh.model.message;

import com.example.teenduh._util.AndroidUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class _MessagesPreviewStore {
  public static HashMap<String, _MessageViewModel> messagesPreviewMap = new HashMap<>();
  public static HashMap<String, Integer> unreadAmountMap = new HashMap<>();

  public static void addMessage(_MessageViewModel message) {
    // TODO: add message to database
    unreadAmountMap.merge(message.getSenderUid(), message.getUnread() ? 1 : 0, Integer::sum);
    messagesPreviewMap.put(message.getSenderUid(), message);
  }

  public static List<_MessageViewModel> getMessagesList() {
    return new ArrayList<>(messagesPreviewMap.values());
  }

  public static List<Integer> getUnreadAmountList() {
    return new ArrayList<>(unreadAmountMap.values());
  }

  public static void fetch() {
    // TODO: fetch messages from database
    messagesPreviewMap.clear();

    addMessage(new _MessageViewModel(
      "1",
      "10",
      "20",
      "John Doe",
      "Hello Dat, are you available for a date?",
      "https://i.imgur.com/1XZV3cj.jpg",
      AndroidUtil.parseDate(2024, 1, 3, 17, 56),
      true)
    );

    addMessage(new _MessageViewModel(
      "2",
      "11",
      "20",
      "Kevin Nguyen",
      "Hi Dat, I'm Kevin, nice to meet you!",
      "https://i.imgur.com/1XZV3cj.jpg",
      AndroidUtil.parseDate(2024, 1, 1, 13, 12),
      false)
    );

    addMessage(new _MessageViewModel(
      "3",
      "12",
      "20",
      "Khanh Nhu",
      "Hey Dat, I'm Khanh Nhu, nice to meet you!",
      "https://i.imgur.com/1XZV3cj.jpg",
      AndroidUtil.parseDate(2023, 12, 31, 17, 56),
      true)
    );

    addMessage(new _MessageViewModel(
      "4",
      "13",
      "20",
      "Antonio Banderas",
      "Hola Dat, I'm Antonio, nice to meet you!",
      "https://i.imgur.com/1XZV3cj.jpg",
      AndroidUtil.parseDate(2023, 12, 31, 17, 56),
      false)
    );

    addMessage(new _MessageViewModel(
      "5",
      "12",
      "20",
      "Khanh Nhu",
      "Hey Dat, I'm Khanh Nhu, nice to meet you!",
      "https://i.imgur.com/1XZV3cj.jpg",
      AndroidUtil.parseDate(2023, 1, 1, 17, 56),
      true)
    );
  }
}
