package com.example.teenduh.model;

import android.net.Uri;

import com.example.teenduh._util.FirebaseUtil;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.IOException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLng;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {
  private String id;
  private String name;
  private LocalDate birthday;
  private String gender;
  private String fcm;
  private LatLng location;
  private String interestPreference;
  private String distancePreference;
  private String lookingFor;
  private Boolean isShowInfoGender;
  private ArrayList<String> sexualOrientationList;
  private String InterestedIn;
  private String city;
  private boolean isInitial = true;
  private String likedPeople;
  private Uri[] imageUris = new Uri[6];
  private List<Image> images;
  private List<String> info = new ArrayList<>();

  private String drinkHabit= "", workoutHabit = "", smokeHabit = "", petHabit = "";
  private String communicationHabit= "", educationHabit = "", zodiacHabit = "";
  String bio = "";

  public User(String name, String city, LocalDate birthday){
    this.name = name;
    this.city = city;
    this.birthday = birthday;
  }


  public User(String name, String gender, String interestPreference, String distancePreference, String lookingFor) {
    this.name = name;
    this.gender = gender;
    this.interestPreference = interestPreference;
    this.distancePreference = distancePreference;
    this.lookingFor = lookingFor;
  }



  public User(String id, String name, String fcm, LocalDate bday){
    this.id = id;
    this.name = name;
    this.fcm = fcm;
    this.birthday = bday;
  }

  public User(String id, String name, String fcm, LocalDate bday, LatLng location){
    this.id = id;
    this.name = name;
    this.fcm = fcm;
    this.birthday = bday;
    this.location = location;
  }


  public User(String id, String name, String fcm, LocalDate bday, LatLng location, List<String> info){
    this.id = id;
    this.name = name;
    this.fcm = fcm;
    this.birthday = bday;
    this.location = location;
    this.info = info;
  }

  public User(String id, String name, String fcm, LocalDate bday, LatLng location, String gender, String drinkHabit, String workoutHabit, String smokeHabit, String petHabit, String communicationHabit, String educationHabit, String zodiacHabit){
    this.id = id;
    this.name = name;
    this.fcm = fcm;
    this.birthday = bday;
    this.location = location;
    this.gender = gender;
    this.drinkHabit = drinkHabit;
    this.workoutHabit = workoutHabit;
    this.smokeHabit = smokeHabit;
    this.petHabit = petHabit;
    this.communicationHabit = communicationHabit;
    this.educationHabit = educationHabit;
    this.zodiacHabit = zodiacHabit;
  }


  public User(String id, String name, String fcm, LocalDate bday, LatLng location, String drinkHabit, String workoutHabit, String smokeHabit, String petHabit, String communicationHabit, String educationHabit, String zodiacHabit){
    this.id = id;
    this.name = name;
    this.fcm = fcm;
    this.birthday = bday;
    this.location = location;
    this.drinkHabit = drinkHabit;
    this.workoutHabit = workoutHabit;
    this.smokeHabit = smokeHabit;
    this.petHabit = petHabit;
    this.communicationHabit = communicationHabit;
    this.educationHabit = educationHabit;
    this.zodiacHabit = zodiacHabit;
  }

  public String getDrinkHabit() {
    return drinkHabit;
  }

  public void setDrinkHabit(String drinkHabit) {
    this.drinkHabit = drinkHabit;
  }

  public String getWorkoutHabit() {
    return workoutHabit;
  }

  public void setWorkoutHabit(String workoutHabit) {
    this.workoutHabit = workoutHabit;
  }

  public String getSmokeHabit() {
    return smokeHabit;
  }

  public void setSmokeHabit(String smokeHabit) {
    this.smokeHabit = smokeHabit;
  }

  public String getPetHabit() {
    return petHabit;
  }

  public void setPetHabit(String petHabit) {
    this.petHabit = petHabit;
  }

  public String getCommunicationHabit() {
    return communicationHabit;
  }

  public void setCommunicationHabit(String communicationHabit) {
    this.communicationHabit = communicationHabit;
  }

  public String getEducationHabit() {
    return educationHabit;
  }

  public void setEducationHabit(String educationHabit) {
    this.educationHabit = educationHabit;
  }

  public String getZodiacHabit() {
    return zodiacHabit;
  }

  public void setZodiacHabit(String zodiacHabit) {
    this.zodiacHabit = zodiacHabit;
  }

  public void setLocation(LatLng location) {
    this.location = location;
  }

  public User(){}
  
  public String getLikedPeople() {
    return likedPeople;
  }
  
  public List<Image> getPics(){
    if (images != null) return images;
    
    images = new ArrayList<>();
    for (int picIdx : picIdxes) {
      images.add(new Image(imageUris[picIdx], name + "/pic " + picIdx));
    }
    return images;
  }

  public void addInfoData(String data){
    this.info.add(data);
  }

  public List<String> getInfo() {
    return info;
  }

  public void setInfo(List<String> info) {
    this.info = info;
  }

  public int getAge() {
    return LocalDate.now().getYear() - birthday.getYear();
  }

  public String getCity() {
    return "What???";
  }

  public String getGender() {
    return gender;
  }

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

  public void setBirthday(LocalDate birthday) {
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

  public boolean getIsInitial() {
    return isInitial;
  }

  public void setFirstFetchDone() {
    this.isInitial = false;
  }

  public Uri getImageListItem(int index) {
    try {
      return imageUris[index];
    } catch (Exception e) {
      return null;
    }
  }
  
  public void setImageUris(Uri[] imageUris) {
    this.imageUris = imageUris;
  }

  private List<Integer> picIdxes = new ArrayList<>();
  public void setPicIdxes(List<Integer> picIdxes){
    this.picIdxes = picIdxes;
  }
  
  public List<Integer> getPicIdxes() {
    return picIdxes;
  }
  
  public void fetchPics(){
    for (Integer index : picIdxes) {
      try {
        System.out.println(name + "/searching: " + index);
        File localFile = File.createTempFile("images", "jpg");
  
        // TODO: modify the storage url
        String filePath = "users/test/" + id + "/" + index;
        StorageReference fileToDownloadRef =  FirebaseUtil
                                                  .getStorageRef()
                                                  .child(filePath);
        fileToDownloadRef.getFile(localFile).addOnCompleteListener(task -> {
          if (!task.isSuccessful()) {
            task.getException().printStackTrace();
            return;
          }
          
          Uri tempUri = Uri.fromFile(localFile);
          System.out.println(name + "/image:" + index + ":" + tempUri);
          imageUris[index] = tempUri;
          // getActivity().runOnUiThread(() -> {
          //   imageView.setImageURI(tempUri);
          //   imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
          // });
        });
      } catch (IOException err){
        err.printStackTrace();
      }
    }
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
