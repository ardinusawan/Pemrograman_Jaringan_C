/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import person.Person;

/**
 *
 * @author ardi nusawan
 */
public class GUIServer extends javax.swing.JFrame implements Runnable{
    ArrayList<ClientHandler> client = new ArrayList();
    ArrayList<Person> player = new ArrayList();
    Thread starter;
    ServerSocket serverSock;
    
    public class ClientHandler implements Runnable{
    //BufferedReader reader;
    Socket sock;
    ObjectOutputStream ous;
    ObjectInputStream ois;
    Person tmp;
    GUIServer server;
    boolean done=false;
    

    
    public ClientHandler(Socket clientSocket,GUIServer server){
        this.server=server;
        try {
            sock = clientSocket;
            this.ous = new ObjectOutputStream(sock.getOutputStream());
            this.ois = new ObjectInputStream(sock.getInputStream());
            //reader = new BufferedReader(ois);
        } catch (IOException ex) {
            //Logger.getLogger(GUIServer.class.getName()).log(Level.SEVERE, null, ex);
            //outputPane.append("Error beginning StreamReader. \n");
        }
        
        
    }
    
    public void send(Person message) throws IOException{
        ous.writeObject(message);
        ous.flush();
        ous.reset();
    }
    
    
    
    @Override
    public void run(){
       
            /**/
            while(!done){
                try {
                    Object temp = ois.readObject();
                    tmp=(Person) temp;
                    if(tmp.signal==0){          //connect
                        for(Person iter:player)
                        {   
                            int tmpInt=iter.signal;
                            iter.signal=0;
                            send(iter);
                            iter.signal=tmpInt;
                        }
                        player.add(tmp);
                        SendToClient(tmp); 
                        
                    } 
                    else if(tmp.signal==1){     //chat
                        SendToClient(tmp);
                    }
                    else if(tmp.signal==2){     //move
                        
                        for(Person iter :player){
                            if(iter.nama.equals(tmp.nama)){
                                if(tmp.velX==-10){
                                    iter.velX = -10;
                                    iter.velY = 0;
                                    moved(iter);
                                }
                                else if(tmp.velX==10){
                                    iter.velX = 10;
                                    iter.velY = 0;
                                    moved(iter);
                                }
                                else if(tmp.velY==-10){
                                    iter.velX = 0;
                                    iter.velY = -10;
                                    moved(iter);
                                }
                                else if(tmp.velY==10){
                                    iter.velX = 0;
                                    iter.velY = 10;
                                    moved(iter);
                                }
                                iter.gambarOrang=tmp.gambarOrang;
                                SendToClient(tmp);
                            }
                        }
                        /*
                        for(Person iter:player){
                           if(iter.nama.equals(tmp.nama)){
                               player.remove(iter);
                               player.add(tmp);
                               SendToClient(tmp);
                           } 
                        }
                        */
                    }
                    else if(tmp.signal==3){
                        //todo: tembak
                    }
                    else if(tmp.signal==4){     //dc
                        for(Person iter :player){
                            if(iter.nama.equals(tmp.nama)){
                                SendToClient(tmp);
                                 
                                sock.close();
                                player.remove(iter);    //apus
                                
                                outputPane.append(iter.nama + "has disconnected.\n");
                                server.Disconnect(this);
                            }
                        }
                    }
                    else if(tmp.signal==-1){
                        for(Person iter :player){
                            if(iter.nama.equals(tmp.nama)){
                                //SendToClient(tmp);
                                tmp.signal=4;
                                send(tmp); 
                                sock.close();
                                player.remove(iter);    //apus
                                
                                outputPane.append(iter.nama + "has disconnected.\n");
                                server.Disconnect(this);
                            }
                        }
                    }
                } catch (IOException|ClassNotFoundException ex) {
                    done=true;
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }
        }
            /**/
        private void moved(Person person) throws IOException{
            person.movement();
            person.batas();
            person.signal=2;
        }
        
        
    }
    
    
    /**
     * Creates new form GUIServer
     */
    public GUIServer() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputPane = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        outputPane.setEditable(false);
        outputPane.setColumns(20);
        outputPane.setRows(5);
        jScrollPane1.setViewportView(outputPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(startButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(stopButton)
                .addGap(72, 72, 72))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startButton)
                    .addComponent(stopButton))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        // TODO add your handling code here:
        starter = new Thread(this);
        starter.start();
        
        outputPane.append("Server started \n");
    }//GEN-LAST:event_startButtonActionPerformed

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
        // TODO add your handling code here:
        //SendToClient("Server:is stopping and all users will be disconnected.\n:Chat");
        outputPane.append("Server Stopping\n");
        try {
            /**/
            ServerStop();
            serverSock.close();
            //starter.stop();
        } catch (IOException ex) {
            Logger.getLogger(GUIServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        outputPane.append("Server Stopped\n");
        /**/
    }//GEN-LAST:event_stopButtonActionPerformed
    
    public synchronized void ServerStop() throws IOException{
        Person Disconnecter = new Person("");
        Disconnecter.signal=-1;
        SendToClient(Disconnecter);
        //player=new ArrayList();
        //client=new ArrayList();
        
            
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIServer().setVisible(true);
            }
        });
    }
    
    
    @Override
    public void run(){
        try {
            serverSock = new ServerSocket(5000);
            while(true){
                Socket clientSock = serverSock.accept();
                ClientHandler ch;
                ch = new ClientHandler(clientSock,this);
                synchronized(client){
                    client.add(ch);
                }
                Thread listener = new Thread(ch);
                listener.start();
                outputPane.append("Got a connection. \n");
        }
        } catch (IOException ex) {
            //Logger.getLogger(GUIServer.class.getName()).log(Level.SEVERE, null, ex);
            outputPane.append("Cannot Start server\n");
        }
    }
  
    public void Disconnect(ClientHandler toStop){
        client.remove(toStop);
    }
    
    
    public synchronized void SendToClient(Person message) throws IOException{
        for(ClientHandler iter:client){
            outputPane.append("Sending a Message\n");
            iter.send(message);
            outputPane.setCaretPosition(outputPane.getDocument().getLength());
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea outputPane;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}
