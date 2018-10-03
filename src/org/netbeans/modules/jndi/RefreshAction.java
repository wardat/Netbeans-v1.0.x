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

import org.openide.nodes.Node;
import org.openide.nodes.FilterNode;
import org.openide.util.HelpCtx;
import org.netbeans.modules.jndi.utils.Refreshable;

/** Is able to refresh selected JndiNode.
*
* @author Ales Novak
*/
public final class RefreshAction extends org.openide.util.actions.NodeAction {

    /** No arg constructor. */
    public RefreshAction() {
        super ();
    }

    /** Performs refresh.
    *
    * @param nodes an array of selected nodes
    */
    protected void performAction(Node[] nodes) {

        if (enable(nodes)) {
            ((Refreshable) nodes[0].getCookie(Refreshable.class)).refresh();
        }
    }

    /** Should be the action enabled?
    *
    * @param nodes an array of selected nodes
    * @return <tt>true</tt> iff the array has length 1 and contains a JndiNode
    */
    protected boolean enable(Node[] nodes) {

        if ((nodes == null) ||
                (nodes.length != 1)) {
            return false;
        }

        return (nodes[0].getCookie(Refreshable.class) != null);
    }

    /** @return name of the action */
    public String getName() {
        return JndiRootNode.getLocalizedString("CTL_RefreshName");
    }

    /** @return help */
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }
}
