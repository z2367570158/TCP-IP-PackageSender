package com.SSheng.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;


public class TableFrame extends JFrame{
	
	
	private JScrollPane s_pan = null; // 滚动块
	private JTable table = null;
	private ModelHTTP model = null;
	
	public static void main(String[] args) {
		new TableFrame();
	}
	
	public TableFrame(){
		
		model = new ModelHTTP(10);
		table = new JTable(model) { // 重写getCellRenderer方法，设置表格中内容居中显示
			public TableCellRenderer getCellRenderer(int row, int column) {
				TableCellRenderer renderer = super.getCellRenderer(row, column);
				if (renderer instanceof JLabel) {
					((JLabel) renderer).setHorizontalAlignment(JLabel.CENTER);
				}
				return renderer;
			}
		};
		table.setBackground(Color.white);
		
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(40); // 设置表格每列的宽度
		tcm.getColumn(1).setPreferredWidth(40);
		tcm.getColumn(2).setPreferredWidth(90);
		tcm.getColumn(3).setPreferredWidth(100);
		tcm.getColumn(4).setPreferredWidth(40);
		tcm.getColumn(5).setPreferredWidth(40);

		s_pan = new JScrollPane(table); // 将表格加入滚动框
		
		
		this.setTitle("查询结果");
		this.setDefaultCloseOperation(3);
		this.getContentPane().add(s_pan, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(600, 400);
		this.setLocationRelativeTo(null); // 设置窗体居中显示
		this.setResizable(false);
		this.setVisible(true);
	}
	public void addData(String snum, String sname, String gender, String phone,
			String edu, String age) {
		model.addRow(snum, sname, gender, phone, edu, age);
		table.updateUI();
	}
}
