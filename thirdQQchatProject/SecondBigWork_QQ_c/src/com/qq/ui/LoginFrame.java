package com.qq.ui;

// �û����е�¼��ҳ��

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.qq.bean.Qquser;
import com.qq.component.ImgPanel;
import com.qq.pub.CommonUse;
import com.qq.pub.TcpMessage;
import com.qq.pub.TcpSocket;
import com.qq.pub.UdpSocket;

public class LoginFrame extends JFrame implements ActionListener {
	
	private TcpSocket tcpSocket = null;           // ʵ����TcpSocket����������в����Ķ���
	private UdpSocket receiveSocket = null;       // ʵ����UdpSocket�������ն˽��в����Ķ���
	
	private JPanel bodyPanel;                     // ���н���panel��һ����body���м�ĵ�¼��centerPanel�Լ��ײ��İ�ťbottomPanel
	
	private JPanel centerPanel;
	private JPanel bottomPanel;
	
	private JLabel accountLabel;                  // �û��������������panel
	private JTextField accountTextField;
	private JLabel passowrdLabel;
    private JPasswordField passwordField;
    
    private JButton loginButton;                  // ��¼��ע�ᰴť
    private JButton registerButton;
    
    // �м�Ĳ���panel
    private void initCenter() {
    	this.centerPanel = new ImgPanel("./login.png");       // ʵ����ImgPanelר���豳���Ķ��󽫵�¼ҳ��ı���ͼƬ����
    	
    	this.accountLabel = new JLabel("�� �� ����");
        this.accountTextField = new JTextField(16);
		this.accountLabel.setFont(new Font("΢���ź�", Font.BOLD, 17));        // setFont(new Font("����", Font.��ʽ, �����С))    ����������ʽ�ķ���
        this.passowrdLabel = new JLabel("��     �룺");
        this.passwordField = new JPasswordField(16);
		this.passowrdLabel.setFont(new Font("΢���ź�", Font.BOLD, 18));        // setFont(new Font("����", Font.��ʽ, �����С))    ����������ʽ�ķ���
        
        // ����box���ֹ�����
        Box box0 = Box.createVerticalBox();
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        
        box1.add(accountLabel);
        box1.add(accountTextField);
        
        box2.add(passowrdLabel);
        box2.add(passwordField);
        
        box0.add(Box.createVerticalStrut(90));
        box0.add(box1);
        box0.add(Box.createVerticalStrut(20));
        box0.add(box2);
        this.centerPanel.add(box0);
    }
    
    // �ײ��İ�ťpanel
    private void initBottom() {
    	this.bottomPanel = new JPanel();
    	
        this.loginButton = new JButton("�� ½");
        this.loginButton.addActionListener(this);
        this.bottomPanel.add(this.loginButton);
        
        this.registerButton = new JButton("ע ��");
        this.registerButton.addActionListener(this);
        this.bottomPanel.add(this.registerButton); 
    }
    
    // �������
    private void init() {
    	this.bodyPanel = (JPanel)this.getContentPane();
    	this.bodyPanel.setLayout(new BorderLayout());
    	
    	this.initCenter();
    	this.bodyPanel.add(this.centerPanel, BorderLayout.CENTER);
    	
    	this.initBottom();
    	this.bodyPanel.add(this.bottomPanel, BorderLayout.SOUTH);
    	
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("QQ��¼ҳ��");
    }
    
    // ��д���췽��
    public LoginFrame() {
    	this.tcpSocket = new TcpSocket(CommonUse.SERVER_IP, CommonUse.SERVER_PORT);              // ͨ��TcpSocket��Ĺ��췽����ʵ��������������������ӵ�socket�����ﴫ��Ĳ����ǵ�����CommonUse�����Ѿ���õı�����ֻ��Ҫ�õ���Щ���Զ�Ҫ����CommonUse���еı��������ܱ�����Ϊ�ֶ�д������
    	this.receiveSocket = new UdpSocket();                // ʵ����receiveSocket���ն�֮����д���
    	this.init();
	}
   
    // ����������main����
	public static void main(String[] args) {
		LoginFrame loginFrame1 = new LoginFrame();
		loginFrame1.setBounds(680, 340, 500, 320);
		loginFrame1.setVisible(true);
	}
	
	
	// �¼��������
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// ������ǵ�¼��ťʱִ�еĲ���
		if(e.getSource() == this.loginButton) {
			Qquser loginUser = new Qquser();            // ʵ������¼��qquser
			
			String account = this.accountTextField.getText();          // ����û����Լ������������ڵ���Ϣ
			String password = new String(this.passwordField.getPassword());
			
			// ���������Ϣ���ø���¼����loginUser
			loginUser.setAccount(account);
			loginUser.setPassword(password);
			loginUser.setIp(this.tcpSocket.getIp());
			loginUser.setPort(String.valueOf(this.receiveSocket.getPort()));             // ����receiveSocket�е�getPort������ø��ն˵�portֵ
			
			TcpMessage sMessage = new TcpMessage();                   // ʵ����sMessage������Ϣ����
			sMessage.setHead(CommonUse.LOGIN);                        // ��sMessage����ͷ��Ϊ��¼
			sMessage.setBody(CommonUse.QQ_USER, loginUser);           // ��sMessage�����崫���¼���û�loginUser
			
			TcpMessage rMessage = this.tcpSocket.submit(sMessage);    // ����tcpSocket���submit�������ǽ�sMessage����ȥ���������ȴ��������Ȼ����ܷ��ص�rMessageֵ
			
			String back = rMessage.getHead();                         // ��ô��ص�rMessage����ͷ��ֵ��back
			// ���صı���ͷ�ǳɹ�ʱ
			if(CommonUse.SUCCESSFUL.equals(back)) {
				JOptionPane.showMessageDialog(this, "��¼�ɹ�");
				Qquser fullUser = (Qquser)rMessage.getBody(CommonUse.QQ_USER);      // ��ô��ص�rMessage��ı�����Ҳ�����û���ȫ����ϢfullUser����
				
				MainFrame mainFrame = new MainFrame(fullUser, this.tcpSocket, this.receiveSocket);      // ʵ����MainFrame�û��б���棬�����û�ȫ����ϢfullUser�����Լ�fullUser����
				                                                                    // ������������socket�Ļ��Ǹ��û����¸����潫���ܼ������û���ͨѶ
				mainFrame.setVisible(true); 
				this.dispose();                 // �رյ�¼ҳ��
			} 
			// ���صı���ͷ��ʧ��ʱ
			else if(CommonUse.FAILURE.equals(back)) {
				JOptionPane.showMessageDialog(this, "��¼ʧ�ܣ��û���������������");
			}
		}
		
		// �������ע�ᰴťʱִ�еĲ���
		else if(e.getSource() == this.registerButton){
			RegisterFrame registerFrame = new RegisterFrame();
			registerFrame.setBounds(700, 320, 512, 390);
			registerFrame.setVisible(true);
		}
	}

}
