package com.google.android.stardroid.data;

/**
 * Created by landon on 12/5/16.
 */

public enum UserData {
    INSTANCE;

    private int userExp = 0;
    private int userLevel = 0;
    private int userScore = 0;


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
    }
}
