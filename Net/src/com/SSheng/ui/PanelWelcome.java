package com.SSheng.ui;

import java.awt.Color;
import java.awt.Font;

import javax.print.ServiceUI;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelWelcome extends JPanel {

	public PanelWelcome() {
		this.setLayout(null);
		JLabel tlogo = new JLabel("Welcome");
		tlogo.setFont(new Font("΢���ź�", Font.BOLD, 28));// ���ñ�ǩ����
		tlogo.setForeground(Color.black);// ����������ɫ
		tlogo.setHorizontalAlignment(JLabel.CENTER);
		tlogo.setVerticalAlignment(JLabel.CENTER);
		tlogo.setBounds(100, 200, 500, 100);
		
		
		
		this.add(tlogo);
		this.setOpaque(false);

	}
}
