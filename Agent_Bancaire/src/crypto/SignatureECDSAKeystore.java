/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

//import cryptix.jce.provider.CryptixCrypto;
import java.io.*;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Kaoutare
 */
public class SignatureECDSAKeystore 
{
    private static String codeProvider =  "BC";//"CryptixCrypto"; //
    private static final SecureRandom prng = new SecureRandom();
    
    static
    {
        Security.addProvider(new BouncyCastleProvider());
        //Security.addProvider(new CryptixCrypto());
        
    }
    
    public static void main(String args[])
    {
        try
        { 
            KeyStore ks = null;
            ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("comptableKaou_keystore.jks"),"cisco12".toCharArray());

            System.out.println(">> Récupération de la clé privée\n");
            PrivateKey clePrivee;
            clePrivee = (PrivateKey) ks.getKey("comptableKaou", "cisco12".toCharArray());
            System.out.println("\t >> Cle privee recuperee = " + clePrivee.toString());
           
            //Signature d'un message
            System.out.println("\n >> Signature du message");
            System.out.println("\n\t >> 1. Instanciation de la signature");
            Signature s = Signature. getInstance("SHA1withECDSA",codeProvider);
            System.out.println("\t >> 2. Initialisation de la signature");
            s.initSign(clePrivee);
            System.out.println("\t >> 3. Ajout du message à la signature");
            s.update("kaou".getBytes());
            System.out.println("\t >> 4. Génération de la signature");
            byte[] signature = s.sign();
            System.out.println("\t\t >> Signature = " + new String(signature));
            System.out.println("\t\t >> Longueur de la signature = " + signature.length+"\n");
            
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
            sVerif.update("kaou".getBytes());
            
            boolean ok = sVerif.verify(signature);
            String reponse= null;
            if (ok) 
                reponse = new String("\t >> Signature testee avec succes");
            else 
                reponse = new String("\t >> Signature testee sans succes");
            
            System.out.println(reponse);
            System.out.println("\n >> Opérations terminées ");
            
            System.exit(0);
        }
        catch (NoSuchAlgorithmException e){ System.out.println(e.getMessage()); }
        catch (FileNotFoundException e){ System.out.println(e.getMessage()); }
        catch (Exception e){ System.out.println(e.getMessage()+ " -- " + e.getClass()); }

    }
}
