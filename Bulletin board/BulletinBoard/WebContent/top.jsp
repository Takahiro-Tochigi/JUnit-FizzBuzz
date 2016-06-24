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
<script type="text/javascript">


function check(){

	if(window.confirm('本当に削除してもよろしいですか？')){ // 確認ダイアログを表示

		return true; // 「OK」時は送信を実行

	}
	else{ // 「キャンセル」時の処理

		window.alert('キャンセルされました'); // 警告ダイアログを表示
		return false; // 送信を中止

	}

}
</script>
</head>
<body>
<div class= "main-contents">
<h2>ホーム画面</h2>

	<div class= "header">
		<a href= "newMessage">新規投稿</a>
		<c:if test="${ loginUser.role_id == 1 && loginUser.branch_id == 1 }">
		<a href= "usermaintenance">ユーザー管理画面</a>
		</c:if>
		<a href= "logout">ログアウト</a>
	</div>

	<c:if test="${ not empty errormessages }">
	<div class="errormessages">
		<ul>
			<c:forEach items ="${errormessages}" var ="message">
				<li><c:out value ="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errormessages" scope="session"/>
	</c:if>

	<div class ="main-contents">
		<h3>投稿検索</h3>
		<form action ="./"  method="get"><br />
			カテゴリー<br/>
			<INPUT type="text" name="category" value="${category}">
			<br>日付<br/>

			<select name="startYear">
			<c:forEach begin="2016" end="2030"  varStatus="status">
				<option value="${status.index}" <c:if test="${ status.index == startYear }">selected</c:if>><c:out value="${status.index}"/></option>
			</c:forEach>
			</select>年

			<select name="startMonth">
			<c:forEach begin="01" end="12"  varStatus="status">
				<option value="${status.index}" <c:if test="${ status.index == startMonth }">selected</c:if> ><c:out value="${status.index}"/></option>
			</c:forEach>
			</select>月

			<select name="startDay">
			<c:forEach begin="01" end="31"  varStatus="status">
				<option value="${status.index}" <c:if test="${ status.index == startDay }">selected</c:if>><c:out value="${status.index}"/></option>
			</c:forEach>
			</select>日
			　～　
			<br>日付<br/>
			<select name="endYear">
			<c:forEach begin="2016" end="2030"  varStatus="status">
				<option value="${status.index}"  <c:if test="${ status.index == endDay }">selected</c:if> ><c:out value="${status.index}"/></option>
			</c:forEach>
			</select>年

			<select name="endMonth">
			<c:forEach begin="01" end="12"  varStatus="status">
				<option value="${status.index}" <c:if test="${ status.index == endMonth }">selected</c:if> ><c:out value="${status.index}"/></option>
			</c:forEach>
			</select>月

			<select name="endDay">
			<c:forEach begin="01" end="31"  varStatus="status">
				<option value="${status.index}"<c:if test="${ status.index == endDay }">selected</c:if> ><c:out value="${status.index}"/></option>
			</c:forEach>
			</select>日

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
					<span class= "date"><fmt:formatDate value= "${message.insert_date }" pattern="yyyy/MM/dd HH:mm:ss" /></span>
				</div>
			</div>
				<c:if test="${ loginUser.id == message.name_id  }">
					<form action="messageDelete" method="post" onSubmit="return check()">
						<input type="submit" value="削除" />
						<input type="hidden" name="message.id" value="${ message.id }"/>
					</form>
				</c:if>



			<div>------------------------------------------------------------------------------------------------</div>


			<c:forEach items= "${ comment }" var="comment">
			<c:if test="${ message.id == comment.post_id}">
			<div>コメント</div>

				<div class= "comment">
					<br>本文  :
					<span class= "body"><c:out value= "${ comment.body }"/></span><br/>
					<br>名前  :
					<span class= "name"><c:out value= "${ comment.name }" /></span><br/>
					<br>コメント日時  :
					<span class= "date"><fmt:formatDate value= "${ comment.insert_date }" pattern="yyyy/MM/dd HH:mm:ss" /></span><br/>
					<c:if test= "${ loginUser.id == comment.name_id }" >
					<form action="commentDelete" method="post" onSubmit="return check()">
						<input type="submit" value="削除" />
						<input type="hidden" name="comment.id" value="${ comment.id }"/>
					</form>
					</c:if>
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