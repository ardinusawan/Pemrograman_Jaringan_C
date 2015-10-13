/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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
     public static boolean setCurrentDirectory(String directory_name)
    {
        boolean result = false;  // Boolean indicating whether directory was set
        File    directory;       // Desired current working directory

        directory = new File(directory_name).getAbsoluteFile();
        if (directory.exists() || directory.mkdirs())
        {
            result = (System.setProperty("user.dir", directory.getAbsolutePath()) != null);
        }

        return result;
    }

    public static PrintWriter openOutputFile(String file_name)
    {
        PrintWriter output = null;  // File to open for writing

        try
        {
            output = new PrintWriter(new File(file_name).getAbsoluteFile());
        }
        catch (Exception exception) {}

        return output;
    }

    
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
            String ls = "ls";
            //System.out.println("test " + new String(buf));
            String buf2 = new String(buf);
            buf2=buf2.trim();
            if(buf2.equals(ls))
            {
           // { System.out.println("HAHAHAHAHAHAHAHAHAHHAHAHAHA"); }
                
            String command = "ls /home/ardi/Downloads";

            Process proc = Runtime.getRuntime().exec(command);

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
            
            }
            
            else 
            {
            //System.out.println("sad");
                String mkdir = "mkdir";
                String cd = "cd";
                String buf3 = new String(buf);
                buf3=buf3.trim();
                String[] parts = buf3.split(" ");
                
                String part1 = parts[0];
                String part2 = parts[1];
            
                if (part1.equals(mkdir))
                {
                    //System.out.println(part2);
                    //String command = new String (buf);

                    Process proc2 = Runtime.getRuntime().exec("mkdir " + part2);
                    //System.out.println("Folder " + part2 + " has been created");
                     String output1 = "Folder ";
                     String output2 = new String(part2);
                     String output3 = " has been created";
                     String out_final = output1 + output2 + output3;
                     System.out.println(out_final);
                    os.write(out_final.getBytes());
                        // Read the output
                       
                    BufferedReader reader =  
                      new BufferedReader(new InputStreamReader(proc2.getInputStream()));

                    String line = "";
                    while((line = reader.readLine()) != null) {
                    //System.out.print(line + "\n");
                       os.write(line.getBytes());

                    }

                    try {   
                        proc2.waitFor();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    
                
                }
                else if(part1.equals(cd))
                {                    
            
                    //System.out.println("cd " + part2);
                    //Runtime.getRuntime().exec("sh -c 'cd /home/ardi/wawan2 && mkdir ww");
                    //File f = new File ( System.getProperty("/home/ardi/wawan2"),part1);
                     //System.out.println(f.getPath());
                    //System.getProperties("user.dir");
                            //System.setProperties(part2.);
                    //String dir = new String("user.dir")
                    String dir = System.getProperty("user.home");
                    
                    System.out.println(dir);
                    
                    
                    
                    //System.out.println("user.dir: " + System.getProperty("user.dir"));

                  
                    
                    
                    
                    

                }
                
            
            }
            //
            
            
            
            
            
            
            
            
            
            
            
            
            
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
