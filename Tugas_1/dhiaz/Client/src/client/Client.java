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
public class Client {

    /**
     * @param args the command line arguments
     */
    private static InetAddress host;
    private static final int PORT = 1234;
    
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
        Socket link = null; //Step 1.
        try {
            link = new Socket(host,PORT);
            Scanner input = new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream(),true);
            String message, response = "";
            do { // tekan enter untuk keluar
                System.out.print("Enter message: ");
                Scanner userEntry = new Scanner(System.in);
                message = userEntry.nextLine();
                output.println(message); //Step 3.
                userEntry.reset();
                if(message.startsWith("ls")){ //ls
                    if(!message.equals("ls") && !message.startsWith("ls "))
                        System.out.println("SERVER> "+message+": command not found");
                    else {
                        getFile(); //terima file hasil print ls
                        //print file
                        try (BufferedReader br = new BufferedReader(new FileReader(FILE_TO_RECEIVED))) {
                            String line = null;
                            while ((line = br.readLine()) != null) {
                                System.out.println(line);
                            }
                        }
                    }    
                }
                else if(message.startsWith("mkdir")) { //mkdir
                    if(message.length()==5)               // "mkdir" (input salah)
                        System.out.println("SERVER> mkdir: missing operrand");
                    else if(message.length()==6) { 
                        if(!message.startsWith("mkdir ")) // "mkdir%" (input salah)
                            System.out.println("SERVER> "+message+": command not found");
                        else                              // "mkdir " (input salah)
                            System.out.println("SERVER> mkdir: missing operrand");
                    }
                    else if(!message.startsWith("mkdir "))// "mkdir%%%%" (input salah)
                        System.out.println("SERVER> "+message+": command not found");
                    else                                  // "mkdir %%%%" (input benar)
                        System.out.println("SERVER> Created directory '"+message.substring(6)+"'");
                }
                else //selain ls & mkdir
                    System.out.println("SERVER> "+message+": command not found");
            } while (!message.equals("")); // tekan enter untuk keluar
        }
        catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
        finally {
            try {
                System.out.println("\n* Closing connection... *");
                link.close();
            }
            catch(IOException ioEx) {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }
    
    public final static String FILE_TO_RECEIVED = "build/empty/temp.xml";
    public final static int FILE_SIZE = 1234;//6022386; // file size temporary hard coded // ukuran file yang dikirim harus kurang dari ini
    public final static int SOCKET_PORT = 13267;       // port socket buat transfer file
    public final static String SERVER = "127.0.0.1";  // localhost
    
    public static void getFile() throws IOException {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try {
            sock = new Socket(SERVER, SOCKET_PORT);

            // terima file
            byte [] mybytearray  = new byte [FILE_SIZE];
            InputStream is = sock.getInputStream();
            fos = new FileOutputStream(FILE_TO_RECEIVED);
            bos = new BufferedOutputStream(fos);
            bytesRead = is.read(mybytearray,0,mybytearray.length);
            current = bytesRead;

            do {
                bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
                if(bytesRead >= 0) current += bytesRead;
            } while(bytesRead > -1);

            bos.write(mybytearray, 0 , current);
            bos.flush();

            File myFile = new File (FILE_TO_RECEIVED);
            myFile.deleteOnExit();
        }
        finally {
            if (fos != null) fos.close();
            if (bos != null) bos.close();
            if (sock != null) sock.close();
        }
    }
}