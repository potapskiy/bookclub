<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:remove var="user" scope="session" />
<fmt:requestEncoding value="UTF-8" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>BookClub</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

</head>
<body>
<center>
	<h1>BookClub!</h1>
	
				<form name="loginForm" action="./auth" method="post"
					accept-charset="UTF-8">
					<table class="box" frame="box">
						<caption class="error">
							<c:if test="${not empty Error}">Incorrect login or password</c:if>
						</caption>
						<c:remove var="Error" scope="request" />

						<tr>
							<td>Login:</td>
							<td><input type="text" name="login" /></td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><input type="password" name="pass" /></td>
						</tr>
						<tr>
							<td align="center"><input type="submit" value="Log in" /></td>
						</tr>
					</table>
				</form>
				
</center>
</body>
</html>
