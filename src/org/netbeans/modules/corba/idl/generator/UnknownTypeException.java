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

package org.netbeans.modules.corba.idl.generator;


/*
 * @author Karel Gardas
 */

public class UnknownTypeException extends Exception {

    String type;

    static final long serialVersionUID =-3717678114065709235L;
    public UnknownTypeException (String symbol) {
        super (symbol);
        type = symbol;
    }

    public String getTypeName () {
        return type;
    }
}


/*
 * $Log
 * $
 */
