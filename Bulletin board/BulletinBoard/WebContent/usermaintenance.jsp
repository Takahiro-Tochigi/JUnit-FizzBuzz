<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
	<title>ユーザー管理画面</title>
</head>
<body>
<div class="main-contents">
	<c:if test="${ not empty errorMessages }">
		<div class="errormessages">
			<ul>
				<c:forEach items= "${errorMessages}" var = "messages">
					<li><c:out value= "${messages}" />
				</c:forEach>
			</ul>
		</div>
		<c:remove var= "errorMessages" scope= "session" />

	</c:if>
	<div class= "header">
		<a href= "signup">新規登録</a>
		<a href= "./">戻る</a>

	</div>
	<div class= "user">
	<c:forEach items="${ user }" var="user">
		<div class = "user">

		<form action="setting" method="get">
			ログインID<br/>
			<c:out value="${ user.login_id }" /><br/>
			名前<br/>
			<c:out value="${ user.name }" /><br/>
			<input type="submit" value="編集" /><br />
			<input type="hidden" name="user.id" value="${ user.id }"/><br/>
		</form>
		</div>
		<div class= "comment">
		</div>
	</c:forEach>
</div>
</div>
<div class = "copyright">Copyright(c) Takahiro Tochigi </div>
</body>
</html>