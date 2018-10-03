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

package org.netbeans.modules.clazz;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Collections;
import java.lang.ref.SoftReference;
import java.lang.reflect.*;

import org.openide.src.*;

/** The implementation of the class element for
* class objects. Presents data about the class -
* uses java reflection to obtain needed information.
*
* @author Dafe Simonek
*/
final class ClassElementImpl extends MemberElementImpl
    implements ClassElement.Impl {
    /** Empty array of initializers - constant to return from getInitializers() */
    private static final InitializerElement[] EMPTY_INITIALIZERS =
        new InitializerElement[0];
    private static final Object[] EMPTY_ARRAY = new Object[0];

    /** Identifier of superclass of this class element,
    * can be empty */
    private Identifier superClass;
    /** Array of identifiers for interfaces */
    private Identifier[] interfaces;

    /** Fields of this class element */
    private SoftReference fields;
    /** Inner classes of this class element */
    private SoftReference inners;
    /** Contructors of this class element */
    private SoftReference constructors;
    /** Methods of this class element */
    private SoftReference methods;

    /** One JavaDoc empty implementation for all objects */
    private static final ClassJavaDocImpl.Class CLASS_JAVADOC_IMPL = new ClassJavaDocImpl.Class ();

    static final long serialVersionUID =-8717988834353784544L;
    /** Default constructor.
    */
    public ClassElementImpl (final Class data) {
        super(data);
    }

    /** Not supported. Throws source exception */
    public void setSuperclass(Identifier superClass) throws SourceException {
        throw new SourceException();
    }

    public Identifier getSuperclass() {
        if (superClass == null) {
            Class sc = ((Class)data).getSuperclass();
            superClass = Identifier.create(sc == null ? "" : sc.getName()); // NOI18N
        }
        return superClass;
    }

    /** Not supported. Throws Source Exception */
    public void setClassOrInterface(boolean isClass) throws SourceException {
        throw new SourceException();
    }

    public boolean isClassOrInterface() {
        return !((Class)data).isInterface();
    }

    /** Not supported. Throws SourceException.
    */
    public void changeInitializers (InitializerElement[] elems, int action) throws SourceException {
        throw new SourceException();
    }

    /** Not available. Always returns empty array */
    public InitializerElement[] getInitializers () {
        return EMPTY_INITIALIZERS;
    }

    /** Changes set of elements.
    * @param elems elements to change
    * @exception SourceException if the action cannot be handled
    */
    public void changeFields (FieldElement[] elems, int action) throws SourceException {
        throw new SourceException();
    }

    public FieldElement[] getFields () {
        Map fieldsMap = (fields == null) ? null : (Map)fields.get();
        if (fieldsMap == null) {
            // soft ref null, we must recreate
            fieldsMap = createFieldsMap();
            fields = new SoftReference(fieldsMap);
        }
        return (FieldElement[])fieldsMap.values().toArray(new FieldElement[0]);
    }

    /** Finds a field with given name.
    * @param name the name of field to look for
    * @return the element or null if field with such name does not exist
    */
    public FieldElement getField (Identifier name) {
        Map fieldsMap = (fields == null) ? null : (Map)fields.get();
        if (fieldsMap == null) {
            // soft ref null, we must recreate
            fieldsMap = createFieldsMap();
            fields = new SoftReference(fieldsMap);
        }
        return (FieldElement)fieldsMap.get(name);
    }

    /** Changes set of elements.
    * @param elems elements to change
    * @exception SourceException if the action cannot be handled
    */
    public void changeMethods (MethodElement[] elems, int action) throws SourceException {
        throw new SourceException();
    }

    public MethodElement[] getMethods () {
        Map methodsMap = (methods == null) ? null : (Map)methods.get();
        if (methodsMap == null) {
            // soft ref null, we must recreate
            methodsMap = createMethodsMap();
            methods = new SoftReference(methodsMap);
        }
        return (MethodElement[])methodsMap.values().toArray(new MethodElement[0]);
    }

    /** Finds a method with given name and argument types.
    * @param name the name of field to look for
    * @param arguments for the method
    * @return the element or null if such method does not exist
    */
    public MethodElement getMethod (Identifier name, Type[] arguments) {
        Map methodsMap = (methods == null) ? null : (Map)methods.get();
        if (methodsMap == null) {
            // soft ref null, we must recreate
            methodsMap = createMethodsMap();
            methods = new SoftReference(methodsMap);
        }
        return (MethodElement)
               methodsMap.get(new MethodElement.Key(name, arguments));
    }

    /** Changes set of elements.
    * @param elems elements to change
    * @exception SourceException if the action cannot be handled
    */
    public void changeConstructors (ConstructorElement[] elems, int action) throws SourceException {
        throw new SourceException();
    }

    public ConstructorElement[] getConstructors () {
        Map constructorsMap =
            (constructors == null) ? null :(Map)constructors.get();
        if (constructorsMap == null) {
            // soft ref null, we must recreate
            constructorsMap = createConstructorsMap();
            constructors = new SoftReference(constructorsMap);
        }
        return (ConstructorElement[])constructorsMap.values().
               toArray(new ConstructorElement[0]);
    }

    /** Finds a constructor with argument types.
    * @param arguments for the method
    * @return the element or null if such method does not exist
    */
    public ConstructorElement getConstructor (Type[] arguments) {
        Map constructorsMap =
            (constructors == null) ? null :(Map)constructors.get();
        if (constructorsMap == null) {
            // soft ref null, we must recreate
            constructorsMap = createConstructorsMap();
            constructors = new SoftReference(constructorsMap);
        }
        return (ConstructorElement)
               constructorsMap.get(new ConstructorElement.Key(arguments));
    }

    /** Changes set of elements.
    * @param elems elements to change
    * @exception SourceException if the action cannot be handled
    */
    public void changeClasses (ClassElement[] elems, int action) throws SourceException {
        throw new SourceException();
    }

    public ClassElement[] getClasses () {
        Map innersMap = (inners == null) ? null : (Map)inners.get();
        if (innersMap == null) {
            // soft ref null, we must recreate
            innersMap = createInnersMap();
            inners = new SoftReference(innersMap);
        }
        return (ClassElement[])innersMap.values().toArray(new ClassElement[0]);
    }

    /** Finds an inner class with given name.
    * @param name the name to look for
    * @return the element or null if such class does not exist
    */
    public ClassElement getClass (Identifier name) {
        Map innersMap = (inners == null) ? null : (Map)inners.get();
        if (innersMap == null) {
            // soft ref null, we must recreate
            innersMap = createInnersMap();
            inners = new SoftReference(innersMap);
        }
        return (ClassElement)innersMap.get(name);
    }

    /** Changes interfaces this class implements (or extends).
    * @param ids identifiers to change
    * @exception SourceException if the action cannot be handled
    */
    public void changeInterfaces (Identifier[] ids, int action) throws SourceException {
        throw new SourceException();
    }

    /** @return all interfaces which the class implements or interface extends.
    */
    public Identifier[] getInterfaces () {
        if (interfaces == null) {
            // create identifier array for interfaces
            Class[] reflIntfs = ((Class)data).getInterfaces();
            interfaces = new Identifier[reflIntfs.length];
            for (int i = 0; i < reflIntfs.length; i++) {
                interfaces[i] = Identifier.create(reflIntfs[i].getName());
            }
        }
        return interfaces;
    }

    /** @return class documentation.
    */
    public JavaDoc.Class getJavaDoc() {
        return CLASS_JAVADOC_IMPL;
    }

    /******** non public methods ***********/

    /** Creates map for fields consisting of identifier - field entries */
    private Map createFieldsMap () {
        // obtain field array
        Field[] reflFields = null;
        try {
            reflFields = ((Class)data).getDeclaredFields();
        } catch (Throwable exc) {
            // rethrow only ThreadDeath, ignore otherwise
            if (exc instanceof ThreadDeath)
                throw (ThreadDeath)exc;
            reflFields = new Field[0];
        }
        // create map
        FieldElement curFE = null;
        Map result = new HashMap(reflFields.length);
        for (int i = 0; i < reflFields.length; i++) {
            // filter out methods added by compiler
            if (!addedByCompiler(reflFields[i])) {
                curFE = new FieldElement(new FieldElementImpl(reflFields[i]),
                                         (ClassElement)element);
                result.put(curFE.getName(), curFE);
            }
        }
        return result;
    }

    /** Creates map for inner classes of this class,
    * consisting of identifier - class element entries */
    private Map createInnersMap () {
        // obtain array of interfaces and inner classes
        Class[] reflInners = null;
        try {
            reflInners = ((Class)data).getDeclaredClasses();
        } catch (Throwable exc) {
            // rethrow only ThreadDeath, ignore otherwise
            if (exc instanceof ThreadDeath)
                throw (ThreadDeath)exc;
            reflInners = new Class[0];
        }
        // create map
        ClassElement curCE = null;
        Map result = new HashMap(reflInners.length);
        for (int i = 0; i < reflInners.length; i++) {
            curCE = new ClassElement(new ClassElementImpl(reflInners[i]),
                                     (ClassElement)element);
            result.put(curCE.getName(), curCE);
        }
        return result;
    }

    /** Creates map for constructors of this class,
    * consisting of constructor key - constructor element entries */
    private Map createConstructorsMap () {
        // obtain constructors array
        Constructor[] reflCons = null;
        try {
            reflCons = ((Class)data).getDeclaredConstructors();
        } catch (Throwable exc) {
            // rethrow only ThreadDeath, ignore otherwise
            if (exc instanceof ThreadDeath)
                throw (ThreadDeath)exc;
            reflCons = new Constructor[0];
        }
        // create map
        ConstructorElement curCE = null;
        Map result = new HashMap(reflCons.length);
        for (int i = 0; i < reflCons.length; i++) {
            curCE = new ConstructorElement(new ConstructorElementImpl(reflCons[i]),
                                           (ClassElement)element);
            result.put(new ConstructorElement.Key(curCE), curCE);
        }
        return result;
    }

    /** Creates map for methods of this class,
    * consisting of method key - method element entries */
    private Map createMethodsMap () {
        // obtain methods array
        Method[] reflMethods = null;
        try {
            reflMethods = ((Class)data).getDeclaredMethods();
        } catch (Throwable exc) {
            // rethrow only ThreadDeath, ignore otherwise
            if (exc instanceof ThreadDeath)
                throw (ThreadDeath)exc;
            reflMethods = new Method[0];
        }
        // create map
        MethodElement curME = null;
        Map result = new HashMap(reflMethods.length);
        for (int i = 0; i < reflMethods.length; i++) {
            // filter out methods added by compiler
            if (!addedByCompiler(reflMethods[i])) {
                curME = new MethodElement(new MethodElementImpl(reflMethods[i]),
                                          (ClassElement)element);
                result.put(new MethodElement.Key(curME), curME);
            }
        }
        return result;
    }

    public Object readResolve() {
        return new ClassElement(this, (SourceElement)null);
    }

    /** @return true if given member was generated automatically by compiler,
    * false otherwise. Decision is made by inspecting the name of the member.
    */
    private static boolean addedByCompiler (Member member) {
        String name = member.getName();
        return name.indexOf('$') >= 0;
    }

}

/*
* Log
*  14   src-jtulach1.13        1/13/00  David Simonek   i18n
*  13   src-jtulach1.12        1/5/00   David Simonek   #2642
*  12   src-jtulach1.11        10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun 
*       Microsystems Copyright in File Comment
*  11   src-jtulach1.10        8/9/99   Ian Formanek    Generated Serial Version 
*       UID
*  10   src-jtulach1.9         6/9/99   Petr Hrebejk    Empty JavaDoc 
*       implementation added.
*  9    src-jtulach1.8         6/9/99   Ian Formanek    ---- Package Change To 
*       org.openide ----
*  8    src-jtulach1.7         5/12/99  Petr Hamernik   ide.src.Identifier 
*       updated
*  7    src-jtulach1.6         3/31/99  David Simonek   setClassOrInterface now 
*       throws SourceException
*  6    src-jtulach1.5         3/26/99  David Simonek   properties, actions 
*       completed, more robust now
*  5    src-jtulach1.4         2/17/99  Petr Hamernik   serialization changed.
*  4    src-jtulach1.3         2/10/99  David Simonek   
*  3    src-jtulach1.2         2/3/99   David Simonek   
*  2    src-jtulach1.1         1/29/99  David Simonek   
*  1    src-jtulach1.0         1/22/99  David Simonek   
* $
*/
