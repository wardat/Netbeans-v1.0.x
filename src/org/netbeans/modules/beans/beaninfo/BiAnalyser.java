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

package org.netbeans.modules.beans.beaninfo;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.ResourceBundle;

import org.openide.src.MethodElement;
import org.openide.src.ClassElement;
import org.openide.src.Type;
import org.openide.nodes.Node;
import org.openide.TopManager;
import org.openide.NotifyDescriptor;
import org.openide.util.NbBundle;

import org.netbeans.modules.beans.PatternAnalyser;
import org.netbeans.modules.beans.Pattern;
import org.netbeans.modules.beans.PropertyPattern;
import org.netbeans.modules.beans.IdxPropertyPattern;
import org.netbeans.modules.beans.EventSetPattern;

/** Analyses the ClassElement trying to find source code patterns i.e.
 * properties or event sets;
 *
 * @author Petr Hrebejk
 */

public class BiAnalyser extends Object implements Node.Cookie {

    private static final ResourceBundle bundle = NbBundle.getBundle( BiAnalyser.class );

    private static final String TAB = "  "; // NOI18N
    private static final String TABx2 = TAB +TAB;
    private static final String TABx3 = TAB + TABx2;

    private static final String ICONNAME_C16 = "iconNameC16"; // NOI18N
    private static final String ICONNAME_C32 = "iconNameC32"; // NOI18N
    private static final String ICONNAME_M16 = "iconNameM16"; // NOI18N
    private static final String ICONNAME_M32 =  "iconNameM32"; // NOI18N

    private static final String DEFAULT_PROPERTY_INDEX = "defaultPropertyIndex"; // NOI18N
    private static final String DEFAULT_EVENT_INDEX = "defaultEventIndex"; // NOI18N

    /** Holds all properties 
     * @associates Property*/
    List properties;

    /** Holds all indexed properties 
     * @associates IdxProperty*/
    List idxProperties;

    /** Holds all events sets 
     * @associates EventSet*/
    List eventSets;

    /** Holds all methods */
    List methods;

    /** Object representing source code of associated BeanInfo */
    BeanInfoSource bis;

    /** Should properties be obtained from introspection */
    private boolean nullProperties = false;

    /** Should event sets be obtained from introspection */
    private boolean nullEventSets = false;

    /* Holds the class for which the bean info is generated */
    ClassElement classElement;

    private String iconC16;
    private String iconM16;
    private String iconC32;
    private String iconM32;
    private int defaultPropertyIndex = -1;
    private int defaultEventIndex = -1;
    /** Creates Bean Info analyser which contains all patterns from PatternAnalyser
    */
    BiAnalyser ( PatternAnalyser pa, ClassElement classElement ) {
        Collection col;
        Iterator it;

        this.classElement = classElement;

        // Fill properties list

        col = pa.getPropertyPatterns();
        properties = new ArrayList( col.size() );
        it = col.iterator();
        while( it.hasNext() ) {
            PropertyPattern pp = (PropertyPattern)it.next();
            //if ( pp.isPublic() )
            properties.add( new BiFeature.Property( pp ) );
        }

        // Fill indexed properties list

        col = pa.getIdxPropertyPatterns();
        idxProperties = new ArrayList( col.size() );
        it = col.iterator();
        while( it.hasNext() ) {
            IdxPropertyPattern ipp = (IdxPropertyPattern)it.next();
            //if ( ipp.isPublic() )


            if ( ipp.getType() != null && ( !ipp.getType().isArray() ||
                                            !ipp.getType().getElementType().equals( ipp.getIndexedType() ) ) ) {
                continue;
            }


            idxProperties.add( new BiFeature.IdxProperty( ipp ) );
        }

        // Fill event sets list

        col = pa.getEventSetPatterns();
        eventSets = new ArrayList( col.size() );
        it = col.iterator();
        while( it.hasNext() ) {
            EventSetPattern esp = (EventSetPattern)it.next();
            //if ( esp.isPublic() )
            eventSets.add( new BiFeature.EventSet( esp ) );
        }

        // Try to find and analyse existing bean info
        bis = new BeanInfoSource( classElement );
        analyzeBeanInfoSource( );

    }

    Collection getProperties() {
        return properties;
    }

    Collection getIdxProperties() {
        return idxProperties;
    }

    Collection getEventSets() {
        return eventSets;
    }

    Collection getMethods() {
        return methods;
    }

    public String getIconC16() {
        return iconC16;
    }

    public void setIconC16(String iconC16) {
        this.iconC16 = iconC16;
    }

    public String getIconM16() {
        return iconM16;
    }

    public void setIconM16(String iconM16) {
        this.iconM16 = iconM16;
    }

    public String getIconC32() {
        return iconC32;
    }

    public void setIconC32(String iconC32) {
        this.iconC32 = iconC32;
    }

    public String getIconM32() {
        return iconM32;
    }

    public void setIconM32(String iconM32) {
        this.iconM32 = iconM32;
    }

    public int getDefaultPropertyIndex() {
        return defaultPropertyIndex;
    }

    public void setDefaultPropertyIndex(int defaultPropertyIndex) {
        this.defaultPropertyIndex = defaultPropertyIndex;
    }

    public int getDefaultEventIndex() {
        return defaultEventIndex;
    }

    public void setDefaultEventIndex(int defaultEventIndex) {
        this.defaultEventIndex = defaultEventIndex;
    }

    boolean isNullProperties() {
        return nullProperties;
    }

    void setNullProperties( boolean nullProperties ) {
        this.nullProperties = nullProperties;
    }

    boolean isNullEventSets() {
        return nullEventSets;
    }

    void setNullEventSets( boolean nullEventSets ) {
        this.nullEventSets = nullEventSets;
    }


    void regenerateSource() {

        if ( bis.exists() ) {
            if ( !bis.isNbBeanInfo() ) {
                String mssg = NbBundle.getBundle(BiAnalyser.class).getString( "MSG_BeanInfoExists" );
                NotifyDescriptor nd = new NotifyDescriptor.Confirmation ( mssg, NotifyDescriptor.YES_NO_OPTION );
                TopManager.getDefault().notify( nd );
                if ( !nd.getValue().equals ( NotifyDescriptor.YES_OPTION ) ) {
                    return;
                }

                try {
                    bis.delete();
                }
                catch ( java.io.IOException e ) {
                    mssg = NbBundle.getBundle(BiAnalyser.class).getString( "MSG_BeanInfoCantDelete" );
                    nd = new NotifyDescriptor.Message ( mssg );
                    TopManager.getDefault().notify( nd );
                    return;
                }
                bis.createFromTemplate();
            }
        }
        else {
            bis.createFromTemplate();

            if ( !bis.isNbBeanInfo() ) {
                return;
            }

        }

        javax.swing.SwingUtilities.invokeLater( new Runnable() {
                                                    public void run() {
                                                        bis.open();
                                                        regenerateProperties();
                                                        regenerateEvents();
                                                        regenerateIcons();
                                                        regenerateDefaultIdx();
                                                    }
                                                } );
    }

    /** Regenerates the property section of BeanInfo */
    private void regenerateProperties() {
        StringBuffer sb = new StringBuffer( 512 );
        int propertyCount = 0;


        if ( nullProperties ) {
            sb.append( TAB + bundle.getString( "COMMENT_NullProperties" ) );
            sb.append( TAB + "private static PropertyDescriptor[] properties = null;\n" ); // NOI18N
            bis.setPropertiesSection( sb.toString(), "  \n" ); // NOI18N
            return;
        }

        // Make common list of all properites
        ArrayList allProperties = new ArrayList( getProperties().size() + getIdxProperties().size() );
        allProperties.addAll( getProperties() );
        allProperties.addAll( getIdxProperties() );

        sb.append( TAB + bundle.getString( "COMMENT_PropertyIdentifiers" ) );

        Iterator it = allProperties.iterator();
        while ( it.hasNext() ) {
            BiFeature bif = ( BiFeature )it.next();

            if ( bif.isIncluded() ) {
                sb.append( TAB + "private static final int " ); // NOI18N
                sb.append( "PROPERTY_" + bif.getName() ); // NOI18N
                sb.append( " = " + (propertyCount++) + ";" ); // NOI18N
                sb.append( "\n" ); // NOI18N
            }
        }

        sb.append( "\n" + TAB + bundle.getString("COMMENT_PropertyArray" ));
        sb.append( TAB + "private static PropertyDescriptor[] properties = new PropertyDescriptor[" + // NOI18N
                   propertyCount + "];\n\n" ); // NOI18N

        if ( propertyCount > 0)
            sb.append( TAB + "static {\n" + TABx2 + "try {\n" ); // NOI18N

        it = allProperties.iterator();
        for ( int i = 0; it.hasNext(); i++ ) {
            BiFeature bif = ( BiFeature )it.next();

            if ( bif.isIncluded() ) {
                sb.append( TABx3 + "properties[PROPERTY_" ).append( bif.getName() ).append("] = "); // NOI18N
                sb.append( bif.getCreationString() ).append(";\n"); // NOI18N

                Collection cs = bif.getCustomizationStrings();
                Iterator csit = cs.iterator();
                while( csit.hasNext() ) {
                    sb.append(  TABx3 + "properties[PROPERTY_" ).append( bif.getName() ).append("]."); // NOI18N
                    sb.append( (String)csit.next() ).append( ";\n" ); // NOI18N
                }
            }
        }

        if ( propertyCount > 0 )
            sb.append( TABx2 + "}\n" +  TABx2 + "catch( IntrospectionException e) {}" ); // NOI18N


        bis.setPropertiesSection( sb.toString(), propertyCount > 0 ? "}\n" : "  \n" ); // NOI18N
    }

    /** Regenerates the event set section of BeanInfo */
    private void regenerateEvents() {
        StringBuffer sb = new StringBuffer( 512 );
        int eventCount = 0;

        if ( nullEventSets ) {
            sb.append( TAB + bundle.getString( "COMMENT_NullEventSets" ) );
            sb.append( TAB + "private static EventSetDescriptor[] eventSets = null;\n" ); // NOI18N
            bis.setEventSetsSection( sb.toString(), "  \n" ); // NOI18N
            return;
        }

        sb.append( TAB + bundle.getString("COMMENT_EventSetsIdentifiers") );


        Iterator it = eventSets.iterator();
        while ( it.hasNext() ) {
            BiFeature bif = ( BiFeature )it.next();

            if ( bif.isIncluded() ) {
                sb.append( TAB + "private static final int " ); // NOI18N
                sb.append( "EVENT_" + bif.getName() ); // NOI18N
                sb.append( " = " + (eventCount++) + ";" ); // NOI18N
                sb.append( "\n" ); // NOI18N
            }
        }

        sb.append( "\n" + TAB + bundle.getString("COMMENT_EventSetsArray"));
        sb.append( TAB + "private static EventSetDescriptor[] eventSets = new EventSetDescriptor[" // NOI18N
                   + eventCount + "];\n\n" ); // NOI18N

        if ( eventCount > 0 )
            sb.append( TAB + "static {\n" + TABx2 + "try {\n" ); // NOI18N

        it = eventSets.iterator();
        for ( int i = 0; it.hasNext(); i++ ) {
            BiFeature bif = ( BiFeature )it.next();
            if ( bif.isIncluded() ) {
                sb.append( TABx3 + "eventSets[EVENT_" ).append( bif.getName() ).append("] = "); // NOI18N
                sb.append( bif.getCreationString() ).append(";\n"); // NOI18N

                Collection cs = bif.getCustomizationStrings();
                Iterator csit = cs.iterator();
                while( csit.hasNext() ) {
                    sb.append(  TABx3 + "eventSets[EVENT_" ).append( bif.getName() ).append("]."); // NOI18N
                    sb.append( (String)csit.next() ).append( ";\n" ); // NOI18N
                }
            }
        }

        if ( eventCount > 0 )
            sb.append( TABx2 + "}\n" +  TABx2 + "catch( IntrospectionException e) {}" ); // NOI18N

        bis.setEventSetsSection( sb.toString(), eventCount > 0 ? "}\n" : "  \n"); // NOI18N
    }

    /** Generate image icon section */
    private void regenerateIcons() {
        StringBuffer sb = new StringBuffer( 200 );

        sb.append( getIconDeclaration( ICONNAME_C16, iconC16 ));
        sb.append( getIconDeclaration( ICONNAME_C32, iconC32 ));
        sb.append( getIconDeclaration( ICONNAME_M16, iconM16 ));
        sb.append( getIconDeclaration( ICONNAME_M32, iconM32 ));

        bis.setIconsSection( sb.toString() );
    }

    private static String getIconDeclaration( String name, String resource ) {
        StringBuffer sb = new StringBuffer( 80 );

        sb.append( TAB + "private static String " ).append( name ).append( " = "); // NOI18N
        if ( resource == null || resource.trim().length() == 0 )
            sb.append( "null;\n"); // NOI18N
        else
            sb.append("\"").append( resource.trim() ).append("\";\n"); // NOI18N
        return sb.toString();
    }

    private void regenerateDefaultIdx() {
        StringBuffer sb = new StringBuffer(100);

        sb.append( TAB + "private static int " + DEFAULT_PROPERTY_INDEX + " = ").append( defaultPropertyIndex ).append( ";\n"); // NOI18N
        sb.append( TAB + "private static int " + DEFAULT_EVENT_INDEX + " = ").append( defaultEventIndex ).append( ";\n"); // NOI18N

        bis.setDefaultIdxSection( sb.toString() );
    }

    /** Analyzes existing BeanInfo */
    private void analyzeBeanInfoSource() {

        if ( !bis.isNbBeanInfo() )
            return;


        String section = bis.getIconsSection();
        Collection code = normalizeText( section );
        setIconsFromBeanInfo( code );

        section = bis.getDefaultIdxSection();
        code = normalizeText( section );
        setDefaultIdxFromBeanInfo( code );


        section = bis.getPropertiesSection();
        code = normalizeText( section );
        nullProperties = setPropertiesFromBeanInfo( properties, code, "PropertyDescriptor[]" ); // NOI18N
        if ( !nullProperties )
            setPropertiesFromBeanInfo( idxProperties, code, "PropertyDescriptor[]" ); // NOI18N


        section = bis.getEventSetsSection();
        code = normalizeText( section );
        nullEventSets = setPropertiesFromBeanInfo( eventSets, code, "EventSetDescriptor[]" ); // NOI18N

    }

    /** "Normalizes" the JavaCode. Removes all unneeded whitespaces. Makes strings from
     * commands. 
     * @param code String containg the java source code
     * @returns Normalized code as collection of string.
     */

    static Collection normalizeText( String code ) {

        ArrayList result = new ArrayList();
        StringBuffer sb = new StringBuffer( 100 );

        final int IN_TEXT = 0;
        final int IN_WHITE = 1;
        int mode = IN_WHITE;
        boolean eo_javaid = false;

        for ( int i = 0; i < code.length(); i++ ) {
            char ch = code.charAt( i );

            switch ( mode ) {
            case IN_TEXT:
                if ( !Character.isWhitespace( ch ) ) {
                    if ( ch == ';' ) {
                        sb.append( ch );
                        result.add( sb.toString() );
                        sb.setLength( 0 );
                        mode = IN_WHITE;
                        eo_javaid = false;
                    }
                    else
                        sb.append( ch );
                }
                else {
                    eo_javaid = Character.isJavaIdentifierPart ( code.charAt( i - 1 ) );
                    mode = IN_WHITE;
                }
                break;
            case IN_WHITE:
                if ( !Character.isWhitespace( ch ) ) {
                    if ( eo_javaid && Character.isJavaIdentifierStart ( ch ) )
                        sb.append( ' ' );
                    sb.append( ch );
                    mode = IN_TEXT;
                }
                break;
            }
        }
        return result;

    }

    static String[] getParameters( String command ) {
        //ArrayList result = new ArrayList();
        String paramString;

        int beg = command.lastIndexOf( '(' );
        int end = command.lastIndexOf( ')' );

        if ( beg != -1 && end != -1 && ( ++beg < end ) )
            paramString = command.substring( beg, end );
        else
            return new String[0];

        StringTokenizer strTok = new StringTokenizer( paramString, ",)" ); // NOI18N

        String[] resultStrs = new String[ strTok.countTokens() ];

        for ( int i = 0; strTok.hasMoreTokens(); i++ )
            resultStrs[i] = strTok.nextToken();


        return resultStrs;
    }

    /** Gets the initializer */
    static String getInitializer( String command ) {

        int beg = command.lastIndexOf( '=' );
        int end = command.lastIndexOf( ';' );

        if ( beg != -1 && end != -1 && ( ++beg < end ) )
            return command.substring( beg, end ).trim();
        else
            return null;
    }

    /** Removes Quotation marks */
    static String removeQuotation( String text ) {

        int beg = text.indexOf( '"' );
        int end = text.lastIndexOf( '"' );

        if ( beg != -1 && end != -1 && ( ++beg < end ) )
            return text.substring( beg, end );
        else
            return null;
    }


    /** Let's the collection of features check for it's properties in BeanInfo */
    boolean setPropertiesFromBeanInfo( Collection features, Collection code, String name ) {

        Iterator it = code.iterator();

        while( it.hasNext() ) {
            String statement = (String)it.next();
            if ( statement.indexOf( name ) != -1 )
                if ( getInitializer( statement ).equals( "null" )  ) // NOI18N
                    return true;
                else
                    break;
        }

        it = features.iterator();

        while( it.hasNext() ) {
            ((BiFeature) it.next()).analyzeCustomization( code );
        }

        return false;
    }

    /** Analyze icons properties from bean info */

    void setIconsFromBeanInfo ( Collection code ) {


        Iterator it = code.iterator();
        while( it.hasNext() ) {
            String statement = ( String ) it.next();

            if ( statement.indexOf( ICONNAME_C16 ) != -1 ) {
                iconC16 = removeQuotation( getInitializer( statement ) );
                continue;
            }
            if ( statement.indexOf( ICONNAME_C32 ) != -1 ) {
                iconC32 = removeQuotation( getInitializer( statement ) );
                continue;
            }
            if ( statement.indexOf( ICONNAME_M16 ) != -1 ) {
                iconM16 = removeQuotation( getInitializer( statement ) );
                continue;
            }
            if ( statement.indexOf( ICONNAME_M32 ) != -1 ) {
                iconM32 = removeQuotation( getInitializer( statement ) );
                continue;
            }
        }
    }


    /** Analyze default section  */

    void setDefaultIdxFromBeanInfo( Collection code ) {
        Iterator it = code.iterator();
        while( it.hasNext() ) {
            String statement = ( String ) it.next();

            if ( statement.indexOf( DEFAULT_PROPERTY_INDEX ) != -1 ) {
                try {
                    defaultPropertyIndex = Integer.parseInt( getInitializer( statement ) );
                }
                catch ( java.lang.NumberFormatException e ) {
                    defaultPropertyIndex = -1;
                }

                continue;
            }
            if ( statement.indexOf( DEFAULT_EVENT_INDEX ) != -1 ) {
                try {
                    defaultEventIndex = Integer.parseInt( getInitializer( statement ) );
                }
                catch ( java.lang.NumberFormatException e ) {
                    defaultEventIndex = -1;
                }

                continue;
            }

        }
    }

}
/*
 * Log
 *  11   Gandalf   1.10        1/13/00  Petr Hrebejk    i18n mk3
 *  10   Gandalf   1.9         1/12/00  Petr Hrebejk    i18n  
 *  9    Gandalf   1.8         10/22/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  8    Gandalf   1.7         10/11/99 Petr Hrebejk    JDK 
 *       IndexedPropertyDescriptor bug fix
 *  7    Gandalf   1.6         8/18/99  Petr Hrebejk    BeanInfo analyse moved 
 *       to separate thread
 *  6    Gandalf   1.5         8/9/99   Petr Hrebejk    BeanInfo for no 
 *       propertes & no events selected
 *  5    Gandalf   1.4         8/5/99   Petr Hrebejk    BeanInfo for Beans with 
 *       no Properties or no EventSets fixed
 *  4    Gandalf   1.3         7/29/99  Petr Hrebejk    Patterns in BeanInfo 
 *       show correctly only public fields and methods
 *  3    Gandalf   1.2         7/28/99  Petr Hrebejk    Property Mode change fix
 *  2    Gandalf   1.1         7/26/99  Petr Hrebejk    BeanInfo fix & Code 
 *       generation fix
 *  1    Gandalf   1.0         7/26/99  Petr Hrebejk    
 * $ 
 */ 