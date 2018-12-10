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
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author ahochart
 */
public class ServeurTCP {
    final static int port = 4444;
    final static ChiffrementAES chif = new ChiffrementAES();
    final static String KEY_FILE = "cle.txt";
    static boolean reading = false;
    
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, Exception{
     InetAddress address;
     address = InetAddress.getLocalHost();
     String[] infos = address.toString().split("/");
     
     ChiffrementAES.key = ChiffrementAES.generateKey();
     ChiffrementAES.exportKey(ChiffrementAES.key, KEY_FILE);
     

            
            ServerSocket socketserveur = new ServerSocket(port);
            System.out.println("lancement du serveur");
            System.out.println("Nom du serveur : "+infos[0]);
            System.out.println("Adresse du serveur : "+infos[1]);
            Socket socketClient = socketserveur.accept();
            DataInputStream In = new DataInputStream(socketClient.getInputStream());
            DataOutputStream Out = new DataOutputStream(socketClient.getOutputStream());
            BufferedReader Input = new BufferedReader(new InputStreamReader(System.in));
            
            
            System.out.println("Commexion avec : "+socketClient.getInetAddress());
            
            String message= "";
            while(!message.equals("bye")){
                if(!reading){
                    ChiffrementAES.dechiffrerMsg(In);
                }
                else{
                    message = Input.readLine();
 
                   byte[] tab = ChiffrementAES.chiffrerMsg(message);
                   Out.writeInt(tab.length);
                   Out.write(tab);
                }
            }
    }
}
