package com.qq.component;

// ����panel�࣬ͨ����д���췽������ʵ����ʵ����ʱ����ͼƬ·��ֱ�ӾͿ�������Ϊpanel����

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.qq.pub.CommonUse;

public class ImgPanel extends JPanel {
	private ImageIcon img = null;
	
	public ImgPanel() {
		// TODO Auto-generated constructor stub
	}
	// ��д���췽������ͼƬ·������ImageIconʵ������Ȼ����ͨ��paintComponentʵ����ͼƬ����
	public ImgPanel(String imgPath) {
		this.img = new ImageIcon(CommonUse.getSystempath() + imgPath );
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(this.img.getImage(), 0, 0, this);
	}
	
}
