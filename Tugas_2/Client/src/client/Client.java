/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import java.io.*;
import java.net.*;
import java.util.*;
/**
 *
 * @author dhz
 */
public class Client { // TCP/IP

    /**
     * @param args the command line arguments
     */
    private static InetAddress host;
    private static final int PORT = 1234;
    private static Scanner input;
    private static int isFileThere;
    
    public static void main(String[] args) throws ClassNotFoundException {
        // TODO code application logic here
        try {
            host = InetAddress.getLocalHost();
        }
        catch(UnknownHostException uhEx) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        accessServer(); 
    }
    
    private static void accessServer() throws ClassNotFoundException {
        Socket link = null;
        try {
            link = new Socket(host,PORT); // buat koneksi ke server
            
            // buat aliran data input output
            input = new Scanner(link.getInputStream()); // gk butuh, karena udah pake transfer file
            PrintWriter output = new PrintWriter(link.getOutputStream(),true);
            
            String message, dir = "/", dir1 = "/";
            int lnCount = 0;
            //Boolean dirExists = false;
            do { // tekan enter untuk keluar
                if(dir.equals("/"))
                    System.out.print("SERVER:/$ ");
                else
                    System.out.print("SERVER:/"+dir.substring(1,dir.length()-1)+"$ ");
                Scanner userEntry = new Scanner(System.in);
                message = userEntry.nextLine();
                output.println(message); // kirim input
                userEntry.reset();
                if(message.startsWith("ls")){ // ---ls---
                    if(!message.equals("ls") && !message.startsWith("ls "))
                        System.out.println(message+": command not found");
                    else {
                        getFile(FILE_TO_RECEIVED); //terima file hasil print ls
                        
                        //print file ls
                        System.out.println("mulai baca");
                        try (BufferedReader br = new BufferedReader(new FileReader(FILE_TO_RECEIVED))) {
                            String line;
                            while ((line = br.readLine()) != null)
                                System.out.println(line);
                        }
                    }    
                }
                else if(message.startsWith("mkdir")) { // ---mkdir---
                    if(message.length()==5)               // "mkdir" (input salah)
                        System.out.println("mkdir: missing operrand");
                    else if(message.length()==6) { 
                        if(!message.startsWith("mkdir ")) // "mkdir%" (input salah)
                            System.out.println(message+": command not found");
                        else                              // "mkdir " (input salah)
                            System.out.println("mkdir: missing operrand");
                    }
                    else if(!message.startsWith("mkdir "))// "mkdir%%%%" (input salah)
                        System.out.println(message+": command not found");
                    else                                  // "mkdir %%%%" (input benar)
                        System.out.println("Created directory '"+message.substring(6)+"'");
                }
                else if(message.startsWith("cd")) { // ---cd---
                    if(message.length()==2) {              // "cd" (input salah)
                        System.out.println("cd: missing operrand");
                    }
                    else if(message.length()==3) { 
                        if(!message.startsWith("cd ")) // "cd%" (input salah)
                            System.out.println(message+": command not found");
                        else                              // "cd " (input salah)
                            System.out.println("cd: missing operrand");
                    }
                    else if(!message.startsWith("cd "))// "cd%%%%" (input salah)
                        System.out.println(message+": command not found");
                    
                    else if(message.equals("cd /")) {
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
                        System.out.println("cd: cannot access "+message.substring(3)+": No such file or directory");
                    }
                    
                    else if(message.startsWith("cd /")) {
                        getFile(FILE_TO_RECEIVED);
                        System.out.println("Abaikan");
                        try (BufferedReader br = new BufferedReader(new FileReader(FILE_TO_RECEIVED))) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                lnCount++;
                            }
                        }

                        if(lnCount>1) {
                            dir1 = dir;
                            if(message.endsWith("/")) {
                                dir = "";
                                dir = dir + message.substring(3);
                            }
                            else {
                                dir = "";
                                dir = dir + message.substring(3) + "/";
                            }
                        } else {
                            System.out.println("cd: cannot access "+message.substring(3)+": No such file or directory");
                        }
                        lnCount = 0;
                    }
                    
                    else {
                        getFile(FILE_TO_RECEIVED);
                        System.out.println("Abaikan");
                        try (BufferedReader br = new BufferedReader(new FileReader(FILE_TO_RECEIVED))) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                lnCount++;
                            }
                        }

                        if(lnCount>1) {
                            dir1 = dir;
                            if(message.endsWith("/")) {
                                dir = dir + message.substring(3);
                            }
                            else {
                                dir = dir + message.substring(3) + "/";
                            }
                        } else {
                            System.out.println("cd: cannot access "+message.substring(3)+": No such file or directory");
                        }
                        lnCount = 0;
                    }
                }
                else if(message.startsWith("wget")) { // ---wget---
                    //System.out.println("1");
                    if(message.length()==4)               // "wget" (input salah)
                        System.out.println("wget: missing operrand");
                    else if(message.length()==5) { 
                        if(!message.startsWith("wget ")) // "wget%" (input salah)
                            System.out.println(message+": command not found");
                        else                              // "wget " (input salah)
                            System.out.println("wget: missing operrand");
                    }
                    else if(!message.startsWith("wget "))// "wget%%%%" (input salah)
                        System.out.println(message+": command not found");
                    else if(message.startsWith("wget /")) {                                // "wget %%%%" (input benar)
                        //System.out.println("2");
                        //System.out.println(message.substring(5));
                        try {   
                            //System.out.println("\n*Masuk wget*");
                              isFileThere=getFile(message.substring(message.lastIndexOf("/")+1));
    //                        getFile(FILE_TO_RECEIVED);
                            System.out.println(isFileThere);
                            if(isFileThere==0){
                            System.out.println("mulai baca");
                            try (BufferedReader br = new BufferedReader(new FileReader(message.substring(5)))) {
    //                        try (BufferedReader br = new BufferedReader(new FileReader(FILE_TO_RECEIVED))) {
                                String line;
                                while ((line = br.readLine()) != null);
                                    System.out.println(line);
                            }

                            System.out.println("File transfered '"+message.substring(5)+"' into Client's Project");
                            }
                            else System.out.println("Error 404: File not found");
                        }
                        catch(IOException ioEx) {
                            System.out.println("Error 404: File not found");
                        }
                    }
                    else {                                // "wget %%%%" (input benar)
                        //System.out.println("2");
                        //System.out.println(message.substring(5));
                        try {   
                            //System.out.println("\n*Masuk wget 2*");
                            isFileThere=getFile(message.substring(5));
                            System.out.println(isFileThere);
                            if(isFileThere==0){
    //                       getFile(FILE_TO_RECEIVED);
                            System.out.println("mulai baca");
                            try (BufferedReader br = new BufferedReader(new FileReader(message.substring(5)))) {
    //                        try (BufferedReader br = new BufferedReader(new FileReader(FILE_TO_RECEIVED))) {
                                String line;
                                while ((line = br.readLine()) !=null );
                                    System.out.println(line);}
                            System.out.println("File transfered '"+message.substring(5)+"' into Client's Project");}
                            else System.out.println("Error 404: File not found");
                        }
                        catch(IOException ioEx) {
                            System.out.println("Error 404: File not found");
                        }
                    }
                }
                
                
                else if(message.startsWith("upload")) { // ---upload---
                    System.out.println("1");
                    if(message.length()==6)               // "upload" (input salah)
                        System.out.println("upload: missing operrand");
                    else if(message.length()==7) { 
                        if(!message.startsWith("upload ")) // "upload%" (input salah)
                            System.out.println(message+": command not found");
                        else                              // "upload " (input salah)
                            System.out.println("upload: missing operrand");
                    }
                    else if(!message.startsWith("upload "))// "upload%%%%" (input salah)
                        System.out.println(message+": command not found");
                    else if(message.startsWith("upload /")) {                                // "upload %%%%" (input benar)
                        System.out.println("4");
                        System.out.println(message.substring(7));
                        try {   
                            sendFile(message.substring(message.lastIndexOf("/")+1));
    //                        getFile(FILE_TO_RECEIVED);

//                            System.out.println("mulai baca");
//                            try (BufferedReader br = new BufferedReader(new FileReader(message.substring(5)))) {
//    //                        try (BufferedReader br = new BufferedReader(new FileReader(FILE_TO_RECEIVED))) {
//                                String line;
//                                while ((line = br.readLine()) != null);
//                                    System.out.println(line);
//                            }

                            System.out.println("File transfered '"+message.substring(5)+"'");
                        }
                        catch(IOException ioEx) {
                            System.out.println("Error 404: File not found");
                        }
                    }
                    else {                                // "upload %%%%" (input benar)
                        System.out.println("2");
                        System.out.println(message.substring(7));
                        try {   
                            sendFile(message.substring(7));
    //                        getFile(FILE_TO_RECEIVED);

//                            System.out.println("mulai baca");
//                            try (BufferedReader br = new BufferedReader(new FileReader(message.substring(5)))) {
//    //                        try (BufferedReader br = new BufferedReader(new FileReader(FILE_TO_RECEIVED))) {
//                                String line;
//                                while ((line = br.readLine()) != null);
//                                    System.out.println(line);
//                            }

                            System.out.println("File transfered '"+message.substring(7)+"'");
                        }
                        catch(IOException ioEx) {
                            System.out.println("Error 404: File not found");
                        }
                    }
                    System.out.println("3");
                }
                
                else // selain cd, ls, mkdir, wget
                    System.out.println(message+": command not found");
            } while (!message.equals("")); // tekan enter untuk keluar
        }
        catch(IOException ioEx) {
            System.out.println(0);
            ioEx.printStackTrace();
        }
        finally { // tutup koneksi
            try {
                System.out.println("\n* Closing connection... *");
                link.close();
                System.out.println("\n* Connection closed*");
            }
            catch(IOException ioEx) {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
        System.out.println("selesai");
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
                sock = servsock.accept();
                
                System.out.println(1.5);
                File myFile = new File (file);
                //myFile.deleteOnExit();
                
                // kirim file
                System.out.println(1.6);
                byte[] mybytearray = new byte [(int)myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                System.out.println(1.7);
                bis.read(mybytearray,0,mybytearray.length);
                os = sock.getOutputStream();
                os.write(mybytearray,0,mybytearray.length);
                os.flush();
                System.out.println("1.success");
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
        }
    }
    
    public static int getFile(String file) throws IOException {
        System.out.println(file);
        int bytesRead;
        int current;
        int retval;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        
        System.out.println(1.1);
        try {
            input.nextLine();
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
            //System.out.println(current);
            if(current>=0){
                bos.write(mybytearray, 0, current);
                bos.flush();
                fos.flush();
//            File myFile = new File (file);
//            myFile.deleteOnExit();
                System.out.println("1.success");
                retval=0;
            }
            else
            {
                System.out.println("1.not success but not fail either");
                retval=1;
            }
            
          
                
            //bos.write(mybytearray, 0, FILE_SIZE);
            
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
        return retval;
    }
    
}