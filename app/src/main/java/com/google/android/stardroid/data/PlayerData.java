package com.google.android.stardroid.data;

/**
 * Created by landon on 12/4/16.
 */

public enum PlayerData {
    INSTANCE;

    private int playerLevel = 7;
    private int playerScore = 496;


    public int getPlayerLevel() {
        return playerLevel;
    }

    public int getPlayerScore() {
        return playerScore;
    }
}
