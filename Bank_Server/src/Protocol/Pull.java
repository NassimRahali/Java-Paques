/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Protocol;

import java.io.Serializable;
import java.util.ArrayList;
import javax.crypto.SealedObject;
import m18.kerberos.tgs.TicketCS;

/**
 *
 * @author Thibault
 */
public class Pull implements Serializable
{
    private static long serialVersionUID = 6;
    private TicketCS ticket;
    private SealedObject authenticator;
    
    private String Name;
    private String Banque;
    private String Valide;
    private ArrayList<String> ids;
    
    // <editor-fold defaultstate="collapsed" desc="GET & SET">
    public String getName()
    {
        return Name;
    }
    
    public void setName(String Name)
    {
        this.Name = Name;
    }
    
    public String getBanque()
    {
        return Banque;
    }
    
    public void setBanque(String Banque)
    {
        this.Banque = Banque;
    }
    
    public String getValide()
    {
        return Valide;
    }
    
    public void setValide(String Valide)
    {
        this.Valide = Valide;
    }

    public ArrayList<String> getIds()
    {
        return ids;
    }

    public void setIds(ArrayList<String> ids)
    {
        this.ids = ids;
    }

    public TicketCS getTicket()
    {
        return ticket;
    }

    public void setTicket(TicketCS ticket)
    {
        this.ticket = ticket;
    }

    public SealedObject getAuthenticator()
    {
        return authenticator;
    }

    public void setAuthenticator(SealedObject authenticator)
    {
        this.authenticator = authenticator;
    }
    // </editor-fold>
}
