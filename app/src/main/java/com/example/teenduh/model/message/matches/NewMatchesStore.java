package com.example.teenduh.model.message.matches;

import java.util.ArrayList;
import java.util.List;

public class NewMatchesStore {
  public static List<NewMatchesViewModel> matchesList = new ArrayList<>();

  public static void addMatch(NewMatchesViewModel match) {
    // TODO: add match to database
    matchesList.add(match);
  }

  public static List<NewMatchesViewModel> getMatchesList() {
    return matchesList;
  }

  public static void fetch() {
    // TODO: fetch matches from database
    matchesList.clear();
    matchesList.add(new NewMatchesViewModel("John Doe", "https://i.imgur.com/1XZV3cj.jpg"));
    matchesList.add(new NewMatchesViewModel("Kevin Nguyen", "https://i.imgur.com/1XZV3cj.jpg"));
    matchesList.add(new NewMatchesViewModel("Khanh Nhu", "https://i.imgur.com/1XZV3cj.jpg"));
    matchesList.add(new NewMatchesViewModel("Antonio Banderas", "https://i.imgur.com/1XZV3cj.jpg"));
  }
}
