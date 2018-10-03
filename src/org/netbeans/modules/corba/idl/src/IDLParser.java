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

//package org.netbeans.modules.corba.idl.parser;
package org.netbeans.modules.corba.idl.src;

import java.util.Vector;

public class IDLParser/*@bgen(jjtree)*/implements IDLParserTreeConstants, IDLParserConstants {/*@bgen(jjtree)*/
    protected JJTIDLParserState jjtree = new JJTIDLParserState();
    public static void main (String args[]) {
        IDLParser parser = null;
        if (args.length == 0) {
            System.out.println("IDL Parser Version 0.1:  Reading from standard input . . .");
            parser = new IDLParser(System.in);
        } else if (args.length == 1) {
            System.out.println("IDL Parser Version 0.1:  Reading from file " + args[0] + " . . .");
            try {
                parser = new IDLParser(new java.io.FileInputStream(args[0]));
                SimpleNode sn = parser.Start ();
                sn.dump ("|");
                System.out.println ("OK :-))");
            } catch (java.io.FileNotFoundException e) {
                System.out.println("IDL Parser Version 0.1:  File " + args[0] + " not found.");
                return;
            } catch (ParseException e) {
                System.out.println ("IDL parse error !!!");
                e.printStackTrace ();
            }

        } else {
            System.out.println("IDL Parser Version 0.1:  Usage is one of:");
            System.out.println("         java IDLParser < inputfile");
            System.out.println("OR");
            System.out.println("         java IDLParser inputfile");
            return;
        }
    }

    /* comment for matching directives    */
    /* | < "#" ([" ","\t"])* (["0"-"9"])+ */
    /*    (([" ","\t"])* "\"" (~["\""])+ "\"" */
    /*           ([" ","\t"])* (["0"-"9"])* ([" ","\t"])* (["0"-"9"])*)? "\n" >  */


    /* starting */
    final public SimpleNode Start() throws ParseException {
        /*@bgen(jjtree) IDLElement */
        IDLElement jjtn000 = new IDLElement(JJTIDLELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            specification();
            jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
            {if (true) return jjtn000;}
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 1 */
    final public void specification() throws ParseException {
label_1:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 10:
            case 13:
            case 14:
            case 18:
            case 19:
            case 28:
            case 43:
            case 44:
            case 56:
            case 57:
            case 61:
            case 71:
                ;
                break;
            default:
                jj_la1[0] = jj_gen;
                break label_1;
            }
            definition();
        }
    }

    /* Production 2 */
    final public void definition() throws ParseException {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 43:
        case 44:
        case 56:
        case 57:
        case 61:
            type_dcl();
            jj_consume_token(9);
            break;
        case 28:
            const_dcl();
            jj_consume_token(9);
            break;
        case 71:
            except_dcl();
            jj_consume_token(9);
            break;
        default:
            jj_la1[1] = jj_gen;
            if (jj_2_1(2)) {
                interfacex();
                jj_consume_token(9);
            } else {
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case 10:
                    module();
                    jj_consume_token(9);
                    break;
                case 13:
                case 18:
                case 19:
                    value();
                    jj_consume_token(9);
                    break;
                default:
                    jj_la1[2] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
    }

    /* Production 3 */
    final public void module() throws ParseException {
        /*@bgen(jjtree) ModuleElement */
        ModuleElement jjtn000 = new ModuleElement(JJTMODULEELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            Identifier id;
            jj_consume_token(10);
            id = identifier();
            jjtn000.setName (id.getName ());
            jjtn000.setLine (id.getLine ());
            jjtn000.setColumn (id.getColumn ());
            jj_consume_token(11);
label_2:
            while (true) {
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case 10:
                case 13:
                case 14:
                case 18:
                case 19:
                case 28:
                case 43:
                case 44:
                case 56:
                case 57:
                case 61:
                case 71:
                    ;
                    break;
                default:
                    jj_la1[3] = jj_gen;
                    break label_2;
                }
                definition();
            }
            jj_consume_token(12);
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 4 */
    final public void interfacex() throws ParseException {
        if (jj_2_2(4)) {
            interface_dcl();
        } else {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 13:
            case 14:
                forward_dcl();
                break;
            default:
                jj_la1[4] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
        }
    }

    /* Production 5 */
    final public void interface_dcl() throws ParseException {
        /*@bgen(jjtree) InterfaceElement */
        InterfaceElement jjtn000 = new InterfaceElement(JJTINTERFACEELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            Vector header = null;
            interface_header();
            jj_consume_token(11);
            interface_body();
            jj_consume_token(12);
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 6 */
    final public void forward_dcl() throws ParseException {
        /*@bgen(jjtree) InterfaceForwardElement */
        InterfaceForwardElement jjtn000 = new InterfaceForwardElement(JJTINTERFACEFORWARDELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);Identifier id;
        try {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 13:
                jj_consume_token(13);
                jjtn000.setAbstract (true);
                break;
            default:
                jj_la1[5] = jj_gen;
                ;
            }
            jj_consume_token(14);
            id = identifier();
            jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
            jjtn000.setName (id.getName ());
            jjtn000.setLine (id.getLine ());
            jjtn000.setColumn (id.getColumn ());
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 7 */
    final public void interface_header() throws ParseException {
        /*@bgen(jjtree) InterfaceHeaderElement */
        InterfaceHeaderElement jjtn000 = new InterfaceHeaderElement(JJTINTERFACEHEADERELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            Vector inter = new Vector ();
            Identifier name;
            Vector inher;
            Boolean abs = new Boolean (false);
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 13:
                jj_consume_token(13);
                jjtn000.setAbstract (true);
                break;
            default:
                jj_la1[6] = jj_gen;
                ;
            }
            jj_consume_token(14);
            name = identifier();
            jjtn000.setName (name.getName ());
            jjtn000.setLine (name.getLine ());
            jjtn000.setColumn (name.getColumn ());
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 15:
                inher = inheritance_spec();
                jjtn000.setInheritedParents (inher);
                break;
            default:
                jj_la1[7] = jj_gen;
                ;
            }
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 8 */
    final public void interface_body() throws ParseException {
label_3:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 17:
            case 28:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 61:
            case 65:
            case 66:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 79:
            case ID:
                ;
                break;
            default:
                jj_la1[8] = jj_gen;
                break label_3;
            }
            export();
        }
    }

    /* Production 9 */
    final public void export() throws ParseException {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 43:
        case 44:
        case 56:
        case 57:
        case 61:
            type_dcl();
            jj_consume_token(9);
            break;
        case 28:
            const_dcl();
            jj_consume_token(9);
            break;
        case 71:
            except_dcl();
            jj_consume_token(9);
            break;
        case 69:
        case 70:
            attr_dcl();
            jj_consume_token(9);
            break;
        case 17:
        case 45:
        case 46:
        case 47:
        case 48:
        case 49:
        case 50:
        case 51:
        case 52:
        case 53:
        case 54:
        case 55:
        case 65:
        case 66:
        case 72:
        case 73:
        case 79:
        case ID:
            op_dcl();
            jj_consume_token(9);
            break;
        default:
            jj_la1[9] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
    }

    /* Production 10 */
    final public Vector inheritance_spec() throws ParseException {
        Vector inherited_from = new Vector ();
        String name = "";
        jj_consume_token(15);
        name = interface_name();
        inherited_from.addElement (name);
label_4:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 16:
                ;
                break;
            default:
                jj_la1[10] = jj_gen;
                break label_4;
            }
            jj_consume_token(16);
            name = interface_name();
            inherited_from.addElement (name);
        }
    {if (true) return inherited_from;}
        throw new Error("Missing return statement in function");
    }

    /* Production 11 */
    final public String interface_name() throws ParseException {
        String name = "";
        name = scoped_name();
        {if (true) return name;}
        throw new Error("Missing return statement in function");
    }

    /* Production 12 */
    final public String scoped_name() throws ParseException {
        String name = "";
        Identifier id = null;
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 17:
            jj_consume_token(17);
            name = name + "::";
            break;
        default:
            jj_la1[11] = jj_gen;
            ;
        }
        id = identifier();
        name = name + id.getName ();
label_5:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 17:
                ;
                break;
            default:
                jj_la1[12] = jj_gen;
                break label_5;
            }
            jj_consume_token(17);
            name = name + "::";
            id = identifier();
            name = name + id.getName ();
        }
    {if (true) return name;}
        throw new Error("Missing return statement in function");
    }

    /*
    String scoped_name() :
{
      String name = "";
      Identifier id = null;
}
{
      LOOKAHEAD(2)
      id = identifier() 
      { return id.getName ();}
    |
      "::" id = identifier()
      { return "::" + id.getName ();}
    |
        // 
        // I must switch from scoped_name() "::" identifier() to identifier() "::" scoped_name()
        // becauseof left-recursion javacc error
        //
      id = identifier() "::" name = scoped_name() 
      { return id.getName () + "::" + name;}
}
    */
    /* Production 13 */
    final public void value() throws ParseException {
        if (jj_2_3(2147483647)) {
            value_dcl();
        } else if (jj_2_4(2147483647)) {
            value_abs_dcl();
        } else if (jj_2_5(2147483647)) {
            value_box_dcl();
        } else if (jj_2_6(2147483647)) {
            value_forward_dcl();
        } else {
            jj_consume_token(-1);
            throw new ParseException();
        }
    }

    /* Production 14 */
    final public void value_forward_dcl() throws ParseException {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 13:
            jj_consume_token(13);
            break;
        default:
            jj_la1[13] = jj_gen;
            ;
        }
        jj_consume_token(18);
        identifier();
    }

    /* Production 15 */
    final public void value_box_dcl() throws ParseException {
        jj_consume_token(18);
        identifier();
        type_spec();
    }

    /* Production 16 */
    final public void value_abs_dcl() throws ParseException {
        /*@bgen(jjtree) ValueAbsElement */
        ValueAbsElement jjtn000 = new ValueAbsElement(JJTVALUEABSELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);Identifier id;
        try {
            jj_consume_token(13);
            jj_consume_token(18);
            id = identifier();
            jjtn000.setName (id.getName ());
            jjtn000.setLine (id.getLine ());
            jjtn000.setColumn (id.getColumn ());
            value_inheritance_spec();
            jj_consume_token(11);
label_6:
            while (true) {
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case 17:
                case 28:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 61:
                case 65:
                case 66:
                case 69:
                case 70:
                case 71:
                case 72:
                case 73:
                case 79:
                case ID:
                    ;
                    break;
                default:
                    jj_la1[14] = jj_gen;
                    break label_6;
                }
                export();
            }
            jj_consume_token(12);
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 17 */
    final public void value_dcl() throws ParseException {
        /*@bgen(jjtree) ValueElement */
        ValueElement jjtn000 = new ValueElement(JJTVALUEELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            value_header();
            jj_consume_token(11);
label_7:
            while (true) {
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case 17:
                case 22:
                case 23:
                case 24:
                case 28:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 61:
                case 65:
                case 66:
                case 69:
                case 70:
                case 71:
                case 72:
                case 73:
                case 79:
                case ID:
                    ;
                    break;
                default:
                    jj_la1[15] = jj_gen;
                    break label_7;
                }
                value_element();
            }
            jj_consume_token(12);
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 18 */
    final public void value_header() throws ParseException {
        /*@bgen(jjtree) ValueHeaderElement */
        ValueHeaderElement jjtn000 = new ValueHeaderElement(JJTVALUEHEADERELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);Identifier name;
        try {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 19:
                jj_consume_token(19);
                jjtn000.setCustom (true);
                break;
            default:
                jj_la1[16] = jj_gen;
                ;
            }
            jj_consume_token(18);
            name = identifier();
            jjtn000.setName (name.getName ());
            jjtn000.setLine (name.getLine ());
            jjtn000.setColumn (name.getColumn ());
            value_inheritance_spec();
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 19 */
    final public void value_inheritance_spec() throws ParseException {
        /*@bgen(jjtree) ValueInheritanceSpecElement */
        ValueInheritanceSpecElement jjtn000 = new ValueInheritanceSpecElement(JJTVALUEINHERITANCESPECELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);String i_name = "";
        String v_name = "";
        Vector values = new Vector ();
        Vector interfaces = new Vector ();
        try {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 15:
                jj_consume_token(15);
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case 20:
                    jj_consume_token(20);
                    jjtn000.setTruncatable (true);
                    break;
                default:
                    jj_la1[17] = jj_gen;
                    ;
                }
                v_name = value_name();
                values.addElement (v_name);
label_8:
                while (true) {
                    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                    case 16:
                        ;
                        break;
                    default:
                        jj_la1[18] = jj_gen;
                        break label_8;
                    }
                    jj_consume_token(16);
                    v_name = value_name();
                    values.addElement (v_name);
                }
                break;
            default:
                jj_la1[19] = jj_gen;
                ;
            }
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 21:
                jj_consume_token(21);
                i_name = interface_name();
                interfaces.addElement (i_name);
label_9:
                while (true) {
                    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                    case 16:
                        ;
                        break;
                    default:
                        jj_la1[20] = jj_gen;
                        break label_9;
                    }
                    jj_consume_token(16);
                    interface_name();
                }
                break;
            default:
                jj_la1[21] = jj_gen;
                ;
            }
            jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
            jjtn000.setInterfaces (interfaces);
            jjtn000.setValues (values);
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 20 */
    final public String value_name() throws ParseException {
        String name = "";
        name = scoped_name();
        {if (true) return name;}
        throw new Error("Missing return statement in function");
    }

    /* Production 21 */
    final public void value_element() throws ParseException {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 17:
        case 28:
        case 43:
        case 44:
        case 45:
        case 46:
        case 47:
        case 48:
        case 49:
        case 50:
        case 51:
        case 52:
        case 53:
        case 54:
        case 55:
        case 56:
        case 57:
        case 61:
        case 65:
        case 66:
        case 69:
        case 70:
        case 71:
        case 72:
        case 73:
        case 79:
        case ID:
            export();
            break;
        case 22:
        case 23:
            state_member();
            break;
        case 24:
            init_dcl();
            break;
        default:
            jj_la1[22] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
    }

    /* Production 22 */
    final public void state_member() throws ParseException {
        /*@bgen(jjtree) StateMemberElement */
        StateMemberElement jjtn000 = new StateMemberElement(JJTSTATEMEMBERELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 22:
                jj_consume_token(22);
                break;
            case 23:
                jj_consume_token(23);
                break;
            default:
                jj_la1[23] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
            type_spec();
            declarators();
            jj_consume_token(9);
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 23 */
    final public void init_dcl() throws ParseException {
        /*@bgen(jjtree) InitDclElement */
        InitDclElement jjtn000 = new InitDclElement(JJTINITDCLELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            jj_consume_token(24);
            identifier();
            jj_consume_token(25);
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 27:
                init_param_decls();
                break;
            default:
                jj_la1[24] = jj_gen;
                ;
            }
            jj_consume_token(26);
            jj_consume_token(9);
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 24 */
    final public void init_param_decls() throws ParseException {
        init_param_decl();
label_10:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 16:
                ;
                break;
            default:
                jj_la1[25] = jj_gen;
                break label_10;
            }
            jj_consume_token(16);
            init_param_decl();
        }
    }

    /* Production 25 */
    final public void init_param_decl() throws ParseException {
        init_param_attribute();
        param_type_spec();
        simple_declarator();
    }

    /* Production 26 */
    final public void init_param_attribute() throws ParseException {
        jj_consume_token(27);
    }

    /* Production 27 */
    final public void const_dcl() throws ParseException {
        /*@bgen(jjtree) ConstElement */
        ConstElement jjtn000 = new ConstElement(JJTCONSTELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            IDLType type; String exp, name; Identifier id;
            jj_consume_token(28);
            type = const_type();
            id = identifier();
            jj_consume_token(29);
            exp = const_exp();
            jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
            if (type != null)
                jjtn000.setType (type.name);
            else
                System.out.println ("ERROR!! type is null");
            if (id != null) {
                jjtn000.setName (id.getName ());
                jjtn000.setLine (id.getLine ());
                jjtn000.setColumn (id.getColumn ());
            }
            else
                System.out.println ("ERROR!! id is null");
            jjtn000.setExpression (exp);
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 28 */
    final public IDLType const_type() throws ParseException {
        IDLType type;
        String name;
        if (jj_2_7(2)) {
            type = integer_type();
            {if (true) return type;}
        } else {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 50:
                type = char_type();
            {if (true) return type;}
                break;
            case 51:
                type = wide_char_type();
            {if (true) return type;}
                break;
            case 52:
                type = boolean_type();
            {if (true) return type;}
                break;
            default:
                jj_la1[26] = jj_gen;
                if (jj_2_8(2147483647)) {
                    type = floating_pt_type();
                    {if (true) return type;}
                } else {
                    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                    case 65:
                        type = string_type();
                    {if (true) return type;}
                        break;
                    case 66:
                        type = wide_string_type();
                    {if (true) return type;}
                        break;
                    case 78:
                        type = fixed_pt_const_type();
                    {if (true) return type;}
                        break;
                    case 17:
                    case ID:
                        name = scoped_name();
                    {if (true) return new IDLType (IDLType.SCOPED, name);}
                        break;
                    case 53:
                        type = octet_type();
                    {if (true) return type;}
                        break;
                    default:
                        jj_la1[27] = jj_gen;
                        jj_consume_token(-1);
                        throw new ParseException();
                    }
                }
            }
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 29 */
    final public String const_exp() throws ParseException {
        String name;
        name = or_expr();
        {if (true) return name;}
        throw new Error("Missing return statement in function");
    }

    /* Production 30 */
    final public String or_expr() throws ParseException {
        String name, tmp;
        name = xor_expr();
label_11:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 30:
                ;
                break;
            default:
                jj_la1[28] = jj_gen;
                break label_11;
            }
            jj_consume_token(30);
            tmp = xor_expr();
            name = name + "|" + tmp;
        }
    {if (true) return name;}
        throw new Error("Missing return statement in function");
    }

    /* Production 31 */
    final public String xor_expr() throws ParseException {
        String name, tmp;
        name = and_expr();
label_12:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 31:
                ;
                break;
            default:
                jj_la1[29] = jj_gen;
                break label_12;
            }
            jj_consume_token(31);
            tmp = and_expr();
            name = name + "^" + tmp;
        }
    {if (true) return name;}
        throw new Error("Missing return statement in function");
    }

    /* Production 32 */
    final public String and_expr() throws ParseException {
        String name, tmp;
        name = shift_expr();
label_13:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 32:
                ;
                break;
            default:
                jj_la1[30] = jj_gen;
                break label_13;
            }
            jj_consume_token(32);
            tmp = shift_expr();
            name = name + "&" + tmp;
        }
    {if (true) return name;}
        throw new Error("Missing return statement in function");
    }

    /* Production 33 */
    final public String shift_expr() throws ParseException {
        String name, tmp;
        name = add_expr();
label_14:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 33:
            case 34:
                ;
                break;
            default:
                jj_la1[31] = jj_gen;
                break label_14;
            }
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 33:
                jj_consume_token(33);
                name += ">>";
                break;
            case 34:
                jj_consume_token(34);
                name += "<<";
                break;
            default:
                jj_la1[32] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
            tmp = add_expr();
            name += tmp;
        }
    {if (true) return name;}
        throw new Error("Missing return statement in function");
    }

    /* Production 34 */
    final public String add_expr() throws ParseException {
        String name, tmp;
        name = mult_expr();
label_15:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 35:
            case 36:
                ;
                break;
            default:
                jj_la1[33] = jj_gen;
                break label_15;
            }
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 35:
                jj_consume_token(35);
                name += "+";
                break;
            case 36:
                jj_consume_token(36);
                name += "-";
                break;
            default:
                jj_la1[34] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
            tmp = mult_expr();
            name += tmp;
        }
    {if (true) return name;}
        throw new Error("Missing return statement in function");
    }

    /* Production 35 */
    final public String mult_expr() throws ParseException {
        String name, tmp;
        name = unary_expr();
label_16:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 37:
            case 38:
            case 39:
                ;
                break;
            default:
                jj_la1[35] = jj_gen;
                break label_16;
            }
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 37:
                jj_consume_token(37);
                name += "*";
                break;
            case 38:
                jj_consume_token(38);
                name += "/";
                break;
            case 39:
                jj_consume_token(39);
                name += "%";
                break;
            default:
                jj_la1[36] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
            tmp = unary_expr();
            name += tmp;
        }
    {if (true) return name;}
        throw new Error("Missing return statement in function");
    }

    /* Production 36 */
    final public String unary_expr() throws ParseException {
        String name = "", tmp = "";
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 35:
        case 36:
        case 40:
            tmp = unary_operator();
            break;
        default:
            jj_la1[37] = jj_gen;
            ;
        }
        name = primary_expr();
    {if (true) return name + tmp;}
        throw new Error("Missing return statement in function");
    }

    /* Production 37 */
    final public String unary_operator() throws ParseException {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 36:
            jj_consume_token(36);
        {if (true) return "-";}
            break;
        case 35:
            jj_consume_token(35);
        {if (true) return "+";}
            break;
        case 40:
            jj_consume_token(40);
        {if (true) return "~";}
            break;
        default:
            jj_la1[38] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 38 */
    final public String primary_expr() throws ParseException {
        String name = "";
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 17:
        case ID:
            name = scoped_name();
        {if (true) return name;}
            break;
        case 41:
        case 42:
        case OCTALINT:
        case DECIMALINT:
        case HEXADECIMALINT:
        case FLOATONE:
        case FLOATTWO:
        case CHARACTER:
        case WCHARACTER:
        case STRING:
        case WSTRING:
        case FIXED:
            name = literal();
        {if (true) return name;}
            break;
        case 25:
            jj_consume_token(25);
            name = const_exp();
            jj_consume_token(26);
        {if (true) return "(" + name + ")";}
            break;
        default:
            jj_la1[39] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 39 */
    final public String literal() throws ParseException {
        String name = "";
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case OCTALINT:
        case DECIMALINT:
        case HEXADECIMALINT:
            name = integer_literal();
        {if (true) return name;}
            break;
        case STRING:
            name = string_literal();
        {if (true) return name;}
            break;
        case WSTRING:
            name = wide_string_literal();
        {if (true) return name;}
            break;
        case CHARACTER:
            name = character_literal();
        {if (true) return name;}
            break;
        case WCHARACTER:
            name = wide_character_literal();
        {if (true) return name;}
            break;
        case FIXED:
            name = fixed_pt_literal();
        {if (true) return name;}
            break;
        case FLOATONE:
        case FLOATTWO:
            name = floating_pt_literal();
        {if (true) return name;}
            break;
        case 41:
        case 42:
            name = boolean_literal();
        {if (true) return name;}
            break;
        default:
            jj_la1[40] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 40 */
    final public String boolean_literal() throws ParseException {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 41:
            jj_consume_token(41);
        {if (true) return "TRUE";}
            break;
        case 42:
            jj_consume_token(42);
        {if (true) return "FALSE";}
            break;
        default:
            jj_la1[41] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 41 */
    final public String positive_int_const() throws ParseException {
        String name = "";
        name = const_exp();
        {if (true) return name;}
        throw new Error("Missing return statement in function");
    }

    /* Production 42 */
    final public void type_dcl() throws ParseException {
        /*@bgen(jjtree) TypeElement */
        TypeElement jjtn000 = new TypeElement(JJTTYPEELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 43:
                IDLType type; SimpleDeclarator name;
                jj_consume_token(43);
                type = type_declarator();
                jjtree.closeNodeScope(jjtn000, true);
                jjtc000 = false;
                jjtn000.setType (type); /* System.out.println ("IDL.jjt " + jjtThis.getType ()); */
                break;
            case 56:
                type = struct_type();
                jjtree.closeNodeScope(jjtn000, true);
                jjtc000 = false;
                jjtn000.setName (type.name);
                jjtn000.setType (new IDLType (IDLType.STRUCT, "struct"));
                break;
            case 57:
                type = union_type();
                jjtree.closeNodeScope(jjtn000, true);
                jjtc000 = false;
                jjtn000.setName (type.name);
                jjtn000.setType (new IDLType (IDLType.UNION, "union"));
                break;
            case 61:
                type = enum_type();
                jjtree.closeNodeScope(jjtn000, true);
                jjtc000 = false;
                jjtn000.setName (type.name);
                jjtn000.setType (new IDLType (IDLType.ENUM, "enum"));
                break;
            case 44:
                jj_consume_token(44);
                name = simple_declarator();
                jjtree.closeNodeScope(jjtn000, true);
                jjtc000 = false;
                jjtn000.setName (name.getName ());
                jjtn000.setType (new IDLType (IDLType.NATIVE, "native"));
                jjtn000.setLine (name.getLine ());
                jjtn000.setColumn (name.getColumn ());
                break;
            default:
                jj_la1[42] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 43 */
    final public IDLType type_declarator() throws ParseException {
        IDLType type;
        type = type_spec();
        declarators();
        {if (true) return type;}
        throw new Error("Missing return statement in function");
    }

    /* Production 44 */
    final public IDLType type_spec() throws ParseException {
        IDLType type;
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 17:
        case 45:
        case 46:
        case 47:
        case 48:
        case 49:
        case 50:
        case 51:
        case 52:
        case 53:
        case 54:
        case 55:
        case 62:
        case 65:
        case 66:
        case 78:
        case 79:
        case ID:
            type = simple_type_spec();
        {if (true) return type;}
            break;
        case 56:
        case 57:
        case 61:
            type = constr_type_spec();
        {if (true) return type;}
            break;
        default:
            jj_la1[43] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 45 */
    final public IDLType simple_type_spec() throws ParseException {
        IDLType type; String name;
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 45:
        case 46:
        case 47:
        case 48:
        case 49:
        case 50:
        case 51:
        case 52:
        case 53:
        case 54:
        case 55:
        case 79:
            type = base_type_spec();
        {if (true) return type;}
            break;
        case 62:
        case 65:
        case 66:
        case 78:
            type = template_type_spec();
        {if (true) return type;}
            break;
        case 17:
        case ID:
            name = scoped_name();
        {if (true) return new IDLType (IDLType.SCOPED, name);}
            break;
        default:
            jj_la1[44] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 46 */
    final public IDLType base_type_spec() throws ParseException {
        IDLType type;
        if (jj_2_9(2)) {
            type = floating_pt_type();
            {if (true) return type;}
        } else {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 47:
            case 48:
            case 49:
                /* LOOKAHEAD(integer_type()) */
                type = integer_type();
            {if (true) return type;}
                break;
            case 50:
                type = char_type();
            {if (true) return type;}
                break;
            case 51:
                type = wide_char_type();
            {if (true) return type;}
                break;
            case 52:
                type = boolean_type();
            {if (true) return type;}
                break;
            case 53:
                type = octet_type();
            {if (true) return type;}
                break;
            case 54:
                type = any_type();
            {if (true) return type;}
                break;
            case 55:
                type = object_type();
            {if (true) return type;}
                break;
            case 79:
                type = value_base_type();
            {if (true) return type;}
                break;
            default:
                jj_la1[45] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 47 */
    final public IDLType template_type_spec() throws ParseException {
        IDLType type;
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 62:
            type = sequence_type();
        {if (true) return type;}
            break;
        case 65:
            type = string_type();
        {if (true) return type;}
            break;
        case 66:
            type = wide_string_type();
        {if (true) return type;}
            break;
        case 78:
            type = fixed_pt_type();
        {if (true) return type;}
            break;
        default:
            jj_la1[46] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 48 */
    final public IDLType constr_type_spec() throws ParseException {
        IDLType type;
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 56:
            type = struct_type();
        {if (true) return type;}
            break;
        case 57:
            type = union_type();
        {if (true) return type;}
            break;
        case 61:
            type = enum_type();
        {if (true) return type;}
            break;
        default:
            jj_la1[47] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 49 */
    final public void declarators() throws ParseException {
        declarator();
label_17:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 16:
                ;
                break;
            default:
                jj_la1[48] = jj_gen;
                break label_17;
            }
            jj_consume_token(16);
            declarator();
        }
    }

    /* Production 50 */
    /*
    void declarator() #DeclaratorElement :
{
      ArrayDeclarator adecl; SimpleDeclarator sdecl;
}
{
      LOOKAHEAD(2)
      adecl = complex_declarator() 
      { 
        jjtThis.setName (adecl.getName ());
        jjtThis.setLine (adecl.getLine ());
        jjtThis.setColumn (adecl.getColumn ());
        jjtThis.setDimension (adecl.getDimension ());
      }
    |
      sdecl = simple_declarator()
      {
        jjtThis.setName (sdecl.getName ());
        jjtThis.setLine (sdecl.getLine ());
        jjtThis.setColumn (sdecl.getColumn ());
      }
}
    */
    /*
    void declarator() :
{
}
{
      LOOKAHEAD(2)
      complex_declarator() 
    |
      simple_declarator()
}
    */
    final public DeclaratorElement declarator() throws ParseException {
        DeclaratorElement element;
        if (jj_2_10(2)) {
            element = complex_declarator();
            {if (true) return element;}
        } else {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case ID:
                element = simple_declarator();
            {if (true) return element;}
                break;
            default:
                jj_la1[49] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 51 */
    final public SimpleDeclarator simple_declarator() throws ParseException {
        /*@bgen(jjtree) SimpleDeclarator */
        SimpleDeclarator jjtn000 = new SimpleDeclarator(JJTSIMPLEDECLARATOR);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            Identifier id;
            id = identifier();
            jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
            jjtn000.setName (id.getName ());
            jjtn000.setLine (id.getLine ());
            jjtn000.setColumn (id.getColumn ());

            {if (true) return jjtn000;}
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 52 */
    final public ArrayDeclarator complex_declarator() throws ParseException {
        ArrayDeclarator decl;
        decl = array_declarator();
        {if (true) return decl;}
        throw new Error("Missing return statement in function");
    }

    /* Production 53 */
    final public IDLType floating_pt_type() throws ParseException {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 45:
            jj_consume_token(45);
        {if (true) return new IDLType (IDLType.FLOAT, "float");}
            break;
        case 46:
            jj_consume_token(46);
        {if (true) return new IDLType (IDLType.DOUBLE, "double");}
            break;
        case 47:
            jj_consume_token(47);
            jj_consume_token(46);
        {if (true) return new IDLType (IDLType.LONGDOUBLE, "long double");}
            break;
        default:
            jj_la1[50] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 54 */
    final public IDLType integer_type() throws ParseException {
        IDLType type;
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 47:
        case 48:
            type = signed_int();
        {if (true) return type;}
            break;
        case 49:
            type = unsigned_int();
        {if (true) return type;}
            break;
        default:
            jj_la1[51] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 55 */
    final public IDLType signed_int() throws ParseException {
        IDLType type;
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 48:
            type = signed_short_int();
        {if (true) return type;}
            break;
        default:
            jj_la1[52] = jj_gen;
            if (jj_2_11(2)) {
                type = signed_longlong_int();
                {if (true) return type;}
            } else {
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case 47:
                    /* LOOKAHEAD(signed_long_int()) */
                    /* LOOKAHEAD(3) */
                    type = signed_long_int();
                {if (true) return type;}
                    break;
                default:
                    jj_la1[53] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
                }
            }
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 56 */
    final public IDLType signed_short_int() throws ParseException {
        jj_consume_token(48);
        {if (true) return new IDLType (IDLType.SHORT, "short");}
        throw new Error("Missing return statement in function");
    }

    /* Production 57 */
    final public IDLType signed_long_int() throws ParseException {
        jj_consume_token(47);
        {if (true) return new IDLType (IDLType.LONG, "long");}
        throw new Error("Missing return statement in function");
    }

    /* Production 58 */
    final public IDLType signed_longlong_int() throws ParseException {
        jj_consume_token(47);
        jj_consume_token(47);
        {if (true) return new IDLType (IDLType.LONGLONG, "long long");}
        throw new Error("Missing return statement in function");
    }

    /* Production 59 */
    final public IDLType unsigned_int() throws ParseException {
        IDLType type;
        if (jj_2_12(2)) {
            type = unsigned_short_int();
            {if (true) return type;}
        } else if (jj_2_13(3)) {
            type = unsigned_longlong_int();
            {if (true) return type;}
        } else {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 49:
                /* LOOKAHEAD(unsigned_long_int()) */
                /* LOOKAHEAD(2) */
                type = unsigned_long_int();
            {if (true) return type;}
                break;
            default:
                jj_la1[54] = jj_gen;
                jj_consume_token(-1);
                throw new ParseException();
            }
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 60 */
    final public IDLType unsigned_short_int() throws ParseException {
        jj_consume_token(49);
        jj_consume_token(48);
        {if (true) return new IDLType (IDLType.USHORT, "unsigned short");}
        throw new Error("Missing return statement in function");
    }

    /* Production 61 */
    final public IDLType unsigned_long_int() throws ParseException {
        jj_consume_token(49);
        jj_consume_token(47);
        {if (true) return new IDLType (IDLType.ULONG, "unsigned long");}
        throw new Error("Missing return statement in function");
    }

    /* Production 62 */
    final public IDLType unsigned_longlong_int() throws ParseException {
        jj_consume_token(49);
        jj_consume_token(47);
        jj_consume_token(47);
        {if (true) return new IDLType (IDLType.ULONGLONG, "unsigned long long");}
        throw new Error("Missing return statement in function");
    }

    /* Production 63 */
    final public IDLType char_type() throws ParseException {
        jj_consume_token(50);
        {if (true) return new IDLType (IDLType.CHAR, "char");}
        throw new Error("Missing return statement in function");
    }

    /* Production 64 */
    final public IDLType wide_char_type() throws ParseException {
        jj_consume_token(51);
        {if (true) return new IDLType (IDLType.WCHAR, "wchar");}
        throw new Error("Missing return statement in function");
    }

    /* Production 65 */
    final public IDLType boolean_type() throws ParseException {
        jj_consume_token(52);
        {if (true) return new IDLType (IDLType.BOOLEAN, "boolean");}
        throw new Error("Missing return statement in function");
    }

    /* Production 66 */
    final public IDLType octet_type() throws ParseException {
        jj_consume_token(53);
        {if (true) return new IDLType (IDLType.OCTET, "octet");}
        throw new Error("Missing return statement in function");
    }

    /* Production 67 */
    final public IDLType any_type() throws ParseException {
        jj_consume_token(54);
        {if (true) return new IDLType (IDLType.ANY, "any");}
        throw new Error("Missing return statement in function");
    }

    /* Production 68 */
    final public IDLType object_type() throws ParseException {
        jj_consume_token(55);
        {if (true) return new IDLType (IDLType.OBJECT, "Object");}
        throw new Error("Missing return statement in function");
    }

    /* Production 69 */
    final public IDLType struct_type() throws ParseException {
        /*@bgen(jjtree) StructTypeElement */
        StructTypeElement jjtn000 = new StructTypeElement(JJTSTRUCTTYPEELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            String name; Identifier id; Vector vect = new Vector (); IDLType type;
            jj_consume_token(56);
            id = identifier();
            name = id.getName ();
            type = new IDLType (IDLType.STRUCT, "struct");
            //type = new IDLType (IDLType.STRUCT, name);
            jjtn000.setType (type);
            jjtn000.setName (name);
            jjtn000.setLine (id.getLine ());
            jjtn000.setColumn (id.getColumn ());
            jj_consume_token(11);
            member_list();
            jj_consume_token(12);
            jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
            {if (true) return type;}
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 70 */
    final public void member_list() throws ParseException {
label_18:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 17:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 61:
            case 62:
            case 65:
            case 66:
            case 78:
            case 79:
            case ID:
                ;
                break;
            default:
                jj_la1[55] = jj_gen;
                break label_18;
            }
            member();
        }
    }

    /* Production 71 */
    final public void member() throws ParseException {
        /*@bgen(jjtree) MemberElement */
        MemberElement jjtn000 = new MemberElement(JJTMEMBERELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            IDLType type; String name = "";
            type = type_spec();
            jjtn000.setType (type); jjtn000.setName (name);
            declarators();
            jj_consume_token(9);
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 72 */
    final public IDLType union_type() throws ParseException {
        /*@bgen(jjtree) UnionTypeElement */
        UnionTypeElement jjtn000 = new UnionTypeElement(JJTUNIONTYPEELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            String name; Identifier id; Vector vect = new Vector (); IDLType type;
            jj_consume_token(57);
            id = identifier();
            name = id.getName ();
            type = new IDLType (IDLType.UNION, "union");
            jjtn000.setName (name);
            jjtn000.setType (type);
            jjtn000.setLine (id.getLine ());
            jjtn000.setColumn (id.getColumn ());
            jj_consume_token(58);
            jj_consume_token(25);
            type = switch_type_spec();
            jj_consume_token(26);
            jjtn000.setSwitchType (type.name);
            jj_consume_token(11);
            switch_body();
            jj_consume_token(12);
            jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
            {if (true) return type;}
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 73 */
    final public IDLType switch_type_spec() throws ParseException {
        IDLType type; String name = "";
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 47:
        case 48:
        case 49:
            type = integer_type();
        {if (true) return type;}
            break;
        case 50:
            type = char_type();
        {if (true) return type;}
            break;
        case 52:
            type = boolean_type();
        {if (true) return type;}
            break;
        case 61:
            type = enum_type();
        {if (true) return type;}
            break;
        case 17:
        case ID:
            name = scoped_name();
        {if (true) return new IDLType (IDLType.SCOPED, name);}
            break;
        default:
            jj_la1[56] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 74 */
    final public void switch_body() throws ParseException {
label_19:
        while (true) {
            casex();
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 59:
            case 60:
                ;
                break;
            default:
                jj_la1[57] = jj_gen;
                break label_19;
            }
        }
    }

    /* Production 75 */
    final public void casex() throws ParseException {
        /*@bgen(jjtree) UnionMemberElement */
        UnionMemberElement jjtn000 = new UnionMemberElement(JJTUNIONMEMBERELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            String cases = "", tmp = ""; IDLType type; Vector tmp_vec; DeclaratorElement element;
label_20:
            while (true) {
                tmp = case_label();
                cases += tmp + ", ";
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case 59:
                case 60:
                    ;
                    break;
                default:
                    jj_la1[58] = jj_gen;
                    break label_20;
                }
            }
            tmp_vec = element_spec();
            type = (IDLType)tmp_vec.elementAt (0);
            element = (DeclaratorElement)tmp_vec.elementAt (1);
            jjtn000.setType (type);
            jjtn000.setCases (cases.substring (0, cases.length () - 2));
            jjtn000.setName (element.getName ());
            jjtn000.setLine (element.getLine ());
            jjtn000.setColumn (element.getColumn ());
            jj_consume_token(9);
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 76 */
    final public String case_label() throws ParseException {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 59:
            String label = "", exp;
            jj_consume_token(59);
            exp = const_exp();
            label += exp;
            jj_consume_token(15);
        {if (true) return label;}
            break;
        case 60:
            jj_consume_token(60);
            jj_consume_token(15);
        {if (true) return "default";}
            break;
        default:
            jj_la1[59] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 77 */
    final public Vector element_spec() throws ParseException {
        IDLType type; String name = ""; DeclaratorElement element; Vector tmp_vec = new Vector ();
        type = type_spec();
        /* type_spec () */
        element = declarator();
        tmp_vec.add (type);
        tmp_vec.add (element);
        {if (true) return tmp_vec;}
        throw new Error("Missing return statement in function");
    }

    /* Production 78 */
    final public IDLType enum_type() throws ParseException {
        /*@bgen(jjtree) EnumTypeElement */
        EnumTypeElement jjtn000 = new EnumTypeElement(JJTENUMTYPEELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            IDLType type; String name; Identifier id; Vector vect = new Vector ();
            jj_consume_token(61);
            id = identifier();
            name = id.getName ();
            type = new IDLType (IDLType.ENUM, "enum");
            jjtn000.setName (name);
            jjtn000.setType (type);
            jjtn000.setLine (id.getLine ());
            jjtn000.setColumn (id.getColumn ());
            jj_consume_token(11);
            enumerator();
label_21:
            while (true) {
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case 16:
                    ;
                    break;
                default:
                    jj_la1[60] = jj_gen;
                    break label_21;
                }
                jj_consume_token(16);
                enumerator();
            }
            jj_consume_token(12);
            jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
        {if (true) return type;}
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 79 */
    final public void enumerator() throws ParseException {
        /*@bgen(jjtree) ConstElement */
        ConstElement jjtn000 = new ConstElement(JJTCONSTELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            Identifier id;
            id = identifier();
            jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
            jjtn000.setType ("");
            jjtn000.setExpression ("");
            jjtn000.setName (id.getName ());
            jjtn000.setLine (id.getLine ());
            jjtn000.setColumn (id.getColumn ());
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 80 */
    final public IDLType sequence_type() throws ParseException {
        IDLType type; String num = "", retval = ""; int val;
        jj_consume_token(62);
        jj_consume_token(63);
        type = simple_type_spec();
        retval = "sequence <" + type.getName ();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 16:
            jj_consume_token(16);
            num = positive_int_const();
            retval = retval + ", " + num;
            break;
        default:
            jj_la1[61] = jj_gen;
            ;
        }
        jj_consume_token(64);
        retval += ">";
        //if (!num.equals (""))
        try {
            val = (new Integer (num)).intValue ();
        } catch (java.lang.NumberFormatException e) {
            //else
            val = -1;
        }

        {if (true) return new IDLType (IDLType.SEQUENCE, retval, type, null);}
        throw new Error("Missing return statement in function");
    }

    /* Production 81 */
    final public IDLType string_type() throws ParseException {
        String name, tmp = ""; int val;
        jj_consume_token(65);
        name = "string";
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 63:
            jj_consume_token(63);
            name = name + "<";
            tmp = positive_int_const();
            name += tmp;
            jj_consume_token(64);
            name = name + ">";
            break;
        default:
            jj_la1[62] = jj_gen;
            ;
        }
        //if (!tmp.equals (""))
        try {
            {if (true) return new IDLType (IDLType.STRING, name, null, null);}
        } catch (java.lang.NumberFormatException e) {
            //else
            {if (true) return new IDLType (IDLType.STRING, name, null, null);}
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 82 */
    final public IDLType wide_string_type() throws ParseException {
        String name, tmp = ""; int val;
        jj_consume_token(66);
        name = "wstring";
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 63:
            jj_consume_token(63);
            name = name + "<";
            tmp = positive_int_const();
            name += tmp;
            jj_consume_token(64);
            name = name + ">";
            break;
        default:
            jj_la1[63] = jj_gen;
            ;
        }
        //if (!tmp.equals (""))
        try {
            {if (true) return new IDLType (IDLType.WSTRING, name, null, null);}
        } catch (java.lang.NumberFormatException e) {
            //else
            {if (true) return new IDLType (IDLType.WSTRING, name, null, null);}
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 83 */
    final public ArrayDeclarator array_declarator() throws ParseException {
        /*@bgen(jjtree) ArrayDeclarator */
        ArrayDeclarator jjtn000 = new ArrayDeclarator(JJTARRAYDECLARATOR);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            String tmp; Identifier id; Vector dim = new Vector ();
            id = identifier();
label_22:
            while (true) {
                tmp = fixed_array_size();
                Integer number = null;
                try {
                    number = new Integer (tmp.substring (1, tmp.length () - 1));
                } catch (NumberFormatException e) {
                }
                if (number != null) {
                    dim.add (number);
                }
                else {
                    dim.add (tmp.substring (1, tmp.length () - 1));
                }
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case 67:
                    ;
                    break;
                default:
                    jj_la1[64] = jj_gen;
                    break label_22;
                }
            }
            jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
            jjtn000.setDimension (dim);
            jjtn000.setName (id.getName ());
            jjtn000.setLine (id.getLine ());
            jjtn000.setColumn (id.getColumn ());
        {if (true) return jjtn000;}
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 84 */
    final public String fixed_array_size() throws ParseException {
        String dim;
        jj_consume_token(67);
        dim = positive_int_const();
        jj_consume_token(68);
        {if (true) return ("[" + dim + "]");}
        throw new Error("Missing return statement in function");
    }

    /* Production 85 */
    final public void attr_dcl() throws ParseException {
        /*@bgen(jjtree) AttributeElement */
        AttributeElement jjtn000 = new AttributeElement(JJTATTRIBUTEELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            SimpleDeclarator name, other; IDLType type;
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 69:
                jj_consume_token(69);
                jjtn000.setReadOnly (true);
                break;
            default:
                jj_la1[65] = jj_gen;
                ;
            }
            jj_consume_token(70);
            type = param_type_spec();
            jjtn000.setType (type);
            name = simple_declarator();
            jjtn000.setName (name.getName ());
            jjtn000.setLine (name.getLine ());
            jjtn000.setColumn (name.getColumn ());
label_23:
            while (true) {
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case 16:
                    ;
                    break;
                default:
                    jj_la1[66] = jj_gen;
                    break label_23;
                }
                jj_consume_token(16);
                other = simple_declarator();
                jjtn000.addOther (other);
            }
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 86 */
    final public void except_dcl() throws ParseException {
        /*@bgen(jjtree) ExceptionElement */
        ExceptionElement jjtn000 = new ExceptionElement(JJTEXCEPTIONELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);Identifier id;
        try {
            jj_consume_token(71);
            id = identifier();
            jjtn000.setName (id.getName ());
            jjtn000.setLine (id.getLine ());
            jjtn000.setColumn (id.getColumn ());
            jj_consume_token(11);
label_24:
            while (true) {
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case 17:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 61:
                case 62:
                case 65:
                case 66:
                case 78:
                case 79:
                case ID:
                    ;
                    break;
                default:
                    jj_la1[67] = jj_gen;
                    break label_24;
                }
                member();
            }
            jj_consume_token(12);
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 87 */
    final public void op_dcl() throws ParseException {
        /*@bgen(jjtree) OperationElement */
        OperationElement jjtn000 = new OperationElement(JJTOPERATIONELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            String attr;
            Vector params, exceptions, contexts;
            Identifier name;
            /* Element returnType; */
            IDLType returnType;
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 72:
                attr = op_attribute();
                jjtn000.setAttribute (attr);
                break;
            default:
                jj_la1[68] = jj_gen;
                ;
            }
            returnType = op_type_spec();
            jjtn000.setReturnType (returnType);
            name = identifier();
            jjtn000.setName (name.getName ());
            jjtn000.setLine (name.getLine ());
            jjtn000.setColumn (name.getColumn ());
            parameter_dcls();
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 76:
                exceptions = raises_expr();
                jjtn000.setExceptions (exceptions);
                break;
            default:
                jj_la1[69] = jj_gen;
                ;
            }
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 77:
                contexts = context_expr();
                jjtn000.setContexts (contexts);
                break;
            default:
                jj_la1[70] = jj_gen;
                ;
            }
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 88 */
    final public String op_attribute() throws ParseException {
        jj_consume_token(72);
        {if (true) return "oneway";}
        throw new Error("Missing return statement in function");
    }

    /* Production 89 */
    final public IDLType op_type_spec() throws ParseException {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 17:
        case 45:
        case 46:
        case 47:
        case 48:
        case 49:
        case 50:
        case 51:
        case 52:
        case 53:
        case 54:
        case 55:
        case 65:
        case 66:
        case 79:
        case ID:
            IDLType type;
            type = param_type_spec();
        {if (true) return type;}
            break;
        case 73:
            jj_consume_token(73);
        {if (true) return new IDLType (IDLType.VOID, "void");}
            break;
        default:
            jj_la1[71] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 90 */
    final public void parameter_dcls() throws ParseException {
        jj_consume_token(25);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 27:
        case 74:
        case 75:
            param_dcl();
label_25:
            while (true) {
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case 16:
                    ;
                    break;
                default:
                    jj_la1[72] = jj_gen;
                    break label_25;
                }
                jj_consume_token(16);
                param_dcl();
            }
            break;
        default:
            jj_la1[73] = jj_gen;
            ;
        }
        jj_consume_token(26);
    }

    /* Production 91 */
    final public void param_dcl() throws ParseException {
        /*@bgen(jjtree) ParameterElement */
        ParameterElement jjtn000 = new ParameterElement(JJTPARAMETERELEMENT);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);
        try {
            IDLType type; SimpleDeclarator name; int attr;
            attr = param_attribute();
            type = param_type_spec();
            name = simple_declarator();
            jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
            jjtn000.setAttribute (attr);
            jjtn000.setType (type);
            jjtn000.setName (name.getName ());
            jjtn000.setLine (name.getLine ());
            jjtn000.setColumn (name.getColumn ());
        } catch (Throwable jjte000) {
            if (jjtc000) {
                jjtree.clearNodeScope(jjtn000);
                jjtc000 = false;
            } else {
                jjtree.popNode();
            }
            if (jjte000 instanceof ParseException) {
                {if (true) throw (ParseException)jjte000;}
            }
            if (jjte000 instanceof RuntimeException) {
                {if (true) throw (RuntimeException)jjte000;}
            }
            {if (true) throw (Error)jjte000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
    }

    /* Production 92 */
    final public int param_attribute() throws ParseException {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 27:
            jj_consume_token(27);
        {if (true) return 0;}
            break;
        case 74:
            jj_consume_token(74);
        {if (true) return 2;}
            break;
        case 75:
            jj_consume_token(75);
        {if (true) return 1;}
            break;
        default:
            jj_la1[74] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 93 */
    final public Vector raises_expr() throws ParseException {
        String name; Vector es = new Vector ();
        jj_consume_token(76);
        jj_consume_token(25);
        name = scoped_name();
        es.addElement (name);
label_26:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 16:
                ;
                break;
            default:
                jj_la1[75] = jj_gen;
                break label_26;
            }
            jj_consume_token(16);
            name = scoped_name();
            es.addElement (name);
        }
        jj_consume_token(26);
    {if (true) return es;}
        throw new Error("Missing return statement in function");
    }

    /* Production 94 */
    final public Vector context_expr() throws ParseException {
        String name; Vector cs = new Vector ();
        jj_consume_token(77);
        jj_consume_token(25);
        name = string_literal();
        cs.addElement (name);
label_27:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case 16:
                ;
                break;
            default:
                jj_la1[76] = jj_gen;
                break label_27;
            }
            jj_consume_token(16);
            name = string_literal();
            cs.addElement (name);
        }
        jj_consume_token(26);
    {if (true) return cs;}
        throw new Error("Missing return statement in function");
    }

    /* Production 95 */
    final public IDLType param_type_spec() throws ParseException {
        IDLType type; String name = "";
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 45:
        case 46:
        case 47:
        case 48:
        case 49:
        case 50:
        case 51:
        case 52:
        case 53:
        case 54:
        case 55:
        case 79:
            type = base_type_spec();
        {if (true) return type;}
            break;
        case 65:
            type = string_type();
        {if (true) return type;}
            break;
        case 66:
            type = wide_string_type();
        {if (true) return type;}
            break;
        case 17:
        case ID:
            name = scoped_name();
        {if (true) return new IDLType (IDLType.SCOPED, name);}
            break;
        default:
            jj_la1[77] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    /* Production 96 */
    final public IDLType fixed_pt_type() throws ParseException {
        String dim; String value;
        jj_consume_token(78);
        jj_consume_token(63);
        dim = positive_int_const();
        jj_consume_token(16);
        value = positive_int_const();
        jj_consume_token(64);
        Vector vect = new Vector ();
        vect.addElement (dim);
        vect.addElement (value);
        {if (true) return  new IDLType (IDLType.FIXED, "fixed <" + dim + ", " + value + ">");}
        throw new Error("Missing return statement in function");
    }

    /* Production 97 */
    final public IDLType fixed_pt_const_type() throws ParseException {
        jj_consume_token(78);
        {if (true) return new IDLType (IDLType.FIXED, "fixed");}
        throw new Error("Missing return statement in function");
    }

    /* Production 98 */
    final public IDLType value_base_type() throws ParseException {
        jj_consume_token(79);
        {if (true) return new IDLType (IDLType.VALUEBASE, "ValueBase");}
        throw new Error("Missing return statement in function");
    }

    /* Definitions of complex regular expressions follow */
    final public Identifier identifier() throws ParseException {
        /*@bgen(jjtree) Identifier */
        Identifier jjtn000 = new Identifier(JJTIDENTIFIER);
        boolean jjtc000 = true;
        jjtree.openNodeScope(jjtn000);Token t;
        try {
            t = jj_consume_token(ID);
            jjtree.closeNodeScope(jjtn000, true);
            jjtc000 = false;
            jjtn000.setName (t.image);
            jjtn000.setLine (t.beginLine);
            jjtn000.setColumn (t.beginColumn - 1);
            //jjtThis.setColumn (t.endColumn);
            {if (true) return jjtn000;}
        } finally {
            if (jjtc000) {
                jjtree.closeNodeScope(jjtn000, true);
            }
        }
        throw new Error("Missing return statement in function");
    }

    final public String integer_literal() throws ParseException {
        Token t;
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case OCTALINT:
            t = jj_consume_token(OCTALINT);
        {if (true) return t.image;}
            break;
        case DECIMALINT:
            t = jj_consume_token(DECIMALINT);
        {if (true) return t.image;}
            break;
        case HEXADECIMALINT:
            t = jj_consume_token(HEXADECIMALINT);
        {if (true) return t.image;}
            break;
        default:
            jj_la1[78] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    final public String string_literal() throws ParseException {
        Token t;
        t = jj_consume_token(STRING);
        {if (true) return t.image;}
        throw new Error("Missing return statement in function");
    }

    final public String wide_string_literal() throws ParseException {
        Token t;
        t = jj_consume_token(WSTRING);
        {if (true) return t.image;}
        throw new Error("Missing return statement in function");
    }

    final public String character_literal() throws ParseException {
        Token t;
        t = jj_consume_token(CHARACTER);
        {if (true) return t.image;}
        throw new Error("Missing return statement in function");
    }

    final public String wide_character_literal() throws ParseException {
        Token t;
        t = jj_consume_token(WCHARACTER);
        {if (true) return t.image;}
        throw new Error("Missing return statement in function");
    }

    final public String floating_pt_literal() throws ParseException {
        Token t;
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case FLOATONE:
            t = jj_consume_token(FLOATONE);
        {if (true) return t.image;}
            break;
        case FLOATTWO:
            t = jj_consume_token(FLOATTWO);
        {if (true) return t.image;}
            break;
        default:
            jj_la1[79] = jj_gen;
            jj_consume_token(-1);
            throw new ParseException();
        }
        throw new Error("Missing return statement in function");
    }

    final public String fixed_pt_literal() throws ParseException {
        Token t;
        t = jj_consume_token(FIXED);
        {if (true) return t.image;}
        throw new Error("Missing return statement in function");
    }

    final private boolean jj_2_1(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        boolean retval = !jj_3_1();
        jj_save(0, xla);
        return retval;
    }

    final private boolean jj_2_2(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        boolean retval = !jj_3_2();
        jj_save(1, xla);
        return retval;
    }

    final private boolean jj_2_3(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        boolean retval = !jj_3_3();
        jj_save(2, xla);
        return retval;
    }

    final private boolean jj_2_4(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        boolean retval = !jj_3_4();
        jj_save(3, xla);
        return retval;
    }

    final private boolean jj_2_5(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        boolean retval = !jj_3_5();
        jj_save(4, xla);
        return retval;
    }

    final private boolean jj_2_6(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        boolean retval = !jj_3_6();
        jj_save(5, xla);
        return retval;
    }

    final private boolean jj_2_7(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        boolean retval = !jj_3_7();
        jj_save(6, xla);
        return retval;
    }

    final private boolean jj_2_8(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        boolean retval = !jj_3_8();
        jj_save(7, xla);
        return retval;
    }

    final private boolean jj_2_9(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        boolean retval = !jj_3_9();
        jj_save(8, xla);
        return retval;
    }

    final private boolean jj_2_10(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        boolean retval = !jj_3_10();
        jj_save(9, xla);
        return retval;
    }

    final private boolean jj_2_11(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        boolean retval = !jj_3_11();
        jj_save(10, xla);
        return retval;
    }

    final private boolean jj_2_12(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        boolean retval = !jj_3_12();
        jj_save(11, xla);
        return retval;
    }

    final private boolean jj_2_13(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        boolean retval = !jj_3_13();
        jj_save(12, xla);
        return retval;
    }

    final private boolean jj_3R_170() {
        if (jj_3R_184()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_205() {
        if (jj_3R_93()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_169() {
        if (jj_3R_198()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_132() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_169()) {
            jj_scanpos = xsp;
            if (jj_3R_170()) {
                jj_scanpos = xsp;
                if (jj_3R_171()) {
                    jj_scanpos = xsp;
                    if (jj_3R_172()) return true;
                    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_204() {
        if (jj_3R_135()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_79() {
        if (jj_scan_token(16)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_78()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_76() {
        if (jj_3R_93()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_203() {
        if (jj_3R_183()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_45() {
        if (jj_scan_token(ID)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_202() {
        if (jj_3R_181()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_201() {
        if (jj_3R_34()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_174() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_201()) {
            jj_scanpos = xsp;
            if (jj_3R_202()) {
                jj_scanpos = xsp;
                if (jj_3R_203()) {
                    jj_scanpos = xsp;
                    if (jj_3R_204()) {
                        jj_scanpos = xsp;
                        if (jj_3R_205()) return true;
                        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_168() {
        if (jj_3R_197()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_167() {
        if (jj_3R_196()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_75() {
        if (jj_scan_token(20)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_166() {
        if (jj_3R_195()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_197() {
        if (jj_scan_token(79)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_63() {
        if (jj_scan_token(21)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_78()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_79()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_77() {
        if (jj_scan_token(16)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_76()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_165() {
        if (jj_3R_187()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_62() {
        if (jj_scan_token(15)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_75()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_76()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_77()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_164() {
        if (jj_3R_183()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_46() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_62()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        xsp = jj_scanpos;
        if (jj_3R_63()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_163() {
        if (jj_3R_182()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_186() {
        if (jj_scan_token(78)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_162() {
        if (jj_3R_181()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_161() {
        if (jj_3R_34()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_44() {
        if (jj_3R_61()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3_9() {
        if (jj_3R_35()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_131() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_9()) {
            jj_scanpos = xsp;
            if (jj_3R_161()) {
                jj_scanpos = xsp;
                if (jj_3R_162()) {
                    jj_scanpos = xsp;
                    if (jj_3R_163()) {
                        jj_scanpos = xsp;
                        if (jj_3R_164()) {
                            jj_scanpos = xsp;
                            if (jj_3R_165()) {
                                jj_scanpos = xsp;
                                if (jj_3R_166()) {
                                    jj_scanpos = xsp;
                                    if (jj_3R_167()) {
                                        jj_scanpos = xsp;
                                        if (jj_3R_168()) return true;
                                        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                                    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                                } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_134() {
        if (jj_scan_token(57)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(58)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(25)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_174()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(26)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(11)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_175()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(12)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_199() {
        if (jj_scan_token(78)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(63)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_214()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(16)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_214()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(64)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_60() {
        if (jj_scan_token(19)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_43() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_60()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(18)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_46()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_150() {
        if (jj_3R_48()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_110()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(9)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_101() {
        if (jj_3R_93()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_154() {
        if (jj_3R_93()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_100() {
        if (jj_3R_132()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_200() {
        if (jj_3R_150()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_153() {
        if (jj_3R_185()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_173() {
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_200()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_85() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_99()) {
            jj_scanpos = xsp;
            if (jj_3R_100()) {
                jj_scanpos = xsp;
                if (jj_3R_101()) return true;
                if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_99() {
        if (jj_3R_131()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_30() {
        if (jj_3R_43()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(11)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_44()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        if (jj_scan_token(12)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_152() {
        if (jj_3R_184()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_194() {
        if (jj_scan_token(16)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_193()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_47() {
        if (jj_3R_64()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_123() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_151()) {
            jj_scanpos = xsp;
            if (jj_3R_152()) {
                jj_scanpos = xsp;
                if (jj_3R_153()) {
                    jj_scanpos = xsp;
                    if (jj_3R_154()) return true;
                    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_151() {
        if (jj_3R_131()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_66() {
        if (jj_3R_86()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_65() {
        if (jj_3R_85()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_48() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_65()) {
            jj_scanpos = xsp;
            if (jj_3R_66()) return true;
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_192() {
        if (jj_scan_token(16)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_93()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_31() {
        if (jj_scan_token(13)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(18)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_46()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(11)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_47()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        if (jj_scan_token(12)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_160() {
        if (jj_scan_token(77)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(25)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_193()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_194()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        if (jj_scan_token(26)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_32() {
        if (jj_scan_token(18)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_48()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_133() {
        if (jj_scan_token(56)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(11)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_173()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(12)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_139() {
        if (jj_3R_48()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_110()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_159() {
        if (jj_scan_token(76)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(25)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_93()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_192()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        if (jj_scan_token(26)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_49() {
        if (jj_scan_token(13)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3_6() {
        if (jj_3R_33()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_33() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_49()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(18)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3_5() {
        if (jj_3R_32()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_196() {
        if (jj_scan_token(55)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_220() {
        if (jj_scan_token(75)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3_4() {
        if (jj_3R_31()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_219() {
        if (jj_scan_token(74)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3_3() {
        if (jj_3R_30()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_118() {
        if (jj_scan_token(44)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_124()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_218() {
        if (jj_scan_token(27)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_212() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_218()) {
            jj_scanpos = xsp;
            if (jj_3R_219()) {
                jj_scanpos = xsp;
                if (jj_3R_220()) return true;
                if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_195() {
        if (jj_scan_token(54)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_117() {
        if (jj_3R_135()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_191() {
        if (jj_scan_token(16)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_190()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_116() {
        if (jj_3R_134()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_187() {
        if (jj_scan_token(53)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_115() {
        if (jj_3R_133()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_190() {
        if (jj_3R_212()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_123()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_124()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_158() {
        if (jj_3R_190()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_191()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_94() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_114()) {
            jj_scanpos = xsp;
            if (jj_3R_115()) {
                jj_scanpos = xsp;
                if (jj_3R_116()) {
                    jj_scanpos = xsp;
                    if (jj_3R_117()) {
                        jj_scanpos = xsp;
                        if (jj_3R_118()) return true;
                        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_114() {
        if (jj_scan_token(43)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_139()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_128() {
        if (jj_scan_token(25)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_158()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(26)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_183() {
        if (jj_scan_token(52)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_214() {
        if (jj_3R_120()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_157() {
        if (jj_scan_token(73)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_182() {
        if (jj_scan_token(51)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_113() {
        if (jj_scan_token(17)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_112() {
        if (jj_scan_token(17)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_127() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_156()) {
            jj_scanpos = xsp;
            if (jj_3R_157()) return true;
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_156() {
        if (jj_3R_123()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_270() {
        if (jj_scan_token(42)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_269() {
        if (jj_scan_token(41)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_263() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_269()) {
            jj_scanpos = xsp;
            if (jj_3R_270()) return true;
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_93() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_112()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_113()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_155() {
        if (jj_scan_token(72)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_181() {
        if (jj_scan_token(50)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_256() {
        if (jj_3R_263()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_130() {
        if (jj_3R_160()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_129() {
        if (jj_3R_159()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_78() {
        if (jj_3R_93()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_255() {
        if (jj_3R_262()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_254() {
        if (jj_3R_261()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_39() {
        if (jj_scan_token(49)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(47)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(47)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_253() {
        if (jj_3R_260()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_240() {
        if (jj_scan_token(39)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_126() {
        if (jj_3R_155()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_252() {
        if (jj_3R_259()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_251() {
        if (jj_3R_258()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_250() {
        if (jj_3R_193()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_71() {
        if (jj_scan_token(15)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_78()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_107() {
        if (jj_scan_token(49)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(47)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_249() {
        if (jj_3R_257()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_248() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_249()) {
            jj_scanpos = xsp;
            if (jj_3R_250()) {
                jj_scanpos = xsp;
                if (jj_3R_251()) {
                    jj_scanpos = xsp;
                    if (jj_3R_252()) {
                        jj_scanpos = xsp;
                        if (jj_3R_253()) {
                            jj_scanpos = xsp;
                            if (jj_3R_254()) {
                                jj_scanpos = xsp;
                                if (jj_3R_255()) {
                                    jj_scanpos = xsp;
                                    if (jj_3R_256()) return true;
                                    if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                                } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_98() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_126()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_127()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_128()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        xsp = jj_scanpos;
        if (jj_3R_129()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        xsp = jj_scanpos;
        if (jj_3R_130()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_121() {
        if (jj_3R_150()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_84() {
        if (jj_3R_98()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(9)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_83() {
        if (jj_3R_97()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(9)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_38() {
        if (jj_scan_token(49)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(48)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_239() {
        if (jj_scan_token(38)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_82() {
        if (jj_3R_96()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(9)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_244() {
        if (jj_scan_token(25)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_120()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(26)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_81() {
        if (jj_3R_95()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(9)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_80() {
        if (jj_3R_94()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(9)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_64() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_80()) {
            jj_scanpos = xsp;
            if (jj_3R_81()) {
                jj_scanpos = xsp;
                if (jj_3R_82()) {
                    jj_scanpos = xsp;
                    if (jj_3R_83()) {
                        jj_scanpos = xsp;
                        if (jj_3R_84()) return true;
                        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_243() {
        if (jj_3R_248()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_96() {
        if (jj_scan_token(71)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(11)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_121()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        if (jj_scan_token(12)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_242() {
        if (jj_3R_93()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_237() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_242()) {
            jj_scanpos = xsp;
            if (jj_3R_243()) {
                jj_scanpos = xsp;
                if (jj_3R_244()) return true;
                if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_89() {
        if (jj_3R_107()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_59() {
        if (jj_3R_64()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_235() {
        if (jj_scan_token(36)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_125() {
        if (jj_scan_token(16)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_124()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_42() {
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_59()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3_13() {
        if (jj_3R_39()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_247() {
        if (jj_scan_token(40)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_58() {
        if (jj_3R_71()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_68() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_12()) {
            jj_scanpos = xsp;
            if (jj_3_13()) {
                jj_scanpos = xsp;
                if (jj_3R_89()) return true;
                if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3_12() {
        if (jj_3R_38()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_122() {
        if (jj_scan_token(69)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_238() {
        if (jj_scan_token(37)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_246() {
        if (jj_scan_token(35)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_229() {
        if (jj_scan_token(34)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_233() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_238()) {
            jj_scanpos = xsp;
            if (jj_3R_239()) {
                jj_scanpos = xsp;
                if (jj_3R_240()) return true;
                if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_232()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_97() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_122()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(70)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_123()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_124()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_125()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_245() {
        if (jj_scan_token(36)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_241() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_245()) {
            jj_scanpos = xsp;
            if (jj_3R_246()) {
                jj_scanpos = xsp;
                if (jj_3R_247()) return true;
                if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_57() {
        if (jj_scan_token(13)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_236() {
        if (jj_3R_241()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_37() {
        if (jj_scan_token(47)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(47)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_234() {
        if (jj_scan_token(35)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_90() {
        if (jj_scan_token(67)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_214()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(68)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_232() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_236()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_237()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_227() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_234()) {
            jj_scanpos = xsp;
            if (jj_3R_235()) return true;
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_226()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_41() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_57()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(14)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        xsp = jj_scanpos;
        if (jj_3R_58()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_69() {
        if (jj_3R_90()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_106() {
        if (jj_scan_token(47)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_228() {
        if (jj_scan_token(33)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_226() {
        if (jj_3R_232()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_233()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_224() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_228()) {
            jj_scanpos = xsp;
            if (jj_3R_229()) return true;
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_223()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_70() {
        if (jj_scan_token(13)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_56() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_70()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(14)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_217() {
        if (jj_scan_token(32)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_216()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_105() {
        if (jj_scan_token(48)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_223() {
        if (jj_3R_226()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_227()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_29() {
        if (jj_3R_41()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(11)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_42()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(12)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_55() {
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        if (jj_3R_69()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_69()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_88() {
        if (jj_3R_106()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_211() {
        if (jj_scan_token(31)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_210()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_40() {
        if (jj_3R_56()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_216() {
        if (jj_3R_223()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_224()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_28() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_2()) {
            jj_scanpos = xsp;
            if (jj_3R_40()) return true;
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3_2() {
        if (jj_3R_29()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3_11() {
        if (jj_3R_37()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_87() {
        if (jj_3R_105()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_67() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_87()) {
            jj_scanpos = xsp;
            if (jj_3_11()) {
                jj_scanpos = xsp;
                if (jj_3R_88()) return true;
                if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_189() {
        if (jj_scan_token(30)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_188()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_210() {
        if (jj_3R_216()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_217()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_209() {
        if (jj_scan_token(63)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_214()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(64)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_185() {
        if (jj_scan_token(66)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_209()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_51() {
        if (jj_3R_68()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_188() {
        if (jj_3R_210()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_211()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_50() {
        if (jj_3R_67()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_34() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_50()) {
            jj_scanpos = xsp;
            if (jj_3R_51()) return true;
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3_1() {
        if (jj_3R_28()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_149() {
        if (jj_3R_188()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_189()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_208() {
        if (jj_scan_token(63)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_214()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(64)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_54() {
        if (jj_scan_token(47)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(46)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_184() {
        if (jj_scan_token(65)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_208()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_120() {
        if (jj_3R_149()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_53() {
        if (jj_scan_token(46)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_52() {
        if (jj_scan_token(45)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_35() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_52()) {
            jj_scanpos = xsp;
            if (jj_3R_53()) {
                jj_scanpos = xsp;
                if (jj_3R_54()) return true;
                if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_148() {
        if (jj_3R_187()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_147() {
        if (jj_3R_93()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_213() {
        if (jj_scan_token(16)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_214()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3_8() {
        if (jj_3R_35()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_146() {
        if (jj_3R_186()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_36() {
        if (jj_3R_55()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_145() {
        if (jj_3R_185()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_198() {
        if (jj_scan_token(62)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(63)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_85()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_213()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(64)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_144() {
        if (jj_3R_184()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_177() {
        if (jj_scan_token(16)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_176()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_143() {
        if (jj_3R_35()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_142() {
        if (jj_3R_183()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_141() {
        if (jj_3R_182()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_124() {
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_140() {
        if (jj_3R_181()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_176() {
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3_7() {
        if (jj_3R_34()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_119() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_7()) {
            jj_scanpos = xsp;
            if (jj_3R_140()) {
                jj_scanpos = xsp;
                if (jj_3R_141()) {
                    jj_scanpos = xsp;
                    if (jj_3R_142()) {
                        jj_scanpos = xsp;
                        if (jj_3R_143()) {
                            jj_scanpos = xsp;
                            if (jj_3R_144()) {
                                jj_scanpos = xsp;
                                if (jj_3R_145()) {
                                    jj_scanpos = xsp;
                                    if (jj_3R_146()) {
                                        jj_scanpos = xsp;
                                        if (jj_3R_147()) {
                                            jj_scanpos = xsp;
                                            if (jj_3R_148()) return true;
                                            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                                        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                                    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                                } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                    } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
                } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_178() {
        if (jj_3R_124()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_261() {
        if (jj_scan_token(FIXED)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3_10() {
        if (jj_3R_36()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_136() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_10()) {
            jj_scanpos = xsp;
            if (jj_3R_178()) return true;
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_268() {
        if (jj_scan_token(FLOATTWO)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_135() {
        if (jj_scan_token(61)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(11)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_176()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_177()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        if (jj_scan_token(12)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_267() {
        if (jj_scan_token(FLOATONE)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_262() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_267()) {
            jj_scanpos = xsp;
            if (jj_3R_268()) return true;
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_260() {
        if (jj_scan_token(WCHARACTER)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_95() {
        if (jj_scan_token(28)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_119()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(29)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_120()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_111() {
        if (jj_3R_138()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_180() {
        if (jj_scan_token(16)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_179()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_222() {
        if (jj_3R_48()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_136()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_137() {
        if (jj_scan_token(16)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_136()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_207() {
        if (jj_scan_token(27)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_259() {
        if (jj_scan_token(CHARACTER)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_231() {
        if (jj_scan_token(60)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(15)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_179() {
        if (jj_3R_207()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_123()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_124()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_258() {
        if (jj_scan_token(WSTRING)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_230() {
        if (jj_scan_token(59)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_120()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(15)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_225() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_230()) {
            jj_scanpos = xsp;
            if (jj_3R_231()) return true;
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_110() {
        if (jj_3R_136()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_137()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_138() {
        if (jj_3R_179()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_180()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_193() {
        if (jj_scan_token(STRING)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_109() {
        if (jj_scan_token(23)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_104() {
        if (jj_3R_135()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_92() {
        if (jj_scan_token(24)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_45()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(25)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_111()) jj_scanpos = xsp;
        else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(26)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(9)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_103() {
        if (jj_3R_134()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_266() {
        if (jj_scan_token(HEXADECIMALINT)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_221() {
        if (jj_3R_225()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_86() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_102()) {
            jj_scanpos = xsp;
            if (jj_3R_103()) {
                jj_scanpos = xsp;
                if (jj_3R_104()) return true;
                if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_102() {
        if (jj_3R_133()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_108() {
        if (jj_scan_token(22)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_265() {
        if (jj_scan_token(DECIMALINT)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_215() {
        Token xsp;
        if (jj_3R_221()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_221()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        if (jj_3R_222()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(9)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_91() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_108()) {
            jj_scanpos = xsp;
            if (jj_3R_109()) return true;
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_48()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_3R_110()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        if (jj_scan_token(9)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_264() {
        if (jj_scan_token(OCTALINT)) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_257() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_264()) {
            jj_scanpos = xsp;
            if (jj_3R_265()) {
                jj_scanpos = xsp;
                if (jj_3R_266()) return true;
                if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_206() {
        if (jj_3R_215()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_175() {
        Token xsp;
        if (jj_3R_206()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_206()) { jj_scanpos = xsp; break; }
            if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        }
        return false;
    }

    final private boolean jj_3R_172() {
        if (jj_3R_199()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_74() {
        if (jj_3R_92()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_73() {
        if (jj_3R_91()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_171() {
        if (jj_3R_185()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_72() {
        if (jj_3R_64()) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    final private boolean jj_3R_61() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_72()) {
            jj_scanpos = xsp;
            if (jj_3R_73()) {
                jj_scanpos = xsp;
                if (jj_3R_74()) return true;
                if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
            } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        } else if (jj_la == 0 && jj_scanpos == jj_lastpos) return false;
        return false;
    }

    public IDLParserTokenManager token_source;
    ASCII_CharStream jj_input_stream;
    public Token token, jj_nt;
    private int jj_ntk;
    private Token jj_scanpos, jj_lastpos;
    private int jj_la;
    public boolean lookingAhead = false;
    private boolean jj_semLA;
    private int jj_gen;
    final private int[] jj_la1 = new int[80];
    final private int[] jj_la1_0 = {0x100c6400,0x10000000,0xc2400,0x100c6400,0x6000,0x2000,0x2000,0x8000,0x10020000,0x10020000,0x10000,0x20000,0x20000,0x2000,0x10020000,0x11c20000,0x80000,0x100000,0x10000,0x8000,0x10000,0x200000,0x11c20000,0xc00000,0x8000000,0x10000,0x0,0x20000,0x40000000,0x80000000,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x2020000,0x0,0x0,0x0,0x20000,0x20000,0x0,0x0,0x0,0x10000,0x0,0x0,0x0,0x0,0x0,0x0,0x20000,0x20000,0x0,0x0,0x0,0x10000,0x10000,0x0,0x0,0x0,0x0,0x10000,0x20000,0x0,0x0,0x0,0x20000,0x10000,0x8000000,0x8000000,0x10000,0x10000,0x20000,0x0,0x0,};
    final private int[] jj_la1_1 = {0x23001800,0x23001800,0x0,0x23001800,0x0,0x0,0x0,0x0,0x23fff800,0x23fff800,0x0,0x0,0x0,0x0,0x23fff800,0x23fff800,0x0,0x0,0x0,0x0,0x0,0x0,0x23fff800,0x0,0x0,0x0,0x1c0000,0x200000,0x0,0x0,0x1,0x6,0x6,0x18,0x18,0xe0,0xe0,0x118,0x118,0x600,0x600,0x600,0x23001800,0x63ffe000,0x40ffe000,0xff8000,0x40000000,0x23000000,0x0,0x0,0xe000,0x38000,0x10000,0x8000,0x20000,0x63ffe000,0x20178000,0x18000000,0x18000000,0x18000000,0x0,0x0,0x80000000,0x80000000,0x0,0x0,0x0,0x63ffe000,0x0,0x0,0x0,0xffe000,0x0,0x0,0x0,0x0,0x0,0xffe000,0x0,0x0,};
    final private int[] jj_la1_2 = {0x80,0x80,0x0,0x80,0x0,0x0,0x0,0x0,0x183e6,0x183e6,0x0,0x0,0x0,0x0,0x183e6,0x183e6,0x0,0x0,0x0,0x0,0x0,0x0,0x183e6,0x0,0x0,0x0,0x0,0x14006,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x7ff0000,0x7fe0000,0x0,0x0,0x1c006,0x1c006,0x8000,0x4006,0x0,0x0,0x10000,0x0,0x0,0x0,0x0,0x0,0x1c006,0x10000,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x8,0x20,0x0,0x1c006,0x100,0x1000,0x2000,0x18206,0x0,0xc00,0xc00,0x0,0x0,0x18006,0xe0000,0x300000,};
    final private JJCalls[] jj_2_rtns = new JJCalls[13];
    private boolean jj_rescan = false;
    private int jj_gc = 0;

    public IDLParser(java.io.InputStream stream) {
        jj_input_stream = new ASCII_CharStream(stream, 1, 1);
        token_source = new IDLParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 80; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    public void ReInit(java.io.InputStream stream) {
        jj_input_stream.ReInit(stream, 1, 1);
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jjtree.reset();
        jj_gen = 0;
        for (int i = 0; i < 80; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    public IDLParser(java.io.Reader stream) {
        jj_input_stream = new ASCII_CharStream(stream, 1, 1);
        token_source = new IDLParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 80; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    public void ReInit(java.io.Reader stream) {
        jj_input_stream.ReInit(stream, 1, 1);
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jjtree.reset();
        jj_gen = 0;
        for (int i = 0; i < 80; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    public IDLParser(IDLParserTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 80; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    public void ReInit(IDLParserTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jjtree.reset();
        jj_gen = 0;
        for (int i = 0; i < 80; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    final private Token jj_consume_token(int kind) throws ParseException {
        Token oldToken;
        if ((oldToken = token).next != null) token = token.next;
        else token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        if (token.kind == kind) {
            jj_gen++;
            if (++jj_gc > 100) {
                jj_gc = 0;
                for (int i = 0; i < jj_2_rtns.length; i++) {
                    JJCalls c = jj_2_rtns[i];
                    while (c != null) {
                        if (c.gen < jj_gen) c.first = null;
                        c = c.next;
                    }
                }
            }
            return token;
        }
        token = oldToken;
        jj_kind = kind;
        throw generateParseException();
    }

    final private boolean jj_scan_token(int kind) {
        if (jj_scanpos == jj_lastpos) {
            jj_la--;
            if (jj_scanpos.next == null) {
                jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
            } else {
                jj_lastpos = jj_scanpos = jj_scanpos.next;
            }
        } else {
            jj_scanpos = jj_scanpos.next;
        }
        if (jj_rescan) {
            int i = 0; Token tok = token;
            while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
            if (tok != null) jj_add_error_token(kind, i);
        }
        return (jj_scanpos.kind != kind);
    }

    final public Token getNextToken() {
        if (token.next != null) token = token.next;
        else token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        jj_gen++;
        return token;
    }

    final public Token getToken(int index) {
        Token t = lookingAhead ? jj_scanpos : token;
        for (int i = 0; i < index; i++) {
            if (t.next != null) t = t.next;
            else t = t.next = token_source.getNextToken();
        }
        return t;
    }

    final private int jj_ntk() {
        if ((jj_nt=token.next) == null)
            return (jj_ntk = (token.next=token_source.getNextToken()).kind);
        else
            return (jj_ntk = jj_nt.kind);
    }

    private java.util.Vector jj_expentries = new java.util.Vector();
    private int[] jj_expentry;
    private int jj_kind = -1;
    private int[] jj_lasttokens = new int[100];
    private int jj_endpos;

    private void jj_add_error_token(int kind, int pos) {
        if (pos >= 100) return;
        if (pos == jj_endpos + 1) {
            jj_lasttokens[jj_endpos++] = kind;
        } else if (jj_endpos != 0) {
            jj_expentry = new int[jj_endpos];
            for (int i = 0; i < jj_endpos; i++) {
                jj_expentry[i] = jj_lasttokens[i];
            }
            boolean exists = false;
            for (java.util.Enumeration enum = jj_expentries.elements(); enum.hasMoreElements();) {
                int[] oldentry = (int[])(enum.nextElement());
                if (oldentry.length == jj_expentry.length) {
                    exists = true;
                    for (int i = 0; i < jj_expentry.length; i++) {
                        if (oldentry[i] != jj_expentry[i]) {
                            exists = false;
                            break;
                        }
                    }
                    if (exists) break;
                }
            }
            if (!exists) jj_expentries.addElement(jj_expentry);
            if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
        }
    }

    final public ParseException generateParseException() {
        jj_expentries.removeAllElements();
        boolean[] la1tokens = new boolean[91];
        for (int i = 0; i < 91; i++) {
            la1tokens[i] = false;
        }
        if (jj_kind >= 0) {
            la1tokens[jj_kind] = true;
            jj_kind = -1;
        }
        for (int i = 0; i < 80; i++) {
            if (jj_la1[i] == jj_gen) {
                for (int j = 0; j < 32; j++) {
                    if ((jj_la1_0[i] & (1<<j)) != 0) {
                        la1tokens[j] = true;
                    }
                    if ((jj_la1_1[i] & (1<<j)) != 0) {
                        la1tokens[32+j] = true;
                    }
                    if ((jj_la1_2[i] & (1<<j)) != 0) {
                        la1tokens[64+j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < 91; i++) {
            if (la1tokens[i]) {
                jj_expentry = new int[1];
                jj_expentry[0] = i;
                jj_expentries.addElement(jj_expentry);
            }
        }
        jj_endpos = 0;
        jj_rescan_token();
        jj_add_error_token(0, 0);
        int[][] exptokseq = new int[jj_expentries.size()][];
        for (int i = 0; i < jj_expentries.size(); i++) {
            exptokseq[i] = (int[])jj_expentries.elementAt(i);
        }
        return new ParseException(token, exptokseq, tokenImage);
    }

    final public void enable_tracing() {
    }

    final public void disable_tracing() {
    }

    final private void jj_rescan_token() {
        jj_rescan = true;
        for (int i = 0; i < 13; i++) {
            JJCalls p = jj_2_rtns[i];
            do {
                if (p.gen > jj_gen) {
                    jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
                    switch (i) {
                    case 0: jj_3_1(); break;
                    case 1: jj_3_2(); break;
                    case 2: jj_3_3(); break;
                    case 3: jj_3_4(); break;
                    case 4: jj_3_5(); break;
                    case 5: jj_3_6(); break;
                    case 6: jj_3_7(); break;
                    case 7: jj_3_8(); break;
                    case 8: jj_3_9(); break;
                    case 9: jj_3_10(); break;
                    case 10: jj_3_11(); break;
                    case 11: jj_3_12(); break;
                    case 12: jj_3_13(); break;
                    }
                }
                p = p.next;
            } while (p != null);
        }
        jj_rescan = false;
    }

    final private void jj_save(int index, int xla) {
        JJCalls p = jj_2_rtns[index];
        while (p.gen > jj_gen) {
            if (p.next == null) { p = p.next = new JJCalls(); break; }
            p = p.next;
        }
        p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
    }

    static final class JJCalls {
        int gen;
        Token first;
        int arg;
        JJCalls next;
    }

}
