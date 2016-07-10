<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags" %>
<%@ page session="true" %>

<c:url value="/api/admin/read-all-users" var="readUrl"/>
<c:url value="/api/admin/create-user" var="createUrl"/>
<c:url value="/api/admin/update-user" var="updateUrl"/>
<c:url value="/api/admin/delete-user" var="destroyUrl"/>
<c:url value="/api/admin/change-pass" var="changePassUrl"/>

<c:url value="/api/admin/read-all-usernames" var="userNameUrl"/>
<c:url value="/api/admin/read-all-firstnames" var="firstNameUrl"/>
<c:url value="/api/admin/read-all-lastnames" var="lastNameUrl"/>

<!DOCTYPE html>
<html>
<head>
    <%@include file="../inc/htmlHead.jsp" %>
</head>

<body>
<div>
    <%@include file="../inc/topMenu.jsp" %>
</div>

<div id="psspopup"></div>

<div>
    <div id="adminUserGrid"></div>
    <script>
        jQuery(function ()
        {
            jQuery("#adminUserGrid")
                    .kendoGrid({
                        "toolbar": [{"name": "create"}],
                        "filterable": true,
                        "resizable" : true,
                        "editable": {
                            "mode": "inline",
                           // "cancelDelete": "cancelDelete",
                            "confirmation": true,
                            "confirmDelete": "confirmDelete"
                        },
                        "columns": [{
                            "width": "200px",
                            "title": "&nbsp;",
                            "command": [{"name": "edit", "text": "Edit"}, {"name": "destroy", "text": "Delete"}, {
                                "name": "password", "text": "Change Password", "click": function (e)
                                {
                                    var dataItem = this.dataItem($(e.currentTarget)
                                            .closest("tr"));

                                    wnd = $("#psspopup")
                                            .kendoWindow({
                                                title: "Change Password",
                                                modal: true,
                                                visible: false,
                                                resizable: false,
                                                width: 300
                                            })
                                            .data("kendoWindow");

                                    detailsTemplate = kendo.template($("#psstemplate")
                                            .html());
                                    wnd.content(detailsTemplate(dataItem));
                                    wnd.center()
                                            .open();

                                    $("#changePass")
                                            .kendoButton({
                                                click: function (e)
                                                {
                                                    // $(e.event.target).closest(".k-button").attr("id")

                                                    var p1   = $("#p1")
                                                            .val();
                                                    var p2   = $("#p2")
                                                            .val();
                                                    var idIn = $("#idIn")
                                                            .val();

                                                    if (p1 != p2)
                                                    {
                                                        alert("Sorry password confirmation does not match");
                                                        return;
                                                    }

                                                    if (/^((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!&?+]).{8,32})$/.test(p1) == false)
                                                    {
                                                        alert("Sorry password does not meet complexity requirements\nMust contains one digit from 0-9\nMust contains one lowercase characters\nMust contains one uppercase characters\nMust contains one special symbols in the list '@#$%!&?+'\nLength at least 8 characters and maximum of 32");
                                                        return;
                                                    }

                                                    var sendData = {};
                                                    sendData.p1  = p1;
                                                    sendData.id  = idIn;

                                                    $.ajax({
                                                        url: "${changePassUrl}",
                                                        method: "POST",
                                                        contentType: "application/json",
                                                        dataType: "json",
                                                        data: JSON.stringify(sendData),
                                                        success: function (data)
                                                        {
                                                            if (data)
                                                            {
                                                                alert("success!");
                                                            }
                                                            wnd.close();
                                                        }
                                                    });
                                                }
                                            });
                                }
                            }]
                        }, {"field": "id", "width": "140px", "title": "ID"}, {
                            "filterable": {
                                "ui": function userNameFilter(element)
                                {
                                    element.kendoAutoComplete({
                                        dataSource: {
                                            transport: {
                                                read: "${userNameUrl}"
                                            }
                                        }
                                    });
                                }
                            }, "field": "username", "width": "140px", "title": "Username"
                        }, {"field": "email", "width": "140px", "title": "Email"}, {
                            "filterable": {
                                "ui": function firstNameFilter(element)
                                {
                                    element.kendoAutoComplete({
                                        dataSource: {
                                            transport: {
                                                read: "${firstNameUrl}"
                                            }
                                        }
                                    });
                                }
                            }, "field": "firstName", "width": "140px", "title": "First Name"
                        }, {
                            "filterable": {
                                "ui": function lastNameFilter(element)
                                {
                                    element.kendoAutoComplete({
                                        dataSource: {
                                            transport: {
                                                read: "${lastNameUrl}"
                                            }
                                        }
                                    });
                                }
                            }, "field": "lastName", "width": "140px", "title": "Last Name"
                        }, {"field": "mainGroup", "width": "140px", "title": "Group"}, {
                            "field": "status",
                            "width": "140px",
                            "title": "Status"
                        }, {"field": "enabled", "width": "140px", "title": "Enabled"}, {
                            "field": "radiusEnabled",
                            "width": "140px",
                            "title": "Radius Enabled"
                        }, {"field": "canLogin", "width": "140px", "title": "Can Login"}, {
                            "field": "locked",
                            "width": "140px",
                            "title": "Is Locked"
                        }, {
                            "field": "accountNonLocked",
                            "width": "140px",
                            "title": "Acc. Non-Locked"
                        }, {
                            "field": "accountNonExpired",
                            "width": "140px",
                            "title": "Acc. Non-Expired"
                        }, {
                            "field": "accountExpired",
                            "width": "140px",
                            "title": "Acc. Expired"
                        }, {
                            "field": "credentialsNonExpired",
                            "width": "140px",
                            "title": "Cred. Non-Expired"
                        }, {
                            "field": "credentialsExpired",
                            "width": "140px",
                            "title": "Cred. Expired"
                        }, {"field": "lastSeen", "width": "140px", "title": "Last Seen"}, {
                            "field": "createdDate",
                            "width": "140px",
                            "title": "Created"
                        }, {"field": "lastModified", "width": "140px", "title": "Last Modified"}],
                        "pageable": true,
                        "sortable": true,
                        "dataSource": {
                            "serverPaging": true,
                            "schema": {
                                "total": "total",
                                "data": "data",
                                "model": {
                                    "id": "id",
                                    "fields": {
                                        "lastName": {"type": "string"},
                                        "credentialsNonExpired": {"type": "boolean"},
                                        "enabled": {"type": "boolean"},
                                        "mainGroup": {"type": "string"},
                                        "firstName": {"type": "string"},
                                        "canLogin": {"type": "boolean"},
                                        "lastSeen": {"type": "date"},
                                        "createdDate": {"type": "date"},
                                        "accountExpired": {"type": "boolean"},
                                        "accountNonExpired": {"type": "boolean"},
                                        "lastModified": {"type": "date"},
                                        "radiusEnabled": {"type": "boolean"},
                                        "locked": {"type": "boolean"},
                                        "credentialsExpired": {"type": "boolean"},
                                        "email": {"type": "string"},
                                        "username": {"type": "string"},
                                        "status": {"type": "string"},
                                        "accountNonLocked": {"type": "boolean"}
                                    }
                                }
                            },
                            "serverFiltering": true,
                            "serverGrouping": true,
                            "serverSorting": true,
                            "pageSize": 5.0,
                            "transport": {
                                "read": {
                                    "dataType": "json",
                                    "type": "POST",
                                    "contentType": "application/json",
                                    "url": "${readUrl}"
                                },
                                "create": {
                                    "dataType": "json",
                                    "type": "POST",
                                    "contentType": "application/json",
                                    "url": "${createUrl}",
                                    "complete": function(e) {
                                        $("#adminUserGrid").data("kendoGrid").dataSource.read();
                                    }},
                                "update": {
                                    "dataType": "json",
                                    "type": "POST",
                                    "contentType": "application/json",
                                    "url": "${updateUrl}"
                                },
                                "destroy": {
                                    "dataType": "json",
                                    "type": "POST",
                                    "contentType": "application/json",
                                    "url": "${destroyUrl}"
                                },
                                "parameterMap": function parameterMap(options, type)
                                {
                                    return JSON.stringify(options);
                                }
                            }
                        },
                        "height": "900px"
                    });
        })</script>
</div>

<script>
    function handle_confirmation(e)
    {
        alert("delete");
    }

    function handle_requestEnd(e)
    {
        $("#adminUserGrid")
                .data("kendoGrid")
                .dataSource
                .read();
    }
</script>

<script type="text/x-kendo-template" id="psstemplate">
    <div id="pass-container">
        <dl>
            <input id="idIn" type="hidden" value="#= id #"/>
            <dt>Password : <input id="p1" type="password"/></dt>
            <dt>Repeat : <input id="p2" type="password"/></dt>
            <dt>
                <button type="button" id="changePass">Save</button>
            </dt>
        </dl>
    </div>
</script>

</body>
</html>

