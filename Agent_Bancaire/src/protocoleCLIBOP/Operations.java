/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocoleCLIBOP;

import java.io.Serializable;

/**
 *
 * @author Kaoutare
 */
public class Operations implements Serializable
{
    private String IdOp;
    private String Montant;
    private String ClientID;
    private String DateOp;
    private String NumCompte;
    private boolean Fiable;

    /**
     * @return the IdOp
     */
    public String getIdOp() {
        return IdOp;
    }

    /**
     * @param IdOp the IdOp to set
     */
    public void setIdOp(String IdOp) {
        this.IdOp = IdOp;
    }

    /**
     * @return the Montant
     */
    public String getMontant() {
        return Montant;
    }

    /**
     * @param Montant the Montant to set
     */
    public void setMontant(String Montant) {
        this.Montant = Montant;
    }

    /**
     * @return the ClientID
     */
    public String getClientID() {
        return ClientID;
    }

    /**
     * @param ClientID the ClientID to set
     */
    public void setClientID(String ClientID) {
        this.ClientID = ClientID;
    }

    /**
     * @return the DateOp
     */
    public String getDateOp() {
        return DateOp;
    }

    /**
     * @param DateOp the DateOp to set
     */
    public void setDateOp(String DateOp) {
        this.DateOp = DateOp;
    }

    /**
     * @return the NumCompte
     */
    public String getNumCompte() {
        return NumCompte;
    }

    /**
     * @param NumCompte the NumCompte to set
     */
    public void setNumCompte(String NumCompte) {
        this.NumCompte = NumCompte;
    }

    /**
     * @return the Fiable
     */
    public boolean isFiable() {
        return Fiable;
    }

    /**
     * @param Fiable the Fiable to set
     */
    public void setFiable(boolean Fiable) {
        this.Fiable = Fiable;
    }
    
}
