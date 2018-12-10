/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetreseaux;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author ahochart
 */
public class ServeurTCP {
    final static int port = 4444;
    final static ResoNom rn = new ResoNom();
    final static ChiffrementAES chif = new ChiffrementAES();
    
    public static void main(String[] args) throws IOException{
     InetAddress address;
     address = InetAddress.getLocalHost();
     String[] infos = address.toString().split("/");
     Scanner sc = new Scanner(System.in);
        try {

            
            ServerSocket socketserveur = new ServerSocket(port);
            System.out.println("lancement du serveur");
            System.out.println("Nom du serveur : "+infos[0]);
            System.out.println("Adresse du serveur : "+infos[1]);
            
            
            
            
            Socket socketClient = socketserveur.accept();
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socketClient.getInputStream()));
            
            PrintWriter pw = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(socketClient.getOutputStream())), true);
            
            System.out.println("Commexion avec : "+socketClient.getInetAddress());
            
            
            Thread envoi = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    while(true){
                        msg=sc.nextLine();
                        try {
                            byte[] msgchiffré = chif.chiffrerMsg(msg);
                        } catch (InvalidKeyException ex) {
                            Logger.getLogger(ServeurTCP.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NoSuchAlgorithmException ex) {
                            Logger.getLogger(ServeurTCP.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NoSuchPaddingException ex) {
                            Logger.getLogger(ServeurTCP.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalBlockSizeException ex) {
                            Logger.getLogger(ServeurTCP.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (BadPaddingException ex) {
                            Logger.getLogger(ServeurTCP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        pw.println(msg);
                        pw.flush();
                    }
                }
            });
            envoi.start();
 
            
            
            Thread recevoir = new Thread(new Runnable() {
                String msg;
                @Override
                public void run() {
                    try{
                        msg = in.readLine();
                        
                        while(!msg.equals("bye")){
                            System.out.println("Client : "+msg);
                            msg=in.readLine();
                        }
                        System.out.println("Client déconnecté");
                        out.close();
                        socketClient.close();
                        socketserveur.close();
                        } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            recevoir.start();
        }catch (IOException e ){
            e.printStackTrace();
        }
    }
}
