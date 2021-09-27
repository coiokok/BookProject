package com.jinzhi.web;

// 执行save.jsp传入数据的保存功能，完成之后跳转至ListServlet查询所有数据信息再跳转至list.jsp进行显示

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jinzhi.bean.Book;
import com.jinzhi.dao.BookDao;

/**
 * Servlet implementation class SaveServlet
 */
public class SaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// 设置中文编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		// 获取save.jsp的表单传过来的数据
		String name = request.getParameter("name");
		float price = Float.parseFloat(request.getParameter("price"));            // 这里还要把从表单获取到的数据从字符型强转为float类型
		String author = request.getParameter("author");
		
		// 封装到一个Book对象的实例中
		Book book = new Book(name, price, author);
		BookDao bookDao =  new BookDao();
		bookDao.save(book);
		
		// 跳转到列表显示页面，jsp的里面还没有数据，servlet要先去查询数据，然后查询完成后才转发到显示数据的jsp，这里不能直接跳转至list.jsp页面，不然会显示不出东西
//		response.sendRedirect("ListServlet");
		
		// 这里是跳转至分页显示页面的PagerServlet，到时候跳转到ListServlet或者PagerServlet都行
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
