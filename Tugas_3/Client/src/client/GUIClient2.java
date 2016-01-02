/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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
public class GUIClient2 extends javax.swing.JFrame {
    String username, serverIP = "localhost";
    int Port = 5000;
    Socket sock;
    ObjectInputStream ois;
    ObjectOutputStream ous;
    //ArrayList<String> userList = new ArrayList();//
    Boolean isConnected = false;
    Person me;
    ArrayList<Person> player;
    /**
     * Creates new form GUIClient2
     */
    public GUIClient2() {
        initComponents();
    }
    
    public class IncomingReader implements Runnable{
        GUIClient2 gui;
        public IncomingReader(GUIClient2 gui){
            this.gui=gui;
        }
        @Override
        public void run(){//edited
            Object temp;
            Person tmp;
            //boolean isMe=false;
            try {
                while((temp= ois.readObject())!=null){
                    System.out.println("got an input");
                    tmp=(Person) temp;
                    if(tmp.signal==0){          //connect
                        //if(!(tmp.nama.equals(me.nama))){
                            moved(tmp);
                            chatTextArea.append(tmp.nama+" has Connected\n");
                            player.add(tmp);
                            writeUsers();//update list online
                            if(tmp.nama.equals(me.nama)){
                                game1.gameLogin(gui);
                            }
                    }
                    else if(tmp.signal==1){     //chat
                        chatTextArea.append(tmp.nama+": "+tmp.chat+"\n");
                    }
                    else if(tmp.signal==2){     //move
                        //chatTextArea.append("someone moved\n");
                        for(Person iter :player){
                            if(iter.nama.equals(tmp.nama)){
                                if(tmp.velX==-10){
                                    iter.kiri();
                                    moved(iter);
                                }
                                else if(tmp.velX==10){
                                    iter.kanan();
                                    moved(iter);
                                }
                                else if(tmp.velY==-10){
                                    iter.atas();
                                    moved(iter);
                                }
                                else if(tmp.velY==10){
                                    iter.bawah();
                                    moved(iter);
                                }
                            }
                            game1.repaint();
                        }
                        /*
                        for(Person iter:player){
                           if(iter.nama.equals(tmp.nama)){
                               player.remove(iter);
                               player.add(tmp);
                               game1.repaint();
                           } 
                        }
                        */
                    }
                    else if(tmp.signal==3){     //attack
                        //todo attack
                        for(Person iter:player){
                            if(iter.nama.equals(tmp.nama)){
                                if(tmp.readyToFire){
                                    iter.tembak();
                                    shoot(iter);
                                }
                            }
                        }
                    }
                    else if(tmp.signal==4){     //disconnect
                        for(Person iter :player){
                            if(iter.nama.equals(tmp.nama)){
                                player.remove(iter);    //apus 
                                System.out.println(iter.nama + " has disconnected.");
                                chatTextArea.append(iter.nama + " has disconnected.\n");
                                writeUsers();//update list online
                                if(iter.nama.equals(me.nama))
                                {
                                    Disconnected();
                                    chatTextArea.append("You have disconnected.\n");
                                    System.out.println("You have disconnected");
                                }
                                break;
                            }
                        }  
                    }
                    else if(tmp.signal==-1){
                        chatTextArea.append("Disconnected from server\n");
                        me.signal=-1;
                        SendToServer();
                    }
                    //game1.repaint();
                    chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
                    
                }
                
            }catch (IOException ex) {
                Logger.getLogger(GUIClient2.class.getName()).log(Level.SEVERE, null, ex);
            }catch (ClassNotFoundException ex) {
                Logger.getLogger(GUIClient2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    
    public void ListenThread(){//edited
        Thread IncomingReader = new Thread (new GUIClient2.IncomingReader(this));
        IncomingReader.start();
    }
    public void SendToServer() throws IOException{
        //semua pengiriman data nanti lewat sini
        //if(this.ous==null) System.out.println("Hey");
        ous.writeObject(me);
        ous.flush();
        ous.reset();
    }
    public void writeUsers(){ //edited
        onlineUsersArea.setText("");
        for (Person iter:player){
            onlineUsersArea.append(iter.nama + "\n");
        }
    }
    
   
    
    public void Disconnect() throws IOException {
            me.signal=4;
            SendToServer();
    }
    
    public void Disconnected() throws IOException{
        chatTextArea.append("Disconnected.\n");
        sock.close();
        isConnected = false;
        usernameField.setEditable(true);
        onlineUsersArea.setText("");
        this.isConnected=false;
        this.game1.loggedIn=false;
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        connectButton = new javax.swing.JButton();
        disconnectButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        onlineUsersArea = new javax.swing.JTextArea();
        game1 = new client.Game();
        jScrollPane2 = new javax.swing.JScrollPane();
        chatTextArea = new javax.swing.JTextArea();
        inputTextArea = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        usernameField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        disconnectButton.setText("Disconnect");
        disconnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Username");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Online Users");

        onlineUsersArea.setEditable(false);
        onlineUsersArea.setColumns(20);
        onlineUsersArea.setRows(5);
        jScrollPane1.setViewportView(onlineUsersArea);

        javax.swing.GroupLayout game1Layout = new javax.swing.GroupLayout(game1);
        game1.setLayout(game1Layout);
        game1Layout.setHorizontalGroup(
            game1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        game1Layout.setVerticalGroup(
            game1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 312, Short.MAX_VALUE)
        );

        chatTextArea.setEditable(false);
        chatTextArea.setColumns(20);
        chatTextArea.setLineWrap(true);
        chatTextArea.setRows(5);
        jScrollPane2.setViewportView(chatTextArea);

        inputTextArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputTextAreaActionPerformed(evt);
            }
        });
        inputTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputTextAreaKeyPressed(evt);
            }
        });

        sendButton.setText("SEND!");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(inputTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendButton))
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(connectButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(disconnectButton))
                    .addComponent(game1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(disconnectButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(connectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(game1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inputTextArea)
                            .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        // TODO add your handling code here:
        Object temp;
        // TODO add your handling code here:
        if(isConnected == false){
            username = usernameField.getText();
            usernameField.setEditable(false);
            try {
                player=new ArrayList();
                sock = new Socket(serverIP, Port);
                ois = new ObjectInputStream(sock.getInputStream());
                ous = new ObjectOutputStream(sock.getOutputStream());
                //writer.println(username + ":has connected.:Connect");//
                /**/
                me=new Person(username);
                me.signal=0;
                System.out.println("nama="+me.nama);
                SendToServer();
                /**/
                isConnected = true;
                
            } catch (IOException ex) {
                chatTextArea.append("Cannot Connect! Try Again. \n");
                usernameField.setEditable(true);
                //Logger.getLogger(GUIClient2.class.getName()).log(Level.SEVERE, null, ex);
            }
            ListenThread();
            
        }
        else if(isConnected == true){
            chatTextArea.append("Server Offline\n");
        }
    }//GEN-LAST:event_connectButtonActionPerformed

    private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectButtonActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            Disconnect();
        } catch (IOException ex) {
            Logger.getLogger(GUIClient2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_disconnectButtonActionPerformed

    private void inputTextAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputTextAreaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputTextAreaActionPerformed

    private void inputTextAreaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputTextAreaKeyPressed
        // TODO add your handling code here:
        int c = evt.getKeyCode();
        boolean isMoved=false, isShoot=false;
        if(c==KeyEvent.VK_ENTER){
            if(!isConnected){
            chatTextArea.append("You are offline!\n");
            }
            else{
                String nothing = "";
                if((inputTextArea.getText()).equals(nothing)){
                    inputTextArea.setText("");
                    inputTextArea.requestFocus();
                }
                else {
                    /**/
                    me.chat=inputTextArea.getText();
                    me.signal=1;
                    try {
                        SendToServer();
                    } catch (IOException ex) {
                        Logger.getLogger(GUIClient2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    inputTextArea.setText("");
                    inputTextArea.requestFocus();
                    /**/
                }
                inputTextArea.setText("");
                inputTextArea.requestFocus();
            }
        }
        else if(c==KeyEvent.VK_LEFT){
            if(isConnected) me.kiri();
            isMoved=true;
        }
        else if(c==KeyEvent.VK_RIGHT){
            if(isConnected) me.kanan();
            isMoved=true;
            
        }
        else if(c==KeyEvent.VK_UP){
            if(isConnected) me.atas();
            isMoved=true;
            //gambar();
           
        }
        else if(c==KeyEvent.VK_DOWN){
            if(isConnected) me.bawah();
            isMoved=true;
        }
        
        else if(c==KeyEvent.VK_CONTROL){
            if(isConnected) ;//me.tembak();
            isShoot=true;
        }
            
        if(isMoved){
            try {
                moved(me);
            } catch (IOException ex) {
                Logger.getLogger(GUIClient2.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                SendToServer();
            } catch (IOException ex) {
                Logger.getLogger(GUIClient2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(isShoot){
            try {
                shoot(me);
            } catch (IOException ex) {
                Logger.getLogger(GUIClient2.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                SendToServer();
            } catch (IOException ex) {
                Logger.getLogger(GUIClient2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        isMoved=false;
        isShoot=false;
    }//GEN-LAST:event_inputTextAreaKeyPressed

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        // TODO add your handling code here:
        if(!isConnected){
            chatTextArea.append("You are offline!\n");
        }
        else{
            String nothing = "";
            if((inputTextArea.getText()).equals(nothing)){
                inputTextArea.setText("");
                inputTextArea.requestFocus();
            }
            else {
                /**/
                me.chat=inputTextArea.getText();
                //System.out.println(me.chat);
                me.signal=1;
                try {
                    SendToServer();
                } catch (IOException ex) {
                    Logger.getLogger(GUIClient2.class.getName()).log(Level.SEVERE, null, ex);
                }
                inputTextArea.setText("");
                inputTextArea.requestFocus();
                /**/
            }
            inputTextArea.setText("");
            inputTextArea.requestFocus();
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    private void moved(Person movingPerson) throws IOException{
        movingPerson.movement();
        movingPerson.batas();
        movingPerson.signal=2;
    }
    public void shoot(Person shootPerson) throws IOException{
//        shootPerson.tembak();
        shootPerson.signal=3;
//        SendToServer();
    }
    
    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameFieldActionPerformed

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
            java.util.logging.Logger.getLogger(GUIClient2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIClient2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIClient2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIClient2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIClient2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chatTextArea;
    private javax.swing.JButton connectButton;
    private javax.swing.JButton disconnectButton;
    private client.Game game1;
    private javax.swing.JTextField inputTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea onlineUsersArea;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}