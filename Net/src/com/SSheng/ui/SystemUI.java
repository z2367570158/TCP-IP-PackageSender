package com.SSheng.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;
import jpcapBackstage.Backstage;

public class SystemUI extends JFrame {

	private JButton bIP;
	private JButton bTCP;
	private JButton bUDP;
	private JButton bHTTP;
	private JButton bARP;
	private JPanel westPanel, centerPanel, southPanel;
	private JLabel time;
	public Backstage bs;

	public static void main(String[] args) throws IOException {

		new SystemUI();
	}
	public SystemUI() throws IOException {
		bs = new Backstage();
		bs.CaptureStart();
		initClient();
	}

	public void initClient() {
		this.setTitle("SSender ver0.01");
		this.setSize(UiConfig.FRAME_WIDTH, UiConfig.FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);// 居中显示
		this.setUndecorated(false);// 禁用此窗体装饰

		westPanel = createwestPanel();
		this.add(westPanel, BorderLayout.WEST);

		centerPanel = new PanelWelcome();
		this.add(centerPanel, BorderLayout.CENTER);

		southPanel = createsouthPanel();
		this.add(southPanel, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	public JPanel createnorthPanel() {
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		return northPanel;
	}

	public JPanel createcenterPanel() {
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());

		return centerPanel;
	}

	/**
	 * 西边面板的构造方法
	 * 
	 * @return 返回创建的西边面板
	 */
	public JPanel createwestPanel() {
		JPanel westPanel = new JPanel();
		westPanel.setPreferredSize(new Dimension(UiConfig.WEST_PANEL_WIDTH,
				UiConfig.FRAME_HEIGHT));
		westPanel.setBackground(Color.gray);

		bIP = new JButton("IP");
		bIP.setPreferredSize(new Dimension(UiConfig.WEST_PANEL_BUTTONS_WIDTH,
				UiConfig.WEST_PANEL_BUTTONS_HEIGHT));
		bIP.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// changeCenterPanel(new PanelIP(SystemUI.this));
				IPPacket ip = new IPPacket();
				try {
					ip.setIPv4Parameter(0, false, false, false, 0, false,
							false, false, 0, 0, 16, 230,
							InetAddress.getByName("192.168.0.101"),
							InetAddress.getByName("192.168.0.113"));
					ip.data="what the fuck".getBytes();
					EthernetPacket ether=new EthernetPacket();
					ether.frametype=EthernetPacket.ETHERTYPE_IP;
					ether.src_mac=Backstage.getMacBytes("9c:4e:36:4d:cd:e4");
					ether.dst_mac=Backstage.getMacBytes("9c:4e:36:4d:cd:e4");
					ip.datalink=ether;

					while(true)
						bs.sender.sendPacket(ip);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}

			}
		});

		bTCP = new JButton("TCP");
		bTCP.setPreferredSize(new Dimension(UiConfig.WEST_PANEL_BUTTONS_WIDTH,
				UiConfig.WEST_PANEL_BUTTONS_HEIGHT));
		bTCP.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
				changeCenterPanel(new PanelTCP(SystemUI.this));
				
				changeCenterPanel(new PanelTCP(SystemUI.this));
				
			}
		});

		bUDP = new JButton("UDP");
		bUDP.setPreferredSize(new Dimension(UiConfig.WEST_PANEL_BUTTONS_WIDTH,
				UiConfig.WEST_PANEL_BUTTONS_HEIGHT));
		bUDP.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.out.println("UDP面板");
				changeCenterPanel(new PanelUDP(SystemUI.this));
			}
		});

		bHTTP = new JButton("HTTP");
		bHTTP.setPreferredSize(new Dimension(UiConfig.WEST_PANEL_BUTTONS_WIDTH,
				UiConfig.WEST_PANEL_BUTTONS_HEIGHT));
		bHTTP.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				System.out.println("HTTP面板");
				changeCenterPanel(new PanelHTTP(SystemUI.this));
			}
		});

		bARP = new JButton("ARP");
		bARP.setPreferredSize(new Dimension(UiConfig.WEST_PANEL_BUTTONS_WIDTH,
				UiConfig.WEST_PANEL_BUTTONS_HEIGHT));
		bARP.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.out.println("ARP面板");
				changeCenterPanel(new PanelARP(SystemUI.this));
			}
		});
		westPanel.add(bARP);
//		westPanel.add(bIP);
		westPanel.add(bTCP);
		westPanel.add(bUDP);
//		westPanel.add(bHTTP);

		return westPanel;
	}

	public JPanel createeastPanel() {
		JPanel eastPanel = new JPanel();
		return eastPanel;
	}

	public JPanel createsouthPanel() {
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.gray);
		time = new JLabel();
		new Thread(new Runnable() {

			public void run() {
				while (true) {
					Date date = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy/MM/dd HH:mm:ss");
					time.setText(dateFormat.format(date));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();

		southPanel.add(time);
		return southPanel;
	}

	public void setCenterPanel(JPanel centerPanel) {
		this.centerPanel = centerPanel;
	}

	public JPanel getCenterPanel() {
		return centerPanel;
	}

	public void changeCenterPanel(JPanel jp) {
		SystemUI.this.remove(centerPanel);
		centerPanel = jp;
		SystemUI.this.add(centerPanel, BorderLayout.CENTER);
		SystemUI.this.validate();
	}
}
