<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="page-content.jsp" />

<p class="list-item">Manage Your Favorites</p>

<jsp:include page="error-list.jsp" />

<p>
<form method="post" action="add.do">
	<table class="list-item">
		<tr>
			<td>URL:</td>
			<td colspan="2"><input type="text" name="url"
				value="${form.getUrl()}" /></td>
		</tr>
		<tr>
			<td>Comment:</td>
			<td><input type="text" name="comment"
				value="${form.getComment()}" /></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="submit"
				name="button" value="Add to Favorites" /></td>
		</tr>
	</table>
</form>
</p>
<hr />
<div>
	<c:if test="${favList.size() == 0}">
No favorite items.
</c:if>
</div>
<div>
	<table style="width: 100%" class="list-item">
		<c:set var="cnt" value="1" />
		<c:forEach var="fav" items="${favList}">
			<c:set var="cnt" value="${cnt + 1}" />
			<c:choose>
				<c:when test="${cnt%2==0}">
					<tr bgcolor="#e6e6e6">
				</c:when>
				<c:otherwise>
					<tr>
				</c:otherwise>
			</c:choose>
			<td width="28%"><a href="url.do?id=${fav.id}"
				title="id=${fav.id}, user=${fav.userId}">${fav.getURL()}</a></td>
			<td width="60%">${fav.getComment()}</td>
			<td width="12%">${fav.getClickCount()}&nbsp;Clicks</td>
			<td width="10px"><a href="delete.do?id=${fav.id}"
				title="delete item">delete</a></td>
			</tr>
		</c:forEach>
		<c:remove var="cnt" />
	</table>
</div>
