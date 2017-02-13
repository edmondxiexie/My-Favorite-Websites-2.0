<jsp:include page="template.jsp" />

<p class="list-item">
	Login or <a href="register.do">register</a> a new user.
</p>

<jsp:include page="error-list.jsp" />

<p>
	<form method="post" action="login.do">
		<table class="list-item">
			<tr>
				<td> Email Address: </td>
				<td><input type="text" name="email" value="${form.getEmail()}"/></td>
			</tr>
			<tr>
				<td> Password: </td>
				<td><input type="password" name="password" value=""/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" name="button" value="Login"/>
				</td>
			</tr>
		</table>
	</form>
</p>
