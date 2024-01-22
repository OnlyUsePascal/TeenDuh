package com.example.teenduh._util.algo;

import com.example.teenduh.model.User;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EloRecommendationSystem {
    private final static double para1 = 300.0;
    private final static double para2 = 400.0;
    private final static double para3 = 2000.0;
    private final static double k = 10.0;
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

    public List<User> getTopProfiles(int n) {
        List<Double> scores = predictScores();
        List<ProfileScore> profileScores = new ArrayList<>();
        for (int i = 0; i < scores.size(); i++) {
            profileScores.add(new ProfileScore(profiles.get(i).getUser(), scores.get(i)));
        }

        //sort profileScores in descending order of score
        Collections.sort(profileScores, (a, b) -> b.getScore().compareTo(a.getScore()));

        List<User> topProfiles = new ArrayList<>();
        for (int i = 0; i < Math.min(n, profileScores.size()); i++) {
            String str = profileScores.get(i).getUser().getName() + " " + profileScores.get(i).getScore();
//            System.out.println("Score: " + str);
//            topProfiles.add(profileScores.get(i).getProfileId());
            topProfiles.add(profileScores.get(i).getUser());
        }

        return topProfiles;
    }

    private static double  jaccardSimilarity(Set<String> set1, Set<String> set2) {
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);
        return (double) intersection.size() / union.size();
    }

    private static double  calculateDesirabilityScore(UserProfile user, UserProfile profile) {
        double interestSimilarity = jaccardSimilarity(user.getInterests(), profile.getInterests());
        return para3 / (1.0 + Math.pow(10, Math.abs(profile.getScore() - user.getScore()) / para2)) + interestSimilarity * para1;
    }

    private static double calculateExpectedScore(User user, User profile) {
        double interestSimilarity = jaccardSimilarity(user.getInterests(), profile.getInterests());
        return 1.0 / (1.0 + Math.pow(10, (profile.getElo() - user.getElo()) / para2));
    }

    public static void updateElo (User userA, User userB, double S) { // S = 1 if userA likes userB, S = 0 if userA dislikes userB
        // Simulate match result, replace with actual logic
        double expectedScoreA = calculateExpectedScore(userA, userB);
        double newEloScore = userA.getElo() + k * (S - expectedScoreA);
        System.out.println("new elo score: " + newEloScore);
        System.out.println("expected score: " + expectedScoreA);
        userA.setElo(newEloScore);
    }



}
