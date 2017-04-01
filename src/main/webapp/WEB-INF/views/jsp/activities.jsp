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
        <h3>The following activities are available to book</h3>
        <c:forEach var="activities" items="${allActivities}">
            <c:set var="activityNameOnly" value="${fn:replace(activities, 'The Peak -', '')}" />
            <a href="../gym/activities/${activities}">${activityNameOnly}</a><br>
        </c:forEach>
        <jsp:include page="footer.jsp" />
    </body>
</html>