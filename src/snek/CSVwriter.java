/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snek;

import java.io.FileWriter;




/**
 *
 * @author crazyiot
 */
public class CSVwriter {

    FileWriter fileWriter = null;

    
   

   

    public void CSVwriter() {

    }

    public void CSVaddscore(String a, int b) { //va a tomar como argumento playername (string), y los puntos del jugador (int)

        try {
            fileWriter = new FileWriter("Scores.csv", true);
            //NO SE GUARDA SI ESTA ABIERTO EL FILE
            fileWriter.append(a);
            fileWriter.append(",");
            fileWriter.append(String.valueOf(b));
            fileWriter.append(System.lineSeparator());                
            fileWriter.flush();
            fileWriter.close();

        } catch (Exception e) {
            System.out.print("ERROR GUARDANDO PUNTOS");
        }
    }

}
