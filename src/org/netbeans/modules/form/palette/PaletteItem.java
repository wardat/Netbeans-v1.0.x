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

package org.netbeans.modules.form.palette;

import org.openide.cookies.InstanceCookie;
import org.openide.loaders.InstanceDataObject;
import org.netbeans.modules.form.compat2.layouts.DesignLayout;
import org.netbeans.modules.form.compat2.border.DesignBorder;
import org.netbeans.modules.form.compat2.border.BorderInfo;

import java.lang.ref.WeakReference;

/** The PaletteItem encapsulate all objects that can be used as components in the form editor
*
* @author   Ian Formanek
*/
public class PaletteItem implements java.io.Serializable {
    /** generated Serialized Version UID */
    //  static final long serialVersionUID = -2098259549820241091L;

    public final static String ATTR_IS_CONTAINER = "isContainer"; // NOI18N
    // -----------------------------------------------------------------------------
    // Global class variables

    /** Weak reference to shared instance of the JavaBean */
    private WeakReference sharedReference = null;

    /** The JavaBean Class represented by this PaletteItem */
    private Class beanClass;

    private InstanceCookie instanceCookie;
    private InstanceDataObject instanceDO;

    private Boolean expliciteIsContainer;

    // -----------------------------------------------------------------------------
    // Constructors

    /** Creates a new PaletteItem */
    public PaletteItem (InstanceCookie instanceCookie) throws ClassNotFoundException, java.io.IOException {
        this.instanceCookie = instanceCookie;
        this.beanClass = instanceCookie.instanceClass ();
    }

    static final long serialVersionUID =6553170650531136255L;
    /** Creates a new PaletteItem */
    public PaletteItem (InstanceDataObject ido) throws ClassNotFoundException, java.io.IOException {
        this.beanClass = ido.instanceClass ();
        this.instanceCookie = ido;
        this.instanceDO = ido;
    }

    /** Creates a new PaletteItem for specified JavaBean class
    * @param beanClass the string name of the Java Bean's classass
    */
    public PaletteItem (String beanName) throws ClassNotFoundException {
        this (Class.forName (beanName));
    }

    /** Creates a new PaletteItem for specified JavaBean class
    * @param beanClass the Java Bean's class
    */
    public PaletteItem (Class beanClass) {
        this (beanClass, java.awt.Container.class.isAssignableFrom (beanClass));
    }

    /** Creates a new PaletteItem for specified JavaBean class
    * @param beanClass the Java Bean's class
    * @param isContainer allows to explicitly specify whether the item represents bean which can contain other beans
    */
    public PaletteItem (Class beanClass, boolean isContainer) {
        this.beanClass = beanClass;
        this.expliciteIsContainer = new Boolean (isContainer);
    }

    // -----------------------------------------------------------------------------
    // Class Methods

    public String getName () {
        String name;
        if (instanceDO != null)
            name = instanceDO.instanceName();
        else if (instanceCookie != null)
            name = instanceCookie.instanceName();
        else
            name = beanClass.getName();
        int i = name.lastIndexOf('.');
        if (i >= 0)
            name = name.substring(i+1);
        return name;
        //return org.openide.util.Utilities.getShortClassName (getItemClass ());
    }

    public String getDisplayName () {
        String name = getName ();
        if (name.endsWith ("BorderInfo")) { // NOI18N
            return name.substring (0, name.length () - 4); // remove the "Info" from BorderInfo classes // NOI18N
        } else if (name.endsWith ("Layout") && name.startsWith ("Design")) { // NOI18N
            return name.substring (6); // remove the "Design" from DesignXXXLayout classes // NOI18N
        }
        return name;
    }

    public Object getSharedInstance () throws InstantiationException, IllegalAccessException {
        Object sharedObject;
        if ((sharedReference == null) || ((sharedObject = sharedReference.get ()) == null)) {
            sharedObject = createInstance ();
            sharedReference = new WeakReference (sharedObject);
        }

        return sharedObject;
    }

    public Object createInstance () throws InstantiationException, IllegalAccessException {
        if (beanClass == null) return null;
        try {
            if (instanceDO != null) {
                return instanceDO.instanceCreate();
            }

            if (instanceCookie != null) {
                return instanceCookie.instanceCreate ();
            }

            return beanClass.newInstance ();
        }catch (ClassNotFoundException e) {
            throw new InstantiationException (e.getMessage ());
        } catch (java.io.IOException e) {
            throw new InstantiationException (e.getMessage ());
        }
    }

    public Class getItemClass () {
        return beanClass;
    }

    public java.beans.BeanInfo getBeanInfo () {
        try {
            return java.beans.Introspector.getBeanInfo (beanClass);
        } catch (java.beans.IntrospectionException e) {
            return null;
        }
    }

    public DesignBorder createBorder () throws InstantiationException, IllegalAccessException {
        return new DesignBorder ((BorderInfo)createInstance ());
    }

    public boolean isBorder () {
        return BorderInfo.class.isAssignableFrom (beanClass);
    }

    public boolean isVisual () {
        return java.awt.Component.class.isAssignableFrom (beanClass);
    }

    public boolean isDesignLayout () {
        return DesignLayout.class.isAssignableFrom (beanClass);
    }

    public boolean isContainer () {
        if (expliciteIsContainer != null) return expliciteIsContainer.booleanValue (); // explicitly set isContainer flag

        boolean isContainer = java.awt.Container.class.isAssignableFrom (beanClass);
        if (instanceDO != null) {
            Object attr = instanceDO.getPrimaryFile ().getAttribute (ATTR_IS_CONTAINER);
            if ((attr != null) && (attr.equals (Boolean.FALSE))) {
                isContainer = false;
            }
        }
        return isContainer;
    }

    public boolean isMenu () {
        return java.awt.MenuBar.class.isAssignableFrom (beanClass) ||
               java.awt.PopupMenu.class.isAssignableFrom (beanClass) ||
               javax.swing.JMenuBar.class.isAssignableFrom (beanClass) ||
               javax.swing.JPopupMenu.class.isAssignableFrom (beanClass);
    }
}

/*
 * Log
 *  17   Gandalf   1.16        3/7/00   Tran Duc Trung  fix #5791: cannot add 
 *       serialized bean to component palette
 *  16   Gandalf   1.15        1/13/00  Ian Formanek    NOI18N #2
 *  15   Gandalf   1.14        1/5/00   Ian Formanek    NOI18N
 *  14   Gandalf   1.13        11/27/99 Patrik Knakal   
 *  13   Gandalf   1.12        10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  12   Gandalf   1.11        7/23/99  Ian Formanek    isContainer property of 
 *       Palette items
 *  11   Gandalf   1.10        7/16/99  Ian Formanek    Fixed bug 1855 - 
 *       Tooltips of borders in Component Pallete print *BorderInfo probably 
 *       instead of *Border .
 *  10   Gandalf   1.9         7/8/99   Ian Formanek    PopupMenu recognized as 
 *       menu component
 *  9    Gandalf   1.8         7/3/99   Ian Formanek    isMenu added
 *  8    Gandalf   1.7         6/10/99  Ian Formanek    Fixed usage of 
 *       InstanceCookie as source for the PaletteItem
 *  7    Gandalf   1.6         6/9/99   Ian Formanek    ---- Package Change To 
 *       org.openide ----
 *  6    Gandalf   1.5         6/7/99   Ian Formanek    Better support of 
 *       instances
 *  5    Gandalf   1.4         5/20/99  Ian Formanek    Fixed multiplication of 
 *       PaletteItems
 *  4    Gandalf   1.3         5/15/99  Ian Formanek    
 *  3    Gandalf   1.2         5/14/99  Ian Formanek    
 *  2    Gandalf   1.1         5/14/99  Ian Formanek    
 *  1    Gandalf   1.0         4/26/99  Ian Formanek    
 * $
 */
