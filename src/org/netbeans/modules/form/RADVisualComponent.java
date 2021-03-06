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

package org.netbeans.modules.form;

import org.openide.nodes.*;
import org.netbeans.modules.form.compat2.layouts.DesignLayout;

import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;

/**
*
* @author Ian Formanek
*/
public class RADVisualComponent extends RADComponent {

    // -----------------------------------------------------------------------------
    // Private properties

    private HashMap constraints = new HashMap (10);
    transient private Node.PropertySet[] visualPropertySet;
    transient private RADVisualContainer parent;

    // -----------------------------------------------------------------------------
    // Initialization

    void initParent (RADVisualContainer parent) {
        this.parent = parent;
    }

    // -----------------------------------------------------------------------------
    // Public interface

    /** @return The JavaBean visual component represented by this RADVisualComponent */
    public Component getComponent () {
        return (Component)getBeanInstance ();
    }

    public RADVisualContainer getParentContainer () {
        return parent;
    }

    /** @return The index of this component within all the subcomponents of its parent */
    public int getComponentIndex () {
        return getParentContainer ().getIndexOf (this);
    }

    // -----------------------------------------------------------------------------
    // Constraints management

    void initConstraints (HashMap map) {
        for (java.util.Iterator it = map.keySet ().iterator (); it.hasNext ();) {
            String layoutClassName = (String) it.next ();
            constraints.put (layoutClassName, map.get (layoutClassName));
        }
    }

    public void setConstraints (Class layoutClass, DesignLayout.ConstraintsDescription constr) {
        constraints.put (layoutClass.getName(), constr);
    }

    public DesignLayout.ConstraintsDescription getConstraints (Class layoutClass) {
        return (DesignLayout.ConstraintsDescription)constraints.get (layoutClass.getName ());
    }

    public Node.PropertySet[] getProperties () {
        if (parent == null) {
            // [PENDING] strange - not initialized yet - it is probably a bad state and this code should be removed
            return super.getProperties ();
        }

        if (visualPropertySet == null) {
            Node.PropertySet[] inh = super.getProperties ();
            visualPropertySet = new Node.PropertySet[inh.length+1];
            System.arraycopy (inh, 0, visualPropertySet, 0, inh.length-1);
            visualPropertySet[visualPropertySet.length-2] =
                new Node.PropertySet ("layout", FormEditor.getFormBundle().getString ("MSG_Layout"), FormEditor.getFormBundle().getString ("MSG_LayoutProps")) {
                    public Node.Property[] getProperties () {
                        return parent.getDesignLayout ().getComponentProperties (RADVisualComponent.this);
                    }
                };
            visualPropertySet[visualPropertySet.length-1] = inh[inh.length-1]; // add events tab to the end
        }
        return visualPropertySet;
    }


    HashMap getConstraintsMap () {
        return constraints;
    }

    // -----------------------------------------------------------------------------
    // Debug methods

    public String toString () {
        String ret = super.toString () + ", constraints: ---------------\n"; // NOI18N
        for (Iterator it = constraints.keySet ().iterator (); it.hasNext (); ) {
            Object key = it.next ();
            ret = ret + "class: "+ key + ", constraints: "+constraints.get (key) + "\n"; // NOI18N
        }
        return ret + "---------------------------"; // NOI18N
    }

}

/*
 * Log
 *  15   Gandalf   1.14        1/12/00  Pavel Buzek     I18N
 *  14   Gandalf   1.13        1/5/00   Ian Formanek    NOI18N
 *  13   Gandalf   1.12        10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  12   Gandalf   1.11        7/5/99   Ian Formanek    Fixed last change
 *  11   Gandalf   1.10        7/5/99   Ian Formanek    getComponentInstance->getBeanInstance,
 *        getComponentClass->getBeanClass
 *  10   Gandalf   1.9         6/9/99   Ian Formanek    ---- Package Change To 
 *       org.openide ----
 *  9    Gandalf   1.8         5/26/99  Ian Formanek    toString
 *  8    Gandalf   1.7         5/15/99  Ian Formanek    
 *  7    Gandalf   1.6         5/15/99  Ian Formanek    
 *  6    Gandalf   1.5         5/14/99  Ian Formanek    
 *  5    Gandalf   1.4         5/12/99  Ian Formanek    
 *  4    Gandalf   1.3         5/11/99  Ian Formanek    Build 318 version
 *  3    Gandalf   1.2         5/10/99  Ian Formanek    
 *  2    Gandalf   1.1         5/4/99   Ian Formanek    Package change
 *  1    Gandalf   1.0         4/26/99  Ian Formanek    
 * $
 */
