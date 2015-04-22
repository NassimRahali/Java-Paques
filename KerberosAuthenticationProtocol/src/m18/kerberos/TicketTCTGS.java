package m18.kerberos;


import java.io.Serializable;
import java.util.Date;

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
    private byte[] kctgsSessionKey;
    private String clientName;
    private Date timeValid;

    public TicketTCTGS(byte[] kctgsSessionKey, String clientName, Date timeValid) {
        this.kctgsSessionKey = kctgsSessionKey;
        this.clientName = clientName;
        this.timeValid = timeValid;
    }

    public byte[] getKctgsSessionKey() {
        return kctgsSessionKey;
    }

    public void setKctgsSessionKey(byte[] kctgsSessionKey) {
        this.kctgsSessionKey = kctgsSessionKey;
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
