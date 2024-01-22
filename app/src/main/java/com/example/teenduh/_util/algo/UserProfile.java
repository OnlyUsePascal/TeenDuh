package com.example.teenduh._util.algo;

import com.example.teenduh.model.User;

import java.util.Set;

public class UserProfile {
    User user;
    Double score;
    Set<String> interests;

    public UserProfile(User user, Double score, Set<String> interests) {
        this.user = user;
        this.score = score;
        this.interests = interests;
    }

    public User getUser() {
        return user;
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