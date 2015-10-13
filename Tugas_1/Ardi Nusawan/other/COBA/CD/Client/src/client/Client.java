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
       
        try {
            byte[] buf = new byte[10];
            byte[] buf2 = new byte[1000];
            Socket socket = new Socket("localhost",1234);
            
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            //        
            System.out.print("masukkan perintah : ");
    
      	   String sWhatever;
     
            Scanner scanIn = new Scanner(System.in);
            sWhatever = scanIn.nextLine();

            scanIn.close();            
            System.out.println("Perintah :" + sWhatever);

            //
            
            os.write(sWhatever.getBytes());
            os.flush();
            
            
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
                System.out.print("\n");
            }
            
            os.close();
            is.close();
            
            
            //Scanner sc = new Scanner(System.in);
            //int i= sc.nextInt();
            //String input = sc.nextLine();
            //System.out.println("Message is " + input);
            
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
