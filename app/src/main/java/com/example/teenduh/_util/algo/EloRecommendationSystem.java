package com.example.teenduh._util.algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EloRecommendationSystem {
    private final double para1 = 300.0;
    private final double para2 = 400.0;
    private final double para3 = 2000.0;
    private final double k = 10.0;
    List<UserProfile> profiles;
    UserProfile userFeatures;

    public EloRecommendationSystem(UserProfile userFeatures, List<UserProfile> profiles) {
        this.userFeatures = userFeatures;
        this.profiles = profiles;
    }

    public List<Double> predictScores() {
        List<Double> scores = new ArrayList<>();
        for (UserProfile profile : profiles) {
            double expectedScore = calculateDesirabilityScore(userFeatures, profile);
            scores.add(expectedScore);
        }
        return scores;
    }

    public List<String> getTopProfiles(int n) {
        List<Double> scores = predictScores();
        List<ProfileScore> profileScores = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++) {
            profileScores.add(new ProfileScore(profiles.get(i).getUserId(), scores.get(i)));
        }

        //sort profileScores in descending order of score
        Collections.sort(profileScores, (a, b) -> b.getScore().compareTo(a.getScore()));

        List<String> topProfiles = new ArrayList<>();
        for (int i = 0; i < Math.min(n, profileScores.size()); i++) {
            String str = profileScores.get(i).getProfileId() + " " + profileScores.get(i).getScore();
//            topProfiles.add(profileScores.get(i).getProfileId());
            topProfiles.add(str);
        }

        return topProfiles;
    }

    private double jaccardSimilarity(Set<String> set1, Set<String> set2) {
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);
        return (double) intersection.size() / union.size();
    }

    private double calculateDesirabilityScore(UserProfile user, UserProfile profile) {
        double interestSimilarity = jaccardSimilarity(user.getInterests(), profile.getInterests());
        return para3 / (1.0 + Math.pow(10, Math.abs(profile.getScore() - user.getScore()) / para2)) + interestSimilarity * para1;
    }

    private double calculateExpectedScore(UserProfile user, UserProfile profile) {
        double interestSimilarity = jaccardSimilarity(user.getInterests(), profile.getInterests());
        return 1.0 / (1.0 + Math.pow(10, (profile.getScore() - user.getScore()) / para2));
    }

    public void updateElo (UserProfile userA, UserProfile userB, double S) {
        // Simulate match result, replace with actual logic
        double expectedScoreA = calculateExpectedScore(userA, userB);
        double newEloScore = userA.getScore() + k * (S - expectedScoreA);
        System.out.println("new elo score: " + newEloScore);
        System.out.println("expected score: " + expectedScoreA);
        userA.setScore(newEloScore);
    }



}
