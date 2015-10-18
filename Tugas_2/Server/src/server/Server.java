/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author dhz
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    private static ServerSocket serverSocket;
    private static final int PORT = 1234;
    public static void main(String[] args)
    // TODO code application logic here
    throws IOException
    {
       try {
           serverSocket = new ServerSocket(PORT);
       }
       catch (IOException ioEx) {
           System.out.println("\nUnable to set up port!");
           System.exit(1); 
       }
       do {
           //Wait for client...
           Socket client = serverSocket.accept();
           System.out.println("\nNew client accepted.\n");
           //Create a thread to handle communication with
           //this client and pass the constructor for this
           //thread a reference to the relevant socket...
           ClientHandler handler = new ClientHandler(client);
           handler.start();//As usual, method calls run.
       } while (true);
    }
}

class ClientHandler extends Thread {
     private Socket client;
     private Scanner input;
     private PrintWriter output;
     public ClientHandler(Socket socket)
     {
        //Set up reference to associated socket...
        client = socket;
        try {
            input = new Scanner(client.getInputStream());
            output = new PrintWriter(
            client.getOutputStream(),true);
        }
        catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
     }
     public void run() {
//        String received;
        String message = input.nextLine(), dir = "/", dir1 = "/";
        try {
            do { 
//            //Accept message from client on
//            //the socket's input stream...
//            received = input.nextLine();
//            //Echo message back to client on
//            //the socket's output stream...
//            output.println("ECHO: " + received);
//            //Repeat above until 'QUIT' sent by client...
            
                if(message.startsWith("ls")) { // ---ls---
                    
                    if(message.equals("ls"))
                        if(dir=="")
                            FileList(message); // kirim hasil ls ke klien
                        else
                            FileList(message+" "+dir); // kirim hasil ls ke klien
                    else if(message.startsWith("ls /"))
                        FileList(message); // kirim hasil ls ke klien
                    else if(message.startsWith("ls "))
                        FileList(message.substring(0,3)+dir+
                            message.substring(3,message.length())); // kirim hasil ls ke klien
                }
                
                else if(message.startsWith("mkdir ")) {// ---mkdir---
                    if(message.startsWith("mkdir /"))
                        new File(message.substring(6,
                                message.length())).mkdirs(); // buat direktori
                    else
                        new File(dir+message.substring(6,
                                message.length())).mkdirs(); // buat direktori
                }
                
                else if(message.startsWith("cd ")) { // ---cd---
                    if(message.equals("cd /")) {
                        dir = "/";
                    }
                    else if(message.startsWith("cd / ")) {
                        dir = "/";
                    }

                    else if(message.equals("cd ..")) {
                        if(!dir.equals("/"))
                            dir = dir1;
                    }
                    else if(message.startsWith("cd .. ")) {
                        if(!dir.equals("/"))
                                dir = dir1;
                    }
                    
                    else if(message.equals("cd .")) {
                    }
                    
                    else if(message.startsWith("cd /")) {
                        dir1 = dir;
                        if (DirExists(message.substring(0,3)+"/"+
                                message.substring(4,message.length()))) //{
                            if(message.endsWith("/"))
                                dir = message.substring(3);
                            else
                                dir = message.substring(3) + "/";
                    }
                    
                    else {
                        dir1 = dir;
                        if (DirExists(message.substring(0,3)+dir+
                                message.substring(3,message.length()))) //{
                            if(message.endsWith("/"))
                                dir = dir + message.substring(3);
                            else
                                dir = dir + message.substring(3) + "/";
                    }
                }
                message = input.nextLine(); // nerima input selanjutnya
            
            } while (!message.equals("QUIT"));
        }
        catch(IOException ioEx) {
            //System.out.println("Unable to disconnect!");
        }
        
        try {
            if (client!=null) {
                System.out.println("Closing down connection...");
                client.close();
            }
        }
        catch(IOException ioEx) {
            System.out.println("Unable to disconnect!");
        }
     }
     
     public static String FileList(String message)
        throws IOException {
            String filename, report = "";
            if (message.equals("ls") || message.equals("ls "))
                  filename = "/";
            else
                filename = message.substring(3);
            File fileDir = new File(filename);
            if (!fileDir.exists())
                report = report.concat("ls: cannot access "+filename+": No such file or directory\n");
            else {
                report = report.concat(filename + " is a ");
                if (fileDir.isFile())
                    report = report.concat("file.\n");
                else
                    report = report.concat("directory.\n"); 
                report = report.concat("It is ");
                if (!fileDir.canRead())
                    report = report.concat("not ");
                report = report.concat("readable.\n");
                report = report.concat("It is ");
                if (!fileDir.canWrite())
                    report = report.concat("not ");
                report = report.concat("writeable.\n");
                if (fileDir.isFile())
                    report = report.concat("Size of file: "+fileDir.length()+ " bytes.\n");
                else { //Nampilin semua file & folder
                    report = report.concat("Contents:\n");
                    String[] fileList = fileDir.list();
                    for (int i=0; i<fileList.length; i++)
                        report = report.concat(" "+fileList[i]+"\n");
                }
            }
            // tulis hasil ls ke file
            PrintWriter writer = new PrintWriter("build/classes/temp.xml", "UTF-8");
            writer.print(report);
            writer.close();
            
            sendFile(); // kirim file hasil print ls ke client
            
            return report;
        }
    
    public static Boolean DirExists(String message)
        throws IOException {
            String filename = message.substring(3), report = " ";
            File fileDir = new File(filename);
            if (!fileDir.exists()) {
                //report = report.concat("cd: cannot access "+filename+": No such file or directory\n");
                PrintWriter writer = new PrintWriter("build/classes/temp.xml", "UTF-8");
                writer.print(report);
                writer.close();

                sendFile(); // kirim file hasil print ls ke client

                return false;
            }
            else {
                report = "1\n2";
                PrintWriter writer = new PrintWriter("build/classes/temp.xml", "UTF-8");
                writer.print(report);
                writer.close();

                sendFile(); // kirim file hasil print ls ke client

                return true;
//                report = report.concat(filename + " is a ");
//                if (fileDir.isFile())
//                    report = report.concat("file.\n");
//                else
//                    report = report.concat("directory.\n"); 
//                report = report.concat("It is ");
//                if (!fileDir.canRead())
//                    report = report.concat("not ");
//                report = report.concat("readable.\n");
//                report = report.concat("It is ");
//                if (!fileDir.canWrite())
//                    report = report.concat("not ");
//                report = report.concat("writeable.\n");
//                if (fileDir.isFile())
//                    report = report.concat("Size of file: "+fileDir.length()+ " bytes.\n");
//                else { //Nampilin semua file & folder
//                    report = report.concat("Contents:\n");
//                    String[] fileList = fileDir.list();
//                    for (int i=0; i<fileList.length; i++)
//                        report = report.concat(" "+fileList[i]+"\n");
//                }
            }
        }
    
    public final static int SOCKET_PORT = 13267;  // port socket buat transfer file
    public final static String FILE_TO_SEND = "build/classes/temp.xml";

    public static void sendFile() throws IOException {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        ServerSocket servsock = null;
        Socket sock = null;
        try {
            servsock = new ServerSocket(SOCKET_PORT); // buat socket server untuk kirim file
            try {
                sock = servsock.accept();
                
                File myFile = new File (FILE_TO_SEND);
                myFile.deleteOnExit();
                
                // kirim file
                byte[] mybytearray = new byte [(int)myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                bis.read(mybytearray,0,mybytearray.length);
                os = sock.getOutputStream();
                os.write(mybytearray,0,mybytearray.length);
                os.flush();
            }
            finally {
                if(bis!=null) bis.close();
                if(os!=null) os.close();
                if(sock!=null) sock.close();
            }
        }
        finally {
            if(servsock!=null) servsock.close();
        }
    }
}