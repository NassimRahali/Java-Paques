package m18.kerberos;

import java.io.Serializable;

/**
 *
 * @author Nassim
 */
public class ASReply implements Serializable {
    private TicketTCTGS ticket;
    private String status = "PWD OK";
    
    public TicketTCTGS getTicket() {
        return ticket;
    }

    public void setTicket(TicketTCTGS ticket) {
        this.ticket = ticket;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
