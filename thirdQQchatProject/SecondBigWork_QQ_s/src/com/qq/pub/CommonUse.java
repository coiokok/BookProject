package com.qq.pub;

// CommonUse���д�����Ҫ����ͷ�Ա�ʱ�ı�������Ҫ�ȶ�ʱ�͵��ø���ı��������Ա�֤������Ϊ����ֶ�����

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.Serializable;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class CommonUse implements Serializable{
	
	public static final String FLAG="FLAG";
	
	public static final String REGISTER="REGISTER";                 // ע�Ṧ�ܱ�ʶ��
	
	public static final String LOGIN="LOGIN";                       // ��¼���ܱ�ʶ��
	
	public static final String ONLINE="ONLINE";                     // ���߹��ܱ�ʶ��
	
	public static final String OFFLINE="OFFLINE";                   // ���߹��ܱ�ʶ��
	
	public static final String FIND="FIND";                         // ���ҹ��ܱ�ʶ��
	
	public static final String QQ_USER="QQ_USER";                   // �û������ʶ��
	
	public static final String SUCCESSFUL="SUCCESSFUL";             // �ɹ���ʶ��
	
	public static final String FAILURE="FAILURE";                   // ʧ�ܱ�ʶ��
	
	public static final String FRIENDS_INFO="FRIENDS_INFO";         // ���󼯺ϱ�ʶ��
	
	public static final String SERVER_IP = "127.0.0.1";             // ipֵ
	
	public static final int SERVER_PORT = 9999;                     // portֵ
	
	public static final String FIND_FRIEND="FIND_FRIEND";           // ���Һ��ѹ��ܱ�ʶ��
	
    public static final String UDP_PACKET_SYMBOL = "\n\r\n\r";      // ���Ӽ����
	
	public static final String MESSAGE = "MESSAGE";                 // ��Ϣ��ʶ��

	
	// ��õ�ǰ��ľ���·���������ô��ڱ���ʱ�ͱ���ͼƬ·�����ʹ��
    public static String getSystempath() {
        File f = new File("");
        return f.getAbsolutePath();
    }
}