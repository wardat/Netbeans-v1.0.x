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

package org.openide.explorer.propertysheet.editors;

/** An interface for PropertyEditor that wishes to use StringArrayCustomEditor as
* its custom editor.  The StringArrayCustomEditor expects this interface as a parameter
* in its constructor and acquires and modifies the property via these 2 methods of this interface.
*
* @author   Ian Formanek
* @version  1.00, Sep 21, 1998
*/
public interface StringArrayCustomizable {
    /** Used to acquire the current value from the PropertyEditor
    * @return the current value of the property
    */
    public String[] getStringArray ();

    /** Used to modify the current value in the PropertyEditor
    * @param value the new value of the property
    */
    public void setStringArray (String[] value);
}


/*
 * Log
 *  3    Gandalf   1.2         10/22/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  2    Gandalf   1.1         6/30/99  Ian Formanek    Moved to package 
 *       org.openide.explorer.propertysheet.editors
 *  1    Gandalf   1.0         1/5/99   Ian Formanek    
 * $
 */
