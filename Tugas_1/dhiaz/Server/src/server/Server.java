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
public class Server { // TCP/IP

    /**
     * @param args the command line arguments
     */
    private static ServerSocket servSock;
    private static final int PORT = 1234;
        
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            servSock = new ServerSocket(PORT); // buat socket server
        }
        catch(IOException ioEx) {
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }
        System.out.println("Server is running...\n");
        while (true)
            handleClient();
    }

    private static void handleClient() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Socket link = null;
        try {
            link = servSock.accept(); // terima koneksi
            
            // buat aliran data input output
            Scanner input = new Scanner(link.getInputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream(),true);
            
            String message = input.nextLine();
            while (!message.equalsIgnoreCase("")) { // tekan enter untuk keluar
                
                if(message.startsWith("ls")) { // ---ls---
                    if(message.equals("ls"))
                        output.println(FileList(message)); // kirim hasil ls ke klien
                    else if(message.startsWith("ls "))
                        output.println(FileList(message)); // kirim hasil ls ke klien
                }
                
                else if(message.startsWith("mkdir ")) // ---mkdir---
                    new File(message.substring(6)).mkdirs(); // buat direktori
                
                message = input.nextLine(); // nerima input selanjutnya
            }
        }
        catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
        finally { // tutup koneksi
            try {
                System.out.println("\n* Closing connection... *");
                link.close();
                System.exit(1);
            }
            catch(IOException ioEx) {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }
    public static String FileList(String message)
        throws IOException {
            String filename, report = "";
            if (message.length()==2)
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
            PrintWriter writer = new PrintWriter("build/empty/temp.xml", "UTF-8");
            writer.print(report);
            writer.close();
            
            sendFile(); // kirim file hasil print ls ke client
            
            return report;
        }
    
    public final static int SOCKET_PORT = 13267;  // port socket buat transfer file
    public final static String FILE_TO_SEND = "build/empty/temp.xml";

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