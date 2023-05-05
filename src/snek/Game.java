/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snek;

import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.util.Random;


public class Game extends JPanel implements KeyListener, ActionListener {

    Random rand = new Random();
    Scanner scan = new Scanner(System.in);
    CSVwriter writer = new CSVwriter(); //writer para los puntos
    CSVreader reader = new CSVreader(); //reader para ense;ar puntos en highscores

    private String playerName = JOptionPane.showInputDialog("Ingrese su nombre"); //prompt para pedirle al jugador nombre
    private int boardW = 20 * 40; //ancho
    private int boardH = 20 * 40; //largo
    private int pixel_total = 800; // cuantos pixeles (el area de juego)
    private int pixel = 20;  //Tama;o por celda o "pixel"

    private int playerSize = 3; //Tama;o del jugador, inicia en 3. Esto se va a usar para cuando coma.
    private int[] playerX = new int[pixel_total]; //un array del tama;o de todo el area. (porque es hasta adonde puede crecer el jugador) es como una lista vacia que se llena cada vez que crece.
    private int[] playerY = new int[pixel_total]; //otro array del mismo tama;o (para coordenada y)

    private int scoreX; //coordenada x de la fruta
    private int scoreY;//coordenada y de la fruta

    //IMAGENES
    private Image fruit; //grafica para la fruta
    private int fruitChoice; //int para seleccionar diferentes frutas
    private Image snakeBody; //grafica para el cuerpo
    private Image snakeHead; //grafica para la cabeza
    
    private boolean inGame = true; //marca que el jugador esta en juego, asi se sabe cuando parar de refresh y poner en false cuando se pierde

    private int keyPressed = KeyEvent.VK_DOWN; //la direccion hacia adonde va a iniciar
    private int lastkeyPressed; 
    private int direction = 0; //para impedir chocar contra uno mismo

    private boolean isSaving = true; //para que solo salve una vez

    Game() {

        makeScore();
        setBackground(Color.black);

        //de adonde inicia
        lastkeyPressed = KeyEvent.VK_DOWN;
        for (int i = 0; i < playerSize; i++) {
            playerX[i] = 100;
            playerY[i] = 20 - (i * pixel);

        }

    }

    public void keyPressed(KeyEvent e) {
        

        keyPressed = e.getKeyCode();
        if (keyPressed == KeyEvent.VK_A) {
            
            Board t = new Board();

            
        }
        //key code lo que hace es que usa una funcion diferente que depende que letra usa.(de una lista https://docs.oracle.com/javafx/2/api/javafx/scene/input/KeyCode.html)
        
        //estos if sirven junto a movement para impedir que el jugador pueda doblar adentro de si mismo. determina en que va a quedar laskeyPressed
        if (lastkeyPressed == KeyEvent.VK_DOWN && keyPressed == KeyEvent.VK_UP) {
            lastkeyPressed = KeyEvent.VK_DOWN;

        } else if (lastkeyPressed == KeyEvent.VK_UP && keyPressed == KeyEvent.VK_DOWN) {
            lastkeyPressed = KeyEvent.VK_UP;

        } else if (lastkeyPressed == KeyEvent.VK_RIGHT && keyPressed == KeyEvent.VK_LEFT) {
            lastkeyPressed = KeyEvent.VK_RIGHT;
        } else if (lastkeyPressed == KeyEvent.VK_LEFT && keyPressed == KeyEvent.VK_RIGHT) {
            lastkeyPressed = KeyEvent.VK_LEFT;
        } else {
            lastkeyPressed = keyPressed;
        }

    }

    public void paintComponent(Graphics graphic) {

        super.paintComponent(graphic);

        if (inGame) {

            loadImages(); //carga las imagenes correctas 
            
            graphic.drawImage(fruit, scoreX, scoreY, this); //dibujo de fruta, llama fruit y loadImages carga la correcta.
           
            //PINTA CULEBRA
            for (int i = 0; i < playerSize; i++) { // para todo el tama;o del jugador

                if (i == 0) { //si no es posicion 0 (cabeza)
                    graphic.drawImage(snakeHead, playerX[i], playerY[i], this); // <--la cabeza
                
                } else { //si es cualquier cosa q no sea posicion 0,
                    graphic.drawImage(snakeBody, playerX[i], playerY[i], this); //<--cuerpo
                }
                graphic.setColor(Color.green);
                graphic.drawString("Puntos:" + playerSize, 700, 700);

                //pintar coordenadas del personaje, ancho del objeto a pintar(pixel), largo del objeto a pintar(pixel)
            }

            //PINTAR MANZANA
        } else if (!inGame) {

            //PANTALLA DE MUERTE 
            graphic.setColor(Color.white);
            graphic.setFont(new Font("Sans serif", Font.BOLD, 30));
            graphic.drawString("OOF! PERDIO!", 285, 50);
            graphic.drawString(playerName + "! usted ha ganado " + playerSize + " puntos!", 175, 100);
            graphic.drawString("Presione 'A' para reiniciar ", 220, 150);

            //PARTE DE SALVAR LOS SCORES
            FileWriter fileWriter = null; //para definir fileWriter e iniciarlo
            if (!inGame && isSaving) {
                writer.CSVaddscore(playerName, playerSize);

                isSaving = false;
            }
            //READER
            //llama al reader para determinar los valores de highscores.
            reader.Reader();

            graphic.setColor(Color.cyan);
            graphic.drawString("~~~~~~~~~~~~~~~~~HIGHSCORES~~~~~~~~~~~~~~~~~", -5, 300);
            graphic.setFont(new Font("Sans serif", Font.ITALIC, 15));
            graphic.drawString("Nombre:Puntos", 350, 330);

            graphic.setFont(new Font("Sans serif", Font.TYPE1_FONT, 50));
            graphic.setColor(Color.green);
            graphic.drawString("#1." + reader.getHighScore1(), 50, 450); //
            
            graphic.setColor(Color.yellow);
            graphic.drawString("#2." + reader.getHighScore2(), 50, 550); //Highscore2
            
            graphic.setColor(Color.red);
            graphic.drawString("#3." + reader.getHighScore3(), 50, 650);

        }
    }

        private void movement() {

            for (int i = playerSize; i > 0; i--) {  //empieza desde atras (se mueve de [playersize] a la nueva pos [0]) 
                playerX[i] = playerX[(i - 1)];      
            playerY[i] = playerY[(i - 1)];      //se repite para todas las posiciones una vez por movimiento
        }

        switch (keyPressed) {                   //controles y movimiento de cabeza, agrega "pixel" a posicion[0].
            case KeyEvent.VK_DOWN:
                if (lastkeyPressed == KeyEvent.VK_UP && keyPressed == KeyEvent.VK_DOWN) {
                    playerY[0] -= pixel;
                    direction = 2;
                    break;
                } else {
                    playerY[0] += pixel;
                    direction = 0;
                    break;
                }
            case KeyEvent.VK_UP:
                if (lastkeyPressed == KeyEvent.VK_DOWN && keyPressed == KeyEvent.VK_UP) {

                    playerY[0] += pixel;
                    direction = 0;
                    break;
                } else {
                    playerY[0] -= pixel;
                    direction = 2;
                    break;
                }
            case KeyEvent.VK_LEFT:
                if (lastkeyPressed == KeyEvent.VK_RIGHT && keyPressed == KeyEvent.VK_LEFT) {
                    playerX[0] += pixel;
                    direction = 1;
                    break;
                } else {
                    playerX[0] -= pixel;
                    direction = 3;
                    break;
                }
            case KeyEvent.VK_RIGHT:
                if (lastkeyPressed == KeyEvent.VK_LEFT && keyPressed == KeyEvent.VK_RIGHT) {
                    playerX[0] -= pixel;
                    direction = 3;
                    break;
                } else {
                    playerX[0] += pixel;
                    direction = 1;
                    break;
                }
        }

    }

    public void actionPerformed(ActionEvent e) {  //constantemente va a estar updating (despues de cada moviemiento)

        
        isValidMove();//funcion para checkear si choco
        movement();//viendo si el usuario ha cambiado la direccion
        repaint(); //refresh adonde esta todo (repinta todo y usa las coordenadas nuevas)

    }

    public void keyTyped(KeyEvent e) { //obligatorio poner aunque no haga nada el metodo (por lo de event). //se usaria si usaramos key typed en vez de keycode

    }

    public void keyReleased(KeyEvent e) {//obligatorio poner aunque no haga nada el metodo. (se usa keycode no keytyped ni keyreleased.)
    }

    public void isValidMove() { 
        if (playerX[0] >= boardW - pixel || playerX[0] < 0) { //borde con los lados
            inGame = false;

        } else if (playerY[0] >= boardH - pixel || playerY[0] < 0) {  //borde con arriba y abajo
            inGame = false;

        } else if ((playerX[0] == scoreX) && (playerY[0] == scoreY)) { //si pasa encima de una comida
            playerSize++;
            makeScore();

        }
        for (int i = 1; i < playerX.length; i++) { //revisa si choca contra el mismo
            if (playerX[0] == playerX[i] && playerY[0] == playerY[i]) {
                inGame = false;
            }
        }

    }

    public void makeScore() { 
        scoreX = (rand.nextInt(37) * pixel) + pixel;
        scoreY = (rand.nextInt(37) * pixel) + pixel; //+pixel es para que no salga en la pura esquina
        fruitChoice = (rand.nextInt(4)); //para determinar cual grafica cargar en load images
    }

    public boolean getInGame() {
        return inGame;
    }

    public boolean getisSaving() {
        return isSaving;
    }

    private void loadImages() {
        //load image de snake
        //basado en otras variables determina cual grafica cargar. uso get class.get class loader y getresource para que busque en el "Sprites"
       
        //CUERPO
        if (keyPressed == KeyEvent.VK_DOWN || keyPressed == KeyEvent.VK_UP) {
            ImageIcon SNAKEBODY = new ImageIcon(getClass().getClassLoader().getResource("\\Sprites\\sbodyVertical.png"));
            snakeBody = SNAKEBODY.getImage();
        } else if (keyPressed == KeyEvent.VK_LEFT || keyPressed == KeyEvent.VK_RIGHT) {
            ImageIcon SNAKEBODY = new ImageIcon(getClass().getClassLoader().getResource("\\Sprites\\sbodyHorizontal.png"));
            snakeBody = SNAKEBODY.getImage();
        }

        //CABEZA
        if (direction == 0) {
            ImageIcon SNAKEHEAD = new ImageIcon(getClass().getClassLoader().getResource("\\Sprites\\sheadDOWN.png"));
            snakeHead = SNAKEHEAD.getImage();
        } else if (direction == 3) {
            ImageIcon SNAKEHEAD = new ImageIcon(getClass().getClassLoader().getResource("\\Sprites\\sheadLEFT.png"));
            snakeHead = SNAKEHEAD.getImage();
        } else if (direction == 1) {
            ImageIcon SNAKEHEAD = new ImageIcon(getClass().getClassLoader().getResource("\\Sprites\\sheadRIGHT.png"));
            snakeHead = SNAKEHEAD.getImage();
        } else if (direction == 2) {
            ImageIcon SNAKEHEAD = new ImageIcon(getClass().getClassLoader().getResource("\\Sprites\\sheadUP.png"));
            snakeHead = SNAKEHEAD.getImage();
        }

        //DETERMINA CUAL GRAFICA VA A USAR LAS FRUTAS
        // CAMBIAR PLACEHOLDERS
        if (fruitChoice == 0) {
            ImageIcon FRUITS = new ImageIcon(getClass().getClassLoader().getResource("\\Sprites\\apple.png"));
            fruit = FRUITS.getImage();

        } else if (fruitChoice == 1) {
            ImageIcon FRUITS = new ImageIcon(getClass().getClassLoader().getResource("\\Sprites\\banana.png"));
            fruit = FRUITS.getImage();
        } else if (fruitChoice == 2) {
            ImageIcon FRUITS = new ImageIcon(getClass().getClassLoader().getResource("\\Sprites\\blackberry.png"));
            fruit = FRUITS.getImage();
        } else if (fruitChoice == 3) {
            ImageIcon FRUITS = new ImageIcon(getClass().getClassLoader().getResource("\\Sprites\\BK.png"));
            fruit = FRUITS.getImage();
        }

        
        
    }
    
}
