/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_server;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kaoutare
 */
public class ThreadDataServer extends Thread
{
    Connection connexion;
    
    public ThreadDataServer(Connection c)
    {
        connexion = c;
    }
    
    @Override
    public void run()
    { 
        
        try
        {
            int port = 0;
            int taillePool = 0;
            int maxTaillePool = 0;
            int tempsLatence = 0;
        
            //Initialisation des paramètres
            try
            {
                Properties p = new Properties();
                FileInputStream fis = new FileInputStream("ThreadDataServer.properties");
                p.load(fis);

                port = Integer.parseInt(p.getProperty("port"));
                taillePool = Integer.parseInt(p.getProperty("taillePool"));
                maxTaillePool = Integer.parseInt(p.getProperty("maxTaillePool"));
                tempsLatence = Integer.parseInt(p.getProperty("tempsLatence"));

                fis.close();
            }
            catch(IOException ex){ Logger.getLogger(Data_Server.class.getName()).log(Level.SEVERE, null, ex); }
    
            
            ThreadPool th = new ThreadPool(taillePool, maxTaillePool, tempsLatence);
            
            ServerSocket socketServ = new ServerSocket(port);
            while(true)
            {
                Socket socketClient = socketServ.accept();
                th.getPoolThreads().execute(new Tache(socketClient,connexion));
            }
        }
        catch(IOException ex){ Logger.getLogger(ThreadDataServer.class.getName()).log(Level.SEVERE, null, ex); }
    }
}
