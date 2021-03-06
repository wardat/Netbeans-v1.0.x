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

package org.netbeans.modules.editor;

import java.awt.Component;
import javax.swing.text.AttributeSet;
import javax.swing.JEditorPane;
import org.netbeans.editor.GuardedDocument;
import org.netbeans.editor.Syntax;
import org.netbeans.editor.Utilities;
import org.openide.text.NbDocument;

/**
* BaseDocument extension managing the readonly blocks of text
*
* @author Miloslav Metelka
* @version 1.00
*/

public class NbEditorDocument extends GuardedDocument
            implements NbDocument.PositionBiasable, NbDocument.WriteLockable,
    NbDocument.Printable, NbDocument.CustomEditor {

    PrintSupport printSupport;

    public NbEditorDocument(Class kitClass) {
        super(kitClass);
        addStyleToLayerMapping(NbDocument.BREAKPOINT_STYLE_NAME,
                               NbDocument.BREAKPOINT_STYLE_NAME + "Layer:5000"); // NOI18N
        addStyleToLayerMapping(NbDocument.ERROR_STYLE_NAME,
                               NbDocument.ERROR_STYLE_NAME + "Layer:6000"); // NOI18N
        addStyleToLayerMapping(NbDocument.CURRENT_STYLE_NAME,
                               NbDocument.CURRENT_STYLE_NAME + "Layer:7000"); // NOI18N
        setNormalStyleName(NbDocument.NORMAL_STYLE_NAME);
    }


    public void setCharacterAttributes(int offset, int length, AttributeSet s,
                                       boolean replace) {
        if (s != null) {
            Object val = s.getAttribute(NbDocument.GUARDED);
            if (val != null && val instanceof Boolean) {
                if (((Boolean)val).booleanValue() == true) { // want make guarded
                    super.setCharacterAttributes(offset, length, guardedSet, replace);
                } else { // want make unguarded
                    super.setCharacterAttributes(offset, length, unguardedSet, replace);
                }
            } else { // not special values, just pass
                super.setCharacterAttributes(offset, length, s, replace);
            }
        }
    }

    protected PrintSupport getPrintSupport() {
        if (printSupport == null) {
            printSupport = new PrintSupport(this);
        }
        return printSupport;
    }

    public java.text.AttributedCharacterIterator[] createPrintIterators() {
        return getPrintSupport().createPrintIterators();
    }

    public Component createEditor(JEditorPane j) {
        return Utilities.getExtUI(j).getExtComponent();
    }

}

/*
 * Log
 *  14   Gandalf-post-FCS1.12.1.0    3/8/00   Miloslav Metelka 
 *  13   Gandalf   1.12        1/13/00  Miloslav Metelka Localization
 *  12   Gandalf   1.11        11/14/99 Miloslav Metelka 
 *  11   Gandalf   1.10        10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  10   Gandalf   1.9         9/10/99  Miloslav Metelka 
 *  9    Gandalf   1.8         8/27/99  Miloslav Metelka 
 *  8    Gandalf   1.7         7/9/99   Miloslav Metelka 
 *  7    Gandalf   1.6         6/9/99   Ian Formanek    ---- Package Change To 
 *       org.openide ----
 *  6    Gandalf   1.5         5/7/99   Miloslav Metelka improved setChar.Attr.()
 *  5    Gandalf   1.4         5/5/99   Miloslav Metelka 
 *  4    Gandalf   1.3         4/22/99  Miloslav Metelka 
 *  3    Gandalf   1.2         4/8/99   Miloslav Metelka 
 *  2    Gandalf   1.1         3/23/99  Miloslav Metelka 
 *  1    Gandalf   1.0         3/18/99  Miloslav Metelka 
 * $
 */

