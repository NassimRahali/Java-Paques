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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            oos = new ObjectOutputStream(cSock.getOutputStream());
            ois = new ObjectInputStream(cSock.getInputStream());
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
                oos.writeObject(push);
            }
            oos.close();
            ois.close();
            cSock.close();
        }
        catch (IOException | ClassNotFoundException ex)
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

