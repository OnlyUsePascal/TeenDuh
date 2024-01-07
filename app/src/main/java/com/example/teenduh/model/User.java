package com.example.teenduh.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private String id;
    private String name;
    private String birthday;
    private String gender;
    private String fcm;
    private String interestPreference;
    private String distancePreference;
    private String lookingFor;
    private Boolean isShowInfoGender;
    private ArrayList<String> sexualOrientationList;
    private String InterestedIn;
    

    public User(String name, String birthday, String gender, String interestPreference, String distancePreference, String lookingFor) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.interestPreference = interestPreference;
        this.distancePreference = distancePreference;
        this.lookingFor = lookingFor;
    }
    
    public User(String id, String name, String fcm){
        this.id = id;
        this.name = name;
        this.fcm = fcm;
    }
    
    public User(){}
    
    public String getFcm() {
        return fcm;
    }
    
    public void setFcm(String fcm) {
        this.fcm = fcm;
    }
    
    public String getName() {
        return name;
    }
    

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public void setInterestedIn(String interestedIn) {
       this.InterestedIn = interestedIn;
    }

    public void setInterestPreference(String interestPreference) {
        this.interestPreference = interestPreference;
    }

    public void setDistancePreference(String distancePreference) {
        this.distancePreference = distancePreference;
    }

    public void setLookingFor(String lookingFor) {
        this.lookingFor = lookingFor;
    }

    public ArrayList<String> getSexualOrientationList() {
        return sexualOrientationList;
    }

    public void setSexualOrientationList(ArrayList<String> sexualOrientationList) {
        this.sexualOrientationList = sexualOrientationList;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return "User{" +
                   "id='" + id + '\'' +
                   ", name='" + name + '\'' +
                   ", fcm='" + fcm + '\'' +
                   '}';
    }
}
