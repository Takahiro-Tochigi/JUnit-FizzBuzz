<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix ="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー登録</title>
</head>
<body>
<div class ="main-contents">
<h2>新規ユーザー登録</h2>
<c:if test ="${ not empty errormessages }">
	<div class ="errormessages">
		<ul>
			<c:forEach items ="${ errormessages }" var="messages">
				<li><c:out value="${ messages }" />
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errormessages" scope="session" />
</c:if>
<form action ="signup" method="post"><br />
	<label for="login_id">ログインID</label><br/>
	<input name="login_id" id="login_id" value="${user.login_id }"><br />

	<label for="password">パスワード</label><br/>
	<input name="password" type="password" id="password" value = "${user.password}" /><br />

	<label for="checkpassword">確認用パスワード</label><br/>
	<input name="checkpassword"  type="password" id="checkpassword" /><br />

	<label for="name">名前</label><br/>
	<input name="name" id="name" value="${user.name}"/><br />

	<label for="branch_id">支店名</label>
	<br><select name="branch_id" >
	<c:forEach items="${ branch_name }" var="branch" >
		<option value="${ branch.id }" ><c:out value="${ branch.branchName }" /></option>
	</c:forEach>
	</select><br/>

	<label for="role_id">部署.役職</label>
	<br><select name="role_id">
	<c:forEach items="${ role_name }" var="role" >
		<option value="${ role.id }" ><c:out value="${ role.roleName }" /></option>
	</c:forEach>
	</select>
	<div>
		<br><input type="submit" value="登録" /><br />
	</div>
		<a href="./usermaintenance">戻る</a>

</form>
<div>------------------------------------------------------------------------------------------------</div>
<div class ="copyright">Copyright(c) Takahiro Tochigi </div>
</div>
</body>
</html>