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

package org.netbeans.modules.editor.options;

import java.awt.Insets;
import java.util.ResourceBundle;

import org.openide.NotifyDescriptor;
import org.openide.TopManager;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.explorer.propertysheet.editors.EnhancedCustomPropertyEditor;

/** Custom editor for java.awt.Insets allowing to set per cent values
 *  as negative numbers.
 *
 * @author   Petr Nejedly
 * @author   Ian Formanek
 */
public class ScrollInsetsCustomEditor extends javax.swing.JPanel implements EnhancedCustomPropertyEditor {

    // the bundle to use
    static ResourceBundle bundle = NbBundle.getBundle (
                                       ScrollInsetsCustomEditor.class);

    static final long serialVersionUID =-1472891501739636852L;

    private ScrollInsetsEditor editor;

    /** Initializes the Form */
    public ScrollInsetsCustomEditor(ScrollInsetsEditor editor) {
        initComponents ();
        this.editor = editor;
        Insets insets = (Insets)editor.getValue();

        if (insets == null) insets = new Insets( 0, 0, 0, 0 );

        topField.setText( int2percent( insets.top ) );                     // NOI18N
        leftField.setText( int2percent( insets.left ) );                   // NOI18N
        bottomField.setText( int2percent( insets.bottom ) );               // NOI18N
        rightField.setText( int2percent( insets.right ) );                 // NOI18N

        jPanel2.setBorder (new javax.swing.border.CompoundBorder (
                               new javax.swing.border.TitledBorder (
                                   new javax.swing.border.EtchedBorder (),
                                   bundle.getString ("SICE_Insets")),
                               new javax.swing.border.EmptyBorder (new java.awt.Insets(5, 5, 5, 5))));

        HelpCtx.setHelpIDString (this, ScrollInsetsCustomEditor.class.getName ());

        setPreferredSize( new java.awt.Dimension( 280, getPreferredSize().height ) );
    }

    public Object getPropertyValue () throws IllegalStateException {
        try {
            return getValue();
        } catch (NumberFormatException e) {
            TopManager.getDefault().notify( new NotifyDescriptor.Message(
                                                bundle.getString("SIC_InvalidValue"),
                                                NotifyDescriptor.ERROR_MESSAGE
                                            ) );
            throw new IllegalStateException();
        }
    }



    public static String int2percent( int i ) {
        if( i < 0 ) return( "" + (-i) + "%" );
        else return( "" + i );
    }

    private int percent2int( String val ) throws NumberFormatException {
        val = val.trim();
        if( val.endsWith( "%" ) ) {
            return -Math.abs( Integer.parseInt( val.substring( 0, val.length() - 1 ) ) );
        } else {
            return Integer.parseInt( val );
        }
    }

    Insets getValue() throws NumberFormatException {
        int top = percent2int( topField.getText() );
        int left = percent2int( leftField.getText() );
        int bottom = percent2int( bottomField.getText() );
        int right = percent2int( rightField.getText() );
        return new Insets( top, left, bottom, right );
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        jPanel2 = new javax.swing.JPanel ();
        topLabel = new javax.swing.JLabel ();
        topField = new javax.swing.JTextField ();
        leftLabel = new javax.swing.JLabel ();
        leftField = new javax.swing.JTextField ();
        bottomLabel = new javax.swing.JLabel ();
        bottomField = new javax.swing.JTextField ();
        rightLabel = new javax.swing.JLabel ();
        rightField = new javax.swing.JTextField ();
        setLayout (new javax.swing.BoxLayout (this, 0));
        setBorder (new javax.swing.border.EmptyBorder( new java.awt.Insets( 5, 5, 5, 5 ) ) );

        jPanel2.setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints1;

        topLabel.setLabelFor (topField);
        topLabel.setText (bundle.getString( "SICE_Top" ));

        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add (topLabel, gridBagConstraints1);

        topField.addActionListener (new java.awt.event.ActionListener () {
                                        public void actionPerformed (java.awt.event.ActionEvent evt) {
                                            updateInsets (evt);
                                        }
                                    }
                                   );

        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.insets = new java.awt.Insets (4, 8, 4, 0);
        gridBagConstraints1.weightx = 1.0;
        jPanel2.add (topField, gridBagConstraints1);

        leftLabel.setLabelFor (leftField);
        leftLabel.setText (bundle.getString( "SICE_Left" ));

        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add (leftLabel, gridBagConstraints1);

        leftField.addActionListener (new java.awt.event.ActionListener () {
                                         public void actionPerformed (java.awt.event.ActionEvent evt) {
                                             updateInsets (evt);
                                         }
                                     }
                                    );

        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.insets = new java.awt.Insets (4, 8, 4, 0);
        gridBagConstraints1.weightx = 1.0;
        jPanel2.add (leftField, gridBagConstraints1);

        bottomLabel.setLabelFor (bottomField);
        bottomLabel.setText (bundle.getString( "SICE_Bottom" ));

        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add (bottomLabel, gridBagConstraints1);

        bottomField.addActionListener (new java.awt.event.ActionListener () {
                                           public void actionPerformed (java.awt.event.ActionEvent evt) {
                                               updateInsets (evt);
                                           }
                                       }
                                      );

        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.insets = new java.awt.Insets (4, 8, 4, 0);
        gridBagConstraints1.weightx = 1.0;
        jPanel2.add (bottomField, gridBagConstraints1);

        rightLabel.setLabelFor (rightField);
        rightLabel.setText (bundle.getString( "SICE_Right" ));

        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
        jPanel2.add (rightLabel, gridBagConstraints1);

        rightField.addActionListener (new java.awt.event.ActionListener () {
                                          public void actionPerformed (java.awt.event.ActionEvent evt) {
                                              updateInsets (evt);
                                          }
                                      }
                                     );

        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.insets = new java.awt.Insets (4, 8, 4, 0);
        gridBagConstraints1.weightx = 1.0;
        jPanel2.add (rightField, gridBagConstraints1);


        add (jPanel2);

    }//GEN-END:initComponents


    private void updateInsets (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateInsets
        try {
            editor.setValue( getValue() );
        } catch (NumberFormatException e) {
            // [PENDING beep]
        }
    }//GEN-LAST:event_updateInsets


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel topLabel;
    private javax.swing.JTextField topField;
    private javax.swing.JLabel leftLabel;
    private javax.swing.JTextField leftField;
    private javax.swing.JLabel bottomLabel;
    private javax.swing.JTextField bottomField;
    private javax.swing.JLabel rightLabel;
    private javax.swing.JTextField rightField;
    // End of variables declaration//GEN-END:variables

}


/*
 * Log
 *  2    Gandalf-post-FCS1.1         3/23/00  Petr Nejedly    Added mising labels
 *  1    Gandalf-post-FCS1.0         3/10/00  Petr Nejedly    initial revision
 * $
 */
