/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ardi
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       String msg;
        try {
            byte[] buf = new byte[1000];
            Socket socket = new Socket("localhost",1234);

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            //kita kerja dari sini
            //baca config, masukin ke var LFolder
            //deklarasi string lfolder
            String LFolder = "/Users/dhanarp/random",inputString;
            int inputInt;
            Scanner s= new Scanner(System.in);

            for(;;)
            {
                System.out.println("Selamat datang di serverku");
                System.out.println("pwd = "+ LFolder);
                System.out.println("Command:");
                System.out.println("1. List Directory");
                System.out.println("2. Create new Directory");
                System.out.println("3. Change Directory"); 
                System.out.println("else = Exit Program");
                inputInt=s.nextInt();                              //cara input int
                //LFolder=s.next();                               //cara input string

                if(inputInt==1)
                {
                    //kirim integer 1, lalu string blank
                    msg="1"+LFolder;
                    os.write(msg.getBytes());
                }
                else if(inputInt==2)
                {
                    System.out.println("Masukkan nama directory baru= ");
                    inputString=s.next();
                    LFolder = LFolder + inputString;
                    msg="2"+LFolder;
                    os.write(msg.getBytes());
                    //kirim integer 2, sama LFolder
                }
                else if(inputInt==3)
                {
                    System.out.println("Masukkan nama directory yang dituju (full path)");
                    System.out.println("Default= /Users/dhanarp/random/");
                    inputString=s.next();
                    LFolder = inputString;    //cuma ganti LFolder

                }
                else
                {
                    msg="0";
                    os.write(msg.getBytes());
                    break;
                }    
                //receive data
                int len;
                while(true)
                {
                    buf = new byte[1000];
                    len = is.read(buf);
                    if(len == -1)
                    {
                        break;
                    }
                    System.out.print(new String(buf));
                }
                //proses
                //print
            }


            //kita kerja sampai sini
            os.close();
            is.close();


        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
