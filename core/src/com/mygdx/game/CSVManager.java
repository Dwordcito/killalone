package com.mygdx.game;

import java.io.PrintWriter;
import java.sql.Timestamp;

import static javax.swing.JOptionPane.showMessageDialog;

public class CSVManager {
    private String csvFile;

    public CSVManager(){
        this.csvFile = "~/score.csv";
    }

    public void saveScore(int score) {
        try{
            java.util.Date date= new java.util.Date();
            Timestamp fullDate = new Timestamp(date.getTime());
            PrintWriter writer = new PrintWriter(this.csvFile, "UTF-8");
            writer.println(score+";"+fullDate);
            writer.close();
        } catch (Exception e) {
            showMessageDialog(null, e.getMessage());
        }
    }
}