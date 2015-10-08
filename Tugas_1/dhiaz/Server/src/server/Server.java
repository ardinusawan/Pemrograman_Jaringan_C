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
    private static ServerSocket servSock;
    private static final int PORT = 1234;
    
    private static InetAddress host;
        
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("Opening port...\n");
        try {
            servSock = new ServerSocket(PORT); //Step 1
        }
        catch(IOException ioEx) {
            System.out.println("Unable to attach to port!");
            System.exit(1);
        }
        do {
            handleClient();
        } while (true);
    }

    private static void handleClient() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Socket link = null; //Step 2
        
        try {
            link = servSock.accept(); //Step 2
            
            Scanner input = new Scanner(link.getInputStream()); //Step 3
            PrintWriter output = new PrintWriter(link.getOutputStream(),true); //Step 3
            
            int numMessages = 0;
            String message = input.nextLine(); //Step 4
            while (!message.equalsIgnoreCase("close")) {
                //System.out.println("1");
                if(message.startsWith("ls")) {
                    
                    output.println(FileList(message));
                    ObjectOutputStream outStream = new ObjectOutputStream(link.getOutputStream());
                    //sendFile("temp.txt", outStream);
                    message = input.nextLine();
                }
                else if(message.startsWith("mkdir")) {
                    MakeDir();
                    output.println("message " + numMessages + ": " + message);
                    message = input.nextLine();
                }
                else {
                    System.out.println("Message received.");
                    numMessages++;
                    //System.out.println("message " + numMessages + ": " + message); //Step 4
                    output.println("message " + numMessages + ": " + message);
                    message = input.nextLine();
                }
            }
            output.println(numMessages + " Messages received."); //Step 4
        }
        catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
        finally {
            try {
                System.out.println("\n* Closing connection... *");
                link.close(); //Step 5
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
            
            //Socket link = servSock.accept();
            //PrintWriter output = new PrintWriter(link.getOutputStream(),true);
            
            if (message.length()==2) {
                Scanner input = new Scanner(System.in);
                System.out.print("Enter name of file/directory ");
                System.out.print("or press <Enter> to quit: ");
                filename = input.nextLine();//message.substring(3);
                while (!filename.equals("")) {//Not <Enter> key.
                    File fileDir = new File(filename);
                    if (!fileDir.exists()) {
                        report = report.concat(filename+" does not exist!\n");
                        report = report.concat(filename+" does not exist!\n");
                        break;
                    }
                    report = report.concat(filename + " is a ");
                    report = report.concat(filename+" is a ");
                    //output.println(report);
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
                        for (int i=0;i<fileList.length;i++)
                            report = report.concat(" "+fileList[i]+"\n");
                    }
                    else {
                        report = report.concat("Size of file: ");
                        report = report.concat(fileDir.length()+ " bytes.\n");
                    }//}
                    System.out.print(report);
                    System.out.print("\n\nEnter name of next file/directory ");
                    System.out.print("or press <Enter> to quit: ");
                    filename = input.nextLine();
                }
                input.reset();
            } else {
            //Scanner input = new Scanner(System.in);
            //System.out.print("Enter name of file/directory ");
            //System.out.print("or press <Enter> to quit: ");
            filename = message.substring(3);//input.nextLine();
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
            }
            System.out.print(report);
            PrintWriter writer = new PrintWriter("temp.txt", "UTF-8");
            writer.write(report);
            writer.close();
            return report;
        }
    
    public static void MakeDir(){
        String directoryname;
        Scanner input = new Scanner(System.in);
        System.out.print("Enter name of file/directory ");
        System.out.print("or press <Enter> to quit: ");
        directoryname = input.nextLine();
        while (!directoryname.equals("")) {//Not <Enter> key.
            //File fileDir = new File(directoryname);
            new File(directoryname).mkdirs();
            System.out.print("\n\nEnter name of next file/directory ");
            System.out.print("or press <Enter> to quit: ");
            directoryname = input.nextLine();
        }
        input.reset();
    }
    
//private static void sendFile(String fileName, ObjectOutputStream outStream)
//    throws IOException {
//        FileInputStream fileIn =
//        new FileInputStream(fileName);
//        long fileLen = (new File(fileName)).length();
//        int intFileLen = (int)fileLen;
//        byte[] byteArray = new byte[intFileLen];
//        fileIn.read(byteArray);
//        fileIn.close();
//        outStream.writeObject(byteArray);
//        outStream.flush(); 
//    }

}
