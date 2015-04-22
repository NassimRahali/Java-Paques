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
public class ASRequest {

    private String requestName;
    private String clientName;
    private String clientPassword;
    private String clientNetworkAddress;
    private String TGSServerName;

    public ASRequest(String req, String cname, String cpwd, String caddress, String tgsname) {
        this.requestName = req;
        this.clientName = cname;
        this.clientPassword = cpwd;
        this.clientNetworkAddress = caddress;
        this.TGSServerName = tgsname;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPassword() {
        return clientPassword;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    public String getClientNetworkAddress() {
        return clientNetworkAddress;
    }

    public void setClientNetworkAddress(String clientNetworkAddress) {
        this.clientNetworkAddress = clientNetworkAddress;
    }

    public String getTGSServerName() {
        return TGSServerName;
    }

    public void setTGSServerName(String TGSServerName) {
        this.TGSServerName = TGSServerName;
    }

}
