package com.qq.pub;

// ��װ�ͻ�������������н����Ĵ��룬���ش���˼·��ʹ�����ü��
//��ʵTcpSocket�൱�ڿͻ��˵�¼�˺ͷ�����ͨ�����õĹ���
//�������ͨ���˲��ܽ���֮��ĸ��ֲ���

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpSocket {
	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	
	
	// TcpSocket���췽����������������췽�����������ip��portֵʱ���ͽ�������ֵʵ������socket���з��������紫����������
	public TcpSocket() {
		// TODO Auto-generated constructor stub
	}
	public TcpSocket(String ip, int port) {
		try {
			this.socket = new Socket(ip, port);
			out = new ObjectOutputStream(this.socket.getOutputStream());
			in = new ObjectInputStream(this.socket.getInputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// submit��������Ϊһ��TcpMessage��ı���ʵ��sMessageΪ��Ҫ���͵ı���
	public TcpMessage submit(TcpMessage sMessage) {
		
		TcpMessage rMessage = null;          // rMessageΪ��Ҫ���ܵı��ģ���ͬ��Ҳ��һ��TcpMessage�������ʵ��
		
		try {
			// ��sMessage����ͨ��writeObject�������ķ�������
			this.out.writeObject(sMessage);
			this.out.flush();
			
			//�ȴ�����������
			rMessage = (TcpMessage)this.in.readObject();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rMessage;
	}
}
