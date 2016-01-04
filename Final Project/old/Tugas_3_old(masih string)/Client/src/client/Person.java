/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author ardi nusawan
 */
public class Person {
    Game game;
    public int x=0;
    public int y=0;
    int velX, velY;
    private int batas_atas = 0;
    private int batas_bawah = 244;
    private int batas_kiri = 0;
    private int batas_kanan = 449;
   
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
    //set x y awal pojok kiri atas
    System.out.println(this.velX+" "+this.velY);
       x = x + velX;
       y = y + velY;
        System.out.println("x: "+ x + " " + velX);
        System.out.println("y: "+ y + " " + velY);
        
       }
}

