/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_server;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import protocoleCLIBOP.ReponseCLIBOP;
import protocoleCLIBOP.RequeteCLIBOP;

/**
 *
 * @author Kaoutare
 */
public class Tache implements Runnable
{
    private Socket socketClient;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private DBCollection collection;
    
    public Tache(Socket sock, DBCollection dbCol)
    {
        socketClient = sock;
        collection = dbCol;
    }

    @Override
    public void run() 
    {
        try
        {
            oos = new ObjectOutputStream(socketClient.getOutputStream());
            ois = new ObjectInputStream(socketClient.getInputStream());
            
            if(ois == null || oos == null)
                System.exit(1);
            else
                System.out.println("Serveur en attente");
        
            boolean fin = false;
        
            //Gestion du protocole
            RequeteCLIBOP req = null;
            ReponseCLIBOP rep = null;

            while(fin == false)
            {
           
                req = (RequeteCLIBOP)ois.readObject();

                switch(req.getCmd())
                {
                    case "BEGIN" : 
                    {
                        rep = new ReponseCLIBOP();
                        rep.setCmd("BEGIN");
                        System.out.println("<Connection>");
                    }
                    break;

                    //Demande liste d'opérations
                    case "GETLIST":
                    {   
                        rep = new ReponseCLIBOP();
                        rep.setCmd("GETLIST");
                        
                        DBObject query = new BasicDBObject();
                        if(req.isIsValid() == true)
                        {
                            
                        }
                        
                        
                    }
                    break;

                    //Valeur moyenne mensuelle des comptes d'un client    
                    case "AVERAGE":
                    {

                    }
                    break;

                    //Validation d'un compte client 
                    case "VALIDATE":
                    {

                    }
                    break;

                    //Demande nombre de débits refusés
                    case "GETDEB":
                    {

                    }
                    break;

                    case "END" : 
                    {
                        rep = new ReponseCLIBOP();
                        rep.setCmd("END");
                        System.out.println("<End connection>");
                    }
                    break;
        
                }
            
                oos.writeObject(rep);
            }
           
            oos.close();
            ois.close();
            socketClient.close();
            
    
        } 
        catch (IOException ex) { Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex); } 
        catch (ClassNotFoundException ex) { Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex); }
    }
    
}
