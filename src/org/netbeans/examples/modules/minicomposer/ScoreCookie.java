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
import javax.swing.event.ChangeListener;
import org.openide.nodes.Node;
import org.openide.util.Task;
public interface ScoreCookie extends Node.Cookie {
    public Task prepare ();
    public Score getScore () throws IOException;
    public void setScore (Score s) throws IOException;
    public boolean isValid ();
    public void addChangeListener (ChangeListener l);
    public void removeChangeListener (ChangeListener l);
}
