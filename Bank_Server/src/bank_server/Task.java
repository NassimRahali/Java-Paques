/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package bank_server;

import Protocol.Request;
import Protocol.Response;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author Thibault
 */
public class Task implements Runnable
{
    
    private SSLSocket cSock;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private DBCollection collection;
    
    public Task(SSLSocket s, DBCollection c)
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
            
            List names = collection.distinct("banque.name");
            List idsTMP = collection.distinct("_id");
            Set<Integer> ids = new HashSet<>();
            
            for (Object id : idsTMP)
                ids.add(Integer.parseInt(id.toString().replace(".0", "")));
            
            Request req = (Request)ois.readObject();
            Response rep = new Response();
            rep.setName(req.getName());
            Boolean fin = null;
            String banque = null; int IDBanque = 0;
            if(names.contains(req.getContent()))
            {
                rep.setStatus(true);
                oos.writeObject(rep);
                fin = false;
                banque = req.getContent();
                DBObject proj = new BasicDBObject("banque._id", 1);
                DBObject query = new BasicDBObject("banque.name", banque);
                DBObject doc = collection.findOne(query, proj);
                String str1[] = doc.toString().split("\"banque\" : \\{ \"_id\" : ");
                String str2[] = str1[1].split(".0}");
                IDBanque = Integer.parseInt(str2[0]);
                System.out.println(banque + " : " + IDBanque);
            }
            else
            {
                rep.setStatus(false);
                oos.writeObject(rep);
                fin = true;
            }
            
            while(fin != true)
            {
                req = (Request)ois.readObject();
                DBObject document = new BasicDBObject();
                
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(d);
                
                switch(req.getName())
                {
                    case "END":
                    {
                        System.out.println("Closing Connection");
                        fin = true;
                    }break;
                    case "credit":
                    {
                        Integer id = 0;
                        while(ids.contains(id))
                            id++;
                        ids.add(id);
                        System.out.println("id choisi : " + id);
                        document.put("_id", id);
                        document.put("type", "credit");
                        document.put("montant", req.getMontant());
                        document.put("valide", "false");
                        document.put("date", date);
                        document.put("banque", new BasicDBObject("_id", IDBanque).append("name", banque));
                        collection.insert(document);
                        
                        rep = new Response();
                        rep.setName(req.getName());
                        rep.setStatus(true);
                    }break;
                    case "debit":
                    {
                        Integer id = 0;
                        while(ids.contains(id))
                            id++;
                        ids.add(id);
                        System.out.println("id choisi : " + id);
                        document.put("_id", id);
                        document.put("type", "debit");
                        document.put("montant", req.getMontant());
                        document.put("valide", "false");
                        document.put("date", date);
                        document.put("banque", new BasicDBObject("_id", IDBanque).append("name", banque));
                        collection.insert(document);
                        
                        rep = new Response();
                        rep.setName(req.getName());
                        rep.setStatus(true);
                    }break;
                    case "etat":
                    {
                        rep = new Response();
                        rep.setName(req.getName());
                        try
                        {
                            Integer id = Integer.parseInt(req.getContent());
                            if(!ids.contains(id))
                                throw new NumberFormatException();
                            document = collection.findOne(new BasicDBObject("_id", id));
                            System.out.println(document);
                            String[] str1 = document.toString().split("\"name\" : \"");
                            String[] str2 = str1[1].split("\"");
                            if(banque.equalsIgnoreCase(str2[0]))
                            {
                                rep.setStatus(true);
                                str1 = document.toString().split("\"montant\" : ");
                                str2 = str1[1].split(" , ");
                                rep.setMontant(Integer.parseInt(str2[0].replace(".0", "")));
                                str1 = document.toString().split("\"valide\" : \"");
                                str2 = str1[1].split("\"");
                                if(str2[0].equalsIgnoreCase("true"))
                                    rep.setValide(true);
                                else
                                    rep.setValide(false);
                            }
                            else
                            {
                                rep.setStatus(false);
                                rep.setValide(false);
                            }
                        }
                        catch(NumberFormatException ex)
                        {
                            //System.out.println("Etat : bad id request");
                            rep.setStatus(false);
                            rep.setValide(true);
                        }
                    }break;
                }
                oos.writeObject(rep);
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
    
}
