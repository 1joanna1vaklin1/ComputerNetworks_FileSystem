/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filesystem;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientFileSystem {

    public static String ip; 
    public static int port; 
    public static DataOutputStream outStream;
    public static BufferedReader inStream;
    public static Socket mySocket;
    
    
    public void clientConnect(){
        try {
            
            mySocket = new Socket(this.ip, this.port);
            //DataOutputStream object to send data through the socket
            outStream = new DataOutputStream(mySocket.getOutputStream());

	    // BufferReader object to read data coming from the server through the socket
            inStream = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            
        } catch (IOException ex) {
            System.out.println("Error is : " + ex.toString());
            Logger.getLogger(ClientFileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String clientCommand(String command){
        String str = "";
        try {

            outStream.writeBytes(command + '\n');		
            str = inStream.readLine(); 
            System.out.println(str);
            return str; 
            
        } catch (IOException ex) {
            Logger.getLogger(ClientFileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str; 
    }
        
    public void clientDisconnect()
    {
        
        try {
            outStream.close();
	    inStream.close();
	    mySocket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ClientFileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
	
    }
    
    public void setIP(String ip){
        this.ip = ip; 
        System.out.println("done");
    }
    
    public void setPort(int port){
        this.port = port; 
        System.out.println("done");
    }
    
    
           

}