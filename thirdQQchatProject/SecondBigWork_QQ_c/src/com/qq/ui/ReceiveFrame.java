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

public class ReceiveFrame extends JFrame implements ActionListener {
	private String message = null;
	private Qquser sender = null;
	private Qquser receiver = null;
	
	private JPanel topPanel;
    private JPanel bottomPanel;
    
    private JLabel fromLabel = null;
   	private JTextField fromField = null;
   	private JLabel contentLabel = null;
    private JTextArea contentArea;
    
    private JButton repeatButton;
    private JButton closeButton;
    
    private void init() {
    	JPanel bodyPanel = (JPanel)this.getContentPane();
		bodyPanel.setLayout(new BorderLayout());
		
		this.topPanel = new JPanel();
		this.bottomPanel = new JPanel();
		bodyPanel.add(this.topPanel, BorderLayout.CENTER);
		bodyPanel.add(this.bottomPanel, BorderLayout.SOUTH);
		
		// ���տ�ĵ�һ�У���ʾ˭������
		this.fromLabel = new JLabel("�����ڣ�");
        this.fromField = new JTextField();
        this.fromField.setPreferredSize(new Dimension(400, 25));
        this.fromField.setEditable(false);
        this.fromField.setText(this.receiver.getName());
        
        // ���տ�Ľ�����Ϣ����
        this.contentLabel = new JLabel("�������ݣ�");
        this.contentArea = new JTextArea();
        this.contentArea.setPreferredSize(new Dimension(400, 130));
        this.contentArea.setText(this.message);
        this.contentArea.setEditable(false);
        JScrollPane contentScrollPane = new JScrollPane(this.contentArea);
        
        Box box = Box.createVerticalBox();
        
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        
        box1.add(this.fromLabel);
        box1.add(this.fromField);
        
        box2.add(this.contentLabel);
        box2.add(contentScrollPane);
        
        box.add(Box.createVerticalStrut(10));
        box.add(box1);
        box.add(Box.createVerticalStrut(10)); 
        box.add(box2);
        
        this.topPanel.add(box);
        
        this.bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        this.repeatButton = new JButton("�ظ�");
        this.repeatButton.addActionListener(this);
        this.closeButton = new JButton("�ر�");
        this.closeButton.addActionListener(this);
        this.bottomPanel.add(this.repeatButton);
        this.bottomPanel.add(this.closeButton);
        
        this.setTitle(this.sender.getName() + "�Ľ��տ�");
        this.setBounds(690, 400, 500, 280);
    }
    
    public ReceiveFrame() {
		// TODO Auto-generated constructor stub
	}
    
    // �ù��캯���ǻ�ȡ������Ϣ�Ķ������Ϣ����MainFrame�����е����յ��ı���ͷ��MESSAGE���ʱ��������������յ���Ϣ���û���Ϊ��һ������sender���롢������Ϣ�Ķ�����Ϊ�ڶ�������receiver���롢���յ�����Ϣ��Ϊ����������message���룬����߼���ϵ���һ��ҪŪ��
    public ReceiveFrame(Qquser sender, Qquser receiver, String message) {
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
		this.init();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// ��������ǻظ���ťʱ
		if(e.getSource() == this.repeatButton) {
			// ��ʵ�������Ϳ򣬽��ս��տ�Ĺ��췽����һ���������û���Ϊsender����ȥ��Ȼ�󷢸��ո����Ƿ���Ϣ�ĺ��Ѷ���receiver������͸ղ�ReceiveFrame�Ĺ��췽���Ƕ�Ӧ�ģ����һ��Ҫ�������飬����ڸ��Ե�MainFrame���յ���Ϣ��ǶȾ�ת���ˣ�
			SendFrame sendFrame1 = new SendFrame(this.sender, this.receiver);
			sendFrame1.setVisible(true);
			this.dispose();
		}
		// ��������ǹرհ�ťʱ
		else if (e.getSource() == this.closeButton) {
			this.dispose();
		}
	}
}
