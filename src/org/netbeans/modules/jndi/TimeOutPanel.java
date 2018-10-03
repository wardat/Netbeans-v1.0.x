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

package org.netbeans.modules.jndi;

/**
 *
 * @author  tzezula
 * @version 
 */
public class TimeOutPanel extends javax.swing.JPanel {

    /** Creates new form TimeOutPanel */
    public TimeOutPanel(String message, String note) {
        initComponents ();
        postInitComponents(message, note);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        jLabel1 = new javax.swing.JLabel ();
        jTextArea1 = new javax.swing.JTextArea ();
        setLayout (new java.awt.GridLayout (2, 1));

        jLabel1.setText ("jLabel1");


        add (jLabel1);

        jTextArea1.setPreferredSize (new java.awt.Dimension(64, 32));
        jTextArea1.setMinimumSize (new java.awt.Dimension(64, 32));
        jTextArea1.setEditable (false);


        add (jTextArea1);

    }//GEN-END:initComponents

    private void postInitComponents(String message, String note){
        this.jLabel1.setText(message);
        this.jTextArea1.setText(note);
        this.jTextArea1.setEnabled(false);
        this.jTextArea1.setBackground(this.getBackground());
        this.jTextArea1.setLineWrap(true);
        this.jTextArea1.setWrapStyleWord(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

}