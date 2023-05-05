/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snek;


import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author crazyiot
 */
public class CSVreader {

    String highscore1 = "3";
    String highscore2 = "2";   //top 3 scores default por si no encuentra ninguno
    String highscore3 = "1";
    int ScoreNumber1 = 3;       //valores de top 3 scores que encuentre.
    int ScoreNumber2 = 2;
    int ScoreNumber3 = 1;
    
    //camino al file csv
    String csvFile = "F:\\desktop 2019 going 2020\\SNEK\\Scores.csv";

    String line = "";
    String cvsSplitBy = ",";


    BufferedReader br = null;

    public void Reader() {

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] info = line.split(cvsSplitBy);
                String tempName = info[0];
                String tempScore = info[1];

                if (Integer.parseInt(tempScore) > ScoreNumber1) {
                    ScoreNumber1 = Integer.parseInt(tempScore);
                    highscore1 = tempName + ":" + tempScore;
                } else if (Integer.parseInt(tempScore) > ScoreNumber2 && ScoreNumber1 > Integer.parseInt(tempScore)) {
                    highscore2 = tempName + ":" + tempScore;
                    ScoreNumber2 = Integer.parseInt(tempScore);
                } else if (Integer.parseInt(tempScore) > ScoreNumber3 && ScoreNumber2 > Integer.parseInt(tempScore)) {
                    ScoreNumber3 = Integer.parseInt(tempScore);
                    highscore3 = tempName + ":" + tempScore;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

public String getHighScore1(){
    return highscore1;
}
public String getHighScore2(){
    return highscore2;
}
public String getHighScore3(){
    return highscore3;
}
}
