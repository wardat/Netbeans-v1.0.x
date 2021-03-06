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

package org.netbeans.examples.modules.minicomposer;
import java.io.IOException;
import org.openide.TopManager;
import org.openide.cookies.*;
import org.openide.loaders.*;
import org.openide.windows.CloneableTopComponent;
public class ScoreOpenSupport extends OpenSupport implements OpenCookie, CloseCookie {
    public ScoreOpenSupport (MultiDataObject.Entry entry) {
        super (entry);
    }
    boolean isOpen () {
        return ! allEditors.isEmpty ();
    }
    protected boolean canClose () {
        if (! super.canClose ()) {
            return false;
        }
        DataObject dob = entry.getDataObject ();
        if (! dob.isModified ()) {
            return true;
        }
        EditorCookie editor = (EditorCookie) dob.getCookie (EditorCookie.class);
        if (editor != null && editor.getOpenedPanes () != null) {
            return true;
        }
        ScoreEditorSupport ses = (ScoreEditorSupport) dob.getCookie (ScoreEditorSupport.class);
        if (ses == null) {
            System.err.println("WARNING: closing modified file, cannot save");
            return true;
        }
        boolean sesCanClose = ses.superCanClose ();
        if (sesCanClose) ses.close ();
        return sesCanClose;
    }
    protected CloneableTopComponent createCloneableTopComponent () {
        return new ScorePanel (entry);
    }
}
