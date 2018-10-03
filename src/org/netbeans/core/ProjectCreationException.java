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

package org.netbeans.core;

/** Thrown during creation of the project
*
* @author Ales Novak
* @version 0.10 Apr 03, 1998
*/
class ProjectCreationException extends RuntimeException {
    /** generated Serialized Version UID */
    static final long serialVersionUID = 8486498846293568023L;
    /**
    * @param msg is a message
    */
    ProjectCreationException (String msg) {
        super(msg);
    }
}

/*
 * Log
 *  2    Gandalf   1.1         10/22/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  1    Gandalf   1.0         1/5/99   Ian Formanek    
 * $
 */
