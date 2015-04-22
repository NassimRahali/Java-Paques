package m18.kerberos;

/*
 * RAHALI Nassim
 * M18
 * 2014-2015
 */

/**
 *
 * @author Nassim
 */
public class ASReply {
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
