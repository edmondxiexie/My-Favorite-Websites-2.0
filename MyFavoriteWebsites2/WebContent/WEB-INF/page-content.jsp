<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="pragma" content="no-cache">
<title>favorite Websites</title>
<style>

a:link {
	color: #4d4d4d;
}

a:visited {
	color: #4d4d4d;
}

a:hover {
	color: #C2185B;
}

a:active {
	color: #4d4d4d;
}

.menu-head {
	font-size: 15pt;
	font-weight: bold;
	color: black;
}

.menu-item {
	font-size: 13pt; 
}
.list-item {
	font-size: 13pt; 
}

#header {
	background-color: #5D4037;
	color: white;
	text-align: left;
	padding: 5px;
}

#navigator {
	line-height: 30px;
	background-color: #D7CCC8;
	height: 100%;
	width: 150px;
	float: left;
	padding: 5px;
}

#content {
	float: left;
	padding: 10px;
	width: 80%
}

#footer {
	background-color: #5D4037;
	color: white;
	clear: both;
	text-align: center;
	padding: 5px;
}

</style>
</head>
<body>
	<div id="header">
		<h1>
			<a style="font-size: 40; color: white; font-family: Helvetica; text-decoration: none" href="welcome.do"
				title="favorites.com">Favorite Websites</a>
		</h1>
	</div>

	<div id="navigator">
		<div>
			<table style="width: 100%">
				<!-- unlogged -->
				<c:choose>
					<c:when test="${user == null}">
						<tr>
							<td><span><a href="login.do" class="menu-item"
									title="Log in">Login</a></span><br /> <span><a
									href="register.do" title="register" class="menu-item">Register</a></span><br /></td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr class="menu-head">
							<%-- <td class="menu-head">${user.lastName} ${user.firstName}</td> --%>
							<td>${user.getFirstName()}&nbsp;${user.getLastName()}</td>
						</tr>
						<tr>
							<td><span><a href="manage.do"
									title="manage your favorites" class="menu-item">manage favorites</a></span><br /> <span><a href="change-pwd.do"
									title="change password" class="menu-item">change password</a></span><br />
								<span><a href="logout.do" title="log out"
									class="menu-item">log out</a></span><br /></td>
						</tr>
						<tr height="20">
							<td class="menu-item"></td>
						</tr>
					</c:otherwise>
				</c:choose>
				<!-- user list -->
				<tr height="20">
					<td class="menu-head">User List</td>
				</tr>
				<tr>
					<td><span><a href="list.do?uid=-1" class="menu-item"
							title="all user">All User</a></span><br /> <c:forEach var="u"
							items="${userList}">
							<span><a href="list.do?uid=${u.id}" class="menu-item"
								title="ID=${u.id}">${u.firstName}&nbsp;${u.lastName} </a> <%-- (id=${u.id}) --%></span>
							<br />
						</c:forEach></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="content">
