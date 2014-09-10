<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>this is user-jsp.jsp</title>
<body>
<div>this is user-jsp.jsp</div>
<div>${groupId }</div>
<div align="center">
	<form action="/lib/h/adduser" method="post">
		用户ID：<input type="text" value="" name="id"/><br>
		姓名：<input type="text" value="" name="name"/><br>
		<input type="submit" value="增加"/><br>
		<font color=red>${error }</font>
	</form>
	</div>
</body>
</html>