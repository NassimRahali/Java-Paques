/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kaoutare
 */
public class Data_Server 
{
    
    public static void main(String[] args) throws IOException 
    {
        //Connection à la base de données BD_CLIENTS
        Connection connexion = null;
        
        try 
        {
            Class driver = Class.forName("com.mysql.jdbc.Driver");
            System.out.println(">> Driver chargé");
            
            connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_clients", "root","root");
            System.out.println(">> Connexion à la BDD BD_CLIENTS réussie");

            ThreadDataServer thServ = new ThreadDataServer(connexion);
            thServ.start();
        } 
        catch (ClassNotFoundException ex) { Logger.getLogger(Data_Server.class.getName()).log(Level.SEVERE, null, ex); } 
        catch (SQLException ex) { Logger.getLogger(Data_Server.class.getName()).log(Level.SEVERE, null, ex); }
        
        
    }   
}
