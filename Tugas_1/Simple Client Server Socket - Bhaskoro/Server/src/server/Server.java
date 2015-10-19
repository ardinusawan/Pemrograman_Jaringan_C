/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            //System.out.println("Server mau berjalan");
            Socket socket = servsock.accept();
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            
            //int count = 1;
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String pesan;
            do
            {
            //System.out.println("Server berjalan ...");
            byte[] buf = new byte[50];
            is.read(buf);
            //System.out.println("Test ...");
            System.out.println(new String (buf));
            pesan = input.readLine();
            //pesan = pesan + "\n";
            os.write(pesan.getBytes());
            //os.write("selamat datang di serverku\r\n".getBytes());
            os.flush();
           // count++;
            }while (!pesan.equals("QUIT"));
            
             //os.close();
           // is.close();
           // socket.close();
            
            //servsock.close();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
