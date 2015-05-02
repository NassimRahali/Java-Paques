/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

import java.io.*;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Kaoutare
 */
public class SignatureECDSA 
{
    private static String codeProvider =  "BC";//"CryptixCrypto"; //
    private static final SecureRandom prng = new SecureRandom();
    
    static
    {
        Security.addProvider(new BouncyCastleProvider());
    }
    
    public static void main(String args[])
    {
        try
        { 
            // Génération des clés
            System.out.println(">> Obtention d'un generateur de cle\n");
            //String curve = "secp192k1";
            String curve = "sect163k1";
            KeyPairGenerator genCles = KeyPairGenerator.getInstance("ECDSA", codeProvider);

            System.out.println(">> Initialisation du generateur de cle\n");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec(curve);
            genCles.initialize(ecSpec, new SecureRandom());
            
            System.out.println(">> Obtention de cles");
            KeyPair deuxCles = genCles.generateKeyPair();
            PublicKey clePublique = deuxCles.getPublic();
            PrivateKey clePrivee = deuxCles.getPrivate();
            System.out.println("\t >> Cle publique generee = " + clePublique);
            System.out.println("\t >> Cle privee generee = " + clePrivee);
            
            // Sérialisation de clés
            System.out.println(">> Serialisation de la cle publique generee");
            ObjectOutputStream clePubliqueFich = new ObjectOutputStream(new FileOutputStream(".\\xp.key"));
            System.out.println("\t >> Fichier ouvert");
            clePubliqueFich.writeObject(clePublique);
            System.out.println("\t >> Cle ecrite");
            clePubliqueFich.close();
            System.out.println("\t >> Fichier ferme\n");
            
            System.out.println(">> Serialisation de la cle privee generee");
            ObjectOutputStream clePriveeFich = new ObjectOutputStream(new FileOutputStream(".\\xs.ser"));
            System.out.println("\t >> Fichier ouvert");
            clePriveeFich.writeObject(clePrivee);
            System.out.println("\t >> Cle ecrite");
            clePriveeFich.close();
            System.out.println("\t >> Fichier ferme\n");
            
            //Signature d'un message
            System.out.println(">> Signature du message");
            System.out.println("\t >> Cle privee recuperee = " + clePrivee.toString());
            Signature s = Signature. getInstance("SHA1withECDSA",codeProvider);
            s.initSign(clePrivee);
            s.update("kaou".getBytes());
            byte[] signature = s.sign();
            System.out.println("\t >> Signature = " + new String(signature));
            System.out.println("\t >> Longueur de la signature = " + signature.length+"\n");
            
            //Vérification de la signature
            System.out.println(">> Vérification de la signature");
            System.out.println("\t >> Cle publique recuperee = "+clePublique.toString());
            Signature s1 = Signature.getInstance ("SHA1withECDSA", codeProvider);
            s1.initVerify(clePublique);
            s1.update("kaou".getBytes());
            
            boolean ok = s1.verify(signature);
            String reponse= null;
            if (ok) 
                reponse = new String("\t >> Signature testee avec succes");
            else 
                reponse = new String("\t>> Signature testee sans succes");
            
            System.out.println(reponse);
            System.out.println("\n>> Opérations terminées ");
            
            System.exit(0);
        }
        catch (NoSuchAlgorithmException e){ System.out.println(e.getMessage()); }
        catch (FileNotFoundException e){ System.out.println(e.getMessage()); }
        catch (Exception e){ System.out.println(e.getMessage()+ " -- " + e.getClass()); }

    }
}
