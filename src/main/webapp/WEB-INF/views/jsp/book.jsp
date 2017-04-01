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
	    <jsp:include page="header.jsp" />
	    <h3 id="classNameAndDate">${activity.className}:${activity.classDate}</h3>
	    <form:form class="form-horizontal" method="post"
    		modelAttribute="bookingForm"
    		action="../book/${activity.id}">
		    <p>Auto-Enrol this class: <form:checkbox path="booked" /></p>
		    <h2><span id="exclusionHeader">Exclusions</span></h2>
            <form:errors />
            <c:if test="${not empty exclusions}">
                You have the following exclusions: <br />
                <br />
                <c:forEach var="exclusion" items="${exclusions}">
                    ${exclusion.exclusionDate} <a
                        href="../book/${activity.id}?cancel=${exclusion.exclusionDate}">[cancel]</a><br>
                </c:forEach>
            </c:if>
            <p>Add exclusion date (dd/mm/yyyy)</p>
            <spring:bind path="exclusionDate">
                <form:input path="exclusionDate" type="text" autocomplete="off" />
                <br/><form:errors path="exclusionDate"  />
            </spring:bind>
            <br/>
            <button id="submit" type="submit">Submit</button>
        </form:form>


          <jsp:include page="footer.jsp" />
            <script>
            $(function() {
              $.datepicker.setDefaults( $.datepicker.regional[ "" ] );
              $( "#exclusionDate" ).datepicker({ dateFormat: 'dd/mm/yy' });
            });
            </script>
    </body>
</html>