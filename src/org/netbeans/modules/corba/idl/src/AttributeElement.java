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

package org.netbeans.modules.corba.idl.src;

import java.util.Vector;

public class AttributeElement extends IDLElement {

    private boolean readonly;
    private IDLType type;

    /**
     * @associates SimpleDeclarator 
     */
    private Vector other;

    public AttributeElement(int id) {
        super(id);
        readonly = false;
        other = new Vector ();
    }

    public AttributeElement(IDLParser p, int id) {
        super(p, id);
    }

    public void setReadOnly (boolean v) {
        readonly = v;
    }

    public boolean getReadOnly () {
        return readonly;
    }

    public void setType (IDLType t) {
        type = t;
    }

    public IDLType getType () {
        return type;
    }

    public Vector getOther () {
        return other;
    }

    public void setOther (Vector o) {
        other = o;
    }

    public void addOther (SimpleDeclarator o) {
        other.addElement (o);
    }

}


