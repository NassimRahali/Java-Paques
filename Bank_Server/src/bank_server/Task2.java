/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package bank_server;

import Protocol.Pull;
import Protocol.Push;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import m18.kerberos.exceptions.BadTimestampException;
import m18.kerberos.tgs.AuthenticatorTGS;
import m18.kerberos.tgs.KCS;

/**
 *
 * @author Thibault
 */
public class Task2 implements Runnable
{
    
    private Socket cSock;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private DBCollection collection;
    
    public Task2(Socket s, DBCollection c)
    {
        cSock = s;
        collection = c;
    }
    
    @Override
    public void run()
    {
        try
        {
            SecretKey KS = new SecretKeySpec("cisco789".getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, KS);
            System.out.println("KS " + KS);
            
            oos = new ObjectOutputStream(cSock.getOutputStream());
            System.out.println(oos);
            ois = new ObjectInputStream(cSock.getInputStream());
            System.out.println(ois);
            if(ois == null || oos == null)
                System.exit(1);
            else
                System.out.println("Flux créés");
            
            Boolean fin = false;
            Pull pull = null;
            Push push = null;
            while(fin != true)
            {
                pull = (Pull)ois.readObject();
                cipher.init(Cipher.DECRYPT_MODE, KS);
                SealedObject so = pull.getTicket().getKCS();
                KCS unsealed = null;
                unsealed = (KCS) so.getObject(cipher);
                SecretKey KCS = unsealed.getKcs();
                // Verification timestamp
                long validity = unsealed.getValidity().getTime();
                long now = new Date().getTime();
                if((validity - now) < 0)
                    throw new BadTimestampException();
                else
                    System.out.println("Timestamp valide");
                
                switch(pull.getName())
                {
                    case "Hello":
                    {
                        push = new Push();
                        push.setName("Hello");
                    }break;
                    case "END":
                    {
                        System.out.println("Closing Connection");
                        fin = true;
                    }break;
                    case "recherche":
                    {
                        push = new Push();
                        push.setName("recherche");
                        DBObject query = new BasicDBObject();
                        if(pull.getValide().equalsIgnoreCase("true") || pull.getValide().equalsIgnoreCase("false"))
                            query.put("valide", pull.getValide());
                        if(!pull.getBanque().equals(""))
                            query.put("banque.name", pull.getBanque());
                        
                        DBCursor cursor = collection.find(query);
                        iterateOverCursor(cursor, push);
                        cursor.close();
                    }break;
                    case "validation":
                    {
                        for(int i = 0 ; i < pull.getIds().size() ; i++)
                        {
                            //System.out.println(pull.getIds().get(i));
                            DBObject query = new BasicDBObject("_id", Integer.parseInt(pull.getIds().get(i)));
                            DBObject update = new BasicDBObject();
                            update.put("$set", new BasicDBObject("valide", "true"));
                            collection.update(query, update);
                        }
                        
                        push = new Push();
                        push.setName("validOK");
                        
                    }break;
                }
                cipher.init(Cipher.DECRYPT_MODE, KCS);
                AuthenticatorTGS auth = (AuthenticatorTGS) pull.getAuthenticator().getObject(cipher);
                cipher.init(Cipher.ENCRYPT_MODE, KCS);
                push.setTimestamp(new SealedObject(auth.getCurrentTime(), cipher));
                
                oos.writeObject(push);
            }
            oos.close();
            ois.close();
            cSock.close();
        }
        catch (BadTimestampException ex)
        {
            System.out.println("BadTimeStampExc");
        }
        catch (Exception ex)
        {
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void iterateOverCursor(DBCursor dbCursor, Push push)
    {
        for (DBObject o : dbCursor)
        {
            //System.out.println(o);
            HashMap map = (HashMap)o.toMap();
            push.getIds().add(map.get("_id").toString().replace(".0", ""));
            push.getMontants().add(map.get("montant").toString().replace(".0", ""));
            push.getTypes().add(map.get("type").toString());
            String str = map.get("valide").toString();
            if(str.equals("true"))
                push.getValides().add(Boolean.TRUE);
            else
                push.getValides().add(Boolean.FALSE);
            String tab1[] = map.get("banque").toString().split("\"name\" : \"");
            String tab2[] = tab1[1].split("\"");
            push.getBanques().add(tab2[0]);
        }
    }
}

