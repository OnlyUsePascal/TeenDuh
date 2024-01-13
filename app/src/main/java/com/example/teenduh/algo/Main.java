package com.example.teenduh.algo;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Set<String> interestList1 = Set.of("interest1", "interest2");
        Set<String> interestList2 = Set.of("interest2", "interest3");
        Set<String> interestList3 = Set.of("interest1", "interest3");
        Set<String> interestList4 = Set.of("interest1", "interest2", "interest4");
        Set<String> interestList5 = Set.of("interest1", "interest2", "interest5");
        Set<String> interestList6 = Set.of("interest2", "interest4", "interest6");
        Set<String> interestList7 = Set.of("interest10", "interest2", "interest7");
        Set<String> interestList8 = Set.of("interest10", "interest11", "interest8");
        Set<String> interestList9 = Set.of("interest10", "interest11", "interest9");
        Set<String> interestList10 = Set.of("interest5", "interest1", "interest6");

        UserProfile user1 = new UserProfile("user1", 1000.0, interestList1);
        UserProfile user2 = new UserProfile("user2", 800.0, interestList2);
        UserProfile user3 = new UserProfile("user3", 799.0, interestList4);
        UserProfile user4 = new UserProfile("user4", 800.0, interestList4);
        UserProfile user5 = new UserProfile("user5", 1200.0, interestList5);
        UserProfile user6 = new UserProfile("user6", 1800.0, interestList6);
        UserProfile user7 = new UserProfile("user7", 2000.0, interestList7);
        UserProfile user8 = new UserProfile("user8", 300.0, interestList8);
        UserProfile user9 = new UserProfile("user9", 500.0, interestList9);
        UserProfile user10 = new UserProfile("user10", 700.0, interestList10);
        List<UserProfile> profiles = List.of(user1, user2, user3, user5, user6, user7, user8, user9, user10);
        UserProfile userFeatures = user4;
        EloRecommendationSystem eloRecommendationSystem = new EloRecommendationSystem(userFeatures, profiles);
        List<String> topProfiles = eloRecommendationSystem.getTopProfiles(10);
        for (String profile : topProfiles) {
            System.out.println("Your current score: " + userFeatures.getScore());
            System.out.println(profile);
            System.out.println("Do you like this profile? (y/n)");
            UserProfile user = null;
            for (UserProfile userProfile : profiles) {
                if (userProfile.getUserId().equals(profile.split(" ")[0])) {
                    user = userProfile;
                    break;
                }
            }
            String answer = sc.nextLine();
            if (answer.equals("y")) {
                eloRecommendationSystem.updateElo(userFeatures, user, 1);
            }else if (answer.equals("n")){
                eloRecommendationSystem.updateElo(userFeatures, user, 0);
            } else {
                System.out.println("Invalid input");
            }
        }


    }
}
