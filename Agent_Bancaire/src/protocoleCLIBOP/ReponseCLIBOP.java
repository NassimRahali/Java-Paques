/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocoleCLIBOP;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Vector;
import javax.crypto.SealedObject;

/**
 *
 * @author Kaoutare
 */
public class ReponseCLIBOP implements Serializable
{
    private boolean isValid = false;
    private String typeOp;
    private String etat;
    private String cmd;
    
    private SealedObject timestamp;
    
    private Vector<Operations> tuples;

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
     * @return the timestamp
     */
    public SealedObject getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(SealedObject timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the tuples
     */
    public Vector<Operations> getTuples() {
        return tuples;
    }

    /**
     * @param tuples the tuples to set
     */
    public void setTuples(Vector<Operations> tuples) {
        this.tuples = tuples;
    }

   
}
