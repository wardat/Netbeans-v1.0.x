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

package org.netbeans.modules.vcs.cmdline.commands;

import java.util.Vector;

/**
 *
 * @author  Martin Entlicher
 * @version 
 */
public class CvsWatchDialog extends javax.swing.JDialog {

    static final long serialVersionUID = 9170356221856697831L;
    /** Creates new form CvsWatchDialog */
    public CvsWatchDialog(java.awt.Frame parent,boolean modal) {
        super (parent, modal);
        initComponents ();
        setTitle(org.openide.util.NbBundle.getBundle(CvsWatchDialog.class).getString("CvsWatchDialog.title"));
        pack ();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel ();
        watchLabel = new javax.swing.JLabel ();
        actionsPanel = new javax.swing.JPanel ();
        recursiveCheckBox = new javax.swing.JCheckBox ();
        jSeparator1 = new javax.swing.JSeparator ();
        jPanel2 = new javax.swing.JPanel ();
        okButton = new javax.swing.JButton ();
        cancelButton = new javax.swing.JButton ();
        getContentPane ().setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints1;
        addWindowListener (new java.awt.event.WindowAdapter () {
                               public void windowClosing (java.awt.event.WindowEvent evt) {
                                   closeDialog (evt);
                               }
                           }
                          );

        jPanel1.setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints2;

        watchLabel.setText (org.openide.util.NbBundle.getBundle(CvsWatchDialog.class).getString("CvsWatchDialog.watchLabel.text"));

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        jPanel1.add (watchLabel, gridBagConstraints2);

        actionsPanel.setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints3;

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridy = 1;
        jPanel1.add (actionsPanel, gridBagConstraints2);

        recursiveCheckBox.setText (org.openide.util.NbBundle.getBundle(CvsWatchDialog.class).getString("CvsWatchDialog.recursiveCheckBox.text"));

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridy = 2;
        gridBagConstraints2.insets = new java.awt.Insets (8, 0, 0, 0);
        jPanel1.add (recursiveCheckBox, gridBagConstraints2);


        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridy = 3;
        gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints2.weightx = 1.0;
        jPanel1.add (jSeparator1, gridBagConstraints2);


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints1.insets = new java.awt.Insets (8, 8, 8, 8);
        gridBagConstraints1.weightx = 1.0;
        gridBagConstraints1.weighty = 1.0;
        getContentPane ().add (jPanel1, gridBagConstraints1);

        jPanel2.setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints4;

        okButton.setText (org.openide.util.NbBundle.getBundle(CvsWatchDialog.class).getString("CvsWatchDialog.jButton1.text"));
        okButton.addActionListener (new java.awt.event.ActionListener () {
                                        public void actionPerformed (java.awt.event.ActionEvent evt) {
                                            okButtonActionPerformed (evt);
                                        }
                                    }
                                   );

        gridBagConstraints4 = new java.awt.GridBagConstraints ();
        gridBagConstraints4.insets = new java.awt.Insets (0, 0, 0, 8);
        gridBagConstraints4.weightx = 1.0;
        jPanel2.add (okButton, gridBagConstraints4);

        cancelButton.setText (org.openide.util.NbBundle.getBundle(CvsWatchDialog.class).getString("CvsWatchDialog.jButton2.text"));
        cancelButton.addActionListener (new java.awt.event.ActionListener () {
                                            public void actionPerformed (java.awt.event.ActionEvent evt) {
                                                cancelButtonActionPerformed (evt);
                                            }
                                        }
                                       );

        gridBagConstraints4 = new java.awt.GridBagConstraints ();
        gridBagConstraints4.insets = new java.awt.Insets (0, 8, 0, 0);
        gridBagConstraints4.weightx = 1.0;
        jPanel2.add (cancelButton, gridBagConstraints4);


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridy = 1;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.insets = new java.awt.Insets (0, 8, 8, 8);
        gridBagConstraints1.weightx = 1.0;
        getContentPane ().add (jPanel2, gridBagConstraints1);

    }//GEN-END:initComponents

    private void cancelButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // Add your handling code here:
        exit = CANCEL;
        closeDialog(null);
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        // Add your handling code here:
        exit = OK;
        closeDialog(null);
    }//GEN-LAST:event_okButtonActionPerformed

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible (false);
        dispose ();
    }//GEN-LAST:event_closeDialog

    /**
     * Set the array of action names we have. The first two elements has to be
     * action "all" and action "none" in this order.
     * @param actions the array of String actions.
     */
    public void setActions(String[] actions) {
        this.actions = actions;
        if (actions.length < 2) return;
        java.awt.GridBagConstraints gridBagConstraints;
        actionCheckBox = new javax.swing.JCheckBox[actions.length - 2];
        for(int i = 0; i < actions.length - 2; i++) {
            //System.out.println("i = "+i);
            //System.out.println("actions["+(i+2)+"] = "+actions[i+2]);
            actionCheckBox[i] = new javax.swing.JCheckBox(actions[i+2], false);
            gridBagConstraints = new java.awt.GridBagConstraints ();
            gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints.gridy = 2+i;
            actionsPanel.add(actionCheckBox[i], gridBagConstraints);
        }
        pack();
    }

    /**
     * Get the actions selected by the user.
     * @return the array of selected actions.
     */
    public String[] getActions() {
        Vector vectActions = new Vector();
        String act[] = null;
        for(int i = 0; i < actions.length - 2; i++) {
            if (actionCheckBox[i].isSelected()) {
                vectActions.add(actions[i+2]);
            }
        }
        if (vectActions.size() == actions.length - 2) {
            act = new String[1];
            act[0] = actions[0].trim(); // all
        } else if (vectActions.size() == 0) {
            act = new String[1];
            act[0] = actions[1].trim(); // none
        } else {
            act = new String[vectActions.size()];
            for(int i = 0; i < vectActions.size(); i++) {
                act[i] = (String) vectActions.get(i);
            }
        }
        return act;
    }

    public boolean getRecursive() {
        return recursiveCheckBox.isSelected();
    }

    public boolean showDialog() {
        show();
        return exit == OK;
    }

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) {
        new CvsWatchDialog (new javax.swing.JFrame (), true).show ();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel watchLabel;
    private javax.swing.JPanel actionsPanel;
    private javax.swing.JCheckBox recursiveCheckBox;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton okButton;
    private javax.swing.JButton cancelButton;
    // End of variables declaration//GEN-END:variables

    private javax.swing.JCheckBox[] actionCheckBox;

    private static final int NONE = 0;
    private static final int OK = 1;
    private static final int CANCEL = 2;
    private int exit = NONE;
    private String[] actions;
}