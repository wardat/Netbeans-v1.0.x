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

package org.openidex.search;

import org.openide.nodes.Node;
import org.openide.util.Task;

/** A task representing one search that provides
* operations to listen to progress of the search,
* to cancel it, to be informed when the task is finished.
*
* @author Jaroslav Tulach
*/

public abstract class SearchTask extends Task {
    /** Creates new SearchTask.
    */
    protected SearchTask(Runnable run) {
        super (run);
    }

    /** Cancels the task.
    */
    public abstract void stop ();

    /** Obtains all result nodes produced by the search.
    * That has been accepted by the provided acceptor.
    * This is blocking call and stops until the search is finished
    * or canceled. 
    *
    * @return array of resulted nodes
    */
    public abstract Node[] getResult ();
}

/*
* Log
*  3    Gandalf-post-FCS1.1.1.0     4/4/00   Petr Kuzel      unknown state
*  2    Gandalf   1.1         12/15/99 Martin Balin    Fixed package statement
*  1    Gandalf   1.0         12/14/99 Petr Kuzel      
* $ 
*/ 
