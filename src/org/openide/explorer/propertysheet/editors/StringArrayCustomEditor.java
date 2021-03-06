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

package org.openide.explorer.propertysheet.editors;

import java.util.Vector;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.*;

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/** A custom editor for array of Strings.
*
* @author  Ian Formanek
* @version 1.00, Sep 21, 1998
*/
public class StringArrayCustomEditor extends javax.swing.JPanel {

    // the bundle to use
    static ResourceBundle bundle = NbBundle.getBundle (
                                       StringArrayCustomEditor.class);

    /**
     * @associates String 
     */
    private Vector itemsVector;
    private StringArrayCustomizable editor;

    private final static int DEFAULT_WIDTH = 400;

    static final long serialVersionUID =-4347656479280614636L;

    /** Initializes the Form */
    public StringArrayCustomEditor(StringArrayCustomizable sac) {
        editor = sac;
        itemsVector = new Vector ();
        String[] array = editor.getStringArray ();
        if (array != null)
            for (int i = 0; i < array.length; i++)
                itemsVector.addElement (array[i]);
        initComponents ();
        itemList.setCellRenderer (new EmptyStringListCellRenderer ());
        itemList.setListData (itemsVector);
        itemList.setSelectionMode (ListSelectionModel.SINGLE_SELECTION);

        setBorder (new javax.swing.border.EmptyBorder (new java.awt.Insets(16, 8, 8, 0)));
        buttonsPanel.setBorder (new javax.swing.border.EmptyBorder (new java.awt.Insets(0, 5, 5, 5)));

        itemLabel.setText (bundle.getString ("CTL_Item"));
        addButton.setText (bundle.getString ("CTL_Add"));
        changeButton.setText (bundle.getString ("CTL_Change"));
        removeButton.setText (bundle.getString ("CTL_Remove"));
        moveUpButton.setText (bundle.getString ("CTL_MoveUp"));
        moveDownButton.setText (bundle.getString ("CTL_MoveDown"));

        updateButtons ();

        HelpCtx.setHelpIDString (this, StringArrayCustomEditor.class.getName ());
    }

    public java.awt.Dimension getPreferredSize () {
        // ensure minimum width
        java.awt.Dimension sup = super.getPreferredSize ();
        return new java.awt.Dimension (Math.max (sup.width, DEFAULT_WIDTH), sup.height);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        editPanel = new javax.swing.JPanel ();
        itemListScroll = new javax.swing.JScrollPane ();
        itemList = new javax.swing.JList ();
        itemPanel = new javax.swing.JPanel ();
        itemLabel = new javax.swing.JLabel ();
        itemField = new javax.swing.JTextField ();
        buttonsPanel = new javax.swing.JPanel ();
        addButton = new javax.swing.JButton ();
        changeButton = new javax.swing.JButton ();
        removeButton = new javax.swing.JButton ();
        jSeparator1 = new javax.swing.JSeparator ();
        moveUpButton = new javax.swing.JButton ();
        moveDownButton = new javax.swing.JButton ();
        paddingPanel = new javax.swing.JPanel ();
        setLayout (new java.awt.BorderLayout ());

        editPanel.setLayout (new java.awt.BorderLayout (0, 8));


        itemList.addListSelectionListener (new javax.swing.event.ListSelectionListener () {
                                               public void valueChanged (javax.swing.event.ListSelectionEvent evt) {
                                                   itemListValueChanged (evt);
                                               }
                                           }
                                          );

        itemListScroll.setViewportView (itemList);

        editPanel.add (itemListScroll, java.awt.BorderLayout.CENTER);

        itemPanel.setLayout (new java.awt.BorderLayout (8, 0));


        itemPanel.add (itemLabel, java.awt.BorderLayout.WEST);


        itemPanel.add (itemField, java.awt.BorderLayout.CENTER);

        editPanel.add (itemPanel, java.awt.BorderLayout.NORTH);


        add (editPanel, java.awt.BorderLayout.CENTER);

        buttonsPanel.setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints1;

        addButton.addActionListener (new java.awt.event.ActionListener () {
                                         public void actionPerformed (java.awt.event.ActionEvent evt) {
                                             addButtonActionPerformed (evt);
                                         }
                                     }
                                    );

        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints1.insets = new java.awt.Insets (0, 8, 0, 8);
        gridBagConstraints1.weightx = 1.0;
        buttonsPanel.add (addButton, gridBagConstraints1);

        changeButton.addActionListener (new java.awt.event.ActionListener () {
                                            public void actionPerformed (java.awt.event.ActionEvent evt) {
                                                changeButtonActionPerformed (evt);
                                            }
                                        }
                                       );

        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints1.insets = new java.awt.Insets (8, 8, 0, 8);
        gridBagConstraints1.weightx = 1.0;
        buttonsPanel.add (changeButton, gridBagConstraints1);

        removeButton.addActionListener (new java.awt.event.ActionListener () {
                                            public void actionPerformed (java.awt.event.ActionEvent evt) {
                                                removeButtonActionPerformed (evt);
                                            }
                                        }
                                       );

        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints1.insets = new java.awt.Insets (8, 8, 8, 8);
        gridBagConstraints1.weightx = 1.0;
        buttonsPanel.add (removeButton, gridBagConstraints1);


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.insets = new java.awt.Insets (0, 4, 0, 4);
        buttonsPanel.add (jSeparator1, gridBagConstraints1);

        moveUpButton.setEnabled (false);
        moveUpButton.addActionListener (new java.awt.event.ActionListener () {
                                            public void actionPerformed (java.awt.event.ActionEvent evt) {
                                                moveUpButtonActionPerformed (evt);
                                            }
                                        }
                                       );

        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints1.insets = new java.awt.Insets (8, 8, 0, 8);
        gridBagConstraints1.weightx = 1.0;
        buttonsPanel.add (moveUpButton, gridBagConstraints1);

        moveDownButton.setEnabled (false);
        moveDownButton.addActionListener (new java.awt.event.ActionListener () {
                                              public void actionPerformed (java.awt.event.ActionEvent evt) {
                                                  moveDownButtonActionPerformed (evt);
                                              }
                                          }
                                         );

        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints1.insets = new java.awt.Insets (8, 8, 0, 8);
        gridBagConstraints1.weightx = 1.0;
        buttonsPanel.add (moveDownButton, gridBagConstraints1);


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.weighty = 1.0;
        buttonsPanel.add (paddingPanel, gridBagConstraints1);


        add (buttonsPanel, java.awt.BorderLayout.EAST);

    }//GEN-END:initComponents

    private void changeButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeButtonActionPerformed
        int sel = itemList.getSelectedIndex ();
        String s = (String) itemsVector.elementAt (sel);
        itemsVector.removeElementAt (sel);
        itemsVector.insertElementAt (itemField.getText (), sel);
        itemList.setListData (itemsVector);
        itemList.setSelectedIndex (sel);
        itemList.repaint ();
        updateValue ();
    }//GEN-LAST:event_changeButtonActionPerformed

    private void moveDownButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveDownButtonActionPerformed
        int sel = itemList.getSelectedIndex ();
        String s = (String) itemsVector.elementAt (sel);
        itemsVector.removeElementAt (sel);
        itemsVector.insertElementAt (s, sel + 1);
        itemList.setListData (itemsVector);
        itemList.setSelectedIndex (sel + 1);
        itemList.repaint ();
        updateValue ();
    }//GEN-LAST:event_moveDownButtonActionPerformed

    private void moveUpButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveUpButtonActionPerformed
        int sel = itemList.getSelectedIndex ();
        String s = (String) itemsVector.elementAt (sel);
        itemsVector.removeElementAt (sel);
        itemsVector.insertElementAt (s, sel - 1);
        itemList.setListData (itemsVector);
        itemList.setSelectedIndex (sel - 1);
        itemList.repaint ();
        updateValue ();
    }//GEN-LAST:event_moveUpButtonActionPerformed

    private void removeButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        // Add your handling code here:
        int currentIndex = itemList.getSelectedIndex ();
        itemsVector.removeElementAt (currentIndex);
        itemList.setListData (itemsVector);

        // set new selection
        if (itemsVector.size () != 0) {
            if (currentIndex >= itemsVector.size ())
                currentIndex = itemsVector.size () - 1;
            itemList.setSelectedIndex (currentIndex);
        }

        itemList.repaint ();

        updateValue ();
    }//GEN-LAST:event_removeButtonActionPerformed

    private void itemListValueChanged (javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_itemListValueChanged
        // Add your handling code here:
        updateButtons ();
        int sel = itemList.getSelectedIndex ();
        if (sel != -1) {
            itemField.setText ((String) itemsVector.elementAt (sel));
        }
    }//GEN-LAST:event_itemListValueChanged

    private void addButtonActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // Add your handling code here:
        itemsVector.addElement (itemField.getText ());
        itemList.setListData (itemsVector);
        itemList.setSelectedIndex (itemsVector.size () - 1);
        itemList.repaint ();
        updateValue ();
    }//GEN-LAST:event_addButtonActionPerformed

    private void updateButtons () {
        int sel = itemList.getSelectedIndex ();
        if (sel == -1) {
            removeButton.setEnabled (false);
            moveUpButton.setEnabled (false);
            moveDownButton.setEnabled (false);
            changeButton.setEnabled (false);
        } else {
            removeButton.setEnabled (true);
            moveUpButton.setEnabled (sel != 0);
            moveDownButton.setEnabled (sel != itemsVector.size () - 1);
            changeButton.setEnabled (true);
        }
    }

    private void updateValue () {
        String [] value = new String [itemsVector.size()];
        itemsVector.copyInto (value);
        editor.setStringArray (value);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel editPanel;
    private javax.swing.JScrollPane itemListScroll;
    private javax.swing.JList itemList;
    private javax.swing.JPanel itemPanel;
    private javax.swing.JLabel itemLabel;
    private javax.swing.JTextField itemField;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton addButton;
    private javax.swing.JButton changeButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton moveUpButton;
    private javax.swing.JButton moveDownButton;
    private javax.swing.JPanel paddingPanel;
    // End of variables declaration//GEN-END:variables

    static class EmptyStringListCellRenderer extends JLabel implements ListCellRenderer {

        protected static Border hasFocusBorder;
        protected static Border noFocusBorder;

        static {
            hasFocusBorder = new LineBorder(UIManager.getColor("List.focusCellHighlight")); // NOI18N
            noFocusBorder = new EmptyBorder(1, 1, 1, 1);
        }

        static final long serialVersionUID =487512296465844339L;
        /** Creates a new NodeListCellRenderer */
        public EmptyStringListCellRenderer () {
            setOpaque (true);
            setBorder (noFocusBorder);
        }

        /** This is the only method defined by ListCellRenderer.  We just
        * reconfigure the Jlabel each time we're called.
        */
        public java.awt.Component getListCellRendererComponent(
            JList list,
            Object value,            // value to display
            int index,               // cell index
            boolean isSelected,      // is the cell selected
            boolean cellHasFocus)    // the list and the cell have the focus
        {
            if (!(value instanceof String)) return this;
            String text = (String)value;
            if ("".equals (text)) text = bundle.getString ("CTL_Empty");

            setText(text);
            if (isSelected){
                setBackground(UIManager.getColor("List.selectionBackground")); // NOI18N
                setForeground(UIManager.getColor("List.selectionForeground")); // NOI18N
            }
            else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            setBorder(cellHasFocus ? hasFocusBorder : noFocusBorder);

            return this;
        }
    }
}

/*
 * Log
 *  12   Gandalf   1.11        1/12/00  Ian Formanek    NOI18N
 *  11   Gandalf   1.10        10/22/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  10   Gandalf   1.9         8/29/99  Ian Formanek    Fixed bug 2205 - 
 *       "Property search path" Property Editor bugs - minimum width is now 400
 *  9    Gandalf   1.8         8/9/99   Ian Formanek    Generated Serial Version
 *       UID
 *  8    Gandalf   1.7         7/8/99   Jesse Glick     Context help.
 *  7    Gandalf   1.6         6/30/99  Ian Formanek    Moved to package 
 *       org.openide.explorer.propertysheet.editors
 *  6    Gandalf   1.5         6/8/99   Ian Formanek    ---- Package Change To 
 *       org.openide ----
 *  5    Gandalf   1.4         6/2/99   Ian Formanek    Fixed event handlers
 *  4    Gandalf   1.3         5/31/99  Ian Formanek    Updated to X2 format
 *  3    Gandalf   1.2         4/3/99   Ian Formanek    Fixed bug 1320 - 
 *       Connecion wizard does not work. No wizard window is opened.
 *  2    Gandalf   1.1         3/4/99   Jan Jancura     bundle moved
 *  1    Gandalf   1.0         1/5/99   Ian Formanek    
 * $
 */
