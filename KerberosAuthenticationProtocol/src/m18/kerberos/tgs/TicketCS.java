/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package m18.kerberos.tgs;

import java.io.Serializable;
import javax.crypto.SealedObject;

/**
 *
 * @author Nassim
 */
public class TicketCS implements Serializable {
    private String service;
    private SealedObject KCS;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public SealedObject getKCS() {
        return KCS;
    }

    public void setKCS(SealedObject KCS) {
        this.KCS = KCS;
    }
    
}
