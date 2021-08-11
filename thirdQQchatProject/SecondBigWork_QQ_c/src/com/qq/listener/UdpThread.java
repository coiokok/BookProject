package com.qq.listener;

// �Է�������UdpThread�������ͬʱ���������ã�1���Ǹ��߳̿��Դ�������Ϣ���ͣ�2���ù۲���ģʽʵ���¼���3����ͨ��UdpSocketʵ�ַ�����Ϣ

import com.qq.pub.UdpSocket;

public class UdpThread extends Thread {
	
	private UdpSocket udpSocket = null;
	private UdpListener l = null;
	
	
	// ��д���췽����udpSocket��Ϊ��������
	public UdpThread() {
		// TODO Auto-generated constructor stub
	}
	public UdpThread(UdpSocket udpSocket) {
		this.udpSocket = udpSocket;
	}
	
	
	// addUdpListener��������¼�����l�����к��ѵ�¼�ͻ�ʵ����udpSocket������ϢȻ��ͻᴥ��
	public void addUdpListener(UdpListener l) {
		this.l = l;
	}
	
	
	// ��д�̵߳�run����
	@Override
	public void run() {
		while(true) {
			String udpInfo = this.udpSocket.receive();          // ���udpSocket���յ�����Ϣ
			this.l.exectue(udpInfo);                            // �������յ�����Ϣ��Ϊ���������¼������l
		} 
	}
}