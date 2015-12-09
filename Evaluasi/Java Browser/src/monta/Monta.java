/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monta;
//sing mari weooeoewoewoeoweoweowoewoekwoekwoekowkeowke

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author dhanarp
 */
public class Monta {

    /**
     * @param args the command line arguments
     */
    
    
    
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        String stream,site,dir,msg,status;
        String[] temp,location = null;
        int flag=0;
        dir="/";
        site="its.ac.id";
        
        Socket sock = new Socket(site,80);
        OutputStream os = sock.getOutputStream();
        InputStream is = sock.getInputStream();
        InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
        BufferedReader reader = new BufferedReader(isReader);
        
        msg=new String("GET "+dir+" HTTP/1.1\r\n" +
                "Host: " + site + "\r\n"+
                "\r\n");
        os.write(msg.getBytes());
        
        
        
        status=reader.readLine();
        //System.out.println(status); 
        while((stream = reader.readLine())!=null){
                //System.out.println(stream);
                if(stream.startsWith("location")){
                   location=stream.split(": ");
                   dir="/"+location[1];
                }
                if(stream.startsWith("Refresh")){
                    location=stream.split(";url=http://monta.if.its.ac.id");
                    flag=2; //flag==2 ---> internal server error
                    //System.out.println(location[1]);
                    dir=location[1];
                }
                if(stream.equals(""))
                    break;
        }
        while((stream = reader.readLine())!=null){
            System.out.println(stream);    
        }
        
        
        //System.out.println(status+" || Harusnya moved");
        temp=status.split(" ");
        //for(String tmp:temp) System.out.println(tmp);
        //System.out.println("done\n\n\n");
        sock.close();
        if(temp[1].startsWith("3")){
                    //System.out.println("masuk 302+reader");
                    
                    msg=new String("GET "+dir+" HTTP/1.1\r\n" +
                    "Host: " + site + "\r\n"+
                    "\r\n");
                    
                    
                    sock = new Socket(site,80);
                    os = sock.getOutputStream();
                    is = sock.getInputStream();
                    isReader = new InputStreamReader(sock.getInputStream());
                    reader = new BufferedReader(isReader);
                    
                    //header
                    os.write(msg.getBytes());
                    //System.out.println("Redirected...");
                    while((stream = reader.readLine())!=null){
                        //System.out.println("."+stream);
                        if(stream.equals(""))
                            break;
                    }
                    //System.out.println("Lanjut");
                    stream=reader.readLine();
                    //System.out.println(stream);
                    //isi
                    while((stream = reader.readLine())!=null){
                        System.out.println(stream);    
                    }
                    flag=1;
                    
             
        }
        else if(flag==0){
            //System.out.println("flag=0");
            while((stream = reader.readLine())!=null){
                //System.out.println(stream);
                if(stream.equals(""))
                    break;
            }
        
            while((stream = reader.readLine())!=null){
                System.out.println(stream);    
            }
            //System.out.println("Keluarr");
        }
        
        else if(flag==2){
            //System.out.println("monta");
                    msg=new String("GET "+dir+" HTTP/1.1\r\n" +
                    "Host: " + site + "\r\n"+
                    "\r\n");
                    
                    //System.out.println("Internal Server Error, redirected...");
                    sock = new Socket(site,80);
                    os = sock.getOutputStream();
                    is = sock.getInputStream();
                    isReader = new InputStreamReader(sock.getInputStream());
                    reader = new BufferedReader(isReader);
                    
                    os.write(msg.getBytes());
                    //System.out.println(msg);
                    while((stream = reader.readLine())!=null){
                        //System.out.println("."+stream);
                        if(stream.equals(""))
                            break;
                    }
                    //System.out.println("Lanjut");
                    //stream=reader.readLine();
                    while((stream = reader.readLine())!=null){
                        System.out.println(stream);    
                    }
        }
    }    
}

