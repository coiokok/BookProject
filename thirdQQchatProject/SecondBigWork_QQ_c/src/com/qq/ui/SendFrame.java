package com.qq.ui;

// ������Ϣ��ҳ��

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.qq.bean.Qquser;
import com.qq.pub.CommonUse;
import com.qq.pub.UdpSocket;

public class SendFrame extends JFrame implements ActionListener {
	private Qquser sender = null;
	private Qquser receiver = null;
	
	private JPanel topPanel;
    private JPanel bottomPanel;
    
    private JLabel receiverLabel = null;
   	private JTextField receiverField = null;
   	private JLabel sendercontentLabel = null;
    private JTextArea sendArea;
    
    private JButton sendButton;
    private JButton closeButton;
    
    private void init() {
    	JPanel bodyPanel = (JPanel)this.getContentPane();
		bodyPanel.setLayout(new BorderLayout());
		
		this.topPanel = new JPanel();
		this.bottomPanel = new JPanel();
		bodyPanel.add(this.topPanel, BorderLayout.CENTER);
		bodyPanel.add(this.bottomPanel, BorderLayout.SOUTH);
		
		// ���Ϳ�ĵ�һ�У���ʾ���͸�˭
		this.receiverLabel = new JLabel("�����ߣ�");
        this.receiverField = new JTextField();
        this.receiverField.setPreferredSize(new Dimension(400, 25));
        this.receiverField.setEditable(false);                     // �����ı��򲻿��޸�
        this.receiverField.setText(this.receiver.getName());
        
        // ���Ϳ�ķ�����Ϣ����
        this.sendercontentLabel = new JLabel("�������ݣ�");
        this.sendArea = new JTextArea();
        this.sendArea.setPreferredSize(new Dimension(400, 130));
        JScrollPane sendScrollPane = new JScrollPane(this.sendArea);
        
        Box box = Box.createVerticalBox();
        
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        
        box1.add(this.receiverLabel);
        box1.add(this.receiverField);
        
        box2.add(this.sendercontentLabel);
        box2.add(sendScrollPane);
        
        box.add(Box.createVerticalStrut(10));
        box.add(box1);
        box.add(Box.createVerticalStrut(10)); 
        box.add(box2);
        
        this.topPanel.add(box);
        
        // �ײ���ť����
        this.bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        this.sendButton = new JButton("����");
        this.sendButton.addActionListener(this);
        this.closeButton = new JButton("�ر�");
        this.closeButton.addActionListener(this);
        this.bottomPanel.add(this.sendButton);
        this.bottomPanel.add(this.closeButton);
        
        this.setTitle(this.sender.getName() + "�ķ��Ϳ�");
        this.setBounds(690, 400, 500, 280);
    }
    
    public SendFrame() {
		// TODO Auto-generated constructor stub
	}
    
    // �ù��췽������ĵ�һ�������Ǹ��û���ȫ����Ϣ���󣬵ڶ��������ǽ��շ�����������캯��������ҳ��Ҳ���Ǻ����б�ҳ�汻ʹ�ã�����ĸ����ѣ��ĸ����ѵĶ���ͻᱻ��Ϊ��������
    public SendFrame(Qquser sender, Qquser receiver) {
		this.sender = sender;
		this.receiver = receiver;
		this.init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// ������Ͱ�ťʱ
		if(e.getSource() == this.sendButton) {
			String message = this.sendArea.getText();
			// ������õľ���UdpSocket������Ϣʱ����UDP������Ҫ�õĹ��췽����ͨ�������ߵ�ip��portֵ���͸�������
			UdpSocket sendSocket = new UdpSocket(this.receiver.getIp(), Integer.parseInt(this.receiver.getPort()));
			// �������udpInfo�� ����ͷMESSAGE��ǡ������ߵ��û��������͵���Ϣ �����������
			String udpInfo = CommonUse.MESSAGE + CommonUse.UDP_PACKET_SYMBOL
					+ this.sender.getAccount() + CommonUse.UDP_PACKET_SYMBOL
					+ message + CommonUse.UDP_PACKET_SYMBOL;
			// �ٵ������е�send�������ͱ���
			sendSocket.send(udpInfo);
			this.dispose();
		}
		// ����رհ�ťʱ
		else if (e.getSource() == this.closeButton) {
			this.dispose();
		}
	}
}
