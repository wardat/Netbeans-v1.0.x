/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2001 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.rmi.wizard;

import java.awt.event.*;
import java.beans.*;
import javax.swing.*;

import org.openide.*;
import org.openide.nodes.*;
import org.openide.loaders.*;
import org.openide.util.*;
import org.openide.util.HelpCtx;
import org.openide.WizardDescriptor;

/** BeanTypePanel is a wizard panel used in [TODO: name of wizard].
 *
 * @author   spsenicka
 */
public class RMINamePanel extends AbstractWizardPanel {

    // ---------------------------------------------------------------------------------------
    // WizardPanel initialization

    static final long serialVersionUID =2709571442037733753L;
    /** Creates new BeanTypePanel */
    public RMINamePanel() {
        initComponents();
    }

    //
    protected DataFolder targetFolder;

    protected void updateNames() {
        String name = nameField.getText();
        interfaceField.setText(name);
        implField.setText(name + (name.equals("") ? "": RMIWizardData.NAME_IMPL));
        stupField.setText(name + (name.equals("") ? "": RMIWizardData.NAME_STUP));
        policyField.setText(name);
        fireChange();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints1;
        setBorder (new javax.swing.border.EmptyBorder(new java.awt.Insets(10, 10, 10, 10)));
        setPreferredSize (new java.awt.Dimension(480, 320));

        titleLabel = new javax.swing.JLabel ();
        titleLabel.setFont (new java.awt.Font ("Dialog", 0, 18));
        titleLabel.setText ("RMI Object name and package");


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.insets = new java.awt.Insets (2, 2, 10, 2);
        gridBagConstraints1.weightx = 1.0;
        add (titleLabel, gridBagConstraints1);

        contentPanel = new javax.swing.JPanel ();
        contentPanel.setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints2;
        contentPanel.setPreferredSize (new java.awt.Dimension(320, 200));

        jLabel1 = new javax.swing.JLabel ();
        jLabel1.setText ("RMI name: ");

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridx = 0;
        gridBagConstraints2.gridy = 0;
        gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
        contentPanel.add (jLabel1, gridBagConstraints2);

        nameField = new javax.swing.JTextField ();
        nameField.addActionListener (new java.awt.event.ActionListener () {
                                         public void actionPerformed (java.awt.event.ActionEvent evt) {
                                             nameFieldActionPerformed (evt);
                                         }
                                     }
                                    );
        nameField.addFocusListener (new java.awt.event.FocusAdapter () {
                                        public void focusLost (java.awt.event.FocusEvent evt) {
                                            nameFieldFocusLost (evt);
                                        }
                                    }
                                   );

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridx = 1;
        gridBagConstraints2.gridy = 0;
        gridBagConstraints2.gridwidth = 2;
        gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints2.weightx = 1.0;
        contentPanel.add (nameField, gridBagConstraints2);

        jLabel2 = new javax.swing.JLabel ();
        jLabel2.setText ("Package: ");

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridx = 0;
        gridBagConstraints2.gridy = 1;
        gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
        contentPanel.add (jLabel2, gridBagConstraints2);

        packageField = new javax.swing.JTextField ();
        packageField.setEditable (false);
        packageField.setBackground (java.awt.Color.lightGray);

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridx = 1;
        gridBagConstraints2.gridy = 1;
        gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints2.weightx = 1.0;
        contentPanel.add (packageField, gridBagConstraints2);

        chooseButton = new javax.swing.JButton ();
        chooseButton.setFont (new java.awt.Font ("SansSerif", 0, 11));
        chooseButton.setMinimumSize (new java.awt.Dimension(73, 15));
        chooseButton.setText ("Choose...");
        chooseButton.setMaximumSize (new java.awt.Dimension(100, 27));
        chooseButton.setPreferredSize (new java.awt.Dimension(80, 20));
        chooseButton.addActionListener (new java.awt.event.ActionListener () {
                                            public void actionPerformed (java.awt.event.ActionEvent evt) {
                                                chooseActionPerformed (evt);
                                            }
                                        }
                                       );

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridx = 2;
        gridBagConstraints2.gridy = 1;
        gridBagConstraints2.insets = new java.awt.Insets (2, 5, 2, 0);
        contentPanel.add (chooseButton, gridBagConstraints2);

        jPanel1 = new javax.swing.JPanel ();
        jPanel1.setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints3;
        jPanel1.setBorder (new javax.swing.border.TitledBorder(
                               new javax.swing.border.EtchedBorder(), "Related files"));

        jLabel3 = new javax.swing.JLabel ();
        jLabel3.setText ("Implementation");

        gridBagConstraints3 = new java.awt.GridBagConstraints ();
        gridBagConstraints3.gridx = 0;
        gridBagConstraints3.gridy = 1;
        gridBagConstraints3.insets = new java.awt.Insets (1, 5, 1, 5);
        gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add (jLabel3, gridBagConstraints3);

        jLabel4 = new javax.swing.JLabel ();
        jLabel4.setText ("Interface");

        gridBagConstraints3 = new java.awt.GridBagConstraints ();
        gridBagConstraints3.gridx = 0;
        gridBagConstraints3.gridy = 0;
        gridBagConstraints3.insets = new java.awt.Insets (1, 5, 1, 5);
        gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add (jLabel4, gridBagConstraints3);

        jLabel5 = new javax.swing.JLabel ();
        jLabel5.setText ("Startup");

        gridBagConstraints3 = new java.awt.GridBagConstraints ();
        gridBagConstraints3.gridx = 0;
        gridBagConstraints3.gridy = 2;
        gridBagConstraints3.insets = new java.awt.Insets (1, 5, 1, 5);
        gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add (jLabel5, gridBagConstraints3);

        jLabel6 = new javax.swing.JLabel ();
        jLabel6.setText ("Policy");

        gridBagConstraints3 = new java.awt.GridBagConstraints ();
        gridBagConstraints3.gridx = 0;
        gridBagConstraints3.gridy = 3;
        gridBagConstraints3.insets = new java.awt.Insets (1, 5, 1, 5);
        gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add (jLabel6, gridBagConstraints3);

        interfaceField = new javax.swing.JTextField ();
        interfaceField.addActionListener (new java.awt.event.ActionListener () {
                                              public void actionPerformed (java.awt.event.ActionEvent evt) {
                                                  interfaceFieldActionPerformed (evt);
                                              }
                                          }
                                         );
        interfaceField.addFocusListener (new java.awt.event.FocusAdapter () {
                                             public void focusLost (java.awt.event.FocusEvent evt) {
                                                 interfaceFieldFocusLost (evt);
                                             }
                                         }
                                        );

        gridBagConstraints3 = new java.awt.GridBagConstraints ();
        gridBagConstraints3.gridx = 1;
        gridBagConstraints3.gridy = 0;
        gridBagConstraints3.gridwidth = 2;
        gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints3.insets = new java.awt.Insets (1, 5, 1, 5);
        gridBagConstraints3.weightx = 1.0;
        jPanel1.add (interfaceField, gridBagConstraints3);

        implField = new javax.swing.JTextField ();
        implField.addActionListener (new java.awt.event.ActionListener () {
                                         public void actionPerformed (java.awt.event.ActionEvent evt) {
                                             implFieldActionPerformed (evt);
                                         }
                                     }
                                    );
        implField.addFocusListener (new java.awt.event.FocusAdapter () {
                                        public void focusLost (java.awt.event.FocusEvent evt) {
                                            implFieldFocusLost (evt);
                                        }
                                    }
                                   );

        gridBagConstraints3 = new java.awt.GridBagConstraints ();
        gridBagConstraints3.gridx = 1;
        gridBagConstraints3.gridwidth = 2;
        gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints3.insets = new java.awt.Insets (1, 5, 1, 5);
        gridBagConstraints3.weightx = 1.0;
        jPanel1.add (implField, gridBagConstraints3);

        stupField = new javax.swing.JTextField ();

        gridBagConstraints3 = new java.awt.GridBagConstraints ();
        gridBagConstraints3.gridx = 1;
        gridBagConstraints3.gridy = 2;
        gridBagConstraints3.gridwidth = 2;
        gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints3.insets = new java.awt.Insets (1, 5, 1, 5);
        gridBagConstraints3.weightx = 1.0;
        jPanel1.add (stupField, gridBagConstraints3);

        policyField = new javax.swing.JTextField ();
        policyField.setEnabled (false);

        gridBagConstraints3 = new java.awt.GridBagConstraints ();
        gridBagConstraints3.gridx = 1;
        gridBagConstraints3.gridy = 3;
        gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints3.insets = new java.awt.Insets (1, 5, 1, 1);
        gridBagConstraints3.weightx = 1.0;
        jPanel1.add (policyField, gridBagConstraints3);

        jCheckBox1 = new javax.swing.JCheckBox ();
        jCheckBox1.addItemListener (new java.awt.event.ItemListener () {
                                        public void itemStateChanged (java.awt.event.ItemEvent evt) {
                                            jCheckBox1ItemStateChanged (evt);
                                        }
                                    }
                                   );

        gridBagConstraints3 = new java.awt.GridBagConstraints ();
        gridBagConstraints3.gridx = 2;
        gridBagConstraints3.gridy = 3;
        gridBagConstraints3.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints3.insets = new java.awt.Insets (0, 1, 0, 0);
        jPanel1.add (jCheckBox1, gridBagConstraints3);

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridx = 0;
        gridBagConstraints2.gridy = 2;
        gridBagConstraints2.gridwidth = 3;
        gridBagConstraints2.gridheight = 0;
        gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints2.insets = new java.awt.Insets (10, 0, 10, 0);
        gridBagConstraints2.weightx = 1.0;
        gridBagConstraints2.weighty = 1.0;
        contentPanel.add (jPanel1, gridBagConstraints2);

        jPanel2 = new javax.swing.JPanel ();
        jPanel2.setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints4;

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridx = 0;
        gridBagConstraints2.gridwidth = 3;
        gridBagConstraints2.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints2.weightx = 1.0;
        gridBagConstraints2.weighty = 1.0;
        contentPanel.add (jPanel2, gridBagConstraints2);


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints1.weightx = 1.0;
        gridBagConstraints1.weighty = 1.0;
        add (contentPanel, gridBagConstraints1);

    }//GEN-END:initComponents

    private void implFieldActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_implFieldActionPerformed
        // Add your handling code here:
        fireChange();
    }//GEN-LAST:event_implFieldActionPerformed

    private void implFieldFocusLost (java.awt.event.FocusEvent evt) {//GEN-FIRST:event_implFieldFocusLost
        // Add your handling code here:
        fireChange();
    }//GEN-LAST:event_implFieldFocusLost

    private void interfaceFieldFocusLost (java.awt.event.FocusEvent evt) {//GEN-FIRST:event_interfaceFieldFocusLost
        // Add your handling code here:
        fireChange();
    }//GEN-LAST:event_interfaceFieldFocusLost

    private void interfaceFieldActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_interfaceFieldActionPerformed
        // Add your handling code here:
        fireChange();
    }//GEN-LAST:event_interfaceFieldActionPerformed

    private void nameFieldActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameFieldActionPerformed
        // Add your handling code here:
        updateNames();
    }//GEN-LAST:event_nameFieldActionPerformed

    private void nameFieldFocusLost (java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameFieldFocusLost
        // Add your handling code here:
        updateNames();
    }//GEN-LAST:event_nameFieldFocusLost


    private void jCheckBox1ItemStateChanged (java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        // Add your handling code here:
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            // activate policyField
            policyField.setEnabled(true);
        } else {
            // deactivate policyField
            policyField.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBox1ItemStateChanged



    private void chooseActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseActionPerformed
        String title = "Select folder...";
        Node place = TopManager.getDefault().getPlaces().nodes().repository();
        try {
            Node[] selected = TopManager.getDefault().getNodeOperation().select(title, title, place, new NodeAcceptor() {
                                  public final boolean acceptNodes(Node[] nodes) {
                                      if (nodes == null || nodes.length != 1) return false;
                                      return nodes[0].getCookie(DataFolder.class) != null;
                                  }
                              });

            setTargetFolder((DataFolder)selected[0].getCookie(DataFolder.class));

        } catch (UserCancelException ex) {
            return;
        }
    }//GEN-LAST:event_chooseActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField nameField;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField packageField;
    private javax.swing.JButton chooseButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField interfaceField;
    private javax.swing.JTextField implField;
    private javax.swing.JTextField stupField;
    private javax.swing.JTextField policyField;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

    // ---------------------------------------------------------------------------------------

    protected void setTargetFolder(DataFolder targetFolder) {
        this.targetFolder = targetFolder;
        if (targetFolder != null) {
            String pkg = targetFolder.getName();
            if (pkg.equals("")) pkg = RMIWizardData.NOPACKAGE;
            packageField.setText(pkg);
        }
        fireChange();
    }

    // WizardDescriptor.Panel implementation

    public boolean isValid () {
        return (targetFolder != null) && !interfaceField.getText().equals("") && !implField.getText().equals("");
    }

    protected void readRMISettings(RMIWizardData data) {
        nameField.setText(data.name);
        interfaceField.setText(data.interfaceName);
        implField.setText(data.implName);
        stupField.setText(data.stupName);
        policyField.setText(data.policyName);
        jCheckBox1.setSelected(data.usePolicy);
        setTargetFolder(data.targetFolder);
    }

    protected void storeRMISettings(RMIWizardData data) {
        data.interfaceName = interfaceField.getText();
        data.implName = implField.getText();
        data.stupName = stupField.getText();
        data.policyName = policyField.getText();
        data.usePolicy = jCheckBox1.isSelected();
        data.targetFolder = targetFolder;
    }
}

/*
 * <<Log>>
 *  7    Gandalf   1.6         11/27/99 Patrik Knakal   
 *  6    Gandalf   1.5         10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  5    Gandalf   1.4         7/27/99  Martin Ryzl     
 *  4    Gandalf   1.3         7/22/99  Martin Ryzl     first working version
 *  3    Gandalf   1.2         7/20/99  Martin Ryzl     
 *  2    Gandalf   1.1         7/20/99  Martin Ryzl     
 *  1    Gandalf   1.0         7/19/99  Martin Ryzl     
 * $
 */
