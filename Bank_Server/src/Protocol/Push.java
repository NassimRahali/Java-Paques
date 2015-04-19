/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocol;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Thibault
 */
public class Push implements Serializable
{
    private String Name;
    private ArrayList<String> ids;
    private ArrayList<String> banques;
    private ArrayList<String> types;
    private ArrayList<String> montants;
    private ArrayList<Boolean> valides;
    
    public Push()
    {
        ids = new ArrayList<>();
        banques = new ArrayList<>();
        types = new ArrayList<>();
        montants = new ArrayList<>();
        valides = new ArrayList<>();
    }
    
    // <editor-fold defaultstate="collapsed" desc="GET & SET">
    public String getName()
    {
        return Name;
    }
    
    public void setName(String Name)
    {
        this.Name = Name;
    }

    public ArrayList<String> getIds()
    {
        return ids;
    }

    public void setIds(ArrayList<String> ids)
    {
        this.ids = ids;
    }

    public ArrayList<String> getBanques()
    {
        return banques;
    }

    public void setBanques(ArrayList<String> banques)
    {
        this.banques = banques;
    }

    public ArrayList<String> getTypes()
    {
        return types;
    }

    public void setTypes(ArrayList<String> types)
    {
        this.types = types;
    }

    public ArrayList<String> getMontants()
    {
        return montants;
    }

    public void setMontants(ArrayList<String> montants)
    {
        this.montants = montants;
    }

    public ArrayList<Boolean> getValides()
    {
        return valides;
    }

    public void setValides(ArrayList<Boolean> valides)
    {
        this.valides = valides;
    }
    // </editor-fold>

}
