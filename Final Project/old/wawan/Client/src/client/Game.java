
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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    int signal_shoot =0;
    int posX_shoot = 0;
    int posY_shoot = 0;
    String menghadap = "";
    String penembak = "";
    
    Person person;
    GUIClient2 gui;
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
    
    public void gameLogin(GUIClient2 gui)
    {
        loggedIn=true;
        this.gui=gui;
        this.repaint();
    }
    
    public void Tembak(String nama, int posX, int posY, String menghadap,int signal){
       System.out.println("penembak : " + nama + " pos X : " + posX+ " pos Y : " + posY + " menghadap ke : " + menghadap + "signal_shoot : " + signal);
        signal_shoot = signal;
        penembak = nama;
        posX_shoot = posX;
        posY_shoot = posY;
        this.menghadap = menghadap;
//        this.repaint();
//       ImageIcon iii = new ImageIcon(this.getClass().getResource("bulletmini.png"));
//       Image bullet = iii.getImage();
//       Graphics2D g2d =(Graphics2D)g;
//       g2d.drawImage(bullet,posX,posY, this);
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
                g2d.drawString(iter.nama + " " + iter.x + " " + iter.y, iter.x, iter.y);
//                g2d.drawString(iter.x + " " + iter.y, iter.x +1, iter.y);
                g2d.drawImage(dude,iter.x,iter.y, this);
//                iter.signal = signal_shoot;
//                System.out.println("Signal = " + iter.signal + " Signalku : " + signal_shoot);
                
                if(signal_shoot==3){
                    if(menghadap.equalsIgnoreCase("dudemini-right.png")){
                        ImageIcon iii = new ImageIcon(this.getClass().getResource("bulletmini.png"));
                        Image bullet = iii.getImage();
                        Graphics2D g2dd =(Graphics2D)g;
                        g2d.drawString(posX_shoot + " " + posY_shoot, posX_shoot, posY_shoot);
                        g2dd.drawImage(bullet,posX_shoot,posY_shoot, this);
                        if((posX_shoot!=iter.x && posY_shoot!=iter.y ) && posX_shoot<=person.batas_kanan+50){
                            posX_shoot++;
                        }
                        else if(posX_shoot==iter.x && posY_shoot==iter.y && !iter.nama.contentEquals(penembak)) {
                            
                            System.out.println(iter.nama + " kena!");
                            try {
                                gui.Die(iter);
                            } catch (IOException ex) {
                                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }repaint();
                    }
                    else if(menghadap.equalsIgnoreCase("dudemini-left.png")){
                        ImageIcon iii = new ImageIcon(this.getClass().getResource("bulletmini.png"));
                        Image bullet = iii.getImage();
                        Graphics2D g2dd =(Graphics2D)g;
                        g2d.drawString(posX_shoot + " " + posY_shoot, posX_shoot, posY_shoot);
                        g2dd.drawImage(bullet,posX_shoot,posY_shoot, this);
                        if((posX_shoot!=iter.x && posY_shoot!=iter.y ) && posX_shoot>=person.batas_kiri-50){
                            posX_shoot--;
                        }else if(posX_shoot==iter.x && posY_shoot==iter.y && !iter.nama.contentEquals(penembak)) {
                            
                            System.out.println(iter.nama + " kena!");
                            try {
                                gui.Die(iter);
                            } catch (IOException ex) {
                                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }repaint();
                        
                    }

                    else if(menghadap.equalsIgnoreCase("dudemini-up.png")){
                        ImageIcon iii = new ImageIcon(this.getClass().getResource("bulletmini.png"));
                        Image bullet = iii.getImage();
                        Graphics2D g2dd =(Graphics2D)g;
                        g2d.drawString(posX_shoot + " " + posY_shoot, posX_shoot, posY_shoot);
                        g2dd.drawImage(bullet,posX_shoot,posY_shoot, this);
                        if((posX_shoot!=iter.x && posY_shoot!=iter.y ) &&posY_shoot>=person.batas_atas-50){
                            posY_shoot--;
                        }else if(posX_shoot==iter.x && posY_shoot==iter.y && !iter.nama.contentEquals(penembak)) {
                            
                            System.out.println(iter.nama + " kena!");
                            try {
                                gui.Die(iter);
                            } catch (IOException ex) {
                                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }repaint();
                        
                    }
                    
                    else if(menghadap.equalsIgnoreCase("dudemini-down.png")){
                        ImageIcon iii = new ImageIcon(this.getClass().getResource("bulletmini.png"));
                        Image bullet = iii.getImage();
                        Graphics2D g2dd =(Graphics2D)g;
                        g2d.drawString(posX_shoot + " " + posY_shoot, posX_shoot, posY_shoot);
                        g2dd.drawImage(bullet,posX_shoot,posY_shoot, this);
                        if((posX_shoot!=iter.x && posY_shoot!=iter.y ) &&posY_shoot<=person.batas_bawah+80){
                            posY_shoot++;
                        }else if(posX_shoot==iter.x && posY_shoot==iter.y && !iter.nama.contentEquals(penembak)) {
                            
                            System.out.println(iter.nama + " kena!");
                            try {
                                gui.Die(iter);
                            } catch (IOException ex) {
                                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }repaint();
                    }
                }
//                    signal_shoot = 0;
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

//    void gameLogin(GUIClient2 gui) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

}

