/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ardi
 */
public class Server {
    //fungsi2
    public void lordMKDIR(String input)
    {
        File theDir = new File(input);
        if (!theDir.exists()) 
        {
            System.out.println("creating directory: " + input);           //gaperlu
            boolean result = false;

            try{
                theDir.mkdir();
                result = true;
            } 
            catch(SecurityException se){
                //handle it
            }        
            if(result) {    
                System.out.println("DIR created");                      //print ke file output
            }
        }
    }
    public static void lordLS(final File folder) 
    {
        for (final File fileEntry : folder.listFiles()) 
        {
            System.out.println(fileEntry.getName());                    //nanti kirim ke file output
        }
    }
    
    //end of fungsi2
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            ServerSocket servsock = new ServerSocket(1234);
            System.out.println("Server mau berjalan");
            Socket socket = servsock.accept();
            System.out.println("Server berjalan ...");
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();         //connected to client
            //listen message
            //kita kerja disini
            //looping start
            
            byte[] buf = new byte[50];
            is.read(buf);
            System.out.println(new String (buf));
            os.write("selamat datang di serverku\r\n".getBytes());
            os.flush();
            
            
            
            //end of looping
            //kita kerja cuma sampai sini
            os.close();
            is.close();
            socket.close();
            servsock.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
