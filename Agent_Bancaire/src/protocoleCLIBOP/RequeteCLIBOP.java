/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocoleCLIBOP;

import java.io.Serializable;
import javax.crypto.SealedObject;
import m18.kerberos.tgs.TicketCS;

/**
 *
 * @author Kaoutare
 */
public class RequeteCLIBOP implements Serializable
{
    private TicketCS ticket;
    private SealedObject authenticator;
    
    private boolean isValid = false;
    private String typeOp;
    private String etat;
    private String cmd;
    private String prenom;
    private String dateDebut;
    private String dateFin;
    private int idCompte;

    /**
     * @return the isValid
     */
    public boolean isIsValid() {
        return isValid;
    }

    /**
     * @param isValid the isValid to set
     */
    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    /**
     * @return the typeOp
     */
    public String getTypeOp() {
        return typeOp;
    }

    /**
     * @param typeOp the typeOp to set
     */
    public void setTypeOp(String typeOp) {
        this.typeOp = typeOp;
    }

    /**
     * @return the etat
     */
    public String getEtat() {
        return etat;
    }

    /**
     * @param etat the etat to set
     */
    public void setEtat(String etat) {
        this.etat = etat;
    }

    /**
     * @return the idCompte
     */
    public int getIdCompte() {
        return idCompte;
    }

    /**
     * @param idCompte the idCompte to set
     */
    public void setIdCompte(int idCompte) {
        this.idCompte = idCompte;
    }


    /**
     * @return the cmd
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * @param cmd the cmd to set
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return the dateDebut
     */
    public String getDateDebut() {
        return dateDebut;
    }

    /**
     * @param dateDebut the dateDebut to set
     */
    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    /**
     * @return the dateFin
     */
    public String getDateFin() {
        return dateFin;
    }

    /**
     * @param dateFin the dateFin to set
     */
    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    /**
     * @return the ticket
     */
    public TicketCS getTicket() {
        return ticket;
    }

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(TicketCS ticket) {
        this.ticket = ticket;
    }

    /**
     * @return the authenticator
     */
    public SealedObject getAuthenticator() {
        return authenticator;
    }

    /**
     * @param authenticator the authenticator to set
     */
    public void setAuthenticator(SealedObject authenticator) {
        this.authenticator = authenticator;
    }
}
