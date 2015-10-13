/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server2;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author ardi
 */
public class Server2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String argv[]) throws Exception
      {
         /* 
    File folder = new File("your/path");
    File[] listOfFiles = folder.listFiles();
    //listOfFiles[].
    //File[] listOfFiles = new File[1000];

    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        System.out.println("File " + listOfFiles[i].getName());
      } else if (listOfFiles[i].isDirectory()) {
        System.out.println("Directory " + listOfFiles[i].getName());
      }
    }
*/

         String fromclient;
         String toclient;
          
         ServerSocket Server = new ServerSocket (5000);
         
         System.out.println ("TCPServer Waiting for client on port 5000");

         while(true) 
         {
         	Socket connected = Server.accept();
            System.out.println( " THE CLIENT"+" "+
            connected.getInetAddress() +":"+connected.getPort()+" IS CONNECTED ");
            
            BufferedReader inFromUser = 
            new BufferedReader(new InputStreamReader(System.in));    
     
            BufferedReader inFromClient =
               new BufferedReader(new InputStreamReader (connected.getInputStream()));
                  
            PrintWriter outToClient =
               new PrintWriter(
                  connected.getOutputStream(),true);
            
            while ( true )
            {
            	
            	System.out.println("SEND(Type Q or q to Quit):");
            	toclient = inFromUser.readLine();
            	
            	if ( toclient.equals ("q") || toclient.equals("Q") )
            	{
            		outToClient.println(toclient);
            		connected.close();
            		break;
            	}
                
            	else
            	{
            	outToClient.println(toclient);
                }
            	
            	fromclient = inFromClient.readLine();
            	
                if ( fromclient.equals("q") || fromclient.equals("Q") )
                {
                	connected.close();
                	break;
                }
                	
                else if( fromclient.equals("ls"))
                {
                    outToClient.println("tampilkan file");
                    
                }
                
                else if(fromclient.equals("cd"))
                {
                
                }
                
                else if(fromclient.equals("mkdir"))
                {
                
                }
                
		        else
		        {
		         System.out.println( "RECIEVED:" + fromclient );
		        } 
			    
			}  
			
          }
      }

    private static boolean listFiles() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
