/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */
package m18.kerberos.tgs;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Nassim
 */
public class AuthenticatorTGS implements Serializable {
    private String clientName;
    private Date currentTime;
    private int checksum;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public int getChecksum() {
        return checksum;
    }

    public void setChecksum(int checksum) {
        this.checksum = checksum;
    }
    
}
