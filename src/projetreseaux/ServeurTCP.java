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
            System.out.println("Nom du serveur : "+rn.getName());
            System.out.println("Adresse du serveur : "+rn.getAddress());
            
            
            
            
            Socket socketClient = socketserveur.accept();
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socketClient.getInputStream()));
            
            PrintWriter pw = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(socketClient.getOutputStream())), true);
            
            System.out.println("Commexion avec : "+socketClient.getInetAddress());
            
            while(true){
                
                
                
                
                
                
                //PrintStream out = new PrintStream(socketClient.getOutputStream());
                
                String message=in.readLine();
                
                if (message.equals("FIN")){
                    System.out.println(socketClient.getInetAddress()+" a fermé la conversation");
                    in.close();
                    pw.close();
                    socketClient.close();
                    socketserveur.close();
                    break;
                }
                
                System.out.println("message recu : "+message);
                pw.println(message);
                

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
    }

}
