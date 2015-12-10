package com.SSheng.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PanelUDP extends JPanel {
	SystemUI systemUI;
	private JButton change;
	private JButton back;
	private JButton delete;
	private JLabel searchL;
	private JTextField searchT;

	public PanelUDP(SystemUI systemui) {
		this.systemUI = systemui;

		this.setPreferredSize(new Dimension(300, 300));
		// this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(null);
		this.setFont(new Font("΢���ź�", Font.BOLD, 28));

		searchL = new JLabel("������Ա����:");
		searchL.setBounds(80, 100, 100, 100);
		searchT = new JTextField();
		searchT.setBounds(100, 200, 250, 40);
		searchT.addKeyListener(new KeyAdapter() {// ����ֻ����������
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();
				if (keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9) {
				} else {
					e.consume(); // �ؼ������ε��Ƿ�����
				}
			}
		});
		change = new JButton("�޸�");
		change.setBounds(150, 380, UiConfig.BUTTON_WIDTH,
				UiConfig.BUTTON_HEIGHT);
		change.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String snum = searchT.getText(); // ��ȡԱ����
				if (snum.length() != 0) {
					try {
						int num = Integer.parseInt(snum);
						
						
					} catch (java.lang.NumberFormatException e) {
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "��Ϣ���Ϊ��");
				}
			}
		});

		delete = new JButton("ɾ��");
		delete.setBounds(250, 380, UiConfig.BUTTON_WIDTH,
				UiConfig.BUTTON_HEIGHT);
		delete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String snum = searchT.getText(); // ��ȡԱ����
				if (snum.length() != 0) {

				} else {
					JOptionPane.showMessageDialog(null, "��Ϣ���Ϊ��");
				}
			}
		});
		back = new JButton("����");
		back.setBounds(350, 380, UiConfig.BUTTON_WIDTH, UiConfig.BUTTON_HEIGHT);
		back.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				systemUI.changeCenterPanel(new PanelWelcome());
			}
		});

		this.add(back);
		this.add(delete);
		this.add(change);
		this.add(searchT);
		this.add(searchL);

	}
}
