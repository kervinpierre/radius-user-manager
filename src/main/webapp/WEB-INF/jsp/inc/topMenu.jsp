<div>
    <c:choose>
        <c:when test="${pageContext.request.userPrincipal.name != null}">
            <spring:url value="/logout" var="logoutUrl" />

            <form:form action="${logoutUrl}" id="logoutForm">
            </form:form>

            <script>
                function formSubmit()
                {
                    document.getElementById("logoutForm").submit();
                }
            </script>

            <div>
                <kendo:menu name="Menu">
                    <kendo:menu-items>

                        <kendo:menu-item text="Prefs"  url="/prefs/">
                        </kendo:menu-item>

                    </kendo:menu-items>
                </kendo:menu>
            </div>
            <div>Welcome : ${pageContext.request.userPrincipal.name} |
                <a href="javascript:formSubmit()">Logout</a>
            </div>

        </c:when>
        <c:otherwise>
            <a href="/login">Login</a>
        </c:otherwise>
    </c:choose>

</div>