/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is Forte for Java, Community Edition. The Initial
 * Developer of the Original Code is Sun Microsystems, Inc. Portions
 * Copyright 1997-2000 Sun Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.autoupdate;

import javax.swing.JButton;
import java.util.ResourceBundle;
import java.util.Collection;

import org.openide.DialogDescriptor;
import org.openide.NotifyDescriptor;
import org.openide.TopManager;
import org.openide.util.NbBundle;
import org.openide.util.HelpCtx;

/** Panel and dialog for showing Module Certificate.
 * @author  Petr Hrebejk
 */
public class CertDialog extends javax.swing.JPanel {

    /** Certificates accepted for this module */
    static final int FOR_MODULE = 0;
    /** Certificates accepted for all modules for now */
    static final int FOR_NOW = 1;
    /** Certificates accepted forever */
    static final int FOREVER = 2;
    /** Certificates not accepted  */
    static final int NOT_ACCEPTED = 3;

    /** The resource bundle */
    private static final ResourceBundle bundle = NbBundle.getBundle( CertDialog.class );
    /** Preferred size of this dialog */
    private static final java.awt.Dimension preferredSize = new java.awt.Dimension( 620, 475 );

    /** Result of the dialog */
    private static int result = NOT_ACCEPTED;

    static final long serialVersionUID =-4862117522808181670L;
    /** Creates new form LicencePanel */
    public CertDialog( Collection certs ) {
        initComponents ();

        certsTextArea.setText( SignVerifier.formatCerts( certs ) );
        certsTextArea.setCaretPosition(0);
    }

    /** Overload getPreffered size to get a bit bigger dialog */
    public java.awt.Dimension getPreferredSize() {
        return preferredSize;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        jScrollPane1 = new javax.swing.JScrollPane ();
        certsTextArea = new javax.swing.JTextArea ();
        setLayout (new java.awt.BorderLayout ());
        setBorder (new javax.swing.border.EmptyBorder(new java.awt.Insets(8, 8, 8, 8)));


        certsTextArea.setEditable (false);
        certsTextArea.setFont (new java.awt.Font ("Monospaced", 0, 12)); // NOI18N

        jScrollPane1.setViewportView (certsTextArea);


        add (jScrollPane1, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea certsTextArea;
    // End of variables declaration//GEN-END:variables

    /** Prestents a dialog withe the certificate to the user */


    static int acceptCert( Collection certs ) {

        DialogDescriptor dialogDescriptor = createDialog( certs );

        result = NOT_ACCEPTED;

        java.awt.Dialog dialog = TopManager.getDefault().createDialog( dialogDescriptor );

        dialog.show();

        dialog.dispose();

        return result;
    }

    /** Only views the certificate */
    static void viewCert( Collection certs ) {

        final CertDialog certPanel = new CertDialog( certs );
        final JButton closeButton = new JButton( bundle.getString( "CTL_Certs_Close" ) );

        DialogDescriptor dd = new DialogDescriptor(
                                  certPanel,
                                  bundle.getString( "CTL_Cert_Title" ),
                                  true,                                                 // Modal
                                  new Object[] { closeButton },                         // Option list
                                  closeButton,                                          // Default
                                  DialogDescriptor.BOTTOM_ALIGN,                        // Align
                                  new HelpCtx ( CertDialog.class ),                     // Help
                                  null );

        dd.setClosingOptions( null );

        java.awt.Dialog dialog = TopManager.getDefault().createDialog( dd );

        dialog.show();
        dialog.dispose();
    }

    /** Creates new dialog */
    private static DialogDescriptor createDialog( Collection certs ) {

        final CertDialog certPanel = new CertDialog( certs );

        final JButton moduleButton = new JButton( bundle.getString( "CTL_Certs_TrustForModule" ) );
        final JButton forNowButton = new JButton( bundle.getString( "CTL_Certs_TrustForNow" ) );
        final JButton foreverButton = new JButton( bundle.getString( "CTL_Certs_TrustForever" ) );
        final JButton cancelButton = new JButton( bundle.getString( "CTL_Certs_Cancel" ) );

        moduleButton.setToolTipText( bundle.getString( "CTL_Certs_TrustForModule_ToolTip" ) );
        forNowButton.setToolTipText( bundle.getString( "CTL_Certs_TrustForNow_ToolTip" ) );
        foreverButton.setToolTipText( bundle.getString( "CTL_Certs_TrustForever_ToolTip" ) );
        cancelButton.setToolTipText( bundle.getString( "CTL_Certs_Cancel_ToolTip" ) );

        DialogDescriptor dd = new DialogDescriptor(
                                  certPanel,
                                  bundle.getString( "CTL_Cert_Title" ),
                                  true,                                                 // Modal
                                  new Object[] {
                                      moduleButton,
                                      forNowButton,
                                      foreverButton,
                                      cancelButton },                                 // Option list
                                  forNowButton,                                         // Default
                                  DialogDescriptor.BOTTOM_ALIGN,                        // Align
                                  new HelpCtx ( CertDialog.class ),                     // Help
                                  new java.awt.event.ActionListener() {
                                      public void actionPerformed( java.awt.event.ActionEvent evt ) {
                                          if ( evt.getSource() == moduleButton )
                                              result = FOR_MODULE;
                                          else if ( evt.getSource() == forNowButton )
                                              result = FOR_NOW;
                                          else if ( evt.getSource() == foreverButton )
                                              result = FOREVER;
                                          else if ( evt.getSource() == cancelButton )
                                              result = NOT_ACCEPTED;
                                          //dialog.setVisible( false );
                                      }
                                  });

        dd.setClosingOptions( null );

        return dd;
    }

}
/*
 * Log
 *  6    Gandalf   1.5         1/12/00  Petr Hrebejk    i18n
 *  5    Gandalf   1.4         1/3/00   Petr Hrebejk    Various bug fixes - 
 *       5097, 5098, 5110, 5099, 5108
 *  4    Gandalf   1.3         12/22/99 Petr Hrebejk    Various bugfixes
 *  3    Gandalf   1.2         12/21/99 Petr Hrebejk    Enabled For module 
 *       button
 *  2    Gandalf   1.1         12/20/99 Petr Hrebejk    Autocheck & security 
 *       finished
 *  1    Gandalf   1.0         12/16/99 Petr Hrebejk    
 * $
 */