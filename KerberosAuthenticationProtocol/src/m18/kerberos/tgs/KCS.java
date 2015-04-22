/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package m18.kerberos.tgs;

import java.io.Serializable;
import java.util.Date;
import javax.crypto.SecretKey;

/**
 *
 * @author Nassim
 */
public class KCS implements Serializable {
    private String clientName;
    private String clientAddress;
    private Date validity;
    private SecretKey kcs;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public SecretKey getKcs() {
        return kcs;
    }

    public void setKcs(SecretKey kcs) {
        this.kcs = kcs;
    }
    
}
