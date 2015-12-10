package jpcapBackstage;

import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class Reciever implements PacketReceiver {

	public void receivePacket(Packet packet) {
		if (false) {

		}

		// System.out.println(packet);
		// printChar(packet.data);
		else if (packet instanceof jpcap.packet.IPPacket) {
			IPPacket ip = (IPPacket) packet;
			if ("192.168.10.15".equals(ip.dst_ip)
					|| "192.168.10.15".equals(ip.src_ip))
				System.out.println("IPPacket:| dst_ip " + ip.dst_ip
						+ "|src_ip " + ip.src_ip + " |len: " + ip.len);

		}
		// 如果是tcp数据包,即通过Socket收发的数据
		if (packet instanceof jpcap.packet.TCPPacket) {
			TCPPacket p = (TCPPacket) packet;
			// 取得这个数据包的相关说明信息
			String s = "TCPPacket:| dst_ip " + p.dst_ip + ":" + p.dst_port
					+ "|src_ip " + p.src_ip + ":" + p.src_port + " |len: "
					+ p.len;
			System.out.println(s);
			System.out.println("******** TCPPacket******** 字节数据如下: ");
			printByte(p.data);
		}
		 // 如果是UDP数据包,即通过DatagramSocket收发的数据
		 else if (packet instanceof jpcap.packet.UDPPacket) {
		 UDPPacket p = (UDPPacket) packet;
		 // 取得这个数据包的相关说明信息
		 String s = "UDPPacket:| dst_ip " + p.dst_ip + ":" + p.dst_port
		 + "|src_ip " + p.src_ip + ":" + p.src_port + " |len: "
		 + p.len;
		 System.out.println(s);
		 System.out.println("******** UDPPacket******** 字节数据如下: ");
		 printByte(p.data);
		 }
		 //ping报文包
		 else if(packet instanceof jpcap.packet.ICMPPacket){
		 ICMPPacket p=(ICMPPacket)packet;
		 //ICMP包的路由链
		 String router_ip="";
		 for(int i=0;i<p.router_ip.length;i++){
		 router_ip+=" "+p.router_ip[i].getHostAddress();
		 }
		 String s="ICMPPacket:| router_ip "+router_ip
		 +" |redir_ip: "+p.redir_ip+" |mtu: "+p.mtu
		 +" |length: "+p.len;
		 System.out.println(s);
		 System.out.println("******** ICMPPacket******** 字节数据如下:");
		 printByte(p.data);
		 }
//		 是否地址转换协议请求包ARP协议包
		 else if (packet instanceof jpcap.packet.ARPPacket) {
		 ARPPacket p = (ARPPacket) packet;
		 Object saa = p.getSenderHardwareAddress();// 发送者网卡地址
		 Object taa = p.getTargetHardwareAddress();// 目标网卡地址
		 String s = "ARPPacket:| SenderHardwareAddress " + saa
		 + "|TargetHardwareAddress " + taa + " |len: " + p.len;
		 System.out.println(s);
		 }
	}

	// else{
	// System.out.println("unkown Packet: "+packet.datalink);
	// printByte(packet.data);
	// }
	// //取得链路层数据头:如果你想局网抓包或伪造数据包
	// DatalinkPacket datalink =packet.datalink;
	// //如果是以太网包
	// if(datalink instanceof jpcap.packet.EthernetPacket){
	// EthernetPacket ep=(EthernetPacket)datalink;
	// String s=" datalink layer packet: "
	// +"|DestinationAddress: "+ep.getDestinationAddress()
	// +"|SourceAddress: "+ep.getSourceAddress();
	// System.out.println(s);
	// }
	// }// 将字节数据中的数据打印出来

	private void printByte(byte[] data) {
		for (byte b : data) {
			System.out.print(b);
		}
		System.out.println();
	}

	private void printChar(byte[] data) {
		for (byte b : data) {
			System.out.print((char) b);
		}
		System.out.println();
	}

}
