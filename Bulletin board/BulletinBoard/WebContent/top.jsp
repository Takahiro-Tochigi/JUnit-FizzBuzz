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
		<c:if test="${ loginUser.role_id == 1 && loginUser.branch_id == 1 }">
		<a href= "usermaintenance">ユーザー管理画面</a>
		</c:if>
		<a href= "logout">ログアウト</a>
	</div>

	<div class ="main-contents">
				<h3>投稿検索</h3>
				<form action ="./"  method="get"><br />
					カテゴリー<br/>
					<textarea name="category" rows="1" cols="10"></textarea><br/>

					<br>日付<br/>
					<textarea name="startYear" rows="1" cols="4"></textarea>年
					<textarea name="startMonth" rows="1" cols="4"></textarea>月
					<textarea name="startDay" rows="1" cols="4"></textarea>日
					　～　
					<textarea name="endYear" rows="1" cols="4"></textarea>年
					<textarea name="endMonth" rows="1" cols="4"></textarea>月
					<textarea name="endDay" rows="1" cols="4"></textarea>日
					<br><input type="submit" value="検索" /><br />

					<div>================================================================================================</div>

				</form>
			</div>


	<div class= "messages">
		<c:forEach items= "${ messages }" var="message">
			<div class= "message">
				<div class= "account-name">
					<div>投稿</div>
					<br>件名  :
					<span class= "subject"><c:out value= "${ message.subject }"/></span><br/>
					<br>本文  :
					<span class= "body"><c:out value= "${ message.body }"/></span><br/>
					<br>カテゴリー:
					<span class= "category"><c:out value= "${ message.category }" /></span><br/>
					<br>名前      :
					<span class= "name"><c:out value= "${ message.name }" /></span><br/>
					<br>投稿日時  :
					<span class= "date"><fmt:formatDate value= "${message.insertdate }" pattern="yyyy/MM/dd HH:mm:ss" /></span><br/>
				</div>
			</div>

			<div>------------------------------------------------------------------------------------------------</div>


			<c:forEach items= "${ comment }" var="comment">
			<c:if test="${ comment.post_id == message.id }">
			<div>コメント</div>
				<div class= "comment">
					<br>本文  :
					<span class= "body"><c:out value= "${ comment.body }"/></span><br/>
					<br>名前  :
					<span class= "name"><c:out value= "${ comment.name }" /></span><br/>
					<br>コメント日時  :
					<span class= "date"><fmt:formatDate value= "${ comment.insert_date }" pattern="yyyy/MM/dd HH:mm:ss" /></span><br/>
					<div>------------------------------------------------------------------------------------------------</div>
				</div>
			</c:if>
			</c:forEach>

			<div class ="main-contents">
				<form action ="newComment"  method="post"><br />
					本文<br/>
					<textarea name="body" rows="10" cols="50"></textarea>

					<input type="submit" value="コメント" />（500文字まで）<br />
					<div>================================================================================================</div>
					<input type="hidden" name="post_id" value="${ message.id }"/><br/>

				</form>
			</div>
		</c:forEach>

	</div>

</div>
<div class ="copyright">Copyright(c) Takahiro Tochigi </div>
</body>
</html>