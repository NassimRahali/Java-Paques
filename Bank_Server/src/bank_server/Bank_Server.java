/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package bank_server;

import SSL.SSL;
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
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class Bank_Server
{
    
    public static void main(String[] args) throws IOException
    {
        int PORT = 0;
        int POOL_SIZE = 0;
        int MAX_POOL_SIZE = 0;
        int KEEP_ALIVE = 0;
        
        // <editor-fold defaultstate="collapsed" desc="Init Properties">
        /*
        try
        {
        Properties prop = new Properties();
        FileOutputStream fos = new FileOutputStream("Bank_Serveur.properties");
        prop.setProperty("port", "7300");
        prop.setProperty("poolSize", "3");
        prop.setProperty("maxPoolSize", "5");
        prop.setProperty("keepAlive", "10");
        
        prop.store(fos, null);
        }
        catch(Exception ex){}
        */
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Load Properties">
        try
        {
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream("Bank_Serveur.properties");
            prop.load(fis);
            
            PORT = Integer.parseInt(prop.getProperty("port", "7300"));
            POOL_SIZE = Integer.parseInt(prop.getProperty("poosSize", "3"));
            MAX_POOL_SIZE = Integer.parseInt(prop.getProperty("maxPoolSize", "5"));
            KEEP_ALIVE = Integer.parseInt(prop.getProperty("keepAlive", "10"));
            
            fis.close();
        } catch (IOException ex)
        {
            Logger.getLogger(Bank_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Connexion DB">
        DBCollection collection = null;
        try
        {
            MongoClient mongoClient = new MongoClient("10.43.14.40");
            DB db = mongoClient.getDB("java");
            collection = db.getCollection("transactions");
        }
        catch(UnknownHostException ex)
        {
            Logger.getLogger(Bank_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        // </editor-fold>
        
        Main2 thread = new Main2(collection);
        thread.start();
        
        Pool threads = new Pool(POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE);
        
        SSLServerSocket sSock = SSL.getSSLServerSocket(PORT);
        while(true)
        {
            SSLSocket cSock = (SSLSocket)sSock.accept();
            threads.pool.execute(new Task(cSock, collection));
        }
    }
    
}

class Main2 extends Thread
{
    DBCollection collection;
    
    public Main2(DBCollection c)
    {
        collection = c;
    }
    
    @Override
    public void run()
    {
        
        try
        {
            int PORT = 0;
            int POOL_SIZE = 0;
            int MAX_POOL_SIZE = 0;
            int KEEP_ALIVE = 0;
            
            // <editor-fold defaultstate="collapsed" desc="Init Properties">
            /*
            try
            {
                Properties prop = new Properties();
                FileOutputStream fos = new FileOutputStream("Bank_Serveur2.properties");
                prop.setProperty("port", "7373");
                prop.setProperty("poolSize", "3");
                prop.setProperty("maxPoolSize", "5");
                prop.setProperty("keepAlive", "10");
                
                prop.store(fos, null);
            }
            catch(Exception ex){}
            */
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Load Properties">
            try
            {
                Properties prop = new Properties();
                FileInputStream fis = new FileInputStream("Bank_Serveur2.properties");
                prop.load(fis);
                
                PORT = Integer.parseInt(prop.getProperty("port", "7373"));
                POOL_SIZE = Integer.parseInt(prop.getProperty("poosSize", "3"));
                MAX_POOL_SIZE = Integer.parseInt(prop.getProperty("maxPoolSize", "5"));
                KEEP_ALIVE = Integer.parseInt(prop.getProperty("keepAlive", "10"));
                
                fis.close();
            } catch (IOException ex)
            {
                Logger.getLogger(Bank_Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            // </editor-fold>
            
            Pool threads = new Pool(POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE);
            
            ServerSocket sSock = new ServerSocket(PORT);
            while(true)
            {
                Socket cSock = sSock.accept();
                threads.pool.execute(new Task2(cSock, collection));
            }
        }
        catch(IOException ex){Logger.getLogger(Main2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
