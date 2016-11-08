package com.mygdx.game.data;

import java.sql.Timestamp;

/**
 * Created by octavio on 07/11/16.
 */
public class Score {
    private int score;
    private Timestamp timestamp;
    private String name;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
