package m18.kerberos.as;

/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Nassim
 */
public class KcTGS implements Serializable {

    private SecretKey KCTGSSessionKey;
    private String version;
    private String serverName;

    public KcTGS(String version, String serverName) {
        this.version = version;
        this.serverName = serverName;
    }
    
    public KcTGS(SecretKey secretKey, String version, String serverName) {
        this.KCTGSSessionKey = secretKey;
        this.version = version;
        this.serverName = serverName;
    }

    public SecretKey getKCTGSSessionKey() {
        return KCTGSSessionKey;
    }

    public void SetKCTGSSessionKey(SecretKey sk) {
        this.KCTGSSessionKey = sk;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
    
    public void generateKcTGSKey()
    {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            kg.init(new SecureRandom());
            KCTGSSessionKey = kg.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KcTGS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
