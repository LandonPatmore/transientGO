package com.google.android.stardroid.data;

/**
 * Created by landon on 12/5/16.
 */

public enum UserData {
    INSTANCE;

    private int userExp;
    private int userLevel = 1;
    private int userScore;
    private int totalUserScore;
    private String userName;

    public int getTotalUserExp(){
        return totalUserScore;
    }

    public int getUserExp() {
        return userExp;
    }

    public void setUserExp(int userExp) {
        this.userExp = userExp;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
        totalUserScore = totalUserScore + userScore;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
