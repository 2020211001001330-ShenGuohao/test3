<%--
  Created by IntelliJ IDEA.
  User: sghcmy
  Date: 2022/10/22
  Time: 9:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<%--<img src="images/up/kun.jpg">--%>
<%--<hr><br/>--%>
<%--<img src="http://localhost:8080/images/up/f7a5435f-3a6b-4e8a-9a76-5f38ec63198e_kun.jpg">--%>

<form action="${pageContext.request.contextPath}/save.action" enctype="multipart/form-data" method="post">
    <input type="file" name="file"/>
    <input type="submit" value="upload">
</form>
</body>
</html>
