<%@ page language="java" session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Gym Booker</title>
        <jsp:include page="import.jsp" />
    </head>
    <body>
        <h1>Error Page</h1>
        <p>Application has encountered an error. Please contact support. <a href="/gym">[home]</a></p>

<!--
Failed URL: ${url}
Exception:  ${exception.message}
    <c:forEach items="${exception.stackTrace}" var="ste">    ${ste}
</c:forEach>
-->

    </body>
</html>