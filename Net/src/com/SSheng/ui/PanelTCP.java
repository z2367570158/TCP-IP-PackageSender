package com.SSheng.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;
import jpcapBackstage.Backstage;

public class PanelTCP extends JPanel {
	private SystemUI systemUI;
	private JLabel lSrcIP, lSrcMac, lDstMac, lDstIP, lTimes, lsrcPort,
			ldstPort, lsequence, lackNum, lwindow, lurgent, lurg, lack, lpsh,
			lrst, lsyn, lfin;
	private JTextField tSrcIP, tSrcMac, tDstIP, tDstMac, tTimes, tsrcPort,
			tdstPort, tsequence, tackNum, twindow, turgent;
	private JButton back, create;
	private JRadioButton urg, ack, psh, rst, syn, fin;
	private boolean flag = false;

	public PanelTCP(SystemUI s) {

		systemUI = s;

		this.setLayout(null);
		this.setPreferredSize(new Dimension(UiConfig.EAST_PANEL_WIDTH,
				UiConfig.EAST_PANEL_HEIGET));
		lSrcIP = new JLabel("源IP地址");
		lSrcIP.setBounds(70, 80, 110, 25);
		tSrcIP = new JTextField();
		tSrcIP.setBounds(160, 80, 200, 25);
		tSrcIP.setText(new String(systemUI.bs.devices[0].addresses[1].address
				.toString().substring(1)));
		lsrcPort = new JLabel("端口");
		lsrcPort.setBounds(370, 80, 70, 25);
		tsrcPort = new JTextField();
		tsrcPort.setBounds(420, 80, 70, 25);
		tsrcPort.setText("80");

		lSrcMac = new JLabel("源Mac地址");
		lSrcMac.setBounds(70, 120, 110, 25);
		tSrcMac = new JTextField();
		tSrcMac.setBounds(160, 120, 200, 25);
		tSrcMac.setText(Backstage
				.getMacString(systemUI.bs.devices[0].mac_address));

		lDstIP = new JLabel("目标IP地址");
		lDstIP.setBounds(70, 160, 110, 25);
		tDstIP = new JTextField();
		tDstIP.setBounds(160, 160, 200, 25);
		tDstIP.setText("192.168.1.133");
		ldstPort = new JLabel("端口");
		ldstPort.setBounds(370, 160, 70, 25);
		tdstPort = new JTextField();
		tdstPort.setBounds(420, 160, 70, 25);
		tdstPort.setText("80");

		lDstMac = new JLabel("目标MAC地址");
		lDstMac.setBounds(70, 200, 110, 25);
		tDstMac = new JTextField();
		tDstMac.setBounds(160, 200, 200, 25);
		tDstMac.setText("00:1a:6b:4a:40:c7");

		lsequence = new JLabel("seq序号");
		lsequence.setBounds(70, 240, 70, 25);
		tsequence = new JTextField();
		tsequence.setBounds(160, 240, 40, 25);
		tsequence.setText("1");

		lackNum = new JLabel("ack序号");
		lackNum.setBounds(220, 240, 70, 25);
		tackNum = new JTextField();
		tackNum.setBounds(280, 240, 40, 25);
		tackNum.setText("1");

		lwindow = new JLabel("窗口大小");
		lwindow.setBounds(340, 240, 100, 25);
		twindow = new JTextField();
		twindow.setBounds(400, 240, 40, 25);
		twindow.setText("1");

		lurgent = new JLabel("紧急指针");
		lurgent.setBounds(440, 240, 100, 25);
		turgent = new JTextField();
		turgent.setBounds(500, 240, 40, 25);
		turgent.setText("1");

		// urg, ack, psh, rst, syn, fin;
		lurg = new JLabel("urg");
		lurg.setBounds(70, 280, 20, 25);
		urg = new JRadioButton();
		urg.setBounds(100, 280, 70, 25);

		lack = new JLabel("ack");
		lack.setBounds(170, 280, 70, 25);
		ack = new JRadioButton();
		ack.setBounds(200, 280, 70, 25);

		lpsh = new JLabel("psh");
		lpsh.setBounds(270, 280, 70, 25);
		psh = new JRadioButton();
		psh.setBounds(300, 280, 70, 25);

		lrst = new JLabel("rst");
		lrst.setBounds(370, 280, 70, 25);
		rst = new JRadioButton();
		rst.setBounds(400, 280, 70, 25);

		lsyn = new JLabel("syn");
		lsyn.setBounds(470, 280, 70, 25);
		syn = new JRadioButton();
		syn.setBounds(500, 280, 70, 25);

		lfin = new JLabel("fin");
		lfin.setBounds(570, 280, 70, 25);
		fin = new JRadioButton();
		fin.setBounds(600, 280, 70, 25);

		lTimes = new JLabel("发送次数");
		lTimes.setBounds(70, 320, 110, 25);
		tTimes = new JTextField();
		tTimes.setBounds(160, 320, 70, 25);
		tTimes.setText("1");
		tTimes.addKeyListener(new KeyAdapter() {// 限制只能输入数字
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();
				if ((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)
						|| keyChar == KeyEvent.VK_MINUS) {
				} else {
					e.consume(); // 关键，屏蔽掉非法输入
				}
			}
		});

		create = new JButton("创建");
		create.setBounds(250, 380, UiConfig.BUTTON_WIDTH,
				UiConfig.BUTTON_HEIGHT);
		create.addActionListener(new ActionListener() {

			PanelTCP p = PanelTCP.this;

			public void actionPerformed(ActionEvent arg0) {
				String strSrcIP = tSrcIP.getText();
				String strSrcMac = tSrcMac.getText();
				String strDecIP = tDstIP.getText();
				String strDecMac = tDstMac.getText();
				String strsrcPort = tsrcPort.getText();
				String strdstPort = tdstPort.getText();
				String strsequence = tsequence.getText();
				String strackNum = tackNum.getText();
				String strwindow = twindow.getText();
				String strurgent = turgent.getText();

				String stime = tTimes.getText();
				if (strSrcIP.length() != 0 || strSrcMac.length() != 0
						|| strDecIP.length() != 0 || strDecMac.length() != 0
						|| stime.length() != 0) {
					try {
						final int times = Integer.parseInt(stime);
						// int src_port, int dst_port, long sequence, long
						// ack_num, boolean urg, boolean ack, boolean psh,
						// boolean rst, boolean syn, boolean fin, boolean rsv1,
						// boolean rsv2, int window, int urgent
						final TCPPacket p = new TCPPacket(Integer
								.parseInt(strsrcPort), Integer
								.parseInt(strdstPort), Integer
								.parseInt(strsequence), Integer
								.parseInt(strackNum), urg.isSelected(), ack
								.isSelected(), psh.isSelected(), rst
								.isSelected(), syn.isSelected(), fin
								.isSelected(), false, false, Integer
								.parseInt(strwindow), Integer
								.parseInt(strurgent));
						try {
							p.setIPv4Parameter(0, false, false, false, 0,
									false, false, false, 0, 1010101, 100,
									IPPacket.IPPROTO_TCP,
									InetAddress.getByName(strSrcIP),
									InetAddress.getByName(strDecIP));
						} catch (UnknownHostException e) {
							e.printStackTrace();
						}
						p.data = ("abcdefghijklmnopqrstuvwxyz").getBytes();

						EthernetPacket ether = new EthernetPacket();
						ether.frametype = EthernetPacket.ETHERTYPE_IP;
						ether.src_mac = Backstage.getMacBytes(strSrcMac);
						ether.dst_mac = Backstage.getMacBytes(strDecMac);
						p.datalink = ether;
						systemUI.bs.sender.sendPacket(p);
						new Thread(new Runnable() {

							public void run() {
								if (times >= 0) {
									for (int i = 0; i < times; i++) {
										systemUI.bs.sender.sendPacket(p);
									}

								} else {
									flag = true;
									while (flag) {
										try {
											Thread.sleep(300);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										systemUI.bs.sender.sendPacket(p);
									}
								}
							}
						}).start();

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(p, "信息项不能为空");
				}
			}
		});

		back = new JButton("返回");
		back.setBounds(350, 380, UiConfig.BUTTON_WIDTH, UiConfig.BUTTON_HEIGHT);
		back.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				flag = false;
				systemUI.changeCenterPanel(new PanelWelcome());
			}
		});

		this.add(lSrcIP);
		this.add(tSrcIP);
		this.add(lsrcPort);
		this.add(tsrcPort);
		this.add(lSrcMac);
		this.add(tSrcMac);
		this.add(lDstIP);
		this.add(tDstIP);
		this.add(ldstPort);
		this.add(tdstPort);
		this.add(lDstMac);
		this.add(tDstMac);
		this.add(lsequence);
		this.add(tsequence);
		this.add(lackNum);
		this.add(tackNum);
		this.add(lwindow);
		this.add(twindow);
		this.add(lurgent);
		this.add(turgent);
		this.add(lurg);
		this.add(urg);
		this.add(lack);
		this.add(ack);
		this.add(lpsh);
		this.add(psh);
		this.add(lrst);
		this.add(rst);
		this.add(lsyn);
		this.add(syn);
		this.add(lfin);
		this.add(fin);

		this.add(lTimes);
		this.add(tTimes);
		this.add(create);
		this.add(back);
	}
}
