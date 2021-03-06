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

import java.beans.*;
import java.util.*;
import javax.swing.event.*;

import org.openide.actions.ToolsAction;
import org.openide.modules.ManifestSection;
import org.openide.util.actions.SystemAction;

/** Holds list of all actions added by modules.
*
* @author jtulach
*/
class ModuleActions extends Object implements ToolsAction.Model, PropertyChangeListener {
    /** array of all actions added by modules */
    private static SystemAction[] array;
    /** of (ModuleItem, List (SystemAction)) 
     * @associates List*/
    private static HashMap map = new HashMap (7);
    /** current module */
    private static Object module;

    /** listeners */
    private static EventListenerList listeners = new EventListenerList ();

    /** instance */
    private static ModuleActions INSTANCE = new ModuleActions ();

    static {
        module = INSTANCE;
    }

    /** Initializes the model.
    */
    public static void initialize () {
        ToolsAction.setModel (INSTANCE);
    }

    /** Array with all activated actions.
    * Can contain null that will be replaced by separators.
    */
    public SystemAction[] getActions () {
        SystemAction[] a = array;
        if (a != null) {
            return a;
        }
        array = a = createActions ();
        return a;
    }

    /** Adds change listener to listen on changes of actions
    */
    public void addChangeListener (ChangeListener l) {
        listeners.add (ChangeListener.class, l);
    }

    /** Removes change listener to listen on changes of actions
    */
    public void removeChangeListener (javax.swing.event.ChangeListener l) {
        listeners.remove (ChangeListener.class, l);
    }

    /** Listens on change of modules and if changed,
    * fires change to all listeners.
    */
    private static void fireChange () {
        Object[] obj = listeners.getListenerList ();
        if (obj.length == 0) return;

        ChangeEvent ev = new ChangeEvent (INSTANCE);
        for (int i = obj.length - 1; i >= 0; i -= 2) {
            ChangeListener l = (ChangeListener)obj[i];
            l.stateChanged (ev);
        }
    }

    /** Change enabled property of an action
    */
    public void propertyChange (PropertyChangeEvent ev) {
        if (SystemAction.PROP_ENABLED.equals (ev.getPropertyName ())) {
            fireChange ();
        }
    }

    /** Attaches to processing of a module
    */
    public static synchronized void attachTo (ModuleItem mi) {
        module = mi;
        if (module == null) {
            // well known value
            module = INSTANCE;
        }
    }

    /** Adds new action to the list.
    */
    public synchronized static void add (ManifestSection.ActionSection as) throws InstantiationException {
        List list = (List)map.get (module);
        if (list == null) {
            list = new LinkedList ();
            map.put (module, list);
        }
        list.add (as.getAction ());
        as.getAction ().addPropertyChangeListener (INSTANCE);

        array = null;
        fireChange (); // PENDING this is too often
    }

    /** Removes new action from the list.
    */
    public synchronized static void remove (ManifestSection.ActionSection as) throws InstantiationException {
        List list = (List)map.get (module);
        if (list == null) {
            return;
        }
        list.remove (as.getAction ());
        as.getAction ().removePropertyChangeListener (INSTANCE);

        if (list.isEmpty ()) {
            map.remove (module);
        }

        array = null;
        fireChange (); // PENDING this is too often
    }

    /** Creates the actions.
    */
    private synchronized static SystemAction[] createActions () {
        java.util.Iterator it = map.values ().iterator ();

        LinkedList arr = new LinkedList ();

        while (it.hasNext ()) {
            List l = (List)it.next ();

            arr.addAll (l);

            if (it.hasNext ()) {
                // add separator between modules
                arr.add (null);
            }

        }

        return (SystemAction[])arr.toArray (new SystemAction[arr.size ()]);
    }
}

/*
* Log
*  7    Gandalf   1.6         10/22/99 Ian Formanek    NO SEMANTIC CHANGE - Sun 
*       Microsystems Copyright in File Comment
*  6    Gandalf   1.5         9/2/99   Jaroslav Tulach #3637
*  5    Gandalf   1.4         8/5/99   Jaroslav Tulach Tools & New action in 
*       editor.
*  4    Gandalf   1.3         7/28/99  Jaroslav Tulach Additional manifest & 
*       separation of actions by modules
*  3    Gandalf   1.2         6/8/99   Ian Formanek    ---- Package Change To 
*       org.openide ----
*  2    Gandalf   1.1         5/13/99  Jaroslav Tulach Services changed to 
*       tools.
*  1    Gandalf   1.0         5/13/99  Jaroslav Tulach 
* $
*/