/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;
import java.io.*;
import java.net.*;
import java.util.*;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
/**
 *
 * @author dhz
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    private static ServerSocket servSock;
    private static final int PORT = 1234;
    
    private static InetAddress host;
        
    public static void main(String[] args) {
        // TODO code application logic here
        //System.out.println("Opening port...\n");
        try {
            servSock = new ServerSocket(PORT); //Step 1
        }
        catch(IOException ioEx) {
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }
        System.out.println("Server running...\n");
        while (true)
            handleClient();
    }

    private static void handleClient() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Socket link = null; //Step 2
        
        try {
            link = servSock.accept(); //Step 2
            
            Scanner input = new Scanner(link.getInputStream()); //Step 3
            PrintWriter output = new PrintWriter(link.getOutputStream(),true); //Step 3
            
            //int numMessages = 0;
            String message = input.nextLine(); //Step 4
            while (!message.equalsIgnoreCase("")) {
                //System.out.println("1");
                if(message.startsWith("ls")) {
                    
                    output.println(FileList(message));
                    //FileList(message);
                    //ObjectOutputStream outStream = new ObjectOutputStream(link.getOutputStream());
                    //System.out.println("1");
                    sendFile();
                    //System.out.println("2");
                    message = input.nextLine();
                }
                else if(message.startsWith("mkdir")) {
                    MakeDir(message);
                    //output.println("message " + numMessages + ": " + message);
                    message = input.nextLine();
                }
                else {
                    //System.out.println("Message received.");
                    //System.out.println("Command not found.");
                    //numMessages++;
                    //System.out.println("message " + numMessages + ": " + message); //Step 4
                    //output.println("message " + numMessages + ": " + message);
                    message = input.nextLine();
                }
            }
            //output.println(numMessages + " Messages received."); //Step 4
        }
        catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
        finally {
            try {
                System.out.println("\n* Closing connection... *");
                link.close(); //Step 5
                //System.out.println("1");
                /*addition*/System.exit(1);
            }
            catch(IOException ioEx) {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
            //System.out.println("2");
        }
    }
    public static String FileList(String message)
        throws IOException {
            String filename, report = "";
            
            //Socket link = servSock.accept();
            //PrintWriter output = new PrintWriter(link.getOutputStream(),true);
            
            if (message.length()==2) //{
                  filename = "/";
//                Scanner input = new Scanner(System.in);
//                System.out.print("Enter name of file/directory ");
//                System.out.print("or press <Enter> to quit: ");
//                filename = input.nextLine();//message.substring(3);
//                while (!filename.equals("")) {//Not <Enter> key.
//                    File fileDir = new File(filename);
//                    if (!fileDir.exists()) {
//                        report = report.concat(filename+" does not exist!\n");
//                        //report = report.concat(filename+" does not exist!\n");
//                        break;
//                    }
//                    //report = report.concat(filename + " is a ");
//                    report = report.concat(filename+" is a ");
//                    //output.println(report);
//                    if (fileDir.isFile())
//                        report = report.concat("file.\n");
//                    else
//                        report = report.concat("directory.\n"); 
//                    report = report.concat("It is ");
//                    if (!fileDir.canRead())
//                        report = report.concat("not ");
//                    report = report.concat("readable.\n");
//                    report = report.concat("It is ");
//                    if (!fileDir.canWrite())
//                        report = report.concat("not ");
//                    report = report.concat("writeable.\n");
//                    if (fileDir.isDirectory()) {
//                        report = report.concat("Contents:\n");
//                        String[] fileList = fileDir.list();
//                        //Now display list of files in
//                        //directory...
//                        for (int i=0;i<fileList.length;i++)
//                            report = report.concat(" "+fileList[i]+"\n");
//                    }
//                    else {
//                        report = report.concat("Size of file: ");
//                        report = report.concat(fileDir.length()+ " bytes.\n");
//                    }//}
//                    System.out.print(report);
//                    System.out.print("\n\nEnter name of next file/directory ");
//                    System.out.print("or press <Enter> to quit: ");
//                    filename = input.nextLine();
//                }
//                input.reset();
//            }
            else //{
                filename = message.substring(3);
                //Scanner input = new Scanner(System.in);
                //System.out.print("Enter name of file/directory ");
                //System.out.print("or press <Enter> to quit: ");
                //filename = input.nextLine();
                //while (!filename.equals("")) {//Not <Enter> key.
            File fileDir = new File(filename);
            if (!fileDir.exists()) {
                report = report.concat(filename+" does not exist!"+"\n");
                //break; //Get out of loop.
            }else {
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
            if (fileDir.isDirectory()) {
                report = report.concat("Contents:\n");
                String[] fileList = fileDir.list();
                //Now display list of files in
                //directory...
                for (int i=0; i<fileList.length; i++)
                    report = report.concat(" "+fileList[i]+"\n");
            }
            else {
                report = report.concat("Size of file: ");
                report = report.concat(fileDir.length()+ " bytes.\n");
            }//}
            //System.out.print("\n\nEnter name of next file/directory ");
            //System.out.print("or press <Enter> to quit: ");
            //filename = input.nextLine();
            }
            //input.reset();
            //}
            //System.out.print(report);
            PrintWriter writer = new PrintWriter("build/empty/temp.xml", "UTF-8");
            writer.print(report);
            writer.close();
            return report;
        }
    
    public static void MakeDir(String message){
        String directoryname;
        if (message.length()==5) {
//            Scanner input = new Scanner(System.in);
//            System.out.print("Enter name of file/directory ");
//            System.out.print("or press <Enter> to quit: ");
//            directoryname = input.nextLine();
//            while (!directoryname.equals("")) {//Not <Enter> key.
//                //File fileDir = new File(directoryname);
//                new File(directoryname).mkdirs();
//                System.out.print("\nEnter name of next file/directory ");
//                System.out.print("or press <Enter> to quit: ");
//                directoryname = input.nextLine();
//            }
//            input.reset();
        }
        else {
            directoryname = message.substring(6);
            new File(directoryname).mkdirs();
        }
            //input.reset();
    }
    
    public final static int SOCKET_PORT = 13267;  // you may change this
  public final static String FILE_TO_SEND = "build/empty/temp.xml";  // you may change this

  public static void sendFile() throws IOException {
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;
    //System.out.println("1.1");
    try {
        //System.out.println("1.2");
      servsock = new ServerSocket(SOCKET_PORT);
      //while (true) {
        //System.out.println("Waiting...");
        try {
            //System.out.println("1.3");
          sock = servsock.accept();
          //System.out.println("Accepted connection : " + sock);
          // send file
          //System.out.println("1.3send");
          File myFile = new File (FILE_TO_SEND);
          byte [] mybytearray  = new byte [(int)myFile.length()];
          //System.out.println("1.3sent");
          fis = new FileInputStream(myFile);
          bis = new BufferedInputStream(fis);
          bis.read(mybytearray,0,mybytearray.length);
          os = sock.getOutputStream();
          //System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
          os.write(mybytearray,0,mybytearray.length);
          os.flush();
          //System.out.println("Done.");
          myFile.deleteOnExit();
          //System.out.println("1.4");
        }
        finally {
          if (bis != null) bis.close();
          if (os != null) os.close();
          if (sock!=null) sock.close();
          //if (fis!=null) fis.close();
          //System.out.println("1.finally");
        }
      //}
    }
    finally {
      if (servsock != null) servsock.close();
    }
  }
    
//    public static void sendFile2() throws IOException {
//        Socket socket = null;
//        String host = "127.0.0.1";
//
//        socket = new Socket(host, 4444);
//
//        File file = new File("test.txt");
//        // Get the size of the file
//        long length = file.length();
//        byte[] bytes = new byte[16 * 1024];
//        InputStream in = new FileInputStream(file);
//        OutputStream out = socket.getOutputStream();
//
//        int count;
//        while ((count = in.read(bytes)) > 0) {
//            out.write(bytes, 0, count);
//        }
//
//        out.close();
//        in.close();
//        socket.close();
//        //print file
//        try (BufferedReader br = new BufferedReader(new FileReader("test.txt"))) {
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }
//         }
//    }
    

}
