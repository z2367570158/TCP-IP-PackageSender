package com.SSheng.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class PanelHTTP extends JPanel{

	private SystemUI systemUI;

	private JLabel name, gender, phone, age, edu;
	private JTextField nameT, ageT, phoneT;
	private ButtonGroup bg;
	private JRadioButton jrbman, jrbwomen;
	private JComboBox<String> eduC;
	private JButton back, create;

	public PanelHTTP(SystemUI s) {

		systemUI = s;

		this.setLayout(null);
		this.setPreferredSize(new Dimension(UiConfig.EAST_PANEL_WIDTH,
				UiConfig.EAST_PANEL_HEIGET));
		name = new JLabel("姓名");
		name.setBounds(100, 80, 70, 25);
		nameT = new JTextField();
		nameT.setBounds(160, 80, 200, 25);

		age = new JLabel("年龄");
		age.setBounds(100, 120, 70, 25);
		ageT = new JTextField();
		ageT.setBounds(160, 120, 200, 25);

		phone = new JLabel("联系电话");
		phone.setBounds(100, 160, 70, 25);
		phoneT = new JTextField();
		phoneT.setBounds(160, 160, 200, 25);

		edu = new JLabel("学历");
		edu.setBounds(100, 200, 70, 25);
		eduC = new JComboBox<String>();
		eduC.addItem("高中");
		eduC.addItem("大专");
		eduC.addItem("本科");
		eduC.addItem("研究生");
		eduC.setBounds(160, 200, 70, 25);

		bg = new ButtonGroup();

		gender = new JLabel("性别");
		gender.setBounds(100, 240, 200, 25);
		jrbman = new JRadioButton("男", true);
		jrbman.setBounds(180, 240, 100, 20);
		jrbwomen = new JRadioButton("女");
		jrbwomen.setBounds(280, 240, 100, 20);

		create = new JButton("创建");
		create.setBounds(250, 380, UiConfig.BUTTON_WIDTH,
				UiConfig.BUTTON_HEIGHT);
		create.addActionListener(new ActionListener() {

			PanelHTTP p = PanelHTTP.this;

			public void actionPerformed(ActionEvent arg0) {

				String name = p.nameT.getText();
				String age = p.ageT.getText();
				String phone = p.phoneT.getText();
				String edu = p.eduC.getSelectedItem().toString();
				String gender = "男";
				if (p.jrbwomen.isSelected())
					gender = "女";
				if (name.length() != 0 && age.length() != 0
						&& phone.length() != 0 && edu.length() != 0
						&& gender.length() != 0) {


				} else {
					JOptionPane.showMessageDialog(p, "信息项不能为空");
				}
			}
		});

		back = new JButton("返回");
		back.setBounds(350, 380, UiConfig.BUTTON_WIDTH, UiConfig.BUTTON_HEIGHT);
		back.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				systemUI.changeCenterPanel(new PanelWelcome());
			}
		});

		this.add(name);
		this.add(nameT);
		this.add(age);
		this.add(ageT);
		this.add(edu);
		this.add(eduC);
		this.add(phone);
		this.add(phoneT);
		this.add(gender);
		this.add(jrbman);
		this.add(jrbwomen);
		this.add(create);
		this.add(back);
		bg.add(jrbman);
		bg.add(jrbwomen);

	}

}
