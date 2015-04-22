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
public class TGSRequest implements Serializable {

    private static long serialVersionUID = 2;
    private String service;
    SealedObject ticket;
    SealedObject authenticator;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public SealedObject getTicket() {
        return ticket;
    }

    public void setTicket(SealedObject ticket) {
        this.ticket = ticket;
    }

    public SealedObject getAuthenticator() {
        return authenticator;
    }

    public void setAuthenticator(SealedObject authenticator) {
        this.authenticator = authenticator;
    }

}
