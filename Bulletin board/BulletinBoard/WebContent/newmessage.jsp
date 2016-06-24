<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿画面</title>
</head>
<body>
<h2>新規投稿</h2>
<a href="./">戻る</a>
<div>
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

<form action ="newMessage"  method="post"><br />

	件名<br/>
	<INPUT type="text" size="40" name="subject" value ="${ message.subject }">
	<br/>
	本文<br/>
	<textarea name="body" rows="10" cols="100"  ><c:out value="${ message.body }"></c:out></textarea>
	<br/>
	カテゴリー<br/>
	<INPUT type="text" name="category" value="${ message.category }">
	<br/>
		<input type="submit" value="投稿" /><br />

</form>
<div>------------------------------------------------------------------------------------------------</div>
<div class = "copyright">Copyright(c) Takahiro Tochigi </div>
</div>
</body>
</html>