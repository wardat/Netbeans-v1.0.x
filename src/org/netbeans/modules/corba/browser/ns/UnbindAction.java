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

package org.netbeans.modules.corba.browser.ns;

import org.openide.nodes.*;
import org.openide.util.actions.*;
import org.openide.util.*;
import org.openide.*;


import org.netbeans.modules.corba.*;

/*
 * @author Karel Gardas
 */

public class UnbindAction extends NodeAction {

    static final long serialVersionUID =-612358068137975158L;
    public UnbindAction () {
        super ();
    }

    protected boolean enable (org.openide.nodes.Node[] nodes) {
        if (nodes == null || nodes.length != 1)
            return false;
        return (nodes[0].getCookie (ContextNode.class) != null);
    }

    public String getName() {
        return NbBundle.getBundle (ContextNode.class).getString ("CTL_UnbindAction");
    }

    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP; // [PENDING]
    }

    protected void performAction (final Node[] activatedNodes) {
        System.out.println ("UnbindAction.java");
        if (enable (activatedNodes)) {
            ((ContextNode) activatedNodes[0].getCookie(ContextNode.class)).unbind ();
        }
    }

}


/*
 * $Log
 * $
 */
