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
    public static void lordMKDIR(String input)
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

        byte[] buf = new byte[1000];
        String msg,msgCode;
        int code;
        File folder = new File("/Users/dhanarp/");


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

           //kirim pesan
            int len;
            while(true)
            {
                //recv from client
                buf = new byte[1000];
                len = is.read(buf);
                if(len == -1)
                {
                    break;
                }
                msg =new String(buf);
                //end of recv. parsing.
                msgCode=msg.substring(0, 1);
                code= Integer.parseInt(msgCode);
                //masuk if
                if(code==1) //LS
                {
                    msg=msg.substring(1);
                    folder = new File(msg);
                    lordLS(folder);
                    //edit lordLS sehingga hasil di print di File
                    //kirim File lewat Socket
                }
                else if(code==2) //mkdir
                {
                    msg=msg.substring(1);
                    lordMKDIR(msg);
                    //harusnyajalan

                }
                //output ke file
                //kirim file
                else if(code==0)        break;

            }
            //end of kirim pesan





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
