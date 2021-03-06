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

package org.netbeans.modules.db.explorer.dlg;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.util.*;
import java.beans.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;

import org.openide.DialogDescriptor;
import org.openide.TopManager;
import org.openide.util.NbBundle;

import org.netbeans.lib.ddl.impl.*;
import org.netbeans.lib.ddl.util.*;
import org.netbeans.modules.db.explorer.*;
import org.netbeans.modules.db.util.*;
import org.netbeans.modules.db.explorer.infos.*;
import org.netbeans.modules.db.explorer.nodes.*;

/**
* xxx
*
* @author Slavek Psenicka
*/
public class AddTableColumnDialog
{
    boolean result = false;
    Dialog dialog = null;
    Specification spec;

    /**
     * @associates Vector 
     */
    Map ixmap;
    String colname = null;
    JTextField colnamefield, colsizefield, colscalefield, defvalfield;
    JTextArea checkfield;
    JComboBox coltypecombo, idxcombo;
    JCheckBox pkcheckbox, ixcheckbox, checkcheckbox, nullcheckbox, uniquecheckbox;
    DataModel dmodel = new DataModel();

    public AddTableColumnDialog(final Specification spe, final DatabaseNodeInfo nfo) {
        spec = spe;
        try {
            JLabel label;
            JPanel pane = new JPanel();
            pane.setBorder(new EmptyBorder(new Insets(5,5,5,5)));
            GridBagLayout layout = new GridBagLayout();
            GridBagConstraints con = new GridBagConstraints ();
            pane.setLayout (layout);
            ResourceBundle bundle = NbBundle.getBundle("org.netbeans.modules.db.resources.Bundle");

            TextFieldListener fldlistener = new TextFieldListener(dmodel);
            IntegerFieldListener intfldlistener = new IntegerFieldListener(dmodel);

            // Column name

            label = new JLabel(bundle.getString("AddTableColumnName"));
            con.weightx = 0.0;
            con.anchor = GridBagConstraints.WEST;
            con.insets = new java.awt.Insets (2, 2, 2, 2);
            con.gridx = 0;
            con.gridy = 0;
            layout.setConstraints(label, con);
            pane.add(label);

            con.fill = GridBagConstraints.HORIZONTAL;
            con.weightx = 1.0;
            con.gridx = 1;
            con.gridy = 0;
            con.insets = new java.awt.Insets (2, 2, 2, 2);
            colnamefield = new JTextField(35);
            colnamefield.setName(ColumnItem.NAME);
            colnamefield.addFocusListener(fldlistener);
            layout.setConstraints(colnamefield, con);
            pane.add(colnamefield);

            // Column type

            Map tmap = spec.getTypeMap();
            Vector ttab = new Vector(tmap.size());
            Iterator iter = tmap.keySet().iterator();
            while (iter.hasNext()) {
                String iterkey = (String)iter.next();
                String iterval = (String)tmap.get(iterkey);
                ttab.add(new TypeElement(iterkey, iterval));
            }

            ColumnItem item = new ColumnItem();
            item.setProperty(ColumnItem.TYPE, ttab.elementAt(0));
            dmodel.addRow(item);

            label = new JLabel(bundle.getString("AddTableColumnType"));
            con.weightx = 0.0;
            con.anchor = GridBagConstraints.WEST;
            con.insets = new java.awt.Insets (2, 2, 2, 2);
            con.gridx = 0;
            con.gridy = 1;
            layout.setConstraints(label, con);
            pane.add(label);

            con.fill = GridBagConstraints.HORIZONTAL;
            con.weightx = 1.0;
            con.gridx = 1;
            con.gridy = 1;
            con.insets = new java.awt.Insets (2, 2, 2, 2);
            coltypecombo = new JComboBox(ttab);
            coltypecombo.addActionListener(new ComboBoxListener(dmodel));
            coltypecombo.setName(ColumnItem.TYPE);
            layout.setConstraints(coltypecombo, con);
            pane.add(coltypecombo);

            // Size subpane

            JPanel subpane = new JPanel();
            GridBagLayout sublayout = new GridBagLayout();
            GridBagConstraints subcon = new GridBagConstraints ();
            subpane.setLayout(sublayout);

            // Column size

            label = new JLabel(bundle.getString("AddTableColumnSize"));
            subcon.weightx = 0.14;
            subcon.anchor = GridBagConstraints.WEST;
            subcon.insets = new java.awt.Insets (2, 2, 2, 2);
            subcon.gridx = 0;
            subcon.gridy = 0;
            sublayout.setConstraints(label, subcon);
            subpane.add(label);

            subcon.fill = GridBagConstraints.HORIZONTAL;
            subcon.weightx = 0.36;
            subcon.gridx = 1;
            subcon.gridy = 0;
            subcon.insets = new java.awt.Insets (2, 2, 2, 10);
            colsizefield = new ValidableTextField(new TextFieldValidator.integer());
            colsizefield.setName(ColumnItem.SIZE);
            colsizefield.addFocusListener(intfldlistener);
            sublayout.setConstraints(colsizefield, subcon);
            subpane.add(colsizefield);

            // Column scale

            label = new JLabel(bundle.getString("AddTableColumnScale"));
            subcon.weightx = 0.14;
            subcon.anchor = GridBagConstraints.WEST;
            subcon.insets = new java.awt.Insets (2, 10, 2, 2);
            subcon.gridx = 2;
            subcon.gridy = 0;
            sublayout.setConstraints(label, subcon);
            subpane.add(label);

            subcon.fill = GridBagConstraints.HORIZONTAL;
            subcon.weightx = 0.36;
            subcon.gridx = 3;
            subcon.gridy = 0;
            subcon.insets = new java.awt.Insets (2, 2, 2, 2);
            colscalefield = new ValidableTextField(new TextFieldValidator.integer());
            colscalefield.setName(ColumnItem.SCALE);
            colscalefield.addFocusListener(intfldlistener);
            sublayout.setConstraints(colscalefield, subcon);
            subpane.add(colscalefield);

            // Insert size subpane

            con.weightx = 1.0;
            con.weighty = 0.0;
            con.gridwidth = 2;
            con.fill = GridBagConstraints.BOTH;
            con.insets = new java.awt.Insets (0, 0, 0, 0);
            con.gridx = 0;
            con.gridy = 2;
            layout.setConstraints(subpane, con);
            pane.add(subpane);

            // Column default value

            label = new JLabel(bundle.getString("AddTableColumnDefault"));
            con.weightx = 0.0;
            con.anchor = GridBagConstraints.WEST;
            con.insets = new java.awt.Insets (2, 2, 2, 2);
            con.gridx = 0;
            con.gridy = 3;
            layout.setConstraints(label, con);
            pane.add(label);

            con.fill = GridBagConstraints.HORIZONTAL;
            con.weightx = 1.0;
            con.gridx = 1;
            con.gridy = 3;
            con.insets = new java.awt.Insets (2, 2, 2, 2);
            defvalfield = new JTextField(35);
            defvalfield.setName(ColumnItem.DEFVAL);
            defvalfield.addFocusListener(fldlistener);
            layout.setConstraints(defvalfield, con);
            pane.add(defvalfield);

            // Check subpane

            subpane = new JPanel();
            subpane.setBorder(new TitledBorder(bundle.getString("AddTableColumnConstraintsTitle")));
            sublayout = new GridBagLayout();
            subcon = new GridBagConstraints ();
            subpane.setLayout(sublayout);

            ActionListener cbxlistener = new CheckBoxListener(dmodel);

            subcon.weightx = 0.0;
            subcon.anchor = GridBagConstraints.WEST;
            subcon.gridx = 0;
            subcon.gridy = 0;
            pkcheckbox = new JCheckBox(bundle.getString("AddTableColumnConstraintPKTitle"));
            pkcheckbox.setName(ColumnItem.PRIMARY_KEY);
            pkcheckbox.addActionListener(cbxlistener);
            sublayout.setConstraints(pkcheckbox, subcon);
            subpane.add(pkcheckbox);

            subcon.gridx = 1;
            subcon.gridy = 0;
            uniquecheckbox = new JCheckBox(bundle.getString("AddTableColumnConstraintUniqueTitle"));
            sublayout.setConstraints(uniquecheckbox, subcon);
            uniquecheckbox.setName(ColumnItem.UNIQUE);
            uniquecheckbox.addActionListener(cbxlistener);
            subpane.add(uniquecheckbox);

            subcon.gridx = 2;
            subcon.gridy = 0;
            nullcheckbox = new JCheckBox(bundle.getString("AddTableColumnConstraintNullTitle"));
            sublayout.setConstraints(nullcheckbox, subcon);
            nullcheckbox.setName(ColumnItem.NULLABLE);
            nullcheckbox.addActionListener(cbxlistener);
            subpane.add(nullcheckbox);

            // Insert subpane

            con.weightx = 1.0;
            con.weighty = 0.0;
            con.gridwidth = 2;
            con.fill = GridBagConstraints.BOTH;
            con.insets = new java.awt.Insets (0, 0, 0, 0);
            con.gridx = 0;
            con.gridy = 4;
            layout.setConstraints(subpane, con);
            pane.add(subpane);

            // Index name combo

            con.gridx = 0;
            con.gridy = 5;
            con.gridwidth = 1;
            ixcheckbox = new JCheckBox(bundle.getString("AddTableColumnConstraintIXTitle"));
            layout.setConstraints(ixcheckbox, con);
            ixcheckbox.setName(ColumnItem.INDEX);
            ixcheckbox.addActionListener(cbxlistener);
            pane.add(ixcheckbox);

            try {
                DatabaseMetaData dmd = nfo.getSpecification().getMetaData();
                String catalog = (String)nfo.get(DatabaseNode.CATALOG);
                String table = (String)nfo.get(DatabaseNode.TABLE);
                DriverSpecification drvSpec = nfo.getDriverSpecification();

                //				ResultSet rs = dmd.getIndexInfo(catalog,nfo.getUser(),table, true, false);

                drvSpec.getIndexInfo(catalog, dmd, table, true, false);

                //if (dmd.getDatabaseProductName().trim().equals("ACCESS"))
                //	rs = dmd.getIndexInfo(catalog, null, table, true, false);
                //else
                //	rs = dmd.getIndexInfo(catalog, dmd.getUserName(), table, true, false);

                ixmap = new HashMap();
                while (drvSpec.rs.next()) {
                    String ixname = drvSpec.rs.getString("INDEX_NAME");
                    if (ixname != null) {
                        Vector ixcols = (Vector)ixmap.get(ixname);
                        if (ixcols == null) {
                            ixcols = new Vector();
                            ixmap.put(ixname,ixcols);
                        }

                        ixcols.add(drvSpec.rs.getString("COLUMN_NAME"));
                    }
                }
                drvSpec.rs.close();
            } catch (Exception e) {
                //
            }

            con.fill = GridBagConstraints.HORIZONTAL;
            con.weightx = 1.0;
            con.gridx = 1;
            con.gridy = 5;
            con.insets = new java.awt.Insets (2, 2, 2, 2);
            idxcombo = new JComboBox(new Vector(ixmap.keySet()));
            layout.setConstraints(idxcombo, con);
            pane.add(idxcombo);

            // Check title and textarea

            con.gridx = 0;
            con.gridy = 6;
            checkcheckbox = new JCheckBox(bundle.getString("AddTableColumnConstraintCheckTitle"));
            layout.setConstraints(checkcheckbox, con);
            checkcheckbox.setName(ColumnItem.CHECK);
            checkcheckbox.addActionListener(cbxlistener);
            pane.add(checkcheckbox);

            con.fill = GridBagConstraints.BOTH;
            con.insets = new java.awt.Insets (0,0,0,0);
            con.gridwidth = 2;
            con.gridheight = 1;
            con.weightx = 1.0;
            con.weighty = 1.0;
            con.gridx =0;
            con.gridy = 7;
            checkfield = new JTextArea(3, 35);
            checkfield.setName(ColumnItem.CHECK_CODE);
            checkfield.addFocusListener(fldlistener);
            JScrollPane spane = new JScrollPane(checkfield);
            layout.setConstraints(spane, con);
            pane.add(spane);

            if (ixmap.size() == 0) {
                pkcheckbox.setEnabled(false);
                ixcheckbox.setEnabled(false);
                idxcombo.setEnabled(false);
            }

            item.addPropertyChangeListener(new PropertyChangeListener() {
                                               public void propertyChange(PropertyChangeEvent evt) {
                                                   String pname = evt.getPropertyName();
                                                   Object nval = evt.getNewValue();
                                                   if (nval instanceof Boolean) {
                                                       boolean set = ((Boolean)nval).booleanValue();
                                                       if (pname.equals(ColumnItem.PRIMARY_KEY)) {
                                                           pkcheckbox.setSelected(set);
                                                       } else if (pname.equals(ColumnItem.INDEX)) {
                                                           ixcheckbox.setSelected(set);
                                                       } else if (pname.equals(ColumnItem.UNIQUE)) {
                                                           uniquecheckbox.setSelected(set);
                                                       } else if (pname.equals(ColumnItem.NULLABLE)) {
                                                           nullcheckbox.setSelected(set);
                                                       }
                                                   }
                                               }
                                           });

            ActionListener listener = new ActionListener() {
                                          public void actionPerformed(ActionEvent event) {
                                              if (event.getSource() == DialogDescriptor.OK_OPTION) {
                                                  result = validate();

                                                  CommandBuffer cbuff = new CommandBuffer();

                                                  if (result) {
                                                      try {
                                                          boolean use_idx = false;
                                                          String tablename = nfo.getTable();
                                                          colname = colnamefield.getText();
                                                          ColumnItem citem = (ColumnItem)dmodel.getData().elementAt(0);
                                                          AddColumn cmd = spec.createCommandAddColumn(tablename);
                                                          org.netbeans.lib.ddl.impl.TableColumn col = null;
                                                          if (citem.isPrimaryKey()) {
                                                              col = (org.netbeans.lib.ddl.impl.TableColumn)cmd.createPrimaryKeyColumn(colname);
                                                          } else if (citem.isUnique()) {
                                                              col = (org.netbeans.lib.ddl.impl.TableColumn)cmd.createUniqueColumn(colname);
                                                          } else col = (org.netbeans.lib.ddl.impl.TableColumn)cmd.createColumn(colname);
                                                          if (citem.isIndexed()) use_idx = true;
                                                          col.setColumnType(Specification.getType(citem.getType().getType()));
                                                          col.setColumnSize(citem.getSize());
                                                          col.setDecimalSize(citem.getScale());
                                                          col.setNullAllowed(citem.allowsNull());
                                                          if (citem.hasDefaultValue()) col.setDefaultValue(citem.getDefaultValue());
                                                          cbuff.add(cmd);

                                                          if (citem.hasCheckConstraint()) {
                                                              cmd.createCheckConstraint(colname, citem.getCheckConstraint());
                                                          }

                                                          if (use_idx) {

                                                              String idxname = (String)idxcombo.getSelectedItem();
                                                              if (ixmap.containsKey(idxname)) {
                                                                  cbuff.add(spec.createCommandDropIndex(idxname));
                                                              }

                                                              CreateIndex xcmd = spec.createCommandCreateIndex(tablename);
                                                              xcmd.setIndexName(idxname);
                                                              Enumeration enu = ((Vector)ixmap.get(idxname)).elements();
                                                              while (enu.hasMoreElements()) {
                                                                  xcmd.specifyColumn((String)enu.nextElement());
                                                              }
                                                              xcmd.specifyColumn(citem.getName());
                                                              cbuff.add(xcmd);
                                                          }

                                                          cbuff.execute();
                                                      } catch (Exception e) {
                                                          e.printStackTrace();
                                                          result = false;
                                                      }
                                                  }
                                              } else result = true;

                                              if (result) {
                                                  dialog.setVisible(false);
                                                  dialog.dispose();
                                              } else Toolkit.getDefaultToolkit().beep();
                                          }
                                      };

            DialogDescriptor descriptor = new DialogDescriptor(pane, bundle.getString("AddColumnDialogTitle"), true, listener);
            dialog = TopManager.getDefault().createDialog(descriptor);
            dialog.setResizable(true);
        } catch (MissingResourceException e) {
            e.printStackTrace();
        }
    }

    public boolean run() {
        if (dialog != null) dialog.setVisible(true);
        return result;
    }

    private boolean validate() {
        Vector cols = dmodel.getData();
        Enumeration colse = cols.elements();
        while(colse.hasMoreElements())
            if (!((ColumnItem)colse.nextElement()).validate())
                return false;

        return true;
    }

    public String getColumnName() {
        return colname;
    }

    class CheckBoxListener implements ActionListener {
        private DataModel data;

        CheckBoxListener(DataModel data) {
            this.data = data;
        }

        public void actionPerformed(ActionEvent event) {
            JCheckBox cbx = (JCheckBox)event.getSource();
            String code = cbx.getName();
            data.setValue(new Boolean(cbx.isSelected()), code, 0);
        }
    }

    class ComboBoxListener implements ActionListener {
        private DataModel data;

        ComboBoxListener(DataModel data) {
            this.data = data;
        }

        public void actionPerformed(ActionEvent event) {
            JComboBox cbx = (JComboBox)event.getSource();
            String code = cbx.getName();
            data.setValue(cbx.getSelectedItem(), code, 0);
        }
    }

    class TextFieldListener implements FocusListener {
        private DataModel data;

        TextFieldListener(DataModel data) {
            this.data = data;
        }

        public void focusGained(FocusEvent event) {
        }

        public void focusLost(FocusEvent event) {
            JTextComponent fld = (JTextComponent)event.getSource();
            String code = fld.getName();
            data.setValue(fld.getText(), code, 0);
        }
    }

    class IntegerFieldListener implements FocusListener {
        private DataModel data;

        IntegerFieldListener(DataModel data) {
            this.data = data;
        }

        public void focusGained(FocusEvent event) {
        }

        public void focusLost(FocusEvent event) {
            JTextComponent fld = (JTextComponent)event.getSource();
            String code = fld.getName();
            String numero = fld.getText();
            Integer ival;
            if (numero == null || numero.length()==0) numero = "0";
            ival = new Integer(numero);
            data.setValue(ival, code, 0);
        }
    }
}
/*
 * <<Log>>
 *  12   Gandalf   1.11        3/3/00   Radko Najman    scale field added
 *  11   Gandalf   1.10        2/16/00  Radko Najman    driver adaptor
 *  10   Gandalf   1.9         11/15/99 Radko Najman    MS ACCESS
 *  9    Gandalf   1.8         10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  8    Gandalf   1.7         10/8/99  Radko Najman    getUser() method 
 *       replaced by dmd.getUserName()
 *  7    Gandalf   1.6         9/13/99  Slavek Psenicka 
 *  6    Gandalf   1.5         9/13/99  Slavek Psenicka 
 *  5    Gandalf   1.4         9/8/99   Slavek Psenicka adaptor changes
 *  4    Gandalf   1.3         7/21/99  Slavek Psenicka Disabling index combo 
 *       with no index in place
 *  3    Gandalf   1.2         6/9/99   Ian Formanek    ---- Package Change To 
 *       org.openide ----
 *  2    Gandalf   1.1         5/21/99  Slavek Psenicka new version
 *  1    Gandalf   1.0         5/14/99  Slavek Psenicka 
 * $
 */
