/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package person;
import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author dhanarp
 */
public class Person implements Serializable{
    public String nama;
    public String chat;
    public int signal=0;
    public int x=getRandomNumberInRange(0, 1)*490;
    public int y=getRandomNumberInRange(0, 1)*250;
    public int velX, velY;
    public int batas_atas = 0;
    public int batas_bawah = 250;
    public int batas_kiri = 0;
    public int batas_kanan = 490;
    public String randomOrang = getRandomAvatar();
    public String gambarOrang;
    public boolean readyToFire = true, shoot = false;
    public int health = 3;
    
    public int score;
//    Bullet B;
    
    private static int getRandomNumberInRange(int min, int max) {

            if (min >= max) {
                    throw new IllegalArgumentException("max must be greater than min");
            }

            Random r = new Random();
            return r.nextInt((max - min) + 1) + min;
    }    
    
    private static String getRandomAvatar() {
            int a = getRandomNumberInRange(0,4);
            if (a == 0) 
                return "dudemini";
            else if (a == 1) 
                return "dudeorange";
            else if (a == 2) 
                return "dudegreen";
            else if (a == 3) 
                return "dudered";
            else 
                return "dudeminia";
    }    
    
    public Person(String input){
        this.nama=input;
        gambarOrang=randomOrang+"-left.png";
    }

    public void batas(){
        if(x>=batas_kanan){
            x = batas_kiri+10;
        }
        else if(x<=batas_kiri){
            x = batas_kanan-10;
        }
        if(y<=batas_atas){
            y = batas_bawah-10;
        }
        else if(y>=batas_bawah){
            y = batas_atas+10;
        }

    }

    public void movement(){
       x = x + velX;
       y = y + velY;
    }

    public void kiri(){
        velX = -10;
        velY = 0;
        gambarOrang=randomOrang+"-left.png";
    }
    public void kanan(){
        velX = 10;
        velY = 0;
        gambarOrang=randomOrang+"-right.png";
    }
    public void atas(){
        velX = 0;
        velY = -10;
        gambarOrang=randomOrang+"-up.png";
    }
    public void bawah(){
        velX = 0;
        velY = 10;
        gambarOrang=randomOrang+"-down.png";
    }
}