<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>掲示板</title>
</head>
<body>
<div class ="main-contents">

	<div class ="header">
		<a href ="newMessage">新規投稿</a>
		<a href ="usermaintenance">ユーザー管理画面</a>
	</div>
	<div class ="messages">
		<c:forEach items="${ messages }" var="message">
			<div class = "message">
				<div class="account-name">
					<span class="subject"><c:out value="${ message.subject }"/></span><br/>
					<span class="body"><c:out value="${ message.body }"/></span>
					<div class="category"><c:out value="${ message.category }" /></div>
					<div class="name"><c:out value="${ message.name }" /></div>
					<div class="date"><fmt:formatDate value="${message.insertdate }" pattern="yyyy/MM/dd HH:mm:ss" /></div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
<div class ="copyright">Copyright(c) Takahiro Tochigi </div>
</body>
</html>