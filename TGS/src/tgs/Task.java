/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tgs;

import Protocol.RequestTGS;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Thibault
 */
public class Task implements Runnable
{
    private Socket cSock;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    public Task(Socket s)
    {
        cSock = s;
    }

    @Override
    public void run()
    {
        try
        {
            // Chargement clé symétrique KTGS
            SecretKey KTGS = new SecretKeySpec("cisco".getBytes(), "DES");
            
            // Récupération de l'objet
            RequestTGS req = (RequestTGS)ois.readObject();
            
            // Décryptage de l'objet RequestTGS avec la clé symétrique KTGS
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, KTGS);
            
            // Récupération clé de session KCTGS
            
            // Décryptage de l'authentificateur
            
            
            
            
        } 
        catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex)
        {
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
