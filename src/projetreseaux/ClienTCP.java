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

/**
 *
 * @author cdhers
 */
public class ClienTCP {

    
    public static void main(String[] arg) throws IOException{ 
        Socket echo = null; 
        PrintWriter out = null ; 
        BufferedReader in = null; 
        

        try {
             echo = new Socket("pcalb-mm0607",4444);
             out = new PrintWriter(echo.getOutputStream(),true);
             in = new BufferedReader(new InputStreamReader(echo.getInputStream()));
            
            
        }
        catch (UnknownHostException e){
            System.out.println("Destination inconnue");
            System.exit(-1);
            
        }
        catch(IOException e){
            System.out.println("now to investigate this IO issue");
            System.exit(-1);
        }
        
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput; 
        while((userInput=stdIn.readLine())!=null){
            out.println(userInput);
            System.err.println("echo :" + in.readLine());
        }
        out.close();
        in.close();
        stdIn.close();
        echo.close();
        

        
    }
    
}
