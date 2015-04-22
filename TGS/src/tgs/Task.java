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
import java.util.logging.Level;
import java.util.logging.Logger;

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
            
            // Récupération de l'objet
            RequestTGS req = (RequestTGS)ois.readObject();
            
            // Décryptage de l'objet RequestTGS avec la clé symétrique KTGS
            
            // Récupération clé de session KCTGS
            
            // Décryptage de l'authentificateur
            
            
            
            
        } 
        catch (IOException | ClassNotFoundException ex)
        {
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
