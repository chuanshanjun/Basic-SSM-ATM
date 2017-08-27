<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
	<meta charset="utf-8" />
	<title>ATM-SSM系统</title>
</head>

<body>
welcom dayuanit ATM-SSM system.

<a href="/user/toRegist.do">注册</a>
<a href="/user/toLogin.do">登录</a>

	<c:if test="${not empty login_user_flag}">
		<h1>用户名：${login_user_flag.username}</h1>
		<h1>用户ID：${login_user_flag.id}</h1>
		<img height="50px" width="50px" src="/user/readFile.do" onerror="javascript:this.src='/images/timg.jpg'">
	</c:if>	
		
	<c:if test="${not empty login_user_flag}">	
		<a href="/bankCard/toOpernAccount.do">开户</a>
		<a href="/user/toUserCenter.do">个人中心</a>
		<a href="/user/toUpload.do">上传你的靓照</a>
		<a href="/user/loginOut.do">注销</a>
	</c:if>



</body>
</html>