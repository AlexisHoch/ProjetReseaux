/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetreseaux;

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
import static sun.security.krb5.Confounder.bytes;
/**
 *
 * @author ahochart
 */

public class ChiffrementAES {
    String KEY = "JESUISMONCHERAMI";
    SecretKeySpec specification; 
    
    public ChiffrementAES(){
        this.specification = new SecretKeySpec(KEY.getBytes(), "AES");
    }
    

    public byte[] chiffrerMsg(String s ) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        
        byte[] bytes = null;
                
        
                    Cipher chiffreur = Cipher.getInstance("AES");
                    chiffreur.init(Cipher.ENCRYPT_MODE, specification);
                    bytes = chiffreur.doFinal(s.getBytes());
        
        return bytes;
    }
    
    public String dechiffrerMsg(byte[] b) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
        Cipher dechiffreur = Cipher.getInstance("AES");
        dechiffreur.init(Cipher.DECRYPT_MODE, specification);
        String s = new String(dechiffreur.doFinal(b));
        
        return s;
    }
    
    
}
