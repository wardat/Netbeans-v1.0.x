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
import javax.swing.JButton;
import java.util.ResourceBundle;

import org.openide.DialogDescriptor;
import org.openide.NotifyDescriptor;
import org.openide.TopManager;
import org.openide.util.NbBundle;
import org.openide.util.HelpCtx;

/** Used to reject certificates in Result dialog
 * @author  Petr Hrebejk
 */
public class RejectDialog extends javax.swing.JPanel {

    static final int FOR_MODULE = 0;
    static final int FOR_NOW = 1;
    static final int FOREVER = 2;
    static final int CANCEL = 3;

    /** The resource bundle */
    private static final ResourceBundle bundle = NbBundle.getBundle( LicenceDialog.class );
    /** Preferred size of this dialog */
    private static final java.awt.Dimension preferredSize = new java.awt.Dimension( 620, 475 );

    /** The only Licence panel instance in system */
    private static AutoCheckInfo dialogPanel;
    /** The dialog descriptor of licence dialog */
    private static DialogDescriptor dialogDescriptor = null;
    /** The Licence dialog */
    private static java.awt.Dialog dialog = null;
    /** Licence dialog Accept button */

    private Settings settings;

    /** Result of the action */
    private static int result = FOR_MODULE;

    /** prefferd width of dialog */
    private int prefX = 0;

    private java.awt.Dimension prefSize = null;

    /** Creates new form LicencePanel */
    public RejectDialog(String message,int messageType) {
        initComponents ();


        messageText.setText( message );
        iconLabel.setIcon( getIconForType( messageType ) );

    }

    /** Overload getPreffered size to get a bit bigger dialog */
    /*
    public java.awt.Dimension getPreferredSize() {
      if ( prefX == 0 ) {
        return super.getPreferredSize();
      }
      
      //if ( prefSize == null ) 
      prefSize = super.getPreferredSize();
      prefSize.width = java.lang.Math.max( prefSize.width, prefX );
      
      return prefSize;
}
    */

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        messagePanel = new javax.swing.JPanel ();
        iconLabel = new javax.swing.JLabel ();
        messageText = new javax.swing.JTextArea ();
        setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints1;
        setBorder (new javax.swing.border.EmptyBorder(new java.awt.Insets(8, 8, 8, 8)));

        messagePanel.setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints2;


        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints2.weighty = 1.0;
        messagePanel.add (iconLabel, gridBagConstraints2);

        messageText.setOpaque (false);
        messageText.setEditable (false);
        messageText.setFont (new java.awt.Font ("Dialog", 0, 12)); // NOI18N

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridwidth = 0;
        gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints2.insets = new java.awt.Insets (0, 18, 0, 0);
        gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints2.weightx = 1.0;
        gridBagConstraints2.weighty = 1.0;
        messagePanel.add (messageText, gridBagConstraints2);


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.weightx = 1.0;
        gridBagConstraints1.weighty = 1.0;
        add (messagePanel, gridBagConstraints1);

    }//GEN-END:initComponents

    private void periodComboBoxActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_periodComboBoxActionPerformed

    }//GEN-LAST:event_periodComboBoxActionPerformed

    private void negativCheckBoxActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_negativCheckBoxActionPerformed

    }//GEN-LAST:event_negativCheckBoxActionPerformed

    private void beforeCheckBoxActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beforeCheckBoxActionPerformed

    }//GEN-LAST:event_beforeCheckBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel messagePanel;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JTextArea messageText;
    // End of variables declaration//GEN-END:variables

    static int showDialog( String message, int messageType ) {
        //if ( dialog == null ) {
        DialogDescriptor dd = createDialog( message, messageType );
        //}

        //dialog.invalidate();
        //dialog.pack();

        Dialog dialog = TopManager.getDefault().createDialog( dd );
        dialog.show();

        return result;
    }

    private static DialogDescriptor createDialog( String message, int messageType ) {

        final JButton moduleButton = new JButton( bundle.getString( "CTL_Certs_RejectForModule" ) );
        final JButton forNowButton = new JButton( bundle.getString( "CTL_Certs_RejectForNow" ) );
        final JButton foreverButton = new JButton( bundle.getString( "CTL_Certs_RejectForever" ) );
        final JButton cancelButton = new JButton( bundle.getString( "CTL_Certs_Cancel" ) );

        moduleButton.setToolTipText( bundle.getString( "CTL_Certs_RejectForModule_ToolTip" ) );
        forNowButton.setToolTipText( bundle.getString( "CTL_Certs_RejectForNow_ToolTip" ) );
        foreverButton.setToolTipText( bundle.getString( "CTL_Certs_RejectForever_ToolTip" ) );
        cancelButton.setToolTipText( bundle.getString( "CTL_Certs_Cancel_ToolTip" ) );

        DialogDescriptor dd;

        dd = new DialogDescriptor(
                 new RejectDialog( message, messageType ),
                 bundle.getString( "CTL_reject.MessageTitle" ),
                 true,                                               // Modal
                 new Object [] {
                     moduleButton,
                     forNowButton,
                     foreverButton,
                     cancelButton },                                   // Option list
                 moduleButton,                                       // Default
                 DialogDescriptor.BOTTOM_ALIGN,                      // Align
                 new HelpCtx ( AutoCheckInfo.class ),                // Help
                 new java.awt.event.ActionListener() {
                     public void actionPerformed( java.awt.event.ActionEvent e ) {

                         if ( e.getSource() == moduleButton ) {
                             result = FOR_MODULE;
                         }
                         else if ( e.getSource() == forNowButton ) {
                             result = FOR_NOW;
                         }
                         else if ( e.getSource() == foreverButton ) {
                             result = FOREVER;
                         }
                         else {
                             result = CANCEL;
                         }

                     }
                 } );

        dd.setClosingOptions( null );

        return dd;
    }


    /** Gets the right icon
     */
    private static  javax.swing.Icon getIconForType(int messageType) {
        if(messageType < 0 || messageType > 3)
            return null;
        switch(messageType) {
        case 0:
            return javax.swing.UIManager.getIcon("OptionPane.errorIcon"); // NOI18N
        case 1:
            return javax.swing.UIManager.getIcon("OptionPane.informationIcon"); // NOI18N
        case 2:
            return javax.swing.UIManager.getIcon("OptionPane.warningIcon"); // NOI18N
        case 3:
            return javax.swing.UIManager.getIcon("OptionPane.questionIcon"); // NOI18N
        }
        return null;
    }


    /** Moves specified window to the center of the screen
    */
    /*
    public static void centerWindow (Window w) {
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dialogSize = w.getSize();
      w.setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
}
    */

}
/*
 * Log
 *  2    Gandalf   1.1         1/12/00  Petr Hrebejk    i18n
 *  1    Gandalf   1.0         12/20/99 Petr Hrebejk    
 * $
 */
