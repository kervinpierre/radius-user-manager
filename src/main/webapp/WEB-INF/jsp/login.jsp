<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spring:url value="/j_spring_security_check" var="login" />
<form:form>
    <div>
        <div>
            <div>Username</div>
            <div>
                <input type="text" name="username" />
            </div>

            <div>Password</div>
            <div>
                <input type="password" name="password" />
            </div>
        </div>
        <div>
            <div style="color: darkred; font-weight: bold">
                <c:if test="${not empty lastError}">
                    <c:out value="${lastError}"/>
                </c:if>
            </div>
        </div>
    </div>

    <div>
        <button utype="submit" id="login">Login</button>
    </div>
</form:form>