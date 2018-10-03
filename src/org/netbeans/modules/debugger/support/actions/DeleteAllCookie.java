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

package org.netbeans.modules.debugger.support.actions;

import org.openide.nodes.Node.Cookie;

/**
* Support for Delete All action.
* 
* @author Jan Jancura
*/
public interface DeleteAllCookie extends Cookie {
    /** generated Serialized Version UID */
    static final long serialVersionUID = -7764859612385032684L;

    /**
    * Delete all breakpoints.
    */
    public void deleteAll ();
}

/*
* Log
*/

