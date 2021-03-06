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

package org.netbeans.core.actions;

import org.openide.TopManager;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.ActionPerformer;
import org.openide.util.actions.CallableSystemAction;

import org.netbeans.core.IDESettings;

/** Opens a HTML Browser on the home URL specified in IDESettings.
* (Or activates last opened).
*
* @author Ian Formanek
*/
public class HTMLViewAction extends CallableSystemAction {
    /** generated Serialized Version UID */
    static final long serialVersionUID = 281181711813174400L;

    /** Icon resource.
    * @return name of resource for icon
    */
    protected String iconResource () {
        return "/org/netbeans/core/resources/actions/htmlView.gif"; // NOI18N
    }

    public void performAction() {
        TopManager tm = TopManager.getDefault();
        tm.setStatusText (NbBundle.getBundle(HTMLViewAction.class).
            getString("CTL_OpeningBrowser")
        );
        try {
            tm.showUrl (new java.net.URL (
                org.openide.awt.HtmlBrowser.getHomePage ()
            ));
        } catch (java.net.MalformedURLException e) {
            if (!org.openide.awt.HtmlBrowser.getHomePage ().
              startsWith ("http://")
            ) {
                try {
                    tm.showUrl (new java.net.URL (
                        "http://" + org.openide.awt.HtmlBrowser.getHomePage ()
                    ));
                } catch (java.net.MalformedURLException e1) {
                    tm.showUrl (IDESettings.getRealHomeURL ());
                }
            } else
                tm.showUrl (IDESettings.getRealHomeURL ());
        }
        tm.setStatusText (""); // NOI18N
    }

    public String getName() {
        return NbBundle.getBundle(HTMLViewAction.class).getString("HTMLView");
    }

    /** @return the action's help context */
    public HelpCtx getHelpCtx() {
        return new org.openide.util.HelpCtx (HTMLViewAction.class);
    }

}

/*
 * Log
 *  14   Gandalf   1.13        1/12/00  Ales Novak      i18n
 *  13   Gandalf   1.12        1/5/00   Jan Jancura     Bug 4872
 *  12   Gandalf   1.11        10/22/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  11   Gandalf   1.10        6/24/99  Jesse Glick     Gosh-honest HelpID's.
 *  10   Gandalf   1.9         6/22/99  Ian Formanek    employed DEFAULT_HELP
 *  9    Gandalf   1.8         6/8/99   Ian Formanek    ---- Package Change To 
 *       org.openide ----
 *  8    Gandalf   1.7         3/26/99  Ian Formanek    Fixed use of obsoleted 
 *       NbBundle.getBundle (this)
 *  7    Gandalf   1.6         3/12/99  David Simonek   
 *  6    Gandalf   1.5         3/6/99   David Simonek   
 *  5    Gandalf   1.4         3/2/99   David Simonek   icons repair
 *  4    Gandalf   1.3         3/1/99   David Simonek   icons etc..
 *  3    Gandalf   1.2         1/21/99  David Simonek   Removed references to 
 *       "Actions" class
 *  2    Gandalf   1.1         1/7/99   Ian Formanek    fixed resource names
 *  1    Gandalf   1.0         1/5/99   Ian Formanek    
 * $
 */
