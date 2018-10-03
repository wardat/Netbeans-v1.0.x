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

package org.netbeans.modules.debugger.debug;

import java.util.Enumeration;

import org.openide.TopManager;
import org.openide.execution.ExecInfo;
import org.openide.debugger.DebuggerType;
import org.openide.debugger.DebuggerException;
import org.openide.util.HelpCtx;
import org.openide.debugger.DebuggerType;
import org.openide.util.NbBundle;
import org.openide.execution.NbProcessDescriptor;

import org.netbeans.modules.debugger.support.ProcessDebuggerType;


/**
* Tools debugger type for sun.tools.debug debugger and JDK 1.1
*/
public class ToolsDebugger11Type extends ProcessDebuggerType {

    static final long serialVersionUID =5999434356855212177L;

    /** Property name of the javaHome property */
    public static final String PROP_JAVA_HOME = "javaHome"; // NOI18N

    /** Switch name constant */
    public static final String JAVA_HOME_SWITCH = "jdk11.java.home"; // NOI18N

    private String javaHome = "";

    /** The default debugger process */
    public static final NbProcessDescriptor DEFAULT_TOOLS_DEBUGGER_11_PROCESS;

    static {
        // initialize DEFAULT_TOOLS_DEBUGGER_11_PROCESS

        DEFAULT_TOOLS_DEBUGGER_11_PROCESS = new NbProcessDescriptor (
                                                "{jdk11.java.home}{/}bin{/}java_g", // NOI18N
                                                "{" + DEBUGGER_OPTIONS + "}" + // NOI18N
                                                " -classpath {" + QUOTE_SWITCH + "}" + // NOI18N
                                                "{" + ToolsDebugger11Type.JAVA_HOME_SWITCH + "}{" + FILE_SEPARATOR_SWITCH + "}lib" + // NOI18N
                                                "{" + FILE_SEPARATOR_SWITCH + "}classes.zip{" + PATH_SEPARATOR_SWITCH + "}" + // NOI18N
                                                "{" + REPOSITORY_SWITCH + "}" + // NOI18N
                                                "{" + LIBRARY_SWITCH + "}" + // NOI18N
                                                "{" + CLASS_PATH_SWITCH + "}" + // NOI18N
                                                "{" + QUOTE_SWITCH + "}" + // NOI18N
                                                " {" + MAIN_SWITCH + "}", // NOI18N
                                                NbBundle.getBundle (ToolsDebugger11Type.class).getString ("MSG_ToolsDebugger11Hint")
                                            );
    }

    public ToolsDebugger11Type() {
        super();
        setDebuggerProcess(DEFAULT_TOOLS_DEBUGGER_11_PROCESS);
    }

    /* Gets the display name for this debugger type. */
    public String displayName () {
        return org.openide.util.NbBundle.getBundle (
                   ToolsDebugger11Type.class
               ).getString ("LAB_ToolsDebugger11TypeName");
    }

    public HelpCtx getHelpCtx () {
        return new HelpCtx (ToolsDebugger11Type.class);
    }

    public String getJavaHome () {
        return javaHome;
    }

    public void setJavaHome (String javaHome) {
        this.javaHome = javaHome;
    }

    /* Starts the debugger. */
    public void startDebugger (ExecInfo info, boolean stopOnMain) throws DebuggerException {
        TopManager.getDefault ().getDebugger ().startDebugger (
            new ToolsDebugger11Info (
                info.getClassName (),
                info.getArguments (),
                stopOnMain ? info.getClassName () : null,
                getDebuggerProcess (),
                getClassPath (),
                getBootClassPath (),
                getRepositoryPath (),
                getLibraryPath (),
                isClassic (),
                getJavaHome ()
            )
        );
    }
}