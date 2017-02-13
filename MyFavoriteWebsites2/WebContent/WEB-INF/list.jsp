<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="template.jsp" />


<div style="font-size: 20px" class="menu-head">
	<c:choose>
		<c:when test="${listUser.id == -1}">
All Users:
</c:when>
		<c:when test="${listUser.email == null}">
User Not Found
</c:when>
		<c:otherwise>
 ${listUser.firstName} ${listUser.lastName}'s Favorites:
</c:otherwise>
	</c:choose>
</div>
<br />
<div class="list-item">
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
					<tr>
				</c:when>
				<c:otherwise>
					<tr>
				</c:otherwise>
			</c:choose>
			<td width="30%"><a href="url.do?id=${fav.id}"
				title="id=${fav.id}, user=${fav.userId}">${fav.getURL()}</a></td>
			<td width="60%">${fav.getComment()}</td>
			<td width="10%">${fav.getClickCount()}&nbsp;Clicks</td>
			</tr>
		</c:forEach>
		<c:remove var="cnt" />
	</table>
</div>
