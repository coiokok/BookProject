package com.qq.ui;

// �����б�ҳ��
// ʵ��UdpListener�ӿڷ�����ͨ���۲���ʵ���¼������в�ͬ���Ľ��յ�ʱִ�в�ͬ����
// ʵ��MouseListener�ӿ��е�����������������˫������ʱ�ͻ�ʵ����������Ϣ����

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import com.qq.bean.Qquser;
import com.qq.component.ClinetImgCell;
import com.qq.listener.UdpListener;
import com.qq.listener.UdpThread;
import com.qq.pub.CommonUse;
import com.qq.pub.TcpMessage;
import com.qq.pub.TcpSocket;
import com.qq.pub.UdpSocket;

public class MainFrame extends JFrame implements UdpListener, MouseListener {
	private TcpSocket tcpSocket = null;
	private UdpSocket receiveSocket = null;
	
	private Qquser fullUser = null;                   // �û���ȫ����ϢfullUser���󣬵�¼ҳ����ʵ���������б�ҳ��ʱ����Ϊ��������
	private List<Qquser> friends = null;              // ���û���ȫ�����Ѷ���ļ���friends
	
	private JLabel userLabel = null;                  // ���û����Ķ���panel
	
	private DefaultListModel<Qquser> listModel = null;     // ��ʾ���ѵ�DefaultListModel������������б�������ֱ��չ����
	private JList<Qquser> friendList = null;               // ���������Ϣ�ļ���
	
	private JPanel bodyPanel = null;
	
	
	// ��д���췽��
	public MainFrame() {
		// TODO Auto-generated constructor stub
	}
	public MainFrame(Qquser fullUser, TcpSocket tcpSocket, UdpSocket receiveSocket) {    // ���������¼ҳ����û���ȫ����ϢfullUser�����Լ������������ͨѶ��tcpSocket���Լ���������ն˽���ͨѶ��udpSocket
		this.fullUser = fullUser;
		this.tcpSocket = tcpSocket;
		this.receiveSocket = receiveSocket;
		this.init();
	}
	
	
	// getFriends������ø��û����������Ѷ��󼯺�
	private List<Qquser> getFriends() {
		List<Qquser> list = null;
		
		TcpMessage sMessage = new TcpMessage();                            // ʵ����������Ϣ�Ķ���sMessage
		sMessage.setHead(CommonUse.FIND);                                  // ����sMessage�ı���ͷ��Ϊ����
		sMessage.setBody(CommonUse.QQ_USER, this.fullUser);                // ����sMessage�ı�������Ϊ�û���ȫ����Ϣ
		
		TcpMessage rMessage = this.tcpSocket.submit(sMessage);             // ���صȷ������������rMessage������rMessage���Ǹ��û��������Ѷ���ļ���friends
		
		list = (List<Qquser>)rMessage.getBody(CommonUse.FRIENDS_INFO);     // ���ñ�����list���س�ȥ
		return list;
	}
	
	
	// createFriendList���ɺ����б���
	private void createFriendList() {
		List<Qquser> beforeList = this.getFriends();                       // beforeList����������к��Ѷ��󼯺�
		
		List<Qquser> afterList = new ArrayList<Qquser>();                  // afterList���������ߵĺ��ѷ���ǰͷ�Ľ�������ļ���
		
		// ����ͷ��ͼƬ���������Ѽ��ϣ���StateΪ1��Ҳ�������ߵĺ��ѵ�ͷ������Ϊ��ɫ��StateΪ0��Ҳ���ǲ����ߵĺ��ѵ�ͷ������Ϊ��ɫ
		for (Qquser tempUser : beforeList) {
			if("1".equals(tempUser.getState())) {
				tempUser.setPlace1("./onimg/" + tempUser.getPic() + ".png");
			} else if("0".equals(tempUser.getState())) {
				tempUser.setPlace1("./outimg/" + tempUser.getPic() + ".png");
			}
		}
		
		// �����ߵĺ��ѷ����б�ǰ�������ԭ���Ƿ���afterList��˳���ȷ����ߵĺ�Ų����ߵļ���
		for (Qquser tempUser : beforeList) {
			if("1".equals(tempUser.getState())) {
				afterList.add(tempUser);
			}
		}
		for (Qquser tempUser : beforeList) {
			if("0".equals(tempUser.getState())) {
				afterList.add(tempUser);
			}
		}
		
		this.friends = afterList;                            // friends��֮���������ɺ����б�ļ��ϣ����ź���ĺ����б��ϸ�ֵ����
		
		this.listModel = new DefaultListModel<Qquser>();     // ʵ������ʾ������Ϣ��DefaultListModel�б�
		
		for (Qquser tempUser : this.friends) {               // ��������listModel������еĺ�����Ϣ
			this.listModel.addElement(tempUser);
		}
		
		this.friendList = new JList<>();
		this.friendList.addMouseListener(this);                     // �������б��ÿ�����Ѷ����������¼�
		this.friendList.setCellRenderer(new ClinetImgCell());       // List��.setCellRenderer()���� �ǿ���������ĳ����ʽ���ö���ӽ�List����������ı���Ҫ��һ��ʵ��ListCellRenderer�ӿڵ���
		this.friendList.setModel(this.listModel);
	}
	
	
	// refreshFriendListˢ�º����б���
	private void refreshFriendList() {
		// ���������ɺ����б����friends���ϼ��ɣ����ԾͲ�����ʵ����һ��beforeList��
		List<Qquser> afterList = new ArrayList<Qquser>();
		
		// ����ͷ��ͼƬ���������Ѽ��ϣ���StateΪ1��Ҳ�������ߵĺ��ѵ�ͷ������Ϊ��ɫ��StateΪ0��Ҳ���ǲ����ߵĺ��ѵ�ͷ������Ϊ��ɫ
		for (Qquser tempUser : this.friends) {
			if("1".equals(tempUser.getState())) {
				tempUser.setPlace1("./onimg/" + tempUser.getPic() + ".png");
			} else if("0".equals(tempUser.getState())) {
				tempUser.setPlace1("./outimg/" + tempUser.getPic() + ".png");
			}
		}
		
		// ����friends���Ͻ����ߵĺ��ѷ����б�ǰ�������ԭ���Ƿ���afterList��˳���ȷ����ߵĺ�Ų����ߵļ���
		for (Qquser tempUser : this.friends) {
			if("1".equals(tempUser.getState())) {
				afterList.add(tempUser);
			}
		}
		for (Qquser tempUser : this.friends) {
			if("0".equals(tempUser.getState())) {
				afterList.add(tempUser);
			}
		}
		
		// ���ź����afterList��friends����ȥʵ���������б�DefaultListModel
		this.friends = afterList;
		
		this.listModel = new DefaultListModel<Qquser>();
		for (Qquser tempUser : this.friends) {
			this.listModel.addElement(tempUser);
		}
		this.friendList.setModel(this.listModel);
		
	}
	
	
	// init��ҳ�淽��
	private void init() {
		this.bodyPanel = (JPanel)this.getContentPane();
		this.bodyPanel.setLayout(new BorderLayout());
		
		this.userLabel = new JLabel(this.fullUser.getName());      // ��ø��û����ǳ�
		this.bodyPanel.add(this.userLabel, BorderLayout.NORTH);
		
		this.createFriendList();                                   // ����createFriendList��ʾ���к��ѵķ���
		this.bodyPanel.add(this.friendList, BorderLayout.CENTER);
		
		UdpThread udpThread = new UdpThread(this.receiveSocket);   // ʵ����UdpThread�߳̽�������Ϣ��receiveSocket����Ϳ��Է�ֹ����
		udpThread.addUdpListener(this);                            // �ٽ����ഫ���addUdpListener������������ϢͨѶ����ʱ�߳̾ͻ�ʵ���¼�
		udpThread.start();
		
		this.setTitle(this.fullUser.getAccount() + "�ĺ����б�");
		this.setBounds(1625, 0, 300, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	// ʵ���¼��������exectue��������ȡ����ͷ��ִ�в���
	@Override
	public void exectue(String udpInfo) {
		
		// ����õı�����CommonUse.UDP_PACKET_SYMBOL�ָ��������иȻ����infos�ַ�������
		String[] infos = udpInfo.split(CommonUse.UDP_PACKET_SYMBOL);
		
		// ����infos[0]���Ǳ���ͷ
		String head = infos[0];
		
		
		// �������ͷ�����ߵ�ʱ��
		if(head.equals(CommonUse.ONLINE)) {             
			
			// ��ȡ���д������Ĳ��ҵ������ߺ��ѵ���Ϣ
			String account = infos[1];
			String ip = infos[2];
			String port = infos[3];
			
			// �������û������к��ѣ��û�����ͬ�ľͰѸú��Ѷ����������Ϊ1��ip��ַ�Լ�portֵ����Ϊ������ͨ��UdpSocket��ȡ����ֵ
			for(Qquser tempUser : this.friends) {
				if(tempUser.getAccount().equals(account)) {
					tempUser.setState("1");
					tempUser.setIp(ip);
					tempUser.setPort(port);
					break;
				}
			}
			// �����û�������Ҳ����ˢ�º����б����ú���ͷ�����
			this.refreshFriendList();
		} 
		
		
		// �������ͷ�����ߵ�ʱ��
		else if (head.equals(CommonUse.OFFLINE)) {
			String account = infos[1];             // ��ȡ�����û����û���
			for (Qquser tempUser : this.friends) { // ���������б���Ķ��󣬽��ö��������ֵ��ipֵ�Լ�portֵ����
				if(account.equals(tempUser.getAccount())) {
					tempUser.setState("0");
					tempUser.setIp("0");
					tempUser.setPort("0");
					break;
				}
			}
			// �����û�������Ҳ����ˢ�º����б����ú���ͷ����
			this.refreshFriendList();
		} 
		
		
		// �������ͷ������Ϣ��ʱ�򣬾����б���û������û�����Ϣ��ʱ�򣬷��صľ����������ͷ
		else if(head.equals(CommonUse.MESSAGE)) {
			Qquser receiver = null;
			String account = infos[1];
			String message = infos[2];
			// ���������б�����˺��жϷ�����Ϣ���û����ĸ�����
			for (Qquser tempUser : this.friends) {
				if(account.equals(tempUser.getAccount())) {
					receiver = tempUser;
					break;
				}
			}
			// ʵ����������Ϣ�Ĵ���
			ReceiveFrame receiveFrame = new ReceiveFrame(this.fullUser, receiver, message);
			receiveFrame.setVisible(true);
		}
	}

	
	// �����˫������ʱ��ʵ����������Ϣ�Ĵ��壬����涼��MouseListener�ӿڵ�ʵ�ַ���������ֻ�õ����������¼�������ֻ��д�������
	@Override
	public void mouseClicked(MouseEvent e) {
		if(1 == e.getButton() && 2 == e.getClickCount()) {
			Qquser receiver = this.friendList.getSelectedValue();
			SendFrame sendFrame = new SendFrame(this.fullUser, receiver);
			sendFrame.setVisible(true);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
