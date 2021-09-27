package com.jinzhi.web;

// 该类是进行书籍信息修改的Servlet，获取的是来自update.jsp的修改信息

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jinzhi.bean.Book;
import com.jinzhi.dao.BookDao;

/**
 * Servlet implementation class UpdateServlet
 */
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		// 获取需要修改的书籍信息
		String name = request.getParameter("name");
		float price = Float.parseFloat(request.getParameter("price"));
		String author = request.getParameter("author");
		int id = Integer.parseInt(request.getParameter("id"));
		
		// 将该书籍信息存到一个book对象中，然后调用DAO中的update方法进行修改
		Book book = new Book(name, price, author);
		book.setId(id);
		BookDao bookDao =  new BookDao();
		bookDao.update(book);
		
		// 跳转至列表显示的Servlet，不能直接跳转到list.jsp因为里面没有数据，要先跳转至servlet去查询数据，然后查询完成后转发到list.jsp
//		response.sendRedirect("ListServlet");
		
		// 更新完成了再重定向回PagerServlet进行pager.jsp的显示以及跳转功能
		response.sendRedirect("PagerServlet");
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
