package com.sludev.propsystem.radiususermanager.util.kendo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class DatasourceSchemaVO
{
    public DatasourceModelVO model;
    public String data;
    public String total;
}


