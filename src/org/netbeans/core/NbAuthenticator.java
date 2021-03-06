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

package org.netbeans.core;

import java.net.Authenticator;
import java.util.ResourceBundle;

import org.openide.TopManager;
import org.openide.DialogDescriptor;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

/** Global password protected sites Authenticator for IDE
 *
 * @author  Petr Hrebejk
 */

class NbAuthenticator extends java.net.Authenticator {

    /** Resource bundle */
    private static ResourceBundle bundle = NbBundle.getBundle( NbAuthenticator.class );

    /** Password panel */
    private static final PasswordPanel passwordPanel = new PasswordPanel();


    protected java.net.PasswordAuthentication getPasswordAuthentication() {
        java.net.InetAddress site = getRequestingSite();
        String host = site == null ? "" : site.getHostName(); // NOI18N

        DialogDescriptor dd = new DialogDescriptor( passwordPanel, host == null ? bundle.getString( "CTL_PasswordProtected" ) : host );
        dd.setHelpCtx (new HelpCtx (NbAuthenticator.class.getName () + ".getPasswordAuthentication")); // NOI18N
        passwordPanel.setPrompt( getRequestingPrompt() );
        java.awt.Dialog dialog = TopManager.getDefault().createDialog( dd );
        dialog.show ();

        if ( dd.getValue().equals( NotifyDescriptor.OK_OPTION ) )
            return new java.net.PasswordAuthentication ( passwordPanel.getUsername(), passwordPanel.getPassword() );
        else
            return null;
    }


    /** Inner class for JPanel with Username & Password fields */

    static class PasswordPanel extends javax.swing.JPanel {

        private static final int DEFAULT_WIDTH = 200;
        private static final int DEFAULT_HEIGHT = 0;

        static final long serialVersionUID =1555749205340031767L;
        /** Creates new form PasswordPanel */
        public PasswordPanel() {
            initComponents ();
        }

        public java.awt.Dimension getPreferredSize () {
            java.awt.Dimension sup = super.getPreferredSize ();
            return new java.awt.Dimension ( Math.max (sup.width, DEFAULT_WIDTH), Math.max (sup.height, DEFAULT_HEIGHT ));
        }

        /** This method is called from within the constructor to
         * initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is
         * always regenerated by the FormEditor.
         */
        private void initComponents () {
            setLayout (new java.awt.BorderLayout ());

            mainPanel = new javax.swing.JPanel ();
            mainPanel.setLayout (new java.awt.GridBagLayout ());
            java.awt.GridBagConstraints gridBagConstraints1;
            mainPanel.setBorder (new javax.swing.border.EmptyBorder(new java.awt.Insets(8, 8, 8, 8)));

            promptLabel = new javax.swing.JLabel ();
            promptLabel.setHorizontalAlignment (0);

            gridBagConstraints1 = new java.awt.GridBagConstraints ();
            gridBagConstraints1.gridwidth = 0;
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.insets = new java.awt.Insets (0, 0, 6, 0);
            mainPanel.add (promptLabel, gridBagConstraints1);

            jLabel1 = new javax.swing.JLabel ();
            jLabel1.setText (org.openide.util.NbBundle.getBundle(NbAuthenticator.class).getString("LAB_AUTH_User_Name"));

            gridBagConstraints1 = new java.awt.GridBagConstraints ();
            gridBagConstraints1.insets = new java.awt.Insets (5, 0, 5, 3);
            gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
            mainPanel.add (jLabel1, gridBagConstraints1);

            usernameField = new javax.swing.JTextField ();
            usernameField.setMinimumSize (new java.awt.Dimension(70, 20));
            usernameField.setPreferredSize (new java.awt.Dimension(70, 20));

            gridBagConstraints1 = new java.awt.GridBagConstraints ();
            gridBagConstraints1.gridwidth = 0;
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.insets = new java.awt.Insets (5, 3, 5, 0);
            gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints1.weightx = 1.0;
            mainPanel.add (usernameField, gridBagConstraints1);

            jLabel2 = new javax.swing.JLabel ();
            jLabel2.setText (org.openide.util.NbBundle.getBundle(NbAuthenticator.class).getString("LAB_AUTH_Password"));

            gridBagConstraints1 = new java.awt.GridBagConstraints ();
            gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
            mainPanel.add (jLabel2, gridBagConstraints1);

            passwordField = new javax.swing.JPasswordField ();
            passwordField.setMinimumSize (new java.awt.Dimension(70, 20));
            passwordField.setPreferredSize (new java.awt.Dimension(70, 20));

            gridBagConstraints1 = new java.awt.GridBagConstraints ();
            gridBagConstraints1.gridwidth = 0;
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.insets = new java.awt.Insets (5, 3, 5, 0);
            gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints1.weightx = 1.0;
            mainPanel.add (passwordField, gridBagConstraints1);


            add (mainPanel, "Center"); // NOI18N

        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JPanel mainPanel;
        private javax.swing.JLabel promptLabel;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JTextField usernameField;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JPasswordField passwordField;
        // End of variables declaration//GEN-END:variables

        String getUsername( ) {
            return usernameField.getText();
        }

        char[] getPassword( ) {
            return passwordField.getPassword();
        }

        void setPrompt( String prompt ) {
            if ( prompt == null ) {
                promptLabel.setVisible( false );
            }
            else {
                promptLabel.setVisible( true );
                promptLabel.setText( prompt );
            }
        }
    }
}

/*
 * Log
 *  6    Gandalf   1.5         1/13/00  Jaroslav Tulach I18N
 *  5    Gandalf   1.4         10/22/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  4    Gandalf   1.3         8/9/99   Ian Formanek    Generated Serial Version
 *       UID
 *  3    Gandalf   1.2         7/8/99   Jesse Glick     Context help.
 *  2    Gandalf   1.1         6/10/99  Petr Hrebejk    Cancel option added
 *  1    Gandalf   1.0         6/10/99  Petr Hrebejk    
 * $
 */
