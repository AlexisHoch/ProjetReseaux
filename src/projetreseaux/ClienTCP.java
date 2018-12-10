/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetreseaux;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author cdhers
 */
public class ClienTCP {

    
    public static void main(String[] arg) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{ 
    
        boolean isReading = !ServeurTCP.reading;

        try (Socket client = new Socket("localhost", ServeurTCP.port);
            DataInputStream clientIn = new DataInputStream(client.getInputStream());
            DataOutputStream clientOut = new DataOutputStream(client.getOutputStream());
            BufferedReader messageInput = new BufferedReader(new InputStreamReader(System.in))) {
           
           SecretKey key = ChiffrementAES.importKey(ServeurTCP.KEY_FILE);
 
           
           String message = "";
           while (!message.equals("bye")) {
               if(isReading){
                   ChiffrementAES.dechiffrerMsg(clientIn);
               }else{
                   byte[] tab = ChiffrementAES.chiffrerMsg(message);
                   clientOut.writeInt(tab.length);
                   clientOut.write(tab);
               }
               isReading = !isReading;
           }
           
       }
   }
   
}


        
