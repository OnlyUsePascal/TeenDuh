package com.example.teenduh._util.algo;

import com.example.teenduh.model.User;

public class ProfileScore {
    User user;
    Double score;

    public ProfileScore(User user, Double score) {
        this.user = user;
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public Double getScore() {
        return score;
    }
}