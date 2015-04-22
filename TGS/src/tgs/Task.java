/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tgs;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import m18.kerberos.as.TicketTCTGS;
import m18.kerberos.exceptions.BadChecksumException;
import m18.kerberos.exceptions.BadTimestampException;
import m18.kerberos.tgs.AuthenticatorTGS;
import m18.kerberos.tgs.KCS;
import m18.kerberos.tgs.TGSReply;
import m18.kerberos.tgs.TGSRequest;
import m18.kerberos.tgs.TicketCS;
import org.joda.time.DateTime;
import org.joda.time.Period;

/**
 *
 * @author Thibault
 */
public class Task implements Runnable
{
    private Socket cSock;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    private SecretKey KCTGS;
    
    public Task(Socket s)
    {
        cSock = s;
    }

    @Override
    public void run()
    {
        try
        {
            oos = new ObjectOutputStream(cSock.getOutputStream());
            ois = new ObjectInputStream(cSock.getInputStream());
                        
            // Chargement clé symétrique KTGS
            SecretKey KTGS = new SecretKeySpec("cisco123".getBytes(), "DES");
            
            // Récupération de l'objet
            TGSRequest TGSreq = (TGSRequest)ois.readObject();
            
            // Décryptage de l'objet TGSRequest avec la clé symétrique KTGS
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, KTGS);
            TicketTCTGS ticket = (TicketTCTGS)TGSreq.getTicket().getObject(cipher);
            
            // Récupération clé de session KCTGS
            KCTGS = ticket.getKctgsSessionKey();
            
            // Reinitialisation du cipher avec la KCTGS
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, KCTGS);
            
            // Décryptage de l'authentificateur
            AuthenticatorTGS auth = (AuthenticatorTGS)TGSreq.getAuthenticator().getObject(cipher);
            
            // Vérification checksum
            if(TGSreq.getTicket().hashCode() != auth.getChecksum())
                throw new BadChecksumException();
            
            // Verification timestamp
            DateTime valid = new DateTime(ticket.getTimeValid().getTime());
            DateTime current = new DateTime(auth.getCurrentTime().getTime());
            Period diff = new Period(valid, current);
            if(diff.getHours() > 8)
                throw new BadTimestampException();
            
            // Construction TGS Reply
            SecretKey kcsKey = new SecretKeySpec("cisco456".getBytes(), "DES");
            SecretKey KS = new SecretKeySpec("cisco789".getBytes(), "DES");
            
            KCS kcs = new KCS();
            kcs.setClientName(auth.getClientName());
            kcs.setValidity(ticket.getTimeValid());
            kcs.setKcs(kcsKey);
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, KS);
            SealedObject soKCS = new SealedObject(kcs, cipher);
            
            TicketCS tick = new TicketCS();
            tick.setService(TGSreq.getService());
            tick.setKCS(soKCS);
            
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, KCTGS);
            
            SealedObject soKCSkeyOnly = new SealedObject(kcsKey, cipher);
            
            TGSReply TGSrep = new TGSReply();
            TGSrep.setKCSkey(soKCSkeyOnly);
            TGSrep.setTicket(tick);
            
            oos.writeObject(TGSrep);
            
        } 
        catch (BadChecksumException ex)
        {
        
        }
        catch (BadTimestampException ex)
        {
        
        }
        catch (Exception ex)
        {
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
