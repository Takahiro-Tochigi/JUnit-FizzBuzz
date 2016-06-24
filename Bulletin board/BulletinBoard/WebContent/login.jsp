<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログイン画面</title>
</head>
<body>
<div class="main-contents">
<h2>ログイン</h2>

<c:if test="${ not empty errorMessages }">
	<div class="errormessages">
		<ul>
			<c:forEach items ="${errorMessages}" var ="message">
				<li><c:out value ="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>

</c:if>

<form action ="login" method="post"><br />
	<label for ="login_id">ログインID</label><br />
	<input name="login_id" id="login_id" value = "${ login_id }"/><br />

	<label for="password">パスワード</label><br />
	<input name="password" type="password" id="password" /><br />


	<input type="submit" value="ログイン" /><br />
</form>

<div>------------------------------------------------------------------------------------</div>


<div class ="copyright">Copyright(c) Takahiro Tochigi</div>
</div>
</body>
</html>