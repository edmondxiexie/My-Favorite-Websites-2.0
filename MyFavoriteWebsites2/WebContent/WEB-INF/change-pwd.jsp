<jsp:include page="template.jsp" />

<p class="list-item">Enter your new password</p>

<jsp:include page="error-list.jsp" />

<p>
<form method="POST" action="change-pwd.do">
	<table class="list-item">
		<tr>
			<td>New Password:</td>
			<td><input type="password" name="password" value="" /></td>
		</tr>
		<tr>
			<td>Confirm New Password:</td>
			<td><input type="password" name="confirm" value="" /></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="submit"
				name="button" value="Change Password" /></td>
		</tr>
	</table>
</form>