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

package org.netbeans.modules.db.explorer.nodes;

import java.sql.*;
import java.util.*;
import org.netbeans.lib.ddl.*;
import org.netbeans.lib.ddl.impl.*;
import org.openide.nodes.*;
import org.openide.NotifyDescriptor;
import org.openide.TopManager;
import org.openide.options.SystemOption;
import org.netbeans.modules.db.*;
import org.netbeans.modules.db.explorer.*;
import org.netbeans.modules.db.explorer.infos.*;

/** Abstract class that can be used as super class of all data objects that
* should contain some nodes. It provides methods for adding/removing
* sub nodes.
*
* @author Miloslav Metelka, Slavek Psenicka
* @version 0.10, Jul 14, 1998
*/
public class RootNode extends DatabaseNode
{
    /** Stores DBNode's connections info */
    private static DatabaseOption option = null;

    /** DDLFactory */
    SpecificationFactory sfactory;

    public static DatabaseOption getOption()
    {
        if (option == null) {
            option = (DatabaseOption)SystemOption.findObject(DatabaseOption.class, false);
        }

        return option;
    }

    public RootNode()
    {
        try {
            sfactory = new SpecificationFactory();
            DatabaseNodeInfo nfo = DatabaseNodeInfo.createNodeInfo(null, "root");
            if (sfactory != null) nfo.setSpecificationFactory(sfactory);
            else throw new Exception("no specification factory");

            setInfo(nfo);
            getInfo().setNode(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean canRename()
    {
        return true;
    }
}

/*
 * <<Log>>
 */
