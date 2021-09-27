package com.jinzhi.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jinzhi.bean.Book;
import com.jinzhi.bean.Pager;
import com.jinzhi.db.DBManager;

public class BookDao extends DBManager implements IDao<Book> {

	
	// 增加数据的save方法
	@Override
	public void save(Book t) {
		// 这个sql增加语句的写法请注意：insert into 表名(name,price,author,context) values(?,?,?,?)
		String sql = "insert into t_book(name,price,author,context) values(?,?,?,?)";
		
		// 获取传入对象的四个属性，然后调用DBManager中的update方法，将sql语句和需要进行操作的四个数据传入
		int count = update(sql, t.getName(),t.getPrice(),t.getAuthor(),t.getContext());
		if(count>0){
			System.out.println("保存成功");
		}else{
			System.out.println("保存失败");
		}
	}

	
	
	// 更新数据的update方法
	@Override
	public void update(Book t) {
		// 这个sql修改语句的写法请注意：update 表名 set name=?,price=?,author=?,context=? where id=?
		String sql = "update t_book set name=?,price=?,author=?,context=? where id=?";
		
		int count = update(sql, t.getName(),t.getPrice(),t.getAuthor(),t.getContext(),t.getId());
		if(count>0){
			System.out.println("更新成功");
		}else{
			System.out.println("更新失败");
		}
	}

	
	
	// 删除数据的del方法
	@Override
	public void del(Serializable id) {
		// 这个sql删除语句的写法请注意：delete from 表名 where id = ?
		String sql = "delete from  t_book where id = ?";
		
		int count = update(sql, id);
		if(count>0){
			System.out.println("删除成功");
		}else{
			System.out.println("删除失败");
		}
	}

	
	
	// 根据id查找数据返回对象的findById方法
	@Override
	public Book findById(Serializable id) {
		String sql = "select * from t_book where id=?";
		rs = query(sql, id);
		Book book = null;
		
		try {
			// 将查到的所有信息赋给Book对象的实例
			while(rs.next()){
				book = new Book(rs.getInt("id"), rs.getString("name"), rs.getFloat("price"), rs.getString("author"), rs.getString("context"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return book;
	}

	
	
	// 返回t_book信息表里的所有对象的集合
	@Override
	public List<Book> list() {
		String sql = "select * from t_book";
		rs = query(sql);
		List<Book> lstBook = new ArrayList<>();
		
		try {
			// 将查到的所有书的信息赋给Book对象的实例，然后再添加进lstBook集合中
			while(rs.next()){
				Book book  = new Book(rs.getInt("id"), rs.getString("name"), rs.getFloat("price"), rs.getString("author"), rs.getString("context"));
				lstBook.add(book);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return lstBook;
	}

	
	
	/**
	 * 
	 * @Title: sumRow
	 * @Description: TODO(获取全部书籍数)
	 * @author: 22345
	 * @param: @return 参数
	 * @return: int 返回类型
	 * @throws:
	 */
	public int sumRow(){
		int sumrow = 0;
		// 该sql语句的意思是获取book表中的数据行数，然后把值赋给数据库中sumRow属性
		rs = query("select count(*) as sumRow from t_book");
		try {
			if(rs.next()){
				// 在这里获取数据库中存的sumRow属性
				sumrow = rs.getInt("sumRow");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return sumrow;
	}
	
	
	
	/**
	 * @Title: pager
	 * @Description: TODO(返回每一页应该显示的书籍数据对象，第一页的就是返回0-5的书籍，第二页的就是返回6-10的书籍)
	 * @author: 22345
	 * @param: @param nowPage
	 * @param: @param pageSize
	 * @param: @return 参数
	 * @return: Pager<Book> 返回类型
	 * @throws:
	 */
	public Pager<Book> pager(int nowPage,int pageSize){
		Pager<Book> pager = new Pager<>();
		
		// 得到总记录数sumRow
		int sumRow = sumRow();
		// 得到总页数sumPage
		int sumPage = sumRow%pageSize==0?sumRow/pageSize:sumRow/pageSize+1;
		
		// 如果当前页面等于总页数，那就不能再跳转至下一页
		if(nowPage>sumPage){
			nowPage=sumPage;
		}
		// 如果当前页面是第一页，那就不能再跳转至上一页
		if(nowPage<1){
			nowPage=1;
		}
		
		// 这里的start代表的是数据从第几个后开始
		int start = (nowPage-1)*pageSize;
		
		// 这个sql语言是 从start值后的开始然后显示book表中的pageSize数的书籍数据，例如：显示从第0个后的5个
		String sql = "select top "+pageSize+" * from t_book where id not in(select top "+start+" id from t_book)";
		rs = query(sql);
		
		
		// 把查到的书籍数据对象赋值给lstBook集合
		List<Book> lstBook = new ArrayList<>();
		try {
			while(rs.next()){
				Book book  = new Book(rs.getInt("id"), rs.getString("name"), rs.getFloat("price"), rs.getString("author"), rs.getString("context"));
				lstBook.add(book);
			}
			pager.setNowPage(nowPage);
			pager.setSumRow(sumRow);
			pager.setPageSize(pageSize);
			pager.setSumPage(sumPage);
			pager.setPageData(lstBook);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return pager;
	}
}