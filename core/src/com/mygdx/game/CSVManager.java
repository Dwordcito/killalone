package com.mygdx.game;

import com.mygdx.game.data.Score;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Vector;

import static javax.swing.JOptionPane.showMessageDialog;

public class CSVManager {
    private String csvFile;

    public CSVManager(){
        this.csvFile = "score.csv";
    }

    public void saveScore(int score, String playerName) {
        try{
            File file = new File(csvFile);
            if(!file.exists()){
                file.createNewFile();
            }
            java.util.Date date= new java.util.Date();
            Timestamp fullDate = new Timestamp(date.getTime());
            PrintWriter writer = new PrintWriter(file, "UTF-8");
            writer.println(playerName+";"+score+";"+fullDate);
            writer.close();
        } catch (Exception e) {
            showMessageDialog(null, e.getMessage());
        }
    }

    public Vector<Score> loadScore(){
        
    }
}