/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;
import java.io.*;
import java.net.*;
import java.util.*; 
import java.util.concurrent.TimeUnit;
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
            link = new Socket(host,PORT); //Step 1.
            Scanner input = new Scanner(link.getInputStream()); //Step 2.
            PrintWriter output = new PrintWriter(link.getOutputStream(),true); //Step 2.
            //Set up stream for keyboard entry...
            //Scanner userEntry = new Scanner(System.in);
            String message, response = "";
            //BufferedReader reader = new BufferedReader(input);
            do {
                //int count = 0;
                System.out.print("Enter message: ");
                Scanner userEntry = new Scanner(System.in);
                message = userEntry.nextLine();
                output.println(message); //Step 3.
                userEntry.reset();
                if(message.startsWith("ls")){
                    //System.out.print("SERVER> ");
                    //System.out.println("1");
                    //test
                    //ObjectInputStream inStream = new ObjectInputStream(link.getInputStream());//Step 1...
                    //PrintWriter outStream =new PrintWriter(link.getOutputStream(),true); //Step 1 (cont'd)...
                    //test
                    //System.out.println("2");

                    //getFile("build/empty/temp.xml", inStream);
                    getFile();
                    //try (BufferedReader br = new BufferedReader(new FileReader("build/empty/temp.xml"))) {
                    try (BufferedReader br = new BufferedReader(new FileReader("build/empty/temp.xml"))) {
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                     }
                    }
                else if(message.startsWith("mkdir")) {
                    //System.out.println("  SERVER> Directory "+message.substring(6)+" created.");
                    if(message.length()==5 || message.length()==6)
                        System.out.println("SERVER> mkdir: missing operrand");
                    else
                        System.out.println("SERVER> Created directory '"+message.substring(6)+"'");
                }
                else
                    //System.out.println("  SERVER> '"+message+"' is not recognized as a command. Please use either 'ls' or 'mkdir'.");
                    System.out.println("SERVER> "+message+": command not found");
//                ObjectInputStream inStream;
//                link = servSock.accept();
//                inStream = new ObjectInputStream(link.getInputStream());
//                getFile(inStream);
                
//                while(!input.nextLine().isEmpty()){//(input.hasNextLine()){
//                    //Scanner ls = new Scanner(input);
//                    
//                    count++;
//                    
//                }
//                for(int lineNum=1; input.hasNext(); lineNum++) {
//                  
//                System.out.println("Line number"+lineNum+": "+input.nextLine());
//                    input.nextLine();
//                }
                //System.out.println(count);
                //while(!input.nextLine().isEmpty()) {
                //while(!input.nextLine().equals("")) {
                //while(true) {
                //while(input.hasNextLine()){
                    //System.out.println(++count);
                    
                    //response = response.concat(input.nextLine()+"\n");//input.nextLine(); //Step 3.
                    
//                    PrintWriter writer = new PrintWriter("build/empty/temp.xml", "UTF-8");
//                    writer.append(input.nextLine()+"\n");
//                    writer.close();
                //System.out.println(response = input.nextLine());
                //    if (input.nextLine().isEmpty())
                //        break;
                //}
                //System.out.println(response);
                //input.reset();
            } while (!message.equals(""));
        }
        catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
        finally {
            try {
                System.out.println("\n* Closing connection... *");
                link.close(); //Step 4.
            }
            catch(IOException ioEx) {
                System.out.println("Unable to disconnect!");
                System.exit(1);
            }
        }
    }
    
public final static String FILE_TO_RECEIVED = "build/empty/temp.xml";  // you may change this, I give a
                                                            // different name because i don't want to
                                                            // overwrite the one used by server...

    public final static int FILE_SIZE = 1234;//6022386; // file size temporary hard coded
    public final static int SOCKET_PORT = 13267;      // you may change this
    public final static String SERVER = "127.0.0.1";  // localhost
    
public static void getFile() throws IOException {
    int bytesRead;
    int current = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    Socket sock = null;
    try {
      sock = new Socket(SERVER, SOCKET_PORT);
      //System.out.println("Connecting...");

      // receive file
      byte [] mybytearray  = new byte [FILE_SIZE];
      InputStream is = sock.getInputStream();
      fos = new FileOutputStream(FILE_TO_RECEIVED);
      bos = new BufferedOutputStream(fos);
      bytesRead = is.read(mybytearray,0,mybytearray.length);
      current = bytesRead;

      do {
         bytesRead =
            is.read(mybytearray, current, (mybytearray.length-current));
         if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead > -1);

      bos.write(mybytearray, 0 , current);
      bos.flush();
      //System.out.println("File " + FILE_TO_RECEIVED+ " downloaded (" + current + " bytes read)");
      File myFile = new File (FILE_TO_RECEIVED);
      myFile.deleteOnExit();
    }
    finally {
      if (fos != null) fos.close();
      if (bos != null) bos.close();
      if (sock != null) sock.close();
    }
  }
    
//    public static void getFile2() throws IOException {
//        ServerSocket serverSocket = null;
//
//        try {
//            serverSocket = new ServerSocket(4444);
//        } catch (IOException ex) {
//            System.out.println("Can't setup server on this port number. ");
//        }
//
//        Socket socket = null;
//        InputStream in = null;
//        OutputStream out = null;
//
//        try {
//            socket = serverSocket.accept();
//        } catch (IOException ex) {
//            System.out.println("Can't accept client connection. ");
//        }
//
//        try {
//            in = socket.getInputStream();
//        } catch (IOException ex) {
//            System.out.println("Can't get socket input stream. ");
//        }
//
//        try {
//            out = new FileOutputStream("test.txt");
//        } catch (FileNotFoundException ex) {
//            System.out.println("File not found. ");
//        }
//        
//        byte[] bytes = new byte[16*1024];
//
//        int count;
//        while ((count = in.read(bytes)) > 0) {
//            out.write(bytes, 0, count);
//        }
//        System.out.println(out.toString());
//
//        out.close();
//        in.close();
//        socket.close();
//        serverSocket.close();
//    }

}