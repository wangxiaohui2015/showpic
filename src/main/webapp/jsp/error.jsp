<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta charset="utf-8">
<title>Error</title>
<base href="<%=basePath%>">
</head>
<body>
	<div style="font-size: 26px; color: red;">Sorry, server error occurred!</div> <a href="main" style="font-size:26px">Go Back</a>
</body>
</html>
