/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetreseaux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author cdhers
 */
public class ClienTCP {

    
    public static void main(String[] arg) throws IOException{ 
        final Socket echo ; 
        final PrintWriter out  ; 
        final BufferedReader in ;
        final Scanner sc = new Scanner(System.in);
        

        try {
             //echo = new Socket("localhost",4444);
             echo = new Socket("pcalb-mm0603",4444);
             out = new PrintWriter(echo.getOutputStream());
             in = new BufferedReader(new InputStreamReader(echo.getInputStream()));
             Thread envoyer = new Thread(new Runnable() {
             String msg;
              @Override
              public void run() {
                while(true){
                  msg = sc.nextLine();
                  out.println(msg);
                  out.flush();
                }
             }
         });
             envoyer.start();
             
             Thread recevoir = new Thread(new Runnable() {
            String msg;
            @Override
            public void run() {
               try {
                 msg = in.readLine();
                 while(!msg.equals("bye")){
                    System.out.println(msg);
                    msg = in.readLine();
                 }
                 System.out.println("Connexion perdue");
                 out.close();
                 echo.close();

               } catch (IOException e) {
                   e.printStackTrace();
               }
            }
        });
             recevoir.start();
           
        }
        catch (UnknownHostException e){
            System.out.println("Pas de Destinataire");
            System.exit(-1);
            
        }
        catch(IOException e){
            System.out.println("now to investigate this IO issue");
            System.exit(-1);
        }

        

        
    }
    
}
