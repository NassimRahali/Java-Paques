package m18.kerberos;

/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */

import java.io.Serializable;
import javax.crypto.SecretKey;

/**
 *
 * @author Nassim
 */
public class KcTGS implements Serializable {

    private SecretKey KCTGSSessionKey;
    private String version;
    private String serverName;

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
}
