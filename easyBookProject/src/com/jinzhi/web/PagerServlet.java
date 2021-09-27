package com.jinzhi.web;

// 用于将书籍信息分页显示

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jinzhi.bean.Book;
import com.jinzhi.bean.Pager;
import com.jinzhi.dao.BookDao;

/**
 * Servlet implementation class PagerServlet
 */
public class PagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		// 获取给的当前应该在的页数值给npage
		String nowPage = request.getParameter("nowPage");
		int npage=1;
		try {
			npage = Integer.parseInt(nowPage);
		} catch (NumberFormatException e) {
			// 当获取不到nowPage时，也就是第一次访问分页页面pager.jsp时，要给npage设置默认值1，不然会报错显示不出来
			npage=1;
		}
		
		// 调用BookDao中的pager方法，将第一个参数nowPage和第二个参数pageSize
		BookDao bookDao = new BookDao();
		// 获取应该显示的书籍信息数据集合
		Pager<Book> pagerBook = bookDao.pager(npage, 5);
		
		// 存放pagerBook数据，然后转发到显示页面pager.jsp
		request.setAttribute("pagerBook", pagerBook);
		request.getRequestDispatcher("pager.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
