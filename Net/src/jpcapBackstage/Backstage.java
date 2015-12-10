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
		if (!devices[0].loopback) {// 不抓取本地循环地址上的数据，即127.0.0.1上不抓
			
			new Thread(new Runnable() {
				public void run() {
					// 创建TestPacketReceiver对象,
					// 在线程中抓取显示这个网络接上进入的数据包
					Reciever receiver = new Reciever();
					captor.loopPacket(2000, receiver);
				}
			}).start(); // 启动一个抓包线程
		}
	}

	public void printDevices() {
		try {
			for (int i = 0; i < devices.length; i++) {
				NetworkInterface nc = devices[i];
				// 一块卡上可能有多个地址:
				String address = "";
				for (int t = 0; t < nc.addresses.length; t++) {
					address += "|addresses[" + t + "]: "
							+ nc.addresses[t].address.toString();
				}
				// 打印说明:
				System.out.println("第" + i + "个接口:" + "|name: " + nc.name
						+ "|loopback: " + nc.loopback + "\r\naddress: "
						+ address);
			}
		} catch (Exception ef) {
			ef.printStackTrace();
			System.out.println("显示网络接口数据失败: " + ef);
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
