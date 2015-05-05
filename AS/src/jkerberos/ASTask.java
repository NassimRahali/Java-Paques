package jkerberos;

/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */


import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import m18.kerberos.as.ASReply;
import m18.kerberos.as.ASRequest;
import m18.kerberos.as.KcTGS;
import m18.kerberos.as.TicketTCTGS;

/**
 *
 * @author Nassim
 */
public class ASTask implements Runnable {

    private Socket cSock;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private final Boolean endConnection = false;

    public ASTask(Socket cs) {
        this.cSock = cs;
    }

    @Override
    public void run() {
        try {
            oos = new ObjectOutputStream(cSock.getOutputStream());
            ois = new ObjectInputStream(cSock.getInputStream());
            if (oos == null || ois == null) {
                System.err.println("Failed to load streams.");
                System.out.println("System will exit.");
                System.exit(-1);
            }
            System.out.println("Streams created.");
            while (!endConnection) {
                ASRequest asRequest = (ASRequest) ois.readObject();
                switch (asRequest.getRequestName()) {
                    case "INITIAL_REQUEST":
                        System.out.println(asRequest.getClientPassword());
                        // If password is correct.
                        String password = asRequest.getClientPassword();
                        System.out.println(password.getBytes().length);
                        SecretKey KC = new SecretKeySpec(password.getBytes(), "DES");
                        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                        cipher.init(Cipher.ENCRYPT_MODE, KC);
                        
                        KcTGS kctgs = new KcTGS("5", asRequest.getTGSServerName());
                        kctgs.generateKcTGSKey();
                        SealedObject sealedKctgs = new SealedObject(kctgs, cipher);
                        SecretKey KTGS = new SecretKeySpec("cisco123".getBytes(), "DES");
                        Cipher cipher2 = Cipher.getInstance("DES/ECB/PKCS5Padding");
                        cipher2.init(Cipher.ENCRYPT_MODE, KTGS);
                        Date validityDate = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(validityDate);
                        c.add(Calendar.HOUR, 8);
                        TicketTCTGS ticketTCTGS = new TicketTCTGS(kctgs.getKCTGSSessionKey(), asRequest.getClientName(), c.getTime());
                        SealedObject sealedTicket = new SealedObject(ticketTCTGS, cipher2);
                        ASReply asReply = new ASReply(sealedTicket, sealedKctgs);
                        asReply.setStatus("OK");
                        oos.writeObject(asReply);
                        break;
                    default:
                        break;
                }

            }
        }
        catch (EOFException ex)
        {
            System.out.println("Connexion fermée par le client.");
            try {
                cSock.close();
            } catch (IOException ex1) {
                Logger.getLogger(ASTask.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException ex) {
            Logger.getLogger(ASTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

}
