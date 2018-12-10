/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetreseaux;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import static sun.security.krb5.Confounder.bytes;
/**
 *
 * @author ahochart
 */

public class ChiffrementAES {
    public static SecretKey key;
    public static Cipher cipher; 
    
    
    
 
    
    
    public static SecretKey importKey(String filePath) throws IOException{
        byte[] byteKey = Files.readAllBytes(Paths.get(filePath));
        
        SecretKey importedKey;
        
        try (FileInputStream keyIn = new FileInputStream(filePath)) {
            importedKey = new SecretKeySpec(byteKey, "AES");
        }
        
        return importedKey;
    }
    
    
    public static void exportKey(SecretKey key, String outputFile) throws Exception{
        try (FileOutputStream keyOut = new FileOutputStream(outputFile)) {
            keyOut.write(key.getEncoded());
        }
    }
        
    
    public static SecretKey generateKey() throws NoSuchAlgorithmException{       
          KeyGenerator keyGen = KeyGenerator.getInstance("AES");
          keyGen.init(256);
          
          SecretKey key = keyGen.generateKey();
          
          ChiffrementAES.key=key;
          return key;
    }
    

    public static byte[] chiffrerMsg(String message) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cryptedmsg = cipher.doFinal(message.getBytes());
        System.err.println("Message crypté = "+message+new String(cryptedmsg));
        
        return cryptedmsg;
    }
    
    public static void dechiffrerMsg(DataInputStream in) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException{
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] cryptedmsg = new byte[in.readInt()];
        
        in.readFully(cryptedmsg);
        
        byte[] decrypterdmsg = cipher.doFinal(cryptedmsg);
        System.err.println("Message crypté : "+new String(cryptedmsg)+" => "+"Message decrypé = "+new String(decrypterdmsg));
   }
    
    public static void main(String[] args) throws IOException, Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        
        ChiffrementAES.exportKey(key,"cle.txt");
        
        SecretKey importedKey = ChiffrementAES.importKey("cle.txt");
      
  }
}
