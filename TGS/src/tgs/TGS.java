/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package tgs;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thibault
 */
public class TGS
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
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
            FileOutputStream fos = new FileOutputStream("TGS.properties");
            prop.setProperty("port", "3838");
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
                FileInputStream fis = new FileInputStream("TGS.properties");
                prop.load(fis);
                
                PORT = Integer.parseInt(prop.getProperty("port", "3838"));
                POOL_SIZE = Integer.parseInt(prop.getProperty("poosSize", "3"));
                MAX_POOL_SIZE = Integer.parseInt(prop.getProperty("maxPoolSize", "5"));
                KEEP_ALIVE = Integer.parseInt(prop.getProperty("keepAlive", "10"));
                
                fis.close();
            } catch (IOException ex)
            {
                Logger.getLogger(TGS.class.getName()).log(Level.SEVERE, null, ex);
            }
            // </editor-fold>
            
            Pool threads = new Pool(POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE);
            
            ServerSocket sSock = new ServerSocket(PORT);
            while(true)
            {
                Socket cSock = sSock.accept();
                threads.pool.execute(new Task(cSock));
            }
        } catch (IOException ex)
        {
            Logger.getLogger(TGS.class.getName()).log(Level.SEVERE, null, ex);
        }
        // </editor-fold>
    }
    
}
