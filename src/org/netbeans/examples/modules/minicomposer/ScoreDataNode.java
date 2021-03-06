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

package org.netbeans.examples.modules.minicomposer;
import javax.swing.event.*;
import org.openide.actions.OpenAction;
import org.openide.loaders.*;
import org.openide.nodes.*;
import org.openide.util.*;
import org.openide.util.actions.SystemAction;
public class ScoreDataNode extends DataNode implements ChangeListener {
    public ScoreDataNode (ScoreDataObject obj) {
        super (obj, Children.LEAF);
        updateValidity ();
        ScoreCookie cookie = (ScoreCookie) getCookie (ScoreCookie.class);
        if (cookie != null)
            cookie.addChangeListener (WeakListener.change (this, cookie));
    }
    public SystemAction getDefaultAction () {
        return SystemAction.get (OpenAction.class);
    }
    protected Sheet createSheet () {
        Sheet sheet = super.createSheet ();
        ExecSupport support = (ExecSupport) getCookie (ExecSupport.class);
        Sheet.Set set = new Sheet.Set ();
        set.setName ("execution");
        set.setDisplayName (NbBundle.getBundle (ScoreDataNode.class).getString ("LBL_Execution"));
        set.setShortDescription (NbBundle.getBundle (ScoreDataNode.class).getString ("HINT_Execution"));
        support.addProperties (set);
        set.remove (ExecSupport.PROP_DEBUGGER_TYPE);
        set.remove (ExecSupport.PROP_FILE_PARAMS);
        sheet.put (set);
        return sheet;
    }
    private void updateValidity () {
        ScoreCookie cookie = (ScoreCookie) getCookie (ScoreCookie.class);
        if (cookie != null && ! cookie.isValid ()) {
            setIconBase ("/org/netbeans/examples/modules/minicomposer/ScoreDataIconError");
        } else {
            setIconBase ("/org/netbeans/examples/modules/minicomposer/ScoreDataIcon");
        }
    }
    public void stateChanged (ChangeEvent ev) {
        updateValidity ();
    }
}
