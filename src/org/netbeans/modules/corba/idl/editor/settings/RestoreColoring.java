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

package org.netbeans.modules.corba.idl.editor.settings;

import javax.swing.JEditorPane;

import org.openide.TopManager;
import org.openide.options.SystemOption;
import org.openide.text.PrintSettings;
import org.openide.filesystems.FileUtil;

import org.netbeans.modules.editor.options.AllOptions;
import org.netbeans.editor.Settings;

/**
 * @author Karel Gardas
 */
public class RestoreColoring {
    private static final String IDL_MIME_TYPE = "text/x-idl";

    public void restore () {
        //System.out.println ("restore ()");
        Settings.addInitializer (new IDLEditorSettings());

        // Registration of the editor kits to JEditorPane
        JEditorPane.registerEditorKitForContentType
        (IDL_MIME_TYPE,
         "org.netbeans.modules.corba.idl.editor.coloring.IDLKit",
         /* "org.netbeans.modules.corba.idl.editor.coloring.IDLKit", */
         this.getClass().getClassLoader());
        AllOptions all_options = (AllOptions)AllOptions.findObject (AllOptions.class, true);
        all_options.addOption (new IDLOptions());
        //PrintSettings print_settings = (PrintSettings)PrintSettings.findObject
        //  (PrintSettings.class, true);
        //print_settings.addOption (new IDLPrintOptions());
    }
}
/*
 * <<Log>>
 *  2    Gandalf   1.1         2/8/00   Karel Gardas    
 *  1    Gandalf   1.0         11/9/99  Karel Gardas    
 * $
 */
