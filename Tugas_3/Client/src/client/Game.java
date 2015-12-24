<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author ardi nusawan
 */
public class Game extends JPanel implements KeyListener, ActionListener{

    private Image dude;
    Person person;
    private Timer t = new Timer(7, this);
    //Scanner s=new Scanner(System.in);
    
    public Game(){
        super.setDoubleBuffered(true);
        
        person = new Person();
        person.x = person.y = 0;
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
//        JComboBox<String> comboBox = new JComboBox<String>();
//        comboBox.addItem("game1");
//        this.add(comboBox);
//        comboBox.setFocusable(false);
    }

//    @Override
//    public void paint(Graphics g){
//        super.paint(g);
//        ImageIcon ii = new ImageIcon(this.getClass().getResource("dudemini.png"));
//        dude = ii.getImage();
//        Graphics2D g2d =(Graphics2D)g;
//        System.out.println("Repaint " + person.x + " " + velX);
//        g2d.drawImage(dude,person.x,person.y, this);
//    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        ImageIcon ii = new ImageIcon(this.getClass().getResource("dudemini.png"));
        dude = ii.getImage();
        Graphics2D g2d =(Graphics2D)g;
//        System.out.println("Repaint " + person.x + " " + velX);
        g2d.drawImage(dude,person.x,person.y, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void kiri(){
        person.velX = -1;
        person.velY = 0;
    }
    public void kanan(){
        person.velX = 1;
        person.velY = 0;
    }
    public void atas(){
        person.velX = 0;
        person.velY = -1;
    }
    public void bawah(){
        person.velX = 0;
        person.velY = 1;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int c = e.getKeyCode();
//        System.out.println(c);
        if(c==KeyEvent.VK_LEFT){
            kiri();
            //gambar();
        }
        else if(c==KeyEvent.VK_RIGHT){
            kanan();
            //gambar();
            
        }
        else if(c==KeyEvent.VK_UP){
            atas();
            //gambar();
           
        }
        else if(c==KeyEvent.VK_DOWN){
            bawah();
            //gambar();
            
        }
       
//        repaint();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.out.println("this is action performed");
        //if(this.velX==-1) System.out.println("kiri"); 
        //System.out.println("not lol :(");
        
       
        
    }
    
    /*public void gambar(){
        System.out.println(this.velX+" "+velY);
        this.person.x = person.x + 1;
        this.person.y = person.y + 1;
        System.out.println("x: "+ person.x + " " + velX);
        System.out.println("y: "+ person.y + " " + velY);
        
    }*/

}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author ardi nusawan
 */
public class Game extends JPanel implements KeyListener, ActionListener{

    private Image dude;
    Person person;
    private int velX, velY;
    private Timer t = new Timer(7, this);
    String gambarOrang="pleaseLogin.jpg";
    
    //Scanner s=new Scanner(System.in);
    
    public Game(){
        super.setDoubleBuffered(true);
        
        person = new Person();
        person.x = person.y = 100;
        this.velX = this.velY = 0;
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
//        JComboBox<String> comboBox = new JComboBox<String>();
//        comboBox.addItem("game1");
//        this.add(comboBox);
//        comboBox.setFocusable(false);
    }
    
    public void gameLogin()
    {
        this.gambarOrang="dudemini-left.png";
        this.repaint();
        
    }

//    @Override
//    public void paint(Graphics g){
//        super.paint(g);
//        ImageIcon ii = new ImageIcon(this.getClass().getResource("dudemini.png"));
//        dude = ii.getImage();
//        Graphics2D g2d =(Graphics2D)g;
//        System.out.println("Repaint " + person.x + " " + velX);
//        g2d.drawImage(dude,person.x,person.y, this);
//    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(gambarOrang));
        dude = ii.getImage();
        Graphics2D g2d =(Graphics2D)g;
//        System.out.println("Repaint " + person.x + " " + velX);
        g2d.drawImage(dude,person.x,person.y, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void kiri(){
        this.velX = -10;
        this.velY = 0;
        gambarOrang="dudemini-left.png";
    }
    public void kanan(){
        this.velX = 10;
        this.velY = 0;
        gambarOrang="dudemini-right.png";
    }
    public void atas(){
        this.velX = 0;
        this.velY = -10;
        gambarOrang="dudemini-up.png";
    }
    public void bawah(){
        this.velX = 0;
        this.velY = 10;
        gambarOrang="dudemini-down.png";
    }

    @Override
    public void keyPressed(KeyEvent e) {
 
//        repaint();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
    public void gameMovement(){
        System.out.println(this.velX+" "+this.velY);
        this.person.x = this.person.x + this.velX;
        this.person.y = this.person.y + this.velY;
        System.out.println("x: "+ this.person.x + " " + this.velX);
        System.out.println("y: "+ this.person.y + " " + this.velY);
        repaint();
    }
    
    /*public void gambar(){
        System.out.println(this.velX+" "+velY);
        this.person.x = person.x + 1;
        this.person.y = person.y + 1;
        System.out.println("x: "+ person.x + " " + velX);
        System.out.println("y: "+ person.y + " " + velY);
        
    }*/

}
>>>>>>> 5c9fecac39898bdd43751bdb24280f2f7681139c
