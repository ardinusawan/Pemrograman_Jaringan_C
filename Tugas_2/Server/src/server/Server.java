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
     private static PrintWriter output;
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
                System.out.println("Loop baru");
                System.out.println(dir);
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
                
                else if(message.startsWith("wget ")) {// ---wget---
                    System.out.println(1);
                    if(message.startsWith("wget /")) {
                        System.out.println("if1");
                        System.out.println("sendFile("+message.substring(5,message.length())+")");
                        sendFile(message.substring(5,message.length()));
                    }
                    else if(message.startsWith("wget ")) {
                        System.out.println("if2");
                        System.out.println("sendFile("+dir+message.substring(5,message.length())+")");
                        sendFile(dir+message.substring(5,message.length())); // kirim hasil ls ke klien
                    }
                    System.out.println(2);
                }
                
                else if(message.startsWith("upload ")) {// ---curl---
                    System.out.println(1);
                    System.out.println("getFile("+dir+message.substring(7,message.length())+")");
                    //getFile("uploaded/"+message.substring(7)); // kirim hasil ls ke klien
                    getFile(message.substring(7)); // kirim hasil ls ke klien
                    System.out.println("mulai baca");
                    //try (BufferedReader br = new BufferedReader(new FileReader("uploaded/"+message.substring(7)))) {
                    try (BufferedReader br = new BufferedReader(new FileReader(message.substring(7)))) {
//                        try (BufferedReader br = new BufferedReader(new FileReader(FILE_TO_RECEIVED))) {
                        String line;
                        while ((line = br.readLine()) != null);
                            System.out.println(line);
                    }
                    System.out.println(2);
                }
                
                message = input.nextLine(); // nerima input selanjutnya
            
            } while (!message.equals("QUIT"));
        }
        catch(IOException ioEx) {
            //System.out.println("Unable to disconnect!");
        }
        System.out.println("End");
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
            
            sendFile(FILE_TO_SEND); // kirim file hasil print ls ke client
            
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

                sendFile(FILE_TO_SEND); // kirim file hasil print ls ke client

                return false;
            }
            else {
                report = "1\n2";
                PrintWriter writer = new PrintWriter("build/classes/temp.xml", "UTF-8");
                writer.print(report);
                writer.close();

                sendFile(FILE_TO_SEND); // kirim file hasil print ls ke client

                return true;
            }
        }
    
    public final static String FILE_TO_SEND = "build/classes/temp.xml";
    public final static String FILE_TO_RECEIVED = "build/classes/temp.xml";
    public final static int FILE_SIZE = 6022386;//1234;//6022386; // file size temporary hard coded // ukuran file yang dikirim harus kurang dari ini
    public final static int SOCKET_PORT = 13267;       // port socket buat transfer file
    public final static String SERVER = "127.0.0.1";  // localhost
    
    public static void sendFile(String file) throws IOException {
        System.out.println(1.2);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        ServerSocket servsock = null;
        Socket sock = null;
        try {
            System.out.println(1.3);
            servsock = new ServerSocket(SOCKET_PORT); // buat socket server untuk kirim file
            try {
                System.out.println(file);
                System.out.println(1.4);
                output.println("no more race condition fam");
                sock = servsock.accept();
                
                System.out.println(1.5);
                File myFile = new File (file);
                //myFile.deleteOnExit();
                
                // kirim file
                System.out.println(1.6);
                byte[] mybytearray = new byte [(int)myFile.length()];
                if(myFile.length()!=0)
                {
                    fis = new FileInputStream(myFile);
                    bis = new BufferedInputStream(fis);
                    System.out.println(1.7);
                    bis.read(mybytearray,0,mybytearray.length);
                    os = sock.getOutputStream();
                    os.write(mybytearray,0,mybytearray.length);
                    //System.out.write(mybytearray,0,mybytearray.length);
                    os.flush();
                    System.out.println("1.success");
                }
                else
                {
                    os = sock.getOutputStream();
                    os.write(mybytearray,0,mybytearray.length);
                    System.out.println("1.no file");
                }
            }
            finally {
                if(bis!=null) bis.close();
                if(os!=null) os.close();
                if(sock!=null) sock.close();
                System.out.println("1.finally");
            }
        }
        finally {
            if(servsock!=null) servsock.close();
            //System.out.println("error");
        }
    }
    
    public static void getFile(String file) throws IOException {
        System.out.println(file);
        int bytesRead;
        int current;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        
        System.out.println(1.1);
        try {
            sock = new Socket(SERVER, SOCKET_PORT); 
            
            System.out.println(1.2);
            // terima file
            byte[] mybytearray = new byte [FILE_SIZE];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;
            
            System.out.println(1.3);
            do {
                bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);
            
            System.out.println(1.4);
            bos.write(mybytearray, 0, current);
            //bos.write(mybytearray, 0, FILE_SIZE);
            bos.flush();
            fos.flush();
//            File myFile = new File (file);
//            myFile.deleteOnExit();
            System.out.println("1.success");
        }
//        catch(IOException ioEx) {
//                System.out.println("...");
//            }
        finally {
            if(fos!=null) fos.close();
            if(bos!=null) bos.close();
            if(sock!=null) sock.close();
            System.out.println("1.finally");
        }
    }
}