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

package org.netbeans.beaninfo.awt;

import java.awt.Image;
import java.beans.*;

/** Base class for awt components - toolbars, checkboxes...
*
* @author Ales Novak
* @version 0.10, August 04, 1998
* @see sun.java.beans.infos.... or
*/
public abstract class ComponentBeanInfo extends SimpleBeanInfo {

    /** Array of property descriptors. */
    private static PropertyDescriptor[] desc;

    // initialization of the array of descriptors
    static {
        try {
            desc = new PropertyDescriptor[] {
                       new PropertyDescriptor("background", java.awt.Component.class, "getBackground", "setBackground"), // 0 // NOI18N
                       new PropertyDescriptor("foreground", java.awt.Component.class, "getForeground", "setForeground"), //1 // NOI18N
                       new PropertyDescriptor("enabled", java.awt.Component.class, "isEnabled", "setEnabled"), //2 // NOI18N
                       new PropertyDescriptor("name", java.awt.Component.class), //3 // NOI18N
                       new PropertyDescriptor("visible", java.awt.Component.class), //4 // NOI18N
                       new PropertyDescriptor("font", java.awt.Component.class) // NOI18N
                   };
        } catch (IntrospectionException ex) {
            throw new InternalError(LabelBeanInfo.getString("EXC_PropInit"));
        }
    }

    /** no-arg */
    ComponentBeanInfo() {
    }

    /** @return Propertydescriptors */
    public PropertyDescriptor[] getPropertyDescriptors() {
        return desc;
    }
}

/*
 * Log
 */
