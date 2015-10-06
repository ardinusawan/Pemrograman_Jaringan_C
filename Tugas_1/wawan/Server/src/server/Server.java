/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            ServerSocket servsock = new ServerSocket(1234);
            System.out.println("Server mau berjalan");
            Socket socket = servsock.accept();
            System.out.println("Server berjalan ...");
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            
            byte[] buf = new byte[50];
            is.read(buf);
            System.out.println(new String (buf));
            os.write("selamat datang di serverku\r\n".getBytes());
            os.flush();
            
            ///
            FileReader reader = null;
            FileWriter writer = null;
            try {
            reader = new FileReader("/home/ardi/Desktop/in.txt");
            writer = new FileWriter("/home/ardi/Desktop/out.txt");
            int a = 0;
                while ((a = reader.read()) != -1) {
                    writer.write(a);
                }
            } catch (FileNotFoundException e) {
            e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                    } finally {
                        try {
                    reader.close();
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            ///
            
            os.close();
            is.close();
            socket.close();
            servsock.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
