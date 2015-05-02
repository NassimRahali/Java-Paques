/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package agent_bancaire;

import GUI.Connexion;
import GUI.SignatureGUI;
import Protocol.Pull;
import Protocol.Push;
import java.awt.Color;
import java.awt.Component;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.System.exit;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.table.DefaultTableModel;
import m18.kerberos.as.ASReply;
import m18.kerberos.as.ASRequest;
import m18.kerberos.as.KcTGS;
import m18.kerberos.tgs.AuthenticatorTGS;
import m18.kerberos.tgs.TGSReply;
import m18.kerberos.tgs.TGSRequest;
import m18.kerberos.tgs.TicketCS;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import protocoleCLIBOP.Operations;
import protocoleCLIBOP.ReponseCLIBOP;
import protocoleCLIBOP.RequeteCLIBOP;

/**
 *
 * @author Thibault
 */
public class Agent_Bancaire extends javax.swing.JFrame
{
    
    private Socket cSock1;
    private ObjectInputStream ois1;
    private ObjectOutputStream oos1;
    private int PORT1;
    private String IP1;
    private String IPAS;
    private int PORTAS;
    private String IPTGS;
    private int PORTTGS;
    
    private KcTGS KCTGS;
    
    private TicketCS Ticket;
    private SealedObject Authentificateur;
    
    private SecretKey KCS;
    private AuthenticatorTGS auth;
    
    //Communication avec Data_Server
    private Socket socketClientDS;
    private ObjectInputStream oisDS;
    private ObjectOutputStream oosDS;
    private int portDS;
    private String ipDS;
    private String ipAsDS;
    private int portAsDS;
    private String ipTgsDS;
    private int portTgsDS;
    
    private DefaultTableModel dmOp;
    private DefaultTableModel dmAvg;
    private DefaultTableModel dmDeb;
    private DefaultTableModel dmValid;
    
    private static String codeProvider =  "BC";//"CryptixCrypto";
    
    static
    {
        Security.addProvider(new BouncyCastleProvider());
        //Security.addProvider(new CryptixCrypto());
    }
    
    public Agent_Bancaire()
    {
        initComponents();
        
        // <editor-fold defaultstate="collapsed" desc="Init Properties 1">
        /*
        try
        {
        Properties prop = new Properties();
        FileOutputStream fos = new FileOutputStream("Agent_Bancaire_1.properties");
        prop.setProperty("port", "7373");
        prop.setProperty("ip", "127.0.0.1");
        prop.setProperty("ipAS", "10.43.14.13");
        prop.setProperty("portAS", "3800");
        prop.setProperty("ipTGS", "127.0.0.1");
        prop.setProperty("portTGS", "3838");
        
        prop.store(fos, null);
        }
        catch(Exception ex){}
        */
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Load Properties 1">
        try
        {
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream("Agent_Bancaire_1.properties");
            prop.load(fis);
            
            PORT1 = Integer.parseInt(prop.getProperty("port", "7373"));
            IP1 = prop.getProperty("ip", "127.0.0.1");
            PORTAS = Integer.parseInt(prop.getProperty("portAS", "3800"));
            IPAS = prop.getProperty("ipAS", "127.0.0.1");
            PORTTGS = Integer.parseInt(prop.getProperty("portTGS", "3838"));
            IPTGS = prop.getProperty("ipTGS", "127.0.0.1");
            
            
            fis.close();
        } catch (IOException ex)
        {
            Logger.getLogger(Agent_Bancaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        // </editor-fold>
        
        //Initialisation des paramètres
        try
        {
            Properties p = new Properties();
            FileInputStream fis = new FileInputStream("AgentBancaireDS.properties");
            p.load(fis);

            portDS = Integer.parseInt(p.getProperty("portDS"));
            ipDS = (p.getProperty("ip"));

            fis.close();
        }
        catch(IOException ex){ Logger.getLogger(Agent_Bancaire.class.getName()).log(Level.SEVERE, null, ex); }
        
        
        this.ButConnexion.setBackground(Color.WHITE);
        this.ButSoumettre.setBackground(Color.WHITE);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TPPrinc = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        p1Ctrl = new javax.swing.JPanel();
        b1Connexion = new javax.swing.JButton();
        Status1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tf1Banque = new javax.swing.JTextField();
        b1Valider = new javax.swing.JButton();
        b1Recherche = new javax.swing.JButton();
        cb1Valide = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tOperations = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        PanCtrl = new javax.swing.JPanel();
        ButConnexion = new javax.swing.JButton();
        ButSoumettre = new javax.swing.JButton();
        ComboCmd = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TextFPrenom = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TextFDateFin = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        TextFDateDeb = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        TextFAnnee = new javax.swing.JTextField();
        ButValidation = new javax.swing.JButton();
        PanRequetes = new javax.swing.JPanel();
        PanReqOp = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabOp = new javax.swing.JTable();
        PanReqAverage = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabAverage = new javax.swing.JTable();
        PanReqValidate = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TabValidate = new javax.swing.JTable();
        PanReqDebit = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TabDebit = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        p1Ctrl.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contrôles", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        b1Connexion.setText("Connexion");
        b1Connexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ConnexionActionPerformed(evt);
            }
        });

        Status1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Status1.setOpaque(true);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Banque");
        jLabel1.setEnabled(false);

        tf1Banque.setEnabled(false);

        b1Valider.setText("Valider");
        b1Valider.setEnabled(false);
        b1Valider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1ValiderActionPerformed(evt);
            }
        });

        b1Recherche.setText("Recherche");
        b1Recherche.setEnabled(false);
        b1Recherche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b1RechercheActionPerformed(evt);
            }
        });

        cb1Valide.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Any", "True", "False" }));
        cb1Valide.setEnabled(false);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Validé");
        jLabel2.setEnabled(false);

        javax.swing.GroupLayout p1CtrlLayout = new javax.swing.GroupLayout(p1Ctrl);
        p1Ctrl.setLayout(p1CtrlLayout);
        p1CtrlLayout.setHorizontalGroup(
            p1CtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p1CtrlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p1CtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b1Connexion, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(p1CtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(p1CtrlLayout.createSequentialGroup()
                        .addComponent(tf1Banque, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cb1Valide, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Status1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(p1CtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(b1Valider, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(b1Recherche, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p1CtrlLayout.setVerticalGroup(
            p1CtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p1CtrlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p1CtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p1CtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(b1Connexion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Status1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(b1Recherche))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(p1CtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(p1CtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tf1Banque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(b1Valider)
                        .addComponent(cb1Valide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        tOperations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Banque", "Type", "Montant", "Validé"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tOperations);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p1Ctrl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(p1Ctrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        TPPrinc.addTab("Bank Server", jPanel1);

        ButConnexion.setText("Connexion");
        ButConnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButConnexionActionPerformed(evt);
            }
        });

        ButSoumettre.setText("Soumettre");
        ButSoumettre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButSoumettreActionPerformed(evt);
            }
        });

        ComboCmd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "GETLIST", "AVERAGE", "VALIDATE", "GETDEB" }));

        jLabel3.setText("Commande");

        jLabel4.setText("Prénom");

        jLabel5.setText("Date : du");

        jLabel6.setText("au");

        jLabel8.setText("Année");

        ButValidation.setText("Valider");
        ButValidation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButValidationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanCtrlLayout = new javax.swing.GroupLayout(PanCtrl);
        PanCtrl.setLayout(PanCtrlLayout);
        PanCtrlLayout.setHorizontalGroup(
            PanCtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanCtrlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanCtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanCtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TextFPrenom)
                    .addComponent(ComboCmd, 0, 127, Short.MAX_VALUE)
                    .addComponent(TextFDateDeb, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(10, 10, 10)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PanCtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TextFDateFin, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanCtrlLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(TextFAnnee, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanCtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ButConnexion, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(ButSoumettre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ButValidation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        PanCtrlLayout.setVerticalGroup(
            PanCtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanCtrlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanCtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboCmd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ButValidation)
                    .addComponent(jLabel8)
                    .addComponent(TextFAnnee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(PanCtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanCtrlLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(PanCtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextFPrenom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanCtrlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TextFDateFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(TextFDateDeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanCtrlLayout.createSequentialGroup()
                        .addComponent(ButSoumettre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ButConnexion)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        TabOp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdOp", "DateOp", "ClientId", "Montant"
            }
        ));
        jScrollPane2.setViewportView(TabOp);

        javax.swing.GroupLayout PanReqOpLayout = new javax.swing.GroupLayout(PanReqOp);
        PanReqOp.setLayout(PanReqOpLayout);
        PanReqOpLayout.setHorizontalGroup(
            PanReqOpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanReqOpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanReqOpLayout.setVerticalGroup(
            PanReqOpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanReqOpLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
        );

        TabAverage.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DateOp", "Prenom", "Montant"
            }
        ));
        jScrollPane3.setViewportView(TabAverage);

        javax.swing.GroupLayout PanReqAverageLayout = new javax.swing.GroupLayout(PanReqAverage);
        PanReqAverage.setLayout(PanReqAverageLayout);
        PanReqAverageLayout.setHorizontalGroup(
            PanReqAverageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanReqAverageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanReqAverageLayout.setVerticalGroup(
            PanReqAverageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanReqAverageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );

        TabValidate.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ClientId", "NumCompte", "Validation"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(TabValidate);

        javax.swing.GroupLayout PanReqValidateLayout = new javax.swing.GroupLayout(PanReqValidate);
        PanReqValidate.setLayout(PanReqValidateLayout);
        PanReqValidateLayout.setHorizontalGroup(
            PanReqValidateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanReqValidateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanReqValidateLayout.setVerticalGroup(
            PanReqValidateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanReqValidateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE))
        );

        TabDebit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Année", "ClientId", "Refusé(s)"
            }
        ));
        jScrollPane5.setViewportView(TabDebit);

        javax.swing.GroupLayout PanReqDebitLayout = new javax.swing.GroupLayout(PanReqDebit);
        PanReqDebit.setLayout(PanReqDebitLayout);
        PanReqDebitLayout.setHorizontalGroup(
            PanReqDebitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanReqDebitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
                .addContainerGap())
        );
        PanReqDebitLayout.setVerticalGroup(
            PanReqDebitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanReqDebitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanRequetesLayout = new javax.swing.GroupLayout(PanRequetes);
        PanRequetes.setLayout(PanRequetesLayout);
        PanRequetesLayout.setHorizontalGroup(
            PanRequetesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanRequetesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanReqOp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(PanRequetesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanRequetesLayout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(PanReqAverage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(PanRequetesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanRequetesLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(PanReqValidate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(20, 20, 20)))
            .addGroup(PanRequetesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanRequetesLayout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(PanReqDebit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        PanRequetesLayout.setVerticalGroup(
            PanRequetesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanRequetesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanReqOp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(PanRequetesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanRequetesLayout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(PanReqAverage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(1, 1, 1)))
            .addGroup(PanRequetesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanRequetesLayout.createSequentialGroup()
                    .addGap(31, 31, 31)
                    .addComponent(PanReqValidate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(PanRequetesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PanRequetesLayout.createSequentialGroup()
                    .addGap(21, 21, 21)
                    .addComponent(PanReqDebit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(21, 21, 21)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanCtrl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanRequetes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanCtrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanRequetes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        TPPrinc.addTab("Data Server", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TPPrinc)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TPPrinc, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b1ConnexionActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_b1ConnexionActionPerformed
    {//GEN-HEADEREND:event_b1ConnexionActionPerformed
        try
        {
            cSock1 = new Socket(IPAS, PORTAS);
            ois1 = new ObjectInputStream(cSock1.getInputStream());
            oos1 = new ObjectOutputStream(cSock1.getOutputStream());
            
            Connexion co = new Connexion(this, true);
            co.setVisible(true);
            
            String pseudo = co.getPseudo();
            String passwd = co.getPasswd();
            while(passwd.length() < 8)
                passwd += passwd;
            passwd = passwd.substring(0, 8);
            
            ASRequest asReq = new ASRequest("INITIAL_REQUEST", pseudo, passwd, "127.0.0.1", "TGS");
            oos1.writeObject(asReq);
            
            // Génération KC
            SecretKey KC = new SecretKeySpec(passwd.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, KC);
            
            // Récupération clé de session
            ASReply asRep = (ASReply)ois1.readObject();
            KCTGS = (KcTGS) asRep.getKCTGS().getObject(cipher);
            
            // Création authenticator
            auth = new AuthenticatorTGS();
            auth.setClientName(pseudo);
            auth.setCurrentTime(new Date());
            auth.setChecksum(auth.getClientName().hashCode());
            
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, KCTGS.getKCTGSSessionKey());
            SealedObject soAuth = new SealedObject(auth, cipher);
            
            // Création TGS Request
            TGSRequest TGSreq = new TGSRequest();
            TGSreq.setService(IP1);
            TGSreq.setTicket(asRep.getTicket());
            TGSreq.setAuthenticator(soAuth);
            
            // Changement socket
            cSock1 = new Socket(IPTGS, PORTTGS);
            ois1 = new ObjectInputStream(cSock1.getInputStream());
            oos1 = new ObjectOutputStream(cSock1.getOutputStream());
            
            // Envoi TGS Request
            oos1.writeObject(TGSreq);
            
            // TGS Reply
            TGSReply TGSrep = (TGSReply)ois1.readObject();
            System.out.println("TGS Reply reçu !!");
            
            // Sauvegarde Authentificateur et Ticket
            Ticket = TGSrep.getTicket();
            cipher.init(Cipher.DECRYPT_MODE, KCTGS.getKCTGSSessionKey());
            KCS = (SecretKey) TGSrep.getKCSkey().getObject(cipher);
            cipher.init(Cipher.ENCRYPT_MODE, KCS);
            Authentificateur = new SealedObject(auth, cipher);
            System.out.println("Fin Kerberos");
            
            cSock1.close();
            cSock1 = new Socket(IP1, PORT1);
            ois1 = new ObjectInputStream(cSock1.getInputStream());
            oos1 = new ObjectOutputStream(cSock1.getOutputStream());                        
            
            for (Component c : this.p1Ctrl.getComponents())
            {
                c.setEnabled(true);
            }
            this.b1Connexion.setEnabled(false);
            
        } catch (Exception ex)
        {
            Logger.getLogger(Agent_Bancaire.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_b1ConnexionActionPerformed

    private void b1ValiderActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_b1ValiderActionPerformed
    {//GEN-HEADEREND:event_b1ValiderActionPerformed
        try
        {
            int[] indices = this.tOperations.getSelectedRows();
            ArrayList<String> ids = new ArrayList<>();
            
            for (int ind : indices)
                ids.add(this.tOperations.getValueAt(ind, 0).toString());
            
            Pull pull = new Pull();
            pull.setAuthenticator(this.Authentificateur);
            pull.setTicket(this.Ticket);
            pull.setName("validation");
            pull.setIds(ids);
            
            oos1.writeObject(pull);
            
            Push push = (Push)ois1.readObject();
            SealedObject so = push.getTimestamp();
            Cipher c = Cipher.getInstance("DES/ECB/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, KCS);
            Date timestamp = (Date)so.getObject(c);
            if(timestamp == auth.getCurrentTime())
                System.out.println("Serveur authentifié");
            
            if(push.getName().equalsIgnoreCase("validOK"))
            {
                this.Status1.setText("Validation réussie");
                this.Status1.setBackground(Color.green);
            }
            else
            {
                this.Status1.setText("Echec validation");
                this.Status1.setBackground(Color.red);
            }
        }
        catch (IOException | ClassNotFoundException ex)
        {
            this.Status1.setText("Echec validation");
            this.Status1.setBackground(Color.red);
            Logger.getLogger(Agent_Bancaire.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex)
        {
            Logger.getLogger(Agent_Bancaire.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_b1ValiderActionPerformed

    private void b1RechercheActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_b1RechercheActionPerformed
    {//GEN-HEADEREND:event_b1RechercheActionPerformed
        try
        {
            DefaultTableModel dm = (DefaultTableModel) this.tOperations.getModel();
            int rowCount = dm.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--)
            {
                dm.removeRow(i);
            }
            
            Pull pull = new Pull();
            pull.setAuthenticator(this.Authentificateur);
            pull.setTicket(this.Ticket);
            pull.setName("recherche");
            pull.setBanque(this.tf1Banque.getText());
            int choix = this.cb1Valide.getSelectedIndex();
            switch(choix)
            {
                case 0:
                    pull.setValide("any");
                    break;
                case 1:
                    pull.setValide("true");
                    break;
                case 2:
                    pull.setValide("false");
                    break;
            }
            oos1.writeObject(pull);
            
            Push push = (Push) ois1.readObject();
            SealedObject so = push.getTimestamp();
            Cipher c = Cipher.getInstance("DES/ECB/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, KCS);
            Date timestamp = (Date)so.getObject(c);
            if(timestamp == auth.getCurrentTime())
                System.out.println("Serveur authentifié");
            
            DefaultTableModel dtm = (DefaultTableModel) this.tOperations.getModel();
            List<Object> list = null;
            for(int i = 0 ; i < push.getIds().size() ; i ++)
            {
                list = new ArrayList<>();
                list.add(push.getIds().get(i));
                list.add(push.getBanques().get(i));
                list.add(push.getTypes().get(i));
                list.add(push.getMontants().get(i));
                list.add(push.getValides().get(i));
                
                dtm.addRow(list.toArray());
            }
            this.tOperations.setModel(dtm);
            
            this.Status1.setText("Recherche réussie : " + dtm.getRowCount() + " résultats");
            this.Status1.setBackground(Color.green);
        }
        catch (IOException | ClassNotFoundException ex)
        {
            this.Status1.setText("Echec recherche");
            this.Status1.setBackground(Color.red);
            Logger.getLogger(Agent_Bancaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(Exception ex)
        {
            Logger.getLogger(Agent_Bancaire.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }//GEN-LAST:event_b1RechercheActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        try
        {
            Pull pull = new Pull();
            pull.setAuthenticator(this.Authentificateur);
            pull.setTicket(this.Ticket);
            pull.setName("END");
            oos1.writeObject(pull);
            System.out.println("Closing");
            ois1.close();
            oos1.close();
            cSock1.close();
            
            RequeteCLIBOP req= new RequeteCLIBOP();
            req.setAuthenticator(this.Authentificateur);
            req.setTicket(this.Ticket);
            req.setCmd("END");
            oosDS.writeObject(pull);
            oisDS.close();
            oosDS.close();
            socketClientDS.close();
            
            
            exit(0);
        }
        catch (Exception ex)
        {
            exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void ButConnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButConnexionActionPerformed
        
        try
        {
            socketClientDS = new Socket(IPAS, PORTAS);
            oisDS = new ObjectInputStream(socketClientDS.getInputStream());
            oosDS = new ObjectOutputStream(socketClientDS.getOutputStream());
            
            Connexion c = new Connexion(this, true);
            c.setVisible(true);
            
            String login = c.getPseudo();
            String pwd = c.getPasswd();
            while(pwd.length() < 8)
                pwd += pwd;
            pwd = pwd.substring(0, 8);
            
            ASRequest asReq = new ASRequest("INITIAL_REQUEST", login, pwd, "127.0.0.1", "TGS");
            oosDS.writeObject(asReq);
            
            // Génération KC
            SecretKey KC = new SecretKeySpec(pwd.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, KC);
            
            // Récupération clé de session
            ASReply repAS = (ASReply)oisDS.readObject();
            KCTGS = (KcTGS) repAS.getKCTGS().getObject(cipher);
            
            // Création authentificateur
            auth = new AuthenticatorTGS();
            auth.setClientName(login);
            auth.setCurrentTime(new Date());
            auth.setChecksum(auth.getClientName().hashCode());
            
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, KCTGS.getKCTGSSessionKey());
            SealedObject soAuth = new SealedObject(auth, cipher);
            
            // Création TGS Request
            TGSRequest TGSreq = new TGSRequest();
            TGSreq.setService(ipDS);
            TGSreq.setTicket(repAS.getTicket());
            TGSreq.setAuthenticator(soAuth);
            
            // Changement socket
            socketClientDS = new Socket(IPTGS, PORTTGS);
            oisDS = new ObjectInputStream(socketClientDS.getInputStream());
            oosDS = new ObjectOutputStream(socketClientDS.getOutputStream());
            
            // Envoi TGS Request
            oosDS.writeObject(TGSreq);
            
            // TGS Reply
            TGSReply TGSrep = (TGSReply)oisDS.readObject();
            System.out.println("TGS Reply reçu !!");
            
            // Sauvegarde Authentificateur et Ticket
            Ticket = TGSrep.getTicket();
            cipher.init(Cipher.DECRYPT_MODE, KCTGS.getKCTGSSessionKey());
            KCS = (SecretKey) TGSrep.getKCSkey().getObject(cipher);
            cipher.init(Cipher.ENCRYPT_MODE, KCS);
            Authentificateur = new SealedObject(auth, cipher);
            System.out.println("Fin Kerberos");
            
            socketClientDS.close();
            socketClientDS = new Socket(ipDS, portDS);
            oisDS = new ObjectInputStream(socketClientDS.getInputStream());
            oosDS = new ObjectOutputStream(socketClientDS.getOutputStream());                        
            
        } 
        catch (Exception ex){Logger.getLogger(Agent_Bancaire.class.getName()).log(Level.SEVERE, null, ex);}
    }//GEN-LAST:event_ButConnexionActionPerformed

    private void initTab()
    {
        dmOp = (DefaultTableModel) this.TabOp.getModel();
        int rowCountO = dmOp.getRowCount();
        for (int i = rowCountO - 1; i >= 0; i--)
        {
            dmOp.removeRow(i);
        }
        
        dmValid = (DefaultTableModel) this.TabValidate.getModel();
        int rowCountV = dmValid.getRowCount();
        for (int i = rowCountV - 1; i >= 0; i--)
        {
            dmValid.removeRow(i);
        }
        
        dmDeb = (DefaultTableModel) this.TabDebit.getModel();
        int rowCountD = dmDeb.getRowCount();
        for (int i = rowCountD - 1; i >= 0; i--)
        {
            dmDeb.removeRow(i);
        }
        
        dmAvg = (DefaultTableModel) this.TabAverage.getModel();
        int rowCountA = dmAvg.getRowCount();
        for (int i = rowCountA - 1; i >= 0; i--)
        {
            dmAvg.removeRow(i);
        }
    }
    
    private void ButSoumettreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButSoumettreActionPerformed
        try
        {
            this.initTab();
            
            
            //Requête
            RequeteCLIBOP req = new RequeteCLIBOP();
            req.setAuthenticator(this.Authentificateur);
            req.setTicket(this.Ticket);
            
            req.setCmd(this.ComboCmd.getSelectedItem().toString());
            req.setIsUpdate(false);
            
            switch(req.getCmd())
            {
                case "GETLIST":
                {
                    req.setCmd("GETLIST");
                    req.setPrenom(this.TextFPrenom.getText());
                    req.setDateDebut(this.TextFDateDeb.getText());
                    req.setDateFin(this.TextFDateFin.getText());  
                }
                break;
                    
                case "AVERAGE":
                {
                    req.setCmd("AVERAGE");
                    req.setPrenom(this.TextFPrenom.getText());
                }
                break;
                    
                case "GETDEB":
                {
                    req.setCmd("GETDEB");
                    req.setPrenom(this.TextFPrenom.getText());
                    req.setAnnee(this.TextFAnnee.getText());
                }
                break;
                    
                case "VALIDATE":
                {
                    req.setCmd("VALIDATE");
                    req.setPrenom(this.TextFPrenom.getText());
                }
                break;
                    
            }
            
            oosDS.writeObject(req);
            
            
            //Réponse
            ReponseCLIBOP rep = (ReponseCLIBOP) oisDS.readObject();
            SealedObject so = rep.getTimestamp();
            Cipher c = Cipher.getInstance("DES/ECB/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, KCS);
            Date timestamp = (Date)so.getObject(c);
            if(timestamp == auth.getCurrentTime())
                System.out.println(">> Serveur authentifié");
            
            this.ButValidation.setBackground(Color.WHITE);
            
            switch(rep.getCmd())
            {
                case "GETLIST":
                {
                    this.PanReqOp.setVisible(true);
                    this.PanReqAverage.setVisible(false);
                    this.PanReqValidate.setVisible(false);
                    this.PanReqDebit.setVisible(false);
 
                    
                    for(int i = 0; i < rep.getTuples().size(); i++)
                    {
                        Operations o = rep.getTuples().get(i);
                        Vector<String> v = new Vector();
                        
                        v.add(o.getIdOp());
                        v.add(o.getDateOp());
                        v.add(o.getClientID());
                        v.add(o.getMontant());
                        
                        dmOp.addRow(v);
                    }
                }
                break;
                    
                case "AVERAGE":
                {
                    this.PanReqOp.setVisible(false);
                    this.PanReqAverage.setVisible(true);
                    this.PanReqValidate.setVisible(false);
                    this.PanReqDebit.setVisible(false);

                    
                    for(int i = 0;i<rep.getTuples().size();i++)
                    {
                        Vector<String> v = new Vector();
                        Operations o = rep.getTuples().get(i);
                        
                        v.add(o.getDateOp());
                        v.add(o.getClientID());
                        v.add(o.getMontant());
                        
                        dmAvg.addRow(v);
                    }
                }
                break;
                    
                case "GETDEB":
                {
                    this.PanReqOp.setVisible(false);
                    this.PanReqAverage.setVisible(false);
                    this.PanReqValidate.setVisible(false);
                    this.PanReqDebit.setVisible(true);
                    
                    
                    for(int i = 0;i<rep.getTuples().size();i++)
                    {
                        Vector<String> v = new Vector();
                        Operations o = rep.getTuples().get(i);
                        
                        v.add(o.getDateOp());
                        v.add(o.getClientID());
                        v.add(o.getNbrRefusees());
                        
                        dmDeb.addRow(v);
                    }
                }
                break;
                    
                case "VALIDATE":
                {
                   
                    this.PanReqOp.setVisible(false);
                    this.PanReqAverage.setVisible(false);
                    this.PanReqValidate.setVisible(true);
                    this.PanReqDebit.setVisible(false);


                    for(int i = 0;i<rep.getTuples().size();i++)
                    {
                        Vector<String> v = new Vector();
                        Operations o = rep.getTuples().get(i);

                        v.add(o.getClientID());
                        v.add(o.getNumCompte());

                        dmValid.addRow(v);
                        dmValid.setValueAt(o.isFiable(), i, 2);
                    } 
                }
                break;   
            }
                    
        }
        catch (IOException | ClassNotFoundException ex){ Logger.getLogger(Agent_Bancaire.class.getName()).log(Level.SEVERE, null, ex); }
        catch(Exception ex){ Logger.getLogger(Agent_Bancaire.class.getName()).log(Level.SEVERE, null, ex); }
    }//GEN-LAST:event_ButSoumettreActionPerformed

    private void ButValidationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButValidationActionPerformed
        
        try 
        {
            //Requête
            RequeteCLIBOP req = new RequeteCLIBOP();
            req.setAuthenticator(this.Authentificateur);
            req.setTicket(this.Ticket);
            
            SignatureGUI s = new SignatureGUI(this, true);
            s.setVisible(true);
            String msg = null;
            byte[] signature = null;
            
            try 
            {
                KeyStore ks = null;
                ks = KeyStore.getInstance("JKS");
                ks.load(new FileInputStream("comptableKaou_keystore.jks"),"cisco12".toCharArray());

                System.out.println(">> Récupération de la clé privée\n");
                PrivateKey clePrivee;
                clePrivee = (PrivateKey) ks.getKey("comptableKaou", "cisco12".toCharArray());
                System.out.println("\t >> Cle privee recuperee = " + clePrivee.toString());

                //Signature d'un message
                System.out.println("\n >> Signature du message");
                System.out.println("\n\t >> 1. Instanciation de la signature");
                java.security.Signature sign = java.security.Signature. getInstance("SHA1withECDSA",codeProvider);
                System.out.println("\t >> 2. Initialisation de la signature");
                sign.initSign(clePrivee);
                System.out.println("\t >> 3. Ajout du message à la signature");
                msg = s.getPseudo()+s.getPasswd();
                sign.update(msg.getBytes());
                System.out.println("\t >> 4. Génération de la signature");
                signature = sign.sign();
                System.out.println("\t\t >> Signature = " + new String(signature));
                System.out.println("\t\t >> Longueur de la signature = " + signature.length+"\n");


            } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | InvalidKeyException | SignatureException ex) {
                Logger.getLogger(SignatureGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SignatureGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SignatureGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchProviderException ex) {
                Logger.getLogger(Agent_Bancaire.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            req.setCmd("UPDATE");
            req.setComptable(msg);
            req.setSignature(signature);
            
            for(int i =0; i< TabValidate.getRowCount(); i++)
            {
                req.getListUpdates().put(TabValidate.getValueAt(i, 1).toString(),TabValidate.getValueAt(i, 2).toString());
            }
            
            oosDS.writeObject(req);
            
            //Réponse
            ReponseCLIBOP rep = (ReponseCLIBOP) oisDS.readObject();
            SealedObject so = rep.getTimestamp();
            Cipher c = Cipher.getInstance("DES/ECB/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, KCS);
            Date timestamp = (Date)so.getObject(c);
            if(timestamp == auth.getCurrentTime())
                System.out.println(">> Serveur authentifié");
            
            
            if(rep.getCmd().equals("UPDATE"))
            {
                if(rep.isIsUpdate()==true)
                   this.ButValidation.setBackground(Color.green);
                else 
                   this.ButValidation.setBackground(Color.red); 
            }
                
        } 
        catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) { Logger.getLogger(Agent_Bancaire.class.getName()).log(Level.SEVERE, null, ex);}
        
        this.initTab();
    }//GEN-LAST:event_ButValidationActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
        * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
        */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(Agent_Bancaire.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(Agent_Bancaire.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(Agent_Bancaire.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(Agent_Bancaire.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new Agent_Bancaire().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButConnexion;
    private javax.swing.JButton ButSoumettre;
    private javax.swing.JButton ButValidation;
    private javax.swing.JComboBox ComboCmd;
    private javax.swing.JPanel PanCtrl;
    private javax.swing.JPanel PanReqAverage;
    private javax.swing.JPanel PanReqDebit;
    private javax.swing.JPanel PanReqOp;
    private javax.swing.JPanel PanReqValidate;
    private javax.swing.JPanel PanRequetes;
    private javax.swing.JLabel Status1;
    private javax.swing.JTabbedPane TPPrinc;
    private javax.swing.JTable TabAverage;
    private javax.swing.JTable TabDebit;
    private javax.swing.JTable TabOp;
    private javax.swing.JTable TabValidate;
    private javax.swing.JTextField TextFAnnee;
    private javax.swing.JTextField TextFDateDeb;
    private javax.swing.JTextField TextFDateFin;
    private javax.swing.JTextField TextFPrenom;
    private javax.swing.JButton b1Connexion;
    private javax.swing.JButton b1Recherche;
    private javax.swing.JButton b1Valider;
    private javax.swing.JComboBox cb1Valide;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel p1Ctrl;
    private javax.swing.JTable tOperations;
    private javax.swing.JTextField tf1Banque;
    // End of variables declaration//GEN-END:variables
}
