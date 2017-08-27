<%@page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
	<meta charset="utf-8" />
	<title>note系统</title>
</head>

<body>

<h1>上传页面</h1>
    <form action="/user/upload.do" method="post" enctype="multipart/form-data">
    	文件：<input type="file" name="fileOne"><br>
    	描述：<input type="text" name="desc"><br>
   		<input type="submit" value="上传"> &nbsp;&nbsp;&nbsp;
   		<a href="/">返回</a>
    </form>
    
</body>

<footer>
1997-2017@copy
</footer>

</html>