<<<<<<< HEAD
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
    public int velX=0, velY=0;
    


    public int x;
    public int y;
    public int batas_kiri = 0;
    public int batas_kanan = 449;
    public int batas_atas = 0;
    public int batas_bawah = 244;
    public void batas(){
        if(x>=batas_kanan){
//            System.out.print("sanpe di batas kanan");
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
    
    public void move(){
    System.out.println(velX+" "+velY);
        x = x + velX;
        y = y +velY;
        System.out.println("x: "+ x + " " + velX);
        System.out.println("y: "+ y + " " + velY);
}
}
=======
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
    public int x;
    public int y;
}
>>>>>>> 5c9fecac39898bdd43751bdb24280f2f7681139c
