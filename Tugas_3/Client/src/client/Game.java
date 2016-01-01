
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
import person.Person;

/**
 *
 * @author ardi nusawan
 */
public class Game extends JPanel implements KeyListener, ActionListener{

    //private Image dude;
    Person person;
    GUIClient gui;
    private Timer t = new Timer(7, this);
    public boolean loggedIn=false;
    String gambarOrang="pleaseLogin.jpg";
    
    //Scanner s=new Scanner(System.in);
    
    public Game(){
        super.setDoubleBuffered(true);
        
        person = new Person("");
        t.start();
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
    }
    
    public void gameLogin(GUIClient gui)
    {
        loggedIn=true;
        this.gui=gui;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(!loggedIn){
            ImageIcon ii = new ImageIcon(this.getClass().getResource(gambarOrang));
            Image dude = ii.getImage();
            Graphics2D g2d =(Graphics2D)g;
            g2d.drawImage(dude,person.x,person.y, this);
        }
        else{
            for(Person iter:gui.player){
                //System.out.println(iter.x);
                ImageIcon ii = new ImageIcon(this.getClass().getResource(iter.gambarOrang));
                Image dude = ii.getImage();
                Graphics2D g2d =(Graphics2D)g;
                g2d.drawString(iter.x + " " + iter.y, iter.x, iter.y);
                g2d.drawImage(dude,iter.x,iter.y, this);
            }
            
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
//    public void gameMovement(){
//        
//    }
    
    /*public void gambar(){
        System.out.println(this.velX+" "+velY);
        this.person.x = person.x + 1;
        this.person.y = person.y + 1;
        System.out.println("x: "+ person.x + " " + velX);
        System.out.println("y: "+ person.y + " " + velY);
        
    }*/

}

