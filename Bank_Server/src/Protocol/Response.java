/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocol;

import java.io.Serializable;

/**
 *
 * @author Thibault
 */
public class Response implements Serializable
{
    private String Name;
    private Boolean Status;
    private Boolean Valide;
    private int Montant;

    // <editor-fold defaultstate="collapsed" desc="GET & SET">
    public String getName()
    {
        return Name;
    }

    public void setName(String Name)
    {
        this.Name = Name;
    }

    public Boolean getStatus()
    {
        return Status;
    }

    public void setStatus(Boolean Status)
    {
        this.Status = Status;
    }

    public Boolean getValide()
    {
        return Valide;
    }

    public void setValide(Boolean Valide)
    {
        this.Valide = Valide;
    }

    public int getMontant()
    {
        return Montant;
    }

    public void setMontant(int Montant)
    {
        this.Montant = Montant;
    }
        // </editor-fold>

}
