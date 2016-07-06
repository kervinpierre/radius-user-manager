<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>
<%@ page session="true"%>

<!DOCTYPE html>
<html>
    <head>
        <%@include file="../inc/htmlHead.jsp" %>
    </head>

    <body>
        <div>
            <%@include file="../inc/topMenu.jsp" %>
        </div>

        <script>
            function validateForm()
            {
                var p1 = $("#P1").val();
                var p2 = $("#P2").val();

                if( p1 != p2 ) {
                    alert("Sorry password confirmation does not match");
                    return false;
                }

                return true;
            }
        </script>
        <div>

            <ul>
                <li>Must contains one digit from 0-9</li>
                <li>Must contains one lowercase characters</li>
                <li>Must contains one uppercase characters</li>
                <li>Must contains one special symbols in the list "@#$%!"</li>
                <li>Length at least 8 characters and maximum of 32</li>
            </ul>
        </div>

    <div>
        <form method="POST" action="/prefs" onsubmit="return validateForm()">
            <div>Update Your Information</div>
            <div>
                <div>
                    <div><label for="P1">Password</label></div>
                    <div>
                        <input id="P1" name="P1" type="password"/>
                    </div>
                </div>

                <div>
                    <div><label for="P2">Password</label></div>
                    <div>
                        <input id="P2" name="P2" type="password"/>
                    </div>
                </div>

                <input type="hidden"
                       name="${_csrf.parameterName}"
                       value="${_csrf.token}" />

            </div>
            <div>
                <button name="saveButton" value="saved">Save</button>
            </div>
        </form>
    </div>

    </body>
</html>

