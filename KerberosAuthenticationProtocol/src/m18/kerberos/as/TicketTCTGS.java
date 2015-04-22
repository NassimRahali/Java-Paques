    package m18.kerberos.as;


import java.io.Serializable;
import java.util.Date;
import javax.crypto.SecretKey;

/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */


/**
 *
 * @author Nassim
 */
public class TicketTCTGS implements Serializable {
    private SecretKey KCTGS;
    private String clientName;
    private Date timeValid;

    public TicketTCTGS(SecretKey kctgsSessionKey, String clientName, Date timeValid) {
        this.KCTGS = kctgsSessionKey;
        this.clientName = clientName;
        this.timeValid = timeValid;
    }

    public SecretKey getKctgsSessionKey() {
        return KCTGS;
    }

    public void setKctgsSessionKey(SecretKey kctgsSessionKey) {
        this.KCTGS = kctgsSessionKey;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getTimeValid() {
        return timeValid;
    }

    public void setTimeValid(Date timeValid) {
        this.timeValid = timeValid;
    }
    
    
}
