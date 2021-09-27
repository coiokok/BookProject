<%@page import="com.jinzhi.bean.Pager"%>
<%@page import="com.jinzhi.bean.Book"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分页显示信息页面</title>
</head>
<body>

<div align="center">
		<table border="1" cellpadding="0" cellspacing="0" width="70%">
			<caption>图书列表</caption>
			<tr>
				<th>图书名称</th>
				<th>图书价格</th>
				<th>图书作者</th>
				<th>操作</th>
			</tr>
			
			<%
			
				// 获取设置的书籍信息分页要用的信息对象pagerBook
				Pager<Book> pagerBook = (Pager<Book>)request.getAttribute("pagerBook");
				// 获取需要显示的pagesize(5个)书籍信息对象的lstBook集合
				List<Book> lstBook =pagerBook.getPageData() ;
				// 遍历lstBook集合
				for(Book b:lstBook){
			
			%>
			
			<!-- 显示区域 -->
			<tr align="center">
				<td><%=b.getName() %></td>
				<td><%=b.getPrice() %></td>
				<td><%=b.getAuthor() %></td>
				<td>
					<a href="DelServlet?id=<%=b.getId()%>" onclick="return confirm('确认要删除吗？')">删除</a>
					<a href="FindByIdServlet?id=<%=b.getId()%>">更新</a>
				</td>
			</tr>	
					
			<%} %>
			
		</table>
		
		<!-- 显示设置的书籍信息分页要用的信息对象pagerBook中的各种信息，nowPage：当前页，sumRow：总记录数，pageSize：每页显示多少条，sumPage：总页数-->
		共<%=pagerBook.getSumRow() %>条记录，每页显示<%=pagerBook.getPageSize() %>条，当前第<%=pagerBook.getNowPage() %>页，共<%=pagerBook.getSumPage() %>页<br/>
		
		<!-- 跳转页面实现只需要将nowPage值更改后传给PagerServlet就可以 -->
		<a href="PagerServlet?nowPage=1">首页</a>
		<a href="PagerServlet?nowPage=<%=pagerBook.getNowPage()-1%>">上一页</a>
		<a href="PagerServlet?nowPage=<%=pagerBook.getNowPage()+1%>">下一页</a>
		<a href="PagerServlet?nowPage=<%=pagerBook.getSumPage()%>">尾页</a>
</div>
<a href="save.jsp">跳转到保存页面</a>
</body>
</html>