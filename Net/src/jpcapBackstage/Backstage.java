package jpcapBackstage;

import java.io.IOException;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;

public class Backstage {

	public NetworkInterface[] devices;
	public JpcapCaptor captor;
	public JpcapSender sender;

	public static void main(String[] args) throws IOException {
		Backstage bs = new Backstage();
		bs.CaptureStart();
//			bs.printDevices();
	}

	public Backstage() throws IOException {
		devices = JpcapCaptor.getDeviceList();
		captor = JpcapCaptor.openDevice(devices[0], 2000, false, 3000);
		sender = captor.getJpcapSenderInstance();
		printDevices();
	}

	public void CaptureStart() throws IOException {
		if (!devices[0].loopback) {// ��ץȡ����ѭ����ַ�ϵ����ݣ���127.0.0.1�ϲ�ץ
			
			new Thread(new Runnable() {
				public void run() {
					// ����TestPacketReceiver����,
					// ���߳���ץȡ��ʾ���������Ͻ�������ݰ�
					Reciever receiver = new Reciever();
					captor.loopPacket(2000, receiver);
				}
			}).start(); // ����һ��ץ���߳�
		}
	}

	public void printDevices() {
		try {
			for (int i = 0; i < devices.length; i++) {
				NetworkInterface nc = devices[i];
				// һ�鿨�Ͽ����ж����ַ:
				String address = "";
				for (int t = 0; t < nc.addresses.length; t++) {
					address += "|addresses[" + t + "]: "
							+ nc.addresses[t].address.toString();
				}
				// ��ӡ˵��:
				System.out.println("��" + i + "���ӿ�:" + "|name: " + nc.name
						+ "|loopback: " + nc.loopback + "\r\naddress: "
						+ address);
			}
		} catch (Exception ef) {
			ef.printStackTrace();
			System.out.println("��ʾ����ӿ�����ʧ��: " + ef);
		}
	}
	

	public static byte[] getMacBytes(String mac) {
		byte[] macBytes = new byte[6];
		String[] strArr = mac.split(":");

		for (int i = 0; i < strArr.length; i++) {
			int value = Integer.parseInt(strArr[i], 16);
			macBytes[i] = (byte) value;
		}
		return macBytes;
	}
	
	public static String getMacString(byte[] mac){
		String temp="";
		for (byte b : mac)
			temp+=Integer.toHexString(b&0xff)+":";
	
		return temp.substring(0, temp.length()-1);
	}
}
