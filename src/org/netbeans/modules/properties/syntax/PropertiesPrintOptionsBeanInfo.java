/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2000 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.properties.syntax;

import java.beans.*;
import java.awt.Image;
import java.util.ResourceBundle;

import org.openide.util.NbBundle;

/** BeanInfo for properties print options
 *
 * @author Petr Jiricka, Libor Kramolis
 */
public class PropertiesPrintOptionsBeanInfo extends org.netbeans.modules.editor.options.BasePrintOptionsBeanInfo {

    public PropertiesPrintOptionsBeanInfo () {
        super ("/org/netbeans/modules/editor/resources/htmlOptions");
    }

    public PropertiesPrintOptionsBeanInfo (String iconPrefix) {
        super (iconPrefix);
    }

    protected Class getBeanClass() {
        return PropertiesPrintOptions.class;
    }
}

/*
 * <<Log>>
 *  2    Gandalf   1.1         10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  1    Gandalf   1.0         9/13/99  Petr Jiricka    
 * $
 */
