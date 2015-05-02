/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_server;


import protocoleCLIBOP.Operations;
import guis.SelectGUI;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;
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
import org.bouncycastle.jce.provider.BouncyCastleProvider;
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
    private static String codeProvider =  "BC";//"CryptixCrypto"; 
    
    static
    {
        Security.addProvider(new BouncyCastleProvider());
        //Security.addProvider(new CryptixCrypto());
        
    }
    
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
                System.out.println(">> Serveur en attente");
        
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
                        
                        
                        try 
                        {
                            Statement instruction = connexion.createStatement();
                            ResultSet rs = instruction.executeQuery("select * from operations where ClientID = (select IdClient from clients where Nom='"+req.getPrenom()+"')"
                                                                    + "and  DateOp between '"+req.getDateDebut()+"' and '"+req.getDateFin()+"'");
                            Vector<Operations> v = new Vector();
                            
                            while(rs.next())
                            {
                                Operations op = new Operations();
                                
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
                            
                            ResultSet rs = instruction.executeQuery("select avg(Montant) as Montant, month(DateOp) as Month from operations where ClientID = (select IdClient from clients where Nom='"+req.getPrenom()+"') group by month(DateOp)");
                            Vector<Operations> v = new Vector();
                            
                            while(rs.next())
                            {
                                Operations op = new Operations();
                                
                                op.setDateOp(rs.getString("Month"));
                                op.setClientID(req.getPrenom());
                                op.setMontant(rs.getString("Montant"));
                                
                                v.add(op);

                            }
                            
                            rep.setTuples(v);
                        } catch (SQLException ex) { Logger.getLogger(SelectGUI.class.getName()).log(Level.SEVERE, null, ex);}
                        
                    }
                    break;

                    //Affichage pour validation d'un compte client 
                    case "VALIDATE":
                    {
                        rep = new ReponseCLIBOP();
                        rep.setCmd("VALIDATE");
            
                        try 
                        {
                            Statement instruction = connexion.createStatement();

                            ResultSet rs = instruction.executeQuery("select * from comptes where ClientID = (select IdClient from clients where Nom='"+req.getPrenom()+"')");

                            Vector<Operations> v = new Vector();

                            while(rs.next())
                            {
                                Operations op = new Operations();

                                op.setNumCompte(rs.getString("NumCompte"));
                                op.setClientID(rs.getString("ClientID"));

                                op.setFiable(rs.getBoolean("Fiable"));


                                v.add(op);
                            }

                            rep.setTuples(v);
                            rep.setIsUpdate(false);

                        } catch (SQLException ex) { Logger.getLogger(SelectGUI.class.getName()).log(Level.SEVERE, null, ex);}
              
                    }
                    break;

                    //Affichage pour validation d'un compte client 
                    case "UPDATE":
                    { 
                        rep = new ReponseCLIBOP();
                        rep.setCmd("UPDATE");
                        
                        
                        //Vérification de la signature
                        KeyStore ksv = null;
                        ksv = KeyStore.getInstance("PKCS12", codeProvider);
                        ksv.load(new FileInputStream("validationCompte_keystore.p12"),"cisco12".toCharArray());

                        System.out.println(">> Vérification de la signature");
                        System.out.println("\t >> Recuperation du certificat");
                        X509Certificate certif = (X509Certificate)ksv.getCertificate("comptableKaouValid");
                        System.out.println("\t >> Recuperation de la cle publique");
                        PublicKey clePublique = certif.getPublicKey();
                        System.out.println("\t\t >> Cle publique recuperee = "+clePublique.toString());
                        Signature sVerif = Signature.getInstance ("SHA1withECDSA", codeProvider);
                        sVerif.initVerify(clePublique);
                        //sVerif.update(req.getComptable().getBytes());
                        sVerif.update("kaou".getBytes());

                        boolean ok = sVerif.verify(req.getSignature());
                        String reponse= null;
                        if (ok)
                        {
                            reponse = new String("\t >> Signature testee avec succes");
                            rep.setIsUpdate(true);
                            try 
                            {
                                for (Map.Entry<String,String> e : req.getListUpdates().entrySet())
                                {
                                    String query = "update comptes SET Fiable = ? WHERE NumCompte = ?";
                                    PreparedStatement preparedStmt = connexion.prepareStatement(query);
                                    preparedStmt.setBoolean(1, Boolean.parseBoolean(e.getValue()));
                                    preparedStmt.setString(2,e.getKey());

                                    preparedStmt.executeUpdate();
                                    System.out.println(">> UPDATE effectuée !");  
                                }

                                rep.setCmd("UPDATE");

                            } catch (SQLException ex) { Logger.getLogger(SelectGUI.class.getName()).log(Level.SEVERE, null, ex);}
                        }
                            
                        else
                        {
                            reponse = new String("\t >> Signature testee sans succes");
                            rep.setIsUpdate(false);
                        }

                        System.out.println(reponse);
                        System.out.println("\n >> Opérations terminées ");
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
                            
                            ResultSet rs = instruction.executeQuery("select dr.Refuse as NombreRefuse from "
                                                                        + "("
                                                                            + "select ClientID, year(DateOp) as Year, count(Traitee) as Refuse from operations where Traitee = 0 group by ClientID, year(DateOp) order by ClientID"
                                                                        + ") dr "
                                                                    + "where Year ='" + req.getAnnee()+ "'and ClientID = (select IdClient from clients where Nom='"+ req.getPrenom()+"')");
                            Vector<Operations> v = new Vector();
                            
                            while(rs.next())
                            {
                                Operations op = new Operations();
                                
                                op.setDateOp(req.getAnnee());
                                op.setNbrRefusees(rs.getString("NombreRefuse"));
                                op.setClientID(req.getPrenom());
                                v.add(op);
                            }
                            
                            rep.setTuples(v);
                            
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
        } catch (KeyStoreException ex) {
            Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
