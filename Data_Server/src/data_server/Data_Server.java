/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_server;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kaoutare
 */
public class Data_Server 
{
    public static void main(String[] args) throws IOException 
    {
        //Connection à la base de données BD_CLIENTS    
        DBCollection collection = null;
        try
        {
            MongoClient mongoClient = new MongoClient("localhost");
            DB db = mongoClient.getDB("bd_clients");
            collection = db.getCollection("bd_clients");
        }
        catch(UnknownHostException ex){ Logger.getLogger(Data_Server.class.getName()).log(Level.SEVERE, null, ex);}
        
        ThreadDataServer thServ = new ThreadDataServer(collection);
        thServ.start();
    }   
}
