package com.qq.server;

// ��Server������ʵ�������߳��ն�ʱ���࣬ÿ��һ���û���¼��Server�ͻ�ʵ����һ��ServerThread�߳��࣬Ȼ��ʵ����ʱ��������ͨѶ��socketʵ�����紫��
// Ȼ��ServerThread���socket�ᴫ����Ϣ�����ģ���ÿ��ҳ�棬Ȼ��ÿ��ҳ����ݴ���ı��Ľ����ж�Ȼ��ִ�в���

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

import com.qq.bean.Qquser;
import com.qq.dao.IQqUserDao;
import com.qq.dao.QqUserDaoImpl;
import com.qq.pub.CommonUse;
import com.qq.pub.TcpMessage;
import com.qq.pub.UdpSocket;

public class ServerThread extends Thread {
	private Qquser fullUser = null;                      // ���յ�¼�û�������Ϣ����
	
	private Socket socket = null;
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	
	
	// onLine���߷������鿴���и������û������ߵĺ��ѣ������������ߵĺ��ѷ���udpInfo�����Ա�֮���ͨѶ
	private void onLine(Qquser qquser) {
		IQqUserDao dao = new QqUserDaoImpl();
		List<Qquser> list = dao.findBySql("select * from qquser where account"
				+ " in (select friendAccount from friend where userAccount = '" + qquser.getAccount() + "') and state = '1'");
		
		// udpInfo���ģ�����ͷ�����ߣ������û�����ip�Լ�portֵ�����ø��ӵĶϵ�CommonUse.UDP_PACKET_SYMBOL���зָ�
		String udpInfo = CommonUse.ONLINE + CommonUse.UDP_PACKET_SYMBOL
				+ qquser.getAccount() + CommonUse.UDP_PACKET_SYMBOL
				+ qquser.getIp() + CommonUse.UDP_PACKET_SYMBOL
				+ qquser.getPort() + CommonUse.UDP_PACKET_SYMBOL;
		
		// �����鵽���������ߺ��Ѳ���UdpSocketʵ������������ipֵ�Լ�portֵ�������Ϳ���֮������Ϣ
		for (Qquser tempUser : list) {
			UdpSocket sendSocket = new UdpSocket(tempUser.getIp(), Integer.parseInt(tempUser.getPort()));         // Integer.parseInt() ���ַ���ת��Ϊint�͵ķ���
			sendSocket.send(udpInfo);                           // ����UdpSocket��send������udpInfo���ķ��͹�ȥ
		}
	}
	
	
	
	// offLine���߷������鿴���и�׼�������û������ߵĺ��ѣ�Ȼ��ip��portֵ���㴫���ȥ���������û����￴���û���ͷ��ͻ���
	private void offLine(Qquser qquser) {
		IQqUserDao dao = new QqUserDaoImpl();
		List<Qquser> list = dao.findBySql("select * from qquser where account"
				+ " in (select friendAccount from friend where userAccount = '" + qquser.getAccount() + "') and state = '1'");
		String udpInfo = CommonUse.OFFLINE + CommonUse.UDP_PACKET_SYMBOL
				+ qquser.getAccount() + CommonUse.UDP_PACKET_SYMBOL;
		for (Qquser tempUser : list) {
			UdpSocket sendSocket = new UdpSocket(tempUser.getIp(), Integer.parseInt(tempUser.getPort()));
			sendSocket.send(udpInfo);
		}
	}
	
	
	
	// findFriends������ͨ����¼�û����˺������ѹ�ϵ������Ҹ��û����������ѵ��˺ţ��ٰ���Щ�������ѵ��˺����û���Ϣ��������ǵ�������Ϣ�����List����
	private List<Qquser> findFriends(Qquser qquser){     
		IQqUserDao dao = new QqUserDaoImpl();
		List<Qquser> list = dao.findBySql("select * from qquser where account in (select friendAccount from friend where userAccount = '" + qquser.getAccount() + "')");     // ����dao���findBySql���ݴ����sql�����в�ѯ
		return list;
	}
	
	
	
	// findFullUser������ͨ����dao���findById��������¼�û����˺Ŵ���ȥ���ظ��˺ŵ�������Ϣ����
	private Qquser findFullUser(String account) {       
		return new QqUserDaoImpl().findById(account);
	}
	
	
	
	// midifyDB�����ǽ���¼���û�����ȥȻ����dao�е�update���������ݿ��е�State״̬��Ϣ��Ϊ1��ip��portֵ�������ģ�ʵ���û�������
	private void midifyDB(Qquser qquser) {              
		qquser.setState("1");
		new QqUserDaoImpl().update(qquser);
	}
	
	
	
	// registe�û�ע�᷽������IQqUserDao��saveע�Ṧ��
	public boolean registe(Qquser qquser) {
		IQqUserDao dao = new QqUserDaoImpl();
		int n = dao.save(qquser);
		if(n > 0) {
			return true;
		}
		return false;
	}
	
	
	
	// checkUser���������û���¼ʱ������˻�����ȥ���ݿ�����Ƿ��и��û������ж������Ƿ���ȷ
	public boolean checkUser(String id, String pass) {  
		IQqUserDao dao = new QqUserDaoImpl();
		
		Qquser qquser = dao.findById(id);               // dao�е�findById�ǽ�id����Ȼ�󷵻������ݿ��в��ҵ��Ķ����qquser
		
		if(qquser != null && qquser.getPassword().equals(pass)) {       // ���������qquserҲ����qquser != null���ҷ��ص�qquser��������û������һ�����¼�ɹ�����true
			return true;
		}
		return false;
	}
	
	
	
	// ��д���췽��
	public ServerThread() {
		// TODO Auto-generated constructor stub
	}
	public ServerThread(Socket socket) {
		System.out.println("��һ���ͻ���¼��");
		this.socket = socket;
		try {
			// �������socket������ж���������
			this.in = new ObjectInputStream(this.socket.getInputStream());
			this.out = new ObjectOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	// ��д�̵߳�run����
	@Override
	public void run() {
		TcpMessage rMessage = null;
		try {
			while(true) {
				
				rMessage = (TcpMessage)this.in.readObject();
				String head = rMessage.getHead();                    // ��ö�ȡ����ϢrMessage����ͷ
				
				
				// �����ȡ�ı���ͷ��ע��ʱ
				if(CommonUse.REGISTER.equals(head)) {
					TcpMessage sMessage = new TcpMessage();          // ʵ�������з�����Ϣ����sMessage
					Qquser registeUser = (Qquser)rMessage.getBody(CommonUse.QQ_USER);        // ��ñ������registeUser����
					// ����ע�᷽������Ҫע���registeUser����
					if(this.registe(registeUser)) {
						sMessage.setHead(CommonUse.SUCCESSFUL);      // ע��ɹ��˾ͽ��ɹ�����������Ϣ����sMessage
					} else {
						sMessage.setHead(CommonUse.FAILURE);         // ע��ʧ���˾ͽ�ʧ�ܴ���������Ϣ����sMessage
					}
					this.out.writeObject(sMessage);                  // �ö���������д������sMessage�����ȥ
					this.out.flush();
				} 
				
				
				// �����ȡ�ı���ͷ�ǵ�¼ʱ
				else if(CommonUse.LOGIN.equals(head)) {
					TcpMessage sMessage = new TcpMessage();          // ʵ�������з�����Ϣ����sMessage
					Qquser loginUser = (Qquser)rMessage.getBody(CommonUse.QQ_USER);         // ��ñ������loginUser����
					if(this.checkUser(loginUser.getAccount(), loginUser.getPassword())) {   // ���������checkUser������loginUser���˺��Լ����봫��ȥ���е�¼�ж�
						
						// ��¼�ɹ�����������midifyDB��������¼��������ݿ���Ϣ�����޸�Ϊ����״̬
						this.midifyDB(loginUser);
						
						// ���������findFullUser������õ�¼�û���ȫ����Ϣ
						this.fullUser = this.findFullUser(loginUser.getAccount());
						
						// ��������Ϣ����sMessage����ͷ��Ϊ�ɹ�������¼�û���������Ϣ������Ϊ������
						this.onLine(this.fullUser);                          // ����online����ʵ�����������ߵĺ��Ѳ���UdpSocket����udpInfo�����Ա�֮���ͨѶ
						
						sMessage.setHead(CommonUse.SUCCESSFUL);
						sMessage.setBody(CommonUse.QQ_USER, this.fullUser);
					} else {
						sMessage.setHead(CommonUse.FAILURE);
					}
					this.out.writeObject(sMessage);                  // �ö���������д������sMessage�����ȥ
					this.out.flush();
				} 
				
				
				// �����ȡ�ı���ͷ�ǲ���ʱ
				else if(CommonUse.FIND.equals(head)) {
					TcpMessage sMessage = new TcpMessage();          // ʵ�������з�����Ϣ����sMessage
					
					Qquser mainUser = (Qquser)rMessage.getBody(CommonUse.QQ_USER);         // ��ñ������mainUser����
					
					List<Qquser> friends = this.findFriends(mainUser);     // ���������findFriends���������������Ѷ���ļ���friends
					sMessage.setBody(CommonUse.FRIENDS_INFO, friends);     // �����󼯺�friends��Ϊ������
					
					this.out.writeObject(sMessage);                  // �ö���������д������sMessage�����ȥ
					this.out.flush();
				}
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// ����һ���ͻ��˹ر�ʱ
			System.out.println("��һ���ͻ�������");
			// �����ڱ��ر�ʱ�����ö��������ֵ��ipֵ�Լ�portֵ��Ϊ0
			this.fullUser.setState("0");
			this.fullUser.setIp("0");
			this.fullUser.setPort("0");
			// ���ø������ݿⷽ�������ݴ������ݿ�
			new QqUserDaoImpl().update(this.fullUser);
			// Ȼ�������Լ�����ĸö�������д���
			this.offLine(this.fullUser);
		}
		
	}

}
