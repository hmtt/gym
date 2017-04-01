<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Gym Booker</title>
        <jsp:include page="import.jsp" />
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <c:set var="activityNameOnly" value="${fn:replace(activityName, 'The Peak -', '')}" />
        <h3>The following times are available to book ${activityNameOnly}</h3>
        <c:if test="${not empty activityTimes}">
	        <c:forEach var="activities" items="${activityTimes}">
		        <a class="activityTime" href="../activities/book/${activities.id}">${activities.classDate}</a><br>
	        </c:forEach>
        </c:if>
        <jsp:include page="footer.jsp" />
    </body>
</html>