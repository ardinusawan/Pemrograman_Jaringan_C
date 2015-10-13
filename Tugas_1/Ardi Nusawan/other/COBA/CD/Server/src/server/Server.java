/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ardi
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            ServerSocket servsock = new ServerSocket(1234);
            //System.out.println("Server mau berjalan");
            Socket socket = servsock.accept();
            //System.out.println("Server berjalan ...");
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            //Process proc = Runtime.getRuntime().exec(command);
            byte[] buf = new byte[50];
            is.read(buf);
            System.out.println(new String (buf));
            
            
            //if ls
            //String ls = "ls";
            //System.out.println(new String(buf));
            //if(buf.toString()==ls)
            //{
            
            
            
            System.out.println("ini adalah " + command.getBytes());
            String command2 = "cd";
            String command3 = " /home/ardi/Download";
                    
            //String a = "A";
        
            //String b = "B";

            String c = command2 + command3;

            System.out.println(c); // prints AB

            
            Process proc = Runtime.getRuntime().exec(command);
            
            //Process proc2 = Runtime.getRuntime().exec(command);

            // Read the output

            BufferedReader reader =  
              new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String line = "";
            while((line = reader.readLine()) != null) {
            System.out.print(line + "\n");
             os.write(line.getBytes());
       
            }

            try {   
                proc.waitFor();
            } catch (InterruptedException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //}
            
           // else 
            //{
           // System.out.println("sad");
            //}
            
            
            
            
            
            
            
            
            
            
            
            os.flush();
           
            
            os.close();
            is.close();
            socket.close();
            servsock.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
