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
<body>

<h2>ユーザー管理</h2>

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


			<br>ログインID　・
			<c:out value="${ user.login_id }" />　　|　　
			名前　・
			<c:out value="${ user.name }" />　　|　　
		<div style="display:inline-flex">
		<form action="setting" method="get">
			<input type="submit" value="編集" />
			<input type="hidden" name="user.id" value="${ user.id }"/>　　|　　
		</form>
		<c:if test="${ loginUser.id != user.id }">
		<form action="delete" method="post" onSubmit="return check()" >
			<input type="submit" value="削除" />
			<input type="hidden" name="user.id" value="${ user.id }"/>　　|
		</form>
		</c:if>
		</div>
		</div>
		<div class= "comment">
		</div>
	<div>----------------------------------------------------------------------</div>
	</c:forEach>
</div>
</div>

<div class = "copyright">Copyright(c) Takahiro Tochigi </div>
</body>
</html>