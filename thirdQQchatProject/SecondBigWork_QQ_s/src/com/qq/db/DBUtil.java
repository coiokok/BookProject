package com.qq.db;

// DButil�࣬����д������Ŀ�л��õ����й����ݿ�Ĳ���

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	
	private Connection conn = null;

	private Statement sta = null;

	private ResultSet rs = null;

	public DBUtil(){
		this.conn = this.getConnection();
	}	

	private Connection getConnection(){
		Connection conn = null;
		
		try {
			// Properties�࣬������Ҫ���ڶ�ȡJava�������ļ���Ҳ���Ƕ�ȡ�洢�����ݿ�������Ϣ��db.properties�ļ��������ܱ������ݿ�
			// db.properties�ļ����Լ�ֵ�Է�ʽ�洢�����ݿ�������Ϣ��
			Properties properties = new Properties();
			properties.load(DBUtil.class.getResourceAsStream("/db.properties"));

			// getProperty(String key);����  �ڴ������б�����������ָ����������
			String driverClass =  properties.getProperty("driver_class");
			String connectionUrl = properties.getProperty("connection_url");
			String dbUser = properties.getProperty("db_user");
			String dbPassword = properties.getProperty("db_password");
			
			Class.forName(driverClass);
			conn = DriverManager.getConnection(connectionUrl, dbUser, dbPassword);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	
	public int update(String sql) {
		int row = -1;
		try {
			sta = conn.createStatement();
			row = sta.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("update:SQLException");
			e.printStackTrace();
		}
		return row;
	}

	
	public ResultSet query(String sql) {
		try {
			sta = conn.createStatement();
			rs = sta.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	
	public void close() {
		try {
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(sta != null){
				sta.close();
				sta = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}