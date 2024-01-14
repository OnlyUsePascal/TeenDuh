package com.example.teenduh.algo;

public class ProfileScore {
    String profileId;
    Double score;

    public ProfileScore(String profileId, Double score) {
        this.profileId = profileId;
        this.score = score;
    }

    public String getProfileId() {
        return profileId;
    }

    public Double getScore() {
        return score;
    }
}