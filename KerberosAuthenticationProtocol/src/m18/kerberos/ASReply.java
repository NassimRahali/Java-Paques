package m18.kerberos;

import java.io.Serializable;
import javax.crypto.SealedObject;

/**
 *
 * @author Nassim
 */
public class ASReply implements Serializable {
    private static long serialVersionUID = 0;
    private SealedObject ticket;
    private SealedObject KCTGS;
    private String status = "PWD OK";

    public ASReply()
    {}
    
    public ASReply(SealedObject ticket, SealedObject KCTGS) {
        this.ticket = ticket;
        this.KCTGS = KCTGS;
    }
    
    public SealedObject getTicket() {
        return ticket;
    }

    public void setTicket(SealedObject ticket) {
        this.ticket = ticket;
    }

    public SealedObject getKCTGS() {
        return KCTGS;
    }

    public void setKCTGS(SealedObject KCTGS) {
        this.KCTGS = KCTGS;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
