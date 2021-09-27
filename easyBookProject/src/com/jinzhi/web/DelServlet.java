package com.jinzhi.web;

// 执行的是list.jsp页面点击删除链接后进行的删除操作

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jinzhi.dao.BookDao;

/**
 * Servlet implementation class DelServlet
 */
public class DelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		// 获取的是list.jsp文件中a href="DelServlet?id=<%=b.getId()%>" 这里设置的id属性值
		String id = request.getParameter("id");
		// 调用BookDao中的del方法将刚获取的id值传入进行数据删除
		new BookDao().del(Integer.parseInt(id));
		
		
		// 然后删除了再重定向回ListServlet进行list.jsp的显示以及跳转功能
//		response.sendRedirect("ListServlet");
		
		// 然后删除了再重定向回PagerServlet进行pager.jsp的显示以及跳转功能
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