/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetreseaux;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author ahochart
 */
public class ResoNom {
    
    private String adresse;
    private String nom;
    
    public ResoNom() throws UnknownHostException{
        InetAddress address = InetAddress.getLocalHost();
        String[] infos = address.toString().split("/");
        this.nom=infos[0];
        this.adresse=infos[1];
    }
    
    public String getName(){
        return this.adresse;
    }
    
    public String nom(){
        return this.nom;
    }
}
