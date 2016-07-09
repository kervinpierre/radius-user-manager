<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>
<%@ page session="true"%>

<c:url value="/api/admin/read-all-radchecks" var="readUrl" />
<c:url value="/api/admin/create-radcheck" var="createUrl" />
<c:url value="/api/admin/update-radcheck" var="updateUrl" />
<c:url value="/api/admin/delete-radcheck" var="destroyUrl" />

<c:url value="/api/admin/read-all-radcheck-usernames" var="userNameUrl" />

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
        <kendo:grid name="grid" pageable="true" sortable="true" height="900px"
                        filterable="true">
            <kendo:grid-editable mode="inline"  confirmation="handle_confirmation"
                                 confirmDelete="confirmDelete"  cancelDelete="cancelDelete">
            </kendo:grid-editable>
            <kendo:grid-toolbar>
                <kendo:grid-toolbarItem name="create" />
            </kendo:grid-toolbar>
            <kendo:grid-columns>
                <kendo:grid-column title="&nbsp;" width="200px">
                    <kendo:grid-column-command>
                        <kendo:grid-column-commandItem name="edit" text="Edit" />
                        <kendo:grid-column-commandItem name="destroy" text="Delete"  />
                    </kendo:grid-column-command>
                </kendo:grid-column>
                <kendo:grid-column title="ID" field="id"  width="120px" />
                <kendo:grid-column title="Username" field="username" width="120px" />
                <kendo:grid-column title="Attribute" field="attribute" width="120px"/>
                <kendo:grid-column title="Operation" field="op" width="120px"/>
                <kendo:grid-column title="Value" field="value" width="120px"/>
            </kendo:grid-columns>
            <kendo:dataSource  pageSize="5" serverPaging="true" serverSorting="true" serverFiltering="true" serverGrouping="true">
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
                                <kendo:grid-column-filterable>
                                    <kendo:grid-column-filterable-ui>
                                        <script>
                                            function userNameFilter(element) {
                                                element.kendoAutoComplete({
                                                    dataSource: {
                                                        transport: {
                                                            read: "${userNameUrl}"
                                                        }
                                                    }
                                                });
                                            }
                                        </script>
                                    </kendo:grid-column-filterable-ui>
                                </kendo:grid-column-filterable>
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="attribute" type="string">
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="op" type="string">
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="value" type="string">
                            </kendo:dataSource-schema-model-field>

                        </kendo:dataSource-schema-model-fields>
                    </kendo:dataSource-schema-model>
                </kendo:dataSource-schema>
            </kendo:dataSource>
        </kendo:grid>
    </div>

    </body>
</html>

