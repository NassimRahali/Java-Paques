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
public class Request implements Serializable
{
    private String Name;
    private String Content;
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

    public String getContent()
    {
        return Content;
    }

    public void setContent(String Content)
    {
        this.Content = Content;
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
