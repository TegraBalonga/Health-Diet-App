package com.example.myhealthdietapp;

import android.widget.ImageView;

public class MealsClass {

    String Protein, Carbs, Fat, Calories, Desc;
    String ImageName;

    public MealsClass() {
    }

    public MealsClass(String calories, String protein, String carbs, String fat, String imageName, String desc) {
        this.Calories = calories;
        this.Protein = protein;
        this.Carbs = carbs;
        this.Fat = fat;
        this.ImageName = imageName;
        this.Desc = desc;
    }

    public String getTxtProtein() {
        return Protein;
    }

    public void setTxtProtein(String protein) {
        this.Protein = protein;
    }

    public String getTxtCarbs() {
        return Carbs;
    }

    public void setTxtCarbs(String carbs) {
        this.Carbs = carbs;
    }

    public String getTxtFat() {
        return Fat;
    }

    public void setTxtFat(String fat) {
        this.Fat = fat;
    }

    public String getImageName() {
        return ImageName;
    }

    public void setImageName(String imageName) {
        this.ImageName = imageName;
    }

    public String getCalories() {
        return Calories;
    }

    public void setCalories(String calories) {
        Calories = calories;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
