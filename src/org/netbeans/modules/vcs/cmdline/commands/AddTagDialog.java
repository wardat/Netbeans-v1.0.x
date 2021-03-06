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

import java.util.*;

import org.openide.util.HelpCtx;

/**
 *
 * @author  Martin Entlicher
 * @version 
 */
public class AddTagDialog extends javax.swing.JDialog {

    static final long serialVersionUID =-7865630079607924993L;
    /** Creates new form AddTagDialog */
    public AddTagDialog(java.awt.Frame parent,boolean modal) {
        super (parent, modal);
        initComponents ();
        setTitle(org.openide.util.NbBundle.getBundle(AddTagDialog.class).getString("AddTagDialog.title"));
        pack ();
        HelpCtx.setHelpIDString (getRootPane (), AddTagDialog.class.getName ());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        tagPanel = new javax.swing.JPanel ();
        rTagRadioButton = new javax.swing.JRadioButton ();
        bTagRadioButton = new javax.swing.JRadioButton ();
        rTagCheckBox = new javax.swing.JCheckBox ();
        revLabel = new javax.swing.JLabel ();
        revComboBox = new javax.swing.JComboBox ();
        tagLabel = new javax.swing.JLabel ();
        tagTextField = new javax.swing.JTextField ();
        ocPanel = new javax.swing.JPanel ();
        okButton = new javax.swing.JButton ();
        cancelButton = new javax.swing.JButton ();
        jSeparator1 = new javax.swing.JSeparator ();
        getContentPane ().setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints1;
        addWindowListener (new java.awt.event.WindowAdapter () {
                               public void windowClosing (java.awt.event.WindowEvent evt) {
                                   closeDialog (evt);
                               }
                           }
                          );

        tagPanel.setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints2;
        javax.swing.ButtonGroup radGroup = new javax.swing.ButtonGroup();
        radGroup.add(rTagRadioButton);
        radGroup.add(bTagRadioButton);


        rTagRadioButton.setText (org.openide.util.NbBundle.getBundle(AddTagDialog.class).getString("AddTagDialog.rTagRadioButton.text"));
        rTagRadioButton.setSelected(true);

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints2.weightx = 1.0;
        tagPanel.add (rTagRadioButton, gridBagConstraints2);

        bTagRadioButton.setText (org.openide.util.NbBundle.getBundle(AddTagDialog.class).getString("AddTagDialog.bTagRadioButton.text"));

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridy = 1;
        gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints2.weightx = 1.0;
        tagPanel.add (bTagRadioButton, gridBagConstraints2);

        rTagCheckBox.setText (org.openide.util.NbBundle.getBundle(AddTagDialog.class).getString("AddTagDialog.rTagCheckBox.text"));

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridy = 2;
        gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints2.weightx = 1.0;
        tagPanel.add (rTagCheckBox, gridBagConstraints2);

        revLabel.setText (org.openide.util.NbBundle.getBundle(AddTagDialog.class).getString("AddTagDialog.revLabel.text"));

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridy = 3;
        gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
        tagPanel.add (revLabel, gridBagConstraints2);


        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridy = 3;
        gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints2.weightx = 1.0;
        tagPanel.add (revComboBox, gridBagConstraints2);

        tagLabel.setText (org.openide.util.NbBundle.getBundle(AddTagDialog.class).getString("AddTagDialog.tagLabel.text"));

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridy = 4;
        gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
        tagPanel.add (tagLabel, gridBagConstraints2);


        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridy = 4;
        gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints2.weightx = 1.0;
        tagPanel.add (tagTextField, gridBagConstraints2);


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints1.insets = new java.awt.Insets (8, 8, 8, 8);
        gridBagConstraints1.weightx = 1.0;
        gridBagConstraints1.weighty = 1.0;
        getContentPane ().add (tagPanel, gridBagConstraints1);

        ocPanel.setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints3;

        okButton.setText (org.openide.util.NbBundle.getBundle(AddTagDialog.class).getString("AddTagDialog.okButton.text"));
        okButton.addActionListener (new java.awt.event.ActionListener () {
                                        public void actionPerformed (java.awt.event.ActionEvent evt) {
                                            okButtonActionPerformed (evt);
                                        }
                                    }
                                   );

        gridBagConstraints3 = new java.awt.GridBagConstraints ();
        gridBagConstraints3.gridy = 1;
        gridBagConstraints3.weightx = 1.0;
        ocPanel.add (okButton, gridBagConstraints3);

        cancelButton.setText (org.openide.util.NbBundle.getBundle(AddTagDialog.class).getString("AddTagDialog.cancelButton.text"));
        cancelButton.addActionListener (new java.awt.event.ActionListener () {
                                            public void actionPerformed (java.awt.event.ActionEvent evt) {
                                                cancelButtonActionPerformed (evt);
                                            }
                                        }
                                       );

        gridBagConstraints3 = new java.awt.GridBagConstraints ();
        gridBagConstraints3.gridy = 1;
        gridBagConstraints3.weightx = 1.0;
        ocPanel.add (cancelButton, gridBagConstraints3);


        gridBagConstraints3 = new java.awt.GridBagConstraints ();
        gridBagConstraints3.gridwidth = 2;
        gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints3.insets = new java.awt.Insets (0, 0, 8, 0);
        gridBagConstraints3.weightx = 1.0;
        ocPanel.add (jSeparator1, gridBagConstraints3);


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridy = 1;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.insets = new java.awt.Insets (0, 8, 8, 8);
        gridBagConstraints1.weightx = 1.0;
        getContentPane ().add (ocPanel, gridBagConstraints1);

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

    public void setRevisions(Vector revisions) {
        revisions.add(0, org.openide.util.NbBundle.getBundle(AddTagDialog.class).getString("AddTagDialog.CVSHead"));
        revComboBox.setModel(new javax.swing.DefaultComboBoxModel(revisions));
        revComboBox.setSelectedIndex(0);
    }

    public int getRevision() {
        return revComboBox.getSelectedIndex();
    }

    public String getTagName() {
        return tagTextField.getText();
    }

    public boolean isRepositoryTag() {
        return rTagCheckBox.isSelected();
    }

    public boolean isReleaseTag() {
        return rTagRadioButton.isSelected();
    }

    public boolean showDialog() {
        show();
        return exit == OK;
    }

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) {
        new AddTagDialog (new javax.swing.JFrame (), true).show ();
    }


    private int exit = NONE;
    private static final int NONE = 0;
    private static final int OK = 1;
    private static final int CANCEL = 2;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel tagPanel;
    private javax.swing.JRadioButton rTagRadioButton;
    private javax.swing.JRadioButton bTagRadioButton;
    private javax.swing.JCheckBox rTagCheckBox;
    private javax.swing.JLabel revLabel;
    private javax.swing.JComboBox revComboBox;
    private javax.swing.JLabel tagLabel;
    private javax.swing.JTextField tagTextField;
    private javax.swing.JPanel ocPanel;
    private javax.swing.JButton okButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables

}