package com.jinzhi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @ClassName: DBManager
 * @Description: TODO(数据访问类DBManager)
 * @author: 22345
 * @date: 2021年6月4日 下午7:53:59
 * @version: V1.0
 */
public class DBManager {
	
	protected Connection conn;       // 数据库连接
	
	private Statement st;
	
	private PreparedStatement ps;    // 执行
	
	protected ResultSet rs;          // 结果
	
	
	
	/**
	 * 
	 * @Title: getConn
	 * @Description: TODO(用getConn方法获取数据库连接的conn对象)
	 * @author: 22345
	 * @param: @return 参数
	 * @return: Connection 返回类型
	 * @throws:
	 */
	public Connection getConn(){		   
		try {
			//1.加载驱动
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//2.获取连接，设置请求地址，账号，密码
			conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;dataBaseName=book","sa","sa");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("数据库连接成功！");
		return conn;
	}
	
	
	
	/**
	 * 
	 * @Title: closeAll
	 * @Description: TODO(关闭数据库连接方法)
	 * @author: 22345
	 * @param:  参数
	 * @return: void 返回类型
	 * @throws:
	 */
	public void closeAll(){
		try {
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 预编译sql
	 * select * from table where name='"+name+"' and age="+age;    注意！这样拼接sql容易被sql注入
	 * select * from table where name=? and age=?                  这样就不会被sql注入
	 * @Title: query
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author: 22345
	 * @param: @param sql
	 * @param: @return 参数
	 * @return: ResultSet 返回类型
	 * @throws:
	 * 对象数组 {@link Object [] obj
	 * 可变参数：可以变化的参数，可变参数就是一个对象数组，必须写在方法的最后一位，一个方法中只能有一个可变参数
	 * 可变参数也可以没有，可有可无，但是必须最后一位，且只能有一位
	 * select * from table 
	 */
	public ResultSet query(String sql,Object...objects){     // 这个objects就是可变参数
		conn = getConn();
		// 预编译sql
		try {
			ps = conn.prepareStatement(sql);
			
			// 记住这段代码！！！会常用！！！
			if(objects!=null&&objects.length>0){
				// 如果objects可变参数是有内容的
				for (int i = 0; i < objects.length; i++) {
					ps.setObject(i+1, objects[i]);
				}
			}
			rs = ps.executeQuery();      // 不管有没有参数都要查询
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	
	
	/**
	 * 
	 * @Title: update
	 * @Description: TODO(增删改操作数据库)
	 * @author: 22345
	 * @param: @param sql
	 * @param: @param objects
	 * @param: @return 参数
	 * @return: int 返回类型
	 * @throws:
	 */
	public int update(String sql,Object...objects){     // Object...objects是可变参数，到时候调用方法时传进来的参数个数不确定
		int count = 0;
		conn = getConn();
		try {
			ps = conn.prepareStatement(sql);
			
			// 记住这段代码！！！会常用！！！
			if(objects!=null&&objects.length>0){
				for (int i = 0; i < objects.length; i++) {
					ps.setObject(i+1, objects[i]);
				}
			}
			count = ps.executeUpdate();      // 执行更新sql
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return count;
				
	}
	
	
	
	// 主函数实例化dbManager建立连接
	public static void main(String[] args) {
		DBManager db = new DBManager();
		System.out.println(db.getConn());
	}

}