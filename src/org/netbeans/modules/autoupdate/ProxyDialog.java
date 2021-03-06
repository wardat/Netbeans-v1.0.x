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

import java.awt.Dialog;
import java.util.ResourceBundle;

import org.openide.DialogDescriptor;
import org.openide.TopManager;
import org.openide.util.NbBundle;
import org.openide.util.HelpCtx;

/**
 *
 * @author  Administrator
 * @version 
 */
public class ProxyDialog extends javax.swing.JPanel {

    /** The resource bundle */
    private static final ResourceBundle bundle = NbBundle.getBundle( ProxyDialog.class );

    /** Creates new form ProxyDialog */
    public ProxyDialog() {
        initComponents ();

        useCheckBox.setSelected( Autoupdater.isUseProxy() );
        hostTextField.setText( Autoupdater.getProxyHost() );
        portTextField.setText( Autoupdater.getProxyPort() );
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        useCheckBox = new javax.swing.JCheckBox ();
        jLabel1 = new javax.swing.JLabel ();
        hostTextField = new javax.swing.JTextField ();
        jLabel2 = new javax.swing.JLabel ();
        portTextField = new javax.swing.JTextField ();
        setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints1;
        setBorder (new javax.swing.border.EmptyBorder(new java.awt.Insets(8, 8, 8, 8)));

        useCheckBox.setText (org.openide.util.NbBundle.getBundle(ProxyDialog.class).getString("ProxyDialog.useCheckBox.text"));


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.insets = new java.awt.Insets (0, 0, 8, 0);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
        add (useCheckBox, gridBagConstraints1);

        jLabel1.setText (org.openide.util.NbBundle.getBundle(ProxyDialog.class).getString("ProxyDialog.jLabel1.text"));


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.insets = new java.awt.Insets (0, 0, 8, 0);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
        add (jLabel1, gridBagConstraints1);

        hostTextField.setColumns (30);


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.insets = new java.awt.Insets (0, 8, 8, 0);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
        add (hostTextField, gridBagConstraints1);

        jLabel2.setText (org.openide.util.NbBundle.getBundle(ProxyDialog.class).getString("ProxyDialog.jLabel2.text"));


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
        add (jLabel2, gridBagConstraints1);

        portTextField.setColumns (6);


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.insets = new java.awt.Insets (0, 8, 0, 0);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
        add (portTextField, gridBagConstraints1);

    }//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox useCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField hostTextField;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField portTextField;
    // End of variables declaration//GEN-END:variables


    static void showDialog() {

        ProxyDialog proxyPanel;

        DialogDescriptor dd = new DialogDescriptor(
                                  proxyPanel = new ProxyDialog(),
                                  bundle.getString( "CTL_ProxyDialog_Title" ),
                                  true,                                                 // Modal
                                  DialogDescriptor.OK_CANCEL_OPTION,                     // Option list
                                  DialogDescriptor.OK_OPTION,                          // Default
                                  DialogDescriptor.BOTTOM_ALIGN,                        // Align
                                  new HelpCtx ( ProxyDialog.class ),                  // Help
                                  null );


        Dialog dialog = TopManager.getDefault().createDialog( dd );
        dialog.show();

        if ( dd.getValue() == DialogDescriptor.OK_OPTION ) {
            Autoupdater.setProxyConfiguration(
                proxyPanel.useCheckBox.isSelected(),
                proxyPanel.hostTextField.getText(),
                proxyPanel.portTextField.getText() );
        }
    }
}