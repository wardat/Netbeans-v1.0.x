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

package org.netbeans.modules.form.compat2.layouts;

import java.beans.*;
import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;

import org.netbeans.modules.form.*;
import org.openide.TopManager;
import org.openide.NotifyDescriptor;
import org.openide.nodes.Node;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Sheet;
import org.openide.explorer.propertysheet.PropertySheet;

/** A customizer providing better editing facility for GridBagLayout
*
* @author   Petr Hrebejk
*/
final public class GridBagCustomizer extends JPanel implements Customizer,
    org.netbeans.modules.form.FormListener {
    /** bundle to obtain text information from */
    private static java.util.ResourceBundle bundle = org.openide.util.NbBundle.getBundle (GridBagCustomizer.class);

    // -----------------------------------------------------------------------------
    // private area

    PropertyChangeSupport propertySupport = new PropertyChangeSupport (this);

    static final int TOP = 1;
    static final int BOTTOM = 2;
    static final int LEFT = 4;
    static final int RIGHT = 8;

    static final int HORIZONTAL = LEFT + RIGHT;
    static final int VERTICAL = TOP + BOTTOM;

    static final int PLUS = 1;
    static final int MINUS = -1;

    private static final ImageIcon REMAINDER_ICON = new ImageIcon (GridBagCustomizer.class.getResource ("/org/netbeans/modules/form/compat2/layouts/resources/remainder.gif")); // NOI18N

    private DesignGridBagLayout designLayout;

    private FormManager2 formManager;
    private RADVisualContainer radContainer;
    private RADVisualComponent[] radComponents;
    private GBComponentProxy[] gbcProxies;

    // Customizer components
    private org.openide.awt.SplittedPanel splitPanel;
    private JPanel designPanel;

    private GridBagControlCenter controlCenter;
    private GBContainerProxy containerProxy;
    private PropertySheet propertySheet;
    private javax.swing.JScrollPane designScrollPane;
    private JLayeredPane designLayeredPane;

    private GBComponentProxy.DragLabel dragLabel = null;

    /** This is a hack. We need to now whether the GLC is painted first time
    * in order to paint empty cols and rows correctlly. Field is setted in
    * setObject() and Ppaint() methods.
    */
    private boolean firstPaint = false;

    static final long serialVersionUID =-632768048562391785L;

    private void initialize () {

        initComponents();

        formManager = designLayout.getFormManager ();
        radContainer = designLayout.getRADContainer ();
        radComponents = radContainer.getSubComponents ();

        gbcProxies = new GBComponentProxy[radComponents.length];
        for (int i = 0; i < radComponents.length; i++) {
            gbcProxies[i] = new GBComponentProxy ( radComponents[i], containerProxy );
        }

        // Adds all proxies and selects the first one
        containerProxy.addAllProxies();

        formManager.addFormListener( this );

    }

    /** inits the components of the customizer */

    private void initComponents() {

        setBorder (new javax.swing.border.EmptyBorder (8, 8, 8, 8));
        setLayout (new BorderLayout ()); // [PENDING]

        splitPanel = new org.openide.awt.SplittedPanel ();
        //splitPanel.setLayout (new java.awt.FlowLayout ());
        splitPanel.setSplitType( org.openide.awt.SplittedPanel.HORIZONTAL );
        splitPanel.setSplitAbsolute( true );
        splitPanel.setSplitPosition( org.openide.awt.SplittedPanel.FIRST_PREFERRED );
        splitPanel.setSplitDragable( true );
        splitPanel.setSplitTypeChangeEnabled( true );

        propertySheet = new PropertySheet ();
        try {
            propertySheet.setSortingMode (PropertySheet.UNSORTED);
        }
        catch (java.beans.PropertyVetoException e) {
            TopManager.getDefault().notifyException( e );
        }

        JPanel panel = new JPanel();
        panel.setLayout( new BorderLayout() );
        panel.add( propertySheet, BorderLayout.CENTER );
        controlCenter = new GridBagControlCenter( this );
        panel.add( controlCenter, BorderLayout.SOUTH );
        splitPanel.add( panel, org.openide.awt.SplittedPanel.ADD_FIRST );

        designScrollPane = new javax.swing.JScrollPane ();
        designPanel = new JPanel() {
                          public Dimension getPreferredSize() {
                              //System.out.println(" THE pref size of DesignPanel " + containerProxy.getPreferredSize() ); // NOI18N
                              return  containerProxy.getPreferredSize();
                          }
                      };

        designPanel.setLayout( new GridBagLayout() );
        designPanel.setBackground( (java.awt.Color) javax.swing.UIManager.getDefaults ().get ("desktop") ); // NOI18N
        GridBagConstraints con = new GridBagConstraints ();
        con.anchor = GridBagConstraints.CENTER;
        con.fill = GridBagConstraints.NONE;
        containerProxy = new GBContainerProxy();

        designPanel.add( containerProxy, con );

        designLayeredPane = new JLayeredPane() {
                                public Dimension getPreferredSize() {

                                    Dimension dpd = designPanel.getPreferredSize();
                                    Dimension spd = designScrollPane.getViewport().getExtentSize();

                                    int width = Math.max( dpd.width + 40, spd.width);
                                    int height = Math.max( dpd.height + 40 , spd.height );

                                    /*
                                    Dimension dpd = designPanel.getPreferredSize();
                                    Dimension spd = designScrollPane.getViewport().getExtentSize();

                                    return  new Dimension( Math.max( dpd.width, spd.width), Math.max( dpd.height, spd.height ) );
                                    */
                                    //System.out.println(" THE GET " + new Dimension( Math.max( 400, width ), Math.max( 300, height ) ) ); // NOI18N

                                    return  new Dimension( Math.max( 400, width ), Math.max( 300, height ) );
                                }
                            };

        //designLayeredPane.setLayout( new BorderLayout() );

        //designLayeredPane.setLayer( designPanel, JLayeredPane.DEFAULT_LAYER.intValue() );
        designLayeredPane.add( designPanel, JLayeredPane.DEFAULT_LAYER );
        designPanel.setBounds( 20, 20, designPanel.getPreferredSize().width, designPanel.getPreferredSize().height);
        designLayeredPane.setOpaque( true );
        designLayeredPane.setBackground(  (java.awt.Color) javax.swing.UIManager.getDefaults ().get ("desktop") ); // NOI18N

        designScrollPane.setViewportView ( designLayeredPane );
        splitPanel.add( designScrollPane, org.openide.awt.SplittedPanel.ADD_SECOND );

        add( splitPanel, BorderLayout.CENTER );
    }

    void setAnchor( int anchor ) {
        List selected = containerProxy.getSelectedProxies();
        Iterator it = selected.iterator();
        while( it.hasNext() )
            setProperty( (GBComponentProxy)it.next(), DesignGridBagLayout.PROP_ANCHOR, new Integer( anchor ));
    }

    void setFill( int fill ) {
        List selected = containerProxy.getSelectedProxies();
        Iterator it = selected.iterator();
        while( it.hasNext() )
            setProperty( (GBComponentProxy)it.next(), DesignGridBagLayout.PROP_FILL, new Integer( fill ));
    };



    void modifyIPad( int action, int what ) {
        List selected = containerProxy.getSelectedProxies();
        Iterator it = selected.iterator();
        while( it.hasNext() ) {
            GBComponentProxy p = (GBComponentProxy)it.next();
            int value =  what == HORIZONTAL ? p.getRealConstraints().ipadx : p.getRealConstraints().ipady;
            value += action;
            if ( value < 0 )
                continue;
            setProperty( p,
                         what == HORIZONTAL ? DesignGridBagLayout.PROP_IPADX : DesignGridBagLayout.PROP_IPADY ,
                         new Integer( value ));
        }
    }

    void modifyInsets( int action, int what ) {
        List selected = containerProxy.getSelectedProxies();
        Iterator it = selected.iterator();
        while( it.hasNext() ) {
            GBComponentProxy p = (GBComponentProxy)it.next();
            Insets old_insets = p.getRealConstraints().insets;
            Insets new_insets = (Insets)old_insets.clone();

            if ( (what & TOP) != 0 ) {
                new_insets.top += action;
                if ( new_insets.top < 0 )
                    new_insets.top = 0;
            }
            if ( (what & BOTTOM) != 0 ) {
                new_insets.bottom += action;
                if ( new_insets.bottom < 0 )
                    new_insets.bottom = 0;
            }
            if ( (what & LEFT) != 0 ) {
                new_insets.left += action;
                if ( new_insets.left < 0 )
                    new_insets.left = 0;
            }
            if ( (what & RIGHT) != 0 ) {
                new_insets.right += action;
                if ( new_insets.right < 0 )
                    new_insets.right = 0;
            }

            setProperty( p, DesignGridBagLayout.PROP_INSETS,  new_insets );
        }
    }


    void modifyGridSize( int action, int what ) {
        List selected = containerProxy.getSelectedProxies();
        Iterator it = selected.iterator();

        while( it.hasNext() ) {
            GBComponentProxy p = (GBComponentProxy)it.next();
            int value =  what == HORIZONTAL ? p.getRealConstraints().gridwidth : p.getRealConstraints().gridheight;

            if (action == 0 )
                value = value == 0 ? 1 : 0;
            else {
                value += action;
                if ( value < 1 ) {
                    value = 1;
                }
            }

            setProperty( p,
                         what == HORIZONTAL ? DesignGridBagLayout.PROP_GRIDWIDTH : DesignGridBagLayout.PROP_GRIDHEIGHT ,
                         new Integer( value ));
        }
    }

    private void setProperty( GBComponentProxy p, String name, Object value ) {

        Node.Property nps[] = designLayout.getComponentProperties( p.getComponent() ) ;


        for ( int i = 0; i < nps.length; i++ ) {
            if ( nps[i].getName().equals( name ) )
                try {
                    Object oldValue = nps[i].getValue();
                    nps[i].setValue( value );
                    p.getNode().propertyChange( name, oldValue, value );
                }
                catch ( java.lang.IllegalAccessException e ) {
                    org.openide.TopManager.getDefault().notifyException( e );
                }
                catch ( java.lang.reflect.InvocationTargetException e ) {
                    org.openide.TopManager.getDefault().notifyException( e );
                }
        }

    }

    // -----------------------------------------------------------------------------
    // Customizer implementation

    /**
    * Set the object to be customized.  This method should be called only
    * once, before the Customizer has been added to any parent AWT container.
    * @param bean  The object to be customized.
    */
    public void setObject(Object bean) {
        designLayout = (DesignGridBagLayout)bean;
        initialize ();
        firstPaint = true;
    }

    /**
    * Register a listener for the PropertyChange event.  The customizer
    * should fire a PropertyChange event whenever it changes the target
    * bean in a way that might require the displayed properties to be
    * refreshed.
    *
    * @param listener  An object to be invoked when a PropertyChange
    *		event is fired.
    */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener (listener);
    }

    /**
    * Remove a listener for the PropertyChange event.
    *
    * @param listener  The PropertyChange listener to be removed.
    */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener (listener);
    }
    /*
    public void propertyChange(final java.beans.PropertyChangeEvent p0) {
      System.out.println("PCH :" + p0 );
}
    */

    // -----------------------------------------------------------------------------
    // Form listener implementation


    /** Called when the form is succesfully loaded and fully initialized
     */
    public void formLoaded() {
    }
    /** Called when a change is performed on the form that should lead to regeneration of initializer and variables declaration
    */
    public void formChanged () {
    }
    /** Called when a change is performed on the form that should lead to regeneration of initializer
    */
    public void codeChanged () {
    }
    /** Called when the form is about to be saved
    */
    public void formToBeSaved () {
    }
    /** Called when the order of components within their parent changes
     * @param cont the container on which the components were reordered
     */
    public void componentsReordered(ComponentContainer cont) {
    }
    /** Called when a new component is added to the form
     * @param comps the delta components
     */
    public void componentsAdded(RADComponent[] comps) {
    }
    /** Called when any component is removed from the form
     * @param comps the delta components
     */
    public void componentsRemoved(RADComponent[] comps) {
    }
    /** Called when any synthetic property of a component on the form is changed
     * The synthetic properties include: variableName, serialize, serializeName, generateGlobalVariable
     * @param evt the event object describing the event
     */
    public void componentChanged(FormPropertyEvent evt) {
    }
    public void propertyChanged(FormPropertyEvent evt) {}
    /** Called when any layout property of specified component on given container changes
     * @param container the visual container on which layout the change happened
     * @param component the component which layout property changed or null if layout's own property changed
     * @param propertyName name of changed property
     * @param oldValue old value of changed property
     * @param newValue new value of changed property
     */
    public void layoutChanged(RADVisualContainer container,RADVisualComponent component,
                              String propertyName,Object oldValue,Object newValue) {
        //System.out.println("Layout changed" + propertyName + newValue ); // NOI18N
        containerProxy.updateAllProxies();
        if ( propertyName == DesignGridBagLayout.PROP_ANCHOR || propertyName == DesignGridBagLayout.PROP_FILL ||
                propertyName == DesignGridBagLayout.PROP_GRIDWIDTH || propertyName == DesignGridBagLayout.PROP_GRIDHEIGHT )
            controlCenter.newSelection( containerProxy.getSelectedProxies() );
    }

public void eventAdded(FormEventEvent evt) {}
    public void eventRemoved(FormEventEvent evt) {}
    public void eventRenamed(FormEventEvent evt) {}


    void innerLayoutChanged() {

        /*
        System.out.println("1 CP Size       : " + containerProxy.getSize() );
        System.out.println("1 CP Preff Size : " + containerProxy.getSize() );

        containerProxy.invalidate();
        containerProxy.revalidate();
        containerProxy.widenEmpty();
        containerProxy.revalidate();

        designPanel.invalidate();
        designPanel.validate();
        designPanel.revalidate();
        */
        //designLayeredPane.invalidate();


        containerProxy.widenEmpty();
        designPanel.setBounds( 20, 20, designPanel.getPreferredSize().width, designPanel.getPreferredSize().height);

        //containerProxy.widenEmpty();
        //containerProxy.invalidate();
        containerProxy.revalidate();

        //designPanel.invalidate();
        //designPanel.validate();
        //designPanel.revalidate();

        designLayeredPane.revalidate();
        //designLayeredPane.repaint();

        /*
        System.out.println("2 CP Size        : " + containerProxy.getSize() );
        System.out.println("2 CP Preff Size  : " + containerProxy.getPreferredSize() );
        System.out.println("2 DP Size        : " + designPanel.getSize() );
        System.out.println("2 DP Preff Size  : " + designPanel.getPreferredSize() );
        System.out.println("2 DLP Size       : " + designLayeredPane.getSize() );
        System.out.println("2 DLP Preff Size : " + designLayeredPane.getPreferredSize() );
        */

    }

    // -----------------------------------------------------------------------------
    // Innerclasses


    /** Proxy component for one component in the container */
    class GBComponentProxy  extends JPanel  {
        /*
        private String name;

        GBComponent (String name) {
          this.name = name;
    }
        */

        private GBContainerProxy parentProxy;
        private RADVisualComponent component;
        private ComponentProxyNode node;
        private javax.swing.border.CompoundBorder compoundBorder;
        private javax.swing.border.MatteBorder insetsBorder;
        private javax.swing.border.MatteBorder remainderBorder;
        // private JPanel innerPanel;
        private JLabel componentLabel;
        private GridBagLayout layout;
        private GridBagConstraints componentConstraints;
        private Color INSETS_COLOR = new Color (255, 255, 204);
        private Color CELL_COLOR = new Color (153, 153, 255);

        private boolean isSelected = false;

        static final long serialVersionUID =-6552012922564179923L;

        GBComponentProxy (final RADVisualComponent component, GBContainerProxy parentProxy ) {

            this.component = component;
            this.parentProxy = parentProxy;

            //setLayout ( new BorderLayout ());

            componentLabel = new javax.swing.JLabel ()
                             {
                                 public void paint( Graphics g ) {
                                     int borderSize = 5;
                                     Color borderColor = Color.blue;

                                     super.paint( g );

                                     if ( GBComponentProxy.this.isSelected() ) {

                                         Dimension size = getSize();
                                         int midHor = (size.width - borderSize ) / 2;
                                         int midVer = (size.height - borderSize) / 2;
                                         g.setColor (borderColor);
                                         g.fillRect (0, 0, borderSize, borderSize); // UpLeft
                                         g.fillRect (size.width-borderSize, 0, borderSize, borderSize); // UpRight
                                         g.fillRect (0, size.height-borderSize, borderSize, borderSize); // LoLeft
                                         g.fillRect (size.width-borderSize, size.height-borderSize, borderSize, borderSize); // LoRight


                                         /*
                                         //if (resizable) {
                                         //g.fillRect (midHor, 0, borderSize, borderSize); // UpMid
                                         //g.fillRect (0, midVer, borderSize, borderSize); // LeftMid
                                         g.fillRect (size.width-borderSize, midVer, borderSize, borderSize); // RightMid
                                         g.fillRect (midHor, size.height-borderSize, borderSize, borderSize); // LoMid
                                         //}
                                         */
                                     }

                                 }

                                 public Dimension getPreferredSize() {
                                     return component.getComponent().getPreferredSize();
                                 }

                                 public Dimension getMinimumSize() {
                                     return component.getComponent().getMinimumSize();
                                 }

                                 public Dimension getMaximumSize() {
                                     return component.getComponent().getMaximumSize();
                                 }
                             };
            componentLabel.setOpaque( true );
            componentLabel.setBorder (new javax.swing.border.EtchedBorder());
            componentLabel.setText (component.getName());
            componentLabel.setHorizontalAlignment (0);
            componentLabel.addMouseListener (new java.awt.event.MouseAdapter () {
                                                 public void mouseClicked (java.awt.event.MouseEvent evt) {
                                                     GBComponentProxy.this.mouseClicked (evt);
                                                 }

                                                 public void mousePressed(java.awt.event.MouseEvent evt) {
                                                     GBComponentProxy.this.mousePressed (evt);
                                                 }

                                                 public void mouseReleased(java.awt.event.MouseEvent evt) {
                                                     GBComponentProxy.this.mouseReleased (evt);
                                                 }

                                             } );

            componentLabel.addMouseMotionListener (new java.awt.event.MouseMotionAdapter () {
                                                       public void mouseDragged (java.awt.event.MouseEvent evt) {
                                                           GBComponentProxy.this.mouseDragged (evt);
                                                       }
                                                   }
                                                  );


            /*
            innerPanel = new JPanel();
            innerPanel.setLayout( layout = new GridBagLayout() );
            innerPanel.setBackground( CELL_COLOR ); 
            innerPanel.setOpaque( true );
                    
            innerPanel.add (componentLabel, getInnerComponentConstraints() );      
            add( innerPanel );
            */


            setLayout( layout = new GridBagLayout() );
            setBackground( CELL_COLOR );
            setOpaque( true );
            updateByComponent();
            add (componentLabel, getInnerComponentConstraints() );
            node = new ComponentProxyNode();
        }

        RADVisualComponent getComponent() {
            return component;
        }

        DesignGridBagLayout.GridBagConstraintsDescription getConstraints() {
            return  ( DesignGridBagLayout.GridBagConstraintsDescription) component.getConstraints( DesignGridBagLayout.class );
        }

        GridBagConstraints getRealConstraints() {
            return getConstraints().getGridBagConstraints();
        }

        ComponentProxyNode getNode() {
            return node;
        }

        void updateByComponent() {
            layout.setConstraints( componentLabel, getInnerComponentConstraints() );
        }


        /*
        public void paint( Graphics g ) {
          int borderSize = 5;
          Color borderColor = Color.blue;
         
          super.paint( g );
          
          if ( isSelected() ) {
            Dimension size = getSize();
            int midHor = (size.width - borderSize ) / 2;
            int midVer = (size.height - borderSize) / 2;
            g.setColor (borderColor);
            g.fillRect (0, 0, borderSize, borderSize); // UpLeft
            g.fillRect (size.width-borderSize, 0, borderSize, borderSize); // UpRight
            g.fillRect (0, size.height-borderSize, borderSize, borderSize); // LoLeft
            g.fillRect (size.width-borderSize, size.height-borderSize, borderSize, borderSize); // LoRight

            
            //if (resizable) {
              g.fillRect (midHor, 0, borderSize, borderSize); // UpMid
              g.fillRect (0, midVer, borderSize, borderSize); // LeftMid
              g.fillRect (size.width-borderSize, midVer, borderSize, borderSize); // RightMid
              g.fillRect (midHor, size.height-borderSize, borderSize, borderSize); // LoMid
            //}
          }
    }
        */

        GridBagConstraints getInnerComponentConstraints() {

            GridBagConstraints con = new GridBagConstraints ();
            con.anchor = getRealConstraints().anchor;
            con.fill = getRealConstraints().fill;
            con.gridheight = 1;
            con.gridwidth = 1;
            con.gridx = 0;
            con.gridy = 0;
            // con.insets
            con.ipadx = getRealConstraints().ipadx;
            con.ipady = getRealConstraints().ipady;
            con.weightx = 1.0;
            con.weighty = 1.0;

            return con;
        }


        GridBagConstraints getProxyConstraints() {
            GridBagConstraints con = new GridBagConstraints ();

            con.anchor = GridBagConstraints.CENTER;
            con.fill = GridBagConstraints.BOTH;
            con.gridheight = getRealConstraints().gridheight;
            con.gridwidth = getRealConstraints().gridwidth;
            con.gridx = getRealConstraints().gridx;
            con.gridy = getRealConstraints().gridy;
            con.insets = new Insets( 3, 3, 3, 3 );
            con.ipadx = 0;
            con.ipady = 0;
            //con.weightx = getRealConstraints().weightx;
            //con.weighty = getRealConstraints().weighty;
            con.weightx = 1.0;
            con.weighty = 1.0;
            Insets in  = getRealConstraints().insets;

            insetsBorder = new javax.swing.border.MatteBorder( in.top, in.left, in.bottom, in.right, INSETS_COLOR );
            remainderBorder =  new javax.swing.border.MatteBorder( 0, 0, con.gridheight == 0 ? 4 : 0, con.gridwidth == 0 ? 4 : 0, REMAINDER_ICON );
            compoundBorder = new javax.swing.border.CompoundBorder( remainderBorder, insetsBorder );
            setBorder( compoundBorder );

            return con;
        }

        boolean isSelected() {
            return isSelected;
        }

        void setSelected(boolean isSelected) {
            if ( this.isSelected == isSelected )
                return;

            this.isSelected = isSelected;
            //innerPanel.invalidate();
            componentLabel.repaint();
            /*
            componentLabel.setBackground( isSelected ?
              (java.awt.Color) javax.swing.UIManager.getDefaults ().get ("controlLtHighlight") :
              (java.awt.Color) javax.swing.UIManager.getDefaults ().get ("Button.background") );
            componentLabel.repaint();
            */
        }

        void mouseClicked( java.awt.event.MouseEvent evt ) {
            /*
            if ( evt.isShiftDown() )
              parentProxy.shiftSelect( this );
            else
              parentProxy.select( this );
            */
        }

        void mousePressed( java.awt.event.MouseEvent evt ) {
            if ( evt.isControlDown() )
                parentProxy.shiftSelect( this );
            else
                parentProxy.select( this );
        }

        void mouseReleased( java.awt.event.MouseEvent evt ) {
            if ( dragLabel != null ) {

                if ( !dragLabel.getLastIndex().equals( dragLabel.getOriginalIndex() ) ) {
                    setProperty( this, DesignGridBagLayout.PROP_GRIDX, new Integer ( dragLabel.getLastIndex().x ) );
                    setProperty( this, DesignGridBagLayout.PROP_GRIDY, new Integer ( dragLabel.getLastIndex().y ) );
                }

                designLayeredPane.remove( dragLabel );
                dragLabel = null;
                componentLabel.setCursor( Cursor.getDefaultCursor() );
                designLayeredPane.repaint();
            }
        }

        void mouseDragged( java.awt.event.MouseEvent evt ) {

            //System.out.print("Dragged " ); // NOI18N


            if ( dragLabel == null ) {
                if ( isSelected() ) {

                    //System.out.println(" - first time" ); // NOI18N


                    dragLabel = new DragLabel();
                    dragLabel.setHotSpot( evt.getPoint() );
                    dragLabel.resolveOrigin();
                    dragLabel.setLastIndex( dragLabel.getIndex( evt.getPoint() ) );
                    dragLabel.setOriginalIndex( dragLabel.getIndex( evt.getPoint() ) );

                    designLayeredPane.setLayer( dragLabel, JLayeredPane.DRAG_LAYER.intValue() );
                    designLayeredPane.add( dragLabel, BorderLayout.CENTER );
                    dragLabel.setBounds( evt.getPoint().x, evt.getPoint().y );
                    componentLabel.setCursor( Cursor.getPredefinedCursor( Cursor.MOVE_CURSOR ) );
                }
                else {
                    //System.out.println(" - Undefined" ); // NOI18N

                }
            }
            else
            {

                Point newLoc;
                if ( !dragLabel.getLastIndex().equals( newLoc = dragLabel.getIndex( evt.getPoint() ) ) ) {

                    Point converted = SwingUtilities.convertPoint( dragLabel, evt.getPoint(), designLayeredPane );

                    //System.out.println(" - with layout change" ); // NOI18N


                    GridBagConstraints  con = getProxyConstraints();
                    int oldx = con.gridx;
                    int oldy = con.gridy;

                    con.gridx = newLoc.x;
                    con.gridy = newLoc.y;

                    parentProxy.layout.setConstraints( this, con );

                    /*
                    invalidate();
                    doLayout();
                    */
                    //System.out.println("Constraints set" ); // NOI18N


                    // parentProxy.widenEmpty();

                    //System.out.println(" Widden " ); // NOI18N


                    //invalidate();
                    //innerPanel.invalidate();
                    //parentProxy.invalidateAllProxies();

                    //parentProxy.invalidate();
                    //parentProxy.revalidate();

                    //parentProxy.widenEmpty();

                    //innerLayoutChanged();



                    dragLabel.resolveOrigin();

                    converted = SwingUtilities.convertPoint( designLayeredPane, converted, dragLabel );
                    //dragLabel.setBounds( converted.x, converted.y );
                    Rectangle r = dragLabel.getBounds();
                    Point loc = r.getLocation();
                    loc.x += r.width + 20;
                    loc.y += r.height + 20;
                    //designScrollPane.getViewport().setViewPosition( r.getLocation() );

                    innerLayoutChanged();
                    dragLabel.repaint();

                    //System.out.println(" layout changed" ); // NOI18N


                    dragLabel.setLastIndex( newLoc );

                }
                else
                    //System.out.println(" - simple" ); // NOI18N

                    dragLabel.setBounds( evt.getPoint().x, evt.getPoint().y );
            }
        }

        /** Properties of this node are displayed in the layout property sheet */
        class ComponentProxyNode extends AbstractNode {

            ComponentProxyNode( ) {
                super( Children.LEAF );

                Sheet sheet = Sheet.createDefault();
                Sheet.Set ps = sheet.get(Sheet.PROPERTIES);

                /*
                Node.Property[] props = designLayout.getComponentProperties( component );
                for(int i = 0; i < props.length; i++ )
                  System.out.println( "PROP" + props[i] );
                  */
                ps.put( designLayout.getComponentProperties( component ));
                //sheet.put( ps );
                setSheet(sheet);
            }

            void propertyChange( String name, Object o, Object n) {
                super.firePropertyChange( name, o, n);
            }
        }

        /** Innerclass for the component which is dragged */
        class DragLabel extends JLabel {


            Point origin;
            Point hotSpot;
            Point lastIndex;
            Point originalIndex;

            private Dimension preferredSize;

            static final long serialVersionUID =992490305277357953L;
            DragLabel() {
                setOpaque( false );
                setEnabled( false );
                setBorder (new javax.swing.border.EtchedBorder());
                setText (component.getName());
                setHorizontalAlignment (0);

                preferredSize = (Dimension)componentLabel.getPreferredSize().clone();
            }

            public Dimension getPreferredSize() {
                preferredSize.height = componentLabel.getPreferredSize().height + getRealConstraints().ipady;
                preferredSize.width = componentLabel.getPreferredSize().width + getRealConstraints().ipadx;

                return preferredSize;
            }

            public Dimension getMinimumSize() {
                return componentLabel.getMinimumSize();
            }

            public Dimension getMaximumSize() {
                return componentLabel.getMaximumSize();
            }

            void setHotSpot( Point hotSpot ) {
                this.hotSpot = hotSpot;
            }

            void resolveOrigin() {
                origin = SwingUtilities.convertPoint( componentLabel, 0, 0, designLayeredPane );
            }

            Point getLastIndex() {
                return lastIndex;
            }

            void setLastIndex( Point lastIndex ) {
                this.lastIndex = lastIndex;
            }

            Point getOriginalIndex() {
                return originalIndex;
            }

            void setOriginalIndex( Point originalIndex ) {
                this.originalIndex = originalIndex;
            }

            Point getIndex( Point p ) {
                return parentProxy.getLayoutLocation( SwingUtilities.convertPoint( componentLabel, p, parentProxy ) );
            }

            public void setBounds( int x, int y ) {
                resolveOrigin();
                super.setBounds( origin.x + x - hotSpot.x , origin.y + y - hotSpot.y, getPreferredSize().width, getPreferredSize().height );
            }

        }

    }


    /** Proxy for the container it's layout is edited */
    class GBContainerProxy extends JPanel {

        private GridBagLayout layout;
        private boolean[][] empties;


        static final long serialVersionUID =5113122235848232590L;

        GBContainerProxy() {
            setLayout( layout = new GridBagLayout() );
            setBorder( new javax.swing.border.BevelBorder( javax.swing.border.BevelBorder.RAISED ) );
            setOpaque( true );
        }

        void addAllProxies() {
            for (int i = 0; i < gbcProxies.length; i++) {
                add( gbcProxies[i], gbcProxies[i].getProxyConstraints() );
            }
            invalidate();
            validate();
            innerLayoutChanged();
            widenEmpty();

            if ( gbcProxies.length > 0 )
                select( gbcProxies[0] );
        }

        void widenEmpty() {

            layout.rowHeights = layout.columnWidths = null;

            layout.layoutContainer( this );
            validate();

            int[][] dims = layout.getLayoutDimensions();

            empties = new boolean[2][];

            int[] widths = new int[ dims[0].length ];
            empties[0] = new boolean[ dims[0].length ];

            for ( int i  = 0; i < widths.length; i++ ) {
                // System.out.println("Col [" + i + "] = " + dims[0][i] ); // NOI18N
                widths[i] = 25;
                empties[0][i] = dims[0][i] == 0 ? true : false;
            }
            layout.columnWidths = widths;


            int[] heights = new int[ dims[1].length ];
            empties[1] = new boolean[ dims[1].length ];
            for ( int i  = 0; i < heights.length; i++ ) {
                // System.out.println("Rpw [" + i + "] = " + dims[1][i] ); // NOI18N
                heights[i] = 25;
                empties[1][i] = dims[1][i] == 0 ? true : false;
            }
            layout.rowHeights = heights;

            //layout.layoutContainer( this );
        }

        public void paint( Graphics g ) {

            if ( firstPaint ) {
                innerLayoutChanged();
                firstPaint = false;
            }

            super.paint( g );

            if ( gbcProxies.length > 0 ) {

                Color emptyColor = new Color( 255, 173, 173 );

                Point origin = layout.getLayoutOrigin();

                int[][] dims = layout.getLayoutDimensions();

                int width = 0;
                for( int i = 0; i < dims[0].length; i ++ ) {
                    width += dims[0][i];
                    //System.out.println("W " + i + " : " + dims[0][i] );
                }

                int height = 0;
                for( int i = 0; i < dims[1].length; i ++ ) {
                    height += dims[1][i];
                    // System.out.println("H " + i + " : " + dims[1][i] );
                }

                //Paint empty rows
                int yCoord = origin.y;
                g.setColor( emptyColor );
                for( int i = 0; i < dims[1].length; i ++ ) {
                    if ( empties[1][i] ) {
                        g.setColor( emptyColor );
                        g.fillRect( origin.x, yCoord, width, dims[1][i] );
                    }
                    yCoord += dims[1][i];
                }

                //Paint empty columns
                int xCoord = origin.x;
                for( int i = 0; i < dims[0].length; i ++ ) {
                    if ( empties[0][i] ) {
                        g.setColor( emptyColor );
                        g.fillRect( xCoord, origin.y, dims[0][i], height );
                    }
                    xCoord += dims[0][i];
                }


                // Paint horizontal lines
                g.setColor( Color.black );
                yCoord = dims[1][0] + origin.y - 1;
                for( int i = 1; i < dims[1].length; i ++ ) {
                    g.drawLine( origin.x, yCoord, origin.x + width - 1, yCoord );
                    g.drawLine( origin.x, yCoord + 1, origin.x + width - 1, yCoord + 1 );
                    yCoord += dims[1][i];
                }

                // Paint vertical lines
                xCoord = dims[0][0] + origin.x - 1;
                for( int i = 1; i < dims[0].length; i ++ ) {
                    g.drawLine( xCoord, origin.y, xCoord, origin.y + height - 1);
                    g.drawLine( xCoord + 1, origin.y, xCoord + 1, origin.y + height - 1);
                    xCoord += dims[0][i];
                }
            }

            paintChildren(g);
        }

        Point getLayoutLocation( Point p ) {
            return layout.location( p.x, p.y );
        }

        /* Updates all proxies */
        void updateAllProxies() {

            for (int i = 0; i < gbcProxies.length; i++) {
                updateProxy( gbcProxies[i] );
            }

            //invalidate();

            innerLayoutChanged();

            //designPanel.revalidate();
            //designPanel.repaint();
            //designLayeredPane.revalidate();
            //designPanel.repaint();
        }

        /* Updates the selected proxy */

        void updateProxy( GBComponentProxy p ) {
            p.updateByComponent();

            p.getProxyConstraints();
            layout.setConstraints( p, p.getProxyConstraints() );

            p.invalidate();
            //p.innerPanel.invalidate();
            p.validate();

        }

        void invalidateAllProxies() {
            for (int i = 0; i < gbcProxies.length; i++) {
                gbcProxies[i].invalidate();
            }
            doLayout();
        }


        List getSelectedProxies() {
            List selected = new ArrayList( gbcProxies.length );

            for (int i = 0; i < gbcProxies.length; i++) {
                if ( gbcProxies[i].isSelected )
                    selected.add( gbcProxies[i] );
            }

            return selected;
        }

        Node[] getSelectedNodes() {
            List selected = getSelectedProxies();

            Node[] result =  new Node[ selected.size() ];

            Iterator it = selected.iterator();

            for( int i = 0; it.hasNext(); i++ ) {
                result[i] = ((GBComponentProxy)it.next()).getNode();
            }

            return result;
        }


        void select( GBComponentProxy p) {
            select( p, false );
        }

        void shiftSelect( GBComponentProxy p) {
            select( p, true );
        }

        void select( GBComponentProxy p, boolean shift ) {

            List selected = getSelectedProxies();

            if ( p.isSelected() ) {
                if ( selected.size() == 1 ) {
                    return;
                }
                else if ( shift ) {
                    p.setSelected( false );
                }
                else {
                    Iterator it = selected.iterator();
                    while ( it.hasNext() ) {
                        ((GBComponentProxy)it.next()).setSelected( false );
                    }
                    p.setSelected( true );
                }

            }
            else {
                if ( !shift ) {
                    Iterator it = selected.iterator();
                    while( it.hasNext() )
                        ((GBComponentProxy)it.next()).setSelected( false );
                }
                p.setSelected( true );
            }

            propertySheet.setNodes( getSelectedNodes() );
            controlCenter.newSelection( getSelectedProxies() );
        }

    }



}

/*
 * Log
 *  19   Gandalf   1.18        1/17/00  Petr Hrebejk    Better implementation of
 *       empty rows + retaining relative coordinates.
 *  18   Gandalf   1.17        1/17/00  Petr Hrebejk    Selecting of components 
 *       now behaves the same way like in FormEditor (using Ctrl)
 *  17   Gandalf   1.16        1/13/00  Ian Formanek    NOI18N #2
 *  16   Gandalf   1.15        1/12/00  Ian Formanek    NOI18N
 *  15   Gandalf   1.14        11/27/99 Patrik Knakal   
 *  14   Gandalf   1.13        10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  13   Gandalf   1.12        10/10/99 Petr Hrebejk    Debug messages removed
 *  12   Gandalf   1.11        10/8/99  Petr Hrebejk    Better scrolling in GLC
 *  11   Gandalf   1.10        9/30/99  Ian Formanek    Reflecting changes in 
 *       FormListener
 *  10   Gandalf   1.9         8/18/99  Ian Formanek    Generated serial version
 *       UID
 *  9    Gandalf   1.8         8/2/99   Petr Hrebejk    Empty container fix
 *  8    Gandalf   1.7         7/31/99  Ian Formanek    Cleaned up comments
 *  7    Gandalf   1.6         7/13/99  Petr Hrebejk    GridSize controls fixed
 *  6    Gandalf   1.5         7/4/99   Petr Hrebejk    Drag & Drop  + Resizing
 *  5    Gandalf   1.4         7/3/99   Ian Formanek    made compilable
 *  4    Gandalf   1.3         7/2/99   Petr Hrebejk    Customizer  
 *  3    Gandalf   1.2         6/24/99  Ian Formanek    
 *  2    Gandalf   1.1         6/24/99  Ian Formanek    
 *  1    Gandalf   1.0         6/22/99  Ian Formanek    
 * $
 */
