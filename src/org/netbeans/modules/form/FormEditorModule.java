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

package org.netbeans.modules.form;

import org.openide.TopManager;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.*;
import org.openide.loaders.*;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.openide.modules.ModuleInstall;

import org.openidex.util.Utilities2;
import org.netbeans.modules.form.actions.*;
import org.netbeans.modules.form.palette.*;

import java.beans.*;
import java.io.File;

/**
* Module installation class for Form Editor
*
* @author Ian Formanek
*/
public class FormEditorModule extends ModuleInstall {

    static final long serialVersionUID =1573432625099425394L;
    /** Module installed for the first time. */
    public void installed () {
        // ---------------------------------------------------------------------------
        // 1. create FormEditor actions
        installActions ();

        // ---------------------------------------------------------------------------
        // 2. copy FormEditor templates
        copyTemplates ();

        // ---------------------------------------------------------------------------
        // 3. create Component Palette under system
        createComponentPalette ();

        restored ();
    }

    // XXX(-tdt) hack around failure of loading TimerBean caused by package
    // renaming com.netbeans => org.netbeans AND the need to preserve user's
    // system settings
    
    private static void timerBeanHack() {
        TopManager.getDefault().getRepository().addRepositoryListener(
            new RepositoryListener() {
                public void fileSystemRemoved (RepositoryEvent ev) {}
                public void fileSystemPoolReordered(RepositoryReorderedEvent ev) {}

                public void fileSystemAdded (RepositoryEvent ev) {
                    FileSystem fs = ev.getFileSystem();
                    if (! (fs instanceof JarFileSystem))
                        return;
                    JarFileSystem jarfs = (JarFileSystem) fs;

                    try {
                        String jarpath = jarfs.getJarFile().getCanonicalPath();
                        if (! jarpath.endsWith(File.separator + "beans"
                                               + File.separator + "TimerBean.jar"))
                            return;
                        File timerbean = new File(
                            System.getProperty("netbeans.home")
                            + File.separator + "beans"
                            + File.separator + "TimerBean.jar");
                        if (jarpath.equals(timerbean.getCanonicalPath()))
                            return;
                        
                        jarfs.setJarFile(timerbean);
                    }
                    catch (java.io.IOException ex) { /* ignore */ }
                    catch (PropertyVetoException ex) { /* ignore */ }
                }
            });
    }
    
    /** Module installed again. */
    public void restored () {
        timerBeanHack();
        
        BeanInstaller.autoLoadBeans ();

        // register standard persistence managers
        PersistenceManager.registerManager (new TuborgPersistenceManager ());
        PersistenceManager.registerManager (new GandalfPersistenceManager ());

        // XXX(-tdt) JDK "forgets" to provide a PropertyEditor for char and Character

        PropertyEditor charEditor;

        charEditor = PropertyEditorManager.findEditor(Character.TYPE);
        if (charEditor == null)
            FormPropertyEditorManager.registerEditor(
                Character.TYPE,
                org.netbeans.modules.form.editors.CharacterEditor.class);

        charEditor = PropertyEditorManager.findEditor(Character.class);
        if (charEditor == null)
            FormPropertyEditorManager.registerEditor(
                Character.class,
                org.netbeans.modules.form.editors.CharacterEditor.class);
    }

    /** Module was uninstalled. */
    public void uninstalled () {
        // ---------------------------------------------------------------------------
        // 1. remove FormEditor actions
        uninstallActions ();

        // [PENDING - ask and delete ComponentPalette]
        // [PENDING - ask and delete Form templates]
    }

    // -----------------------------------------------------------------------------
    // Private methods

    private void installActions () {
        try {
            // install actions into menu
            Utilities2.createAction (InstallBeanAction.class,
                                     DataFolder.create (org.openide.TopManager.getDefault ().getPlaces ().folders().menus (), "Tools"), // NOI18N
                                     "UnmountFSAction", true, true, false, false // NOI18N
                                    );

            Utilities2.createAction (ComponentInspectorAction.class,
                                     DataFolder.create (org.openide.TopManager.getDefault ().getPlaces ().folders().menus (), "View"), // NOI18N
                                     "HTMLViewAction", true, false, false, false // NOI18N
                                    );

            // install actions into toolbar
            DataFolder formFolder = DataFolder.create (org.openide.TopManager.getDefault ().getPlaces ().folders().toolbars (), "Form"); // NOI18N
            localizeFile (formFolder.getPrimaryFile ());
            DataFolder paletteFolder = DataFolder.create (org.openide.TopManager.getDefault ().getPlaces ().folders().toolbars (), "Palette"); // NOI18N
            localizeFile (paletteFolder.getPrimaryFile ());

            DataObject[] formInstances = {
                Utilities2.createAction (ComponentInspectorAction.class, formFolder),
                Utilities2.createAction (TestModeAction.class, formFolder),
                Utilities2.createAction (DesignModeAction.class, formFolder),
                Utilities2.createAction (ShowGridAction.class, formFolder),
            };
            formFolder.setOrder (formInstances);

            Utilities2.createAction (PaletteAction.class, paletteFolder);

            // install actions into action pool
            DataFolder formActions = DataFolder.create (org.openide.TopManager.getDefault ().getPlaces ().folders ().actions (), "Form"); // NOI18N
            localizeFile (formActions.getPrimaryFile ());
            Utilities2.createAction (InstallBeanAction.class, formActions);
            Utilities2.createAction (ComponentInspectorAction.class, formActions);
            Utilities2.createAction (PaletteAction.class, formActions);
            Utilities2.createAction (CustomizeLayoutAction.class, formActions);
            Utilities2.createAction (DesignModeAction.class, formActions);
            Utilities2.createAction (EventsAction.class, formActions);
            Utilities2.createAction (GotoEditorAction.class, formActions);
            Utilities2.createAction (GotoFormAction.class, formActions);
            Utilities2.createAction (GotoInspectorAction.class, formActions);
            Utilities2.createAction (SelectLayoutAction.class, formActions);
            Utilities2.createAction (ShowGridAction.class, formActions);
            Utilities2.createAction (TestModeAction.class, formActions);
            Utilities2.createAction (InstallToPaletteAction.class, formActions);

        } catch (Exception e) {
            if (System.getProperty ("netbeans.debug.exceptions") != null) { // NOI18N
                e.printStackTrace ();
            }
            // ignore failure to install
        }
    }

    private void uninstallActions () {
        try {
            // remove actions from menu
            Utilities2.removeAction (InstallBeanAction.class, DataFolder.create (org.openide.TopManager.getDefault ().getPlaces ().folders().menus (), "Tools")); // NOI18N
            Utilities2.removeAction (ComponentInspectorAction.class, DataFolder.create (org.openide.TopManager.getDefault ().getPlaces ().folders().menus (), "View")); // NOI18N

            // remove actions from toolbar
            DataFolder formFolder = DataFolder.create (org.openide.TopManager.getDefault ().getPlaces ().folders().toolbars (), "Form"); // NOI18N
            DataFolder paletteFolder = DataFolder.create (org.openide.TopManager.getDefault ().getPlaces ().folders().toolbars (), "Palette"); // NOI18N

            Utilities2.removeAction (ComponentInspectorAction.class, formFolder);
            Utilities2.removeAction (TestModeAction.class, formFolder);
            Utilities2.removeAction (DesignModeAction.class, formFolder);
            Utilities2.removeAction (ShowGridAction.class, formFolder);
            Utilities2.removeAction (PaletteAction.class, paletteFolder);

            if (formFolder.getChildren ().length == 0) formFolder.delete ();
            if (paletteFolder.getChildren ().length == 0) paletteFolder.delete ();

            // remove actions from action pool
            DataFolder formActions = DataFolder.create (org.openide.TopManager.getDefault ().getPlaces ().folders ().actions (), "Form"); // NOI18N
            Utilities2.removeAction (ComponentInspectorAction.class, formActions);
            Utilities2.removeAction (InstallBeanAction.class, formActions);
            Utilities2.removeAction (PaletteAction.class, formActions);
            Utilities2.removeAction (CustomizeLayoutAction.class, formActions);
            Utilities2.removeAction (DesignModeAction.class, formActions);
            Utilities2.removeAction (EventsAction.class, formActions);
            Utilities2.removeAction (GotoEditorAction.class, formActions);
            Utilities2.removeAction (GotoFormAction.class, formActions);
            Utilities2.removeAction (GotoInspectorAction.class, formActions);
            Utilities2.removeAction (SelectLayoutAction.class, formActions);
            Utilities2.removeAction (ShowGridAction.class, formActions);
            Utilities2.removeAction (TestModeAction.class, formActions);
            Utilities2.removeAction (InstallToPaletteAction.class, formActions);

            if (formActions.getChildren ().length == 0) formActions.delete ();

        } catch (Exception e) {
            if (System.getProperty ("netbeans.debug.exceptions") != null) { // NOI18N
                e.printStackTrace ();
            }
            // ignore failure to uninstall
        }
    }

    private void copyTemplates () {
        try {
            FileUtil.extractJar (
                TopManager.getDefault ().getPlaces ().folders().templates ().getPrimaryFile (),
                NbBundle.getLocalizedFile ("org.netbeans.modules.form.resources.templates", "jar").openStream () // NOI18N
            );
        } catch (java.io.IOException e) {
            TopManager.getDefault ().notifyException (e);
        }
    }

    private void createComponentPalette () {
        FileObject root = TopManager.getDefault ().getRepository ().getDefaultFileSystem ().getRoot ();
        FileObject paletteFolder;
        if ((paletteFolder = root.getFileObject ("Palette")) == null) { // NOI18N
            try {
                paletteFolder = root.createFolder ("Palette"); // NOI18N
            } catch (java.io.IOException e) {
                TopManager.getDefault ().notify (new NotifyDescriptor.Message (NbBundle.getBundle (FormEditorModule.class).getString ("ERR_CreatingPalette"), NotifyDescriptor.ERROR_MESSAGE)); // NOI18N
                return;
            }
        }
        DataFolder paletteDataFolder = DataFolder.findFolder (paletteFolder);

        FileObject awtCategory = null; DataFolder awtFolder;
        FileObject swingCategory = null; DataFolder swingFolder;
        FileObject swing2Category = null; DataFolder swing2Folder;
        FileObject beansCategory = null; DataFolder beansFolder;
        FileObject layoutsCategory = null; DataFolder layoutsFolder;
        FileObject bordersCategory = null; DataFolder bordersFolder;

        java.util.ArrayList categoryErrors = new java.util.ArrayList ();
        java.util.ArrayList componentErrors = new java.util.ArrayList ();

        // -----------------------------------------------------------------------------
        // Create AWT Category and components
        try {
            if ((awtCategory = paletteFolder.getFileObject ("AWT")) == null) // NOI18N
                awtCategory = paletteFolder.createFolder ("AWT"); // NOI18N
            createInstances (awtCategory, defaultAWTComponents, null, componentErrors);
            localizeFile (awtCategory);
        } catch (java.io.IOException e) {
            categoryErrors.add ("AWT"); // NOI18N
        }
        awtFolder = DataFolder.findFolder (awtCategory);

        // -----------------------------------------------------------------------------
        // Create Swing Category and components
        try {
            if ((swingCategory = paletteFolder.getFileObject ("Swing")) == null) // NOI18N
                swingCategory = paletteFolder.createFolder ("Swing"); // NOI18N
            createInstances (swingCategory, defaultSwingComponents, null, componentErrors);
            localizeFile (swingCategory);
        } catch (java.io.IOException e) {
            categoryErrors.add ("Swing"); // NOI18N
        }
        swingFolder = DataFolder.findFolder (swingCategory);

        // -----------------------------------------------------------------------------
        // Create Swing2 Category and components
        try {
            if ((swing2Category = paletteFolder.getFileObject ("Swing2")) == null) // NOI18N
                swing2Category = paletteFolder.createFolder ("Swing2"); // NOI18N
            createInstances (swing2Category, defaultSwing2Components, null, componentErrors);
            localizeFile (swing2Category);
        } catch (java.io.IOException e) {
            categoryErrors.add ("Swing2"); // NOI18N
        }
        swing2Folder = DataFolder.findFolder (swing2Category);

        // -----------------------------------------------------------------------------
        // Create Beans Category and components
        try {
            if ((beansCategory = paletteFolder.getFileObject ("Beans")) == null) // NOI18N
                beansCategory = paletteFolder.createFolder ("Beans"); // NOI18N
            createInstances (beansCategory, defaultBeansComponents, null, componentErrors);
            localizeFile (beansCategory);
        } catch (java.io.IOException e) {
            categoryErrors.add ("Beans"); // NOI18N
        }
        beansFolder = DataFolder.findFolder (beansCategory);

        // -----------------------------------------------------------------------------
        // Create Layouts Category and components
        try {
            if ((layoutsCategory = paletteFolder.getFileObject ("Layouts")) == null) // NOI18N
                layoutsCategory = paletteFolder.createFolder ("Layouts"); // NOI18N
            createInstances (layoutsCategory, defaultLayoutsComponents, defaultLayoutsIcons, componentErrors);
            localizeFile (layoutsCategory);
        } catch (java.io.IOException e) {
            categoryErrors.add ("Layouts"); // NOI18N
        }
        layoutsFolder = DataFolder.findFolder (layoutsCategory);

        // -----------------------------------------------------------------------------
        // Create Borders Category and components
        try {
            if ((bordersCategory = paletteFolder.getFileObject ("Borders")) == null) // NOI18N
                bordersCategory = paletteFolder.createFolder ("Borders"); // NOI18N
            createInstances (bordersCategory, defaultBorders, defaultBordersIcons, componentErrors);
            localizeFile (bordersCategory);
        } catch (java.io.IOException e) {
            categoryErrors.add ("Borders"); // NOI18N
        }
        bordersFolder = DataFolder.findFolder (bordersCategory);

        try {
            paletteDataFolder.setOrder (new DataObject[] { awtFolder, swingFolder, swing2Folder, beansFolder, layoutsFolder, bordersFolder } );
        } catch (java.io.IOException e) {
        }

        if ((categoryErrors.size () != 0) || (componentErrors.size () != 0)) {
            TopManager.getDefault ().notify (new NotifyDescriptor.Message (NbBundle.getBundle (FormEditorModule.class).getString ("ERR_ProblemsCreatingPalette"), NotifyDescriptor.WARNING_MESSAGE)); // NOI18N
        }
    }

    private void createInstances (FileObject folder, String[] classNames, String[] iconNames, java.util.Collection componentErrors) {
        java.util.ArrayList orderList = new java.util.ArrayList (classNames.length);
        for (int i = 0; i < classNames.length; i++) {
            String fileName = formatName (classNames[i]);
            FileLock lock = null;
            try {
                if (folder.getFileObject (fileName+".instance") == null) { // NOI18N
                    FileObject fo = folder.createData (fileName, "instance"); // NOI18N
                    if ((iconNames != null) && (iconNames [i] != null)) {
                        lock = fo.lock ();
                        java.io.OutputStream os = fo.getOutputStream (lock);
                        String ic = "icon="+iconNames[i]; // NOI18N
                        os.write (ic.getBytes ());
                    }
                    DataObject obj = DataObject.find (fo);
                    if (obj != null) {
                        orderList.add (obj);
                    }
                }
            } catch (java.io.IOException e) {
                componentErrors.add (fileName);
            } finally {
                if (lock != null) {
                    lock.releaseLock ();
                }
            }
        }
        if (orderList.size () > 0) {
            DataFolder dataFolder = DataFolder.findFolder (folder);
            if (dataFolder != null) {
                try {
                    dataFolder.setOrder ((DataObject[])orderList.toArray (new DataObject[orderList.size ()]));
                } catch (java.io.IOException e) {
                    // ignore failure to set order
                }
            }
        }
    }

    private static void localizeFile (FileObject file) {
        try {
            // This attribute is private to the system file system and must be specially added this way:
            file.setAttribute ("SystemFileSystem.localizingBundle", "org.netbeans.modules.form.Bundle"); // NOI18N
        } catch (java.io.IOException ioe) {
            if (Boolean.getBoolean ("netbeans.debug.exceptions")) // NOI18N
                ioe.printStackTrace ();
        }
    }

    private String formatName (String className) {
        return className.substring (className.lastIndexOf (".") + 1) + "[" + className.replace ('.', '-') + "]"; // NOI18N
    }

    // -----------------------------------------------------------------------------
    // Default Palette contents

    /** The default AWT Components */
    private final static String[] defaultAWTComponents = new String[] {
                "java.awt.Label", // NOI18N
                "java.awt.Button", // NOI18N
                "java.awt.TextField", // NOI18N
                "java.awt.TextArea", // NOI18N
                "java.awt.Checkbox", // NOI18N
                "java.awt.Choice", // NOI18N
                "java.awt.List", // NOI18N
                "java.awt.Scrollbar", // NOI18N
                "java.awt.ScrollPane", // NOI18N
                "java.awt.Panel", // NOI18N
                "java.awt.Canvas", // NOI18N
                "java.awt.MenuBar", // NOI18N
                "java.awt.PopupMenu", // NOI18N
            };

    /** The default Swing Components */
    private final static String[] defaultSwingComponents = new String[] {
                "javax.swing.JLabel", // NOI18N
                "javax.swing.JButton", // NOI18N
                "javax.swing.JCheckBox", // NOI18N
                "javax.swing.JRadioButton", // NOI18N
                "javax.swing.JComboBox", // NOI18N
                "javax.swing.JList", // NOI18N
                "javax.swing.JTextField", // NOI18N
                "javax.swing.JTextArea", // NOI18N
                "javax.swing.JToggleButton", // NOI18N
                "javax.swing.JPanel", // NOI18N
                "javax.swing.JTabbedPane", // NOI18N
                "javax.swing.JScrollBar", // NOI18N
                "javax.swing.JScrollPane", // NOI18N
                "javax.swing.JMenuBar", // NOI18N
                "javax.swing.JPopupMenu", // NOI18N
            };

    /** The default Swing Components - Swing2 category */
    private final static String[] defaultSwing2Components = new String[] {
                "javax.swing.JSlider", // NOI18N
                "javax.swing.JProgressBar", // NOI18N
                "javax.swing.JSplitPane", // NOI18N
                "javax.swing.JPasswordField", // NOI18N
                "javax.swing.JSeparator", // NOI18N
                "javax.swing.JTextPane", // NOI18N
                "javax.swing.JEditorPane", // NOI18N
                "javax.swing.JTree", // NOI18N
                "javax.swing.JTable", // NOI18N
                "javax.swing.JToolBar", // NOI18N
                "javax.swing.JInternalFrame", // NOI18N
                "javax.swing.JLayeredPane", // NOI18N
                "javax.swing.JDesktopPane", // NOI18N
                "javax.swing.JOptionPane", // NOI18N
            };


    //  private final static String[] defaultDBComponents = new String[] {
    //    "org.netbeans.lib.sql.JDBCRowSet", // NOI18N
    //    "org.netbeans.lib.sql.components.DataNavigator", // NOI18N
    //  };

    /** The default Swing Components - beans category */
    private final static String[] defaultBeansComponents = new String[] {
                // for future use.
            };

    /** The default Layout Components */
    private final static String[] defaultLayoutsComponents = new String[] {
                "org.netbeans.modules.form.compat2.layouts.DesignFlowLayout", // NOI18N
                "org.netbeans.modules.form.compat2.layouts.DesignBorderLayout", // NOI18N
                "org.netbeans.modules.form.compat2.layouts.DesignGridLayout", // NOI18N
                "org.netbeans.modules.form.compat2.layouts.DesignCardLayout", // NOI18N
                "org.netbeans.modules.form.compat2.layouts.DesignAbsoluteLayout", // NOI18N
                "org.netbeans.modules.form.compat2.layouts.DesignGridBagLayout", // NOI18N
                "org.netbeans.modules.form.compat2.layouts.DesignBoxLayout", // NOI18N
            };

    /** The default Layout Components */
    private final static String[] defaultLayoutsIcons = new String[] {
                "/org/netbeans/modules/form/resources/palette/flowLayout.gif", // NOI18N
                "/org/netbeans/modules/form/resources/palette/borderLayout.gif", // NOI18N
                "/org/netbeans/modules/form/resources/palette/gridLayout.gif", // NOI18N
                "/org/netbeans/modules/form/resources/palette/cardLayout.gif", // NOI18N
                "/org/netbeans/modules/form/resources/palette/absoluteLayout.gif", // NOI18N
                "/org/netbeans/modules/form/resources/palette/gridBagLayout.gif", // NOI18N
                "/org/netbeans/modules/form/resources/palette/boxLayout.gif", // NOI18N
            };

    /** The default Swing Borders */
    private final static String[] defaultBorders = new String[] {
                "org.netbeans.modules.form.compat2.border.EmptyBorderInfo", // NOI18N
                "org.netbeans.modules.form.compat2.border.LineBorderInfo", // NOI18N
                "org.netbeans.modules.form.compat2.border.MatteIconBorderInfo", // NOI18N
                "org.netbeans.modules.form.compat2.border.MatteColorBorderInfo", // NOI18N
                "org.netbeans.modules.form.compat2.border.TitledBorderInfo", // NOI18N
                "org.netbeans.modules.form.compat2.border.EtchedBorderInfo", // NOI18N
                "org.netbeans.modules.form.compat2.border.BevelBorderInfo", // NOI18N
                "org.netbeans.modules.form.compat2.border.SoftBevelBorderInfo", // NOI18N
                "org.netbeans.modules.form.compat2.border.CompoundBorderInfo", // NOI18N
            };

    /** The default Swing Borders */
    private final static String[] defaultBordersIcons = new String[] {
                "/org/netbeans/modules/form/resources/palette/border.gif", // NOI18N
                "/org/netbeans/modules/form/resources/palette/lineBorder.gif", // NOI18N
                "/org/netbeans/modules/form/resources/palette/matteIconBorder.gif", // NOI18N
                "/org/netbeans/modules/form/resources/palette/matteColorBorder.gif", // NOI18N
                "/org/netbeans/modules/form/resources/palette/titledBorder.gif", // NOI18N
                "/org/netbeans/modules/form/resources/palette/etchedBorder.gif", // NOI18N
                "/org/netbeans/modules/form/resources/palette/bevelBorder.gif", // NOI18N
                "/org/netbeans/modules/form/resources/palette/softBevelBorder.gif", // NOI18N
                "/org/netbeans/modules/form/resources/palette/compoundBorder.gif", // NOI18N
            };
}

/*
 * Log
 *  44   Gandalf   1.43        2/29/00  Tran Duc Trung  JDK "forgets" to provide
 *       a PropertyEditor for char and Character. We need to implement one 
 *       ourselves
 *  43   Gandalf   1.42        1/19/00  Jesse Glick     Localized filenames.
 *  42   Gandalf   1.41        1/17/00  Jesse Glick     Localized filenames.
 *  41   Gandalf   1.40        1/16/00  Jesse Glick     Actions pool; localized 
 *       jars.
 *  40   Gandalf   1.39        1/5/00   Ian Formanek    NOI18N
 *  39   Gandalf   1.38        1/4/00   Ian Formanek    Proper Removal of 
 *       actions, I18Nzed
 *  38   Gandalf   1.37        1/4/00   Ian Formanek    Uses 
 *       Utilities2.createAction for Toolbars and action pool
 *  37   Gandalf   1.36        11/27/99 Patrik Knakal   
 *  36   Gandalf   1.35        11/25/99 Ian Formanek    Uses Utilities module
 *  35   Gandalf   1.34        10/27/99 Ian Formanek    Fixed bug 4596 - There 
 *       should be no separator in View menu between Component Inspector and 
 *       other windows.
 *  34   Gandalf   1.33        10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  33   Gandalf   1.32        10/1/99  Petr Hrebejk    org.openide.modules.ModuleInstall
 *        changed to class + some methods added
 *  32   Gandalf   1.31        9/13/99  Ian Formanek    Fixed bug 3182 - 
 *       Property Editor for Border has two the same tabs.
 *  31   Gandalf   1.30        8/15/99  Ian Formanek    Removed 
 *       ListModelFormAware editor
 *  30   Gandalf   1.29        8/9/99   Ian Formanek    Used currentClassLoader 
 *       to fix problems with loading beans only present in repository
 *  29   Gandalf   1.28        7/31/99  Ian Formanek    Fixed registration of 
 *       BorderEditor
 *  28   Gandalf   1.27        7/15/99  Ian Formanek    Better cleanup when 
 *       uninstalling
 *  27   Gandalf   1.26        7/15/99  Ian Formanek    Installation of actions 
 *       into action pool, works better if actions already exist
 *  26   Gandalf   1.25        7/15/99  Ian Formanek    Items in 
 *       ComponentPalette are ordered
 *  25   Gandalf   1.24        7/11/99  Ian Formanek    Persistence managers 
 *       registered
 *  24   Gandalf   1.23        7/6/99   Ian Formanek    Installs menu and 
 *       toolbar actions...
 *  23   Gandalf   1.22        6/30/99  Ian Formanek    added registration of 
 *       ListModelFormAwareEditor
 *  22   Gandalf   1.21        6/22/99  Ian Formanek    Added Canvas to AWT 
 *       components
 *  21   Gandalf   1.20        6/10/99  Ian Formanek    copy templates on 
 *       install
 *  20   Gandalf   1.19        6/9/99   Ian Formanek    ---- Package Change To 
 *       org.openide ----
 *  19   Gandalf   1.18        6/7/99   Ian Formanek    AutoLoad beans enabled 
 *       again
 *  18   Gandalf   1.17        6/4/99   Ian Formanek    
 *  17   Gandalf   1.16        5/30/99  Ian Formanek    Minor property editors 
 *       tweaks, fixed problem with empty border's icon
 *  16   Gandalf   1.15        5/14/99  Ian Formanek    
 *  15   Gandalf   1.14        5/14/99  Ian Formanek    
 *  14   Gandalf   1.13        5/11/99  Ian Formanek    Build 318 version
 *  13   Gandalf   1.12        5/4/99   Ian Formanek    Icons again
 *  12   Gandalf   1.11        4/26/99  Ian Formanek    
 *  11   Gandalf   1.10        4/23/99  Ian Formanek    Icons for layouts and 
 *       borders
 *  10   Gandalf   1.9         4/8/99   Ian Formanek    Removed BeanInfo init
 *  9    Gandalf   1.8         4/5/99   Ian Formanek    
 *  8    Gandalf   1.7         3/31/99  Ian Formanek    
 *  7    Gandalf   1.6         3/31/99  Ian Formanek    Fixed bug 1410 - Many 
 *       exceptions (see attachment) are thrown only during first startup after 
 *       installing.
 *  6    Gandalf   1.5         3/30/99  Ian Formanek    Creates default palette 
 *       on first installation
 *  5    Gandalf   1.4         3/30/99  Ian Formanek    
 *  4    Gandalf   1.3         3/27/99  Ian Formanek    
 *  3    Gandalf   1.2         3/26/99  Ian Formanek    
 *  2    Gandalf   1.1         3/22/99  Ian Formanek    
 *  1    Gandalf   1.0         3/22/99  Ian Formanek    
 * $
 */
