<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Gym Booker</title>
        <jsp:include page="import.jsp" />
    </head>
    <body>
        <jsp:include page="header.jsp" />

        <c:choose>
            <c:when test="${not empty existingBookings}">
                <h3>Bookings</h3>
                <table class="table table-striped">
                    <thead>
                      <tr>
                        <th>Class Name</th>
                        <th>Class Date</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="existingBooking" items="${existingBookings}" varStatus="theCount">
                            <tr>
                                <td>
                                    <span id="scheduledBookingName_${theCount.index}">${existingBooking.activity.className}</span>
                                </td>
                                <td>
                                    <span id="scheduledBookingDate_${theCount.index}"><a href="/gym/activities/book/${existingBooking.activity.id}">${existingBooking.activity.classDate}</a></span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <h3>Exclusions</h3>


                <table class="table table-striped">
                    <thead>
                      <tr>
                        <th>Class Name</th>
                        <th>Class Date</th>
                        <th>Exclusion Date</th>
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="existingBooking" items="${existingBookings}" varStatus="theCount">
                        <c:if test="${not empty existingBooking.exclusions}">
                            <c:forEach var="exclusion" items="${existingBooking.exclusions}">
                                <tr>
                                    <td>
                                        <span id="scheduledBookingExclusionName_${theCount.index}">${existingBooking.activity.className}</span>
                                    </td>
                                    <td>
                                        <span id="scheduledBookingExclusionDate_${theCount.index}">${existingBooking.activity.classDate}
                                    </td>
                                    <td>
                                        <span id="scheduledBookingExclusionSpecifiedDate_${theCount.index}"><fmt:formatDate pattern="dd/MM/yyyy" value="${exclusion.exclusionDate}" /></span>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>

            </c:when>
            <c:otherwise>
                You have no scheduled bookings
            </c:otherwise>
        </c:choose>


        <jsp:include page="footer.jsp" />
    </body>
</html>