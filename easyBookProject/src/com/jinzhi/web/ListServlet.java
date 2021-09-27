package com.jinzhi.web;

// 调用查询数据库中所有书籍数据的方法然后把查到的所有书籍数据的对象传给list.jsp显示界面

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jinzhi.bean.Book;
import com.jinzhi.dao.BookDao;

/**
 * Servlet implementation class ListServlet
 */
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 设置中文编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		// 调用BookDao中的list方法获取书籍数据库里所有数据的对象
		BookDao bookDao = new BookDao();
		List<Book> lstBook = bookDao.list();
				
		// 存放所有的书籍数据对象，然后请求转发发送到list.jsp显示页面
		request.setAttribute("lstBook", lstBook);
		request.getRequestDispatcher("list.jsp").forward(request, response);
	}

	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
