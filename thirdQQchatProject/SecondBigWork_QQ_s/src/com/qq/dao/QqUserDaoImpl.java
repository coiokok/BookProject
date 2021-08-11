package com.qq.dao;

// �������ݿ����û���qquser�����Ľӿ�ʵ����

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.qq.bean.Qquser;
import com.qq.db.DBUtil;

public class QqUserDaoImpl implements IQqUserDao {

	
	// ����qq�˺�ȥ�����û���������Ϣ������һ��qquser����
	public Qquser findById(String qqNo) {
		Qquser qquser = null;
		String sql = "select * from qquser where account = '" + qqNo + "'";
		DBUtil util = new DBUtil();
		ResultSet rs = util.query(sql);
		try {
			while(rs.next()) {
				qquser = new Qquser();
				qquser.setAccount(rs.getString(1));
				qquser.setName(rs.getString(2));
				qquser.setPassword(rs.getString(3));
				qquser.setState(rs.getString(4));
				qquser.setIp(rs.getString(5));
				qquser.setPort(rs.getString(6));
				qquser.setPic(rs.getString(7));
				qquser.setInfo(rs.getString(8));
				qquser.setPlace1(rs.getString(9));
				qquser.setPlace2(rs.getString(10));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return qquser;
	}

	
	// �����û���Ϣ
	public int save(Qquser qquser) {
		if(qquser == null){
			return -1;
		}
		int result = -1;
		String account = qquser.getAccount();
		String name = qquser.getName();
		String password = qquser.getPassword();
		String state = qquser.getState();
		String ip = qquser.getIp();
		String port = qquser.getPort();
		String pic = qquser.getPic();
		String sql = "insert into QqUser(account,name,password,state,ip,port,pic,info,place1,place2) " + 
		 "values(" +
		 "'" + account + "', " +
		 "'" + name + "', " +
		 "'" + password + "', " +
		 "'" + state + "', " +
		 "'" + ip + "', " +
		 "'" + port + "', " +
		 "'" + pic + "', " +
		 "'" + 0 + "', " +
		 "'" + 0 + "', " +
		 "'" + 0 + "'  " +
		 ")";
		DBUtil dbUtil = new DBUtil();
		result = dbUtil.update(sql);
		dbUtil.close();
		return result;
	}

	
	// ���ݴ�������qq�û������е��˺Ÿ��¸��û��� ���߱�ʶstate��ip��ַ��portֵ
	public int update(Qquser qquser) {
		String sql = "update qquser set state = '" + qquser.getState() + "', ip = '" + qquser.getIp() + "', port = '" + qquser.getPort() + "' where account = '" + qquser.getAccount() + "'";
		DBUtil dbUtil = new DBUtil();
		int num = dbUtil.update(sql);
		return num;
	}

	
	// ���ݴ�������sql�����и���
	public int update(String sql) {
		DBUtil dbUtil = new DBUtil();
		int num =dbUtil.update(sql);
		return num;
	}
	
	
	// ����sql�����в����û������صĿ����Ƕ���û�����ļ���
	public List<Qquser> findBySql(String sql) {
		List<Qquser> list = new ArrayList<Qquser>();
		DBUtil util = new DBUtil();
		ResultSet rs = util.query(sql);
		try {
			while(rs.next()) {
				Qquser qquser = new Qquser();
				qquser.setAccount(rs.getString(1));
				qquser.setName(rs.getString(2));
				qquser.setPassword(rs.getString(3));
				qquser.setState(rs.getString(4));
				qquser.setIp(rs.getString(5));
				qquser.setPort(rs.getString(6));
				qquser.setPic(rs.getString(7));
				qquser.setInfo(rs.getString(8));
				qquser.setPlace1(rs.getString(9));
				qquser.setPlace2(rs.getString(10));
				list.add(qquser);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}