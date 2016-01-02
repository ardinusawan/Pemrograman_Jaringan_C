/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package person;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author ardi nusawan
 */
public class Bullet extends JPanel{
    Person person;
    String gambarBullet="bulletmini.png";
  
    
    public Bullet() throws IOException{
        super.setDoubleBuffered(true);
        
}


    public void sendBullet() throws IOException{
        System.out.println("metembak");
        this.repaint();
   
}
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(gambarBullet));
        Image bullet = ii.getImage();
        Graphics2D g2d=(Graphics2D)g;
        g2d.drawImage(bullet, person.x,person.y, this);
    }

}
