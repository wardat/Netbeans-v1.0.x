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

package org.netbeans.modules.debugger.delegator;

import java.awt.Component;
import javax.swing.*;


/**
* Shows all curently debugged sessions and its persistency.
*
* @author   Jan Jancura
*/
public class FinishActionPanel extends javax.swing.JPanel {

    static final long serialVersionUID =-3129231084066441254L;
    /** Creates new form FinishActionPanel */
    public FinishActionPanel() {
        initComponents ();
        jList3.setCellRenderer (new SessionCellRenderer ());
        jList3.getSelectionModel ().setSelectionMode (ListSelectionModel.SINGLE_SELECTION);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        setLayout (new java.awt.BorderLayout ());

        jPanel1 = new javax.swing.JPanel ();
        jPanel1.setLayout (new java.awt.BorderLayout ());
        jPanel1.setBorder (new javax.swing.border.CompoundBorder(
                               new javax.swing.border.TitledBorder(
                                   new javax.swing.border.EtchedBorder(), "Finish sessions: "), // NOI18N
                               new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5))));

        jList3 = new javax.swing.JList ();
        jList3.addKeyListener (new java.awt.event.KeyAdapter () {
                                   public void keyPressed (java.awt.event.KeyEvent evt) {
                                       jList3KeyPressed (evt);
                                   }
                               }
                              );
        jList3.addMouseListener (new java.awt.event.MouseAdapter () {
                                     public void mousePressed (java.awt.event.MouseEvent evt) {
                                         jList3MousePressed (evt);
                                     }
                                 }
                                );

        jPanel1.add (jList3, "Center"); // NOI18N


        add (jPanel1, "Center"); // NOI18N

        jCheckBox1 = new javax.swing.JCheckBox ();
        jCheckBox1.setText ("Do not show this dialog next time."); // NOI18N


        add (jCheckBox1, "South"); // NOI18N

    }//GEN-END:initComponents

    private void jList3MousePressed (java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList3MousePressed
        // Add your handling code here:
        if (evt.getClickCount() == 1)
            toggleValue ();
    }//GEN-LAST:event_jList3MousePressed

    private void jList3KeyPressed (java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList3KeyPressed
        // Add your handling code here:
        if (evt.getKeyCode () == java.awt.event.KeyEvent.VK_SPACE)
            toggleValue ();
    }//GEN-LAST:event_jList3KeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JList jList3;
    private javax.swing.JCheckBox jCheckBox1;
    // End of variables declaration//GEN-END:variables
    private boolean[] state;



    // main methods ....................................................................................

    public void setSessions (Session[] sessions) {
        jList3.setListData (sessions);
        state = new boolean [sessions.length];
        int i, k = sessions.length;
        for (i = 0; i < k; i++)
            state [i] = sessions [i].isPersistent ();
    }

    public boolean[] getState () {
        return state;
    }

    public void setShowFinishDialog (boolean show) {
        jCheckBox1.setSelected (!show);
    }

    public boolean getShowFinishDialog () {
        return !jCheckBox1.isSelected ();
    }


    // private methods ....................................................................................

    private void toggleValue () {
        int sel = jList3.getMinSelectionIndex ();
        if (sel < 0) return;
        state [sel] = !state [sel];
        jList3.repaint();
    }


    // innerclasses ....................................................................................

    private class SessionCellRenderer extends JCheckBox implements ListCellRenderer {

        static final long serialVersionUID =9177911840632809890L;
        public Component getListCellRendererComponent (
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus
        ) {
            if (isSelected) {
                setBackground (list.getSelectionBackground ());
                setForeground (list.getSelectionForeground ());
            } else {
                setBackground (list.getBackground ());
                setForeground (list.getForeground ());
            }
            Session s = (Session) value;
            String n = s.getLocationName ();
            String nn = s.getSessionName () + (n.equals ("localhost") ? "" : (" : " + n)); // NOI18N
            setText (nn);
            setSelected (!state [index]);
            setEnabled (list.isEnabled ());
            setFont (list.getFont ());
            return this;
        }
    }
}
/*
 * Log
 *  1    Jaga      1.0         2/25/00  Daniel Prusa    
 * $
 */
