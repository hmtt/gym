<%@ page language="java" session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html lang="en">
  <head>
    <title tiles:fragment="title">Booker</title>
    <jsp:include page="import.jsp" />
  </head>
  <body>
      <div class="container">
        <form class="form-signin" name='loginForm' action="<c:url value='/logout' var='logoutUrl'/>" method='POST'>
            <h2 class="form-signin-heading">Please sign in</h2>
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class="msg">${msg}</div>
            </c:if>
          <label for="Username" class="sr-only">Username</label>
          <input type="username" name="user" class="form-control" placeholder="Username" required autofocus>
          <label for="Password" class="sr-only">Password</label>
          <input type="password" name="password" class="form-control" placeholder="Password" required>
          <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
           <input type="hidden"
                     name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
      </div>
  </body>
</html>