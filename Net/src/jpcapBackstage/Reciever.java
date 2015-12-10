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
		// �����tcp���ݰ�,��ͨ��Socket�շ�������
		if (packet instanceof jpcap.packet.TCPPacket) {
			TCPPacket p = (TCPPacket) packet;
			// ȡ��������ݰ������˵����Ϣ
			String s = "TCPPacket:| dst_ip " + p.dst_ip + ":" + p.dst_port
					+ "|src_ip " + p.src_ip + ":" + p.src_port + " |len: "
					+ p.len;
			System.out.println(s);
			System.out.println("******** TCPPacket******** �ֽ���������: ");
			printByte(p.data);
		}
		 // �����UDP���ݰ�,��ͨ��DatagramSocket�շ�������
		 else if (packet instanceof jpcap.packet.UDPPacket) {
		 UDPPacket p = (UDPPacket) packet;
		 // ȡ��������ݰ������˵����Ϣ
		 String s = "UDPPacket:| dst_ip " + p.dst_ip + ":" + p.dst_port
		 + "|src_ip " + p.src_ip + ":" + p.src_port + " |len: "
		 + p.len;
		 System.out.println(s);
		 System.out.println("******** UDPPacket******** �ֽ���������: ");
		 printByte(p.data);
		 }
		 //ping���İ�
		 else if(packet instanceof jpcap.packet.ICMPPacket){
		 ICMPPacket p=(ICMPPacket)packet;
		 //ICMP����·����
		 String router_ip="";
		 for(int i=0;i<p.router_ip.length;i++){
		 router_ip+=" "+p.router_ip[i].getHostAddress();
		 }
		 String s="ICMPPacket:| router_ip "+router_ip
		 +" |redir_ip: "+p.redir_ip+" |mtu: "+p.mtu
		 +" |length: "+p.len;
		 System.out.println(s);
		 System.out.println("******** ICMPPacket******** �ֽ���������:");
		 printByte(p.data);
		 }
//		 �Ƿ��ַת��Э�������ARPЭ���
		 else if (packet instanceof jpcap.packet.ARPPacket) {
		 ARPPacket p = (ARPPacket) packet;
		 Object saa = p.getSenderHardwareAddress();// ������������ַ
		 Object taa = p.getTargetHardwareAddress();// Ŀ��������ַ
		 String s = "ARPPacket:| SenderHardwareAddress " + saa
		 + "|TargetHardwareAddress " + taa + " |len: " + p.len;
		 System.out.println(s);
		 }
	}

	// else{
	// System.out.println("unkown Packet: "+packet.datalink);
	// printByte(packet.data);
	// }
	// //ȡ����·������ͷ:����������ץ����α�����ݰ�
	// DatalinkPacket datalink =packet.datalink;
	// //�������̫����
	// if(datalink instanceof jpcap.packet.EthernetPacket){
	// EthernetPacket ep=(EthernetPacket)datalink;
	// String s=" datalink layer packet: "
	// +"|DestinationAddress: "+ep.getDestinationAddress()
	// +"|SourceAddress: "+ep.getSourceAddress();
	// System.out.println(s);
	// }
	// }// ���ֽ������е����ݴ�ӡ����

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
