/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_server;


import protocoleCLIBOP.Operations;
import guis.SelectGUI;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import m18.kerberos.exceptions.BadTimestampException;
import m18.kerberos.tgs.AuthenticatorTGS;
import m18.kerberos.tgs.KCS;
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
    private Connection connexion;
    
    public Tache(Socket sock, Connection c)
    {
        socketClient = sock;
        connexion = c;
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
            
            oos = new ObjectOutputStream(socketClient.getOutputStream());
            ois = new ObjectInputStream(socketClient.getInputStream());
            
            if(ois == null || oos == null)
                System.exit(1);
            else
                System.out.println(">>Serveur en attente");
        
            boolean fin = false;
            
            //Gestion du protocole
            RequeteCLIBOP req = null;
            ReponseCLIBOP rep = null;

            while(fin == false)
            {
           
                req = (RequeteCLIBOP)ois.readObject();
                
                cipher.init(Cipher.DECRYPT_MODE, KS);
                SealedObject so = req.getTicket().getKCS();
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
                        Operations op = new Operations();
                        
                        try 
                        {
                            Statement instruction = connexion.createStatement();
                            ResultSet rs = instruction.executeQuery("select * from operations where ClientID = (select IdClient from clients where Nom='"+req.getPrenom()+"')\n" +
                                                                    "and  DateOp between '"+req.getDateDebut()+"' and '"+req.getDateFin()+"'");
                            Vector<Operations> v = new Vector();
                            
                            while(rs.next())
                            {
                                op.setIdOp(rs.getString("IdOp"));
                                op.setClientID(rs.getString("ClientID"));
                                op.setMontant(rs.getString("Montant"));
                                op.setDateOp(rs.getString("DateOp"));
                                
                                v.add(op);
                            }
                            
                            rep.setTuples(v);
                            
                        } catch (SQLException ex) { Logger.getLogger(SelectGUI.class.getName()).log(Level.SEVERE, null, ex);}
                   
                    }
                    break;

                    //Valeur moyenne mensuelle des comptes d'un client    
                    case "AVERAGE":
                    {
                        rep = new ReponseCLIBOP();
                        rep.setCmd("AVERAGE");
                        
                        try 
                        {
                            Statement instruction = connexion.createStatement();
                            //A FAIRE
                            ResultSet rs = instruction.executeQuery("select * from operations where ClientID = (select IdClient from clients where Nom='"+req.getPrenom()+"')\n" +
                                                                    "and  DateOp between '"+req.getDateDebut()+"' and '"+req.getDateFin()+"'");
//                            rep.setTuples(rs);
                        } catch (SQLException ex) { Logger.getLogger(SelectGUI.class.getName()).log(Level.SEVERE, null, ex);}
                        
                    }
                    break;

                    //Validation d'un compte client 
                    case "VALIDATE":
                    {
                        rep = new ReponseCLIBOP();
                        rep.setCmd("VALIDATE");
                        
                        Operations op = new Operations();
                        try 
                        {
                            Statement instruction = connexion.createStatement();
                            
                            ResultSet rs = instruction.executeQuery("select * from comptes where ClientID = (select IdClient from clients where Nom="+req.getPrenom());
                            
                            Vector<Operations> v = new Vector();
                            
                            while(rs.next())
                            {
                                op.setNumCompte("NumCompte");
                                op.setClientID(rs.getString("ClientID"));
                                op.setFiable(rs.getBoolean("Fiable"));
                                
                                v.add(op);
                            }
                            
                            rep.setTuples(v);
                            
                        } catch (SQLException ex) { Logger.getLogger(SelectGUI.class.getName()).log(Level.SEVERE, null, ex);}
                        
                        
                    }
                    break;

                    //Demande nombre de débits refusés
                    case "GETDEB":
                    {
                        rep = new ReponseCLIBOP();
                        rep.setCmd("GETDEB");
                        
                        try 
                        {
                            Statement instruction = connexion.createStatement();
                            //A FAIRE
                            ResultSet rs = instruction.executeQuery("select * from operations where ClientID = (select IdClient from clients where Nom='"+req.getPrenom()+"')\n" +
                                                                    "and  DateOp between '"+req.getDateDebut()+"' and '"+req.getDateFin()+"'");
                            
//                            rep.setTuples(rs);
                        } catch (SQLException ex) { Logger.getLogger(SelectGUI.class.getName()).log(Level.SEVERE, null, ex);}
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
                
                cipher.init(Cipher.DECRYPT_MODE, KCS);
                AuthenticatorTGS auth = (AuthenticatorTGS) req.getAuthenticator().getObject(cipher);
                cipher.init(Cipher.ENCRYPT_MODE, KCS);
                rep.setTimestamp(new SealedObject(auth.getCurrentTime(), cipher));
                
                oos.writeObject(rep);
            }
           
            oos.close();
            ois.close();
            socketClient.close();
     
        } 
        catch (IOException ex) { Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex); } 
        catch (ClassNotFoundException ex) { Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex); } catch (InvalidKeyException ex) {
            Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadTimestampException ex) {
            Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
