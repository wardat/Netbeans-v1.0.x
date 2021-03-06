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

package org.netbeans.modules.web.core.jswdk;

import java.awt.Image;
import java.beans.*;
import java.util.ResourceBundle;
import org.openide.util.NbBundle;

import org.netbeans.modules.debugger.delegator.DefaultDebuggerType;

/** Object that provides beaninfo for {@link ServletJspDebuggerType}.
*
* @author Petr Jiricka
*/
public class ServletJspDebuggerTypeBeanInfo extends SimpleBeanInfo {

    private static Image icon;

    private static BeanDescriptor descr;
    private static PropertyDescriptor[] prop;
    static {
        try {
            descr = new BeanDescriptor (ServletJspDebuggerType.class);
            ResourceBundle bundle = NbBundle.getBundle(ServletJspDebuggerTypeBeanInfo.class);

            descr.setName (bundle.getString("CTL_Debug_Name"));

            prop = new PropertyDescriptor[5];
            prop[0] = new PropertyDescriptor ("port", ServletJspDebuggerType.class, "getPort", "setPort"); // 0 // NOI18N
            prop[0].setDisplayName(bundle.getString("CTL_PROP_Port"));
            prop[0].setShortDescription(bundle.getString("HINT_Port"));
            prop[1] = new PropertyDescriptor ("documentRoot", ServletJspDebuggerType.class, "getDocumentRoot", "setDocumentRoot"); // 1 // NOI18N
            prop[1].setDisplayName(bundle.getString("CTL_PROP_DocRoot"));
            prop[1].setShortDescription(bundle.getString("HINT_DocRoot"));
            prop[1].setPropertyEditorClass(ContextRootEditor.class);
            prop[2] = new PropertyDescriptor ("MIMETypes", ServletJspDebuggerType.class, "getMIMETypes", "setMIMETypes"); // 2 // NOI18N
            prop[2].setDisplayName(bundle.getString("CTL_PROP_MIMETypes"));
            prop[2].setShortDescription(bundle.getString("HINT_MIMETypes"));
            prop[2].setPropertyEditorClass(PairPropertyEditor.MimeMapEditor.class);
            prop[3] = new PropertyDescriptor ("welcomeFiles", ServletJspDebuggerType.class, "getWelcomeFiles", "setWelcomeFiles"); // 3 // NOI18N
            prop[3].setDisplayName(bundle.getString("CTL_PROP_WelcomeFiles"));
            prop[3].setShortDescription(bundle.getString("HINT_WelcomeFiles"));
            prop[4] = new PropertyDescriptor ("invoker", ServletJspDebuggerType.class, "isInvoker", "setInvoker"); // 4 // NOI18N
            prop[4].setDisplayName(bundle.getString("CTL_PROP_Invoker"));
            prop[4].setShortDescription(bundle.getString("HINT_Invoker"));
        } catch (IntrospectionException ex) {
            if (Boolean.getBoolean ("netbeans.debug.exceptions")) // NOI18N
                ex.printStackTrace ();
        }
    }

    public BeanDescriptor getBeanDescriptor () {
        return descr;
    }

    public PropertyDescriptor[] getPropertyDescriptors () {
        return prop;
    }

    public BeanInfo[] getAdditionalBeanInfo () {
        try {
            return new BeanInfo[] { Introspector.getBeanInfo (DefaultDebuggerType.class) };
        } catch (IntrospectionException ie) {
            if (Boolean.getBoolean ("netbeans.debug.exceptions")) // NOI18N
                ie.printStackTrace ();
            return null;
        }
    }

    /** @param type Desired type of the icon
    * @return returns the Executor's/Debugger Type's icon
    */
    public Image getIcon(final int type) {
        if (icon == null)
            icon = loadImage("/org/netbeans/modules/web/core/resources/servletDebugging.gif"); // NOI18N
        return icon;
    }

}


/*
 * Log
 *  6    Gandalf-post-FCS1.4.1.0     4/18/00  Jan Jancura     New "default" debugger 
 *       type
 *  5    Gandalf   1.4         1/13/00  Petr Jiricka    More i18n
 *  4    Gandalf   1.3         1/12/00  Petr Jiricka    i18n phase 1
 *  3    Gandalf   1.2         10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  2    Gandalf   1.1         10/12/99 Petr Jiricka    Removed debug messages
 *  1    Gandalf   1.0         10/9/99  Petr Jiricka    
 * $
 */
