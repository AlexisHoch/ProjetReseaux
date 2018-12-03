/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetreseaux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author ahochart
 */
public class ServeurTCP {
    final static int port = 4444;
    final static ResoNom rn = new ResoNom();
    
    public static void main(String[] args) throws IOException{
        try {

            
            ServerSocket socketserveur = new ServerSocket(port);
            System.out.println("lancement du serveur");
            /*System.out.println("Nom du serveur : "+rn.getName());
            System.out.println("Adresse du serveur : "+rn.getAddress());*/
            while(true){
                Socket socketClient = socketserveur.accept();
                String message = "";
                
                System.out.println("Commexion avec : "+socketClient.getInetAddress());
                
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socketClient.getInputStream()));
                
                PrintStream out = new PrintStream(socketClient.getOutputStream());
                
                message=in.readLine();
                out.println(message);
                
                socketClient.close();
         
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
    }

}
