/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package person;

/**
 *
 * @author dhanarp
 */

import java.io.Serializable;

/**
 *
 * @author dhanarp
 */
public class Person implements Serializable{
    public String nama;
    public String chat;
    public int signal=0;
    public int x=0;
    public int y=0;
    public int velX, velY;
    private int batas_atas = 0;
    private int batas_bawah = 244;
    private int batas_kiri = 0;
    private int batas_kanan = 449;
    public String gambarOrang;
    
public Person(String input){
    this.nama=input;
    gambarOrang="dudemini-left.png";
}
   
public void batas(){
    if(x>=batas_kanan){
        x = batas_kanan;
    }
    else if(x<=batas_kiri){
        x = batas_kiri;
    }
    if(y<=batas_atas){
        y = batas_atas;
    }
    else if(y>=batas_bawah){
        y = batas_bawah;
    }
    
}

public void movement(){
       //System.out.println(this.velX+" "+this.velY);
       x = x + velX;
       y = y + velY;
       //System.out.println("x: "+ x + " " + velX);
       //System.out.println("y: "+ y + " " + velY);
        
    }
public void kiri(){
        velX = -10;
        velY = 0;
        gambarOrang="dudemini-left.png";
    }
    public void kanan(){
        velX = 10;
        velY = 0;
        gambarOrang="dudemini-right.png";
    }
    public void atas(){
        velX = 0;
        velY = -10;
        gambarOrang="dudemini-up.png";
    }
    public void bawah(){
        velX = 0;
        velY = 10;
        gambarOrang="dudemini-down.png";
    }
}