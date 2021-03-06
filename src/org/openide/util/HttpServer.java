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

package org.openide.util;

import java.net.URL;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.net.InetAddress;

// import org.openide.modules.IllegalModuleException;
import org.openide.util.NbBundle;
import org.openide.filesystems.FileObject;

/** Maps internal NetBeans resources such as repository objects to URLs.
* The mapping is delegated to an HTTP server module, which registers to do
* the mapping. It is also responsible for actually serving the individual data objects
* from the Repository and resources from the system classpath.
* @author Petr Jiricka
*/
public abstract class HttpServer {

    /** regular server to be used */
    private static HttpServer.Impl registeredServer = null;

    private HttpServer() { }

    /** Returns a server implementation which is currently registered with the system.
    *  'Normal' registered server has priority over a default registered server.
    *  If no server has been registered, internal error is 
    */                            
    private static HttpServer.Impl getServer() throws UnknownHostException {
        if (registeredServer != null)
            return registeredServer;
        else
            throw new UnknownHostException(NbBundle.getBundle(HttpServer.class).getString("MSG_NoServerRegistered"));
    }

    /** Register the system HTTP server.
    * Typically this would be done in {@link org.openide.modules.ModuleInstall#installed}
    * or {@link org.openide.modules.ModuleInstall#restored}.
    * @param server the server to register
    * @throws SecurityException if there was already one registered
    */
    public static void registerServer(HttpServer.Impl server) throws SecurityException {
        if (registeredServer != null)
            throw new SecurityException(NbBundle.getBundle(HttpServer.class).getString("SERVER_REGISTERED"));
        registeredServer = server;
    }

    /** Deregister the system HTTP server.
    * Typically this would be done in {@link org.openide.modules.ModuleInstall#uninstalled}.
    * @param server the server to deregister
    * @throws SecurityException if the specified server was not the installed one
    */
    public static void deregisterServer(HttpServer.Impl server) throws SecurityException {
        if (registeredServer == null)
            return;                   // [PENDING] maybe remove this test and let it throw sec exc --jglick
        if (registeredServer != server)
            throw new SecurityException(NbBundle.getBundle(HttpServer.class).getString("SERVER_CANNOT_UNREGISTER"));
        else
            registeredServer = null;
    }

    /** Map a file object to a URL.
    * Should ensure that the file object is accessible via the given URL.
    * @param fo the file object to represent
    * @return a URL providing access to it
    * @throws MalformedURLException for the usual reasons
    * @throws UnknownHostException for the usual reasons, or if there is no registered server
    */
    public static URL getRepositoryURL(FileObject fo) throws MalformedURLException, UnknownHostException {
        return getServer().getRepositoryURL(fo);
    }

    /** Map the repository root to a URL.
    * This URL should serve a page from which repository objects are accessible.
    * This means that it should serve a package-oriented view of the Repository, corresponding
    * to a merge of all files present in the root folders of visible file systems.
    * @return a URL
    * @throws MalformedURLException for the usual reasons
    * @throws UnknownHostException for the usual reasons, or if there is no registered server
    */
    public static URL getRepositoryRoot() throws MalformedURLException, UnknownHostException {
        return getServer().getRepositoryRoot();
    }

    /** Map a resource path to a URL.
    * Should ensure that the resource is accessible via the given URL.
    * @param resourcePath path of the resource in classloader format (e.g. <code>/some/path/resources/icon32.gif</code>)
    * @return a URL providing access to it
    * @see ClassLoader#getResource(java.lang.String)
    * @see TopManager#systemClassLoader()
    * @throws MalformedURLException for the usual reasons
    * @throws UnknownHostException for the usual reasons, or if there is no registered server
    */
    public static URL getResourceURL(String resourcePath) throws MalformedURLException, UnknownHostException {
        return getServer().getResourceURL(resourcePath);
    }

    /** Get URL root for a resource from system classpath.
    * @param resourcePath the resource path
    * @return the URL
    * @throws MalformedURLException for the usual reasons
    * @throws UnknownHostException for the usual reasons
    * @see HttpServer#getResourceURL
    */
    public static URL getResourceRoot() throws MalformedURLException, UnknownHostException {
        return getServer().getResourceRoot();
    }

    /** Requests the server to allow access to it from a given IP address.
    *  This can be useful if a module wishes another machine to be able to access
    *  the server, such as a machine running a deployment server.
    *  The server may or may not grant access to the IP address, for example
    *  if the user does not wish to grant access to the IP address.
    *  @param addr address for which access is requested
    *  @return <code>true</code> if access has been granted
    */
    public static boolean allowAccess(InetAddress addr) throws UnknownHostException {
        return getServer().allowAccess(addr);
    }

    /** Implementation of the HTTP server.
    * Must be implemented by classes which want to register as a server.
    * <p>Such a server must be prepared to specially serve pages from
    * within the IDE, i.e. the Repository and the system class
    * loader. (It may also serve external pages, if desired.) It should
    * have a system option specifying at least the port number (<em>by
    * default, an unused port above 1000</em>), the host access
    * restrictions (<em>by default, only <code>localhost</code></em>),
    * and an toggle to disable it. It should provide URLs using the
    * protocol <code>http</code> so as not to need to register a new protocol
    * handler.
    */
    public interface Impl {

        /** Get the URL for a file object.
        * @param fo the file object
        * @return the URL
        * @throws MalformedURLException for the usual reasons
        * @throws UnknownHostException for the usual reasons
        * @see HttpServer#getRepositoryURL
        */
        public URL getRepositoryURL(FileObject fo) throws MalformedURLException, UnknownHostException;

        /** Get the URL for the Repository. For this URL,
        * the implementation should display a page containing a list of links to subdirectories (packages).
        * @return the URL
        * @throws MalformedURLException for the usual reasons
        * @throws UnknownHostException for the usual reasons
        * @see HttpServer#getRepositoryRoot
        */
        public URL getRepositoryRoot() throws MalformedURLException, UnknownHostException;

        /** Get the URL for a resource from system classpath. The URL must comply to java naming conventions,
        * i.e. the URL must end with a fully qualified resource name.
        * @param resourcePath the resource path
        * @return the URL
        * @throws MalformedURLException for the usual reasons
        * @throws UnknownHostException for the usual reasons
        * @see HttpServer#getResourceURL
        */
        public URL getResourceURL(String resourcePath) throws MalformedURLException, UnknownHostException;

        /** Get URL root for a resource from system classpath.
        * @param resourcePath the resource path
        * @return the URL
        * @throws MalformedURLException for the usual reasons
        * @throws UnknownHostException for the usual reasons
        * @see HttpServer#getResourceURL
        */
        public URL getResourceRoot() throws MalformedURLException, UnknownHostException;

        /** Requests the server to allow access to it from a given IP address.
        *  This can be useful if a module wishes another machine to be able to access
        *  the server, such as a machine running a deployment server.
        *  The server may or may not grant access to the IP address, for example
        *  if the user does not wish to grant access to the IP address.
        *  @param addr address for which access is requested
        *  @return <code>true</code> if access has been granted
        */
        public boolean allowAccess(InetAddress addr) throws UnknownHostException;
    }

}
/*
 * Log
 *  10   Gandalf   1.9         1/12/00  Pavel Buzek     I18N
 *  9    Gandalf   1.8         10/22/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  8    Gandalf   1.7         6/24/99  Petr Jiricka    Added method for 
 *       allowina access to an IP address
 *  7    Gandalf   1.6         6/8/99   Ian Formanek    ---- Package Change To 
 *       org.openide ----
 *  6    Gandalf   1.5         5/31/99  Petr Jiricka    
 *  5    Gandalf   1.4         5/15/99  Jesse Glick     [JavaDoc]
 *  4    Gandalf   1.3         5/15/99  Jesse Glick     [JavaDoc], and private 
 *       constructor for stylistic reasons.
 *  3    Gandalf   1.2         5/11/99  Petr Jiricka    
 *  2    Gandalf   1.1         5/11/99  Petr Jiricka    
 *  1    Gandalf   1.0         5/10/99  Petr Jiricka    
 * $
 */
