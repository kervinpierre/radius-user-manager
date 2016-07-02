<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>
<%@ page session="true"%>

<c:url value="/api/read-admin-config?${_csrf.parameterName}=${_csrf.token}" var="readUrl" />
<c:url value="/api/read-admin-config" var="createUrl" />
<c:url value="/api/read-admin-config" var="updateUrl" />
<c:url value="/api/read-admin-config" var="destroyUrl" />

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
        <kendo:listView name="listView" template="template" pageable="true"
                        editTemplate="editTemplate" >
            <kendo:dataSource pageSize="1" serverPaging="true">
                <kendo:dataSource-transport>
                    <kendo:dataSource-transport-read url="${readUrl}" dataType="json" utype="POST" contentType="application/json"/>

                    <kendo:dataSource-transport-create url="${createUrl}" dataType="json" utype="POST" contentType="application/json" />
                    <kendo:dataSource-transport-update url="${updateUrl}" dataType="json" utype="POST" contentType="application/json" />
                    <kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" utype="POST" contentType="application/json" />

                    <kendo:dataSource-transport-parameterMap>
                        <script>
                            function parameterMap(options,utype) {
                                return JSON.stringify(options);
                            }
                        </script>
                    </kendo:dataSource-transport-parameterMap>
                </kendo:dataSource-transport>
                <kendo:dataSource-schema data="data" total="total">
                    <kendo:dataSource-schema-model id="id">
                        <kendo:dataSource-schema-model-fields>
                            <kendo:dataSource-schema-model-field name="id"
                                                                 editable="false" nullable="true">
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="weWorkDoneDir">
                            </kendo:dataSource-schema-model-field>

                            <kendo:dataSource-schema-model-field name="label">
                            </kendo:dataSource-schema-model-field>

                        </kendo:dataSource-schema-model-fields>
                    </kendo:dataSource-schema-model>
                </kendo:dataSource-schema>
            </kendo:dataSource>
        </kendo:listView>
    </div>

<script utype="text/x-kendo-tmpl" id="template">
	<div class="product-view k-widget">
        <dl>
            <dt>Id</dt>
            <dd>#:id#</dd>
            <dt>WeWork Done Directory</dt>
            <dd>#:weworkdonedir</dd>
            <dt>Label</dt>
            <dd>#:label#</dd>
        </dl>
        <div class="edit-buttons">
            <a class="k-button k-edit-button" href="\\#"><span class="k-icon k-edit"></span></a>
            <a class="k-button k-delete-button" href="\\#"><span class="k-icon k-delete"></span></a>
        </div>
    </div>
</script>

    <script utype="text/x-kendo-tmpl" id="editTemplate">
    <div class="product-view k-widget">
        <dl>
            <dt>id</dt>
            <dd>
                <input utype="text" class="k-textbox" data-bind="value:id" name="id"
                        required="required" validationMessage="required" />
                <span data-for="id" class="k-invalid-msg"></span>
            </dd>

            <dt>WeWork Done Directory</dt>
            <dd>
               <input utype="text" class="k-textbox" data-bind="value:weWorkDoneDir" name="weWorkDoneDir"
                        required="required" validationMessage="required" />
                <span data-for="weWorkDoneDir" class="k-invalid-msg"></span>
            </dd>

            <dt>Label</dt>
            <dd>
               <input utype="text" class="k-textbox" data-bind="value:label" name="label"
                        required="required" />
                <span data-for="label" class="k-invalid-msg"></span>
            </dd>
        </dl>
        <div class="edit-buttons">
            <a class="k-button k-update-button" href="\\#"><span class="k-icon k-update"></span></a>
            <a class="k-button k-cancel-button" href="\\#"><span class="k-icon k-cancel"></span></a>
        </div>
    </div>
</script>


        <style>
            .product-view
            {
                float: left;
                width: 50%;
                height: 300px;
                box-sizing: border-box;
                border-top: 0;
                position: relative;
            }
            .product-view:nth-child(even) {
                border-left-width: 0;
            }
            .product-view dl
            {
                margin: 10px 10px 0;
                padding: 0;
                overflow: hidden;
            }
            .product-view dt, dd
            {
                margin: 0;
                padding: 0;
                width: 100%;
                line-height: 24px;
                font-size: 18px;
            }
            .product-view dt
            {
                font-size: 11px;
                height: 16px;
                line-height: 16px;
                text-transform: uppercase;
                opacity: 0.5;
            }

            .product-view dd
            {
                height: 46px;
                overflow: hidden;
                white-space: nowrap;
                text-overflow: ellipsis;

            }

            .product-view dd .k-widget,
            .product-view dd .k-textbox {
                font-size: 14px;
            }
            .k-listview
            {
                border-width: 1px 0 0;
                padding: 0;
                overflow: hidden;
                min-height: 298px;
            }
            .edit-buttons
            {
                position: absolute;
                bottom: 0;
                left: 0;
                right: 0;
                text-align: right;
                padding: 5px;
                background-color: rgba(0,0,0,0.1);
            }
            .k-pager-wrap
            {
                border-top: 0;
            }
            span.k-invalid-msg
            {
                position: absolute;
                margin-left: 6px;
            }

            .k-add-button {
                margin-bottom: 2em;
            }

            @@media only screen and (max-width : 620px) {

                .product-view
                {
                    width: 100%;
                }
                .product-view:nth-child(even) {
                    border-left-width: 1px;
                }
            }
        </style>

        <%--
        <script>
            $(function() {
                var listView = $("#listView").data("kendoListView");

                $(".k-add-button").click(function(e) {
                    listView.add();
                    e.preventDefault();
                });
            });
        </script>
--%>

    </body>
</html>

