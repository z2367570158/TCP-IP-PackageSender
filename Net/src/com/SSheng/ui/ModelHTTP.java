package com.SSheng.ui;


import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ModelHTTP extends AbstractTableModel {

    private static final long serialVersionUID = -7495940408592595397L;

    private Vector content = null;

    private String[] title_name = { "工号", "姓名","性别","电话","学历","年龄" };

    public ModelHTTP() {
        content = new Vector();
    }

    public ModelHTTP(int count) {
        content = new Vector(count);
    }

    public void addRow(String snum,String sname,String spassw,String sphone,String sgen,String sauth) {
        Vector v = new Vector(6);
        v.add(0, snum);
        v.add(1, sname);
        v.add(2, spassw);
        v.add(3, sphone);
        v.add(4,sgen);
        v.add(5,sauth);
        content.add(v);
    }

    public void removeRow(int row) {
        content.remove(row);
    }
    public void removeRows(int row, int count) {
        for (int i = 0; i < count; i++) {
            if (content.size() > row) {
                content.remove(row);
            }
        }
    }
    public String getColumnName(int col) {
        return title_name[col];
    }
    
    public int getColumnCount() {
        return title_name.length;
    }

    public int getRowCount() {
        return content.size();
    }

    public Object getValueAt(int row, int col) {
        return ((Vector) content.get(row)).get(col);
    }

  
}

