package com.mygdx.game.data;

import java.sql.Timestamp;

/**
 * Created by octavio on 07/11/16.
 */
public class Score implements Comparable<Score> {
    private int score;
    private Timestamp timestamp;
    private String name;

    public Score(int score, Timestamp timestamp, String name){
        this.score = score;
        this.timestamp = timestamp;
        this.name = name;
    }

    @Override public int compareTo(Score score)
    {
        if(this.getScore() > score.getScore()){
            return -1;
        } else if(this.getScore() == score.getScore()){
            return 0;
        } else {
            return 1;
        }
    }

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
