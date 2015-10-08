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
                System.out.println("\nSERVER> ");
                
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
//                    System.out.println("Line number"+lineNum+": "+input.nextLine());
//                    input.nextLine();
//                }
                //System.out.println(count);
                //while(!input.nextLine().isEmpty()) {
                //while(!input.nextLine().equals("")) {
                //while(true) {
                //while(input.hasNextLine()){
                    //System.out.println(++count);
                    
                    //response = response.concat(input.nextLine()+"\n");//input.nextLine(); //Step 3.
                    
//                    PrintWriter writer = new PrintWriter("temp.txt", "UTF-8");
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
                System.out.println(
                "\n* Closing connection... *");
                link.close(); //Step 4.
            }
            catch(IOException ioEx) {
                System.out.println(
                "Unable to disconnect!");
                System.exit(1);
            }
        }
    }
    
//private static void getFile(ObjectInputStream inStream)
//    throws IOException, ClassNotFoundException {
//        byte[] byteArray = (byte[])inStream.readObject();
//        FileOutputStream mediaStream;
//        mediaStream =
//        new FileOutputStream("temp.txt");
//        mediaStream.write(byteArray); 
//    }

}