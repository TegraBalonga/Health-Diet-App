package com.example.myhealthdietapp;

import android.app.Application;

import com.google.firebase.database.Exclude;

public class UsersDetailsClass {

    String Height, Weight, Age,UnitHeight,UnitWeight,Gender, Username, Goal;


  public UsersDetailsClass(){}
        public UsersDetailsClass(String height, String weight, String age, String unitHeight, String unitWeight, String gender, String goal) {
        Height = height;
        Weight = weight;
        Age = age;
        UnitHeight = unitHeight;
        UnitWeight = unitWeight;
        Gender = gender;
        Goal = goal;
    }


    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getUnitHeight() {
        return UnitHeight;
    }

    public void setUnitHeight(String unitHeight) {
        UnitHeight = unitHeight;
    }

    public String getUnitWeight() {
        return UnitWeight;
    }

    public void setUnitWeight(String unitWeight) {
        UnitWeight = unitWeight;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public  String getUsername() {
        return Username;
    }

    public  void setUsername(String username) {
        Username = username;
    }

    public String getGoal() { return Goal; }

    public void setGoal(String goal) { Goal = goal; }
}
