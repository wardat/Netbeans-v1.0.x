/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2001 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.lib.ddl;

/**
* Interface of foreign key constraint.
*
* @author Slavek Psenicka
*/
public interface ForeignKeyConstraintDescriptor extends TableConstraintDescriptor
{
    /** Returns name of Referenced table */
    public String getReferencedTableName();

    /** Sets name of Referenced table
    * @param cname Referenced table name.
    */
    public void setReferencedTableName(String cname);

    /** Returns name of Referenced column */
    public String getReferencedColumnName();

    /** Sets name of Referenced column
    * @param cname Referenced column name.
    */
    public void setReferencedColumnName(String cname);
}

/*
* <<Log>>
*  4    Gandalf   1.3         10/22/99 Ian Formanek    NO SEMANTIC CHANGE - Sun 
*       Microsystems Copyright in File Comment
*  3    Gandalf   1.2         5/14/99  Slavek Psenicka new version
*  2    Gandalf   1.1         4/23/99  Slavek Psenicka new version
*  1    Gandalf   1.0         4/6/99   Slavek Psenicka 
* $
*/
