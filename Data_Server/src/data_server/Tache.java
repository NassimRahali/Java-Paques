/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_server;


import guis.SelectGUI;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import protocoleCLIBOP.ReponseCLIBOP;
import protocoleCLIBOP.RequeteCLIBOP;

/**
 *
 * @author Kaoutare
 */
public class Tache implements Runnable
{
    private Socket socketClient;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Connection connexion;
    
    public Tache(Socket sock, Connection c)
    {
        socketClient = sock;
        connexion = c;
    }

    @Override
    public void run() 
    {
        try
        {
            oos = new ObjectOutputStream(socketClient.getOutputStream());
            ois = new ObjectInputStream(socketClient.getInputStream());
            
            if(ois == null || oos == null)
                System.exit(1);
            else
                System.out.println("Serveur en attente");
        
            boolean fin = false;
            
            //Gestion du protocole
            RequeteCLIBOP req = null;
            ReponseCLIBOP rep = null;

            while(fin == false)
            {
           
                req = (RequeteCLIBOP)ois.readObject();

                switch(req.getCmd())
                {
                    case "BEGIN" : 
                    {
                        rep = new ReponseCLIBOP();
                        rep.setCmd("BEGIN");
                        System.out.println("<Connection>");
                    }
                    break;

                    //Demande liste d'opérations
                    case "GETLIST":
                    {   
                        rep = new ReponseCLIBOP();
                        rep.setCmd("GETLIST");
                        
                        try 
                        {
                            Statement instruction = connexion.createStatement();
                            ResultSet rs = instruction.executeQuery("select * from operations where ClientID = (select IdClient from clients where Nom='"+req.getPrenom()+"')\n" +
                                                                    "and  DateOp between '"+req.getDateDebut()+"' and '"+req.getDateFin()+"'");
                            while (rs.next())
                            {
                                String tuple = rs.getString("IdOp") + "#" +  rs.getString("IdOp") + "#" +  rs.getString("ClientID") + "#" + rs.getString("Montant") ;
                                rep.getTuples().add(tuple);
                            }
                        } catch (SQLException ex) { Logger.getLogger(SelectGUI.class.getName()).log(Level.SEVERE, null, ex);}
                        
                        
                    }
                    break;

                    //Valeur moyenne mensuelle des comptes d'un client    
                    case "AVERAGE":
                    {
                        rep = new ReponseCLIBOP();
                        rep.setCmd("AVERAGE");
                        
                        try 
                        {
                            Statement instruction = connexion.createStatement();
                            //A FAIRE
                            ResultSet rs = instruction.executeQuery("select * from operations where ClientID = (select IdClient from clients where Nom='"+req.getPrenom()+"')\n" +
                                                                    "and  DateOp between '"+req.getDateDebut()+"' and '"+req.getDateFin()+"'");
                            while (rs.next())
                            {
                                //A FAIRE
                                String tuple = rs.getString("IdOp") + "#" + rs.getString("ClientID");
                                rep.getTuples().add(tuple);
                            }
                        } catch (SQLException ex) { Logger.getLogger(SelectGUI.class.getName()).log(Level.SEVERE, null, ex);}
                        
                    }
                    break;

                    //Validation d'un compte client 
                    case "VALIDATE":
                    {
                        rep = new ReponseCLIBOP();
                        rep.setCmd("VALIDATE");
                        
                        try 
                        {
                            Statement instruction = connexion.createStatement();
                            
                            ResultSet rs = instruction.executeQuery("select Fiable as Validation from comptes where Fiable = 1 and IdCompte = " + req.getIdCompte()+"and ClientID = (select IdClient from clients where Nom="+req.getPrenom());
                            while (rs.next())
                            {
                                //A FAIRE
                                String tuple = rs.getString("IdOp") + "#" + rs.getString("ClientID");
                                rep.getTuples().add(tuple);
                            }
                        } catch (SQLException ex) { Logger.getLogger(SelectGUI.class.getName()).log(Level.SEVERE, null, ex);}
                            
                    }
                    break;

                    //Demande nombre de débits refusés
                    case "GETDEB":
                    {
                        rep = new ReponseCLIBOP();
                        rep.setCmd("GETDEB");
                        
                        try 
                        {
                            Statement instruction = connexion.createStatement();
                            //A FAIRE
                            ResultSet rs = instruction.executeQuery("select * from operations where ClientID = (select IdClient from clients where Nom='"+req.getPrenom()+"')\n" +
                                                                    "and  DateOp between '"+req.getDateDebut()+"' and '"+req.getDateFin()+"'");
                            while (rs.next())
                            {
                                //A FAIRE
                                String tuple = rs.getString("IdOp") + "#" + rs.getString("ClientID");
                                rep.getTuples().add(tuple);
                            }
                        } catch (SQLException ex) { Logger.getLogger(SelectGUI.class.getName()).log(Level.SEVERE, null, ex);}
                    }
                    break;

                    case "END" : 
                    {
                        rep = new ReponseCLIBOP();
                        rep.setCmd("END");
                        System.out.println("<End connection>");
                    }
                    break;
        
                }
            
                oos.writeObject(rep);
            }
           
            oos.close();
            ois.close();
            socketClient.close();
            
    
        } 
        catch (IOException ex) { Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex); } 
        catch (ClassNotFoundException ex) { Logger.getLogger(Tache.class.getName()).log(Level.SEVERE, null, ex); }
    }
    
}
