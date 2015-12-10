package com.SSheng.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import jpcapBackstage.Backstage;
import sample.ARP;

/**
static final short ARP_REPLY=2	ARPӦ��
static final short ARP_REQUEST=1	ARP����
short hardtype	Ӳ������
static final short HARDTYPE_ETHER=1	Ӳ�����ͣ���̫��
static final short
 HARDTYPE_FRAMERELAY	Ӳ�����ͣ�֡�м�
static final short
 HARDTYPE_IEEE802=6	Ӳ�����ͣ����ƻ�
short hlen	Ӳ����ַ����
static final short INV_REPLY=9	Identify peer Ӧ��
static final short INV_REQUEST=8	Identify peer ����
short operation	�����ֶΣ�ָ�����ֲ�������
short plen	Э���ַ����
short prototype	Э������
static final short PROTOTYPE_IP=2048	Э�����ͣ�IP
static final short RARP_REPLY=4	RARPӦ��
static final short RARP_REQUEST=3	RARP����
byte[] sender_hardaddr	���Ͷ���̫����ַ
byte[] sender_protoaddr	���Ͷ�IP��ַ
byte[] target_hardaddr	Ŀ����̫����ַ
byte[] target_protoaddr	Ŀ��IP��ַ
**/
public class PanelARP extends JPanel{

	private SystemUI systemUI;

	private JLabel lSrcIP, lSrcMac, lDecMac, lDecIP,lTimes;
	private JTextField tSrcIP, tSrcMac, tDecIP, tDecMac,tTimes;
	private JButton back, create;
	private boolean flag = false;
	public PanelARP(SystemUI s) {

		systemUI = s;

		this.setLayout(null);
		this.setPreferredSize(new Dimension(UiConfig.EAST_PANEL_WIDTH,
				UiConfig.EAST_PANEL_HEIGET));
		lSrcIP = new JLabel("ԴIP��ַ");
		lSrcIP.setBounds(70, 80, 110, 25);
		tSrcIP = new JTextField();
		tSrcIP.setBounds(160, 80, 200, 25);
		tSrcIP.setText(new String(systemUI.bs.devices[1].addresses[1].address.toString().substring(1)));

		lSrcMac = new JLabel("ԴMac��ַ");
		lSrcMac.setBounds(70, 120, 110, 25);
		tSrcMac = new JTextField();
		tSrcMac.setBounds(160, 120, 200, 25);
		tSrcMac.setText(Backstage.getMacString(systemUI.bs.devices[1].mac_address));
		
		lDecIP = new JLabel("Ŀ��IP��ַ");
		lDecIP.setBounds(70, 160, 110, 25);
		tDecIP = new JTextField();
		tDecIP.setBounds(160, 160, 200, 25);
		tDecIP.setText("192.168.1.133");

		lDecMac = new JLabel("Ŀ��MAC��ַ");
		lDecMac.setBounds(70, 200, 110, 25);
		tDecMac = new JTextField();
		tDecMac.setBounds(160, 200, 200, 25);
		tDecMac.setText("00:1a:6b:4a:40:c7");
		
		lTimes = new JLabel("���ʹ���");
		lTimes.setBounds(70, 240, 110, 25);
		tTimes = new JTextField();
		tTimes.setBounds(160, 240, 70, 25);
		tTimes.setText("1");
		tTimes.addKeyListener(new KeyAdapter() {// ����ֻ����������
			public void keyTyped(KeyEvent e) {
				int keyChar = e.getKeyChar();
				if ((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)||keyChar == KeyEvent.VK_MINUS) {
				} else {
					e.consume(); // �ؼ������ε��Ƿ�����
				}
			}
		});
		
		
		create = new JButton("����");
		create.setBounds(250, 380, UiConfig.BUTTON_WIDTH,
				UiConfig.BUTTON_HEIGHT);
		create.addActionListener(new ActionListener() {

			PanelARP p = PanelARP.this;

			public void actionPerformed(ActionEvent arg0) {
				String strSrcIP = tSrcIP.getText();
				String strSrcMac = tSrcMac.getText();
				String strDecIP = tDecIP.getText();
				String strDecMac = tDecMac.getText();
				String stime = tTimes.getText();
				if (strSrcIP.length() != 0||strSrcMac.length() != 0||strDecIP.length() != 0||strDecMac.length() != 0||stime.length()!=0) {
					try {
						final int times = Integer.parseInt(stime);
						final ARPPacket arp=new ARPPacket();
						arp.hardtype=ARPPacket.HARDTYPE_ETHER;
						arp.prototype=ARPPacket.PROTOTYPE_IP;
						arp.operation=ARPPacket.ARP_REQUEST;
						arp.hlen=6;
						arp.plen=4;
						arp.sender_hardaddr=Backstage.getMacBytes(strSrcMac);
						arp.sender_protoaddr=InetAddress.getByName(strSrcIP).getAddress();
						arp.target_hardaddr=Backstage.getMacBytes(strDecMac);
						arp.target_protoaddr=InetAddress.getByName(strDecIP).getAddress();
						

						EthernetPacket ether=new EthernetPacket();
						ether.frametype=EthernetPacket.ETHERTYPE_ARP;
						ether.src_mac=Backstage.getMacBytes(strSrcMac);
						ether.dst_mac=Backstage.getMacBytes(strDecMac);
						arp.datalink=ether;
						
						new Thread(new Runnable() {
							
							public void run() {
								if(times>=0){
									for(int i=0;i<times;i++){
										systemUI.bs.sender.sendPacket(arp);
									}
									
								}else{
									flag=true;
									while(flag){
										try {
											Thread.sleep(300);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										systemUI.bs.sender.sendPacket(arp);
									}
								}
							}
						}).start();
						
					}catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(p, "��Ϣ���Ϊ��");
				}
			}
		});

		back = new JButton("����");
		back.setBounds(350, 380, UiConfig.BUTTON_WIDTH, UiConfig.BUTTON_HEIGHT);
		back.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				flag = false;
				systemUI.changeCenterPanel(new PanelWelcome());
			}
		});

		this.add(lSrcIP);
		this.add(tSrcIP);
		this.add(lSrcMac);
		this.add(tSrcMac);
		this.add(lDecIP);
		this.add(tDecIP);
		this.add(lDecMac);
		this.add(tDecMac); 
		this.add(lTimes);
		this.add(tTimes);   
		this.add(create);      
		this.add(back);
	}

}