/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snek;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 *
 * @author crazyiot
 */

public class Board extends JFrame { 

    
    private int boardW = 20 * 40; //ancho
    private int boardH = 20 * 40; //largo
    
   
    private int speed = 80; //Tiempo entre ticks, entre mas bajo mas rapido el juego
    
  

    public Board() {
        
        this.setSize(boardW, boardH);
        Game game = new Game(); //Inicia el juego
        addKeyListener(game); //agrega funcion para leer que estripa el jugador
        add(game);
        setVisible(true); //hace el cuadro adonde esta el jugador y el tablero visible
        Timer t = new Timer(speed, game); //timer para iniciar el juego, determina velocidad del jugador
       
        t.start();
        
        
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                t.stop();
                System.exit(0);
            }
        });
         
    }

   
}
