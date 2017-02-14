<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${errors != null && errors.size() > 0}">
	<p style="font-size:medium; color:red">
		<c:forEach var = "error" items = "${errors}">
			${error} <br />
		</c:forEach>
	</p>
</c:if>
