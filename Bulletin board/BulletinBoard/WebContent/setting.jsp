<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${ user.name }の設定</title>
	<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="main-contents">

<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<li><c:out value="${message}" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>

<form action="setting" method="post"><br />
	<label for="login_id">ログインID</label>
	<br><input name="login_id" id="login_id" value="${user.login_id }"><br />

	<label for="password">パスワード</label>
	<br><input name="password" type="password" id="password" /><br />

	<label for="checkpassword">確認用パスワード</label>
	<br><input name="checkpassword"  type="password" id="checkpassword" /><br />

	<label for="name">名前</label>
	<br><input name="name" id="name" value="${user.name }"/><br />

	<label for="branch_id">支店名</label>
	<br><select name="branch_id">
	<c:forEach items="${branch_name }" var="branch">
		<option value="<c:out value="${ branch.id }" />"><c:out value="${ branch.branchName }" /></option>
	</c:forEach>
	</select><br/>

	<label for="role_id">部署.役職</label>
	<br><input name="role_id" id="role_id" value="${user.role_id }"/><br />

	<input type="submit" value="更新"  /> <br />
	<a href="usermaintenance">戻る</a>
	<input type="hidden" name="user.id" value="${ user.id }"/><br/>
</form>
<div class="copyright">Copyright(c)Takahiro Tochigi</div>
</div>
</body>
</html>
