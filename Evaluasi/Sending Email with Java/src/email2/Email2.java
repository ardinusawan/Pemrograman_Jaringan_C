/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email2;

import java.net.*;
import java.io.*;
import java.util.*;

public class Email2 {

        public static void main(String[] args) {
                int port = 25; 
                String host = "202.46.129.82";

                try {           
                        Socket socket = new Socket(host, port); 

                        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
                        String username = encoder.encode("tes@its-sby.edu".getBytes());
                        String password = encoder.encode("tesprogjar".getBytes());
//                        char[] a = {'t','e','s','@','i','t','s','-','s','b','y','.','e','d','u'};
//                        String username = new String(a); 
                        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                        DataInputStream is = new DataInputStream(socket.getInputStream());
//                      DataInputStream isDI = new DataInputStream(socket.getInputStream()); 
//                        BufferedReader is = new BufferedReader(new InputStreamReader(isDI));

                        dos.writeBytes("HELO\r\n");
                        dos.writeBytes("AUTH LOGIN");
                        dos.writeBytes("\r\n");
                        dos.writeBytes(username);
                        dos.writeBytes("\r\n");
                        dos.writeBytes(password);
                        dos.writeBytes("\r\n");
                        dos.writeBytes("MAIL From: <tes@its-sby.edu>");
                        dos.writeBytes("\r\n");
                        dos.writeBytes("RCPT To: <ardi.nusawan13@gmail.com>");
                        dos.writeBytes("\r\n");
                        dos.writeBytes("DATA\r\n");
                        dos.writeBytes("Subject: hmm\r\n");
                        dos.writeBytes("Kita Berhasil Kawan!!! ");
                        dos.writeBytes("\r\n.\r\n");
                        dos.writeBytes("QUIT\r\n");

                        dos.flush();

                        String responseline;
                        while((responseline = is.readLine())!=null) {
                                System.out.println(responseline);
                        }

                        is.close();
                        dos.close( );                  
                        socket.close( );
                } 
                catch (IOException ex) {
                        System.err.println(ex);
                }
        }
}

//Credit : http://stackoverflow.com/questions/9561637/send-an-email-through-gmail-without-javamail