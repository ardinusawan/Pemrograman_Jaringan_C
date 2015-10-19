/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
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
            Socket socket = new Socket("localhost",1234);
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            int count = 1;
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String pesan;
            
            String message;
            do
            {
            pesan = input.readLine();
            os.write(pesan.getBytes());
            os.flush();
            
            
            byte[] buf = new byte[50];
            
            is.read(buf);
                System.out.println(new String(buf));
                message = new String(buf); 
                //System.out.println("pesan" + pesan);
                //os.flush();
            
            }while (!message.equals("QUIT"));
            //os.close();
            //is.close();
            //socket.close();
              
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
