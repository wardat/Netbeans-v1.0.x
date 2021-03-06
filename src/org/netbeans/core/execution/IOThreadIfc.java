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

package org.netbeans.core.execution;

import org.openide.windows.InputOutput;

/** Thread that implements this interface has its own redirection of
* I/O operations.
*
* @author Ales Novak
*/
interface IOThreadIfc {

    /** Getter for InputOutput to which an I/O operation is to be redirected
    *
    * @return InputOutput
    */
    InputOutput getInputOutput();
}

/*
 * Log
 *  2    Gandalf   1.1         10/22/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  1    Gandalf   1.0         10/8/99  Ales Novak      
 * $
 */
