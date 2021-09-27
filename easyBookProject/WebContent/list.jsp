<%@page import="com.jinzhi.bean.Book"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示信息页面</title>
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
			// 获取刚才ListServlet穿过来的lstBook所有书籍数据对象的集合
			List<Book> lstBook = (List<Book>)request.getAttribute("lstBook");
		
			for(Book b:lstBook){        // 注意这里的写法，遍历集合时用两个Java代码块把jsp代码包住
		%>
		
		<tr align="center">
			
			<td><%=b.getName() %></td>
			<td><%=b.getPrice() %></td>
			<td><%=b.getAuthor() %></td>
			
			<!-- 下面是进行删除和更新操作的按钮，点击跳转时还要将点击的书籍数据对象中的ID传过去 -->
			<td>
				<a href="DelServlet?id=<%=b.getId()%>" onclick="return confirm('确认要删除吗？')">删除</a>
				<a href="FindByIdServlet?id=<%=b.getId()%>">更新</a>
			</td>
		</tr>		
			
		<%
			} 
		%>
		
	</table>
</div>
<a href="save.jsp">跳转到保存页面</a>
</body>
</html>