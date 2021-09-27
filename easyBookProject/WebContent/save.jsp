<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>保存信息页面</title>
</head>
<body>

<div align="center">
	<!-- 将表单填写的数据通过post请求发送给SaveServlet -->
	<form action="SaveServlet" method="post">
		图书名称<input name="name"><br/>
		图书价格<input name="price"><br/>
		图书作者<input name="author"><br/>
		<input type="submit" value="保存">
	</form>
</div>
</body>
</html>