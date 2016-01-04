
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
import javax.swing.ImageIcon;
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
    String gambarOrang="pleaseLogin.png";
    
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
                ImageIcon ii = new ImageIcon(this.getClass().getResource(iter.gambarOrang));
                Image dude = ii.getImage();
                Graphics2D g2d =(Graphics2D)g;
                g2d.drawString(iter.nama, iter.x, iter.y);
                g2d.drawImage(dude,iter.x,iter.y, this);
                
                if(signal_shoot==3){
                    if(menghadap.endsWith("-right.png")){
                        ImageIcon iii = new ImageIcon(this.getClass().getResource("bulletmini_right.png"));
                        Image bullet = iii.getImage();
                        Graphics2D g2dd =(Graphics2D)g;
                        g2dd.drawImage(bullet,posX_shoot+1,posY_shoot, this);
                        if(posX_shoot<=person.batas_kanan+50){
                            posX_shoot++;
                        }
                    }
                    else if(menghadap.endsWith("-left.png")){
                        ImageIcon iii = new ImageIcon(this.getClass().getResource("bulletmini_left.png"));
                        Image bullet = iii.getImage();
                        Graphics2D g2dd =(Graphics2D)g;
                        g2dd.drawImage(bullet,posX_shoot-1,posY_shoot, this);
                        
                        if(posX_shoot>=person.batas_kiri-50){
                            posX_shoot--;
                        }
                    }

                    else if(menghadap.endsWith("-up.png")){
                        ImageIcon iii = new ImageIcon(this.getClass().getResource("bulletmini_up.png"));
                        Image bullet = iii.getImage();
                        Graphics2D g2dd =(Graphics2D)g;
                        g2dd.drawImage(bullet,posX_shoot,posY_shoot-1, this);
                        if(posY_shoot>=person.batas_atas-50){
                            posY_shoot--;
                        }
                    }
                    
                    else if(menghadap.endsWith("-down.png")){
                        ImageIcon iii = new ImageIcon(this.getClass().getResource("bulletmini_down.png"));
                        Image bullet = iii.getImage();
                        Graphics2D g2dd =(Graphics2D)g;
                        g2dd.drawImage(bullet,posX_shoot,posY_shoot+1, this);
                        if(posY_shoot<=person.batas_bawah+80){
                            posY_shoot++;
                        }
                    }
                    if(posX_shoot==gui.me.x && posY_shoot==gui.me.y) {
                        System.out.println(penembak + " menembak " + gui.me.nama + " !!!!!!");
                        gui.tambahScore(penembak);

                        try {
                            gui.Die(gui.me);
                        } catch (IOException ex) {
                            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } repaint();
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
    @Override
    public void actionPerformed(ActionEvent e) {    
    }
    
}

