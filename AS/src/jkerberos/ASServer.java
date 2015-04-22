/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package jkerberos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Nassim
 */
public class ASServer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        int PORT = 0;
        int POOL_SIZE = 0;
        int MAX_POOL_SIZE = 0;
        int KEEP_ALIVE = 0;
        String KEY_FILENAME_TGS = null;

        try {
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream("ASServer.properties");
            prop.load(fis);

            PORT = Integer.parseInt(prop.getProperty("port", "3800"));
            POOL_SIZE = Integer.parseInt(prop.getProperty("poosSize", "3"));
            MAX_POOL_SIZE = Integer.parseInt(prop.getProperty("maxPoolSize", "5"));
            KEEP_ALIVE = Integer.parseInt(prop.getProperty("keepAlive", "10"));
            KEY_FILENAME_TGS = prop.getProperty("key_filename", "ktgs");
            fis.close();
        } catch (IOException ex) {
            Logger.getLogger(ASServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        Pool threads = new Pool(POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE);

        ServerSocket sSock = new ServerSocket(PORT);
        
        while (true) {
            System.out.println("Passage en accept");
            Socket cSock = sSock.accept();
            threads.pool.execute(new ASTask(cSock));
        }
    }

}
