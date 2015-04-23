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
public class TGSReply implements Serializable {

    private static long serialVersionUID = 5;
    private TicketCS ticket;
    private SealedObject KCSkey;

    public TicketCS getTicket() {
        return ticket;
    }

    public void setTicket(TicketCS ticket) {
        this.ticket = ticket;
    }

    public SealedObject getKCSkey() {
        return KCSkey;
    }

    public void setKCSkey(SealedObject KCSkey) {
        this.KCSkey = KCSkey;
    }
}
