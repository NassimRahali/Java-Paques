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
public class RequestTGS implements Serializable
{
    private String serverName;
    private byte[] authentificateur;
    private byte[] ticket;
}
