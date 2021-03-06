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

package org.netbeans.beaninfo.editors;

import org.openide.util.HelpCtx;
import org.openide.explorer.propertysheet.editors.EnhancedCustomPropertyEditor;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.*;

/** A custom editor for Strings.
*
* @author  Ian Formanek
* @version 1.00, Sep 21, 1998
*/
public class StringCustomEditor extends javax.swing.JPanel implements EnhancedCustomPropertyEditor {

    private StringEditor editor;

    static final long serialVersionUID =7348579663907322425L;
    /** Initializes the Form */
    public StringCustomEditor(StringEditor ed) {
        editor = ed;
        String s = (String) editor.getValue ();
        if (s == null) s = ""; // NOI18N
        initComponents ();
        textArea.setText (s);
        setBorder (new javax.swing.border.EmptyBorder (new java.awt.Insets(8, 8, 8, 8)));
        setPreferredSize (new java.awt.Dimension(500, 300));
        HelpCtx.setHelpIDString (this, StringCustomEditor.class.getName ());
    }

    /**
    * @return Returns the property value that is result of the CustomPropertyEditor.
    * @exception InvalidStateException when the custom property editor does not represent valid property value
    *            (and thus it should not be set)
    */
    public Object getPropertyValue () throws IllegalStateException {
        return textArea.getText ();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        setLayout (new java.awt.BorderLayout ());

        textAreaScroll = new javax.swing.JScrollPane ();

        textArea = new javax.swing.JTextArea ();

        textAreaScroll.setViewportView (textArea);


        add (textAreaScroll, "Center"); // NOI18N

    }//GEN-END:initComponents



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane textAreaScroll;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables

}

/*
 * Log
 *  11   Gandalf   1.10        1/13/00  Petr Jiricka    i18n
 *  10   Gandalf   1.9         10/22/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  9    Gandalf   1.8         8/18/99  Ian Formanek    Fixed bug 2322 - Some PE
 *       couldn't be initialized - en exception is issued
 *  8    Gandalf   1.7         8/9/99   Ian Formanek    Generated Serial Version
 *       UID
 *  7    Gandalf   1.6         7/15/99  Ian Formanek    Fixed preferred size of 
 *       the custom property editor
 *  6    Gandalf   1.5         7/8/99   Jesse Glick     Context help.
 *  5    Gandalf   1.4         6/30/99  Ian Formanek    Reflecting changes in 
 *       editors packages and enhanced property editor interfaces
 *  4    Gandalf   1.3         6/8/99   Ian Formanek    ---- Package Change To 
 *       org.openide ----
 *  3    Gandalf   1.2         5/31/99  Ian Formanek    Updated to X2 format
 *  2    Gandalf   1.1         4/3/99   Ian Formanek    Fixed bug 1320 - 
 *       Connecion wizard does not work. No wizard window is opened.
 *  1    Gandalf   1.0         1/5/99   Ian Formanek    
 * $
 */
