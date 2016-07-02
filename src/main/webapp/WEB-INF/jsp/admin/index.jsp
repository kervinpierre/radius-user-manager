<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>
<%@ page session="true"%>

<c:url value="/api/read-all-users?${_csrf.parameterName}=${_csrf.token}" var="readUrl" />
<c:url value="/api/create-user?${_csrf.parameterName}=${_csrf.token}" var="createUrl" />
<c:url value="/api/update-user?${_csrf.parameterName}=${_csrf.token}" var="updateUrl" />
<c:url value="/api/read-all-users" var="destroyUrl" />

<!DOCTYPE html>
<html>
    <head>
        <%@include file="../inc/htmlHead.jsp" %>
    </head>

    <body>
        <div>
            <%@include file="../inc/topMenu.jsp" %>
        </div>

    <div>
        <kendo:grid name="grid" pageable="true" sortable="true" height="550px" >
            <kendo:grid-editable mode="inline" confirmation="Are you sure you want to remove this item?"/>
            <kendo:grid-toolbar>
                <kendo:grid-toolbarItem name="create"/>
            </kendo:grid-toolbar>
            <kendo:grid-columns>
                <kendo:grid-column title="ID" field="id"  width="120px" />
                <kendo:grid-column title="Username" field="username" width="120px" />
                <kendo:grid-column title="Set Password" field="password" width="120px" />
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
                <kendo:grid-column title="&nbsp;" width="250px">
                    <kendo:grid-column-command>
                        <kendo:grid-column-commandItem name="edit" />
                        <kendo:grid-column-commandItem name="destroy" />
                    </kendo:grid-column-command>
                </kendo:grid-column>
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
                    <kendo:dataSource-schema-model>
                        <kendo:dataSource-schema-model-fields>

                            <kendo:dataSource-schema-model-field name="username" type="string">
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="password" type="string">
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

                            <kendo:dataSource-schema-model-field name="lastSeen" type="string">
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="created" type="string">
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="lastModified" type="string">
                            </kendo:dataSource-schema-model-field>

                        </kendo:dataSource-schema-model-fields>
                    </kendo:dataSource-schema-model>
                </kendo:dataSource-schema>
            </kendo:dataSource>
        </kendo:grid>
    </div>

    </body>
</html>

