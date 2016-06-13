<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored ="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv= "Content-Type" content="text/html; charset=UTF-8">
<title>掲示板</title>
</head>
<body>
<div class= "main-contents">

	<div class= "header">
		<a href= "newMessage">新規投稿</a>
		<c:if test="${ loginUser.branch_id == 1 }">
		<a href= "usermaintenance">ユーザー管理画面</a>
		</c:if>
		<a href= "logout">ログアウト</a>
	</div>
	<div class= "messages">
		<c:forEach items= "${ messages }" var="message">
			<div class= "message">
				<div class= "account-name">
					件名<br/>
					<span class= "subject"><c:out value= "${ message.subject }"/></span><br/>
					本文<br/>
					<span class= "body"><c:out value= "${ message.body }"/></span><br/>
					カテゴリー<br/>
					<div class= "category"><c:out value= "${ message.category }" /></div>
					名前<br/>
					<div class= "name"><c:out value= "${ message.name }" /></div>
					投稿日時<br/>
					<div class= "date"><fmt:formatDate value= "${message.insertdate }" pattern="yyyy/MM/dd HH:mm:ss" /></div>
				</div>
			</div>

			<div>------------------------------------------------------------------------------------------------</div>

			<br>コメント<br/>

			<c:forEach items= "${ comment }" var="comment">
			<c:if test="${ comment.post_id == message.id }">
				<div class= "comment">
					<span class= "body"><c:out value= "${ comment.body }"/></span>
					<div class= "name"><c:out value= "${ comment.name }" /></div>
					<div class= "date"><fmt:formatDate value= "${ comment.insert_date }" pattern="yyyy/MM/dd HH:mm:ss" /></div>
				</div>
			</c:if>
			</c:forEach>

			<div>------------------------------------------------------------------------------------------------</div>

			<div class ="main-contents">
				<form action ="newComment"  method="post"><br />
					本文<br/>
					<textarea name="body" rows="10" cols="50"></textarea>

					<input type="submit" value="コメント" />（500文字まで）<br />
					<div>======================================================================================================</div>
					<input type="hidden" name="post_id" value="${ message.id }"/><br/>

				</form>
			</div>
		</c:forEach>

	</div>

</div>
<div class ="copyright">Copyright(c) Takahiro Tochigi </div>
</body>
</html>