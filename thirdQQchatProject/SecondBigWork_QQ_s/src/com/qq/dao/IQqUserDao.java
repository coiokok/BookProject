package com.qq.dao;

// �������ݿ����û���qquser�����Ľӿ�

import java.util.List;

import com.qq.bean.Qquser;


public interface IQqUserDao {

	public int save(Qquser icqUser);             // �����û���Ϣ
	
	public int update(Qquser qquser);            // ���ݴ�������qq�û������е��˺Ÿ��¸��û��� ���߱�ʶstate��ip��ַ��portֵ��һ����û���¼֮��͵������������Ȼ�����ݿ�͸ı���û�����ϢΪ�ѵ�¼��
	
	public int update(String SQL);               // ���ݴ�������sql�����и���

	public Qquser findById(String qqNo);         // ����qq�˺�ȥ�����û���������Ϣ������һ��qquser����
	
	public List findBySql(String sql);           // ����sql�����в����û������صĿ����Ƕ���û�����ļ���
	
}

