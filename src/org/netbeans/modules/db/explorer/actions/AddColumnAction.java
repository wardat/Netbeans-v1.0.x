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

package org.netbeans.modules.db.explorer.actions;

import java.sql.Connection;
import org.openide.*;
import org.openide.nodes.*;
import org.netbeans.lib.ddl.impl.Specification;
import org.netbeans.modules.db.explorer.nodes.*;
import org.netbeans.modules.db.explorer.infos.*;
import org.netbeans.modules.db.explorer.dlg.AddTableColumnDialog;

public class AddColumnAction extends DatabaseAction
{
    static final long serialVersionUID =5894518352294344657L;
    public void performAction (Node[] activatedNodes)
    {
        Node node;
        if (activatedNodes != null && activatedNodes.length>0) node = activatedNodes[0];
        else return;

        try {

            DatabaseNodeInfo info = (DatabaseNodeInfo)node.getCookie(DatabaseNodeInfo.class);
            TableNodeInfo nfo = (TableNodeInfo)info.getParent(nodename);
            AddTableColumnDialog dlg = new AddTableColumnDialog((Specification)nfo.getSpecification(), nfo);
            if (dlg.run()) nfo.addColumn(dlg.getColumnName());

        } catch(Exception e) {
            TopManager.getDefault().notify(new NotifyDescriptor.Message("Unable to add column, "+e.getMessage(), NotifyDescriptor.ERROR_MESSAGE));
        }
    }
}
/*
 * <<Log>>
 *  7    Gandalf   1.6         11/27/99 Patrik Knakal   
 *  6    Gandalf   1.5         10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  5    Gandalf   1.4         6/9/99   Ian Formanek    ---- Package Change To 
 *       org.openide ----
 *  4    Gandalf   1.3         5/21/99  Slavek Psenicka new version
 *  3    Gandalf   1.2         5/14/99  Slavek Psenicka new version
 *  2    Gandalf   1.1         4/23/99  Slavek Psenicka oprava activatedNode[0] 
 *       check
 *  1    Gandalf   1.0         4/23/99  Slavek Psenicka 
 * $
 */
