package com.example.teenduh.algo;

import java.util.Set;

public class UserProfile {
    String userId;
    Double score;
    Set<String> interests;

    public UserProfile(String userId, Double score, Set<String> interests) {
        this.userId = userId;
        this.score = score;
        this.interests = interests;
    }

    public String getUserId() {
        return userId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Set<String> getInterests() {
        return interests;
    }

    public void setInterests(Set<String> interests) {
        this.interests = interests;
    }

    public void updateScore(Double newScore) {
        this.score = newScore;
    }


}