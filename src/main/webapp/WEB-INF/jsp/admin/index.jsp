<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>
<%@ page session="true"%>

<c:url value="/api/read-all-users?${_csrf.parameterName}=${_csrf.token}" var="readUrl" />
<c:url value="/api/create-user?${_csrf.parameterName}=${_csrf.token}" var="createUrl" />
<c:url value="/api/update-user?${_csrf.parameterName}=${_csrf.token}" var="updateUrl" />
<c:url value="/api/read-all-users" var="destroyUrl" />
<c:url value="/api/change-pass?${_csrf.parameterName}=${_csrf.token}" var="changePassUrl" />

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
        <kendo:grid name="grid" pageable="true" sortable="true" height="550px" >
            <kendo:grid-editable mode="inline" confirmation="Are you sure you want to remove this item?"/>
            <kendo:grid-toolbar>
                <kendo:grid-toolbarItem name="create" />
            </kendo:grid-toolbar>
            <kendo:grid-columns>
                <kendo:grid-column title="&nbsp;" width="200px">
                    <kendo:grid-column-command>
                        <kendo:grid-column-commandItem name="edit" text="Edit" />
                        <kendo:grid-column-commandItem name="destroy" text="Delete" />
                        <kendo:grid-column-commandItem name="password" text="Change Password">
                            <kendo:grid-column-commandItem-click>
                                <script>
                                    function(e)
                                    {
                                        var dataItem = this.dataItem($(e.currentTarget).closest("tr"));

                                        wnd = $("#psspopup")
                                                .kendoWindow({
                                                    title: "Change Password",
                                                    modal: true,
                                                    visible: false,
                                                    resizable: false,
                                                    width: 300
                                                }).data("kendoWindow");

                                        detailsTemplate = kendo.template($("#psstemplate").html());
                                        wnd.content(detailsTemplate(dataItem));
                                        wnd.center().open();

                                        $("#changePass").kendoButton({
                                            click: function(e) {
                                                // $(e.event.target).closest(".k-button").attr("id")

                                                var p1 = $("#p1").val();
                                                var p2 = $("#p2").val();
                                                var idIn = $("#idIn").val();

                                                if( p1 != p2 ) {
                                                    alert("Sorry password confirmation does not match");
                                                    return;
                                                }

                                                var sendData = {};
                                                sendData.p1 = p1;
                                                sendData.id = idIn;

                                                $.ajax({ url: "${changePassUrl}",
                                                        method: "POST",
                                                        contentType: "application/json",
                                                        dataType: "json",
                                                        data: JSON.stringify(sendData),
                                                        success:function(data) {
                                                            if( data ) {
                                                                alert("success!");
                                                            }
                                                            wnd.close();
                                                        }});
                                            }
                                        });
                                    }
                                </script>
                            </kendo:grid-column-commandItem-click>
                        </kendo:grid-column-commandItem>
                    </kendo:grid-column-command>
                </kendo:grid-column>
                <kendo:grid-column title="ID" field="id"  width="120px" />
                <kendo:grid-column title="Username" field="username" width="120px" />
                <kendo:grid-column title="Email" field="email" width="120px"/>
                <kendo:grid-column title="First Name" field="firstName" width="120px"/>
                <kendo:grid-column title="Last Name" field="lastName" width="120px"/>
                <kendo:grid-column title="Group" field="mainGroup" width="120px"/>
                <kendo:grid-column title="Status" field="status" width="120px"/>
                <kendo:grid-column title="Enabled" field="enabled" width="120px"/>
                <kendo:grid-column title="Can Login" field="canLogin" width="120px"/>
                <kendo:grid-column title="Is Locked" field="locked" width="120px"/>
                <kendo:grid-column title="Last Seen" field="lastSeen" width="120px"/>
                <kendo:grid-column title="Created" field="created" width="120px"/>
                <kendo:grid-column title="Last Modified" field="lastModified" width="120px"/>
            </kendo:grid-columns>
            <kendo:dataSource  pageSize="2" serverPaging="true" serverSorting="true" serverFiltering="true" serverGrouping="true">
                <kendo:dataSource-transport>
                    <kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="POST" contentType="application/json" />
                    <kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json"/>
                    <kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json" />
                    <kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="POST" contentType="application/json" />
                    <kendo:dataSource-transport-parameterMap>
                        <script>
                            function parameterMap(options,type) {
                                return JSON.stringify(options);
                            }
                        </script>
                    </kendo:dataSource-transport-parameterMap>
                </kendo:dataSource-transport>
                <kendo:dataSource-schema  data="data" total="total" >
                    <kendo:dataSource-schema-model id="id">
                        <kendo:dataSource-schema-model-fields>

                            <kendo:dataSource-schema-model-field name="username" type="string">
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="email" type="string">
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="firstName" type="string">
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="lastName" type="string">
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="mainGroup" type="string">
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="status" type="string">
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="enabled" type="boolean" />
                            <kendo:dataSource-schema-model-field name="canLogin" type="boolean" />
                            <kendo:dataSource-schema-model-field name="locked" type="boolean" />

                            <kendo:dataSource-schema-model-field name="lastSeen" type="date">
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="created" type="date">
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="lastModified" type="date">
                            </kendo:dataSource-schema-model-field>

                        </kendo:dataSource-schema-model-fields>
                    </kendo:dataSource-schema-model>
                </kendo:dataSource-schema>
            </kendo:dataSource>
        </kendo:grid>
    </div>

        <script type="text/x-kendo-template" id="psstemplate">
            <div id="pass-container">
                <dl>
                    <input id="idIn" type="hidden" value="#= id #" />
                    <dt>Password : <input id="p1" type="password"/></dt>
                    <dt>Repeat   : <input id="p2" type="password"/></dt>
                    <dt><button type="button" id="changePass">Save</button></dt>
                </dl>
            </div>
        </script>

    </body>
</html>

