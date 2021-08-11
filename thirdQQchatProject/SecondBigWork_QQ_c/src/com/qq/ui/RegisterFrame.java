package com.qq.ui;

// �û�����ע���ҳ��

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

public class RegisterFrame extends JFrame implements ActionListener {
	private TcpSocket tcpSocket = null;
	
	private JPanel bodyPanel = null;
	private JPanel centerPanel = null;
    private JPanel bottomPanel = null;
    
    private JLabel accountLabel = null;
    private JLabel nameLabel = null;
    private JLabel passwordLabel = null;
    private JLabel repasswordLabel = null;
    private JLabel imgLabel = null;
    
    private JTextField accountTextField = null;
    private JTextField nameTextField = null;
    private JPasswordField passwordField = null;
    private JPasswordField repasswordField = null;
    private JComboBox imgComboBox = null;
    
    private JButton registerButton = null;
    private JButton cancelButton = null;
    
    // �Զ����ͷ�񷽷�
    private void addHead() {
    	File[] files = new File("./selectimg").listFiles();       // ʹ��File��listFiles()������ø��ļ����µ������ļ�
		for (File file : files) {            // ��������
			this.imgComboBox.addItem(new ImageIcon(file.getAbsolutePath()));        // ��ÿ��ͼƬ�ļ��ľ��Զ�λͨ��getAbsolutePath()��������ImageIconʵ������addItem��ͷ��ѡ�������б�
			}
    }
    
    // �м�������Ϣ����Ĵ���
    private void initCenter() {
    	// ͨ��ImgPanel�Ĺ��췽��ʵ����panel����
    	this.centerPanel = new ImgPanel("./register.png");
    	
    	this.accountLabel = new JLabel("��         �ţ�", JLabel.RIGHT);
		this.accountLabel.setPreferredSize(new Dimension(70, 24));       // .setPreferredSize(new Dimension(��, ��))    ����panel��С�������ǻ����Ž����С�仯���仯
    	this.nameLabel = new JLabel("��         �ƣ�", JLabel.RIGHT);
		this.nameLabel.setPreferredSize(new Dimension(70, 24));
		this.passwordLabel = new JLabel("��         �룺", JLabel.RIGHT);
		this.passwordLabel.setPreferredSize(new Dimension(70, 24));
		this.repasswordLabel = new JLabel("ȷ�����룺", JLabel.RIGHT);
		this.repasswordLabel.setPreferredSize(new Dimension(70, 24));
		this.imgLabel = new JLabel("ѡ��ͷ��", JLabel.RIGHT);
		this.imgLabel.setPreferredSize(new Dimension(70, 24));
		
		this.accountTextField = new JTextField();
	    this.accountTextField.setPreferredSize(new Dimension(160, 24));
		this.nameTextField = new JTextField();
		this.nameTextField.setPreferredSize(new Dimension(160, 24));
		this.passwordField = new JPasswordField();
		this.passwordField.setPreferredSize(new Dimension(160, 24));
		this.repasswordField = new JPasswordField();
		this.repasswordField.setPreferredSize(new Dimension(160, 24));
		this.imgComboBox = new JComboBox();
		
		this.addHead();                 // addHead()����������д���Զ����ͷ�񷽷�
		
		// Box���ֹ���������Ҫ�������panelʱ��Ҫ����һ��������ŵ�box����ס������ŵ�box
		Box box0 = Box.createVerticalBox();              // .createVerticalBox()�������ŵ�box����
		Box box1 = Box.createHorizontalBox();            // .createHorizontalBox()�������ŵ�box����
		Box box2 = Box.createHorizontalBox();
		Box box3 = Box.createHorizontalBox();
		Box box4 = Box.createHorizontalBox();
		Box box5 = Box.createHorizontalBox();
		
		box1.add(this.accountLabel);
		box1.add(this.accountTextField);
		
		box2.add(this.nameLabel);
		box2.add(this.nameTextField);
		
		box3.add(this.passwordLabel);
		box3.add(this.passwordField);
		
		box4.add(this.repasswordLabel);
		box4.add(this.repasswordField);
		
		box5.add(this.imgLabel);
		box5.add(this.imgComboBox);
		
		box0.add(Box.createVerticalStrut(90));           // .createVerticalStrut(���)���ú���һ��box�ļ��
		box0.add(box1);
		box0.add(Box.createVerticalStrut(10));
		box0.add(box2);
		box0.add(Box.createVerticalStrut(10));
		box0.add(box3);
		box0.add(Box.createVerticalStrut(10));
		box0.add(box4);
		box0.add(Box.createVerticalStrut(10));
		box0.add(box5);
		
		this.centerPanel.add(box0);
    }
    
    // �·���ťpanel�Ĵ���
    private void initBottom() {
    	this.bottomPanel = new JPanel();
    	
    	this.registerButton = new JButton("ע ��");
    	this.registerButton.addActionListener(this);
    	this.cancelButton = new JButton("�� ��");
    	this.cancelButton.addActionListener(this);
    	
    	this.bottomPanel.add(this.registerButton);
    	this.bottomPanel.add(this.cancelButton);
    }
    
    // ���ɴ���ķ���
    private void init() {
    	this.bodyPanel = (JPanel)this.getContentPane();
    	this.bodyPanel.setLayout(new BorderLayout());
    	
    	this.initCenter();
    	this.bodyPanel.add(this.centerPanel, BorderLayout.CENTER);
    
    	this.initBottom();
    	this.bodyPanel.add(this.bottomPanel, BorderLayout.SOUTH);
    	
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setTitle("QQע��ҳ��");
    }
    
    // ��д���췽����TcpSocket���췽��ʵ��������ip��ַ��portֵ��������������
    public RegisterFrame() {
    	this.tcpSocket = new TcpSocket("127.0.0.1", CommonUse.SERVER_PORT);
    	this.init();
	}
   
    
    // �¼��������
	@Override
	public void actionPerformed(ActionEvent e) {
		// ��������ע�ᰴť
		if(e.getSource() == this.registerButton) {
			Qquser qquser = new Qquser();
			
			String account = this.accountTextField.getText();
			String name = this.nameTextField.getText();
			String password = new String(this.passwordField.getPassword());
			String repassword = new String(this.repasswordField.getPassword());
			
			// ���ͷ��ͼƬ�����ַ���������lastIndexOf�������\��λ���ٻ��.��λ��Ȼ������substring����ȡ����֮���ͷ��ͼƬ������
			String head = this.imgComboBox.getSelectedItem().toString();
			int start = head.lastIndexOf("\\");
			int end = head.lastIndexOf(".");
			String headString = head.substring(start + 1, end);
			
			if(password.equals(repassword)) {
				// ������ֵ
				qquser.setAccount(account);
				qquser.setName(name);
				qquser.setPassword(password);
				qquser.setState("0");
				qquser.setIp("0");
				qquser.setPort("0");
				qquser.setPic(headString);
				
				// ʵ��������������ͷ���壬ͷΪREGISTER����Ϊ�ջ�������qquser����
				TcpMessage sMessage = new TcpMessage();
				
				sMessage.setHead(CommonUse.REGISTER);
				sMessage.setBody(CommonUse.QQ_USER, qquser);
				
				// ����tcpSocket��submit������sMessage���Ͳ����շ��ص�rMessage
				TcpMessage rMessage = this.tcpSocket.submit(sMessage);
				
				// ��ͨ������ͷ�������ж��Ƿ�ע��ɹ�
				String back = rMessage.getHead();
				if(CommonUse.SUCCESSFUL.equals(back)) {
					JOptionPane.showMessageDialog(this, "ע��ɹ�");
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(this, "ע��ʧ��");
				}
			} else {
				JOptionPane.showMessageDialog(this, "�����������벻һ��");
			}
		}
		// �����������ð�ť
		else if(e.getSource() == this.cancelButton){
		    this.accountTextField.setText("");
			this.nameTextField.setText("");
			this.passwordField.setText("");
			this.repasswordField.setText("");
		}
	}

}
