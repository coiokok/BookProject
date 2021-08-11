package com.qq.pub;

// ����ն�֮�����ͨѶ��UdpSocket
// ��ʵUdpSocket�͸�������ͻ��ˣ��û���֮�䷢����Ϣ�Ĺ���

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpSocket {
	private DatagramSocket datagramSocket = null;                // DatagramSocket���з�����Ϣ
	private DatagramPacket datagramPacket = null;                // DatagramPacket������Ϣ��д�����ݷ��ͻ��ǽ���ʵ������ʽ��ͬ
	
	
	// ��д���캯�����������ip��portֵʱ����ʹ�ý�����Ϣ��datagramPacket
	public UdpSocket() {
		byte[] pool = new byte[1024];
		try {
			this.datagramSocket = new DatagramSocket();
			this.datagramPacket = new DatagramPacket(pool, 0, pool.length);       // datagramPacket������Ϣ�ĸ�ʽ
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// ��д���캯���������ip��portֵʱ����ʹ�÷�����Ϣ��datagramPacket
	public UdpSocket(String ip, int port) {
		byte[] pool = new byte[1024];
		try {
			this.datagramSocket = new DatagramSocket();
			this.datagramPacket = new DatagramPacket(pool, 0, pool.length, InetAddress.getByName(ip), port);       // datagramPacket������Ϣ�ĸ�ʽ
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// send������Ϣ������������Ĳ�����Ϊ���ݽ��д���
	public void send(String udpInfo) {
		this.datagramPacket.setData(udpInfo.getBytes());
		try {
			this.datagramSocket.send(this.datagramPacket);           // socket.send(packet);  .send()���� ����packet����
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//  receive������Ϣ�������÷������ؽ��յ�����Ϣ
	public String receive() {
		String data = null;
		try {
			this.datagramSocket.receive(this.datagramPacket);         // socket.receive(packet);  .receive()���� ����packet����
			data = new String(this.datagramPacket.getData());         // ��packet�е�byte���ݻ�ȡ��ת��Ϊ�ַ���
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;               // ���ؽ��յ�����Ϣ
	}
	
	
	// getPort��ý��д�����ն˵�Portֵ�ķ���
	public int getPort() {
		return this.datagramSocket.getLocalPort();                    // .getLocalPort() ����Լ�portֵ�ķ���
	}
	
}
