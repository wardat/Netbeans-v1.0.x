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

package org.netbeans.modules.clazz;

import java.awt.Image;
import java.awt.Toolkit;
import java.lang.reflect.*;
import java.util.Vector;
import java.util.ResourceBundle;
import java.io.*;
import javax.swing.SwingUtilities;

import org.openide.nodes.*;
import org.openide.TopManager;
import org.openide.loaders.DataNode;
import org.openide.util.datatransfer.ExTransferable;
import org.openide.util.RequestProcessor;
import org.openide.util.NbBundle;
import org.openide.src.*;
import org.openide.src.nodes.SourceChildren;
import org.openide.src.nodes.DefaultFactory;
import org.openide.cookies.SourceCookie;
import org.openide.loaders.ExecSupport;

/** Represents ClassDataObject
*
* @author Ales Novak, Ian Formanek, Jan Jancura, Dafe Simonek
*/
class ClassDataNode extends DataNode implements Runnable {
    /** generated Serialized Version UID */
    static final long serialVersionUID = -1543899241509520203L;

    /** Properties */
    private final static String PROP_CLASS_NAME = "className"; // NOI18N
    private final static String PROP_IS_JAVA_BEAN = "isJavaBean"; // NOI18N
    private final static String PROP_IS_APPLET = "isApplet"; // NOI18N
    private final static String PROP_IS_EXECUTABLE = "isExecutable"; // NOI18N
    private final static String PROP_FILE_PARAMS = "fileParams"; // NOI18N
    private final static String PROP_EXECUTION = "execution"; // NOI18N

    private final static String EXECUTION_SET_NAME     = "Execution"; // NOI18N

    /** Icon bases for icon manager */
    private final static String CLASS_BASE =
        "/org/netbeans/modules/clazz/resources/class"; // NOI18N
    private final static String CLASS_MAIN_BASE =
        "/org/netbeans/modules/clazz/resources/classMain"; // NOI18N
    private final static String ERROR_BASE =
        "/org/netbeans/modules/clazz/resources/classError"; // NOI18N
    private final static String BEAN_BASE =
        "/org/netbeans/modules/clazz/resources/bean"; // NOI18N
    private final static String BEAN_MAIN_BASE =
        "/org/netbeans/modules/clazz/resources/beanMain"; // NOI18N

    /** a flag whether the children of this object are only items declared
    * by this class, or all items (incl. inherited)
    */
    private boolean showDeclaredOnly = true;  // [PENDING - get default value from somewhere ?]

    /** ClassDataObject that is represented */
    protected ClassDataObject obj;

    /** The flag indicating whether right icon has been already found */
    transient boolean iconResolved = false;

    // -----------------------------------------------------------------------
    // constructor

    /** @param obj is a ClassDataObject that is to be represented
    */
    ClassDataNode(final ClassDataObject obj) {
        /* Changed for multiple factories
        super(obj, new SourceChildren(ClassElementNodeFactory.getInstance()));
        */
        super(obj, new SourceChildren( ClassDataObject.getExplorerFactory()) );
        this.obj = obj;
        initialize();
    }

    /** Returns icon base string which should be used for
    * icon inicialization. Subclasses can ovveride this method
    * to provide their own icon base string.
    */
    protected String initialIconBase () {
        return CLASS_BASE;
    }

    private void initialize () {
        setIconBase(initialIconBase());
        // icons...
        RequestProcessor.postRequest(this, 200);
    }

    public void setParams (final String params) throws IOException {
        ((ClassDataObject) getDataObject()).setParams(params);
    }

    public String getParams () {
        return ((ClassDataObject) getDataObject()).getParams();
    }

    void setExecution (boolean i) throws IOException {
        ((ClassDataObject) getDataObject()).setExecution (i);
    }

    boolean getExecution () {
        return ((ClassDataObject) getDataObject()).getExecution ();
    }

    /** Creates property set for this node */
    protected Sheet createSheet () {
        Sheet s = super.createSheet();
        ResourceBundle bundle = NbBundle.getBundle(ClassDataNode.class);
        Sheet.Set ps = s.get(Sheet.PROPERTIES);
        ps.put(new PropertySupport.ReadOnly (
                   PROP_CLASS_NAME,
                   String.class,
                   bundle.getString("PROP_className"),
                   bundle.getString("HINT_className")
               ) {
                   public Object getValue () throws InvocationTargetException {
                       return obj.getClassName();
                   }
               });
        ps.put(new PropertySupport.ReadOnly (
                   ElementProperties.PROP_MODIFIERS,
                   String.class,
                   bundle.getString ("PROP_modifiers"),
                   bundle.getString ("HINT_modifiers")
               ) {
                   public Object getValue () throws InvocationTargetException {
                       Object result = null;
                       try {
                           result = obj.getModifiers();
                       } catch (IOException ex) {
                           // ignore - return null
                       } catch (ClassNotFoundException ex) {
                           // ignore - return null
                       }
                       return result;
                   }
               });
        ps.put(new PropertySupport.ReadOnly (
                   ElementProperties.PROP_SUPERCLASS,
                   Class.class,
                   bundle.getString ("PROP_superclass"),
                   bundle.getString ("HINT_superclass")
               ) {
                   public Object getValue () throws InvocationTargetException {
                       Object result = null;
                       try {
                           result = obj.getSuperclass();
                       } catch (IOException ex) {
                           // ignore - return null
                       } catch (ClassNotFoundException ex) {
                           // ignore - return null
                       }
                       return result;
                   }
               });
        ps.put(new PropertySupport.ReadOnly (
                   PROP_IS_EXECUTABLE,
                   Boolean.TYPE,
                   bundle.getString ("PROP_isExecutable"),
                   bundle.getString ("HINT_isExecutable")
               ) {
                   public Object getValue () throws InvocationTargetException {
                       return new Boolean(obj.isExecutable());
                   }
               });
        ps.put(new PropertySupport.ReadOnly (
                   ElementProperties.PROP_CLASS_OR_INTERFACE,
                   Boolean.TYPE,
                   bundle.getString ("PROP_isInterface"),
                   bundle.getString ("HINT_isInterface")
               ) {
                   public Object getValue () throws InvocationTargetException {
                       return new Boolean (obj.isInterface());
                   }
               });
        ps.put(new PropertySupport.ReadOnly (
                   PROP_IS_APPLET,
                   Boolean.TYPE,
                   bundle.getString ("PROP_isApplet"),
                   bundle.getString ("HINT_isApplet")
               ) {
                   public Object getValue () throws InvocationTargetException {
                       return new Boolean (obj.isApplet());
                   }
               });
        ps.put(new PropertySupport.ReadOnly (
                   PROP_IS_JAVA_BEAN,
                   Boolean.TYPE,
                   bundle.getString ("PROP_isJavaBean"),
                   bundle.getString ("HINT_isJavaBean")
               ) {
                   public Object getValue () throws InvocationTargetException {
                       return new Boolean (obj.isJavaBean());
                   }
               });
        // execution property set, if possible (not for ser objects)
        if (!(this instanceof SerDataNode)) {
            ExecSupport es = (ExecSupport)getCookie(ExecSupport.class);
            if (es != null) {
                Sheet.Set exps = new Sheet.Set();
                exps.setName(EXECUTION_SET_NAME);
                exps.setDisplayName(bundle.getString ("PROP_executionSetName"));
                exps.setShortDescription(bundle.getString ("HINT_executionSetName"));
                es.addProperties (exps);
                s.put(exps);
            }
        }

        return s;
    }

    /** The implementation of the Runnable interface
    * (initialization tasks in separate thread)
    */
    public void run () {
        // set right source element to our children
        SourceCookie sc =
            (SourceCookie)getDataObject().getCookie(SourceCookie.class);
        if (sc != null) {
            ((SourceChildren)getChildren()).setElement(sc.getSource());
        }

        resolveIcons();
    }

    // --------------------------------------------------------------------
    // private methods

    /** Find right icon for this node. */
    protected void resolveIcons () {
        ClassDataObject dataObj = (ClassDataObject)getDataObject();
        try {
            dataObj.getBeanClass (); // check exception
            if (dataObj.isJavaBean ()) {
                if (dataObj.isExecutable ())
                    setIconBase(BEAN_MAIN_BASE);
                else
                    setIconBase(BEAN_BASE);
            } else
                if (dataObj.isExecutable ())
                    setIconBase(CLASS_MAIN_BASE);
                else
                    setIconBase(CLASS_BASE);
        } catch (IOException ex) {
            setIconBase(ERROR_BASE);
        } catch (ClassNotFoundException ex) {
            setIconBase(ERROR_BASE);
        }
        iconResolved = true;
    }
}

/*
 * Log
 *  28   Gandalf   1.27        1/18/00  David Simonek   Execution now correctly 
 *       disabled for ser data nodes
 *  27   Gandalf   1.26        1/13/00  David Simonek   i18n
 *  26   Gandalf   1.25        1/5/00   David Simonek   
 *  25   Gandalf   1.24        10/29/99 Jesse Glick     Using undeprecated 
 *       variant of *Support.addProperties.
 *  24   Gandalf   1.23        10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  23   Gandalf   1.22        8/9/99   Jaroslav Tulach Delays initialization of
 *       children => solves one deadlock.
 *  22   Gandalf   1.21        6/28/99  Petr Hrebejk    Multiple node factories 
 *       added
 *  21   Gandalf   1.20        6/9/99   Ian Formanek    ---- Package Change To 
 *       org.openide ----
 *  20   Gandalf   1.19        5/10/99  Jaroslav Tulach DataNode.canRename
 *  19   Gandalf   1.18        4/22/99  Ales Novak      new model of execution
 *  18   Gandalf   1.17        4/4/99   Ian Formanek    
 *  17   Gandalf   1.16        3/26/99  David Simonek   
 *  16   Gandalf   1.15        3/26/99  Ian Formanek    Fixed use of obsoleted 
 *       NbBundle.getBundle (this)
 *  15   Gandalf   1.14        3/22/99  Ian Formanek    Icons location fixed
 *  14   Gandalf   1.13        3/22/99  Ian Formanek    Icons moved from 
 *       modules/resources to this package
 *  13   Gandalf   1.12        3/16/99  Petr Hamernik   renaming static fields
 *  12   Gandalf   1.11        3/15/99  Petr Hamernik   
 *  11   Gandalf   1.10        2/25/99  Jaroslav Tulach Change of clipboard 
 *       management  
 *  10   Gandalf   1.9         2/15/99  David Simonek   
 *  9    Gandalf   1.8         2/9/99   David Simonek   
 *  8    Gandalf   1.7         2/9/99   David Simonek   little fixes - init in 
 *       separate thread
 *  7    Gandalf   1.6         2/3/99   David Simonek   
 *  6    Gandalf   1.5         2/1/99   David Simonek   
 *  5    Gandalf   1.4         1/20/99  David Simonek   rework of class DO
 *  4    Gandalf   1.3         1/19/99  David Simonek   
 *  3    Gandalf   1.2         1/13/99  David Simonek   
 *  2    Gandalf   1.1         1/6/99   Ian Formanek    Reflecting change in 
 *       datasystem package
 *  1    Gandalf   1.0         1/5/99   Ian Formanek    
 * $
 */
