package com.jinzhi.web;

// 根据Id查找书籍信息对象的Servlet，根据在list.jsp页面点击书籍时传入的id来查找该书籍的所有信息，查到之后把这个对象带到update.jsp
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jinzhi.bean.Book;
import com.jinzhi.dao.BookDao;

/**
 * Servlet implementation class FindByIdServlet
 */
public class FindByIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindByIdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
				
		BookDao bookDao = new BookDao();
		// 获取在list.jsp页面传入的点击书籍的id
		int id = Integer.parseInt(request.getParameter("id"));
		// 通过调用dao里的findById方法将查到的book对象得到
		Book book = bookDao.findById(id);
		
		// 用请求转发将查到的book对象传到update.jsp更新页面
		request.setAttribute("book", book);
		request.getRequestDispatcher("update.jsp").forward(request, response);
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
