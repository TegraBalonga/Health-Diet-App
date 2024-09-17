package com.example.myhealthdietapp;

public class ProgressClass {

    int Num, Weight;

    public ProgressClass() { }

    public ProgressClass(int num, int weight) {
        Num = num;
        Weight = weight;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }
}
