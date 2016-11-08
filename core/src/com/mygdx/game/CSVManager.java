package com.mygdx.game;

import com.mygdx.game.data.Score;

import java.io.*;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
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
            PrintWriter writer = new PrintWriter(new FileOutputStream(file,true));
            writer.println(playerName+";"+score+";"+fullDate);
            writer.close();
        } catch (Exception e) {
            showMessageDialog(null, e.getMessage());
        }
    }

    public Vector<Score> loadScore(){
        Vector<Score> scoreReturn = new Vector<Score>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.csvFile));
            String line = reader.readLine();

            while(line!=null){
                scoreReturn.add(new Score(Integer.valueOf(line.split(";")[1]),Timestamp.valueOf(line.split(";")[2]),line.split(";")[0]));
                line = reader.readLine();
            }

        }catch (Exception e) {
            showMessageDialog(null, e.getMessage());
        }

        Collections.sort(scoreReturn);
        return scoreReturn;
    }
}