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

package org.netbeans.modules.jarpackager;

import java.io.Serializable;

import org.openide.filesystems.FileObject;

/** Interface for filtering file objects. Implementations
* can decide which file objects to accept.
*
* @author Dafe Simonek
*/
public interface FileObjectFilter extends Serializable {

    /** Accepts or refuses given file object.
    * @param fo File object to accept of refuse
    * @return true if given file object can be accepted, false otherwise.
    */
    public boolean accept (FileObject fo);

}

/*
* <<Log>>
*  5    Gandalf   1.4         10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun 
*       Microsystems Copyright in File Comment
*  4    Gandalf   1.3         6/9/99   Ian Formanek    ---- Package Change To 
*       org.openide ----
*  3    Gandalf   1.2         6/4/99   David Simonek   manifest creation now 
*       supported correctly
*  2    Gandalf   1.1         6/3/99   David Simonek   
*  1    Gandalf   1.0         5/26/99  David Simonek   
* $
*/