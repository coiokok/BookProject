<%@page import="com.jinzhi.bean.Book"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更新信息页面</title>
</head>
<body>

<div align="center">
	<!-- 将表单填写的数据通过post请求发送给UpdateServlet -->
	<form action="UpdateServlet" method="post">
	<%
		Book book = (Book)request.getAttribute("book");
	%>
		<input type="hidden" name="id" value="<%=book.getId() %>">          <!-- 注意这里是要获取该书籍的id值的，因为之后进行修改操作时要通过id知道修改的是哪个书籍的，但是可以隐藏起来 -->
		图书名称<input name="name" value="<%=book.getName() %>"><br/>
		图书价格<input name="price" value="<%=book.getPrice() %>"><br/>
		图书作者<input name="author" value="<%=book.getAuthor() %>"><br/>
		<input type="submit" value="修改">
	</form>
</div>
</body>
</html>