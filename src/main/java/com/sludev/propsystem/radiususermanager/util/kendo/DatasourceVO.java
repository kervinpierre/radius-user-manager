package com.sludev.propsystem.radiususermanager.util.kendo;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class DatasourceVO
{
    public Boolean  batch;
    public Object[] data;
    public Integer  page;
    public Integer  pageSize;
    public Integer  total;
    public Boolean  serverPaging;
    public DatasourceSchemaVO schema;

    private DatasourceVO()
    {

    }

    public void addSchema()
    {
        schema = new DatasourceSchemaVO();
        schema.data = "data";
    }

    public static DatasourceVO from()
    {
        DatasourceVO res = new DatasourceVO();

        return res;
    }

    public void validate()
    {
        if( total != null  && schema != null )
        {
            schema.total = "total";
        }
    }

    public void setSchemaModelId(String name)
    {
        if( StringUtils.isBlank(name) )
        {
            return;
        }

        if( schema.model == null )
        {
            schema.model = new DatasourceModelVO();
        }

        schema.model.id = name;
    }
}


