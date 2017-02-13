<jsp:include page="template.jsp" />

<p class="list-item">
    Enter the following information.
</p>

<jsp:include page="error-list.jsp" />

<p>
	<form method="post">
		<table class="list-item">
			<tr>
				<td>First Name: </td>
				<td><input type="text" name="firstName" value="${form.getFirstName()}"/></td>
			</tr>
			<tr>
				<td>Last Name: </td>
				<td><input type="text" name="lastName" value="${form.getLastName()}"/></td>
			</tr>
			<tr>
				<td>Email Address: </td>
				<td><input type="text" name="email" value="${form.getEmail()}"/></td>
			</tr>
			<tr>
				<td>Password: </td>
				<td><input type="password" name="password" value=""/></td>
			</tr>
			<tr>
				<td>Confirm Password: </td>
				<td><input type="password" name="confirm" value=""/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" name="button" value="Register"/>
				</td>
			</tr>
		</table>
	</form>
</p>

