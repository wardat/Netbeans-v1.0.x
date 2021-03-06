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

package org.netbeans.modules.form;

import java.beans.*;
import java.lang.reflect.Method;
import java.util.*;

import org.openide.TopManager;
import org.openide.util.Utilities;

/** The MethodPicker is a form which allows user to pick one of methods
* with specified required return type.
*
* @author  Ian Formanek
* @version 1.00, Aug 29, 1998
*/
public class MethodPicker extends javax.swing.JDialog {

    public static final int CANCEL = 0;
    public static final int OK = 1;

    static final long serialVersionUID =7355140527892160804L;
    /** Initializes the Form */
    public MethodPicker(java.awt.Frame parent, FormManager2 manager, RADComponent componentToSelect, Class requiredType) {
        super (parent != null ? parent : TopManager.getDefault ().getWindowManager ().getMainWindow (), true);

        this.manager = manager;
        this.requiredType = requiredType;
        initComponents ();

        insidePanel.setBorder (new javax.swing.border.EmptyBorder (new java.awt.Insets(8, 8, 3, 8)));
        buttonsPanel.setBorder (new javax.swing.border.EmptyBorder (new java.awt.Insets(0, 5, 5, 5)));

        setDefaultCloseOperation (javax.swing.JDialog.DO_NOTHING_ON_CLOSE);
        addWindowListener (new java.awt.event.WindowAdapter () {
                               public void windowClosing (java.awt.event.WindowEvent evt) {
                                   cancelDialog ();
                               }
                           }
                          );

        // attach cancel also to Escape key
        getRootPane().registerKeyboardAction(
            new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    cancelDialog ();
                }
            },
            javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0, true),
            javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW
        );

        Collection allComponents = manager.getAllComponents ();
        components = (RADComponent[])allComponents.toArray (new RADComponent [allComponents.size ()]);
        int selIndex = -1;
        for (int i = 0; i < components.length; i++) {
            componentsCombo.addItem (components[i].getName ());
            if ((componentToSelect != null) && (componentToSelect.equals (components[i])))
                selIndex = i;
        }

        if (selIndex != -1) {
            selectedComponent = components[selIndex];
            componentsCombo.setSelectedIndex (selIndex);
        }

        methodList.setSelectionMode (javax.swing.ListSelectionModel.SINGLE_SELECTION);
        updateMethodList ();

        // localize components
        setTitle ( java.text.MessageFormat.format (
                       FormEditor.getFormBundle ().getString ("CTL_FMT_CW_SelectMethod"),
                       new Object[] { Utilities.getShortClassName (requiredType) }
                   )
                 );
        componentLabel.setText (FormEditor.getFormBundle ().getString ("CTL_CW_Component")); // "Component:"
        okButton.setText (FormEditor.getFormBundle ().getString ("CTL_OK")); // "OK"
        cancelButton.setText (FormEditor.getFormBundle ().getString ("CTL_CANCEL")); //"Cancel"
        //    parametersButton.setText (FormEditor.getFormBundle ().getString ("CTL_CW_Parameters")); // "Parameters"

        updateButtons ();

        pack ();
        org.openidex.util.Utilities2.centerWindow (this);
    }

    public java.awt.Dimension getPreferredSize () {
        java.awt.Dimension pref = super.getPreferredSize ();
        return new java.awt.Dimension (Math.max (pref.width, 250), Math.max (pref.height, 300));
    }

    int getReturnStatus () {
        return returnStatus;
    }

    RADComponent getSelectedComponent () {
        return selectedComponent;
    }

    void setSelectedComponent (RADComponent selectedComponent) {
        if (selectedComponent != null)
            componentsCombo.setSelectedItem (selectedComponent.getName ());
    }

    MethodDescriptor getSelectedMethod () {
        if ((selectedComponent == null) || (methodList.getSelectedIndex () == -1))
            return null;
        return descriptors [methodList.getSelectedIndex ()];
    }

    void setSelectedMethod (MethodDescriptor selectedMethod) {
        if (selectedMethod == null) {
            methodList.setSelectedIndex (-1);
        } else {
            methodList.setSelectedValue (FormUtils.getMethodName (selectedMethod), true);
        }
    }

    // ----------------------------------------------------------------------------
    // private methods

    private void addComponentsRecursively (ComponentContainer cont, Vector vect) {
        RADComponent[] children = cont.getSubBeans ();
        for (int i = 0; i < children.length; i++) {
            vect.addElement (children[i]);
            if (children[i] instanceof ComponentContainer)
                addComponentsRecursively ((ComponentContainer)children[i], vect);
        }
    }

    private void updateMethodList () {
        RADComponent sel = getSelectedComponent ();
        if (sel == null) {
            methodList.setListData (new Object [0]);
            methodList.revalidate ();
            methodList.repaint ();
        } else {
            MethodDescriptor[] descs = sel.getBeanInfo ().getMethodDescriptors ();
            ArrayList filtered = new ArrayList ();
            for (int i = 0; i < descs.length; i ++) {
                if (requiredType.isAssignableFrom (descs[i].getMethod ().getReturnType ()) &&
                        (descs[i].getMethod ().getParameterTypes ().length == 0)) // [FUTURE: - currently we allow only methods without params]
                {
                    filtered.add (descs[i]);
                }
            }
            // sort the methods by name
            Collections.sort (filtered, new Comparator () {
                                  public int compare(Object o1, Object o2) {
                                      return ((MethodDescriptor)o1).getName ().compareTo (((MethodDescriptor)o2).getName ());
                                  }
                              }
                             );

            descriptors = new MethodDescriptor[filtered.size ()];
            filtered.toArray (descriptors);

            String[] items = new String [descriptors.length];
            for (int i = 0; i < descriptors.length; i++)
                items[i] = FormUtils.getMethodName (descriptors[i]);
            methodList.setListData (items);
            methodList.revalidate ();
            methodList.repaint ();
        }
    }

    private void updateButtons () {
        //    parametersButton.setEnabled (false); // [FUTURE: - disabled for now]
        if ((getSelectedComponent () == null) || (getSelectedMethod () == null)) {
            okButton.setEnabled (false);
            //      parametersButton.setEnabled (false);
        } else {
            if (getSelectedMethod ().getMethod ().getParameterTypes ().length > 0) {
                okButton.setEnabled (false);
                //        parametersButton.setEnabled (true);
            } else {
                okButton.setEnabled (true);
                //        parametersButton.setEnabled (false);
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        insidePanel = new javax.swing.JPanel ();
        propertiesScrollPane = new javax.swing.JScrollPane ();
        methodList = new javax.swing.JList ();
        jPanel1 = new javax.swing.JPanel ();
        componentLabel = new javax.swing.JLabel ();
        componentsCombo = new javax.swing.JComboBox ();
        buttonsPanel = new javax.swing.JPanel ();
        leftButtonsPanel = new javax.swing.JPanel ();
        parametersButton = new javax.swing.JButton ();
        rightButtonsPanel = new javax.swing.JPanel ();
        okButton = new javax.swing.JButton ();
        cancelButton = new javax.swing.JButton ();

        insidePanel.setLayout (new java.awt.BorderLayout (0, 5));


        methodList.addListSelectionListener (new javax.swing.event.ListSelectionListener () {
                                                 public void valueChanged (javax.swing.event.ListSelectionEvent evt) {
                                                     methodListValueChanged (evt);
                                                 }
                                             }
                                            );

        propertiesScrollPane.setViewportView (methodList);

        insidePanel.add (propertiesScrollPane, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout (new java.awt.BorderLayout (8, 0));

        componentLabel.setText (FormEditor.getFormBundle ().getString ("CTL_Component"));

        jPanel1.add (componentLabel, java.awt.BorderLayout.WEST);

        componentsCombo.addItemListener (new java.awt.event.ItemListener () {
                                             public void itemStateChanged (java.awt.event.ItemEvent evt) {
                                                 componentsComboItemStateChanged (evt);
                                             }
                                         }
                                        );

        jPanel1.add (componentsCombo, java.awt.BorderLayout.CENTER);

        insidePanel.add (jPanel1, java.awt.BorderLayout.NORTH);


        getContentPane ().add (insidePanel, java.awt.BorderLayout.CENTER);

        buttonsPanel.setLayout (new java.awt.BorderLayout ());

        leftButtonsPanel.setLayout (new java.awt.FlowLayout (0, 5, 5));

        parametersButton.setText (FormEditor.getFormBundle ().getString ("CTL_CW_Parameters"));

        leftButtonsPanel.add (parametersButton);

        buttonsPanel.add (leftButtonsPanel, java.awt.BorderLayout.WEST);

        rightButtonsPanel.setLayout (new java.awt.FlowLayout (2, 5, 5));

        okButton.setText (FormEditor.getFormBundle ().getString ("CTL_OK"));
        okButton.addActionListener (new java.awt.event.ActionListener () {
                                        public void actionPerformed (java.awt.event.ActionEvent evt) {
                                            okButtonActionPerformed (evt);
                                        }
                                    }
                                   );

        rightButtonsPanel.add (okButton);

        cancelButton.setText (FormEditor.getFormBundle ().getString ("CTL_CANCEL"));
        cancelButton.addActionListener (new java.awt.event.ActionListener () {
                                            public void actionPerformed (java.awt.event.ActionEvent evt) {
                                                cancelButtonActionPerformed (evt);
                                            }
                                        }
                                       );

        rightButtonsPanel.add (cancelButton);

        buttonsPanel.add (rightButtonsPanel, java.awt.BorderLayout.EAST);


        getContentPane ().add (buttonsPanel, java.awt.BorderLayout.SOUTH);

    }//GEN-END:initComponents

    private void methodListValueChanged (javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_methodListValueChanged
        if (methodList.getSelectedIndex () == -1)
            selectedMethod = null;
        else
            selectedMethod = descriptors[methodList.getSelectedIndex ()];
        updateButtons ();
    }//GEN-LAST:event_methodListValueChanged

    private void componentsComboItemStateChanged (java.awt.event.ItemEvent evt) {//GEN-FIRST:event_componentsComboItemStateChanged
        if (componentsCombo.getSelectedIndex () == -1)
            selectedComponent = null;
        else
            selectedComponent = components[componentsCombo.getSelectedIndex ()];
        updateMethodList ();
    }//GEN-LAST:event_componentsComboItemStateChanged

    private void okButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        returnStatus = OK;
        setVisible (false);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        cancelDialog ();
    }//GEN-LAST:event_cancelButtonActionPerformed

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:closeDialog
        cancelDialog ();
    }//GEN-LAST:closeDialog

    private void cancelDialog () {
        returnStatus = CANCEL;
        setVisible (false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel insidePanel;
    private javax.swing.JScrollPane propertiesScrollPane;
    private javax.swing.JList methodList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel componentLabel;
    private javax.swing.JComboBox componentsCombo;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JPanel leftButtonsPanel;
    private javax.swing.JButton parametersButton;
    private javax.swing.JPanel rightButtonsPanel;
    private javax.swing.JButton okButton;
    private javax.swing.JButton cancelButton;
    // End of variables declaration//GEN-END:variables


    private FormManager2 manager;
    private int returnStatus = CANCEL;

    private RADComponent[] components;
    private Class requiredType;
    private MethodDescriptor[] descriptors;
    private RADComponent selectedComponent;
    private MethodDescriptor selectedMethod;

}

/*
 * Log
 *  17   Gandalf   1.16        1/12/00  Pavel Buzek     I18N
 *  16   Gandalf   1.15        1/10/00  Ian Formanek    Fixed bug 4936 - 
 *       Incorrect initialization of Form Connection dialog
 *  15   Gandalf   1.14        1/5/00   Ian Formanek    NOI18N
 *  14   Gandalf   1.13        11/27/99 Patrik Knakal   
 *  13   Gandalf   1.12        11/25/99 Ian Formanek    Uses Utilities module
 *  12   Gandalf   1.11        10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  11   Gandalf   1.10        10/9/99  Ian Formanek    Fixed bug 4151 - Menus, 
 *       MenuItems etc. are missing in Component combo box in Form Connection 
 *       (or Connection Wizard).
 *  10   Gandalf   1.9         7/31/99  Ian Formanek    Parameters Button is not
 *       displayed - not in the .form file (i.e. when opened as a form, it will 
 *       be recreated !)
 *  9    Gandalf   1.8         7/31/99  Ian Formanek    Localization completed
 *  8    Gandalf   1.7         6/29/99  Ian Formanek    Removed incorrect cast
 *  7    Gandalf   1.6         6/9/99   Ian Formanek    ---- Package Change To 
 *       org.openide ----
 *  6    Gandalf   1.5         6/1/99   Ian Formanek    Fixed removed event 
 *       handlers
 *  5    Gandalf   1.4         5/31/99  Ian Formanek    Updated to X2 form 
 *       format
 *  4    Gandalf   1.3         5/24/99  Ian Formanek    Non-Visual components
 *  3    Gandalf   1.2         5/17/99  Ian Formanek    Fixed bug 1810 - 
 *       Connection Wizard: the items in list should be alphabetically sorted.
 *  2    Gandalf   1.1         5/15/99  Ian Formanek    
 *  1    Gandalf   1.0         5/13/99  Ian Formanek    
 * $
 */

