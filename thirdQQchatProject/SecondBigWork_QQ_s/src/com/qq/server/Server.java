package com.qq.server;

// ��������Ҫ��Ӧ����ͻ���client���Է�������Ҫʵ�ֶ��߳�

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.qq.pub.CommonUse;

public class Server {

public static void main(String[] args) {
		
		System.out.println("����������");
		
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try {
			
			 // ʵ����ServerSocket�����������portֵ��ֻ��֮ǰ��pub��CommonUse���и���SERVER_PORT����
			serverSocket = new ServerSocket(CommonUse.SERVER_PORT);       // ���ʵ����ServerSocketǧ���ܷŽ�ѭ����Ϊ������һֱ�¸�һ��portֵ
			
			
			while(true) {         // Ҫ���кܶ�ε�SocketͨѶ����Ҫ�ŵ���ѭ����൱��һֱ�����ŷ�����
				socket = serverSocket.accept();                           // ����ͨѶ�ͽ���ͨѶServerSocket��һ��Socket
				
				ServerThread thread = new ServerThread(socket);           // ÿ��һ��Socket��ʵ��һ�����߳�
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
