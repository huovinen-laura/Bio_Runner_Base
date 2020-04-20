package com.mygdx.game.screens;

import com.mygdx.game.Button;

public class HighScoreEntry {
    private String name;
    private int score;

    public HighScoreEntry() {
    }

    public HighScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    // Getters and setters ...
}